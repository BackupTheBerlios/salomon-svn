package ks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

public class SQLConsole extends JFrame
{
	private JButton _btnCommit = null;

	private JButton _btnExecute = null;

	private JButton _btnNext = null;

	private JButton _btnPrevious = null;

	private JButton _btnRollback = null;

	// klasa odpowiedzialna za polaczenie z baza danych
	private DBConnector _connector = null;

	private JPanel _contentPane = null;

	private JTextPane _edtSQLQuery = null;

	private JPanel _pnlMain = null;

	private JScrollPane _scrlResult = null;

	//  @jve:visual-info decl-index=0 visual-constraint="33,72"
	private JSplitPane _sptConsolePane = null;

	/**
	 * Table which represents results of SQL query
	 */
	private JTable _tblResult = null;

	private JToolBar _toolCommands = null;

	private CommandHistory _history = null;

	/**
	 * Text area which shows results of SQL query and error messages
	 */
	private JTextArea _msgArea;

	/**
	 * This is the default constructor
	 */
	public SQLConsole()
	{
		super();
		_connector = new DBConnector();
		_msgArea = getMessageArea();
		_history = new CommandHistory(100);
		initialize();
	}

	private JTextArea getMessageArea()
	{
		JTextArea txtArea = new JTextArea();
		((JTextArea) txtArea).setEditable(false);
		txtArea.setBackground(Color.WHITE);
		return txtArea;
	}

	public static void main(String[] args)
	{
		new SQLConsole();
		System.out.println("SQLConsole.main()");
	}

	private void commit()
	{
		try {
			_connector.commit();
			showMessage("Commit complete");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showMessage(e.getLocalizedMessage());
		}
	}

	private JTable createResultTable(ResultSet resultSet) throws SQLException
	{
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] columnNames = new String[columnCount];
		// getting column names
		for (int i = 0; i < columnCount; i++) {
			columnNames[i] = metaData.getColumnLabel(i + 1);
		}
		// getting data
		LinkedList rows = new LinkedList();
		int size = 0;
		while (resultSet.next()) {
			Object[] row = new Object[columnCount];
			int i = 0;
			for (; i < columnCount; i++) {
				row[i] = resultSet.getObject(i + 1);
			}
			rows.add(row);
			size++;
		}
		// creating result table
		Object[][] data = new Object[size][columnCount];
		for (int i = 0; i < size; i++) {
			data[i] = (Object[]) rows.get(i);
		}
		// printing result
		for (int i = 0; i < columnCount; i++) {
			System.out.print(columnNames[i] + " ");
		}
		System.out.println("=============================================");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < columnCount; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println("");
		}
		//TODO: use TableModel
		return new JTable(data, columnNames);
	}

	/**
	 * Method executes query from text pane and places results in the table
	 *  
	 */
	private void executeQuery()
	{
		String query = _edtSQLQuery.getText();
		JComponent cmpResult = null;
		try {
			_connector.executeQuery(query);
			ResultSet resultSet = _connector.getResultSet();
			if (resultSet == null) {
				int updatedRows = _connector.getUpdateCount();
				showMessage("Updated rows: " + updatedRows);
			} else {
				cmpResult = createResultTable(resultSet);
				resultSet.close();
				_scrlResult.setViewportView(cmpResult);
			}
			// if there was no exception, then adding command to the history
			_history.addCommand(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			_btnCommit.setText("Commit");
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
			_btnExecute.setText("Execute");
			_btnExecute.setMnemonic(KeyEvent.VK_ENTER);
			_btnExecute.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
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
			_btnNext.setText("->");
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
			_btnPrevious.setText("<-");
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
			_btnRollback.setText("Rollback");
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
	private JTextPane getEdtSQLQuery()
	{
		if (_edtSQLQuery == null) {
			_edtSQLQuery = new JTextPane();
			_edtSQLQuery.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e)
				{
					if (e.isControlDown()) {
						System.out.println("SQLConsole.getEdtSQLQuery(): " + e.getKeyCode());
						switch (e.getKeyCode()) {
							case KeyEvent.VK_F8 :
								executeQuery();
								break;
							case KeyEvent.VK_DOWN :
								nextCommand();
								break;
							case KeyEvent.VK_UP :
								previousCommand();
								break;
						}
					}
				}
			});
		}
		return _edtSQLQuery;
	}

	/**
	 * This method initializes contentPane
	 * 
	 * @return JPanel
	 */
	private JPanel getJContentPane()
	{
		if (_contentPane == null) {
			_contentPane = new JPanel();
			_contentPane.setLayout(new BorderLayout());
		}
		return _contentPane;
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
			_sptConsolePane.setTopComponent(getEdtSQLQuery());
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
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setContentPane(getPnlMain());
		this.setSize(400, 400);
		this.setTitle("Console");
		// connecting to data base
		try {
			_connector.connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e)
			{
				// TODO Auto-generated Event stub windowClosing()
				try {
					_connector.disconnect();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		this.setVisible(true);
	}

	/**
	 *  
	 */
	private void nextCommand()
	{
		// TODO Auto-generated method stub
		System.out.println("SQLConsole.nextCommand()");
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
		try {
			_connector.rollback();
			showMessage("Rollback complete");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showMessage(e.getLocalizedMessage());
		}
	}

	private void showMessage(String message)
	{
		_msgArea.setText(message);
		_scrlResult.setViewportView(_msgArea);
	}

	/**
	 * @author nico Class manages command history list.
	 */
	class CommandHistory
	{
		private List _commandHistory = null;

		private int _currentPosition = -1;

		public CommandHistory(int initialSize)
		{
			_commandHistory = new ArrayList(initialSize);
			_currentPosition = -1;
		}

		/**
		 * @return command or null if there is no previous command
		 */
		public String getPreviousCommand()
		{
			String command = null;
			if (_commandHistory.size() > 1 && _currentPosition > 0) {
				_currentPosition--;
				command = (String) _commandHistory.get(_currentPosition);
			}
			return command;
		}

		/**
		 * @return command or null if there is no next command
		 */
		public String getNextCommand()
		{
			String command = null;
			if (_currentPosition < _commandHistory.size() - 1) {
				_currentPosition++;
				command = (String) _commandHistory.get(_currentPosition);
			}
			return command;
		}

		/**
		 * Method adds command at the end of command history.
		 * 
		 * @param command
		 *            command to add
		 */
		public void addCommand(String command)
		{
			_currentPosition = _commandHistory.size();
			_commandHistory.add(command);
		}
	}

	// TODO: ta klasa powinna byc gdzie indziej, nie jako wewnetrzna
	class DBConnector
	{
		private Connection _connection = null;

		private Statement _statement = null;

		public void commit() throws SQLException
		{
			_connection.commit();
		}

		public void connect() throws SQLException, ClassNotFoundException
		{
			Class.forName("org.firebirdsql.jdbc.FBDriver");
			_connection = DriverManager.getConnection(
					"jdbc:firebirdsql://127.0.0.1/c:/database/testbase.gdb",
					"SYSDBA", "masterkey");
			_connection.setAutoCommit(false);
			_statement = _connection.createStatement();
		}

		public void disconnect() throws SQLException
		{
			if (_statement != null) {
				_statement.close();
			}
			if (_connection != null) {
				_connection.close();
			}
		}

		public boolean executeQuery(String query) throws SQLException
		{
			return _statement.execute(query);
		}

		/**
		 * @return result set or null if query was INSERT, UPDATE or DELETE
		 * @throws SQLException
		 */
		public ResultSet getResultSet() throws SQLException
		{
			return _statement.getResultSet();
		}

		public int getUpdateCount() throws SQLException
		{
			return _statement.getUpdateCount();
		}

		public void rollback() throws SQLException
		{
			_connection.rollback();
		}
	}
} //  @jve:visual-info decl-index=0 visual-constraint="37,22"
