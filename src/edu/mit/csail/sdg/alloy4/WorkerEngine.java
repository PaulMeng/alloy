/*
 * Alloy Analyzer 4 -- Copyright (c) 2006-2008, Felix Chang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.mit.csail.sdg.alloy4;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * This class allows you to execute tasks in a subprocess, and receive its outputs via callback.
 *
 * <p> By executing the task in a subprocess, we can always terminate a runaway task explicitly by calling stop(),
 * and we can control how much memory to give to the subprocess.
 *
 * <p> Only one task may execute concurrently at any given time; if you try to issue a new task
 * when the previous task hasn't finished, then you will get an IOException.
 *
 * <p> As long as the subprocess hasn't terminated either due to crashing or due to user calling stop(),
 * then the same subprocess is reused to execute each subsequent task; however, if the subprocess crashed,
 * the crash will be reported to the parent process via callback, and if we try to execute another task,
 * then a new subprocess will be spawned automatically.
 */

public final class WorkerEngine {

  /** This defines an interface for performing tasks in a subprocess. */
  public interface WorkerTask extends Serializable {
     /** The task should write zero or more non-null Objects to the ObjectOutputStream to report progress to the parent process. */
     public void run(ObjectOutputStream out);
  }

  /** This defines an interface for receiving results from a subprocess. */
  public interface WorkerCallback {
     /** The task would send zero or more non-null Objects to this handler (the objects will be serialized by the sub JVM and deseralized in the parent JVM). */
     public void callback(Object msg);
     /** If the task completed successfully, this method will be called. */
     public void done();
     /** If the task terminated with an error, this method will be called. */
     public void fail();
  }

  /** This wraps the given InputStream such that the resulting object's "close()" method does nothing; if stream==null, we get an InputStream that always returns EOF. */
  private static InputStream wrap(final InputStream stream) {
     return new InputStream() {
        public int read()                           throws IOException { if (stream==null) return -1; else return stream.read(); }
        public int read(byte b[], int off, int len) throws IOException { if (len==0) return 0; else if (stream==null) return -1; else return stream.read(b, off, len); }
        public long skip(long n)                    throws IOException { if (stream==null) return 0; else return stream.skip(n); }
     };
  }

  /** This wraps the given OutputStream such that the resulting object's "close()" method simply calls "flush()"; if stream==null, we get an OutputStream that ignores all writes. */
  private static OutputStream wrap(final OutputStream stream) {
     return new OutputStream() {
        public void write(int b)                      throws IOException { if (stream!=null) stream.write(b); }
        public void write(byte b[], int off, int len) throws IOException { if (stream!=null) stream.write(b, off, len); }
        public void flush()                           throws IOException { if (stream!=null) stream.flush(); }
        public void close()                           throws IOException { if (stream!=null) stream.flush(); } // we intentionally DON'T close the file
     };
  }

  /** If nonnull, it is the latest sub JVM. */
  private static Process latest_sub = null;

  /** If nonnull, it is the latest worker thread talking to the sub JVM. (If latest_sub==null, then we guarantee latest_manager is also null) */
  private static Thread latest_manager = null;

  /** Constructor is private since this class does not need to be instantiated. */
  private WorkerEngine() { }

  /** This terminates the subprocess, and prevent any further results from reaching the parent's callback handler. */
  public static void stop() {
     synchronized(WorkerEngine.class) {
        try { if (latest_sub!=null) latest_sub.destroy(); } finally { latest_manager=null; latest_sub=null; }
     }
  }

  /** This returns true iff the subprocess is still busy processing the last task. */
  public static boolean isBusy() {
     synchronized(WorkerEngine.class) { return latest_manager!=null && latest_manager.isAlive(); }
  }

  /**
   * This issues a new task to the subprocess; if subprocess hasn't been constructed yet or has terminated abnormally, this method will launch a new subprocess.
   * @param task - the task that we want the subprocess to execute
   * @param newmem - the amount of memory (in megabytes) we want the subprocess to have (if the subproces has not terminated, then this parameter is ignored)
   * @param jniPath - if nonnull and nonempty, then it specifies the subprocess's default JNI library location
   * @param callback - the handler that will receive outputs from the task
   * @throws IOException - if the subprocess is still busy processing the last task
   * @throws IOException - if an error occurred in launching a sub JVM or talking to it
   */
  public static void run(final WorkerTask task, final int newmem, final String jniPath, final WorkerCallback callback) throws IOException {
     synchronized(WorkerEngine.class) {
        final Process sub;
        if (latest_manager!=null && latest_manager.isAlive()) throw new IOException("Subprocess still performing the previous task.");
        try { if (latest_sub!=null) latest_sub.exitValue(); latest_manager=null; latest_sub=null; } catch(IllegalThreadStateException ex) { }
        if (latest_sub==null) {
           String java = "java", javahome = System.getProperty("java.home");
           if (javahome!=null && javahome.length()>0) {
              // First try "[JAVAHOME]/bin/java"
              File f = new File(javahome + File.separatorChar + "bin" + File.separatorChar + "java");
              // Then try "[JAVAHOME]/java"
              if (!f.isFile()) f = new File(javahome + File.separatorChar + "java");
              // All else, try "java" (and let the Operating System search the program path...)
              if (f.isFile()) java = f.getAbsolutePath();
           }
           callback.callback("<NEW JVM>");
           if (jniPath!=null && jniPath.length()>0)
               sub = Runtime.getRuntime().exec(new String[] {
                  java,
                  "-Xmx" + newmem + "m",
                  "-Djava.library.path=" + jniPath,
                  "-cp", System.getProperty("java.class.path"), WorkerEngine.class.getName(),
                  Version.buildDate(), ""+Version.buildNumber()
               });
           else
               sub = Runtime.getRuntime().exec(new String[] {
                  java,
                  "-Xmx" + newmem + "m",
                  "-cp", System.getProperty("java.class.path"), WorkerEngine.class.getName(),
                  Version.buildDate(), ""+Version.buildNumber()
               });
           latest_sub = sub;
        } else {
           sub = latest_sub;
        }
        latest_manager = new Thread(new Runnable() {
           public void run() {
              ObjectInputStream sub2main = null;
              ObjectOutputStream main2sub = null;
              try {
                 main2sub = new ObjectOutputStream(wrap(sub.getOutputStream())); main2sub.writeObject(task); main2sub.close();
                 sub2main = new ObjectInputStream(wrap(sub.getInputStream()));
              } catch(Throwable ex) {
                 sub.destroy(); Util.close(main2sub); Util.close(sub2main);
                 synchronized(WorkerEngine.class) { if (latest_sub != sub) return; callback.fail(); return; }
              }
              while(true) {
                 synchronized(WorkerEngine.class) { if (latest_sub != sub) return; }
                 Object x;
                 try {
                    x = sub2main.readObject();
                 } catch(Throwable ex) {
                    sub.destroy(); Util.close(sub2main);
                    synchronized(WorkerEngine.class) { if (latest_sub != sub) return; callback.fail(); return; }
                 }
                 synchronized(WorkerEngine.class) { if (latest_sub != sub) return; if (x==null) {callback.done(); return;} else callback.callback(x); }
              }
           }
        });
        latest_manager.start();
     }
  }

  /**
   * This is the entry point for the sub JVM.
   *
   * <p> Behavior is very simple: it reads a WorkerTask object from System.in, then execute it, then read another...
   * If any error occurred, or if it's disconnected from the parent process's pipe, it then terminates itself
   * (since we assume the parent process will notice it and react accordingly)
   */
  public static void main(String[] args) {
     // To prevent people from accidentally invoking this class, or invoking it from an incompatible version, we add a simple sanity check on the command line arguments
     if (args.length!=2) return;
     if (!args[0].equals(Version.buildDate())) return;
     if (!args[1].equals("" + Version.buildNumber())) return;
     // To prevent a zombie process, we set a default handler to terminate itself if something does slip through our detection
     Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
        public void uncaughtException(Thread t, Throwable e) { halt(1); }
     });
     // Redirect System.in, System.out, System.err to no-op (so that if a task tries to read/write to System.in/out/err, those reads and writes won't mess up the ObjectInputStream/ObjectOutputStream)
     System.setIn(wrap((InputStream)null));
     System.setOut(new PrintStream(wrap((OutputStream)null)));
     System.setErr(new PrintStream(wrap((OutputStream)null)));
     final FileInputStream in = new FileInputStream(FileDescriptor.in);
     final FileOutputStream out = new FileOutputStream(FileDescriptor.out);
     // Preload these 3 libraries; on MS Windows with JDK 1.6 this seems to prevent freezes
     try { System.loadLibrary("minisat");       } catch(Throwable ex) { }
     try { System.loadLibrary("minisatprover"); } catch(Throwable ex) { }
     try { System.loadLibrary("zchaff");        } catch(Throwable ex) { }
     // Now we repeat the following read-then-execute loop
     Thread t = null;
     while(true) {
        final WorkerTask task;
        try {
           ObjectInputStream oin = new ObjectInputStream(wrap(in));
           task = (WorkerTask) oin.readObject();
           oin.close();
        } catch(Throwable ex) {
           halt(1);
           return;
        }
        // Our main thread has a loop that keeps "attempting" to read bytes from System.in,
        // and delegate the actual task to a separate "worker thread".
        // This way, if the parent process terminates, then this subprocess should see it almost immediately
        // (since the inter-process pipe will be broken) and will terminate (regardless of the status of the worker thread)
        if (t!=null && t.isAlive()) {
           // We only get here if the previous subtask has informed the parent that the job is done,
           // and that the parent then issued another job.  So we wait up to 5 seconds for the worker thread to confirm its termination.
           // If 5 seconds is up, then we assume something terrible has happened.
           try {t.join(5000); if (t.isAlive()) halt(1);} catch (Throwable ex) {halt(1);}
        }
        t = new Thread(new Runnable() {
           public void run() { try { ObjectOutputStream x=new ObjectOutputStream(wrap(out)); task.run(x); x.writeObject(null); x.close(); } catch(Throwable ex) { halt(1); } }
        });
        t.start();
     }
  }

  /** This method terminates the caller's process. */
  private static void halt(int exitCode) { Runtime.getRuntime().halt(exitCode); }
}