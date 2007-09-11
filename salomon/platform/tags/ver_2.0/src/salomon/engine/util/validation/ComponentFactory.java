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

package salomon.engine.util.validation;

import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import salomon.util.gui.validation.IComponentFactory;

import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * 
 */
public final class ComponentFactory implements IComponentFactory
{
    private ValidationModel _validationModel;

    /**
     * @param validationModel
     */
    public ComponentFactory(ValidationModel validationModel)
    {
        _validationModel = validationModel;
    }

    /**
     * @see salomon.engine.util.validation.IComponentFactoryEx#createIntegerField(java.lang.String)
     */
    public JFormattedTextField createIntegerField(String propertyName)
    {
        return BasicComponentFactory.createIntegerField(_validationModel.getModel(propertyName));
    }

    /**
     * @see salomon.util.gui.validation.IComponentFactory#createTextArea(java.lang.String)
     */
    public JTextArea createTextArea(String propertyName)
    {
        return createTextArea(propertyName, true);
    }

    /**
     * @see salomon.util.gui.validation.IComponentFactory#createTextArea(java.lang.String, boolean)
     */
    public JTextArea createTextArea(String propertyName,
            boolean commitOnFocusLost)
    {
        return BasicComponentFactory.createTextArea(
                _validationModel.getModel(propertyName), commitOnFocusLost);
    }

    /**
     * @see salomon.util.gui.validation.IComponentFactory#createTextField(java.lang.String)
     */
    public JTextField createTextField(String propertyName)
    {
        return createTextField(propertyName, true);
    }

    /**
     * @see salomon.util.gui.validation.IComponentFactory#createTextField(java.lang.String, boolean)
     */
    public JTextField createTextField(String propertyName,
            boolean commitOnFocusLost)
    {
        return BasicComponentFactory.createTextField(
                _validationModel.getModel(propertyName), commitOnFocusLost);
    }

}
