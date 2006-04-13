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

package salomon.util.gui.validation;

import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 */
public interface IComponentFactory
{
    /**
     * Creates and returns a formatted text field that is bound 
     * to the Integer value of the given ValueModel.
     * Empty strings are converted to <code>null</code> and vice versa.<p>
     * 
     * @param valueModel  the model that holds the value to be edited
     * @return a formatted text field for Integer instances that is bound
     *     to the specified valueModel
     * 
     * @throws NullPointerException if the valueModel is <code>null</code>
     */    
    JFormattedTextField createIntegerField(String propertyName);
    
    /**
     * Creates and returns a text field linked to given propery in the model.
     * Text changes are commited on focus lost.
     * 
     * @param propertyName  name of the property in the model
     * @return a text area
     * 
     * @throws NullPointerException if property is invalid
     * 
     */
    JTextArea createTextArea(String propertyName);

    /**
     * Creates and returns a text area linked to given propery in the model.
     * 
     * @param propertyName  name of the property in the model
     * @param commitOnFocusLost  true to commit text changes on focus lost,
     *     false to commit text changes on every character typed
     * @return a text area
     * 
     * @throws NullPointerException if property is invalid
     * 
     */
    JTextArea createTextArea(String propertyName, boolean commitOnFocusLost);

    /**
     * Creates and returns a text field linked to given propery in the model.
     * Text changes are commited on focus lost.
     * 
     * @param propertyName  name of the property in the model
     * @return a text field
     * 
     * @throws NullPointerException if property is invalid
     * 
     */
    JTextField createTextField(String propertyName);

    /**
     * Creates and returns a text field linked to given propery in the model.
     * 
     * @param propertyName  name of the property in the model
     * @param commitOnFocusLost  true to commit text changes on focus lost,
     *     false to commit text changes on every character typed
     * @return a text field
     * 
     * @throws NullPointerException if property is invalid
     * 
     */
    JTextField createTextField(String propertyName, boolean commitOnFocusLost);
}
