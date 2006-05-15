/*
 * Copyright (C) 2005 Salomon Team
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

package salomon.engine.controller.gui.statusbar;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.Messages;

/**
 * 
 */
public class StatusBar extends JPanel
{
    private static final Logger LOGGER = Logger.getLogger(StatusBar.class);

    private Map<String, JTextField> _items;

    StatusBar()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        _items = new HashMap<String, JTextField>();
    }

    void addItem(String name)
    {
        JTextField txtItem = new JTextField();
        txtItem.setEditable(false);
        txtItem.setBorder(BorderFactory.createLoweredBevelBorder());
        txtItem.setToolTipText(Messages.getString(name));

        Dimension dim = new Dimension(100, 20);
        txtItem.setMinimumSize(dim);
        txtItem.setMaximumSize(dim);
        txtItem.setPreferredSize(dim);

        _items.put(name, txtItem);
        this.add(txtItem);
    }

    void setItem(String name, String value)
    {
        JTextField txtField = _items.get(name);
        if (txtField != null) {
            txtField.setText(value);
        } else {
            LOGGER.error("Invalid object key in status bar: " + name);
        }
    }
}
