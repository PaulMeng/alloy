/*
 * Alloy Analyzer
 * Copyright (c) 2007 Massachusetts Institute of Technology
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA,
 * 02110-1301, USA
 */

package edu.mit.csail.sdg.alloy4compiler.ast;

import java.util.List;
import edu.mit.csail.sdg.alloy4.ConstList;
import edu.mit.csail.sdg.alloy4.Pos;
import edu.mit.csail.sdg.alloy4.Env;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4.ErrorSyntax;
import edu.mit.csail.sdg.alloy4.ErrorType;
import edu.mit.csail.sdg.alloy4.ConstList.TempList;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig.Field;
import static edu.mit.csail.sdg.alloy4compiler.ast.TypeCheckContext.cset;

/**
 * Immutable; represents a call.
 *
 * <p> <b>Invariant:</b>  all x:args | x.mult==0
 */

public final class ExprCall extends Expr {

    /** The actual function being called; never null. */
    public final Func fun;

    /** The list of arguments to the call. */
    public final ConstList<Expr> args;

    /** Caches the span() result. */
    private Pos span=null;

    /** Returns a Pos object spanning the entire expression. */
    @Override public Pos span() {
        Pos p=span;
        if (p==null) {
            p=pos;
            for(Expr a:args) p=p.merge(a.span());
            span=p;
        }
        return p;
    }

    /** Print a textual description of it and all subnodes to a StringBuilder, with the given level of indentation. */
    @Override public void toString(StringBuilder out, int indent) {
        if (indent<0) {
            out.append(fun.label).append('[');
            for(int i=0; i<args.size(); i++) { if (i>0) out.append(", "); out.append(args.get(i)); }
            out.append(']');
        } else {
            for(int i=0; i<indent; i++) { out.append(' '); }
            out.append("call ").append(fun).append(" with type=").append(type).append('\n');
            for(Expr a:args) { a.toString(out, indent+2); }
        }
    }

    /** This visitor assumes the input expression is already fully typechecked, and derive a tight bound on the return type. */
    private static class DeduceType extends VisitReturn {
        private final Env<ExprVar,Type> env=new Env<ExprVar,Type>();
        private DeduceType() { }
        @Override public Object visit(ExprVar x)     { Type t=env.get(x); return t!=null ? t : x.type; }
        @Override public Object visit(ExprSig x)     { return x.type; }
        @Override public Object visit(ExprField x)   { return x.type; }
        @Override public Object visit(Sig x)         { return x.type; }
        @Override public Object visit(Field x)       { return x.type; }
        @Override public Object visit(ExprAnd x)     { return Type.FORMULA; }
        @Override public Object visit(ExprBuiltin x) { return Type.FORMULA; }
        @Override public Object visit(ExprITE x) throws Err {
            Type t=(Type)x.left.accept(this);
            if (t.size()==0) return t; // This means x.left is either a formula, or an integer expression
            return t.unionWithCommonArity((Type)x.right.accept(this));
        }
        @Override public Object visit(ExprBinary x) throws Err {
            switch(x.op) {
              case GT: case GTE: case LT: case LTE: case IFF: case EQUALS: case IN: case OR: return Type.FORMULA;
            }
            Type a=(Type)(x.left.accept(this));
            Type b=(Type)(x.right.accept(this));
            switch(x.op) {
              case JOIN: return a.join(b);
              case DOMAIN: return b.domainRestrict(a);
              case RANGE: return a.rangeRestrict(b);
              case INTERSECT: return a.intersect(b);
              case PLUSPLUS: return a.unionWithCommonArity(b);
              case PLUS: if (a.is_int) return Type.INT; else return a.unionWithCommonArity(b);
              case MINUS: if (a.is_int) return Type.INT; else return a.pickCommonArity(b);
              default: return a.product(b);
            }
        }
        @Override public Object visit(ExprUnary x) throws Err {
            Type t=(Type)(x.sub.accept(this));
              switch(x.op) {
              case CARDINALITY: case CAST2INT: return Type.INT;
              case CAST2SIGINT: return Sig.SIGINT.type;
              case LONEOF: case ONEOF: case SETOF: case SOMEOF: return t;
              case TRANSPOSE: return t.transpose();
              case CLOSURE: return t.closure();
              case RCLOSURE: return Type.make2(Sig.UNIV);
              default: return Type.FORMULA;
            }
        }
        @Override public Object visit(ExprConstant x) throws Err {
            switch(x.op) {
              case IDEN: return Type.make2(Sig.UNIV);
              case NUMBER: return Type.INT;
              default: return Type.FORMULA;
            }
        }
        @Override public Object visit(ExprQuant x) throws Err {
            if (x.op == ExprQuant.Op.SUM) return Type.INT;
            if (x.op != ExprQuant.Op.COMPREHENSION) return Type.FORMULA;
            Type ans=Type.EMPTY;
            for(ExprVar v:x.vars) {
                Type t=(Type)(v.expr.accept(this));
                env.put(v, t);
                if (ans==Type.EMPTY) ans=t; else ans=ans.product(t);
            }
            for(ExprVar v:x.vars) env.remove(v);
            return ans;
        }
        @Override public Object visit(ExprLet x) throws Err {
            env.put(x.left, (Type)(x.right.accept(this)));
            Object ans=x.sub.accept(this);
            env.remove(x.left);
            return ans;
        }
        @Override public Object visit(ExprCall x) throws Err {
            return x.fun.returnDecl.type;
        }
    }

    /** Constructs an ExprCall node with the given function "pred/fun" and the list of arguments "args". */
    private ExprCall(Pos pos, Type type, Func fun, ConstList<Expr> args, long weight) throws Err {
        super(pos, type, 0, weight);
        this.fun = fun;
        this.args = args;
    }

    /**
     * Constructs an ExprCall node with the given predicate/function "fun" and the list of arguments "args".
     * @throws ErrorSyntax - if the number of arguments does not match the number of parameters
     * @throws ErrorSyntax - if one or more argument is a multiplicity expression
     * @throws ErrorType   - if one or more argument cannot possibly have the correct legal type
     */
    public static Expr make(Pos pos, Func fun, List<Expr> args, long extraWeight) throws Err {
        if (extraWeight<0) extraWeight=0;
        if (args==null) args=ConstList.make();
        if (args.size() != fun.params.size())
            throw new ErrorSyntax(pos, ""+fun+" has "+fun.params.size()+" parameters but is called with "+args.size()+" arguments.");
        final TempList<Expr> newargs=new TempList<Expr>(args.size());
        final Type t0=fun.returnDecl.type;
        Type t=t0;
        for(int i=0; i<args.size(); i++) {
            int a = fun.params.get(i).type.arity();
            Expr x = args.get(i);
            if (x.mult!=0) throw new ErrorSyntax(x.span(), "Multiplicity expression not allowed here.");
            if (x.type==null) {
                t=null;
            } else {
                x=cset(x);
                if (!x.type.hasArity(a)) throw new ErrorType(x.span(), "This should have arity "+a+" but instead its possible type(s) are "+x.type);
            }
            newargs.add(x);
            extraWeight += x.weight;
        }
        if (t!=null && !fun.isPred) {
            try {
                // This provides a limited form of polymorphic function,
                // by using actual arguments at each call site to derive a tighter bound on the return value.
                DeduceType d = new DeduceType();
                for(int i=0; i<args.size(); i++) {
                    ExprVar param=fun.params.get(i);
                    d.env.put(param, newargs.get(i).type.extract(param.type.arity()));
                }
                t = (Type) (fun.returnDecl.accept(d));
                if (t==null || t.is_int || t.is_bool || t.arity()!=t0.arity()) t=t0; // Sanity check
            } catch(Throwable ex) {
                t = t0;
            }
        }
        return new ExprCall(pos, t, fun, newargs.makeConst(), extraWeight);
    }

    /** Typechecks an ExprCall object (first pass). */
    @Override Expr check(final TypeCheckContext cx) throws Err {
        boolean changed=false;
        TempList<Expr> args = new TempList<Expr>(this.args.size());
        long w=0;
        for(int i=0; i<this.args.size(); i++) {
            Expr arg=this.args.get(i);
            Expr res=cset(arg.check(cx));
            w=w+res.weight;
            args.add(res);
            if (arg!=res) changed=true;
            int ar=fun.params.get(i).type.arity();
            if (!res.type.hasArity(ar))
                throw new ErrorType(arg.span(), "This expression should have arity "+ar+" but instead has type "+res.type);
        }
        return changed ? make(pos, fun, args.makeConst(), weight-w) : this;
    }

    /** Typechecks an ExprCall object (second pass). */
    @Override Expr check(final TypeCheckContext cx, Type t) throws Err {
        boolean changed=false;
        TempList<Expr> args = new TempList<Expr>(this.args.size());
        long w=0;
        for(int i=0; i<this.args.size(); i++) {
            Expr arg=this.args.get(i);
            Expr res=cset(arg.check(cx, fun.params.get(i).type)); // Use the function's param type to narrow down choices
            w=w+res.weight;
            args.add(res);
            if (arg!=res) changed=true;
        }
        return changed ? make(pos, fun, args.makeConst(), weight-w) : this;
    }

    /** Accepts the return visitor. */
    @Override Object accept(VisitReturn visitor) throws Err { return visitor.visit(this); }
}