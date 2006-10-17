package edu.mit.csail.sdg.alloy4util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;
import java.util.Random;

/**
 * Utility class for doing common I/O and XML operations.
 *
 * <p/><b>Thread Safety:</b>  Safe.
 *
 * @author Felix Chang
 */

public final class Util {

    /** This constructor is private, since this utility class never needs to be instantiated. */
    private Util() { }

    /** This variable caches the value of the system's file separator. */
    private static final String fs=System.getProperty("file.separator");

    /** Returns true iff running on Windows **/
    public static boolean onWindows() { return System.getProperty("os.name").toLowerCase().startsWith("windows"); };

    /** Returns true iff running on a Mac OS X. **/
    public static boolean onMac() { return System.getProperty("mrj.version")!=null; }

    /** Appends "st", "nd", "rd", "th"... as appropriate; (for example, 1 becomes 1st, 2 becomes 2nd...) */
    public static String th(int n) {
        if (n>0 && (n%10)==1 && (n%100)!=11) return n+"st";
        if (n>0 && (n%10)==2 && (n%100)!=12) return n+"nd";
        if (n>0 && (n%10)==3 && (n%100)!=13) return n+"rd";
        return n+"th";
    }

    /**
     * This method is called when a harmless or impossible exception occurred;
     * currently it does not do anything, but we could choose to log the occurrence instead.
     */
    public static void harmless(String message, Exception ex) { }

    /**
     * Write a String into a PrintWriter, and encode special characters using XML-specific encoding.
     *
     * <p/>
     * In particular, it changes LESS THAN, GREATER THAN, AMPERSAND, SINGLE QUOTE, and DOUBLE QUOTE
     * into the &amp;lt; &amp;gt; &amp;amp; &amp;apos; and &amp;quot; and turns any characters
     * outside of 32..126 range into the &amp;#xHHHH encoding
     * (where HHHH is the 4 digit lowercase hexadecimal representation of the character value).
     *
     * @param out - the PrintWriter to write into
     * @param str - the String to write out
     */
    public static void encodeXML(PrintWriter out, String str) {
        int n=str.length();
        for(int i=0; i<n; i++) {
            char c=str.charAt(i);
            if (c=='<') { out.write("&lt;"); continue; }
            if (c=='>') { out.write("&gt;"); continue; }
            if (c=='&') { out.write("&amp;"); continue; }
            if (c=='\'') { out.write("&apos;"); continue; }
            if (c=='\"') { out.write("&quot;"); continue; }
            if (c>=32 && c<127) { out.write(c); continue; }
            out.write("&#x");
            String v=Integer.toString((int)c, 16);
            while(v.length()<4) v="0"+v;
            out.write(v);
            out.write(';');
        }
    }

    /**
     * Write a list of Strings into a PrintWriter, where strs[2n] are written as-is, and strs[2n+1] are XML-encoded.
     *
     * <p/> For example, if you call encodeXML(out, A, B, C, D, E), it is equivalent to the following:
     * <br/> out.print(A);
     * <br/> out.encodeXML(B);
     * <br/> out.print(C);
     * <br/> out.encodeXML(D);
     * <br/> out.print(E);
     * <br/> In other words, it writes the even entries as-is, and writes the odd entries using XML encoding.
     *
     * @param out - the PrintWriter to write into
     * @param strs - the list of Strings to write out
     */
    public static void encodeXMLs(PrintWriter out, String... strs) {
        for(int i=0; i<strs.length; i++) {
            if ((i%2)==0) out.print(strs[i]); else encodeXML(out,strs[i]);
        }
    }

    /** Copy the list of files from JAR into the destination directory, then set the correct permissions on them. */
    public static synchronized void copy(boolean executable, String destdir, String... names) {
        String[] args=new String[names.length+2];
        args[0]="chmod"; // This does not work on Windows, but the "executable" bit is not needed on Windows anyway.
        args[1]=(executable ? "700" : "600"); // 700 means read+write+executable; 600 means read+write.
        for(int i=0; i<names.length; i++) {
            String name=names[i];
            String destname=(destdir+fs+name).replace('/', File.separatorChar);
            int last=destname.lastIndexOf(File.separatorChar);
            new File(destname.substring(0,last)).mkdirs(); // Error will be caught later by the file copy
            copy(name, destname);
            args[i+2]=destname;
        }
        if (onWindows()) return;
        try {Runtime.getRuntime().exec(args).waitFor();} catch (Exception ex) {harmless("chmod",ex);}
    }

    /** Copy the given file from JAR into the destination file; the destination file is deleted first. */
    private static synchronized void copy(String sourcename, String destname) {
        InputStream resStream=Util.class.getClassLoader().getResourceAsStream(sourcename);
        // The following lines can be commented out in the release builds; they're only useful for Alloy 4 developers
        String os = System.getProperty("os.name").toLowerCase().replace(' ','-');
        if (os.startsWith("mac-")) os="mac"; else if (os.startsWith("windows-")) os="windows";
        String arch = System.getProperty("os.arch").toLowerCase().replace(' ','-');
        if (arch.equals("powerpc")) arch="ppc-"+os; else arch=arch.replaceAll("\\Ai[3456]86\\z","x86")+"-"+os;
        if (resStream==null) {
            try { resStream=new FileInputStream(".."+fs+"alloy4"+fs+sourcename); }
            catch (FileNotFoundException e) { resStream=null; }
        }
        if (resStream==null) {
            try { resStream=new FileInputStream(".."+fs+"alloy4"+fs+"jni"+fs+arch+fs+sourcename); }
            catch (FileNotFoundException e) { resStream=null; }
        }
        if (resStream==null) {
            try { resStream=new FileInputStream(".."+fs+"alloy4"+fs+"binary"+fs+arch+fs+sourcename); }
            catch (FileNotFoundException e) { resStream=null; }
        }
        if (resStream==null) {
            try { resStream=new FileInputStream(".."+fs+"alloy4viz"+fs+"binary"+fs+arch+fs+sourcename); }
            catch (FileNotFoundException e) { harmless("The file is just not applicable on this platform",e); return; }
        }
        // The above lines can be commented out in the release builds; they're only useful for Alloy 4 developers
        if (resStream==null) OurDialog.fatal(null,"Error occurred in writing the file \""+destname+"\"!");
        boolean result=true;
        InputStream binStream=new BufferedInputStream(resStream);
        FileOutputStream tmpFileOutputStream=null;
        try {
            (new File(destname)).delete();
            tmpFileOutputStream=new FileOutputStream(destname);
        } catch (FileNotFoundException e) {
            result=false;
        }
        if (tmpFileOutputStream!=null) {
            try {
                byte[] b = new byte[16384];
                while (true) {
                    int numRead = binStream.read(b);
                    if (numRead == -1) break;
                    tmpFileOutputStream.write(b, 0, numRead);
                }
            } catch(IOException e) { result=false; }
            try { tmpFileOutputStream.close(); } catch(IOException ex) { result=false; }
        }
        try { binStream.close(); } catch(IOException ex) { result=false; }
        try { resStream.close(); } catch(IOException ex) { result=false; }
        if (!result) OurDialog.fatal(null,"Error occurred in writing the file \""+destname+"\"!");
    }

    /** This variable caches the result of alloyHome() function call. */
    private static String alloyHome=null;

    /** Determines an appropriate temporary directory to store Alloy files; it is guaranteed to be an absolute path. */
    public static synchronized String alloyHome() {
        if (alloyHome!=null) return alloyHome;
        String temp=System.getProperty("java.io.tmpdir");
        if (temp==null || temp.length()==0)
            OurDialog.fatal(null,"Error! Please specify a temporary directory with the Java java.io.tmpdir property!");
        String username=System.getProperty("user.name");
        File tempfile=new File(temp+fs+"alloy4tmp-"+(username==null?"":username));
        String ans=tempfile.getAbsolutePath();
        tempfile.mkdirs();
        if (!tempfile.isDirectory()) {
            OurDialog.fatal(null, "Error! Cannot create the temporary directory "+ans);
        }
        if (!onWindows()) {
            String[] args={"chmod", "700", ans};
            try {Runtime.getRuntime().exec(args).waitFor();}
            catch (Exception ex) {harmless("Util.alloyHome()", ex);} // We only intend to make a best effort.
        }
        return alloyHome=ans+fs;
    }

    /**
     * Create an empty temporary directory for use, designate it "deleteOnExit", then return it.
     * It is guaranteed to be an absolute path.
     */
    public static String maketemp() {
        Random r=new Random(new Date().getTime());
        while(true) {
            int i=r.nextInt(1000000);
            String dest = Util.alloyHome()+"tmp"+fs+i;
            File f=new File(dest);
            if (f.exists()) continue;
            f.mkdirs();
            f.deleteOnExit();
            if (f.isDirectory()) return dest+fs;
        }
    }

    /** Lock then overwrite the given file; throws IOException if there's an error. */
    public static synchronized void lockThenWrite(String filename, String content) throws IOException {
        RandomAccessFile raf=null;
        raf = new RandomAccessFile(new File(filename),"rw"); // If this line fails, IOException will be thrown
        FileChannel fc = raf.getChannel(); // This line does not throw IOException
        try {
            // If any code in this block throws IOException, it will go to the catch clause for clean up
            @SuppressWarnings("unused")
            FileLock lock=fc.lock();
            raf.setLength(0);
            raf.write(content.getBytes("UTF-8"));
            raf.close();
        } catch(IOException ex) {
            // Here, we make a final attempt to close the file, before forwarding the IOException to outside
            // (Since open file descriptors is a scarce resource... furthermore, if it was locked, then
            // it will prevent other processes from accessing this)
            try { raf.close(); } catch(IOException ex2) { harmless("close",ex2); }
            throw ex;
        }
    }

    /** Lock, read up to 30000 bytes, then erase the given file; throws IOException if there's an error. */
    public static synchronized String lockThenReadThenErase(String filename) throws IOException {
        RandomAccessFile raf;
        raf = new RandomAccessFile(new File(filename),"rw"); // If this line fails, IOException will be thrown
        FileChannel fc = raf.getChannel(); // This line does not throw IOException
        byte[] buffer = new byte[30000];
        int length = 0;
        try {
            // If any code in this block throws IOException, it will go to the catch clause for clean up
            @SuppressWarnings("unused")
            FileLock lock=fc.lock();
            while(true) {
                int c=raf.read();
                if (c<0) break;
                buffer[length]=(byte)c;
                length++;
            }
            raf.seek(0);
            raf.setLength(0);
            raf.close();
            return new String(buffer,0,length,"UTF-8");
        } catch(IOException ex) {
            // Here, we make a final attempt to close the file, before forwarding the IOException to outside
            // (Since open file descriptors is a scarce resource... furthermore, if it was locked, then
            // it will prevent other processes from accessing this)
            try { raf.close(); } catch(IOException ex2) { harmless("close",ex2); }
            throw ex;
        }
    }
}