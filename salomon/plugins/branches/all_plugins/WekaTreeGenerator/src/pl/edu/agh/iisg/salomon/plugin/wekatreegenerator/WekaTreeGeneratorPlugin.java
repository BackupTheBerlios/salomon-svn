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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import org.apache.log4j.Logger;
import pl.edu.agh.iisg.salomon.plugin.wekatreegenerator.result.WekaTreeGeneratorResult;
import pl.edu.agh.iisg.salomon.plugin.wekatreegenerator.result.WekaTreeGeneratorResultComponent;
import pl.edu.agh.iisg.salomon.plugin.wekatreegenerator.settings.WekaTreeGeneratorSettings;
import pl.edu.agh.iisg.salomon.plugin.wekatreegenerator.settings.WekaTreeGeneratorSettingsComponent;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.attribute.IAttributeData;
import salomon.platform.data.attribute.IAttributeManager;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeManager;
import salomon.plugin.*;
import salomon.util.weka.TreeConverter;
import weka.classifiers.Classifier;
import weka.core.Drawable;
import weka.core.Instances;

/**
 * Ad3* (strange), Id3?, J48, LMT!, M5P*, NBTree*, RandomTree?, REPTree
 */
public final class WekaTreeGeneratorPlugin implements IPlugin
{

    public IResult doJob(IDataEngine engine, IEnvironment environment,
                         ISettings settings)
    {
        WekaTreeGeneratorResult result = new WekaTreeGeneratorResult();
        try {
            WekaTreeGeneratorSettings treeSettings = (WekaTreeGeneratorSettings) settings;
            String attributeSetName = treeSettings.getAttributeSetName();
            IAttributeManager attributeManager = engine.getAttributeManager();
            IAttributeSet attributeSet = attributeManager.getAttributeSet(attributeSetName);

            String dataSetName = treeSettings.getDataSetName();
            IDataSetManager dataSetManager = engine.getDataSetManager();
            IDataSet dataSet = dataSetManager.getDataSet(dataSetName);

            IAttributeData attributeData = attributeSet.selectAttributeData(dataSet);
            // CORRECT Instances instances = AttributeConverter.toWeka(attributeData);
            /////// FAKE
            Reader reader = new BufferedReader(new FileReader("D:\\home\\iju\\dv\\salomon\\platform\\trunk\\test\\junit\\res\\weka\\labor.arff"));
            Instances instances = new Instances(new Instances(reader));
            instances.setClassIndex(instances.numAttributes() - 1);            
            ///////////

            String algorithmName = treeSettings.getAlgorithmName();
            Classifier classifier = Classifier.forName("weka.classifiers.trees." + algorithmName, null);

            ITreeManager treeManager = engine.getTreeManager();
            String treeName = treeSettings.getTreeName();
            ITree tree = null;//TODO fixme: treeManager.getTree(treeName);
            if (tree == null) {
                tree = treeManager.createTree();
                tree.setName(treeName);
            }
            classifier.buildClassifier(instances);

            TreeConverter.convert(tree, attributeSet, (Drawable) classifier);
            treeManager.add(tree);

            result.setOutput(classifier.toString());
        } catch (Exception e) {
            LOGGER.error("", e);
        }

        return result;
    }

    public IResultComponent getResultComponent()
    {
        return new WekaTreeGeneratorResultComponent();
    }

    public ISettingComponent getSettingComponent(IPlatformUtil platformUtil)
    {
        return new WekaTreeGeneratorSettingsComponent();
    }

    private static final Logger LOGGER = Logger.getLogger(WekaTreeGeneratorPlugin.class);

}
