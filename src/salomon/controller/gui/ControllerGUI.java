
package salomon.controller.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import salomon.core.Config;
import salomon.core.Messages;
import salomon.core.Project;
import salomon.core.SQLConsole;
import salomon.core.data.DBManager;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBTableName;
import salomon.core.event.ProjectEvent;
import salomon.core.event.ProjectListener;
import salomon.core.event.TaskEvent;
import salomon.core.event.TaskListener;

/*
 * Created on 2004-05-03
 *  
 */
/**
 * @author nico
 *  
 */
public class ControllerGUI extends JFrame
{
	private static Logger _logger = Logger.getLogger(ControllerGUI.class);

	private JButton _btnApply = null;

	private JButton _btnRun = null;

	private JPanel _contentPane = null;

	private GUIButtons _guiButtons = null;

	private GUIMenu _guiMenu = null;

	private JList _lstPlugins = null;

	private JList _lstTasks = null;

	private JMenuBar _menuBar = null;

	private JPanel _pnlInit = null;

	private JPanel _pnlManagerButtons = null;

	private JPanel _pnlPluginButtons = null;

	private JPanel _pnlPlugins = null;

	private JPanel _pnlTaskButtons = null;

	private JPanel _pnlTasks = null;

	private JPanel _pnlProjectProperties = null;

	private List _projectListeners = null;

	private String _resourcesDir = null;

	private JWindow _splashScreen = null;

	private int _strutHeight = 10;

	private int _strutWidth = 10;

	private TaskEditionManager _taskEditionManager = null;

	private List _taskListeners = null;

	private JToolBar _toolBar = null;

	private JTextField _txtProjectName = null;

	private JTextField _txtProjectInfo = null;

	public ControllerGUI()
	{
		super();
		_resourcesDir = Config.getString("RESOURCES_DIR");
		createSplashScreen();
		long startTime = System.currentTimeMillis();
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				showSplashScreen();
			}
		});
		_taskEditionManager = new TaskEditionManager();
		// event handlers
		_taskListeners = new LinkedList();
		_projectListeners = new LinkedList();
		_guiButtons = new GUIButtons();
		_guiMenu = new GUIMenu(this);
		initialize();
		_taskEditionManager.setPositionComponent(_contentPane);
		_guiMenu.setPositionComponent(_contentPane);
		_guiButtons.setEditionManager(_taskEditionManager);
		//
		// waiting configured time
		//
		long currentTime = System.currentTimeMillis();
		long splashTime = 0;
		long waitingTime = 0;
		try {
			splashTime = Long.parseLong(Config.getString("SPLASH_TIME")) * 1000;
			waitingTime = splashTime - (currentTime - startTime);
		} catch (NumberFormatException e) {
			_logger.fatal("", e);
		}
		_logger.debug("waitingtime: " + waitingTime);
		if (waitingTime > 0) {
			try {
				Thread.sleep(waitingTime);
			} catch (InterruptedException e) {
				_logger.fatal("", e);
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				hideSplashScreen();
			}
		});
	}

	public void addProjectListener(ProjectListener listener)
	{
		_projectListeners.add(listener);
	}

	public void addTaskListener(TaskListener listener)
	{
		_taskListeners.add(listener);
	}

	/**
	 * Called after project save/load/creation
	 * 
	 * @param project
	 */
	public void refreshGui(Project project)
	{
		_taskEditionManager.reloadTasks(project.getManagerEngine()
				.getTasksManager().getTasks());
	}

	/**
	 * Shows dialog which enables to initialize project settings.
	 * 
	 * @param project
	 */
	public void setProjectProperties(Project project)
	{
		if (_pnlProjectProperties == null) {
			_pnlProjectProperties = new JPanel();
			_pnlProjectProperties.setLayout(new GridLayout(0, 2));
			_txtProjectName = new JTextField();
			_txtProjectInfo = new JTextField();
			_pnlProjectProperties.add(new JLabel("Project name"));
			_pnlProjectProperties.add(_txtProjectName);
			_pnlProjectProperties.add(new JLabel("Project info"));
			_pnlProjectProperties.add(_txtProjectInfo);
		}
		String name = project.getName();
		String info = project.getInfo();
		_txtProjectName.setText(name == null ? "" : name);
		_txtProjectInfo.setText(info == null ? "" : info);
		JOptionPane.showMessageDialog(_contentPane, _pnlProjectProperties,
				"Enter project properties", JOptionPane.INFORMATION_MESSAGE);
		project.setName(_txtProjectName.getText());
		project.setInfo(_txtProjectInfo.getText());
	}

	/**
	 * This method initializes _menuBar
	 * 
	 * @return
	 */
	public JMenuBar getJMenuBar()
	{
		if (_menuBar == null) {
			_menuBar = new JMenuBar();
			JMenu project = new JMenu(Messages.getString("MNU_PROJECT")); //$NON-NLS-1$
			project.add(_guiMenu.getItmNew());
			project.add(_guiMenu.getItmOpen());
			project.add(_guiMenu.getItmSave());
			project.addSeparator();
			project.add(_guiMenu.getItmExit());
			JMenu tools = new JMenu(Messages.getString("MNU_TOOLS")); //$NON-NLS-1$
			tools.add(_guiMenu.getItmSQLConsole());
			JMenu help = new JMenu(Messages.getString("MNU_HELP")); //$NON-NLS-1$			
			help.add(_guiMenu.getItmAbout());
			_menuBar.add(project);
			_menuBar.add(tools);
			_menuBar.add(help);
		}
		return _menuBar;
	}

	public void removeProjectListener(ProjectListener listener)
	{
		_projectListeners.remove(listener);
	}

	public void removeTaskListener(TaskListener listener)
	{
		_taskListeners.remove(listener);
	}

	public void showErrorMessage(String msg)
	{
		JOptionPane.showMessageDialog(_contentPane, msg, Messages
				.getString("TIT_ERROR"), JOptionPane.ERROR_MESSAGE);
	}

	public void showInfoMessage(String msg)
	{
		JOptionPane.showMessageDialog(_contentPane, msg, Messages
				.getString("TIT_INFO"), JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean showQuestionMessage(String title, String msg)
	{
		int retVal = JOptionPane.showConfirmDialog(_contentPane, msg, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		return (retVal == JOptionPane.YES_OPTION);
	}

	public boolean showWarningMessage(String msg)
	{
		int retVal = JOptionPane.showConfirmDialog(_contentPane, msg, Messages
				.getString("TIT_WARN"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		return (retVal == JOptionPane.YES_OPTION);
	}

	/**
	 * This method initializes _btnRun
	 * 
	 * @return JButton
	 */
	JButton getBtnRun()
	{
		if (_btnRun == null) {
			_btnRun = new JButton();
			_btnRun.setText(Messages.getString("BTN_RUN")); //$NON-NLS-1$
			_btnRun.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					TaskEvent taskEvent = new TaskEvent();
					fireRunTasks(taskEvent);
				}
			});
		}
		return _btnRun;
	}

	private void createSplashScreen()
	{
		ImageIcon image = new ImageIcon(_resourcesDir + "/"
				+ Config.getString("SPLASH_SCREEN"));
		SplashLabel splashLabel = new SplashLabel(image);
		_splashScreen = new JWindow();
		_splashScreen.setLocation(splashLabel.getLocation());
		_splashScreen.getContentPane().add(splashLabel);
		_splashScreen.pack();
	}

	private void exit()
	{
		//TODO: change it
		_logger.fatal("###  Application exited  ###"); //$NON-NLS-1$
		System.exit(0);
	}

	private void fireApplyTasks(TaskEvent event)
	{
		for (Iterator iter = _taskListeners.iterator(); iter.hasNext();) {
			TaskListener listener = (TaskListener) iter.next();
			listener.applyTasks(event);
		}
	}

	void fireLoadProject(ProjectEvent event)
	{
		int projectID = chooseProject();
		if (projectID > 0) {
			event.setProjectID(projectID);
			for (Iterator iter = _projectListeners.iterator(); iter.hasNext();) {
				ProjectListener listener = (ProjectListener) iter.next();
				listener.loadProject(event);
			}
		}
	}

	private int chooseProject()
	{
		int projectID = 0;
		DBTableName[] tableNames = {new DBTableName("projects")};
		DBColumnName[] columnNames = {
				new DBColumnName(tableNames[0], "project_id", "Id"),
				new DBColumnName(tableNames[0], "name", "Name"),
				new DBColumnName(tableNames[0], "info", "Info")};
		// executing query
		ResultSet resultSet = null;
		try {
			resultSet = DBManager.getInstance().select(columnNames, tableNames,
					null);
			JTable projectTable = null;
			projectTable = SQLConsole.createResultTable(resultSet);			
			projectID = showProjectList(projectTable);
		} catch (Exception e) {
			_logger.fatal("", e);
			showErrorMessage("Cannot load project list.");
		}
		return projectID;
	}

	private int showProjectList(JTable table)
	{
		int projectID = 0;
		JScrollPane panel = new JScrollPane();
		panel.setViewportView(table);
		Dimension dim = new Dimension(250, 200);
		panel.setMaximumSize(dim);
		panel.setPreferredSize(dim);		
		int result = JOptionPane.showConfirmDialog(_contentPane, panel,
				"Choose project", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);		
		if (result == JOptionPane.OK_OPTION) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				projectID = ((Integer) table.getValueAt(selectedRow, 0))
						.intValue();
			}
		}
		return projectID;
	}

	void fireNewProject(ProjectEvent event)
	{
		for (Iterator iter = _projectListeners.iterator(); iter.hasNext();) {
			ProjectListener listener = (ProjectListener) iter.next();
			listener.newProject(event);
		}
	}

	private void fireRunTasks(TaskEvent event)
	{
		for (Iterator iter = _taskListeners.iterator(); iter.hasNext();) {
			TaskListener listener = (TaskListener) iter.next();
			listener.runTasks(event);
		}
	}

	void fireSaveProject(ProjectEvent event)
	{
		event.setTaskList(_taskEditionManager.getTasks());
		for (Iterator iter = _projectListeners.iterator(); iter.hasNext();) {
			ProjectListener listener = (ProjectListener) iter.next();
			listener.saveProject(event);
		}
	}

	/**
	 * This method initializes _btnApply
	 * 
	 * @return JButton
	 */
	private JButton getBtnApply()
	{
		if (_btnApply == null) {
			_btnApply = new JButton();
			_btnApply.setText(Messages.getString("BTN_APPLY")); //$NON-NLS-1$
			_btnApply.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					TaskEvent taskEvent = new TaskEvent();
					taskEvent.setTaskList(_taskEditionManager.getTasks());
					_logger.debug("applying tasks - sending TaskEvent"); //$NON-NLS-1$
					fireApplyTasks(taskEvent);
				}
			});
		}
		return _btnApply;
	}

	/**
	 * This method initializes _contentPane
	 * 
	 * @return JPanel
	 */
	private JPanel getJContentPane()
	{
		if (_contentPane == null) {
			_contentPane = new JPanel();
			_contentPane.setLayout(new BorderLayout());
			_contentPane.add(getToolBar(), BorderLayout.NORTH);
			_contentPane.add(getPnlManagerButtons(), BorderLayout.SOUTH);
			_contentPane.add(getPnlInit(), BorderLayout.CENTER);
		}
		return _contentPane;
	}

	/**
	 * This method initializes _lstPlugins
	 * 
	 * @return JList
	 */
	private JList getLstPlugins()
	{
		if (_lstPlugins == null) {
			_lstPlugins = _taskEditionManager.getPluginList();
			_lstPlugins.setBorder(BorderFactory.createLoweredBevelBorder());
			_lstPlugins.setPreferredSize(new Dimension(100, 200));
		}
		return _lstPlugins;
	}

	/**
	 * This method initializes _lstTasks
	 * 
	 * @return JList
	 */
	private JList getLstTasks()
	{
		if (_lstTasks == null) {
			_lstTasks = _taskEditionManager.getTaskList();
			_lstTasks.setBorder(BorderFactory.createLoweredBevelBorder());
			_lstTasks.setPreferredSize(new Dimension(100, 200));
		}
		return _lstTasks;
	}

	private JPanel getPnlInit()
	{
		if (_pnlInit == null) {
			_pnlInit = new JPanel();
			_pnlInit.setLayout(new GridLayout(1, 2));
			_pnlInit.add(getPnlPlugins());
			_pnlInit.add(getPnlTasks());
		}
		return _pnlInit;
	}

	/**
	 * This method initializes _pnlManagerButtons
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlManagerButtons()
	{
		if (_pnlManagerButtons == null) {
			_pnlManagerButtons = new JPanel();
			_pnlManagerButtons.setLayout(new BoxLayout(_pnlManagerButtons,
					BoxLayout.X_AXIS));
			_pnlManagerButtons.add(Box.createHorizontalGlue());
			_pnlManagerButtons.add(getBtnApply());
			_pnlManagerButtons.add(Box.createHorizontalStrut(_strutWidth));
			_pnlManagerButtons.add(getBtnRun());
			_pnlManagerButtons.add(Box.createHorizontalGlue());
		}
		return _pnlManagerButtons;
	}

	/**
	 * This method initializes _pnlPluginButtons
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlPluginButtons()
	{
		if (_pnlPluginButtons == null) {
			_pnlPluginButtons = new JPanel();
			_pnlPluginButtons.setLayout(new BoxLayout(_pnlPluginButtons,
					BoxLayout.Y_AXIS));
			_pnlPluginButtons.add(Box.createVerticalGlue());
			_pnlPluginButtons.add(_guiButtons.getBtnAddAll());
			_pnlPluginButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlPluginButtons.add(_guiButtons.getBtnAdd());
			_pnlPluginButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlPluginButtons.add(_guiButtons.getBtnRemove());
			_pnlPluginButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlPluginButtons.add(_guiButtons.getBtnRemoveAll());
			_pnlPluginButtons.add(Box.createVerticalGlue());
		}
		return _pnlPluginButtons;
	}

	/**
	 * This method initializes _pnlPlugins
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlPlugins()
	{
		if (_pnlPlugins == null) {
			_pnlPlugins = new JPanel();
			_pnlPlugins.setLayout(new BorderLayout());
			_pnlPlugins.add(getPnlPluginButtons(), BorderLayout.EAST);
			_pnlPlugins.add(getLstPlugins(), BorderLayout.CENTER);
			_pnlPlugins.setBorder(BorderFactory.createTitledBorder(null,
					Messages.getString("TIT_PLUGINS"), TitledBorder.LEFT, //$NON-NLS-1$
					TitledBorder.DEFAULT_POSITION));
		}
		return _pnlPlugins;
	}

	/**
	 * This method initializes _pnlTaskButtons
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlTaskButtons()
	{
		if (_pnlTaskButtons == null) {
			_pnlTaskButtons = new JPanel();
			_pnlTaskButtons.setLayout(new BoxLayout(_pnlTaskButtons,
					BoxLayout.Y_AXIS));
			_pnlTaskButtons.add(Box.createVerticalGlue());
			_pnlTaskButtons.add(_guiButtons.getBtnFirst());
			_pnlTaskButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlTaskButtons.add(_guiButtons.getBtnUp());
			_pnlTaskButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlTaskButtons.add(_guiButtons.getBtnDown());
			_pnlTaskButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlTaskButtons.add(_guiButtons.getBtnLast());
			_pnlTaskButtons.add(Box.createVerticalGlue());
		}
		return _pnlTaskButtons;
	}

	/**
	 * This method initializes _pnlTasks
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlTasks()
	{
		if (_pnlTasks == null) {
			_pnlTasks = new JPanel();
			_pnlTasks.setLayout(new BorderLayout());
			_pnlTasks.add(getPnlTaskButtons(), BorderLayout.EAST);
			_pnlTasks.add(getLstTasks(), BorderLayout.CENTER);
			_pnlTasks.setBorder(BorderFactory.createTitledBorder(null, Messages
					.getString("TIT_TASKS"), //$NON-NLS-1$
					TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
		}
		return _pnlTasks;
	}

	private JToolBar getToolBar()
	{
		if (_toolBar == null) {
			_toolBar = new JToolBar();
			_toolBar.add(_guiMenu.getBtnNew());
			_toolBar.add(_guiMenu.getBtnOpen());
			_toolBar.add(_guiMenu.getBtnSave());
		}
		return _toolBar;
	}

	private void hideSplashScreen()
	{
		_splashScreen.setVisible(false);
		_splashScreen = null;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setContentPane(getJContentPane());
		this.setSize(600, 500);
		//
		// center frame
		//
		Point location = new Point();
		location.x = (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2;
		location.y = (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2;
		this.setLocation(location);
		this.setTitle(Messages.getString("APP_NAME")); //$NON-NLS-1$
		this.setJMenuBar(getJMenuBar());
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				exit();
			}
		});
		_logger.debug("initialize end");
	}

	private void showSplashScreen()
	{
		_splashScreen.setVisible(true);
	}

	/**
	 * @author nico
	 * 
	 * Class represents splash screen label. It allows to place version and
	 * other information at the scree
	 *  
	 */
	private class SplashLabel extends JLabel
	{
		Point _location = null;

		public SplashLabel(ImageIcon image)
		{
			super(image);
			_logger.debug("image: " + image);
			_location = new Point();
			_location.x = (Toolkit.getDefaultToolkit().getScreenSize().width - image
					.getIconWidth()) / 2;
			_location.y = (Toolkit.getDefaultToolkit().getScreenSize().height - image
					.getIconHeight()) / 2;
		}

		public Point getLocation()
		{
			return _location;
		}

		public void paint(Graphics g)
		{
			super.paint(g);
			Color oldColor = g.getColor();
			Font oldFont = g.getFont();
			Color newColor = Color.BLUE;
			Font newFont = new Font("Dialog", Font.BOLD, 15);
			g.setColor(newColor);
			g.setFont(newFont);
			String version = Messages.getString("TIT_VERSION") + ": "
					+ Messages.getString("VERSION");
			g.drawString(version, 255, 260);
			// setting back old color and font
			g.setColor(oldColor);
			g.setFont(oldFont);
		}
	}
}