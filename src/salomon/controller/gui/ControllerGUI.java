package salomon.controller.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import salomon.core.SQLConsole;
import salomon.core.event.*;
import org.apache.log4j.Logger;

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

	private JButton _btnAdd = null;

	private JButton _btnAddAll = null;

	private JButton _btnApply = null;

	private JButton _btnDown = null;

	private JButton _btnFirst = null;

	private JButton _btnLast = null;

	private JButton _btnRemove = null;

	private JButton _btnRemoveAll = null;

	private JButton _btnRun = null;

	private JButton _btnUp = null;
	
	private JButton _btnNew = null;

	private JButton _btnOpen = null;
	
	private JButton _btnSave = null;
	
	private JPanel _contentPane = null;

	private JMenuItem _itmAbout = null;

	private JMenuItem _itmExit = null;

	private JMenuItem _itmNew = null;

	private JMenuItem _itmOpen = null;

	private JMenuItem _itmSave = null;

	private JMenuItem _itmSQLConsole;

	private JList _lstPlugins = null;

	private JList _lstTasks = null;

	private ManipulationListener _manipulationListener = null;
	
	private ProjectListener _projectListener = null;

	private JMenuBar _menuBar = null;

	private JPanel _pnlInit = null;

	private JPanel _pnlManagerButtons = null;

	private JPanel _pnlPluginButtons = null;

	private JPanel _pnlPlugins = null;

	private JPanel _pnlTaskButtons = null;

	private JPanel _pnlTasks = null;

	private int _strutHeight = 10;

	private int _strutWidth = 10;

	private TaskEditionManager _taskEditionManager = null;

	private List _taskListeners = null;

	private JToolBar _toolBar = null;

	public ControllerGUI()
	{
		super();
		_taskEditionManager = new TaskEditionManager();
		_manipulationListener = new ManipulationListener();
		_projectListener = new ProjectListener();
		_taskListeners = new LinkedList();
		initialize();
	}

	public static void main(String[] args)
	{
		new ControllerGUI().setVisible(true);
	}

	public void addTaskListener(TaskListener listener)
	{
		_taskListeners.add(listener);
	}

	private void exit()
	{
		//TODO: change it
		_logger.fatal("###  Application exited  ###");
		System.exit(0);
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
			JMenu project = new JMenu("Project");
			project.add(getItmNew());
			project.add(getItmOpen());
			project.add(getItmSave());
			project.add(getItmExit());
			JMenu tools = new JMenu("Tools");
			tools.add(getItmSQLConsole());
			JMenu help = new JMenu("Help");
			help.add(getItmAbout());
			_menuBar.add(project);
			_menuBar.add(tools);
			_menuBar.add(help);
		}
		return _menuBar;
	}

	public void removeTaskListener(TaskListener listener)
	{
		_taskListeners.remove(listener);
	}

	/**
	 * Creates button with given text and standard settings
	 * 
	 * @param text
	 * @return
	 */
	private JButton createManipulationButton(String text)
	{
		JButton button = new JButton();
		button.setText(text);
		button.setFont(new Font("Dialog", Font.BOLD, 10));
		button.addActionListener(_manipulationListener);
		Dimension dim = new Dimension(50, 25);
		button.setPreferredSize(dim);
		button.setMinimumSize(dim);
		button.setMaximumSize(dim);
		return button;
	}
	
	private JButton createProjectButton(String text) {
		JButton button = new JButton();
		button.setText(text);
		button.addActionListener(_projectListener);
		button.setFont(new Font("Dialog", Font.BOLD, 10));
		return button;
	}
	
	private void fireApplyTasks(TaskEvent event)
	{
		for (Iterator iter = _taskListeners.iterator(); iter.hasNext();) {
			TaskListener listener = (TaskListener) iter.next();
			listener.applyTasks(event);
		}
	}

	private void fireRunTasks(TaskEvent event)
	{
		for (Iterator iter = _taskListeners.iterator(); iter.hasNext();) {
			TaskListener listener = (TaskListener) iter.next();
			listener.runTasks(event);
		}
	}

	/**
	 * This method initializes _btnAdd
	 * 
	 * @return JButton
	 */
	private JButton getBtnAdd()
	{
		if (_btnAdd == null) {
			_btnAdd = createManipulationButton(">");
		}
		return _btnAdd;
	}

	private JButton getBtnNew()
	{
		if (_btnNew == null) {
			_btnNew = createProjectButton("New");
		}
		return _btnNew;
	}

	private JButton getBtnOpen()
	{
		if (_btnOpen == null) {
			_btnOpen = createProjectButton("Open");
		}
		return _btnOpen;
	}
	
	private JButton getBtnSave()
	{
		if (_btnSave == null) {
			_btnSave = createProjectButton("Save");
		}
		return _btnSave;
	}
	
	/**
	 * This method initializes _btnAddAll
	 * 
	 * @return JButton
	 */
	private JButton getBtnAddAll()
	{
		if (_btnAddAll == null) {
			_btnAddAll = createManipulationButton(">>");
		}
		return _btnAddAll;
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
			_btnApply.setText("Apply");
			_btnApply.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					TaskEvent taskEvent = new TaskEvent();
					taskEvent.setTaskList(_taskEditionManager.getTasks());
					_logger.debug("applying tasks - sending TaskEvent");
					fireApplyTasks(taskEvent);
				}
			});
		}
		return _btnApply;
	}

	/**
	 * This method initializes _btnDown
	 * 
	 * @return JButton
	 */
	private JButton getBtnDown()
	{
		if (_btnDown == null) {
			_btnDown = createManipulationButton("v");
		}
		return _btnDown;
	}

	/**
	 * This method initializes _btnFirst
	 * 
	 * @return JButton
	 */
	private JButton getBtnFirst()
	{
		if (_btnFirst == null) {
			_btnFirst = createManipulationButton("^^");
		}
		return _btnFirst;
	}

	/**
	 * This method initializes _btnLast
	 * 
	 * @return JButton
	 */
	private JButton getBtnLast()
	{
		if (_btnLast == null) {
			_btnLast = createManipulationButton("vv");
		}
		return _btnLast;
	}

	/**
	 * This method initializes _btnRemove
	 * 
	 * @return JButton
	 */
	private JButton getBtnRemove()
	{
		if (_btnRemove == null) {
			_btnRemove = createManipulationButton("X");
		}
		return _btnRemove;
	}

	/**
	 * This method initializes _btnRemoveAll
	 * 
	 * @return JButton
	 */
	private JButton getBtnRemoveAll()
	{
		if (_btnRemoveAll == null) {
			_btnRemoveAll = createManipulationButton("X");
		}
		return _btnRemoveAll;
	}

	/**
	 * This method initializes _btnRun
	 * 
	 * @return JButton
	 */
	private JButton getBtnRun()
	{
		if (_btnRun == null) {
			_btnRun = new JButton();
			_btnRun.setText("Run");
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

	/**
	 * This method initializes _btnUp
	 * 
	 * @return JButton
	 */
	private JButton getBtnUp()
	{
		if (_btnUp == null) {
			_btnUp = createManipulationButton("^");
		}
		return _btnUp;
	}

	private JMenuItem getItmAbout()
	{
		if (_itmAbout == null) {
			_itmAbout = new JMenuItem();
			_itmAbout.setText("About");
			_itmAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("actionPerformed()");
				}
			});
		}
		return _itmAbout;
	}

	private JMenuItem getItmExit()
	{
		if (_itmExit == null) {
			_itmExit = new JMenuItem();
			_itmExit.setText("Exit");
			_itmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("actionPerformed()");
				}
			});
		}
		return _itmExit;
	}

	private JMenuItem getItmNew()
	{
		if (_itmNew == null) {
			_itmNew = new JMenuItem();
			_itmNew.setText("New");
			_itmNew.addActionListener(_projectListener);
		}
		return _itmNew;
	}

	private JMenuItem getItmOpen()
	{
		if (_itmOpen == null) {
			_itmOpen = new JMenuItem();
			_itmOpen.setText("Open");
			_itmOpen.addActionListener(_projectListener);
		}
		return _itmOpen;
	}

	private JMenuItem getItmSave()
	{
		if (_itmSave == null) {
			_itmSave = new JMenuItem();
			_itmSave.setText("Save");
			_itmSave.addActionListener(_projectListener);
		}
		return _itmSave;
	}

	private JMenuItem getItmSQLConsole()
	{
		if (_itmSQLConsole == null) {
			_itmSQLConsole = new JMenuItem();
			_itmSQLConsole.setText("SQLConsole");
			_itmSQLConsole.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					showSQLConsole();
				}
			});
		}
		return _itmSQLConsole;
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
			_pnlPluginButtons.add(getBtnAddAll());
			_pnlPluginButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlPluginButtons.add(getBtnAdd());
			_pnlPluginButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlPluginButtons.add(getBtnRemove());
			_pnlPluginButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlPluginButtons.add(getBtnRemoveAll());
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
			_pnlPlugins.setBorder(BorderFactory
					.createTitledBorder(null, "Plugins", TitledBorder.LEFT,
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
			_pnlTaskButtons.add(getBtnFirst());
			_pnlTaskButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlTaskButtons.add(getBtnUp());
			_pnlTaskButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlTaskButtons.add(getBtnDown());
			_pnlTaskButtons.add(Box.createVerticalStrut(_strutHeight));
			_pnlTaskButtons.add(getBtnLast());
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
			_pnlTasks.setBorder(BorderFactory.createTitledBorder(null, "Tasks",
					TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
		}
		return _pnlTasks;
	}

	/**
	 * This is the default constructor
	 */
	private JToolBar getToolBar()
	{
		if (_toolBar == null) {
			_toolBar = new JToolBar();
			_toolBar.add(getBtnNew());
			_toolBar.add(getBtnOpen());
			_toolBar.add(getBtnSave());
		}
		return _toolBar;
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
		this.setTitle("Task manager");
		this.setJMenuBar(getJMenuBar());
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				exit();
			}
		});
	}

	/**
	 *  Method show SQLConsole.
	 */
	private void showSQLConsole()
	{
		new SQLConsole(false);
	}
	/**
	 * 
	 * @author nico
	 *
	 * Class handles events from buttons, which are used to manage tasks.
	 */
	private class ManipulationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object object = e.getSource();
			if (object == _btnAdd) {
				_taskEditionManager.addTask();
			} else if (object == _btnAddAll) {
				// TODO:
			} else if (object == _btnRemove) {
				_taskEditionManager.removeTask();
			} else if (object == _btnRemoveAll) {
				//TODO:
			} else if (object == _btnUp) {
				_taskEditionManager.moveUp();
			} else if (object == _btnDown) {
				_taskEditionManager.moveDown();
			} else if (object == _btnFirst) {
				//TODO:
			} else if (object == _btnLast) {
				//TODO:
			} else {
				_logger.error("Not supported button: " + object);
			}
		}
	}
	
	/**
	 * 
	 * @author nico
	 *
	 * Class handles events from buttons and menu items, 
	 * which are used to manage projects.
	 */
	private class ProjectListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object object = e.getSource();
			if (object == _btnNew || object == _itmNew) {
				_logger.debug("New");
			} else if (object == _btnOpen || object == _itmOpen) {
				_logger.debug("Open");
			} else if (object == _btnSave || object == _itmSave) {
				_logger.debug("Save");
			} else {
				_logger.error("Not supported button: " + object);
			}
		}
	}
}