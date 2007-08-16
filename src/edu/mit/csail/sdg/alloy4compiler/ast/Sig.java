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

import java.util.Collection;
import edu.mit.csail.sdg.alloy4.Pos;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4.ErrorFatal;
import edu.mit.csail.sdg.alloy4.ErrorType;
import edu.mit.csail.sdg.alloy4.ErrorWarning;
import edu.mit.csail.sdg.alloy4.ErrorSyntax;
import edu.mit.csail.sdg.alloy4.ConstList;
import edu.mit.csail.sdg.alloy4.ConstList.TempList;
import edu.mit.csail.sdg.alloy4.SafeList;
import edu.mit.csail.sdg.alloy4.Util;

/** Mutable; reresents a signature. */

public abstract class Sig extends Expr {

    /** The built-in "univ" signature. */
    public static final PrimSig UNIV = new PrimSig("univ", null, false);

    /** The built-in "Int" signature. */
    public static final PrimSig SIGINT = new PrimSig("Int", UNIV, false);

    /** The built-in "seq/Int" signature. */
    public static final PrimSig SEQIDX = new PrimSig("seq/Int", SIGINT, true);

    /** The built-in "none" signature. */
    public static final PrimSig NONE = new PrimSig("none", null, false);

    /** Returns the name for this sig; this name need not be unique. */
    @Override public final String toString() { return label; }

    /** {@inheritDoc} */
    @Override public final void toString(StringBuilder out, int indent) {
        if (indent<0) {
            out.append(label);
        } else {
            for(int i=0; i<indent; i++) { out.append(' '); }
            out.append("sig ").append(label).append(" with type=").append(type).append('\n');
        }
    }

    /** {@inheritDoc} */
    @Override public final Pos span() { return pos; }

    /** {@inheritDoc} */
    @Override public Expr resolve(Type t, Collection<ErrorWarning> warns) { return this; }

    /** {@inheritDoc} */
    @Override final Object accept(VisitReturn visitor) throws Err { return visitor.visit(this); }

    /**
     * True if this sig is one of the built-in sig.
     * <p> Note: if builtin==true, then we ensure it is not abstract, and it is not ordered
     */
    public final boolean builtin;

    /**
     * Nonnull if this sig is abstract.
     * <p> Note: if a sig is abstract, then it cannot and will not be a subset sig.
     */
    public final Pos isAbstract;

    /**
     * Nonnull if this sig is a subset sig.
     * <p> Note: if a sig is a subset sig, then it cannot and will not be abstract.
     */
    public final Pos isSubset;

    /**
     * Nonnull if this sig is ordered.
     */
    public final Pos isOrdered;

    /**
     * Nonnull if this sig's multiplicity is declared to be lone.
     * <p> Note: at most one of "lone", "one", "some" can be nonnull for each sig.
     */
    public final Pos isLone;

    /**
     * Nonnull if this sig's multiplicity is declared to be one.
     * <p> Note: at most one of "lone", "one", "some" can be nonnull for each sig.
     */
    public final Pos isOne;

    /**
     * Nonnull if this sig's multiplicity is declared to be some.
     * <p> Note: at most one of "lone", "one", "some" can be nonnull for each sig.
     */
    public final Pos isSome;

    /** The label for this sig; this name does not need to be unique. */
    public final String label;

    /** Returns true if this sig is a toplevel sig (meaning: it is UNIV, or it is a non-subset sig with parent==UNIV) */
    public final boolean isTopLevel() {
        return (this!=NONE) && (this instanceof PrimSig) && (this==UNIV || ((PrimSig)this).parent==UNIV);
    }

    /** Constructs a new builtin PrimSig. */
    private Sig(String label) {
        super(Pos.UNKNOWN, null);
        this.builtin=true;
        this.isAbstract=null;
        this.isLone=null;
        this.isOne=null;
        this.isSome=null;
        this.label=label;
        this.isSubset=null;
        this.isOrdered=null;
    }

    /** Constructs a new PrimSig or SubsetSig. */
    private Sig(Pos pos, Type type, String label, Pos abs, Pos lone, Pos one, Pos some, Pos subset, Pos isOrdered) throws Err {
        super(pos, type);
        if (lone!=null && one!=null)  throw new ErrorSyntax(lone.merge(one),  "You cannot delcare a sig to be both lone and one.");
        if (lone!=null && some!=null) throw new ErrorSyntax(lone.merge(some), "You cannot delcare a sig to be both lone and some.");
        if (one!=null  && some!=null) throw new ErrorSyntax(one.merge(some),  "You cannot delcare a sig to be both one and some.");
        this.builtin=false;
        this.isOrdered=isOrdered;
        this.isAbstract=abs;
        this.isLone=lone;
        this.isOne=one;
        this.isSome=some;
        this.label=label;
        this.isSubset=subset;
    }

    //==============================================================================================================//

    /**
     * Mutable; reresents a non-subset signature.
     *
     * <p> Note: except for "children()", the return value of every method is always valid for all time;
     * for example, given sigs A and B, and you call C=A.intersect(B), then the result C will always be
     * the intersection of A and B even if the caller later constructs more sigs or subsigs or subsetsigs...
     */

    public static final class PrimSig extends Sig {

        /**
         * Stores its immediate children sigs (not including NONE)
         * <p> Note: if this==UNIV, then this list will always be empty, since we don't keep track of UNIV's children
         */
        private final SafeList<PrimSig> children = new SafeList<PrimSig>();

        /**
         * Returns its immediate children sigs (not including NONE)
         * <p> Note: if this==UNIV, then this method will throw an exception, since we don't keep track of UNIV's children
         */
        public SafeList<PrimSig> children() throws Err {
            if (this==UNIV) throw new ErrorFatal("Internal error (cannot enumerate the subsigs of UNIV)");
            return children.dup();
        }

        /** True if all subsigs will get this sig's type. */
        final boolean hint_isLeaf;

        /** If this is UNIV or NONE, then this field is null, else this field is the parent sig. */
        public final PrimSig parent;

        /** Constructs a builtin PrimSig. */
        private PrimSig(String label, PrimSig parent, boolean add) {
            super(label);
            this.hint_isLeaf=false;
            this.parent=parent;
            if (add) this.parent.children.add(this);
        }

        /**
         * Constructs a non-builtin sig.
         *
         * @param pos - the position in the original file where this sig was defined (can be null if unknown)
         * @param parent - the parent (must not be null, and must not be NONE)
         * @param label - the name of this sig (it does not need to be unique)
         * @param isAbstract - nonnull iff this sig should be abstract
         * @param lone - nonnull iff this sig has the "lone" multiplicity
         * @param one - nonnull iff this sig has the "one" multiplicity
         * @param some - nonnull iff this sig has the "some" multiplicity
         * @param isLeaf - true if all its future subsigs shall have the same "type" as this sig
         *
         * @throws ErrorSyntax if the signature has two or more multiplicities
         * @throws ErrorType if you attempt to extend the builtin sigs NONE, SIGINT, or SEQIDX
         */
        public PrimSig(Pos pos, PrimSig parent, String label, Pos isAbstract, Pos lone, Pos one, Pos some, Pos ordered, boolean isLeaf) throws Err {
            super(pos, (parent!=null && parent.hint_isLeaf) ? parent.type : null, label, isAbstract, lone, one, some, null, ordered);
            if (parent==SIGINT) throw new ErrorSyntax(pos, "sig "+label+" cannot extend the builtin \"Int\" signature");
            if (parent==SEQIDX) throw new ErrorSyntax(pos, "sig "+label+" cannot extend the builtin \"seq/Int\" signature");
            if (parent==NONE)   throw new ErrorSyntax(pos, "sig "+label+" cannot extend the builtin \"none\" signature");
            if (parent==null) parent=UNIV; else if (parent!=UNIV) parent.children.add(this);
            this.parent = parent;
            this.hint_isLeaf = isLeaf || (parent.hint_isLeaf);
        }

        /**
         * Constructs a non-builtin sig.
         *
         * @param pos - the position in the original file where this sig was defined (can be null if unknown)
         * @param parent - the parent (must not be null, and must not be NONE)
         * @param label - the name of this sig (it does not need to be unique)
         * @param isAbstract - true iff this sig should be abstract
         * @param lone - true iff this sig has the "lone" multiplicity
         * @param one - true iff this sig has the "one" multiplicity
         * @param some - true iff this sig has the "some" multiplicity
         * @param isLeaf - true if all its future subsigs shall have the same "type" as this sig
         *
         * @throws ErrorSyntax if the signature has two or more multiplicities
         * @throws ErrorType if you attempt to extend the builtin sigs NONE, SIGINT, or SEQIDX
         */
        public PrimSig(Pos pos, PrimSig parent, String label, boolean isAbstract, boolean lone, boolean one, boolean some, boolean isLeaf) throws Err {
            this(pos, parent, label, isAbstract?Pos.UNKNOWN:null, lone?Pos.UNKNOWN:null, one?Pos.UNKNOWN:null, some?Pos.UNKNOWN:null, null, isLeaf);
        }

        /**
         * Returns true iff "this is equal or subtype of that"
         */
        public boolean isSubtypeOf(PrimSig that) {
            if (this==NONE || this==that || that==UNIV) return true;
            if (this==UNIV || that==NONE) return false;
            for(PrimSig me=this; me!=null; me=me.parent) if (me==that) return true;
            return false;
        }

        /**
         * Returns the intersection between this and that (and returns "none" if they do not intersect).
         */
        public PrimSig intersect(PrimSig that) {
            if (this.isSubtypeOf(that)) return this;
            if (that.isSubtypeOf(this)) return that;
            return NONE;
        }

        /**
         * Returns true iff the intersection between this and that is not "none".
         */
        public boolean intersects(PrimSig that) {
            if (this.isSubtypeOf(that)) return this!=NONE;
            if (that.isSubtypeOf(this)) return that!=NONE;
            return false;
        }

        /**
         * Returns the most-specific-sig that contains this and that.
         * In particular, if this extends that, then return that.
         */
        public PrimSig leastParent(PrimSig that) {
            if (isSubtypeOf(that)) return that;
            PrimSig me=this;
            while(true) {
                if (that.isSubtypeOf(me)) return me;
                me=me.parent;
                if (me==null) return UNIV;
            }
        }
    }

    //==============================================================================================================//

    /** Mutable; reresents a subset signature. */

    public static final class SubsetSig extends Sig {

        /** The list of Sig that it is a subset of; this list is never empty. */
        public final ConstList<Sig> parents;

        /** Computes the type for this sig. */
        private static Type getType(Pos pos, String label, Iterable<Sig> parents) throws Err {
            Type ans=null;
            if (parents!=null) {
                for(Sig parent: parents) {
                    if (parent==UNIV)   throw new ErrorSyntax(pos, "sig "+label+" is already implicitly a subset of the builtin \"univ\" signature");
                    if (parent==SIGINT) throw new ErrorSyntax(pos, "sig "+label+" cannot be a subset of the builtin \"Int\" signature");
                    if (parent==SEQIDX) throw new ErrorSyntax(pos, "sig "+label+" cannot be a subset of the builtin \"seq/Int\" signature");
                    if (parent==NONE)   throw new ErrorSyntax(pos, "sig "+label+" cannot be a subset of the builtin \"none\" signature");
                    if (ans==null) ans=parent.type; else ans=ans.unionWithCommonArity(parent.type);
                }
            }
            return (ans!=null) ? ans : (UNIV.type);
        }

        /**
         * Constructs a subset sig.
         *
         * @param pos - the position in the original file where this sig was defined (can be null if unknown)
         * @param parents - the list of parents (if this list is null or empty, we assume the caller means UNIV)
         * @param label - the name of this sig (it does not need to be unique)
         * @param subsetPosition - if nonnull, it denotes the location of the "subset" token in the input file
         * @param lone - nonnull iff this sig has the "lone" multiplicity
         * @param one - nonnull iff this sig has the "one" multiplicity
         * @param some - nonnull iff this sig has the "some" multiplicity
         *
         * @throws ErrorSyntax if the signature has two or more multiplicities
         * @throws ErrorType if parents contains NONE or SIGINT or SEQIDX
         */
        public SubsetSig(Pos pos, Collection<Sig> parents, String label, Pos subsetPosition, Pos lone, Pos one, Pos some, Pos ordered) throws Err {
            super(pos, getType(pos,label,parents), label, null, lone, one, some, Pos.UNKNOWN.merge(subsetPosition), ordered);
            TempList<Sig> temp = new TempList<Sig>(parents==null ? 1 : parents.size());
            if (parents!=null) for(Sig parent:parents) if (!temp.contains(parent)) temp.add(parent);
            if (temp.size()==0) temp.add(UNIV);
            this.parents = temp.makeConst();
        }
    }

    //==============================================================================================================//

    /** Immutable; represents a field. */

    public static final class Field extends Expr {

        /** The sig that this field belongs to; never null. */
        public final Sig sig;

        /** The label for this field; this name does not need to be unique. */
        public final String label;

        /** The bounding formula; it is always of the form "all x: one ThisSig | x.ThisField in y" */
        public final Expr boundingFormula;

        /** Constructs a new Field object. */
        private Field(Pos pos, Sig sig, String label, ExprVar var, Expr bound) throws Err {
            super(pos, null, false, sig.type.product(bound.type), 0, 0, null);
            if (sig.builtin) throw new ErrorSyntax("Builtin sig \""+sig+"\" cannot have fields.");
            if (!bound.errors.isEmpty())
                throw bound.errors.get(0);
            if (bound.hasCall())
                throw new ErrorSyntax(pos, "Field \""+label+"\" declaration cannot contain a function or predicate call.");
            this.sig=sig;
            this.label=label;
            if (var==null) var = sig.oneOf("this");
            // If the field declaration is unary, and does not have any multiplicity symbol, we assume it's "one of"
            if (bound.mult==0 && bound.type.arity()==1) bound=ExprUnary.Op.ONEOF.make(null, bound);
            boundingFormula=ExprQuant.Op.ALL.make(pos, null, Util.asList(var), var.join(this).in(bound));
        }

        /** Returns a human-readable description of this field's name. */
        @Override public String toString() { return "field ("+sig+" <: "+label+")"; }

        /** {@inheritDoc} */
        @Override public void toString(StringBuilder out, int indent) {
            if (indent<0) {
                out.append(label);
            } else {
                for(int i=0; i<indent; i++) { out.append(' '); }
                out.append("field ").append(sig).append(" <: ").append(label).append(" with type=").append(type).append('\n');
            }
        }

        /** {@inheritDoc} */
        @Override public Pos span() { return pos; }

        /** {@inheritDoc} */
        @Override public Expr resolve(Type t, Collection<ErrorWarning> warns) { return this; }

        /** {@inheritDoc} */
        @Override Object accept(VisitReturn visitor) throws Err { return visitor.visit(this); }
    }

    //==============================================================================================================//

    /** The list of fields. */
    private final SafeList<Field> fields = new SafeList<Field>();

    /** Returns the list of fields (as an unmodifiable list). */
    public final SafeList<Field> getFields() { return fields.dup(); }

    //TODO
    private Sig orderingTarget=null;
    public final void addOrderfields(Sig orderingTarget) throws Err {
        if (this.orderingTarget==orderingTarget) return;
        if (this.orderingTarget!=null) throw new ErrorFatal("Internal error (Same Ord is used on two different sigs)");
        this.orderingTarget=orderingTarget;
    }
    public final Sig getOrderingTarget() { return orderingTarget; }

    /**
     * Add then return a new field F, where "all x: ThisSig | x.F in bound"
     * <p> Note: the bound must be fully-typechecked and have exactly 0 free variables.
     *
     * @param pos - the position in the original file where this field was defined (can be null if unknown)
     * @param label - the name of this field (it does not need to be unique)
     * @param bound - the new field will be bound by "all x: one ThisSig | x.ThisField in y"
     *
     * @throws ErrorSyntax  if the sig is one of the builtin sig
     * @throws ErrorSyntax  if the bound contains a predicate/function call
     * @throws ErrorType    if the bound is not fully typechecked or is not a set/relation
     */
    public final Field addField(Pos pos, String label, Expr bound) throws Err {
        return addTrickyField(pos, label, null, bound);
    }

    /**
     * Add then return a new field, where "all x: ThisSig | x.F in bound"
     * <p> Note: the bound must be fully-typechecked and have exactly 0 free variable, or have "x" as its sole free variable.
     *
     * @param pos - the position in the original file where this field was defined (can be null if unknown)
     * @param label - the name of this field (it does not need to be unique)
     * @param x - a quantified variable "x: one ThisSig"
     * @param bound - the new field will be bound by "all x: one ThisSig | x.ThisField in bound"
     *
     * @throws ErrorSyntax  if the sig is one of the builtin sig
     * @throws ErrorSyntax  if the bound contains a predicate/function call
     * @throws ErrorType    if the bound is not fully typechecked or is not a set/relation
     */
    public final Field addTrickyField(Pos pos, String label, ExprVar x, Expr bound) throws Err {
        bound=bound.typecheck_as_set();
        if (bound.ambiguous) bound=bound.resolve_as_set(Expr.sink);
        final Field f=new Field(pos, this, label, x, bound);
        fields.add(f);
        return f;
    }
}
