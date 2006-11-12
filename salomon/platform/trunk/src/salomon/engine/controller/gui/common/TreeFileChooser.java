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

package salomon.engine.controller.gui.common;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import com.jgoodies.forms.factories.ButtonBarFactory;

/**
 * 
 */
public final class TreeFileChooser
{
    private JButton _btnCancel;

    private JButton _btnOK;

    private JTree _fileTree;

    private File _rootDir;

    private JFrame _parentFrame;

    public TreeFileChooser(File rootDir)
    {
        _rootDir = rootDir;
        initComponents();
    }

    private JPanel buildPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(getFileTree()), BorderLayout.CENTER);
        JPanel buttonsPanel = ButtonBarFactory.buildAddRemoveRightBar(_btnOK,
                _btnCancel);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JTree getFileTree()
    {
        // file tree is created in every method call
        // as file list should be refreshed
        // TODO:
        _fileTree = new JTree(new FileSystemModel(_rootDir));
        return _fileTree;
    }

    private void initComponents()
    {
        DialogListener listener = new DialogListener();
        _btnOK = new JButton("OK");
        _btnOK.addActionListener(listener);
        _btnCancel = new JButton("Cancel");
        _btnCancel.addActionListener(listener);

    }

    private JDialog _dialog;

    private JDialog getDialog()
    {
        if (_dialog == null) {
            //            _dialog = new JDialog(_parentFrame, false);
            _dialog = new JDialog(_parentFrame, false);
            _dialog.setTitle("Select plugin files");
            JPanel dialogPanel = buildPanel();
            _dialog.getContentPane().add(dialogPanel);
        }
        return _dialog;
    }

    public void showDialog()
    {
        JDialog dialog = getDialog();
        dialog.pack();
        //        Point point = Utils.getCenterLocation(this);
        //        _dialog.setLocation(point);
        dialog.setVisible(true);
    }

    /**
     * Set the value of parentFrame field.
     * @param parentFrame The parentFrame to set
     */
    public void setParentFrame(JFrame parentFrame)
    {
        _parentFrame = parentFrame;
    }

    private class DialogListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == _btnOK) {
                TreePath[] paths = _fileTree.getSelectionPaths();
                for (TreePath path : paths) {
                    File node = (File) path.getLastPathComponent();
                    System.out.println(node.getName());
                }
            }
            closeDialog();
        }
    }

    private void closeDialog()
    {
        _dialog.setVisible(false);
    }

}