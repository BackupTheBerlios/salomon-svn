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

package pl.edu.agh.iisg.salomon.plugin.datasetunion;

import java.util.StringTokenizer;

import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleStruct;

/**
 *  
 */
public final class USettings extends SimpleStruct implements ISettings 
{
    public static final String FIRST_DATA_SET = "firstDataSet";
    public static final String SECOND_DATA_SET = "secondDataSet";
    public static final String RESULT_DATA_SET = "resultDataSet";
    
}