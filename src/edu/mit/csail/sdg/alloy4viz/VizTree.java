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

package edu.mit.csail.sdg.alloy4viz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import edu.mit.csail.sdg.alloy4compiler.ast.Expr;
import edu.mit.csail.sdg.alloy4compiler.ast.ExprHasName;
import edu.mit.csail.sdg.alloy4compiler.ast.ExprVar;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig.Field;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Tuple;
import edu.mit.csail.sdg.alloy4compiler.translator.A4TupleSet;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4.OurTree;
import edu.mit.csail.sdg.alloy4.Pair;
import edu.mit.csail.sdg.alloy4.Util;
import static edu.mit.csail.sdg.alloy4.Util.encode;

/** GUI tree that displays an instance as a tree.
 *
 * <p><b>Thread Safety:</b> Can be called only by the AWT event thread.
 */

public final class VizTree extends OurTree {

   /** {@inheritDoc} */
   @Override public String convertValueToText(Object val, boolean selected, boolean expanded, boolean leaf, int row, boolean focus) {
      String c = ">";
      if (onWindows) c = selected ? " style=\"color:#ffffff;\">" : " style=\"color:#000000;\">";
      if (val instanceof A4Solution) return "<html> <b" + c + encode(title==null ? "" : title)+"</b></html>";
      if (val instanceof Sig) {
         String label = ((Sig)val).label;
         if (label.startsWith("this/")) label = label.substring(5);
         return "<html> <b" + c + "sig</b> <span" + c + encode(label) + "</span></html>";
      }
      if (val instanceof ExprVar) return "<html> <b" + c + "set</b> <span" + c + encode(((ExprVar)val).label) + "</span></html>";
      if (val instanceof String) return "<html> <span" + c + encode((String)val) + "</span></html>";
      if (val instanceof Pair) return "<html> <b" + c + "field</b> <span" + c + encode(((ExprHasName)(((Pair<?,?>)val).b)).label) + "</span></html>";
      if (val instanceof A4Tuple) {
         StringBuilder sb = new StringBuilder("<html> <span" + c);
         A4Tuple tp = (A4Tuple) val;
         for(int i=1; i<tp.arity(); i++) {
            if (i>1) sb.append(" -> ");
            sb.append(encode(tp.atom(i)));
         }
         sb.append("</span></html>");
         return sb.toString();
      }
      return "";
   }

   /** {@inheritDoc} */
   @Override public Object alloyAskRoot() { return instance; }

   /** {@inheritDoc} */
   @Override public List<?> alloyAsk(Object parent) {
      List<Object> ans = new ArrayList<Object>();
      try {
         if (parent instanceof A4Solution) {
            for(Sig s: instance.getAllReachableSigs()) { if (s!=Sig.NONE) ans.add(s); }
            for(ExprVar v: instance.getAllSkolems()) if (v.type.arity()==1 && v.label.startsWith("$")) ans.add(v);
            Collections.sort(ans, new Comparator<Object>() {
               public int compare(Object a, Object b) {
                  if (a==Sig.UNIV) return -1; else if (b==Sig.UNIV) return 1;
                  if (a==Sig.SIGINT) return -1; else if (b==Sig.SIGINT) return 1;
                  if (a==Sig.SEQIDX) return -1; else if (b==Sig.SEQIDX) return 1;
                  if (a==Sig.STRING) return -1; else if (b==Sig.STRING) return 1;
                  String t1, t2;
                  if (a instanceof Sig) { t1=((Sig)a).label; if (b instanceof ExprVar) return -1; else t2=((Sig)b).label; }
                     else { t1=((ExprVar)a).label; if (b instanceof Sig) return 1; else t2=((ExprVar)b).label; }
                  return Util.slashComparator.compare(t1, t2);
               }
            });
         } else if (parent instanceof Sig || parent instanceof ExprVar) {
            A4TupleSet ts = (A4TupleSet) (instance.eval((Expr)parent));
            for(A4Tuple t: ts) ans.add(t.atom(0));
         } else if (parent instanceof String) {
            String atom = (String)parent;
            for(Sig s: instance.getAllReachableSigs()) for(Field f: s.getFields()) for(A4Tuple t: instance.eval(f)) {
               if (t.atom(0).equals(atom)) { ans.add(new Pair<String,ExprHasName>(atom, f)); break; }
            }
            for(ExprVar f: instance.getAllSkolems()) if (f.type.arity()>1) for(A4Tuple t: (A4TupleSet)(instance.eval(f))) {
               if (t.atom(0).equals(atom)) { ans.add(new Pair<String,ExprHasName>(atom, f)); break; }
            }
         } else if (parent instanceof Pair) {
            Pair<?,?> p = (Pair<?,?>)parent;
            ExprHasName rel = (ExprHasName) (p.b);
            String atom = (String) (p.a);
            for(A4Tuple tuple: (A4TupleSet) (instance.eval(rel))) if (tuple.atom(0).equals(atom)) {
               if (tuple.arity()==2) ans.add(tuple.atom(1)); else ans.add(tuple);
            }
         } else if (parent instanceof A4Tuple) {
            A4Tuple tp = (A4Tuple)parent;
            for(int i=1; i<tp.arity(); i++) ans.add(tp.atom(i));
            for(int i=ans.size()-1; i>0; i--) for(int j=i-1; j>=0; j--) if (ans.get(i).equals(ans.get(j))) { ans.remove(i); break; }
         }
         return ans;
      } catch(Err er) {
         return ans;
      }
   }

   /** This suppresses javac's warning about missing serialVersionUID. */
   private static final long serialVersionUID = 0;

   /** Caches whether we're on Windows or not. */
   private final boolean onWindows;

   /** The title of this tree. */
   private final String title;

   /** The instance being displayed. */
   private final A4Solution instance;

   /** Constructs a tree to display the given instance. */
   public VizTree(A4Solution instance, String title, int fontSize) {
      super(fontSize);
      this.instance = instance;
      this.title = title;
      this.onWindows = Util.onWindows();
      alloyStart();
   }
}
