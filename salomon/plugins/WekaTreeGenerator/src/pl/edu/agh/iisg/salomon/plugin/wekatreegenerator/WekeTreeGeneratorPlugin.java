/*
 * Copyright (C) 2006 WekaTreeGenerator Team
 *
 * This file is part of WekaTreeGenerator.
 *
 * WekaTreeGenerator is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * WekaTreeGenerator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with WekaTreeGenerator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.wekatreegenerator;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;

import salomon.plugin.IPlatformUtil;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * 
 */
public final class WekeTreeGeneratorPlugin implements IPlugin
{

    public IResult doJob(IDataEngine engine, IEnvironment environment, ISettings settings)
    {
        throw new UnsupportedOperationException("Method WekeTreeGeneratorPlugin.doJob() not implemented yet!");
    }

    public IResultComponent getResultComponent()
    {
        throw new UnsupportedOperationException("Method WekeTreeGeneratorPlugin.getResultComponent() not implemented yet!");
    }

    public ISettingComponent getSettingComponent(IPlatformUtil platformUtil)
    {
        throw new UnsupportedOperationException("Method WekeTreeGeneratorPlugin.getSettingComponent() not implemented yet!");
    }

}
