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

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import org.syntax.jedit.JEditTextArea;
import org.syntax.jedit.TextAreaDefaults;

//import salomon.engine.Messages;
//import salomon.engine.platform.IManagerEngine;
import salomon.util.gui.Utils;
import salomon.util.scripting.JavaRunner;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

public final class JavaEditorFrame extends JFrame
{
    private JButton _clearButton;

    private JavaEditorComponent _javaEditorComponent;

    private JavaRunner _javaRunner;

    private JEditTextArea _outputComponent;

    private JButton _runButton;

    //FIXME:
    public JavaEditorFrame(Object managerEngine)//IManagerEngine managerEngine)
    {
        _javaRunner = new JavaRunner();
        _javaRunner.defineVariable("managerEngine", managerEngine);
        setTitle("Java Editor");
        JComponent panel = buildPanel();
        getContentPane().add(panel);
        pack();
        setLocation(Utils.getCenterLocation(this));
        _javaEditorComponent.requestFocus();

        setVisible(true);
    }

    JComponent buildPanel()
    {
        FormLayout layout = new FormLayout("F:D:GROW", "");
        //                "left:max(40dlu;pref), 3dlu, max(70dlu;pref)", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        initComponents();

        // input
        builder.appendSeparator("FIXME:");//Messages.getString("TT_JAVA_EDITOR_INPUT"));
        builder.append(_javaEditorComponent);
        builder.append(ButtonBarFactory.buildCenteredBar(_runButton));
        builder.appendSeparator("FIXME:");//Messages.getString("TT_JAVA_EDITOR_OUTPUT"));
        builder.append(_outputComponent);
        builder.append(ButtonBarFactory.buildCenteredBar(_clearButton));

        return builder.getPanel();
    }

    private void initComponents()
    {
        initInputComponent();
        initOutputComponent();
        _runButton = new JButton("Run");
        _runButton.addActionListener(new ActionListener() {
            @SuppressWarnings("synthetic-access")
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                _javaRunner.run(_javaEditorComponent.getText(),
                        new JEditTextAreaOutputStream(_outputComponent));
            }
        });

        _clearButton = new JButton("Clear");
        _clearButton.addActionListener(new ActionListener() {
            @SuppressWarnings("synthetic-access")
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                _outputComponent.setText("");
            }
        });

    }

    private void initInputComponent()
    {
        TextAreaDefaults textAreaDefaults = TextAreaDefaults.getDefaults();
        textAreaDefaults.rows = 10;
        _javaEditorComponent = new JavaEditorComponent(textAreaDefaults);
    }

    private void initOutputComponent()
    {
        TextAreaDefaults textAreaDefaults = TextAreaDefaults.getDefaults();
        textAreaDefaults.editable = false;
        textAreaDefaults.caretVisible = false;
        textAreaDefaults.caretBlinks = false;
        textAreaDefaults.rows = 10;
        _outputComponent = new JEditTextArea(textAreaDefaults);
    }

}
