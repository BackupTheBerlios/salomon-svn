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

package salomon.engine.controller.gui.agentconfig;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.agent.AgentManager;
import salomon.engine.agent.IAgent;
import salomon.engine.agent.IAgentManager;
import salomon.engine.agent.IConfigComponent;
import salomon.engine.agentconfig.AgentConfig;
import salomon.engine.agentconfig.AgentConfigInfo;
import salomon.engine.agentconfig.AgentConfigManager;
import salomon.engine.agentconfig.IAgentConfig;
import salomon.engine.agentconfig.IAgentConfigManager;
import salomon.engine.controller.gui.ControllerFrame;
import salomon.engine.controller.gui.common.action.ActionManager;
import salomon.engine.controller.gui.task.SettingsDialog;
import salomon.engine.platform.serialization.XMLSerializer;
import salomon.engine.project.IProject;
import salomon.platform.serialization.IObject;
import salomon.util.gui.Utils;
import salomon.util.serialization.SimpleStruct;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 */
public class AgentConfigManagerGUI
{
    private static final Logger LOGGER = Logger.getLogger(AgentConfigManagerGUI.class);

    private ActionManager _actionManager;

    private JButton _addAgentConfig;

    private JPopupMenu _addAgentPopup;

    private AgentConfigManager _agentConfigManager;

    private JPanel _agentListPanel;

    private AgentManager _agentManager;

    private JPanel _agentPanel;

    private JPopupMenu _agentPopup;

    private JList _agentsList;

    private DefaultListModel _agentsModel;

    private JComboBox _cmbAgents;

    private JButton _configureAgent;

    private ControllerFrame _parent;

    private IProject _project;

    private JButton _removeAgentConfig;

    private int _selectedItem;

    public AgentConfigManagerGUI(IAgentManager agentManager,
            IAgentConfigManager agentConfigManager)
    {
        _agentManager = (AgentManager) agentManager;
        _agentConfigManager = (AgentConfigManager) agentConfigManager;
        _agentsModel = new DefaultListModel();
    }

    public void addAgentConfig()
    {
        LOGGER.info("AgentConfigManagerGUI.addAgentConfig()");
        int result = JOptionPane.showConfirmDialog(_parent, _agentPanel,
                "Enter Agent properties", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            AgentConfig agentConfig = (AgentConfig) _agentConfigManager.createAgentConfig();
            IAgent agent = (IAgent) _cmbAgents.getSelectedItem();
            agentConfig.setAgent(agent);
            // assuming the project is not null while adding new configuration
            agentConfig.setProject(_project);
            LOGGER.debug("Agent chosen: " + agent);
            _agentConfigManager.addAgentConfig(agentConfig);
            _agentsModel.addElement(agentConfig);
        }
    }

    public void configureAgent()
    {
        LOGGER.info("AgentConfigManagerGUI.configureAgent()");
        IAgentConfig agentConfig = (IAgentConfig) _agentsList.getSelectedValue();

        IConfigComponent configComponent = agentConfig.getAgent().getConfigComponent();
        Component component = configComponent.getComponent();
        SettingsDialog dialog = new SettingsDialog(_parent, "Configure agent");
        dialog.setSettingsComponent(component);
        if (dialog.showSettingsDialog()) {
            IObject config = configComponent.getConfig();
            LOGGER.debug("config: " + config);
            if (config != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                XMLSerializer.serialize((SimpleStruct) config, bos);
                ((AgentConfigInfo) agentConfig.getInfo()).setConfiguration(bos.toString());
            }
        }
    }

    public JPanel getAgentListPanel()
    {
        if (_agentListPanel == null) {
            initComponents();
            _agentListPanel = createAgentListPanel();
        }
        // reloading agent configuration
        _agentsModel.clear();
        IAgentConfig[] configs = _agentConfigManager.getAgentConfigs(_project);
        for (IAgentConfig config : configs) {
            _agentsModel.addElement(config);
        }
        return _agentListPanel;
    }

    public void removeAgentConfig()
    {
        LOGGER.info("AgentConfigManagerGUI.removeAgentConfig()");
        if (Utils.showQuestionMessage("Removing agent",
                "Remove agent from the project?")) {
            IAgentConfig config = (IAgentConfig) _agentsList.getSelectedValue();
            _agentConfigManager.removeAgentConfig(config);
            _agentsModel.removeElement(config);
        }
    }

    public void saveAgents()
    {
        AgentConfig[] configs = new AgentConfig[_agentsModel.getSize()];
        _agentsModel.copyInto(configs);
        for (AgentConfig config : configs) {
            config.getInfo().save();
        }
    }

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

    public void setProject(IProject project)
    {
        _project = project;
    }

    private JPopupMenu createAddAgentConfigPopup()
    {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem addAgent = new JMenuItem("Add agent");
        popup.add(addAgent);

        return popup;
    }

    private JComboBox createAgentCombo()
    {
        IAgent[] agents = _agentManager.getAgents();
        JComboBox agentCombo = new JComboBox(agents);
        return agentCombo;
    }

    private JPopupMenu createAgentConfigPopup()
    {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem addAgent = new JMenuItem("Add agent");
        // TODO:
        addAgent.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                addAgentConfig();
            }
        });

        JMenuItem removeAgent = new JMenuItem("Remove agent");
        JMenuItem configureAgent = new JMenuItem("Configure");

        popup.add(addAgent);
        popup.add(removeAgent);
        popup.addSeparator();
        popup.add(configureAgent);

        return popup;
    }

    private JPanel createAgentListPanel()
    {
        LOGGER.info("AgentConfigManagerGUI.createAgentListPanel()");
        FormLayout layout = new FormLayout("pref:grow", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();
        builder.appendSeparator("Agents");

        builder.append(new JScrollPane(_agentsList));
        JPanel buttonsPanel = ButtonBarFactory.buildCenteredBar(new JButton[]{
                _addAgentConfig, _removeAgentConfig, _configureAgent});
        builder.append(buttonsPanel);

        return builder.getPanel();
    }

    private JPanel createAgentPanel()
    {
        FormLayout layout = new FormLayout(
                "left:pref, 3dlu, right:100dlu:grow", "fill:default");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.appendSeparator("Agent data");
        builder.append(_cmbAgents, 3);

        // TODO: store in DB
        builder.appendSeparator("Agent config info");
        builder.append(new JScrollPane(new JTextField()), 3);

        return builder.getPanel();
    }

    private void initComponents()
    {
        _addAgentPopup = createAddAgentConfigPopup();
        _agentPopup = createAgentConfigPopup();
        _cmbAgents = createAgentCombo();
        _agentPanel = createAgentPanel();
        _agentsList = new JList(_agentsModel);
        _agentsList.addMouseListener(new PopupListener());

        _addAgentConfig = new JButton(_actionManager.getAddAgentConfigAction());
        _addAgentConfig.setText("Add");

        _removeAgentConfig = new JButton(
                _actionManager.getRemoveAgentConfigAction());
        _removeAgentConfig.setText("Remove");
        _configureAgent = new JButton(_actionManager.getConfigureAgentAction());
        _configureAgent.setText("Configure");
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
                    _agentPopup.show(e.getComponent(), e.getX(), e.getY());
                } else {
                    _addAgentPopup.show(e.getComponent(), e.getX(), e.getY());
                }

            }
        }
    }
}
