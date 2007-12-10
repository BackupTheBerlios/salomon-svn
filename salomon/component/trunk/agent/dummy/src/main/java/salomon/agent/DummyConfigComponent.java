/*
 * Copyright (C) 2007 agent-dummy Team
 *
 * This file is part of agent-dummy.
 *
 * agent-dummy is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * agent-dummy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with agent-dummy; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.agent;

import java.awt.Component;

import javax.swing.JTextField;

import salomon.platform.serialization.IObject;
import salomon.util.serialization.SimpleString;

/**
 * 
 */
public class DummyConfigComponent implements IConfigComponent
{
    private JTextField _textField;

    public DummyConfigComponent()
    {
        _textField = new JTextField();
    }

    /**
     * @see salomon.agent.IConfigComponent#getComponent()
     */
    public Component getComponent()
    {
        return _textField;
    }

    /**
     * @see salomon.agent.IConfigComponent#getConfig()
     */
    public IObject getConfig()
    {
        SimpleString string = new SimpleString();
        string.setValue(_textField.getText());
        return string;
    }

}
