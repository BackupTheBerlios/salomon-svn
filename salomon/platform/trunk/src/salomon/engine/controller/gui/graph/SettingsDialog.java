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

package salomon.engine.controller.gui.graph;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import salomon.engine.Messages;
import salomon.util.gui.Utils;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationResultViewFactory;

/**
 * 
 */
public final class SettingsDialog
{
    private boolean _approved;

    private JButton _cancelButton;

    private JDialog _dialog;

    private JButton _okButton;

    private ValidationResultModel _resultModel;

    private Component _settingsComponent;

    private Component _validationComponent;

    private ValidationListener _validationListener;

    private JFrame _parent;

    public SettingsDialog(JFrame parent)
    {
        _parent = parent;
        _approved = false;
        _validationListener = new ValidationListener();
        initComponents();
    }

    public void refresh()
    {
        _okButton.setEnabled(!_resultModel.hasErrors());
        _dialog.pack();
    }

    /**
     * Set the value of settingsComponent field.
     * @param settingsComponent The settingsComponent to set
     */
    public void setSettingsComponent(Component settingsComponent)
    {
        _settingsComponent = settingsComponent;
    }

    public void setValidationModel(ValidationResultModel resultModel)
    {
        _resultModel = resultModel;
        if (_resultModel != null) {
            _resultModel.addPropertyChangeListener(_validationListener);
            _validationComponent = ValidationResultViewFactory.createReportIconAndTextPane(resultModel);;
        } else {
            _validationComponent = null;
        }
    }

    public boolean showSettingsDialog()
    {
        _approved = false;
        _dialog.getContentPane().removeAll();
        _dialog.getContentPane().add(getPanel());
        _dialog.pack();
        _dialog.setLocation(Utils.getCenterLocation(_dialog));
        _dialog.setVisible(true);
        return _approved;
    }

    private JPanel getPanel()
    {
        FormLayout layout = new FormLayout(
                "left:default:grow, 10dlu, left:default:grow",
                "fill:default:grow");

        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        if (_validationComponent != null) {
            builder.append(_validationComponent, 3);
        }
        builder.appendSeparator("Plugin settings");
        builder.append(_settingsComponent, 3);

        JPanel buttonPanel = ButtonBarFactory.buildRightAlignedBar(_okButton,
                _cancelButton);
        builder.append(buttonPanel, 3);
        return builder.getPanel();
    }

    private void initComponents()
    {
        _dialog = new JDialog(_parent,
                Messages.getString("TIT_PLUGIN_SETTINGS"));
        _dialog.setResizable(true);
        _dialog.setModal(true);        

        ActionListener listener = new DialogListenter();
        _okButton = new JButton("OK");
        _okButton.addActionListener(listener);
        _cancelButton = new JButton("Cancel");
        _cancelButton.addActionListener(listener);
    }

    private class DialogListenter implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            _dialog.setVisible(false);
            _approved = (e.getSource() == _okButton);
        }
    }

    private class ValidationListener implements PropertyChangeListener
    {
        public void propertyChange(PropertyChangeEvent e)
        {
           refresh();
        }
    }
}
