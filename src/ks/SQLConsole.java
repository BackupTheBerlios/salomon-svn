package ks;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.LinkedList;

public class SQLConsole extends JFrame
{
	private JPanel _contentPane = null;

	//  @jve:visual-info decl-index=0 visual-constraint="33,72"
	private JSplitPane _sptConsolePane = null;

	private JTextPane _edtSQLQuery = null;

	private JScrollPane _scrlResult = null;

	private JTable _tblResult = null;

	private JButton _btnExecute = null;

	private JButton _btnCommit = null;

	private JButton _btnRollback = null;

	private JToolBar _toolCommands = null;

	private JPanel _pnlMain = null;

	// klasa odpowiedzialna za polaczenie z baza danych
	private DBConnector _connector = null;

	public static void main(String[] args)
	{
		new SQLConsole();
		System.out.println("SQLConsole.main()");
	}

	/**
	 * This is the default constructor
	 */
	public SQLConsole()
	{
		super();
		_connector = new DBConnector();
		initialize();
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
				System.out.println("windowClosing()");
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
		this.show();
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
	 * This method initializes edtSQLQuery
	 * 
	 * @return JTextPane
	 */
	private JTextPane getEdtSQLQuery()
	{
		if (_edtSQLQuery == null) {
			_edtSQLQuery = new JTextPane();
		}
		return _edtSQLQuery;
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
	 * This method initializes btnExecute
	 * 
	 * @return JButton
	 */
	private JButton getBtnExecute()
	{
		if (_btnExecute == null) {
			_btnExecute = new JButton();
			_btnExecute.setText("Execute");
			_btnExecute.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					System.out.println("actionPerformed()");
					// TODO Auto-generated Event stub actionPerformed()
					executeQuery();
				}
			});
		}
		return _btnExecute;
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
		}
		return _btnCommit;
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
		}
		return _btnRollback;
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
		}
		return _toolCommands;
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
	 * Method executes query from text pane and places results in the table
	 *  
	 */
	private void executeQuery()
	{
		String query = _edtSQLQuery.getText();
		try {
			ResultSet resultSet = _connector.executeQuery(query);
			JTable table = createResultTable(resultSet);
			resultSet.close();
			_scrlResult.setViewportView(table);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	// TODO: ta klasa powinna byc gdzie indziej, nie jako wewnetrzna
	class DBConnector
	{
		private Connection _connection = null;

		private Statement _statement = null;

		public void connect() throws SQLException, ClassNotFoundException
		{
			Class.forName("org.firebirdsql.jdbc.FBDriver");
			_connection = DriverManager.getConnection(
					"jdbc:firebirdsql://127.0.0.1/c:/database/testbase.gdb",
					"SYSDBA", "masterkey");
			_statement = _connection.createStatement();
		}

		public ResultSet executeQuery(String query) throws SQLException
		{
			return _statement.executeQuery(query);
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
	}
} //  @jve:visual-info decl-index=0 visual-constraint="37,22"
