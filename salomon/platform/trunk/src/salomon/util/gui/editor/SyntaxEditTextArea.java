/*
 * Copyright (C) 2006 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.util.gui.editor;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.syntax.jedit.JEditTextArea;

public final class SyntaxEditTextArea extends JPanel
{
    private JEditTextArea _editTextArea;

    public SyntaxEditTextArea()
    {
        _editTextArea = new JEditTextArea();
        setLayout(new BorderLayout());
        add(_editTextArea, BorderLayout.CENTER);
    }
    
    public void setText(String text)
    {
        _editTextArea.setText(text);
    }
    
    public String getText()
    {
        return _editTextArea.getText();
    }
    
    public void setEditable(boolean editable)
    {
        _editTextArea.setEditable(editable);
    }
    
    public boolean isEditable()
    {
        return _editTextArea.isEditable();
    }
    
    
}
