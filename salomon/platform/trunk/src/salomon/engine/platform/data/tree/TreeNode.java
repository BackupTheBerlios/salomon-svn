/*
 * Copyright (C) 2005 Salomon Team
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

package salomon.engine.platform.data.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.tree.ITreeEdge;
import salomon.platform.data.tree.ITreeNode;

/**
 * Implementacja elementu drzewa
 * 
 * @author Mateusz Nowakowski
 *
 */
public final class TreeNode implements ITreeNode
{
    private int id;

    private Type type;

    private String value;

    private String parentEdge;

    private ITreeNode parent;

    private ITreeNode[] children;

    /**
     * Konstruktor dodatkowo ustawiajacy identyfikator
     */
    public TreeNode(int id, ITreeNode parent, String edge, Type type, String value)
    {
        this(parent, edge, type, value);
        this.id = id;
    }

    /**
     *
     * Konstruktor elementu drzewa. W przypadku gdy lement drzewa jest korzeniem drzewa  - w miejsce parent nalezy umie�ci� null.
     */
    public TreeNode(ITreeNode parent, String edge, Type type, String value)
    {
        this.type = type;
        this.value = value;
        this.children = new ITreeNode[0];
        if (parent != null) {
            this.parentEdge = edge;
            this.setParent(parent);
        }
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#setParent(salomon.platform.data.tree.INode)
     */
    public void setParent(ITreeNode parent)
    {
        this.parent = parent;
        for (ITreeNode node : parent.getChildren())
            if (node.equals(this))
                return;
        parent.addChild(this);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#addChild(salomon.platform.data.tree.INode)
     */
    public void addChild(ITreeNode child)
    {
        List<ITreeNode> childs = new ArrayList<ITreeNode>(Arrays.asList(children));
        childs.add(child);
        this.children = childs.toArray(new ITreeNode[childs.size()]);
        child.setParent(this);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#addChildren(salomon.platform.data.tree.INode[])
     */
    public void addChildren(ITreeNode[] ch)
    {
        List<ITreeNode> childs = new ArrayList<ITreeNode>(Arrays.asList(children));
        childs.addAll(Arrays.asList(ch));
        for (ITreeNode child : ch) {
            child.setParent(this);
        }
        this.children = childs.toArray(new ITreeNode[childs.size()]);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#setChildren(salomon.platform.data.tree.INode[])
     */
    public void setChildren(ITreeNode[] children)
    {
        this.children = children;
        for (ITreeNode child : children) {
            child.setParent(this);
        }
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#getLeafs()
     */
    public ITreeNode[] getLeafs()
    {
        if (this.isLeaf())
            return new ITreeNode[]{this};
        List<ITreeNode> leafs = new ArrayList<ITreeNode>();
        for (ITreeNode child : children)
            leafs.addAll(Arrays.asList(child.getLeafs()));
        return leafs.toArray(new ITreeNode[leafs.size()]);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#getRoot()
     */
    public ITreeNode getRoot()
    {
        if (this.isRoot())
            return this;
        else
            return getParent().getRoot();
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#isLeaf()
     */
    public boolean isLeaf()
    {
        return ((children == null) || (children.length == 0));
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#isRoot()
     */
    public boolean isRoot()
    {
        return (parent == null);
    }

    /**
     * @return Returns the children.
     */
    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#getChildren()
     */
    public ITreeNode[] getChildren()
    {
        return children;
    }

    /**
     * @return Returns the parent.
     */
    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#getParent()
     */
    public ITreeNode getParent()
    {
        return parent;
    }

//    /**
//     * @return Returns the parentEdge.
//     */
//    /* (non-Javadoc)
//     * @see salomon.platform.data.tree.INode#getParentEdge()
//     */
//    public String getParentEdge()
//    {
//        return parentEdge;
//    }

    /**
     * @param parentEdge The parentEdge to set.
     */
    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#setParentEdge(java.lang.String)
     */
    public void setParentEdge(String parentEdge)
    {
        this.parentEdge = parentEdge;
    }

    /**
     * @return Returns the type.
     */
    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#getType()
     */
    public Type getType()
    {
        return type;
    }

    /**
     * @param type The type to set.
     */
    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#setType(salomon.platform.data.tree.INode.Type)
     */
    public void setType(Type type)
    {
        this.type = type;
    }

    /**
     * @return Returns the value.
     */
    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#getValue()
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param value The value to set.
     */
    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#setValue(java.lang.String)
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * @return Returns the id.
     */
    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#getId()
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    /* (non-Javadoc)
     * @see salomon.platform.data.tree.INode#setId(int)
     */
    public void setId(int id)
    {
        this.id = id;
    }

    public IAttributeDescription getAttributeDescription()
    {
        throw new UnsupportedOperationException("Method Node.getAttributeDescription() not implemented yet!");
    }

    public ITreeEdge[] getChildrenEdges()
    {
        throw new UnsupportedOperationException("Method Node.getChildrenEdges() not implemented yet!");
    }

    public ITreeEdge getParentEdge()
    {
        throw new UnsupportedOperationException("Method Node.getParentEdge() not implemented yet!");
    }

    public IAttributeDescription setAttributeDescription()
    {
        throw new UnsupportedOperationException("Method Node.setAttributeDescription() not implemented yet!");
    }

}