/*
 * Created on 2004-05-03
 *
 */

package salomon.controller.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.core.IManagerEngine;
import salomon.core.Messages;
import salomon.core.plugin.PluginLoader;
import salomon.core.task.ITask;
import salomon.plugin.IPlugin;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

import com.sun.jmx.snmp.tasks.Task;

/**
 * @author nico
 *  
 */
public final class TaskEditionManager
{

	private IManagerEngine _managerEngine;

	private ControllerFrame _parent;

	private JList _pluginList;

	private DefaultListModel _pluginListModel;

	private JPopupMenu _pluginPopup;

	private MouseListener _popupListener;

	private JComponent _positionComponent;

	private int _selectedItem;

	private JList _taskList;

	private DefaultListModel _taskListModel;

	private JPopupMenu _taskPopup;

	public TaskEditionManager(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
		_taskListModel = new DefaultListModel();
		_pluginListModel = new DefaultListModel();
		_taskList = new JList(_taskListModel);
		_pluginList = new JList(_pluginListModel);
		// adding listener
		_popupListener = new PopupListener();
		_taskList.addMouseListener(_popupListener);
		_pluginList.addMouseListener(_popupListener);
		//TODO: getting plugin from Engine?
		Collection urls = _managerEngine.getPluginManager().getAvailablePlugins();
		for (Iterator iter = urls.iterator(); iter.hasNext();) {
			URL url = (URL) iter.next();
			_pluginListModel.addElement(new LocalPlugin(url));
		}
	}

	/**
	 * Adds selected plugin to list of tasks
	 */
	public void addTask()
	{
		int index = _pluginList.getSelectedIndex();
		if (index >= 0) {
			LocalPlugin localPlugin = (LocalPlugin) _pluginListModel.get(index);
			IPlugin plugin;
			try {
				plugin = localPlugin.getPlugin();
				TaskGUI taskGUI = new TaskGUI();
				taskGUI.setPlugin(plugin);
				taskGUI.setName(getTaskName());
				_taskListModel.addElement(taskGUI);
			} catch (Exception e) {
				_logger.fatal("", e); //$NON-NLS-1$				
				_parent.showErrorMessage(Messages.getString("ERR_CANNOT_LOAD_PLUGIN"));
			}
		} else {
			_logger.warn("Invalid index. Wrong list selected?"); //$NON-NLS-1$
		}
	}

	public JList getPluginList()
	{
		return _pluginList;
	}

	public JList getTaskList()
	{
		return _taskList;
	}

	public List getTasks()
	{
		return Arrays.asList(_taskListModel.toArray());
	}

	/**
	 * Moves task up in the task list. It will be executed earlier.
	 *  
	 */
	public void moveDown()
	{
		if (_taskListModel.size() > 1) {
			int index = _taskList.getSelectedIndex();
			if (index >= 0 && index < _taskListModel.getSize() - 1) {
				Object task = _taskListModel.remove(index);
				_taskListModel.add(index + 1, task);
				_taskList.setSelectedIndex(index + 1);
			} else {
				_logger.warn("Nothing or wrong index selected"); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Moves task down in the task list. It will be executed later.
	 *  
	 */
	public void moveUp()
	{
		if (_taskListModel.size() > 1) {
			int index = _taskList.getSelectedIndex();
			if (index >= 1) {
				Object task = _taskListModel.remove(index);
				_taskListModel.add(index - 1, task);
				_taskList.setSelectedIndex(index - 1);
			} else {
				_logger.warn("Nothing or wrong index selected"); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Loads tasks to task list.
	 * 
	 * @param tasks
	 */
	public void refresh()
	{
		_logger.debug("reloading plugins");
		_pluginListModel.removeAllElements();
		Collection plugins = _managerEngine.getPluginManager().getAvailablePlugins();
		for (Iterator iter = plugins.iterator(); iter.hasNext();) {
            LocalPlugin localPlugin = new LocalPlugin((URL) iter.next());
			_pluginListModel.addElement(localPlugin);    
		}
		_logger.debug("reloading tasks");
		_taskListModel.removeAllElements();
		Collection tasks = _managerEngine.getTasksManager().getTasks();
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			_logger.debug("adding task");
			_taskListModel.addElement(new TaskGUI((ITask) iter.next()));
		}

	}

	/**
	 * Removes plugin from list of tasks and adds it to available plugins list
	 * (?)
	 *  
	 */
	public void removeTask()
	{
		int index = _taskList.getSelectedIndex();
		if (index >= 0) {
			Task task = (Task) _taskListModel.remove(index);
			System.out.println("plugin = " + task); //$NON-NLS-1$
		} else {
			_logger.warn("Invalid index. Wrong list selected?"); //$NON-NLS-1$
		}
	}

	public void runTasks()
	{
		_managerEngine.getTasksManager().start();
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent(ControllerFrame parent)
	{
		_parent = parent;
	}

	private JPopupMenu getPluginPopup()
	{
		if (_pluginPopup == null) {
			_pluginPopup = new JPopupMenu();
			JMenuItem itmInfo = new JMenuItem(Messages.getString("MNU_INFO")); //$NON-NLS-1$
			itmInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					showPluginInfo();
				}
			});
			_pluginPopup.add(itmInfo);
		}
		return _pluginPopup;
	}

	private String getTaskName()
	{
		JTextField txtTaskName = new JTextField();
		JOptionPane.showMessageDialog(
				_positionComponent,
				txtTaskName,
				Messages.getString("TXT_ENTER_TASK_NAME"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		return txtTaskName.getText();
	}

	private JPopupMenu getTaskPopup()
	{
		if (_taskPopup == null) {
			_taskPopup = new JPopupMenu();
			JMenuItem itmSettings = new JMenuItem(
					Messages.getString("MNU_SETTINGS")); //$NON-NLS-1$
			itmSettings.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					showSettingsPanel();
				}
			});
			JMenuItem itmResult = new JMenuItem(
					Messages.getString("MNU_RESULT")); //$NON-NLS-1$
			itmResult.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					showResultPanel();
				}
			});
			_taskPopup.add(itmSettings);
			_taskPopup.add(itmResult);
		}
		return _taskPopup;
	}

	private void showPluginInfo()
	{
		LocalPlugin plugin = (LocalPlugin) _pluginListModel.get(_selectedItem);
		String info;
		_logger.debug("plugin: " + plugin); //$NON-NLS-1$
		try {
			info = plugin.getPlugin().getDescription().getInfo();
			JOptionPane.showMessageDialog(_positionComponent, info);
		} catch (Exception e) {
			_logger.fatal("", e); //$NON-NLS-1$
			_parent.showErrorMessage(Messages.getString("ERR_CANNOT_LOAD_PLUGIN")); //$NON-NLS-1$
		}
	}

	private void showResultPanel()
	{
		TaskGUI currentTask = (TaskGUI) _taskListModel.get(_selectedItem);
		IPlugin plugin = currentTask.getPlugin();
		IResultComponent resultComponent = plugin.getResultComponent();
		Component comp = resultComponent.getComponent(currentTask.getResult());
		Dimension maxDim = new Dimension(400, 300);
		Dimension prefDim = comp.getPreferredSize();
		// setting maximum size
		if (prefDim.height > maxDim.height) {
			prefDim.height = maxDim.height;
		}
		if (prefDim.width > maxDim.width) {
			prefDim.width = maxDim.width;
		}
		comp.setMaximumSize(prefDim);
		comp.setPreferredSize(prefDim);
		JOptionPane.showMessageDialog(
				_positionComponent,
				comp,
				Messages.getString("TIT_PLUGIN_RESULT"), JOptionPane.PLAIN_MESSAGE); //$NON-NLS-1$
	}

	private void showSettingsPanel()
	{
		TaskGUI currentTask = (TaskGUI) _taskListModel.get(_selectedItem);
		IPlugin plugin = currentTask.getPlugin();
		ISettingComponent settingComponent = plugin.getSettingComponent();
		ISettings inputSettings = currentTask.getSettings();
		if (inputSettings == null) {
			inputSettings = plugin.getSettingComponent().getDefaultSettings();
		}
		int result = JOptionPane.showConfirmDialog(_positionComponent,
				settingComponent.getComponent(inputSettings),
				Messages.getString("TIT_PLUGIN_SETTINGS"), //$NON-NLS-1$
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			ISettings settings = settingComponent.getSettings();
			_logger.info("settings: " + settings); //$NON-NLS-1$
			currentTask.setSettings(settings);
		}
	}

	/** Class helps managing plugin loading */
	public class LocalPlugin
	{
		private IPlugin _plugin = null;

		private URL _pluginLocation = null;

		public LocalPlugin(URL pluginFile)
		{
			_pluginLocation = pluginFile;
		}

		/**
		 * Method returns plugin. If it is not loaded, tries load it using
		 * PluginLoader
		 * 
		 * @throws Exception
		 */
		public IPlugin getPlugin() throws Exception
		{
			if (_plugin == null) {
				_logger.debug("trying to load plugin"); //$NON-NLS-1$
				_plugin = PluginLoader.loadPlugin(_pluginLocation);
			}
			return _plugin;
		}

		public String toString()
		{
            //TODO:
            String path = _pluginLocation.getPath();
            int index = path.lastIndexOf('/');
            
			return path.substring(index + 1);
		}
	}

	private class PopupListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e)
		{
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e)
		{
			if (e.isPopupTrigger()) {
				// zapamietanie ktory komponent z listy wywoluje menu
				JList list = (JList) e.getSource();
				_selectedItem = list.locationToIndex(e.getPoint());
				if (list == _pluginList) {
					getPluginPopup().show(e.getComponent(), e.getX(), e.getY());
				} else {
					getTaskPopup().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	}

	private static Logger _logger = Logger.getLogger(TaskEditionManager.class);
}