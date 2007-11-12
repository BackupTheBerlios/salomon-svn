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

package salomon.engine.controller.gui.common.spread;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.database.DBManager;
import salomon.platform.exception.DBException;
import salomon.util.gui.Utils;

import com.jgoodies.forms.builder.ButtonBarBuilder;

/**
 * 
 */
public abstract class AbstractManagedSearchSpread extends AbstractSearchSpread
{
    protected AbstractChildForm _childForm;

    private JButton _addButton;

    private ButtonBarBuilder _builder;

    private JPanel _buttonsPanel;

    private JButton _editButton;

    private JButton _removeButton;

    public AbstractManagedSearchSpread(DBManager dbManager)
    {
        super(dbManager);
        _builder = new ButtonBarBuilder();
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
        //        this.setName(getSpreadName());
    }

    public final void refresh()
    {
        // TODO: bug: cannot ensure showing new record
        // when any filters are filled

        // quick fix: pass the id and add OR to the query
        search();
    }

    public void setButtonEnabled(Buttons button, boolean isVisable)
    {
        switch (button) {
            case ADD :
                _addButton.setEnabled(isVisable);
                break;
            case EDIT :
                _editButton.setEnabled(isVisable);
                break;
            case REMOVE :
                _removeButton.setEnabled(isVisable);
                break;
        }
    }

    public void setButtonVisable(Buttons button, boolean isVisable)
    {
        switch (button) {
            case ADD :
                _addButton.setVisible(isVisable);
                break;
            case EDIT :
                _editButton.setVisible(isVisable);
                break;
            case REMOVE :
                _removeButton.setVisible(isVisable);
                break;
        }
    }

    /**
     * @param childForm The childComponent to set.
     */
    public void setChildForm(AbstractChildForm childForm)
    {
        _childForm = childForm;
    }

    protected void addCustomButton(JButton button)
    {
        _builder.addGridded(button);
    }

    private JPanel getButtonsPanel()
    {
        if (_buttonsPanel == null) {
            ButtonsListener listener = new ButtonsListener();
            _addButton = new JButton(Messages.getString("BTN_ADD"));
            _addButton.addActionListener(listener);
            _editButton = new JButton(Messages.getString("BTN_EDIT"));
            _editButton.addActionListener(listener);
            _removeButton = new JButton(Messages.getString("BTN_REMOVE"));
            _removeButton.addActionListener(listener);
            // putting buttons starting from remove button, as the rest is not supported yet
            _builder.addGriddedButtons(new JButton[]{_removeButton, _addButton,
                    _editButton});
            _builder.addGlue();
            _buttonsPanel = _builder.getPanel();
        }
        return _buttonsPanel;
    }

    public enum Buttons {
        ADD, EDIT, REMOVE
    }

    private class ButtonsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            if (_childForm != null) {
                Object source = e.getSource();
                if (source == _addButton) {
                    _childForm.init();
                } else {
                    if (_table != null) {
                        int key = _table.getSelectedKey();
                        if (key >= 0) {
                            try {
                                if (source == _editButton) {
                                    _childForm.edit(key);
                                } else if (source == _removeButton) {
                                    _childForm.remove(key);
                                }
                            } catch (DBException ex) {
                                LOGGER.fatal("", ex);
                                Utils.showErrorMessage(Messages.getString("ERR_DB_ACCESS"));
                            }
                        }
                    }
                }
            }
        }
    }

    private static final Logger LOGGER = Logger.getLogger(AbstractManagedSearchSpread.class);

}
