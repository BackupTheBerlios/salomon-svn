/*
 * Created on 2004-05-27
 *
 */

package salomon.controller.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import salomon.core.Config;
import salomon.core.Messages;
import salomon.core.SQLConsole;
import salomon.core.event.ProjectEvent;

/**
 * @author nico
 *  
 */
public class GUIMenu
{
	private static Logger _logger = Logger.getLogger(GUIMenu.class);

	private JButton _btnNew = null;

	private JButton _btnOpen = null;

	private JButton _btnSave = null;

	private JMenuItem _itmAbout = null;

	private JMenuItem _itmExit = null;

	private JMenuItem _itmNew = null;

	private JMenuItem _itmOpen = null;

	private JMenuItem _itmSave = null;

	private JMenuItem _itmSQLConsole;

	private JPanel _pnlAbout = null;

	private JComponent _positionComponent = null;

	private ProjectListener _projectListener = null;

	private String _resourcesDir = null;
	
	private ControllerGUI _controllerGui = null; 

	public GUIMenu(ControllerGUI controllerGUI)
	{
		_controllerGui = controllerGUI;
		_resourcesDir = Config.getString("RESOURCES_DIR");
		_projectListener = new ProjectListener();		
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
			_itmNew.addActionListener(_projectListener);
		}
		return _itmNew;
	}

	JMenuItem getItmOpen()
	{
		if (_itmOpen == null) {
			_itmOpen = new JMenuItem();
			_itmOpen.setText(Messages.getString("MNU_OPEN")); //$NON-NLS-1$
			_itmOpen.addActionListener(_projectListener);
		}
		return _itmOpen;
	}

	JMenuItem getItmSave()
	{
		if (_itmSave == null) {
			_itmSave = new JMenuItem();
			_itmSave.setText(Messages.getString("MNU_SAVE")); //$NON-NLS-1$
			_itmSave.addActionListener(_projectListener);
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
		button.setIcon(new ImageIcon(_resourcesDir + Config.FILE_SEPARATOR + text));
		button.addActionListener(_projectListener);		
		return button;
	}

	private JPanel getOfficialAbout()
	{
		if (_pnlAbout == null) {
			_pnlAbout = new JPanel();
			_pnlAbout.setLayout(new BorderLayout());
			_pnlAbout.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
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
			JLabel lblVersionTitle = new JLabel(Messages
					.getString("TIT_VERSION")); //$NON-NLS-1$
			JLabel lblVersion = new JLabel(Messages.getString("VERSION")); //$NON-NLS-1$
			lblVersion.setForeground(Color.RED);
			JLabel lblAuthorsTitle = new JLabel(Messages
					.getString("TIT_AUTHORS")); //$NON-NLS-1$
			JLabel lblStub = new JLabel();
			JLabel lblAuthor1 = new JLabel("Nikodem Jura"); //$NON-NLS-1$
			JLabel lblAuthor2 = new JLabel("Krzysztof Rajda"); //$NON-NLS-1$
			JLabel lblThanksTitle = new JLabel(Messages.getString("TIT_THANKS")); //$NON-NLS-1$
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
			detailsPanel
					.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
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
			_pnlAbout.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
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
			JLabel lblVersionTitle = new JLabel(Messages
					.getString("TIT_VERSION")); //$NON-NLS-1$
			JLabel lblVersion = new JLabel(Messages.getString("VERSION")); //$NON-NLS-1$
			lblVersion.setForeground(Color.RED);
			JLabel lblAuthorsTitle = new JLabel(Messages
					.getString("TIT_AUTHORS")); //$NON-NLS-1$
			JLabel lblAuthors = new JLabel(Messages.getString("AUTHORS")); //$NON-NLS-1$
			detailsPanel.add(lblVersionTitle);
			detailsPanel.add(lblVersion);
			detailsPanel.add(lblAuthorsTitle);
			detailsPanel.add(lblAuthors);
			detailsPanel
					.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
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

	/**
	 * 
	 * @author nico
	 * 
	 * Class handles events from buttons and menu items, which are used to
	 * manage projects.
	 */
	private class ProjectListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object object = e.getSource();
			if (object == _btnNew || object == _itmNew) {
				_logger.debug("New"); //$NON-NLS-1$
				_controllerGui.fireNewProject(new ProjectEvent());
			} else if (object == _btnOpen || object == _itmOpen) {
				_logger.debug("Open"); //$NON-NLS-1$
				_controllerGui.fireLoadProject(new ProjectEvent());
			} else if (object == _btnSave || object == _itmSave) {
				_logger.debug("Save"); //$NON-NLS-1$
				_controllerGui.fireSaveProject(new ProjectEvent());
			} else {
				_logger.error("Not supported button: " + object); //$NON-NLS-1$
			}
		}
	}
}