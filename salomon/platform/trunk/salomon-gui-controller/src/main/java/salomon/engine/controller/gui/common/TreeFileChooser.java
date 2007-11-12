/*
 * Copyright (C) 2006 DirectoryTree Team
 *
 * This file is part of DirectoryTree.
 *
 * DirectoryTree is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * DirectoryTree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with DirectoryTree; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.controller.gui.common;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import salomon.util.gui.Utils;

import com.jgoodies.forms.factories.ButtonBarFactory;

public final class TreeFileChooser
{
    private JButton _btnCancel;

    private JButton _btnOK;

    private JDialog _dialog;

    private FileFilter _fileFilter;

    private JTree _fileTree;

    private boolean _okPressed;

    private File _rootDir;

    private File[] _selectedFiles;

    public TreeFileChooser(File rootDir)
    {
        _rootDir = rootDir;
        _okPressed = false;
        _fileTree = new JTree();
        initComponents();
    }

    /**
     * Returns the selectedFiles.
     * @return The selectedFiles
     */
    public File[] getSelectedFiles()
    {
        return _selectedFiles;
    }

    /**
     * Set the value of fileFilter field.
     * @param fileFilter The fileFilter to set
     */
    public void setFileFilter(FileFilter fileFilter)
    {
        _fileFilter = fileFilter;
    }

    public void setSelectionMode(SelectionMode mode)
    {
        if (_fileTree != null) {
            _fileTree.getSelectionModel().setSelectionMode(mode.getMode());
        }
    }

    public boolean showDialog(JFrame parent)
    {
        JDialog dialog = getDialog(parent);
        dialog.pack();
        Point point = Utils.getCenterLocation(dialog);
        dialog.setLocation(point);
        dialog.setVisible(true);
        return _okPressed;
    }

    private JPanel buildPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane(getFileTree());
        scroll.setBorder(BorderFactory.createLoweredBevelBorder());
        panel.add(scroll, BorderLayout.CENTER);
        JPanel buttonsPanel = ButtonBarFactory.buildAddRemoveRightBar(_btnOK,
                _btnCancel);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void closeDialog()
    {
        _dialog.setVisible(false);
    }

    private JDialog getDialog(JFrame parent)
    {
        if (_dialog == null) {
            _dialog = new JDialog(parent, true);
            _dialog.setTitle("Select plugin files");
            JPanel dialogPanel = buildPanel();
            _dialog.getContentPane().add(dialogPanel);
        }
        return _dialog;
    }

    private JTree getFileTree()
    {
        FileSystemModel model = new FileSystemModel(_rootDir);
        if (_fileFilter != null) {
            model.setFileFilter(_fileFilter);
        }
        _fileTree.setModel(model);
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

    public static enum SelectionMode {

        CONTIGUOUS_SELECTION(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION),

        DISCONTIGUOUS_SELECTION(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION),

        SINGLE_SELECTION(TreeSelectionModel.SINGLE_TREE_SELECTION);

        private int _mode;

        SelectionMode(int mode)
        {
            _mode = mode;
        }

        public int getMode()
        {
            return _mode;
        }
    }

    private class DialogListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (_okPressed = (e.getSource() == _btnOK)) {
                TreePath[] paths = _fileTree.getSelectionPaths();
                _selectedFiles = new File[paths.length];
                for (int i = 0; i < paths.length; ++i) {
                    _selectedFiles[i] = (File) paths[i].getLastPathComponent();
                }
            }
            closeDialog();
        }
    }

}
