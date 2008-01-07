/*
 * Copyright (C) 2006 TreeVis Team
 *
 * This file is part of TreeVis.
 *
 * TreeVis is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * TreeVis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with TreeVis; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.treevis;

import pl.edu.agh.iisg.salomon.plugin.treevis.result.TreeVisResult;
import pl.edu.agh.iisg.salomon.plugin.treevis.result.TreeVisResultComponent;
import pl.edu.agh.iisg.salomon.plugin.treevis.settings.TreeVisSettings;
import pl.edu.agh.iisg.salomon.plugin.treevis.settings.TreeVisSettingsComponent;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;

import salomon.plugin.IPlatformUtil;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public final class TreeVisPlugin implements IPlugin
{

    public IResult doJob(IDataEngine engine, IEnvironment environment,
            ISettings settings)
    {
        TreeVisSettings visSettings = (TreeVisSettings) settings;
        TreeVisResult result = new TreeVisResult();
        result.setTreeName(visSettings.getTreeName());
        
        return result;
    }

    public IResultComponent getResultComponent()
    {
        return new TreeVisResultComponent();
    }

    public ISettingComponent getSettingComponent(IPlatformUtil platformUtil)
    {
        return new TreeVisSettingsComponent();
    }

}
