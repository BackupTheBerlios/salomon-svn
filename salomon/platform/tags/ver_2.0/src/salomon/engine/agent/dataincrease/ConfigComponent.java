/*
 * Copyright (C) 2007 Salomon Team
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

package salomon.engine.agent.dataincrease;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.agent.IConfigComponent;
import salomon.platform.serialization.IInteger;
import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IStruct;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleStruct;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 */
public class ConfigComponent implements IConfigComponent
{
    private static final Logger LOGGER = Logger.getLogger(ConfigComponent.class);

    private JComponent _configComponent;

    private JTextField _txtTreshold;

    public ConfigComponent()
    {
        initComponents();
        buildPanel();
    }

    /**
     * @see salomon.engine.agent.IConfigComponent#getComponent()
     */
    public Component getComponent()
    {
        return _configComponent;
    }

    /**
     * @see salomon.engine.agent.IConfigComponent#getConfig()
     */
    public IObject getConfig()
    {
        LOGGER.info("ConfigComponent.getConfig()");
        String strConfig = _txtTreshold.getText();
        IStruct config = null;
        if (strConfig != null && strConfig.trim().length() > 0) {
            config = new SimpleStruct();
            config.setField(DataIncreaseAgent.TRESHOLD, new SimpleInteger(
                    Integer.parseInt(strConfig)));
        }
        return config;
    }

    public void update(IStruct object)
    {
        IInteger treshold = (IInteger) object.getField(DataIncreaseAgent.TRESHOLD);
        _txtTreshold.setText("" + treshold.getValue());
    }

    private void buildPanel()
    {
        FormLayout layout = new FormLayout(
                "fill:default:grow, 3dlu, fill:100dlu:grow", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.append("Treshold", _txtTreshold);
        _configComponent = builder.getPanel();
    }

    private void initComponents()
    {
        _txtTreshold = new JTextField();
    }

}
