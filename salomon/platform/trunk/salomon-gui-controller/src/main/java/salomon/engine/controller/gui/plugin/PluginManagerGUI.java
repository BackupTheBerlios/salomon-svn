/*
 * Copyright (C) 2004 Salomon Team
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

package salomon.engine.controller.gui.plugin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.Config;
import salomon.engine.Messages;
import salomon.engine.controller.gui.ControllerFrame;
import salomon.engine.controller.gui.common.SearchFileFilter;
import salomon.engine.controller.gui.common.TreeFileChooser;
import salomon.engine.controller.gui.common.action.ActionManager;
import salomon.engine.controller.gui.common.statusbar.StatusBar;
import salomon.engine.plugin.ILocalPlugin;
import salomon.engine.plugin.IPluginManager;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.plugin.PluginInfo;
import salomon.engine.plugin.PluginManager;
import salomon.engine.plugin.PluginInfo.PluginType;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.Utils;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public final class PluginManagerGUI
{
    private static final Logger LOGGER = Logger.getLogger(PluginManagerGUI.class);

    private static final String PLUGIN_DESC = "Plugins";

    private static final String PLUGIN_EXT = "jar";

    /**
     * 
     * @uml.property name="_actionManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ActionManager _actionManager;

    private JButton _btnPluginFileLoad;

    private JComponent _editPluginPanel;

    private TreeFileChooser _treeFileChooserPlugin;

    /**
     * 
     * @uml.property name="_parent"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ControllerFrame _parent;

    private JList _pluginList;

    private DefaultListModel _pluginListModel;

    /**
     * 
     * @uml.property name="_pluginManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IPluginManager _pluginManager;

    private JPopupMenu _pluginPopup;

    private JPopupMenu _pluginPopupAdd;

    private JFrame _pluginsViewerFrame;

    private MouseListener _popupListener;

    private JRadioButton _radioLocationFile;

    private JRadioButton _radioLocationUrl;

    private int _selectedItem;

    private JTextArea _txtPluginInfo;

    private JTextField _txtPluginLocation;

    private JTextField _txtPluginName;

    /**
     * 
     */
    public PluginManagerGUI(IPluginManager pluginManager)
    {
        _pluginManager = pluginManager;

        _pluginListModel = new DefaultListModel();
        _pluginList = new JList(_pluginListModel);
        // adding listener
        _popupListener = new PopupListener();
        _pluginList.addMouseListener(_popupListener);
    }

    /**
     * 
     */
    public void addPlugin()
    {
        // TODO: change it
        getEditPluginPanel();
        _txtPluginName.setText("");
        _txtPluginLocation.setText("");
        _txtPluginInfo.setText("");
        int retVal = JOptionPane.showConfirmDialog(_parent,
                getEditPluginPanel(), Messages.getString("MNU_ADD_PLUGIN"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (retVal == JOptionPane.OK_OPTION) {
            // determine plugin type
            boolean isUrl = _radioLocationUrl.isSelected();
            String location = _txtPluginLocation.getText();
            if (isUrl) {
                try {
                    // try to validate given URL
                    new URL(location);
                } catch (MalformedURLException e) {
                    LOGGER.fatal("", e);
                    Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN_INVALID_URL"));
                    return;
                }
            }
            LocalPlugin plugin = null;
            try {
                plugin = (LocalPlugin) _pluginManager.createPlugin();
            } catch (PlatformException e) {
                LOGGER.fatal("", e);
                Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
                return;
            }
            boolean wasOk = false;
            try {
            	//FIXME:
                PluginInfo desc = null; plugin.getInfo();
//                desc.setName(_txtPluginName.getText());
                desc.setLocation(location);
                desc.setInfo(_txtPluginInfo.getText());
                if (isUrl) {
                    desc.setPluginType(PluginType.REMOTE);
                } else {
                    desc.setPluginType(PluginType.LOCAL);
                }

                wasOk = _pluginManager.savePlugin(plugin);
            } catch (PlatformException e) {
                LOGGER.error("", e);
                Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
            }
            if (wasOk) {
                refresh();
            } else {
                Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
            }
        }
    }

    /**
     * Shows FileDialog in which plugin file can be choosen
     */
    public void choosePluginFile()
    {
        if (_treeFileChooserPlugin.showDialog(_parent)) {
            File file = _treeFileChooserPlugin.getSelectedFiles()[0];
            // TODO: doesn't support subdirecotries
            _txtPluginLocation.setText(file.getName());
            LOGGER.debug("Opening: " + file.getName());
        }

    }

    public JList getPluginList()
    {
        return _pluginList;
    }

    /**
     * Reload plugin list and refresh GUI
     */
    public void refresh()
    {
        LOGGER.debug("reloading plugins");

        ILocalPlugin[] plugins = null;
        try {
            _pluginListModel.removeAllElements();
            _pluginManager.clearPluginList();
            plugins = _pluginManager.getPlugins();

            if (plugins != null)
                for (ILocalPlugin plugin : plugins) {
                    LOGGER.debug("adding plugin:" + plugin);
                    _pluginListModel.addElement(plugin);
                }
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_LOAD_PLUGINS"));
            return;
        }
    }

    /**
     * removes plugin
     */
    public void removePlugin()
    {
        if (Utils.showQuestionMessage(Messages.getString("TIT_WARN"),
                Messages.getString("TXT_REMOVE_PLUGIN_QUESTION"))) {
            ILocalPlugin plugin = (LocalPlugin) _pluginListModel.get(_selectedItem);

            boolean wasOk = false;
            try {
                wasOk = _pluginManager.removePlugin(plugin);
            } catch (PlatformException e) {
                LOGGER.error("", e);
            }

            if (wasOk) {
                _pluginListModel.remove(_selectedItem);
                _parent.refreshGui();
            } else {
                Utils.showErrorMessage(Messages.getString("ERR_CANNOT_REMOVE_PLUGIN"));
            }
        }
    }

    /**
     * saves plugin data in database
     */
    public void savePlugin()
    {
        // TODO: reimplement this method, substract some common code from this and addPlugin()
        // and move it to separate method
        LocalPlugin plugin = (LocalPlugin) _pluginListModel.get(_selectedItem);
        PluginInfo pluginInfo = null;
        try {
        	// FIXME:
            pluginInfo = null; //plugin.getInfo();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
            return;
        }
        // to initialize components
        getEditPluginPanel();
        //FIXME:
        //_txtPluginName.setText(pluginInfo.getName());
        _txtPluginLocation.setText(pluginInfo.getLocation().toString());
        _txtPluginInfo.setText(pluginInfo.getInfo());
        int retVal = JOptionPane.showConfirmDialog(_parent,
                getEditPluginPanel(), Messages.getString("MNU_EDIT_PLUGIN"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (retVal == JOptionPane.OK_OPTION) {
            // determine plugin type
            boolean isUrl = _radioLocationUrl.isSelected();
            String location = _txtPluginLocation.getText();
            if (isUrl) {
                try {
                    // try to validate given URL
                    new URL(location);
                } catch (MalformedURLException e) {
                    LOGGER.fatal("", e);
                    Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN_INVALID_URL"));
                    return;
                }
            }
            boolean wasOk = false;
            try {
            	// FIXME:
                //pluginInfo.setName(_txtPluginName.getText());
                pluginInfo.setInfo(_txtPluginInfo.getText());
                if (isUrl) {
                    pluginInfo.setPluginType(PluginType.REMOTE);
                } else {
                    pluginInfo.setPluginType(PluginType.LOCAL);
                }
                wasOk = _pluginManager.savePlugin(plugin);
            } catch (PlatformException e) {
                LOGGER.error("", e);
                Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
            }

            if (wasOk) {
                // refresh();
            } else {
                Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
            }
        }
    }

    /**
     * @param actionManager
     */
    public void setActionManager(ActionManager actionManager)
    {
        _actionManager = actionManager;
    }

    /**
     * @param parent The parent to set.
     */
    public void setParent(ControllerFrame parent)
    {
        _parent = parent;
    }

    /**
     * Set the value of statusBar field.
     * 
     * @param statusBar The statusBar to set
     */
    public void setStatusBar(StatusBar statusBar)
    {
        // empty body
    }

    /**
     * Switch between FILE and URL type of plugin location
     */
    public void switchPluginLocationType()
    {
        if (_radioLocationFile.isSelected()) {
            _btnPluginFileLoad.setEnabled(true);
            _txtPluginLocation.setText("");
        }
        if (_radioLocationUrl.isSelected()) {
            _btnPluginFileLoad.setEnabled(false);
            _txtPluginLocation.setText("http://");
        }
    }

    public void viewPlugins()
    {
        if (_pluginsViewerFrame == null) {
            _pluginsViewerFrame = new JFrame(Messages.getString("TIT_PLUGINS"));
            _pluginsViewerFrame.getContentPane().add(
                    new PluginSpread(
                            ((PluginManager) _pluginManager).getDBManager()));
            _pluginsViewerFrame.pack();
        }
        _pluginsViewerFrame.setLocation(Utils.getCenterLocation(_pluginsViewerFrame));
        _pluginsViewerFrame.setVisible(true);
    }

    private JPanel createPluginPanel()
    {
        FormLayout layout = new FormLayout(
                "left:pref, 3dlu, right:100dlu, 3dlu, right:20dlu", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();
        builder.appendSeparator("Plugin data");

        // initialize components

        _btnPluginFileLoad = new JButton();
        _btnPluginFileLoad.setAction(_actionManager.getChoosePluginFileAction());
        _btnPluginFileLoad.setText(Messages.getString("BTN_BROWSE"));

        _treeFileChooserPlugin = new TreeFileChooser(
                new File(Config.PLUGINS_DIR));
        _treeFileChooserPlugin.setFileFilter(new SearchFileFilter(PLUGIN_EXT,
                PLUGIN_DESC));

        int size = 100;
        _txtPluginName = new JTextField(size);
        _txtPluginLocation = new JTextField(size);
        _txtPluginInfo = new JTextArea(3, 10);
        _radioLocationUrl = new JRadioButton();
        _radioLocationFile = new JRadioButton();
        _txtPluginLocation.setText("");

        _radioLocationUrl.setAction(_actionManager.getSwitchPluginLocationTypeAction());
        _radioLocationFile.setAction(_actionManager.getSwitchPluginLocationTypeAction());

        ButtonGroup buttonGroupLocation = new ButtonGroup();
        buttonGroupLocation.add(_radioLocationFile);
        buttonGroupLocation.add(_radioLocationUrl);
        _radioLocationFile.setSelected(true);

        // putting components
        builder.append("Plugin name", _txtPluginName, 3);
        builder.append("Plugin location");
        builder.append(_txtPluginLocation, _btnPluginFileLoad);
        builder.append(Messages.getString("TXT_PLUGIN_FILE"),
                _radioLocationFile, 3);
        builder.append(Messages.getString("TXT_PLUGIN_URL"), _radioLocationUrl,
                3);

        builder.appendSeparator("Plugin info");
        builder.append(new JScrollPane(_txtPluginInfo), 5);

        return builder.getPanel();
    }

    /**
     * @return JComponent Panel responsible for edition of plugin parameters
     */
    private JComponent getEditPluginPanel()
    {
        if (_editPluginPanel == null) {
            _editPluginPanel = createPluginPanel();
        }
        return _editPluginPanel;
    }

    private JPopupMenu getPluginPopup()
    {
        if (_pluginPopup == null) {
            _pluginPopup = new JPopupMenu();

            JMenuItem itmAdd = new JMenuItem(
                    _actionManager.getAddPluginAction());
            itmAdd.setText(Messages.getString("MNU_ADD_PLUGIN")); //$NON-NLS-1$

            JMenuItem itmEdit = new JMenuItem(
                    _actionManager.getSavePluginAction());
            itmEdit.setText(Messages.getString("MNU_EDIT_PLUGIN"));
            JMenuItem itmRemove = new JMenuItem(
                    _actionManager.getRemovePluginAction());
            itmRemove.setText(Messages.getString("MNU_REMOVE_PLUGIN"));
            _pluginPopup.add(itmAdd);
            _pluginPopup.add(itmEdit);
            _pluginPopup.add(itmRemove);
        }
        return _pluginPopup;
    }

    private JPopupMenu getPluginPopupAdd()
    {
        if (_pluginPopupAdd == null) {
            _pluginPopupAdd = new JPopupMenu();

            JMenuItem itmAdd = new JMenuItem(
                    _actionManager.getAddPluginAction());
            itmAdd.setText(Messages.getString("MNU_ADD_PLUGIN")); //$NON-NLS-1$

            _pluginPopupAdd.add(itmAdd);
        }
        return _pluginPopupAdd;
    }

    private final class PopupListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            maybeShowPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e)
        {
            if (e.isPopupTrigger()) {
                JList list = (JList) e.getSource();
                _selectedItem = list.getSelectedIndex();
                if (_selectedItem >= 0) {
                    getPluginPopup().show(e.getComponent(), e.getX(), e.getY());
                } else {
                    getPluginPopupAdd().show(e.getComponent(), e.getX(),
                            e.getY());
                }

            }
        }
    }
}
