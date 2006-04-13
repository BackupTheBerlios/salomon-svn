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

package salomon.engine.controller.gui;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.controller.gui.graph.GraphTaskManagerGUI;
import salomon.engine.controller.gui.viewer.ProjectViewer;
import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.project.Project;
import salomon.engine.project.ProjectManager;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.Utils;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Class used to manage with projects editing.
 */
public final class ProjectManagerGUI
{
    private static final Logger LOGGER = Logger.getLogger(ProjectManagerGUI.class);

    private static final String SB_CUR_PROJECT = "TT_CURRENT_PROJECT";

    /**
     * 
     * @uml.property name="_parent"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ControllerFrame _parent;

    private JPanel _pnlProjectProperties;

    /**
     * 
     * @uml.property name="_projectManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IProjectManager _projectManager;

    private JFrame _projectViewerFrame;

    private StatusBar _statusBar;

    /**
     * 
     * @uml.property name="_taskManagerGUI"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private GraphTaskManagerGUI _taskManagerGUI;

    private JTextArea _txtProjectInfo;

    private JTextField _txtProjectName;

    private JTextField _txtProjectCrDate;

    private JTextField _txtProjectLastMod;

    /**
     */
    public ProjectManagerGUI(IProjectManager projectManager)
    {
        _projectManager = projectManager;
    }

    private int chooseProject()
    {
        int projectID = 0;

        try {
            // FIXME ugly but quick
            Collection projects = ((ProjectManager) _projectManager).getProjectList();
            JTable projectTable = null;
            projectTable = Utils.createResultTable(projects);
            projectID = showProjectList(projectTable);
        } catch (Exception e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage("Cannot load project list.");
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

        int size = 100;

        _txtProjectName = new JTextField(size);
        _txtProjectCrDate = new JTextField();
        _txtProjectCrDate.setEnabled(false);
        _txtProjectLastMod = new JTextField();
        _txtProjectLastMod.setEnabled(false);

        _txtProjectInfo = new JTextArea(3, 10);

        builder.append("Project name");
        builder.append(_txtProjectName, 3);
        builder.append("Creation date");
        builder.append(_txtProjectCrDate, 3);
        builder.append("Last mod. date");
        builder.append(_txtProjectLastMod, 3);

        builder.appendSeparator("Project info");
        builder.append(new JScrollPane(_txtProjectInfo), 5);

        return builder.getPanel();
    }

    public void editProject()
    {

        try {
            Project project = (Project) _projectManager.getCurrentProject();

            // saving project
            this.saveProject(project);

        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PROJECT"));
        }
    }

    public void newProject()
    {

        try {
            Project project = (Project) _projectManager.createProject();
            // saving project
            this.saveProject(project);

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
                _statusBar.setItem(SB_CUR_PROJECT,
                        ((Project) project).getInfo().getName());
                _parent.refreshGui();
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_OPEN_PROJECT"));
        }
    }

    public void saveProject()
    {
        try {
            Project project = (Project) _projectManager.getCurrentProject();

            // saving project
            this.saveProject(project);

        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PROJECT"));
        }
    }

    private void saveProject(Project project) throws PlatformException
    {
        if (setProjectProperties(project)) {
            if (_taskManagerGUI.saveTasks()) {
                _projectManager.saveProject();
                Utils.showInfoMessage("Project saved successfully");
            } else {
                Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PROJECT"));
                return;
            }
            _statusBar.setItem(SB_CUR_PROJECT,
                    project.getInfo().getName());
            _parent.refreshGui();
        }
    }

    /**
     * @param parent The parent to set.
     */
    public void setParent(ControllerFrame parent)
    {
        _parent = parent;
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
            _pnlProjectProperties = createProjectPanel();
        }

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

        int retVal = JOptionPane.showConfirmDialog(_parent,
                _pnlProjectProperties, "Enter project properties",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (retVal == JOptionPane.YES_OPTION) {
            project.getInfo().setName(_txtProjectName.getText());
            project.getInfo().setInfo(_txtProjectInfo.getText());
            _statusBar.setItem(SB_CUR_PROJECT, _txtProjectName.getText());
            approved = true;
        }
        return approved;
    }

    /**
     * Set the value of statusBar field.
     * 
     * @param statusBar The statusBar to set
     */
    public void setStatusBar(StatusBar statusBar)
    {
        _statusBar = statusBar;
        _statusBar.addItem(SB_CUR_PROJECT);
    }

    public void setTaskManagerGUI(GraphTaskManagerGUI taskManagerGUI)
    {
        _taskManagerGUI = taskManagerGUI;
    }

    private int showProjectList(JTable table)
    {
        int projectID = 0;
        JScrollPane panel = new JScrollPane();
        panel.setViewportView(table);
        // Dimension dim = new Dimension(250, 200);
        // panel.setMaximumSize(dim);
        // panel.setPreferredSize(dim);

        int result = JOptionPane.showConfirmDialog(_parent, panel,
                "Choose project", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                projectID = ((Integer) table.getValueAt(selectedRow, 0)).intValue();
            }
        }

        return projectID;
    }

    public void viewProjects()
    {
        if (_projectViewerFrame == null) {
            _projectViewerFrame = new JFrame(Messages.getString("TIT_PROJECTS"));
            _projectViewerFrame.getContentPane().add(
                    new ProjectViewer(
                            ((ProjectManager) _projectManager).getDbManager()));
            _projectViewerFrame.pack();
        }
        _projectViewerFrame.setLocation(Utils.getCenterLocation(_projectViewerFrame));
        _projectViewerFrame.setVisible(true);
    }
}