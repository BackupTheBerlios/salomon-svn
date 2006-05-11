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
import java.awt.Dimension;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.controller.gui.ControllerFrame;
import salomon.engine.controller.gui.StatusBar;
import salomon.engine.controller.gui.action.ActionManager;
import salomon.engine.controller.gui.viewer.TaskViewer;
import salomon.engine.plugin.IPluginManager;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.plugin.PlatformUtil;
import salomon.engine.plugin.PluginManager;
import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.Task;
import salomon.engine.task.TaskInfo;
import salomon.engine.task.TaskManager;
import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.gui.Utils;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResultModel;

public final class GraphTaskManagerGUI
{
    private ActionManager _actionManager;

    private JComboBox _cmbPlugins;

    private ControllerFrame _parent;

    private PluginManager _pluginManager;

    private JPanel _pnlTaskProperties;

    private TaskGraphEditor _taskGraphEditor;

    private TaskManager _taskManager;

    private JFrame _tasksViewerFrame;

    private JTextField _txtTaskCrDate;

    private JTextArea _txtTaskInfo;

    private JTextField _txtTaskLastMod;

    private JTextField _txtTaskName;

    private JTextField _txtTaskStatus;

    private PlatformUtil _platformUtil;

    public GraphTaskManagerGUI(ITaskManager tasksManager,
            IPluginManager pluginManager)
    {
        _taskManager = (TaskManager) tasksManager;
        _pluginManager = (PluginManager) pluginManager;
        _platformUtil = _taskManager.getPlatformUtil();
    }

    public ITask createTask()
    {
        ITask newTask = null;
        try {
            // creating task
            newTask = _taskManager.createTask();

            // setting some params from GUI
            if (setTaskProperties(newTask)) {
                // adding task to managers
                _taskManager.addTask(newTask);
            } else {
                // nulling unneccessary reference
                // null should be return to indicate
                // that user cancelled task creation
                newTask = null;
            }
        } catch (Exception e) {
            LOGGER.fatal("", e); //$NON-NLS-1$				
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_LOAD_PLUGIN"));
        }

        return newTask;
    }

    public void editTask(ITask task)
    {
        try {
            setTaskProperties(task);
        } catch (PlatformException e) {
            LOGGER.fatal("Exception was thrown!", e);
        }
    }

    /**
     * Returns the actionManager.
     * 
     * @return The actionManager
     */
    public ActionManager getActionManager()
    {
        return _actionManager;
    }

    public JPanel getGraphPanel()
    {
        _taskGraphEditor = new TaskGraphEditor(this);
        return _taskGraphEditor;
    }

    public void refresh()
    {
        LOGGER.debug("reloading tasks");
        //		_graph.removeAllEdges();
        //		_graph.removeAllVertices();
        ITask[] tasks = null;
        try {
            tasks = _taskManager.getTasks();
        } catch (PlatformException e1) {
            LOGGER.fatal("", e1);
            Utils.showErrorMessage("Cannot load task list");
            return;
        }

        _taskGraphEditor.loadTasks(tasks);
    }

    public void removeAllTasks()
    {
        throw new UnsupportedOperationException(
                "Method removeAllTasks() not implemented yet!");
    }

    public void removeTask()
    {
        throw new UnsupportedOperationException(
                "Method removeTask() not implemented yet!");
    }

    public void startTasks()
    {
        List<ITask> executionPlan = GraphPlanner.makePlan(_taskGraphEditor.getGraph());
        if (executionPlan == null) {
            JOptionPane.showMessageDialog(_parent,
                    "Cannot create plan. There is a loop!",
                    "Cannot create plan!", JOptionPane.PLAIN_MESSAGE); //$NON-NLS-1$			
        } else {
            try {
                int i = 1;
                for (ITask task : executionPlan) {
                    // setting task order
                    ((TaskInfo) task.getInfo()).setTaskNr(i);
                    ++i;
                }
                _taskManager.saveTasks();
                _taskManager.getRunner().start();
            } catch (PlatformException e) {
                LOGGER.fatal("", e);
            }
        }
    }

    public void pauseTasks()
    {
        try {
            _taskManager.getRunner().pause();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
    }

    public void resumeTasks()
    {
        try {
            _taskManager.getRunner().resume();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
    }

    public void stopTasks()
    {
        try {
            _taskManager.getRunner().stop();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
    }

    public boolean saveTasks()
    {
        boolean success = false;
        try {
            _taskManager.saveTasks();
            success = true;
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_TASKS"));
        }
        return success;
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

    /**
     * Set the value of statusBar field.
     * 
     * @param statusBar The statusBar to set
     */
    public void setStatusBar(StatusBar statusBar)
    {
    }

    public void showResultPanel(ITask task)
    {

        IPlugin plugin = null;
        IResult result = null;
        Component comp = null;
        try {
            plugin = task.getPlugin();
            IResultComponent resultComponent = plugin.getResultComponent();
            result = task.getResult();
            IDataEngine dataEngine = _taskManager.getProject().getProjectManager().getSolution().getDataEngine();
            comp = resultComponent.getComponent(result, dataEngine);
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage("ERR_CANNOT_SHOW_TASK_RESULT");
            return;
        }

        Dimension maxDim = new Dimension(400, 300);
        Dimension prefDim = comp.getPreferredSize();
        // setting maximum size
        if (prefDim.height > maxDim.height) {
            prefDim.height = maxDim.height;
        }
        if (prefDim.width > maxDim.width) {
            prefDim.width = maxDim.width;
        }
        comp.setSize(prefDim);
        JOptionPane.showMessageDialog(
                _parent,
                comp,
                Messages.getString("TIT_PLUGIN_RESULT"), JOptionPane.PLAIN_MESSAGE); //$NON-NLS-1$
    }

    public void showSettingsPanel(ITask task)
    {
        LOGGER.info("GraphTaskManagerGUI.showSettingsPanel()");
        LOGGER.debug("task: " + task);
        IPlugin plugin = null;
        ISettingComponent settingComponent = null;
        ISettings inputSettings = null;
        try {
            plugin = task.getPlugin();
            settingComponent = plugin.getSettingComponent(_platformUtil);
            inputSettings = task.getSettings();
        } catch (PlatformException e1) {
            LOGGER.fatal("", e1);
            Utils.showErrorMessage("ERR_CANNOT_SHOW_TASK_SETTINGS");
            return;
        }
        // if settings are null, default settings are taken
        if (inputSettings == null) {
            inputSettings = plugin.getSettingComponent(_platformUtil).getDefaultSettings();
        }
        // FIXME needed by DataSet
        IDataEngine dataEngine = null;
        try {
            dataEngine = _taskManager.getProject().getProjectManager().getSolution().getDataEngine();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage("ERR_CANNOT_SHOW_TASK_SETTINGS");
            return;
        }
        Component taskSettingsComponent = settingComponent.getComponent(
                inputSettings, dataEngine);

        // showing dialog
        SettingsDialog settingsDialog = new SettingsDialog(_parent);
        settingsDialog.setSettingsComponent(taskSettingsComponent);
        ValidationResultModel resultModel = _platformUtil.getValidationResultModel();

        // if resultModel == null 
        //it means plugin doesn't use validation
        if (resultModel != null) {
            settingsDialog.setValidationModel(resultModel);
        }

        if (settingsDialog.showSettingsDialog()) {

            //        int result = JOptionPane.showConfirmDialog(_positionComponent,
            //                taskSettingsComponent, , //$NON-NLS-1$
            //                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            //        if (result == JOptionPane.OK_OPTION) {
            ISettings settings = settingComponent.getSettings();
            LOGGER.info("settings: " + settings); //$NON-NLS-1$
            try {
                task.setSettings(settings);
                task.getInfo().save();
            } catch (Exception e) {
                LOGGER.fatal("", e);
                Utils.showErrorMessage("ERR_CANNOT_SET_TASK_SETTINGS");
            }
        }
    }

    public void viewTasks()
    {
        if (_tasksViewerFrame == null) {
            _tasksViewerFrame = new JFrame(Messages.getString("TIT_TASKS"));
            _tasksViewerFrame.getContentPane().add(
                    new TaskViewer(((TaskManager) _taskManager).getDBManager()));
            _tasksViewerFrame.pack();
        }
        _tasksViewerFrame.setLocation(Utils.getCenterLocation(_tasksViewerFrame));
        _tasksViewerFrame.setVisible(true);
    }

    private JPanel createTaskPanel()
    {
        FormLayout layout = new FormLayout(
                "left:pref, 3dlu, right:100dlu:grow", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.appendSeparator("Task Data");

        Dimension size = new Dimension(150, 20);
        _txtTaskName = new JTextField();
        _txtTaskName.setPreferredSize(size);

        _cmbPlugins = new JComboBox();
        _cmbPlugins.setPreferredSize(size);

        _txtTaskCrDate = new JTextField();
        _txtTaskCrDate.setEnabled(false);
        _txtTaskCrDate.setPreferredSize(size);

        _txtTaskLastMod = new JTextField();
        _txtTaskLastMod.setEnabled(false);
        _txtTaskLastMod.setPreferredSize(size);

        _txtTaskStatus = new JTextField();
        _txtTaskStatus.setEnabled(false);
        _txtTaskStatus.setPreferredSize(size);

        _txtTaskInfo = new JTextArea(3, 10);

        builder.append(new JLabel("Task name"), _txtTaskName);
        builder.append(new JLabel("Plugin"), _cmbPlugins);
        builder.append(new JLabel("Task Status"), _txtTaskStatus);
        builder.append(new JLabel("Creation Date"), _txtTaskCrDate);
        builder.append(new JLabel("Last Modification Date"), _txtTaskLastMod);

        builder.appendSeparator("Task Info");
        builder.append(new JScrollPane(_txtTaskInfo), 3);

        return builder.getPanel();
    }

    private boolean setTaskProperties(ITask iTask) throws PlatformException
    {
        boolean accepted = false;
        Task task = (Task) iTask;
        LocalPlugin localPlugin = null;

        if (_pnlTaskProperties == null) {
            _pnlTaskProperties = createTaskPanel();
        }

        String name = task.getInfo().getName();
        String info = task.getInfo().getInfo();
        Date dcdate = task.getInfo().getCreationDate();
        Date dmdate = task.getInfo().getLastModificationDate();

        String cdate = dcdate == null
                ? ""
                : DateFormat.getDateInstance().format(dcdate) + " "
                        + DateFormat.getTimeInstance().format(dcdate);
        String mdate = dmdate == null
                ? ""
                : DateFormat.getDateInstance().format(dmdate) + " "
                        + DateFormat.getTimeInstance().format(dmdate);
        String status = task.getInfo().getStatus();

        // selecting plugin

        // trying to get available plugins
        final int pluginId = task.getInfo().getPluginID();
        _cmbPlugins.removeAllItems();
        if (pluginId == 0) {
            LocalPlugin[] plugins = (LocalPlugin[]) _pluginManager.getPlugins();
            for (LocalPlugin plugin : plugins) {
                _cmbPlugins.addItem(plugin);
            }
        } else {
            localPlugin = (LocalPlugin) _pluginManager.getPlugin(pluginId);
            _cmbPlugins.addItem(localPlugin);
            _cmbPlugins.setEditable(false);
        }

        _txtTaskName.setText(name == null ? "" : name);
        _txtTaskInfo.setText(info == null ? "" : info);
        _txtTaskCrDate.setText(cdate == null ? "" : cdate);
        _txtTaskLastMod.setText(mdate == null ? "" : mdate);
        _txtTaskStatus.setText(status == null ? "" : status);

        int result = JOptionPane.showConfirmDialog(_parent, _pnlTaskProperties,
                "Enter Task properties", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {

            // loading plugin
            try {
                if (localPlugin == null) {
                    localPlugin = (LocalPlugin) _cmbPlugins.getSelectedItem();
                }
                localPlugin.load();
                // adding copy of plugin !!!
                LocalPlugin pluginCopy = (LocalPlugin) localPlugin.clone();
                task.setPlugin(pluginCopy);
                task.getInfo().setName(_txtTaskName.getText());
                task.getInfo().setInfo(_txtTaskInfo.getText());

                _taskManager.saveTasks();
                accepted = true;
            } catch (Exception e) {
                LOGGER.fatal("", e);
                Utils.showErrorMessage(Messages.getString("ERR_CANNOT_LOAD_PLUGIN"));
            }
        }
        return accepted;
    }

    private static final Logger LOGGER = Logger.getLogger(GraphTaskManagerGUI.class);

}
