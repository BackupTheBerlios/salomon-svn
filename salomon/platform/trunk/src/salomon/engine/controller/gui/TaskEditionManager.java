/*
 * Created on 2004-05-03
 *
 */

package salomon.engine.controller.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.controller.gui.action.ActionManager;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.Messages;
import salomon.engine.platform.plugin.PluginLoader;
import salomon.platform.task.ITask;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.gui.Utils;

/**
 * Class used to manage with tasks editing. It enables creating and configuring
 * tasks and a queue of tasks.
 */
public final class TaskEditionManager
{

	private ActionManager _actionManager;

	private JComponent _editPluginPanel;

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

	private JTextField _txtPluginInfo;

	private JTextField _txtPluginLocation;

	private JTextField _txtPluginName;

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
	}

	public void addPlugin()
	{
		//TODO: change it
		getEditPluginPanel();
		_txtPluginName.setText("");
		_txtPluginLocation.setText("");
		_txtPluginInfo.setText("");
		int retVal = JOptionPane.showConfirmDialog(_parent,
				getEditPluginPanel(), Messages.getString("MNU_ADD_PLUGIN"),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (retVal == JOptionPane.OK_OPTION) {
			URL url = null;
			try {
				url = new URL(_txtPluginLocation.getText());
			} catch (MalformedURLException e) {
				_logger.fatal("", e);
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
				return;
			}
			Description desc = new Description();
			desc.setName(_txtPluginName.getText());
			desc.setLocation(url);
			desc.setInfo(_txtPluginInfo.getText());
			if (_managerEngine.getPluginManager().savePlugin(desc)) {
				refresh();
			} else {
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
			}
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
               // TODO: change it !!!
                int pluginID = localPlugin.getPluginDescription().getPluginID();                
                plugin.getDescription().setPluginID(pluginID);                
				TaskGUI taskGUI = new TaskGUI();
				taskGUI.setPlugin(plugin);
				taskGUI.setName(getTaskName());
				_taskListModel.addElement(taskGUI);
			} catch (Exception e) {
				_logger.fatal("", e); //$NON-NLS-1$				
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_LOAD_PLUGIN"));
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
			LocalPlugin localPlugin = new LocalPlugin((Description) iter.next());
			_logger.debug("adding plugin:" + localPlugin);
			_logger.debug("description:" + localPlugin.getPluginDescription());
			_pluginListModel.addElement(localPlugin);
		}
		_logger.debug("reloading tasks");
		_taskListModel.removeAllElements();
		ITask[] tasks = _managerEngine.getTasksManager().getTasks();
        for (ITask task : tasks) {
            _logger.debug("adding task");
            _taskListModel.addElement(new TaskGUI(task));   
        }
	}

	public void removePlugin()
	{
		if (Utils.showQuestionMessage(Messages.getString("TIT_WARN"),
				Messages.getString("TXT_REMOVE_PLUGIN_QUESTION"))) {
			Description desc = ((LocalPlugin) _pluginListModel.get(_selectedItem)).getPluginDescription();
			if (_managerEngine.getPluginManager().removePlugin(desc)) {
				_pluginListModel.remove(_selectedItem);
			} else {
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
			}
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
			TaskGUI task = (TaskGUI) _taskListModel.remove(index);
			System.out.println("plugin = " + task); //$NON-NLS-1$
		} else {
			_logger.warn("Invalid index. Wrong list selected?"); //$NON-NLS-1$
		}
	}

	public void runTasks()
	{
		_managerEngine.getTasksManager().start();
	}

	public void savePlugin()
	{
		Description desc = ((LocalPlugin) _pluginListModel.get(_selectedItem)).getPluginDescription();
		// to initialize components
		getEditPluginPanel();
		_txtPluginName.setText(desc.getName());
		_txtPluginLocation.setText(desc.getLocation().toString());
		_txtPluginInfo.setText(desc.getInfo());
		int retVal = JOptionPane.showConfirmDialog(_parent,
				getEditPluginPanel(), Messages.getString("MNU_EDIT_PLUGIN"),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (retVal == JOptionPane.OK_OPTION) {
			URL url = null;
			try {
				url = new URL(_txtPluginLocation.getText());
			} catch (MalformedURLException e) {
				_logger.fatal("", e);
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
				return;
			}
			desc.setName(_txtPluginName.getText());
			desc.setLocation(url);
			desc.setInfo(_txtPluginInfo.getText());
			if (_managerEngine.getPluginManager().savePlugin(desc)) {
				//refresh();
			} else {
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
			}
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

	private JComponent getEditPluginPanel()
	{
		if (_editPluginPanel == null) {
			_editPluginPanel = new PluginEditPanel();
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
					_actionManager.getSavePluginAction()); //$NON-NLS-1$
			itmEdit.setText(Messages.getString("MNU_EDIT_PLUGIN"));
			JMenuItem itmRemove = new JMenuItem(
					_actionManager.getRemovePluginAction()); //$NON-NLS-1$
			itmRemove.setText(Messages.getString("MNU_REMOVE_PLUGIN"));
			_pluginPopup.add(itmAdd);
			_pluginPopup.add(itmEdit);
			_pluginPopup.add(itmRemove);
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
        comp.setSize(prefDim);
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
				settingComponent.getComponent(inputSettings, null), //TODO: change it!!!
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

		Description _pluginDescription;

		private IPlugin _plugin;

		public LocalPlugin(Description pluginDescription)
		{
			_pluginDescription = pluginDescription;
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
				_plugin = PluginLoader.loadPlugin(_pluginDescription.getLocation());
			}
			return _plugin;
		}

		public Description getPluginDescription()
		{
			return _pluginDescription;
		}

		public String toString()
		{
			//TODO: change it to name:version or sth
			String path = _pluginDescription.getLocation().getPath();
			int index = path.lastIndexOf('/');

			return path.substring(index + 1);
		}
	}

	private class PluginEditPanel extends JPanel
	{

		private Dimension _labelDim = new Dimension(100, 20);

		private Dimension _textDim = new Dimension(250, 20);

		public PluginEditPanel()
		{
			_txtPluginName = new JTextField();
			_txtPluginLocation = new JTextField();
			_txtPluginInfo = new JTextField();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(createLabeledText(Messages.getString("TXT_PLUGIN_NAME"),
					_txtPluginName));
			this.add(createLabeledText(
					Messages.getString("TXT_PLUGIN_LOCATION"),
					_txtPluginLocation));
			this.add(createLabeledText(Messages.getString("TXT_PLUGIN_INFO"),
					_txtPluginInfo));
		}

		private Box createLabeledText(String labelText, JTextField textField)
		{
			Box box = Box.createHorizontalBox();
			JLabel label = new JLabel(labelText);
			label.setPreferredSize(_labelDim);
			label.setMinimumSize(_labelDim);
			label.setMaximumSize(_labelDim);
			textField.setPreferredSize(_textDim);
			textField.setMinimumSize(_textDim);
			textField.setMaximumSize(_textDim);
			box.add(label);
			box.add(textField);

			return box;
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
				if (_selectedItem >= 0) {
					if (list == _pluginList) {
						getPluginPopup().show(e.getComponent(), e.getX(),
								e.getY());
					} else {
						getTaskPopup().show(e.getComponent(), e.getX(),
								e.getY());
					}
				}
			}
		}
	}

	private static Logger _logger = Logger.getLogger(TaskEditionManager.class);
}