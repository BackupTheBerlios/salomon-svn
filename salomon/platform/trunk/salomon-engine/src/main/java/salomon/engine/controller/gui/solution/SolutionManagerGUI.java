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

package salomon.engine.controller.gui.solution;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.Starter;
import salomon.engine.Config;
import salomon.engine.Messages;
import salomon.engine.controller.gui.ControllerFrame;
import salomon.engine.controller.gui.common.SearchFileFilter;
import salomon.engine.controller.gui.common.TreeFileChooser;
import salomon.engine.controller.gui.common.action.ActionManager;
import salomon.engine.controller.gui.task.SettingsDialog;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.plugin.PlatformUtil;
import salomon.engine.solution.ISolution;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.solution.Solution;
import salomon.engine.solution.SolutionInfo;
import salomon.engine.solution.SolutionManager;
import salomon.engine.solution.event.SolutionEvent;
import salomon.engine.solution.event.SolutionListener;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.Utils;
import salomon.util.gui.validation.IComponentFactory;
import salomon.util.gui.validation.IValidationModel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResultModel;

/**
 * Class used to manage with solutions editing.
 */
public final class SolutionManagerGUI
{
    private final static String DB_DESC = "Database files";

    private final static String DB_EXT = "gdb";

    private static final Logger LOGGER = Logger.getLogger(SolutionManagerGUI.class);

    /**
     * 
     * @uml.property name="_actionManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ActionManager _actionManager;

    private JButton _btnChooseDB;

    private JButton _btnExit;

    private JButton _btnNew;

    private JButton _btnOpen;

    private JButton _btnTestConnect;

    private JComboBox _comboSolutionList;

    private TreeFileChooser _dbFileChooser;

    private ControllerFrame _parent;

    private JPanel _pnlSolutionController;

    private JPanel _pnlSolutionProperties;

    private SettingsDialog _settingsDialog;

    private JFrame _solutionChooserFrame;

    private List<SolutionListener> _solutionListeners;

    /**
     * 
     * @uml.property name="_solutionManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private SolutionManager _solutionManager;

    private Solution[] _solutions;

    private JFrame _solutionViewerFrame;

    private JTextField _txtDBPath;

    private JTextField _txtHostname;

    private JTextField _txtPasswd;

    private JTextField _txtSolutionCrDate;

    private JTextArea _txtSolutionInfo;

    private JTextField _txtSolutionLastMod;

    private JTextField _txtSolutionName;

    private JTextField _txtUsername;

    /**
     */
    public SolutionManagerGUI(ISolutionManager solutionManager)
    {
        _solutionManager = (SolutionManager) solutionManager;
        _solutionListeners = new LinkedList<SolutionListener>();
        initComponents();
    }

    public void addSolutionListener(SolutionListener listener)
    {
        _solutionListeners.add(listener);
    }

    public void editSolution()
    {
        ISolution solution = null;
        try {
            solution = _solutionManager.getCurrentSolution();
            setSolutionProperties(solution);

            // informing listeners
            fireSolutionModified(new SolutionEvent(
                    (SolutionInfo) solution.getInfo()));
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_EDIT_SOLUTION"));
        }
    }

    /**
     * This method initializes _btnExit
     * 
     * @return JButton
     */
    public JButton getBtnExit()
    {
        if (_btnExit == null) {
            _btnExit = this.createActionButton(_actionManager.getExitAction(),
                    Messages.getString("BTN_EXIT"));
            _btnExit.setMnemonic('e');
        }
        return _btnExit;
    }

    /**
     * This method initializes _btnNew
     * 
     * @return JButton
     */
    public JButton getBtnNew()
    {
        if (_btnNew == null) {
            _btnNew = this.createActionButton(
                    _actionManager.getNewSolutionAction(),
                    Messages.getString("BTN_NEW"));
            _btnNew.setMnemonic('n');
        }
        return _btnNew;
    }

    /**
     * This method initializes _btnOpen
     * 
     * @return JButton
     */
    public JButton getBtnOpen()
    {
        if (_btnOpen == null) {
            _btnOpen = this.createActionButton(
                    _actionManager.getOpenSolutionAction(),
                    Messages.getString("BTN_OPEN"));
            _btnOpen.setMnemonic('o');
        }
        return _btnOpen;
    }

    public JPanel getPanel()
    {
        return _pnlSolutionController;
    }

    /**
     * Returns the solutionManager.
     * @return The solutionManager
     */
    public SolutionManager getSolutionManager()
    {
        return _solutionManager;
    }

    public JPanel getSolutionsPanel()
    {
        JPanel panel = new JPanel();
        panel.add(getSolutionsCombo());
        _comboSolutionList.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createTitledBorder(Messages.getString("TIT_SOLUTIONS")));

        return panel;
    }

    public void initSolutionFrame()
    {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        JPanel pnlManagerButtons = new JPanel();
        pnlManagerButtons.setLayout(new BoxLayout(pnlManagerButtons,
                BoxLayout.Y_AXIS));
        pnlManagerButtons.add(Box.createVerticalGlue());
        pnlManagerButtons.add(getBtnOpen());
        pnlManagerButtons.add(getBtnNew());
        pnlManagerButtons.add(Box.createVerticalStrut(10));
        pnlManagerButtons.add(getBtnExit());
        pnlManagerButtons.add(Box.createVerticalGlue());

        panel.setLayout(new BorderLayout());
        panel.add(getSolutionsPanel(), BorderLayout.CENTER);
        panel.add(pnlManagerButtons, BorderLayout.EAST);

        _solutionChooserFrame = new JFrame(Messages.getString("TIT_SOLUTIONS"));
        _solutionChooserFrame.setResizable(false);

        _solutionChooserFrame.getContentPane().add(panel);
        _solutionChooserFrame.pack();

        _solutionChooserFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                Starter.exit();
            }
        });

        Point location = new Point();
        location.x = (Toolkit.getDefaultToolkit().getScreenSize().width - _solutionChooserFrame.getWidth()) / 2;
        location.y = (Toolkit.getDefaultToolkit().getScreenSize().height - _solutionChooserFrame.getHeight()) / 2;
        _solutionChooserFrame.setLocation(location);
    }

    public void newSolution()
    {
        try {
            ISolution solution = _solutionManager.createSolution();
            if (setSolutionProperties(solution)) {
                _solutionManager.addSolution(solution);
                _solutions = (Solution[]) ((SolutionManager) _solutionManager).getSolutions();
                _comboSolutionList.addItem(solution);
                _comboSolutionList.setSelectedItem(solution);
                _comboSolutionList.repaint();

                // informing listeners
                fireSolutionCreated(new SolutionEvent(
                        (SolutionInfo) solution.getInfo()));
                // forcing panel to be rebuilt
                _pnlSolutionProperties = null;
            }
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_CREATE_SOLUTION"));
        }
    }

    public void openSolution()
    {
        boolean approved = false;

        // if _solutionChooserFrame is null
        // the dialog to choose solution should be shown
        if (_solutionChooserFrame == null) {
            int result = JOptionPane.showConfirmDialog(_parent,
                    getSolutionsPanel(), "Choose solution",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            approved = (result == JOptionPane.OK_OPTION);
        } else {
            // if mehod is called during application starting
            // solution was choosen
            approved = true;
        }

        if (approved) {

            int selectedRow = _comboSolutionList.getSelectedIndex();
            final int solutionID = _solutions[selectedRow].getInfo().getId();
            LOGGER.info("chosen solution: " + solutionID);
            // FIXME - do it in better way
            Solution solution = null;
            try {
                solution = (Solution) _solutionManager.getSolution(solutionID);
                // forcing connecting to external database
                try {
                    solution.getDataEngine();
                } catch (Exception e) {
                    LOGGER.fatal("", e);
                    Utils.showErrorMessage(Messages.getString("ERR_CANNOT_OPEN_SOLUTION"));
                    return;
                }
                LOGGER.info("Connected to external data base");

                fireSolutionOpened(new SolutionEvent(solution.getInfo()));
            } catch (PlatformException e) {
                LOGGER.fatal("", e);
                Utils.showErrorMessage(Messages.getString("ERR_CANNOT_CREATE_PROJECT"));
                return;
            }
        }
        // if method is called at start of application (_solutionChooserFrame ==
        // null)
        // the behaviour is diffrent -- main frame shout be shown etc.
        if (_solutionChooserFrame != null) {
            _solutionChooserFrame.setVisible(false);
            _solutionChooserFrame = null;
        }
        _parent.refreshGui();
        _parent.setVisible(true);
    }

    public void removeSolutionListener(SolutionListener listener)
    {
        _solutionListeners.remove(listener);
    }

    public void saveSolution()
    {
        try {
            Solution solution = (Solution) _solutionManager.getCurrentSolution();
            // setting solution name if neccessary
            // TODO: remove this checking, make user to enter solution name
            // while
            // creating it
            if (solution.getInfo().getName() == null) {
                setSolutionProperties(solution);
            }

        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_SOLUTION"));
        }
    }

    public void setActionManager(ActionManager actionManager)
    {
        _actionManager = actionManager;
    }

    /**
     * @param startframe The parent to set.
     */
    public void setParent(ControllerFrame startframe)
    {
        _parent = startframe;
    }

    public void showSolutionChooser()
    {
        if (_solutionChooserFrame == null) {
            // it should never happen, as frame should be initialized in the constructor
            initSolutionFrame();
        }
        _solutionChooserFrame.setVisible(true);
    }

    public void viewSolutions()
    {

        if (_solutionViewerFrame == null) {
            _solutionViewerFrame = new JFrame(
                    Messages.getString("TIT_SOLUTIONS"));
            _solutionViewerFrame.getContentPane().add(
                    new SolutionSpread(
                            ((SolutionManager) _solutionManager).getDBManager()));
            _solutionViewerFrame.pack();
        }
        _solutionViewerFrame.setLocation(Utils.getCenterLocation(_solutionViewerFrame));
        _solutionViewerFrame.setVisible(true);
    }

    private JButton createActionButton(Action action, String text)
    {
        JButton button = new JButton(action);
        button.setText(text);
        Dimension dim = new Dimension(75, 25);
        button.setPreferredSize(dim);
        button.setMinimumSize(dim);
        button.setMaximumSize(dim);
        return button;
    }

    private JPanel createSolutionPanel()
    {
        FormLayout layout = new FormLayout(
                "left:pref, 3dlu, 100dlu:grow, 3dlu, right:20dlu", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();
        builder.appendSeparator("Solution data");

        builder.append("Solution name");
        builder.append(_txtSolutionName, 3);
        builder.append("Hostname", _txtHostname, 3);
        builder.append("Database path");
        builder.append(_txtDBPath, _btnChooseDB);
        builder.append("Username", _txtUsername, 3);
        builder.append("Password", _txtPasswd, 3);
        builder.append("Creation date", _txtSolutionCrDate, 3);
        builder.append("Last mod. date", _txtSolutionLastMod, 3);
        builder.appendSeparator("Test connection");
        builder.append("", _btnTestConnect, 2);

        builder.appendSeparator("Solution info");
        builder.append(new JScrollPane(_txtSolutionInfo), 5);

        return builder.getPanel();
    }

    private void fireSolutionCreated(SolutionEvent event)
    {
        for (SolutionListener listener : _solutionListeners) {
            listener.solutionCreated(event);
        }
    }

    private void fireSolutionModified(SolutionEvent event)
    {
        for (SolutionListener listener : _solutionListeners) {
            listener.solutionModified(event);
        }
    }

    private void fireSolutionOpened(SolutionEvent event)
    {
        for (SolutionListener listener : _solutionListeners) {
            listener.solutionOpened(event);
        }
    }

    private JComboBox getSolutionsCombo()
    {
        if (_comboSolutionList == null) {
            try {
                _solutions = (Solution[]) ((SolutionManager) _solutionManager).getSolutions();
                _comboSolutionList = new JComboBox(_solutions);
            } catch (Exception e) {
                LOGGER.fatal("", e);
                Utils.showErrorMessage(Messages.getString("ERR_CANNOT_LOAD_SOLUTIONS"));
            }
        }
        return _comboSolutionList;
    }

    private void initComponents()
    {
        _settingsDialog = new SettingsDialog(_parent, "Solution settings");
        _settingsDialog.setSeparator("Solution settings");

        int size = 100;
        _txtHostname = new JTextField(size);
        _txtSolutionCrDate = new JTextField();
        _txtSolutionCrDate.setEnabled(false);
        _txtSolutionLastMod = new JTextField();
        _txtSolutionLastMod.setEnabled(false);
        _txtSolutionInfo = new JTextArea(3, 10);

        _btnChooseDB = new JButton(Messages.getString("BTN_BROWSE"));
        _btnChooseDB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                searchDataBase();
            }
        });

        _btnTestConnect = new JButton("Test connection");
        _btnTestConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                testConnect();
            }
        });
    }

    private void searchDataBase()
    {
        if (_dbFileChooser == null) {
            _dbFileChooser = new TreeFileChooser(new File(Config.DB_DIR));
            _dbFileChooser.setFileFilter(new SearchFileFilter(DB_EXT, DB_DESC));
        }
        if (_dbFileChooser.showDialog(_parent)) {
            File file = _dbFileChooser.getSelectedFiles()[0];
            _txtDBPath.setText(file.getName());
            LOGGER.debug("Opening: " + file.getName());
        }
    }

    /**
     * Shows dialog which enables to initialize solution settings.
     * 
     * @param solution
     * @throws PlatformException
     */
    private boolean setSolutionProperties(ISolution iSolution)
            throws PlatformException
    {
        boolean approved = false;
        Solution solution = (Solution) iSolution;
        if (_pnlSolutionProperties == null) {
            // validation        
            SolutionModel solutionModel = new SolutionModel();
            SolutionValidator solutionValidator = new SolutionValidator(
                    solutionModel, _solutionManager);
            PlatformUtil platformUtil = (PlatformUtil) _solutionManager.getPlaftormUtil();
            IValidationModel validationModel = platformUtil.getValidationModel(solutionValidator);
            IComponentFactory componentFactory = validationModel.getComponentFactory();

            ValidationResultModel resultModel = platformUtil.getValidationResultModel();

            // creating fields
            _txtSolutionName = componentFactory.createTextField(
                    SolutionModel.PROPERTYNAME_SOLUTION_NAME, false);

            // FIXME:
            _txtDBPath = new JTextField();
            _txtUsername = new JTextField();
            _txtPasswd = new JPasswordField();

            // recreating solution panel
            _pnlSolutionProperties = createSolutionPanel();

            // setting component for dialog
            _settingsDialog.setSettingsComponent(_pnlSolutionProperties);
            _settingsDialog.setValidationModel(resultModel);
        }

        String name = solution.getInfo().getName();
        String info = solution.getInfo().getInfo();
        String host = solution.getInfo().getHost();
        String path = solution.getInfo().getPath();
        String user = solution.getInfo().getUser();
        String pass = solution.getInfo().getPasswd();
        Date dcdate = solution.getInfo().getCreationDate();
        Date dmdate = solution.getInfo().getLastModificationDate();

        String cdate = dcdate == null
                ? ""
                : DateFormat.getDateInstance().format(dcdate) + " "
                        + DateFormat.getTimeInstance().format(dcdate);
        String mdate = dmdate == null
                ? ""
                : DateFormat.getDateInstance().format(dmdate) + " "
                        + DateFormat.getTimeInstance().format(dmdate);

        _txtSolutionName.setText(name == null ? "" : name);
        _txtSolutionInfo.setText(info == null ? "" : info);
        _txtHostname.setText(host == null ? "" : host);
        _txtDBPath.setText(path == null ? "" : path);
        _txtUsername.setText(user == null ? "" : user);
        _txtPasswd.setText(pass == null ? "" : pass);
        _txtSolutionCrDate.setText(cdate == null ? "" : cdate);
        _txtSolutionLastMod.setText(mdate == null ? "" : mdate);

        if (_settingsDialog.showSettingsDialog()) {
            solution.getInfo().setName(_txtSolutionName.getText());
            solution.getInfo().setInfo(_txtSolutionInfo.getText());
            solution.getInfo().setHost(_txtHostname.getText());
            solution.getInfo().setPath(_txtDBPath.getText());
            solution.getInfo().setUser(_txtUsername.getText());
            solution.getInfo().setPasswd(_txtPasswd.getText());
            _solutionManager.addSolution(iSolution);
            approved = true;
        }
        return approved;
    }

    private void testConnect()
    {
        ExternalDBManager externalDBManager = new ExternalDBManager();
        String host = _txtHostname.getText();
        String dataBasePath = _txtDBPath.getText();
        String user = _txtUsername.getText();
        String passwd = _txtDBPath.getText();

        try {
            externalDBManager.connect(host, dataBasePath, user, passwd);
            externalDBManager.disconnect();
            Utils.showInfoMessage(Messages.getString("TT_SUCCESSFULLY_CONNECTED"));
        } catch (Exception e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage(Messages.getString("ERR_CONNECTION_ERROR"));
        }
    }
}