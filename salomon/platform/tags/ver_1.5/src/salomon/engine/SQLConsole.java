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

package salomon.engine;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.PlainDocument;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.syntax.jedit.JEditTextArea;
import org.syntax.jedit.tokenmarker.PLSQLTokenMarker;

import salomon.engine.database.DBManager;

import salomon.util.gui.Utils;

import salomon.platform.exception.PlatformException;

/**
 * Class represents simple SQL console. It is used to debug application. Allows
 * excecuting SQL queries and DDL statements. It supports transactions and makes
 * managing with database easier supplying commands history.
 */
public final class SQLConsole extends JFrame
{
    private static final Logger LOGGER = Logger.getLogger(SQLConsole.class);

    private JButton _btnCommit;

    private JButton _btnExecute;

    private JButton _btnNext;

    private JButton _btnPrevious;

    private JButton _btnRollback;

    // private JPanel _contentPane = null;

    /**
     * Class represents connection to data base
     * 
     * @uml.property name="_dbManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private DBManager _dbManager;

    //    private JTextPane _edtSQLQuery = null;
    private JEditTextArea _edtSQLQuery;

    /**
     * 
     * @uml.property name="_history"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private CommandHistory _history;

    /**
     * Indicates if console has been initialized.
     */
    private boolean _initialized;

    /**
     * If true it means than it is run standalone, and on exit close connection
     * to database and calls System.exit();
     */
    private boolean _isStandAlone;

    /**
     * Text area which shows results of SQL query and error messages
     */
    private JTextArea _msgArea;

    private JPanel _pnlMain;

    private JScrollPane _scrlResult;

    private JSplitPane _sptConsolePane;

    /** Table which represents results of SQL query */
    private JTable _tblResult;

    private JToolBar _toolCommands;

    /**
     * Creates instance of SQLConsole object.
     * 
     */
    public SQLConsole(DBManager manager)
    {
        _isStandAlone = (manager == null);
        if (_isStandAlone) {
            PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
        }
        if (manager != null) {
            _dbManager = manager;
        } else {
            throw new UnsupportedOperationException(
                    "Standalone mode not supported!");
        }
    }

    /**
     * starts SQLConsole
     * 
     * @param args parameters from the command line
     */
    public static void main(String[] args)
    {
        new SQLConsole(null);
    }

    public void executeBatch(File file) throws PlatformException
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder string = new StringBuilder((int) file.length());

            String line = null;
            while ((line = reader.readLine()) != null) {
                // skip lines starting with comment
                // TODO: add full supprot for comments
                if (line.trim().startsWith("/*")) {
                    continue;
                }
                string.append(line.trim());
            }
            String[] commands = string.toString().split(";");            
            _dbManager.executeBatch(commands);
            // do not commit by default
            // if commit is required, it should be included in batch file

        } catch (FileNotFoundException e) {
            throw new PlatformException(e.getLocalizedMessage());
        } catch (IOException e) {
            throw new PlatformException(e.getLocalizedMessage());
        } catch (SQLException e) {
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    public void showConsole()
    {
        if (!_initialized) {
            initialize();
            _initialized = true;
        }
    }

    private void commit()
    {
        _dbManager.commit();
        showMessage(Messages.getString("TXT_COMMIT_COMPLETE")); //$NON-NLS-1$
    }

    /**
     * Method executes query from text pane and places results in the table
     * 
     */
    private void executeQuery()
    {
        String query = _edtSQLQuery.getText();
        JComponent cmpResult = null;
        ResultSet resultSet = null;
        try {
            _dbManager.executeQuery(query);
            resultSet = _dbManager.getResultSet();
            if (resultSet == null) {
                int updatedRows = _dbManager.getUpdateCount();
                showMessage(Messages.getString("TXT_UPDATED_ROWS") + updatedRows); //$NON-NLS-1$
            } else {
                cmpResult = Utils.createResultTable(resultSet);
                _scrlResult.setViewportView(cmpResult);
            }
            // if there was no exception, then adding command to the history
            _history.addCommand(query);
        } catch (SQLException e) {
            LOGGER.fatal("", e); //$NON-NLS-1$
            showMessage(e.getLocalizedMessage());
        }
    }

    /**
     * This method initializes btnCommit
     * 
     * @return JButton
     */
    private JButton getBtnCommit()
    {
        if (_btnCommit == null) {
            _btnCommit = new JButton();
            _btnCommit.setText(Messages.getString("BTN_COMMIT")); //$NON-NLS-1$
            _btnCommit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    commit();
                }
            });
        }
        return _btnCommit;
    }

    /**
     * This method initializes btnExecute
     * 
     * @return JButton
     */
    private JButton getBtnExecute()
    {
        if (_btnExecute == null) {
            _btnExecute = new JButton();
            _btnExecute.setText(Messages.getString("BTN_EXECUTE")); //$NON-NLS-1$
            _btnExecute.setMnemonic(KeyEvent.VK_ENTER);
            _btnExecute.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    executeQuery();
                }
            });
        }
        return _btnExecute;
    }

    /**
     * This method initializes btnBack
     * 
     * @return JButton
     */
    private JButton getBtnNext()
    {
        if (_btnNext == null) {
            _btnNext = new JButton();
            _btnNext.setText("->"); //$NON-NLS-1$
            _btnNext.setMnemonic(KeyEvent.VK_DOWN);
            _btnNext.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    nextCommand();
                }
            });
        }
        return _btnNext;
    }

    /**
     * This method initializes btnPrevious
     * 
     * @return JButton
     */
    private JButton getBtnPrevious()
    {
        if (_btnPrevious == null) {
            _btnPrevious = new JButton();
            _btnPrevious.setText("<-"); //$NON-NLS-1$
            _btnPrevious.setMnemonic(KeyEvent.VK_UP);
            _btnPrevious.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    previousCommand();
                }
            });
        }
        return _btnPrevious;
    }

    /**
     * This method initializes btnRollback
     * 
     * @return JButton
     */
    private JButton getBtnRollback()
    {
        if (_btnRollback == null) {
            _btnRollback = new JButton();
            _btnRollback.setText(Messages.getString("BTN_ROLLBACK")); //$NON-NLS-1$
            _btnRollback.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    rollback();
                }
            });
        }
        return _btnRollback;
    }

    /**
     * This method initializes edtSQLQuery
     * 
     * @return JTextPane
     */
    private JEditTextArea getEdtSQLQuery()
    {
        if (_edtSQLQuery == null) {
            _edtSQLQuery = new JEditTextArea();
            _edtSQLQuery.setTokenMarker(new PLSQLTokenMarker());
            _edtSQLQuery.getDocument().getDocumentProperties().put(
                    PlainDocument.tabSizeAttribute, 4);
        }

        return _edtSQLQuery;
    }

    private JTextArea getMessageArea()
    {
        JTextArea txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setBackground(Color.WHITE);
        return txtArea;
    }

    /**
     * This method initializes pnlMain
     * 
     * @return JPanel
     */
    private JPanel getPnlMain()
    {
        if (_pnlMain == null) {
            _pnlMain = new JPanel();
            _pnlMain.setLayout(new BorderLayout());
            _pnlMain.add(getToolCommands(), BorderLayout.NORTH);
            _pnlMain.add(getSptPane(), BorderLayout.CENTER);
        }
        return _pnlMain;
    }

    /**
     * This method initializes scrlResult
     * 
     * @return JScrollPane
     */
    private JScrollPane getScrlResult()
    {
        if (_scrlResult == null) {
            _scrlResult = new JScrollPane();
            _scrlResult.setViewportView(getTblResult());
        }
        return _scrlResult;
    }

    /**
     * This method initializes sptPane
     * 
     * @return JSplitPane
     */
    private JSplitPane getSptPane()
    {
        if (_sptConsolePane == null) {
            _sptConsolePane = new JSplitPane();
            _sptConsolePane.setTopComponent(new JScrollPane(getEdtSQLQuery()));
            _sptConsolePane.setBottomComponent(getScrlResult());
            _sptConsolePane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            _sptConsolePane.setDividerLocation(100);
        }
        return _sptConsolePane;
    }

    /**
     * This method initializes tblResult
     * 
     * @return JTable
     */
    private JTable getTblResult()
    {
        if (_tblResult == null) {
            _tblResult = new JTable();
        }
        return _tblResult;
    }

    /**
     * This method initializes toolCommands
     * 
     * @return JToolBar
     */
    private JToolBar getToolCommands()
    {
        if (_toolCommands == null) {
            _toolCommands = new JToolBar();
            _toolCommands.add(getBtnExecute());
            _toolCommands.add(getBtnCommit());
            _toolCommands.add(getBtnRollback());
            _toolCommands.add(Box.createHorizontalGlue());
            _toolCommands.add(getBtnPrevious());
            _toolCommands.add(getBtnNext());
        }
        return _toolCommands;
    }

    /**
     * This method initializes this
     */
    private void initialize()
    {
        _msgArea = getMessageArea();
        _history = new CommandHistory(100);
        this.setContentPane(getPnlMain());
        this.setSize(400, 400);
        this.setTitle(Messages.getString("TIT_CONSOLE")); //$NON-NLS-1$
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if (_isStandAlone) {
                    try {
                        _dbManager.disconnect();
                    } catch (SQLException e1) {
                        LOGGER.fatal("", e1); //$NON-NLS-1$
                    }
                    System.exit(0);
                }
            }
        });
        this.setLocation(Utils.getCenterLocation(this));
        this.setVisible(true);
        _edtSQLQuery.requestFocus();
    }

    /**
     * 
     */
    private void nextCommand()
    {
        // TODO Auto-generated method stub
        String command = _history.getNextCommand();
        if (command != null) {
            _edtSQLQuery.setText(command);
        }
    }

    /**
     * 
     */
    private void previousCommand()
    {
        // TODO Auto-generated method stub
        String command = _history.getPreviousCommand();
        if (command != null) {
            _edtSQLQuery.setText(command);
        }
    }

    private void rollback()
    {
        _dbManager.rollback();
        showMessage(Messages.getString("TXT_ROLLBACK_COMPLETE")); //$NON-NLS-1$
    }

    private void showMessage(String message)
    {
        _msgArea.setText(message);
        _scrlResult.setViewportView(_msgArea);
    }

    /**
     * Class manages command history list.
     */
    private static final class CommandHistory
    {
        private List<String> _commandHistory;

        private int _currentPosition = -1;

        CommandHistory(int initialSize)
        {
            _commandHistory = new ArrayList<String>(initialSize);
            _currentPosition = -1;
        }

        /**
         * Method adds command at the end of command history.
         * 
         * @param command command to add
         */
        void addCommand(String command)
        {
            _currentPosition = _commandHistory.size();
            _commandHistory.add(command);
        }

        /**
         * @return command or null if there is no next command
         */
        String getNextCommand()
        {
            String command = null;
            if (_currentPosition < _commandHistory.size() - 1) {
                _currentPosition++;
                command = _commandHistory.get(_currentPosition);
            }

            return command;
        }

        /**
         * @return command or null if there is no previous command
         */
        String getPreviousCommand()
        {
            String command = null;
            if (_commandHistory.size() > 1 && _currentPosition > 0) {
                _currentPosition--;
                command = _commandHistory.get(_currentPosition);
            }

            return command;
        }
    }
}