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

import java.io.File;
import java.io.FileFilter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Class implements TreeModel.
 * It allows displaying files in a tree.
 *
 */
public class FileSystemModel implements TreeModel
{
    private FileFilter _fileFilter;

    private FileTreeNode _root;

    private Map<FileTreeNode, List<FileTreeNode>> _sortedChildren;

    /**
     * Create a tree model using the specified file as root.
     * 
     * @param root  Root file (directory typically).
     */
    public FileSystemModel(File root)
    {
        _root = new FileTreeNode(root);
        _sortedChildren = new TreeMap<FileTreeNode, List<FileTreeNode>>();
    }

    public FileSystemModel(File root, FileFilter fileFilter)
    {
        this(root);
        _fileFilter = fileFilter;
    }

    public void addTreeModelListener(TreeModelListener listener)
    {
        // empty body
    }

    public Object getChild(Object parent, int index)
    {
        List children = (List) _sortedChildren.get(parent);
        return children == null ? null : children.get(index);
    }

    public int getChildCount(Object parent)
    {
        FileTreeNode file = (FileTreeNode) parent;
        int childCount = 0;

        if (file.isDirectory()) {
            File[] children = (_fileFilter == null)
                    ? file.listFiles()
                    : file.listFiles(_fileFilter);
            if (children != null && children.length > 0) {
                List<FileTreeNode> childrenList = new ArrayList<FileTreeNode>(
                        children.length);
                for (File child : children) {
                    childrenList.add(new FileTreeNode(child));
                }

                Collections.sort(childrenList);
                _sortedChildren.put(file, childrenList);
                childCount = childrenList.size();
            }
        }
        return childCount;
    }

    public int getIndexOfChild(Object parent, Object child)
    {
        List children = (List) _sortedChildren.get(parent);
        return children.indexOf(child);
    }

    public Object getRoot()
    {
        return _root;
    }

    public boolean isLeaf(Object node)
    {
        return ((File) node).isFile();
    }

    public void removeTreeModelListener(TreeModelListener listener)
    {
        // empty body
    }

    /**
     * Set the value of fileFilter field.
     * @param fileFilter The fileFilter to set
     */
    public final void setFileFilter(FileFilter fileFilter)
    {
        _fileFilter = fileFilter;
    }

    public void valueForPathChanged(TreePath path, Object newValue)
    {
        // empty body
    }

    /**
     * Extension to the java.io.File object but with more
     * appropriate compare rules
     * 
     */
    private class FileTreeNode extends File
    {
        public FileTreeNode(File file)
        {
            super(file, "");
        }

        /**
         * Compare two FileTreeNode objects so that directories
         * are sorted first.
         * 
         * @param object  Object to compare to.
         * @return        Compare identifier.
         */
        @Override
        public int compareTo(File otherFile)
        {
            Collator collator = Collator.getInstance();

            if (this.isDirectory() && otherFile.isFile())
                return -1;
            else if (this.isFile() && otherFile.isDirectory())
                return 1;
            else
                return collator.compare(this.getName(), otherFile.getName());
        }

        /**
         * Retur a string representation of this node.
         * The inherited toString() method returns the entire path.
         * For use in a tree structure, the name is more appropriate.
         * 
         * @return  String representation of this node.
         */
        public String toString()
        {
            return getName();
        }
    }

}
