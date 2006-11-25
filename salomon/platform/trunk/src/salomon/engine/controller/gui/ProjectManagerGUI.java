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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
import salomon.engine.project.ProjectInfo;
import salomon.engine.project.ProjectManager;
import salomon.engine.project.event.ProjectEvent;
import salomon.engine.project.event.ProjectListener;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.DBDataTable;
import salomon.util.gui.Utils;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Class used to manage with projects editing.
 */
public final class ProjectManagerGUI
{
    private static final Logger LOGGER = Logger.getLogger(ProjectManagerGUI.class);

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

    private JFrame _projectViewerFrame;

    /**
     * 
     * @uml.property name="_taskManagerGUI"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private GraphTaskManagerGUI _taskManagerGUI;

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

    public void editProject()
    {

        try {
            Project project = (Project) _projectManager.getCurrentProject();

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

            // saving project
            this.saveProject(project, false);

            // informing listeners
            fireProjectCreated(new ProjectEvent((ProjectInfo) project.getInfo()));

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

    /**
     * @param parent The parent to set.
     */
    public void setParent(ControllerFrame parent)
    {
        _parent = parent;
    }

    public void setTaskManagerGUI(GraphTaskManagerGUI taskManagerGUI)
    {
        _taskManagerGUI = taskManagerGUI;
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

    private void saveProject(Project project, boolean forceNew) throws PlatformException
    {
        if (setProjectProperties(project)) {
            _projectManager.saveProject(forceNew);
            Utils.showInfoMessage(Messages.getString("TT_PROJECT_SAVED"));
            _parent.refreshGui();
        }
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
                projectID = ((Integer) table.getModel().getValueAt(selectedRow, 0)).intValue();
            }
        }

        return projectID;
    }
}