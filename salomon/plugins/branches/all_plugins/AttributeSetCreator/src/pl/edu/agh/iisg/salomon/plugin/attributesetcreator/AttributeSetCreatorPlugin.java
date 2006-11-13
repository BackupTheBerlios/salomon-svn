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

package pl.edu.agh.iisg.salomon.plugin.attributesetcreator;

import org.apache.log4j.Logger;

import pl.edu.agh.iisg.salomon.plugin.attributesetcreator.result.CreatorResult;
import pl.edu.agh.iisg.salomon.plugin.attributesetcreator.result.CreatorResultComponent;
import pl.edu.agh.iisg.salomon.plugin.attributesetcreator.settings.CreatorSettingComponent;
import pl.edu.agh.iisg.salomon.plugin.attributesetcreator.settings.CreatorSettings;
import pl.edu.agh.iisg.salomon.plugin.attributesetcreator.settings.CreatorSettings.Description;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.attribute.IAttributeManager;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlatformUtil;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * @author nico
 */
public final class AttributeSetCreatorPlugin implements IPlugin
{

    private IResultComponent _resultComponent;

    private ISettingComponent _settingComponent;

    /**
     * 
     */
    public IResult doJob(IDataEngine dataEngine, IEnvironment env,
            ISettings settings)
    {
        CreatorSettings cSettings = (CreatorSettings) settings;
        String attributeSetName = cSettings.getAttributeSetName();
        IAttributeManager attributeSetManager = dataEngine.getAttributeManager();

        boolean isSuccessfull = false;

        try {
            Description[] strDesc = cSettings.getDescriptions();
            IAttributeDescription[] descriptions = new IAttributeDescription[strDesc.length];
            int i = 0;
            for (Description desc : strDesc) {
                if (desc != null) {
                    descriptions[i] = attributeSetManager.createAttributeDescription(
                            desc.getAttributeName(), desc.getTableName(),
                            desc.getColumnName(), desc.getType(), desc.getIsOutput().equals("Y"));
                    ++i;
                }
            }
            
            IAttributeSet attributeSet = attributeSetManager.createAttributeSet(descriptions);
            attributeSet.setName(cSettings.getAttributeSetName());
            attributeSetManager.add(attributeSet);

            isSuccessfull = true;
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }

        CreatorResult result = new CreatorResult();
        result.setSuccessful(isSuccessfull);
        if (!isSuccessfull) {
            attributeSetName = "ERROR";
        }
        result.setAttributeSetName(attributeSetName);
        return result;
    }

    /**
     * 
     */
    public IResultComponent getResultComponent()
    {
        if (_resultComponent == null) {
            _resultComponent = new CreatorResultComponent();
        }
        return _resultComponent;
    }

    /**
     * 
     */
    public ISettingComponent getSettingComponent(IPlatformUtil platformUtil)
    {
        if (_settingComponent == null) {
            _settingComponent = new CreatorSettingComponent(platformUtil);
        }
        return _settingComponent;
    }

    private static final Logger LOGGER = Logger.getLogger(AttributeSetCreatorPlugin.class);
}
