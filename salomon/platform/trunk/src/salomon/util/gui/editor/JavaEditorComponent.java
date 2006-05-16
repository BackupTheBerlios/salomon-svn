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
import javax.swing.text.PlainDocument;

import org.syntax.jedit.JEditTextArea;
import org.syntax.jedit.TextAreaDefaults;
import org.syntax.jedit.tokenmarker.JavaTokenMarker;

public final class JavaEditorComponent extends JPanel
{
    private JEditTextArea _textArea;

    public JavaEditorComponent()
    {
        this(TextAreaDefaults.getDefaults());
    }

    JavaEditorComponent(TextAreaDefaults textAreaDefaults)
    {
        _textArea = new JEditTextArea(textAreaDefaults);
        _textArea.setTokenMarker(new JavaTokenMarker());
        _textArea.getDocument().getDocumentProperties().put(
                PlainDocument.tabSizeAttribute, TAB_SIZE);
        setLayout(new BorderLayout());
        add(_textArea, BorderLayout.CENTER);
    }

    public String getText()
    {
        return _textArea.getText();
    }

    public void setText(String text)
    {
        _textArea.setText(text);
    }

    private static final int TAB_SIZE = 4;
}
