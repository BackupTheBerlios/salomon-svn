package ks;

import javax.swing.*; 
import java.sql.*;


public class SQLConsole extends JFrame {

	private JPanel _contentPane = null;  //  @jve:visual-info  decl-index=0 visual-constraint="33,72"

	private JSplitPane _sptConsolePane = null;
	private JTextPane _edtSQLQuery = null;
	private JScrollPane _scrlResult = null;
	private JTable _tblResult = null;
	
	private JButton _btnExecute = null;
	private JButton _btnCommit = null;
	private JButton _btnRollback = null;
	private JToolBar _toolCommands = null;
	private JPanel _pnlMain = null;
	
	
	public static void main(String[] args) {
		new SQLConsole();
		System.out.println("SQLConsole.main()");
	}
	/**
	 * This is the default constructor
	 */
	public SQLConsole() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getPnlMain());
		this.setSize(390, 271);
		this.setTitle("Console");
		this.show();
	}
	/**
	 * This method initializes contentPane
	 * 
	 * @return JPanel
	 */
	private JPanel getJContentPane() {
		if (_contentPane == null) {
			_contentPane = new JPanel();
			_contentPane.setLayout(new java.awt.BorderLayout());
		}
		return _contentPane;
	}
	/**
	 * This method initializes sptPane
	 * 
	 * @return JSplitPane
	 */
	private JSplitPane getSptPane() {
		if(_sptConsolePane == null) {
			_sptConsolePane = new JSplitPane();
			_sptConsolePane.setTopComponent(getEdtSQLQuery());
			_sptConsolePane.setBottomComponent(getScrlResult());
			_sptConsolePane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return _sptConsolePane;
	}
	/**
	 * This method initializes edtSQLQuery
	 * 
	 * @return JTextPane
	 */
	private JTextPane getEdtSQLQuery() {
		if(_edtSQLQuery == null) {
			_edtSQLQuery = new JTextPane();
		}
		return _edtSQLQuery;
	}
	/**
	 * This method initializes tblResult
	 * 
	 * @return JTable
	 */
	private JTable getTblResult() {
		if(_tblResult == null) {
			_tblResult = new JTable();
		}
		return _tblResult;
	}
	/**
	 * This method initializes scrlResult
	 * 
	 * @return JScrollPane
	 */
	private JScrollPane getScrlResult() {
		if(_scrlResult == null) {
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
	private JButton getBtnExecute() {
		if(_btnExecute == null) {
			_btnExecute = new JButton();
			_btnExecute.setText("Execute");
		}
		return _btnExecute;
	}
	/**
	 * This method initializes btnCommit
	 * 
	 * @return JButton
	 */
	private JButton getBtnCommit() {
		if(_btnCommit == null) {
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
	private JButton getBtnRollback() {
		if(_btnRollback == null) {
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
	private JToolBar getToolCommands() {
		if(_toolCommands == null) {
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
	private JPanel getPnlMain() {
		if(_pnlMain == null) {
			_pnlMain = new JPanel();
			_pnlMain.setLayout(new java.awt.BorderLayout());
			_pnlMain.add(getToolCommands(), java.awt.BorderLayout.NORTH);
			_pnlMain.add(getSptPane(), java.awt.BorderLayout.CENTER);
		}
		return _pnlMain;
	}
	/**
	 * Method executes query from text pane and places results in the table
	 *
	 */
	private void executeQuery()	{
		String query = _edtSQLQuery.getText();	
						
	}
	
	// TODO: ta klasa powinna byc gdzie indziej, nie jako wewnetrzna
	class DBConnector {
	
		private Connection _connection = null;
		private Statement _statement = null;
				
		
		public void connect() throws SQLException, ClassNotFoundException{
			Class.forName("org.firebirdsql.jdbc.FBDriver");	
			_connection = DriverManager.getConnection(
								 "jdbc:firebirdsql://127.0.0.1/c:/database/testbase.gdb",
								 "SYSDBA", "masterkey");
			_statement = _connection.createStatement();	
		}	
		
		public ResultSet executeQuery(String query) throws SQLException {													
			return  _statement.executeQuery(query);			
		}			
		
		public void disconnect() throws SQLException{
			_connection.close();
			_statement.close();
		}		
	}	
}  //  @jve:visual-info  decl-index=0 visual-constraint="37,22"
