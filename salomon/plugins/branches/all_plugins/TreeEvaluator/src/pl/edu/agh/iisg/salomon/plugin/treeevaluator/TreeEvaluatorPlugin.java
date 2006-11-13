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

package pl.edu.agh.iisg.salomon.plugin.treeevaluator;

import org.apache.log4j.Logger;

import pl.edu.agh.iisg.salomon.plugin.treeevaluator.result.TreeEvaluatorSettings;
import pl.edu.agh.iisg.salomon.plugin.treeevaluator.result.TreeEvaluatorSettingsComponent;
import pl.edu.agh.iisg.salomon.plugin.treeevaluator.settings.TreeEvaluatorResult;
import pl.edu.agh.iisg.salomon.plugin.treeevaluator.settings.TreeEvaluatorResultComponent;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.data.tree.ITreeNode;

import salomon.plugin.IPlatformUtil;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public final class TreeEvaluatorPlugin implements IPlugin
{

    public IResult doJob(IDataEngine engine, IEnvironment environment,
            ISettings settings)
    {
        TreeEvaluatorResult result = new TreeEvaluatorResult();
        TreeEvaluatorSettings evaluatorSettings = (TreeEvaluatorSettings) settings;
        try {
            IDataSetManager dataSetManager = engine.getDataSetManager();
            String dataSetName = evaluatorSettings.getDataSetName();
            IDataSet dataSet = dataSetManager.getDataSet(dataSetName);
            
            ITreeManager treeManager = engine.getTreeManager();
            String treeName = evaluatorSettings.getTreeName();
            ITree tree = treeManager.getTree(treeName);
            
//            ITreeNode resultTreeNode = tree.evaluate(dataSet);
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return result;
    }

    public IResultComponent getResultComponent()
    {
        return new TreeEvaluatorResultComponent();
    }

    public ISettingComponent getSettingComponent(IPlatformUtil platformUtil)
    {
        return new TreeEvaluatorSettingsComponent();
    }

    private static final Logger LOGGER = Logger.getLogger(TreeEvaluatorPlugin.class);

}
