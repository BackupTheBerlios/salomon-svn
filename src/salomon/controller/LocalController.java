
package salomon.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import salomon.controller.gui.ControllerFrame;
import salomon.controller.gui.ControllerPanel;
import salomon.controller.gui.ProjectEditionManager;
import salomon.controller.gui.SplashScreen;
import salomon.controller.gui.TaskEditionManager;
import salomon.controller.gui.action.ActionManager;
import salomon.core.Config;
import salomon.core.IManagerEngine;
import salomon.core.Messages;
import salomon.core.SQLConsole;
import salomon.core.data.DBManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class LocalController  implements IController
{

	private JPanel _contentPane;

	private LocalGUIMenu _guiMenu;

	private IManagerEngine _managerEngine;

	private JMenuBar _menuBar;

	private JToolBar _toolBar;

	private ActionManager _actionManager;

	private TaskEditionManager _taskEditionManager;

	private ProjectEditionManager _projectEditionManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.controller.IController#start(salomon.core.IProjectManager)
	 */
	public void start(IManagerEngine managerEngine)
	{
		SplashScreen.show();
		try {
			DBManager.getInstance();
		} catch (SQLException e) {
			_logger.fatal("", e);
		} catch (ClassNotFoundException e) {
			_logger.error("", e);
		}
		_managerEngine = managerEngine;
		// Creates a new empty project
		_managerEngine.getProjectManager().newProject();
		_projectEditionManager = new ProjectEditionManager(_managerEngine);
		_taskEditionManager = new TaskEditionManager(_managerEngine);
		_actionManager = new ActionManager(_projectEditionManager,
				_taskEditionManager);
		_guiMenu = new LocalGUIMenu(_actionManager);
		ControllerFrame frame = new ControllerFrame();
		frame.setContentPane(getJContentPane());
		frame.setJMenuBar(getJMenuBar());
		frame.setJToolBar(getToolBar());

		_taskEditionManager.setParent(frame);
		_projectEditionManager.setParent(frame);
		_projectEditionManager.setTaskEditionManager(_taskEditionManager);

		SplashScreen.hide();
		frame.setVisible(true);
	}

	private JComponent getJContentPane()
	{
		if (_contentPane == null) {
			_contentPane = new ControllerPanel(_taskEditionManager,
					_actionManager);
		}
		return _contentPane;
	}

	private JMenuBar getJMenuBar()
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

	private void initialize()
	{

	}

	private final static class LocalGUIMenu
	{

		private JButton _btnNew;

		private JButton _btnOpen;

		private JButton _btnSave;

		private JMenuItem _itmAbout;

		private JMenuItem _itmExit;

		private JMenuItem _itmNew;

		private JMenuItem _itmOpen;

		private JMenuItem _itmSave;

		private JMenuItem _itmSQLConsole;

		private JPanel _pnlAbout;

		private JComponent _positionComponent;

		private String _resourcesDir;

		private ActionManager _actionManager;

		public LocalGUIMenu(ActionManager actionManager)
		{
			_actionManager = actionManager;
			_resourcesDir = Config.getString("RESOURCES_DIR");
		}

		JButton getBtnNew()
		{
			if (_btnNew == null) {
				_btnNew = createProjectButton(Config.getString("ICO_PROJECT_NEW")); //$NON-NLS-1$
			}
			return _btnNew;
		}

		JButton getBtnOpen()
		{
			if (_btnOpen == null) {
				_btnOpen = createProjectButton(Config.getString("ICO_PROJECT_OPEN")); //$NON-NLS-1$
			}
			return _btnOpen;
		}

		JButton getBtnSave()
		{
			if (_btnSave == null) {
				_btnSave = createProjectButton(Config.getString("ICO_PROJECT_SAVE")); //$NON-NLS-1$
			}
			return _btnSave;
		}

		JMenuItem getItmAbout()
		{
			if (_itmAbout == null) {
				_itmAbout = new JMenuItem();
				_itmAbout.setText(Messages.getString("MNU_ABOUT")); //$NON-NLS-1$
				_itmAbout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						showAboutDialog();
					}
				});
			}
			return _itmAbout;
		}

		JMenuItem getItmExit()
		{
			if (_itmExit == null) {
				_itmExit = new JMenuItem();
				_itmExit.setText(Messages.getString("MNU_EXIT")); //$NON-NLS-1$
				_itmExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						//exit();
					}
				});
			}
			return _itmExit;
		}

		JMenuItem getItmNew()
		{
			if (_itmNew == null) {
				_itmNew = new JMenuItem();
				_itmNew.setText(Messages.getString("MNU_NEW")); //$NON-NLS-1$
				_itmNew.addActionListener(_actionManager.getNewProjectAction());
			}
			return _itmNew;
		}

		JMenuItem getItmOpen()
		{
			if (_itmOpen == null) {
				_itmOpen = new JMenuItem();
				_itmOpen.setText(Messages.getString("MNU_OPEN")); //$NON-NLS-1$
				_itmOpen.addActionListener(_actionManager.getOpenProjectAction());
			}
			return _itmOpen;
		}

		JMenuItem getItmSave()
		{
			if (_itmSave == null) {
				_itmSave = new JMenuItem();
				_itmSave.setText(Messages.getString("MNU_SAVE")); //$NON-NLS-1$
				_itmSave.addActionListener(_actionManager.getSaveProjectAction());
			}
			return _itmSave;
		}

		JMenuItem getItmSQLConsole()
		{
			if (_itmSQLConsole == null) {
				_itmSQLConsole = new JMenuItem();
				_itmSQLConsole.setText(Messages.getString("MNU_CONSOLE")); //$NON-NLS-1$
				_itmSQLConsole.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						showSQLConsole();
					}
				});
			}
			return _itmSQLConsole;
		}

		JPanel getPnlAbout()
		{
			if (_pnlAbout == null) {
				if (Config.getString("OFFICIAL").equalsIgnoreCase("Y")) {
					_pnlAbout = getOfficialAbout();
				} else {
					_pnlAbout = getUnofficialAbout();
				}
			}
			return _pnlAbout;
		}

		void setPositionComponent(JComponent component)
		{
			_positionComponent = component;
		}

		/**
		 * Method show SQLConsole.
		 */
		void showSQLConsole()
		{
			new SQLConsole(false);
		}

		private JButton createProjectButton(String text)
		{
			JButton button = new JButton();
			button.setIcon(new ImageIcon(_resourcesDir + Config.FILE_SEPARATOR
					+ text));
			//      TODO:button.addActionListener(_projectListener);
			return button;
		}

		private JPanel getOfficialAbout()
		{
			if (_pnlAbout == null) {
				_pnlAbout = new JPanel();
				_pnlAbout.setLayout(new BorderLayout());
				_pnlAbout.setBorder(BorderFactory.createEmptyBorder(30, 0, 30,
						0));
				//
				// application name
				//
				JLabel lblAppName = new JLabel(new ImageIcon(_resourcesDir
						+ "/" + Config.getString("LOGO"))); //$NON-NLS-1$
				//
				// version and author panel
				//
				JPanel detailsPanel = new JPanel();
				detailsPanel.setLayout(new GridLayout(0, 2));
				JLabel lblVersionTitle = new JLabel(
						Messages.getString("TIT_VERSION")); //$NON-NLS-1$
				JLabel lblVersion = new JLabel(Messages.getString("VERSION")); //$NON-NLS-1$
				lblVersion.setForeground(Color.RED);
				JLabel lblAuthorsTitle = new JLabel(
						Messages.getString("TIT_AUTHORS")); //$NON-NLS-1$
				JLabel lblStub = new JLabel();
				JLabel lblAuthor1 = new JLabel("Nikodem Jura"); //$NON-NLS-1$
				JLabel lblAuthor2 = new JLabel("Krzysztof Rajda"); //$NON-NLS-1$
				JLabel lblThanksTitle = new JLabel(
						Messages.getString("TIT_THANKS")); //$NON-NLS-1$
				JLabel lblThanks = new JLabel("Jakub Galkowski"); //$NON-NLS-1$
				// setting components on panel
				detailsPanel.add(lblVersionTitle);
				detailsPanel.add(lblVersion);
				detailsPanel.add(lblAuthorsTitle);
				detailsPanel.add(lblAuthor1);
				detailsPanel.add(lblStub);
				detailsPanel.add(lblAuthor2);
				detailsPanel.add(lblThanksTitle);
				detailsPanel.add(lblThanks);
				detailsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0,
						0, 0));
				// adding componens
				_pnlAbout.add(lblAppName, BorderLayout.CENTER);
				_pnlAbout.add(detailsPanel, BorderLayout.SOUTH);
			}
			return _pnlAbout;
		}

		private JPanel getUnofficialAbout()
		{
			if (_pnlAbout == null) {
				_pnlAbout = new JPanel();
				_pnlAbout.setLayout(new BorderLayout());
				_pnlAbout.setBorder(BorderFactory.createEmptyBorder(30, 0, 30,
						0));
				//
				// application name
				//
				JLabel lblAppName = new JLabel(new ImageIcon(_resourcesDir
						+ "/" + Config.getString("LOGO"))); //$NON-NLS-1$
				//
				// version and author panel
				//
				JPanel detailsPanel = new JPanel();
				detailsPanel.setLayout(new GridLayout(0, 2));
				JLabel lblVersionTitle = new JLabel(
						Messages.getString("TIT_VERSION")); //$NON-NLS-1$
				JLabel lblVersion = new JLabel(Messages.getString("VERSION")); //$NON-NLS-1$
				lblVersion.setForeground(Color.RED);
				JLabel lblAuthorsTitle = new JLabel(
						Messages.getString("TIT_AUTHORS")); //$NON-NLS-1$
				JLabel lblAuthors = new JLabel(Messages.getString("AUTHORS")); //$NON-NLS-1$
				detailsPanel.add(lblVersionTitle);
				detailsPanel.add(lblVersion);
				detailsPanel.add(lblAuthorsTitle);
				detailsPanel.add(lblAuthors);
				detailsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0,
						0, 0));
				// adding componens
				_pnlAbout.add(lblAppName, BorderLayout.CENTER);
				_pnlAbout.add(detailsPanel, BorderLayout.SOUTH);
			}
			return _pnlAbout;
		}

		/** Method shows about dialog. */
		private void showAboutDialog()
		{
			JOptionPane.showMessageDialog(_positionComponent, getPnlAbout(),
					"About", JOptionPane.PLAIN_MESSAGE);
		}

	}

	private static Logger _logger = Logger.getLogger(LocalController.class);
}