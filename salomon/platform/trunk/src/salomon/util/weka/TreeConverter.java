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

package salomon.util.weka;

import org.apache.log4j.Logger;

import salomon.platform.data.tree.ITree;
import salomon.platform.exception.PlatformException;

import weka.core.Drawable;

/**
 * 
 */
public final class TreeConverter
{
    public static void convert(ITree outputTree, Drawable wekaTree) throws PlatformException
    {
        if (wekaTree.graphType() != Drawable.TREE) {
            throw new PlatformException("Invalid type of graph! " + wekaTree.graphType());
        }
        try {
            String tree = wekaTree.graph();
        } catch (Exception e) {
            throw new PlatformException("Cannot covert weka tree!", e);
        }
        
        
        
    }

    private static final Logger LOGGER = Logger.getLogger(TreeConverter.class);
}
