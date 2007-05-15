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

package salomon.engine.controller.gui.project;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.agentconfig.IAgentConfig;
import salomon.engine.controller.gui.ControllerFrame;
import salomon.engine.controller.gui.agentconfig.AgentConfigManagerGUI;
import salomon.engine.controller.gui.common.action.ActionManager;
import salomon.engine.controller.gui.task.SettingsDialog;
import salomon.engine.plugin.PlatformUtil;
import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.project.Project;
import salomon.engine.project.ProjectInfo;
import salomon.engine.project.ProjectManager;
import salomon.engine.project.event.ProjectEvent;
import salomon.engine.project.event.ProjectListener;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.DBDataTable;
import salomon.util.gui.Utils;
import salomon.util.gui.validation.IComponentFactory;
import salomon.util.gui.validation.IValidationModel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResultModel;

/**
 * Class used to manage with projects editing.
 */
public final class ProjectManagerGUI
{
    private static final Logger LOGGER = Logger.getLogger(ProjectManagerGUI.class);

    private ActionManager _actionManager;

    private SettingsDialog _agentSettingDialog;

    private JButton _btnConfigureAgents;

    private JButton _btnStartAgents;

    private JButton _btnStopAgents;

    private JCheckBox _chkAgentsEnabled;

    private Modes _currentMode;

    /**
     * 
     * @uml.property name="_parent"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ControllerFrame _parent;

    private JPanel _pnlProjectProperties;

    private List<ProjectListener> _projectListeners;

    /**
     * 
     * @uml.property name="_projectManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ProjectManager _projectManager;

    private SettingsDialog _projectSettingsDialog;

    private JFrame _projectViewerFrame;

    private JTextField _txtProjectCrDate;

    private JTextArea _txtProjectInfo;

    private JTextField _txtProjectLastMod;

    private JTextField _txtProjectName;

    /**
     */
    public ProjectManagerGUI(IProjectManager projectManager)
    {
        _projectManager = (ProjectManager) projectManager;
        _projectListeners = new LinkedList<ProjectListener>();
    }

    public void addProjectListener(ProjectListener listener)
    {
        _projectListeners.add(listener);
    }

    public void configureProjectAgents(
            AgentConfigManagerGUI agentConfigManagerGUI)
    {
        LOGGER.info("ProjectManagerGUI.configureAgents()");
        agentConfigManagerGUI.setProject(_projectManager.getCurrentProject());
        _agentSettingDialog.setSettingsComponent(agentConfigManagerGUI.getAgentListPanel());

        if (_agentSettingDialog.showSettingsDialog()) {
            agentConfigManagerGUI.saveAgents();
        }
    }

    public void editProject()
    {
        try {
            Project project = (Project) _projectManager.getCurrentProject();

            _currentMode = Modes.EDIT;
            // saving project
            this.saveProject(project, false);

            // informing listeners
            fireProjectModified(new ProjectEvent(
                    (ProjectInfo) project.getInfo()));

        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PROJECT"));
        }
    }

    /**
     * Returns the projectManager.
     * @return The projectManager
     */
    public ProjectManager getProjectManager()
    {
        return _projectManager;
    }

    public void newProject()
    {
        try {
            Project project = (Project) _projectManager.createProject();

            _currentMode = Modes.ADD;
            // saving project
            this.saveProject(project, false);

            // informing listeners
            fireProjectCreated(new ProjectEvent((ProjectInfo) project.getInfo()));

            // forcing panel to be rebuilt
            _pnlProjectProperties = null;
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_CREATE_PROJECT"));
        }
    }

    public void openProject()
    {
        try {
            final int projectId = chooseProject();
            if (projectId > 0) {
                IProject project = _projectManager.getProject(projectId);
                // informing listeners
                fireProjectOpened(new ProjectEvent(
                        (ProjectInfo) project.getInfo()));
                _parent.refreshGui();
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_OPEN_PROJECT"));
        }
    }

    public void removeProjectListener(ProjectListener listener)
    {
        _projectListeners.remove(listener);
    }

    public void saveProject(boolean forceNew)
    {
        try {
            Project project = (Project) _projectManager.getCurrentProject();

            // saving project
            this.saveProject(project, forceNew);

        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PROJECT"));
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

    private Thread _agentsRunner;

    public void startProjectAgents()
    {
        LOGGER.info("ProjectManagerGUI.startProjectAgents()");
        final IProject project = _projectManager.getCurrentProject();
        final IAgentConfig[] agentConfigs = project.getAgentConfigs();

        _agentsRunner = new Thread() {
            public void run()
            {
                for (IAgentConfig config : agentConfigs) {
                    config.getAgent().start(project);
                }
            }
        };
        _agentsRunner.start();
    }

    public void stopProjectAgents()
    {
        LOGGER.info("ProjectManagerGUI.stopProjectAgents()");
        if (_agentsRunner != null) {
            final IProject project = _projectManager.getCurrentProject();
            final IAgentConfig[] agentConfigs = project.getAgentConfigs();

            for (IAgentConfig config : agentConfigs) {
                config.getAgent().stop();
            }
            _agentsRunner.interrupt();
            _agentsRunner = null;
        }
    }

    public void viewProjects()
    {
        if (_projectViewerFrame == null) {
            _projectViewerFrame = new JFrame(Messages.getString("TIT_PROJECTS"));
            _projectViewerFrame.getContentPane().add(
                    new ProjectSpread(
                            ((ProjectManager) _projectManager).getDbManager()));
            _projectViewerFrame.pack();
        }
        _projectViewerFrame.setLocation(Utils.getCenterLocation(_projectViewerFrame));
        _projectViewerFrame.setVisible(true);
    }

    private int chooseProject()
    {
        int projectID = 0;

        try {
            projectID = showProjectList(createProjectsTable());
        } catch (Exception e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_LOAD_PROJECTS"));
        }

        return projectID;
    }

    private JPanel createProjectPanel()
    {
        FormLayout layout = new FormLayout(
                "left:pref, 3dlu, right:100dlu, 3dlu, right:20dlu", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();
        builder.appendSeparator("Project data");

        builder.append("Project name");
        builder.append(_txtProjectName, 3);
        builder.append("Creation date");
        builder.append(_txtProjectCrDate, 3);
        builder.append("Last mod. date");
        builder.append(_txtProjectLastMod, 3);

        builder.appendSeparator("Agent configuration");
        JPanel agentsRunning = ButtonBarFactory.buildAddRemoveBar(
                _btnStartAgents, _btnStopAgents);
        builder.append(_btnConfigureAgents);
        builder.append(agentsRunning, 3);

        builder.appendSeparator("Project info");
        builder.append(new JScrollPane(_txtProjectInfo), 5);

        return builder.getPanel();
    }

    private JTable createProjectsTable() throws PlatformException
    {
        IProject[] projects = _projectManager.getProjects();
        String[] columnNames = new String[]{"ID", "Name", "Info", "Cr. date",
                "Lm. date"};
        // FIXME: workaround, reimplement this 
        Object[][] data = new Object[projects.length][columnNames.length];

        int i = 0;
        for (IProject project : projects) {
            ProjectInfo info = (ProjectInfo) project.getInfo();
            data[i][0] = info.getId();
            data[i][1] = info.getName();
            data[i][2] = info.getInfo();
            data[i][3] = info.getCreationDate();
            data[i][4] = info.getLastModificationDate();

            ++i;
        }

        DBDataTable table = new DBDataTable(data, columnNames);
        return table;
    }

    private void fireProjectCreated(ProjectEvent event)
    {
        for (ProjectListener listener : _projectListeners) {
            listener.projectCreated(event);
        }
    }

    private void fireProjectModified(ProjectEvent event)
    {
        for (ProjectListener listener : _projectListeners) {
            listener.projectModified(event);
        }
    }

    private void fireProjectOpened(ProjectEvent event)
    {
        for (ProjectListener listener : _projectListeners) {
            listener.projectOpened(event);
        }
    }

    private void initComponents()
    {
        _projectSettingsDialog = new SettingsDialog(_parent,
                Messages.getString("TIT_EDIT_PROJECT"));
        _projectSettingsDialog.setSeparator("Project settings");

        // project settings panel
        _txtProjectCrDate = new JTextField();
        _txtProjectCrDate.setEnabled(false);
        _txtProjectLastMod = new JTextField();
        _txtProjectLastMod.setEnabled(false);

        _agentSettingDialog = new SettingsDialog(_parent, "Configure agents");
        _agentSettingDialog.setSeparator("Agent configuration");

        _btnConfigureAgents = new JButton(
                _actionManager.getConfigureProjectAgentsAction());
        _btnConfigureAgents.setText("Configure agents");
        _btnConfigureAgents.setToolTipText("Save project before configuring agents");

        _btnStartAgents = new JButton(_actionManager.getStartAgentsAction());
        _btnStartAgents.setText("Start agents");

        _btnStopAgents = new JButton(_actionManager.getStopAgentsAction());
        _btnStopAgents.setText("Stop agents");

        _chkAgentsEnabled = new JCheckBox("Agents enabled");
        _txtProjectInfo = new JTextArea(3, 10);

    }

    private void saveProject(Project project, boolean forceNew)
            throws PlatformException
    {
        if (setProjectProperties(project)) {
            _projectManager.saveProject(forceNew);
            Utils.showInfoMessage(Messages.getString("TT_PROJECT_SAVED"));
            _parent.refreshGui();
        }
    }

    private void setControls(Modes mode)
    {
        boolean enabled = (mode == Modes.EDIT);
        _chkAgentsEnabled.setEnabled(enabled);
        _btnConfigureAgents.setEnabled(enabled);
    }

    /**
     * Shows dialog which enables to initialize project settings.
     * 
     * @param project
     * @throws PlatformException 
     */
    private boolean setProjectProperties(IProject iProject)
            throws PlatformException
    {
        boolean approved = false;
        Project project = (Project) iProject;

        if (_pnlProjectProperties == null) {
            initComponents();

            // validation
            ProjectModel model = new ProjectModel();
            ProjectValidator projectValidator = new ProjectValidator(model,
                    _projectManager);
            PlatformUtil platformUtil = (PlatformUtil) _projectManager.getPlaftormUtil();
            IValidationModel validationModel = platformUtil.getValidationModel(projectValidator);
            IComponentFactory componentFactory = validationModel.getComponentFactory();

            ValidationResultModel resultModel = platformUtil.getValidationResultModel();
            _txtProjectName = componentFactory.createTextField(
                    ProjectModel.PROPERTYNAME_PROJECT_NAME, false);

            // recreating panel with new projectName component
            _pnlProjectProperties = createProjectPanel();

            // setting component for dialog
            _projectSettingsDialog.setSettingsComponent(_pnlProjectProperties);
            _projectSettingsDialog.setValidationModel(resultModel);
        }

        // setting the controls mode appropriately
        setControls(_currentMode);

        String name = project.getInfo().getName();
        String info = project.getInfo().getInfo();
        Date cdate = project.getInfo().getCreationDate();
        String scdate = cdate == null
                ? ""
                : DateFormat.getDateInstance().format(cdate) + " "
                        + DateFormat.getTimeInstance().format(cdate);
        Date lmdate = project.getInfo().getLastModificationDate();
        String slmdate = lmdate == null
                ? ""
                : DateFormat.getDateInstance().format(lmdate) + " "
                        + DateFormat.getTimeInstance().format(lmdate);

        _txtProjectName.setText(name == null ? "" : name);
        _txtProjectInfo.setText(info == null ? "" : info);
        _txtProjectCrDate.setText(scdate == null ? "" : scdate);
        _txtProjectLastMod.setText(slmdate == null ? "" : slmdate);

        if (_projectSettingsDialog.showSettingsDialog()) {
            project.getInfo().setName(_txtProjectName.getText());
            project.getInfo().setInfo(_txtProjectInfo.getText());
            approved = true;
        }
        return approved;
    }

    private int showProjectList(JTable table)
    {
        int projectID = 0;
        JScrollPane panel = new JScrollPane();
        panel.setViewportView(table);

        int result = JOptionPane.showConfirmDialog(_parent, panel,
                "Choose project", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // FIXME: hack
                projectID = ((Integer) table.getModel().getValueAt(selectedRow,
                        0)).intValue();
            }
        }

        return projectID;
    }

    private enum Modes {
        ADD, EDIT
    }
}