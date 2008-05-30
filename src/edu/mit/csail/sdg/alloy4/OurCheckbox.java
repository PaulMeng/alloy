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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Graphical checkbox.
 *
 * <p><b>Thread Safety:</b> Can be called only by the AWT event thread.
 */

public abstract class OurCheckbox extends JPanel implements ActionListener {

    /** This silences javac's warning about missing serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The icon to use when the checkbox is off. */
    public static final Icon OFF = OurUtil.loadIcon("images/cb0.gif");

    /** The icon to use when the checkbox is on. */
    public static final Icon ON = OurUtil.loadIcon("images/cb1.gif");

    /** The icon to use when the checkbox is off entirely. */
    public static final Icon ALL_OFF = OurUtil.loadIcon("images/tcb01.gif");

    /** The icon to use when the checkbox is on entirely. */
    public static final Icon ALL_ON = OurUtil.loadIcon("images/tcb02.gif");

    /** The icon to use when the checkbox is off due to inheritance. */
    public static final Icon I_OFF = OurUtil.loadIcon("images/tcb03.gif");

    /** The icon to use when the checkbox is on due to inheritance. */
    public static final Icon I_ON = OurUtil.loadIcon("images/tcb04.gif");

    /** The underlying JCheckBox object. */
    private final JCheckBox box;

    /** The JLabel object for displaying a label next to the checkbox. */
    private final JLabel jlabel;

    /**
     * Constructs a OurCheckbox object.
     * @param label - the label to display next to the checkbox
     * @param tooltip - the tool tip to show when the mouse hovers over this checkbox
     * @param icon - the initial icon to display
     */
    public OurCheckbox(String label, String tooltip, Icon icon) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        box = new JCheckBox(icon);
        box.addActionListener(this);
        box.setMaximumSize(box.getPreferredSize());
        box.setToolTipText(tooltip);
        jlabel = OurUtil.label(OurUtil.getVizFont(), label);
        jlabel.setToolTipText(tooltip);
        if (icon==ON || icon==OFF) { add(box); add(jlabel); } else { add(jlabel); add(box); }
        setAlignmentX(RIGHT_ALIGNMENT);
    }

    /** Changes the icon. */
    public final void setCheckboxIcon(Icon icon) { if (box.getIcon()!=icon) box.setIcon(icon); }

    /** This method is called by Swing to enable/disable a component. */
    @Override public final void setEnabled(boolean enabled) {
        if (box!=null) box.setEnabled(enabled);
        if (jlabel!=null) jlabel.setEnabled(enabled);
    }

    /** This method is called by Swing to change its background color. */
    @Override public final void setBackground(Color color) {
        super.setBackground(color);
        if (box!=null) box.setBackground(color);
        if (jlabel!=null) jlabel.setBackground(color);
    }

    /** This method is called by Swing when the user clicks on the checkbox; subclasses should override this method to provide the custom behavior. */
    public abstract void actionPerformed(ActionEvent e);
}