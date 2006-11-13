/*
 * Copyright (C) 2006 TreeEvaluator Team
 *
 * This file is part of TreeEvaluator.
 *
 * TreeEvaluator is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * TreeEvaluator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with TreeEvaluator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.treeevaluator.settings;

import salomon.util.serialization.SimpleStruct;

import salomon.platform.serialization.IObject;

import salomon.plugin.IResult;

public class TreeEvaluatorResult extends SimpleStruct implements IResult
{

    public void init(IObject object)
    {
        //TODO
    }

    public boolean isSuccessful()
    {
        //FIXME
        return true;
    }

}
