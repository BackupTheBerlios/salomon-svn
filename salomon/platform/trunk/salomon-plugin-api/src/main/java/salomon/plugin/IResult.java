/*
 * Copyright (C) 2004 Salomon Team
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

package salomon.plugin;

import java.io.Serializable;

import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IStruct;

/**
 * Represents result of plugin execution.
 *
 */
public interface IResult extends Serializable, IStruct
{
    /**
     * 
     * @return true if plugin successfully finished, false otherwise
     */
    boolean isSuccessful();

    /**
     * Initilizes the result object.
     * @param object
     */
    void init(IObject object);
}