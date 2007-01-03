package edu.mit.csail.sdg.alloy4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This list allows add() but disallows remove() and set(); null values are allowed.
 *
 * <p>
 * By making this sacrifice, we are able to provide a very cheap "copy constructor"
 * that simulates making a copy without actually making a copy.
 *
 * <p>
 * Furthermore, this class's iterator allows concurrent insertion and iteration
 * (that is, we can iterate over the list while adding elements to the
 * list at the same time).
 * The iterator is guaranteed to iterate over exactly the elements
 * that existed at the time that the iterator was created.
 *
 * <p><b>Thread Safety:</b>  Safe.
 *
 * @param <T> - the type of element
 */

public final class SafeList<T> implements Collection<T> {

    /** The actual list of elements; it will be shared by an original SafeList and all its unmodifiable copies. */
    private final List<T> list;

    /** If negative, that means this instance is mutable; otherwise, it is the list size at the time of the copy. */
    private final int max;

    /** Constructs a modifiable empty list. */
    public SafeList() { list=new ArrayList<T>(); max=(-1); }

    /** Constructs a modifiable empty list with the initial capacity. */
    public SafeList(int initialCapacity) { list=new ArrayList<T>(initialCapacity); max=(-1); }

    /** Constructs a modifiable list containing the elements from the given collection. */
    public SafeList(Collection<? extends T> initialValue) { list=new ArrayList<T>(initialValue); max=(-1); }

    /** Private constructor for assigning exact values to "list" and "max". */
    private SafeList(List<T> list, int max) { this.list=list; this.max=max; }

    /** Constructs an unmodifiable copy of an existing SafeList. */
    public SafeList<T> dup() {
        List<T> mylist;
        int mymax;
        synchronized(SafeList.class) {mylist=list; mymax=size();}
        return new SafeList<T>(mylist,mymax);
    }

    /** Computes a hash code that is consistent with SafeList's equals() and java.util.List's hashCode() methods. */
    @Override public int hashCode() {
        int answer=1, i=0, n=size();
        // size() is synchronized; by reading the size up front, we can then read each element one-by-one
        // while allowing other threads to add to this list. Since existing elements in SafeList will never change,
        // we can safely interleave the read and insertion without putting a grand lock on the entire loop.
        for(Object obj:this) {
            if (i>=n) break;
            i++;
            answer=31*answer;
            if (obj!=null) answer=answer+obj.hashCode();
        }
        return answer;
    }

    /** Returns true if (that instanceof SafeList), and that contains the same elements as this list. */
    @Override public boolean equals(Object that) {
        if (this==that) return true;
        if (!(that instanceof SafeList)) return false;
        SafeList x=(SafeList)that;
        int n; Iterator a,b;
        synchronized(SafeList.class) {
            n=size();
            if (n!=x.size()) return false;
            a=iterator();
            b=x.iterator();
        }
        for(int i=0; i<n; i++) { // We must read up to n elements only
            Object aa=a.next(), bb=b.next();
            if (aa==null) { if (bb!=null) return false; } else { if (!aa.equals(bb)) return false; }
        }
        return true;
    }

    /** Returns true if the list contains the given element. */
    public boolean contains(Object item) {
        for(T entry:this) {
            if (entry==null) { if (item==null) return true; } else { if (entry.equals(item)) return true; }
        }
        return false;
    }

    /** Add an element into the list. */
    public boolean add(T item) {
        if (max>=0) throw new UnsupportedOperationException();
        synchronized(SafeList.class) {list.add(item); return true;}
    }

    /** Get an element from the list. */
    public T get(int i) {
        if (max>=0 && i>=max) throw new IndexOutOfBoundsException();
        synchronized(SafeList.class) {return list.get(i);}
    }

    /** Returns the size of the list. */
    public int size() {
        if (max>=0) return max;
        synchronized(SafeList.class) {return list.size();}
    }

    /** Returns true if the list is empty. */
    public boolean isEmpty() { return size()==0; }

    /**
     * Returns an iterator that iterates over elements in this list
     * (in the order that they were inserted).
     *
     * <p> Note: This iterator's remove() method always throws UnsupportedOperationException.
     *
     * <p> Note: This iterator always returns exactly the list of elements that existed
     * at the time that the iterator was created (even if the list is modified after that point).
     */
    public Iterator<T> iterator() {
        synchronized(SafeList.class) {
            return new Iterator<T>() {
                private final int imax=(max>=0 ? max : list.size());
                private int now=0;
                public final boolean hasNext() { return now<imax; }
                public final T next() {
                    if (now>=imax) throw new NoSuchElementException();
                    synchronized(SafeList.class) { T answer=list.get(now); now++; return answer; }
                }
                public final void remove() { throw new UnsupportedOperationException(); }
            };
        }
    }

    /** Returns an array containing every element from this list. */
    public Object[] toArray() {
        int i=0, n=size();
        // size() is synchronized; by reading the size up front, we can then read each element one-by-one
        // while allowing other threads to add to this list. Since existing elements in SafeList will never change,
        // we can safely interleave the read and insertion without putting a grand lock on the entire loop.
        Object[] answer=new Object[n];
        for(Object obj:this) { if (i>=n) break; answer[i]=obj; i++; }
        return answer;
    }

    /** See java.util.Collection for more information on this method. */
    @SuppressWarnings("unchecked")
    public <S> S[] toArray(S[] answer) {
        int i=0, n=size();
        // size() is synchronized; by reading the size up front, we can then read each element one-by-one
        // while allowing other threads to add to this list. Since existing elements in SafeList will never change,
        // we can safely interleave the read and insertion without putting a grand lock on the entire loop.
        if (answer.length<n) answer=(S[])java.lang.reflect.Array.newInstance(answer.getClass().getComponentType(),n);
        for(Object obj:this) { if (i>=n) break; answer[i]=(S)obj; i++; }
        while(i<n) { answer[i]=null; i++; }
        return answer;
    }

    /** True if this list contains everything from that collection; we return true if that collection is empty. */
    public boolean containsAll(Collection<?> collection) {
        for(Object obj:collection) if (!contains(obj)) return false;
        return true;
    }

    /** Adds every element from that collection. */
    public boolean addAll(Collection<? extends T> collection) {
        boolean changed=false;
        synchronized(SafeList.class) {
            // Since other threads might try to add element to this SafeList also,
            // we must put a lock on the entire loop, so that addAll() completes atomically.
            for(T obj:collection) { add(obj); changed=true; }
        }
        return changed;
    }

    /** This operation is not supported by SafeList. */
    public boolean remove(Object object) { throw new UnsupportedOperationException(); }

    /** This operation is not supported by SafeList. */
    public boolean removeAll(Collection<?> collection) { throw new UnsupportedOperationException(); }

    /** This operation is not supported by SafeList. */
    public boolean retainAll(Collection<?> collection) { throw new UnsupportedOperationException(); }

    /** This operation is not supported by SafeList. */
    public void clear() { throw new UnsupportedOperationException(); }
}