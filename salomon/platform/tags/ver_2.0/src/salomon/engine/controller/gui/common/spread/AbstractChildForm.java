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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
public abstract class AbstractChildForm
{
    protected DBManager _manager;

    private JPanel _buttonsPanel;

    private JPanel _controlPanel;

    protected int _currentKey;

    private JDialog _dialog;

    private Modes _mode;

    private JButton _okButton;

    protected JFrame _parentFrame;

    private AbstractManagedSearchSpread _spread;

    public AbstractChildForm(AbstractManagedSearchSpread spread,
            DBManager manager)
    {
        _manager = manager;
        _currentKey = 0;
        _mode = Modes.UNKNOWN;
        _spread = spread;
        // getPanel is not implemented        
        // _controlPanel = getPanel();
    }

    public final void edit(int key) throws DBException
    {
        LOGGER.info("AbstractChildForm.edit()");
        _currentKey = key;
        _mode = Modes.EDIT;
        loadData(key);
        showDialog();
        refreshControls();
    }

    public abstract JPanel getPanel();

    public final void init()
    {
        LOGGER.info("AbstractChildForm.init()");
        _currentKey = 0;
        _mode = Modes.INIT;
        initData();
        showDialog();
    }

    public abstract void initData();

    public abstract void loadData(int key) throws DBException;

    public final void remove(int key) throws DBException
    {
        LOGGER.info("AbstractChildForm.remove()");
        _mode = Modes.REMOVE;
        if (Utils.showQuestionMessage(_spread, "",
                Messages.getString("WARN_DELETE_RECORD"))) {
            removeData(key);
            _spread.refresh();
        }
    }

    public abstract void removeData(int key) throws DBException;

    /**
     * Called after adding.
     * 
     */
    public abstract void save() throws DBException;

    /**
     * Called after editing.
     * 
     * @param key primary key
     */
    public abstract void save(int key) throws DBException;

    /**
     * @param parentFrame The parentFrame to set.
     */
    public void setParentFrame(JFrame parentFrame)
    {
        _parentFrame = parentFrame;
    }

    protected abstract void refreshForm(int key);

    private JPanel getButtonsPanel()
    {
        if (_buttonsPanel == null) {
            _okButton = new JButton("OK");
            DialogListener dialogListener = new DialogListener();
            _okButton.addActionListener(dialogListener);
            JButton cancelButton = new JButton("Anuluj");
            cancelButton.addActionListener(dialogListener);

            ButtonBarBuilder builder = new ButtonBarBuilder();
            builder.addGlue();
            builder.addGriddedButtons(new JButton[]{_okButton, cancelButton,});
            _buttonsPanel = builder.getPanel();
        }
        return _buttonsPanel;
    }

    private JDialog getDialog()
    {
        if (_dialog == null) {
            LOGGER.info("AbstractChildForm.getDialog()");
            _dialog = new JDialog(_parentFrame, false);
            _dialog.setTitle(_controlPanel.getName());
            JPanel dialogPanel = new JPanel();
            dialogPanel.setLayout(new BorderLayout());
            dialogPanel.add(_controlPanel, BorderLayout.CENTER);
            dialogPanel.add(getButtonsPanel(), BorderLayout.SOUTH);
            _dialog.getContentPane().add(dialogPanel);
        }
        return _dialog;
    }

    private void showDialog()
    {
        JDialog dialog = getDialog();
        dialog.pack();
        Point point = Utils.getCenterLocation(this.getPanel());
        _dialog.setLocation(point);
        dialog.setVisible(true);
    }

    private class DialogListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == _okButton) {
                try {
                    switch (_mode) {
                        case INIT :
                            LOGGER.info("INIT: saving");
                            save();
                            _spread.refresh();
                            break;
                        case EDIT :
                            LOGGER.info("EDIT: saving");
                            save(_currentKey);
                            _spread.refresh();
                            break;
                        case REMOVE :
                            LOGGER.info("REMOVE");
                            remove(_currentKey);
                            _spread.refresh();
                            break;
                    }
                    _dialog.setVisible(false);
                } catch (DBException ex) {
                    _manager.rollback();
                    LOGGER.fatal("", ex);
                    Utils.showErrorMessage(Messages.getString("ERR_DB_ACCESS"));
                }
            } else {
                _dialog.setVisible(false);
            }

        }
    }

    public abstract void refreshControls();

    private enum Modes {
        EDIT, INIT, REMOVE, UNKNOWN
    }

    private static final Logger LOGGER = Logger.getLogger(AbstractChildForm.class);
}