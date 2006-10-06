package edu.mit.csail.sdg.alloy4.helper;

/**
 * Immutable; stores the filename and line/column position.
 *
 * <p/> <b>Invariant:</b>  filename!=null && x>0 && y>0
 *
 * @author Felix Chang
 */

public final class Pos {

    /** The filename (it can be an empty string if unknown) */
    public final String filename;

    /** The column position (from 1..) */
    public final int x;

    /** The row position (from 1..) */
    public final int y;

    /**
     * Constructs a new Pos object.
     * @param filename - the filename (it can be an empty string if unknown)
     * @param x - the column position (from 1..)
     * @param y - the row position (from 1..)
     */
    public Pos(String filename, int x, int y) {
        this.filename=(filename==null?"":filename);
        this.x=(x>0?x:1);
        this.y=(y>0?y:1);
    }
}