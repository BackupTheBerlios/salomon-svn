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

package pl.edu.agh.iisg.salomon.plugin.datasetcreator.settings;

import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IStruct;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleStruct;

/**
 * @author nico
 */
public final class CreatorSettings extends SimpleStruct implements ISettings
{

	public static final String DATA_SET_NAME = "dataSetName";

	public static final String CONDITIONS = "conditions";

	// work around

	public static final String ELEM1 = "elem1";

	public static final String ELEM2 = "elem2";

	public static final String ELEM3 = "elem3";

	public static final String ELEM4 = "elem4";

	public static final String ELEM5 = "elem5";

	public void init(IObject o)
	{
		IStruct struct = (IStruct) o;
		for (String field : struct.getFieldNames()) {
			this.setField(field, struct.getField(field));
		}
	}
}
