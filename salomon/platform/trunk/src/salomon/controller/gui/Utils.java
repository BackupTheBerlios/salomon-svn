
package salomon.controller.gui;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import salomon.core.Messages;

/**
 * Class supply some useful methods used in GUI. All public methods are static
 * to simplyfy usage.
 */
public class Utils
{

	private JComponent _parent;

	private void setParentImpl(JComponent parent)
	{
		_parent = parent;
	}

	private void showErrorMessageImpl(String msg)
	{
		JOptionPane.showMessageDialog(_parent, msg,
				Messages.getString("TIT_ERROR"), JOptionPane.ERROR_MESSAGE);
	}

	private void showInfoMessageImpl(String msg)
	{
		JOptionPane.showMessageDialog(_parent, msg,
				Messages.getString("TIT_INFO"), JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean showQuestionMessageImpl(String title, String msg)
	{
		int retVal = JOptionPane.showConfirmDialog(_parent, msg, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		return (retVal == JOptionPane.YES_OPTION);
	}

	private boolean showWarningMessageImpl(String msg)
	{
		int retVal = JOptionPane.showConfirmDialog(_parent, msg,
				Messages.getString("TIT_WARN"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		return (retVal == JOptionPane.YES_OPTION);
	}

	/**
	 * Creates JTable basing on given collection of data.
	 * 
	 * @param allData collection of data
	 * @return JTable representing given data
	 */
	public static JTable createResultTable(Collection allData)
	{
		String[] columnNames = null;
		Object[][] data = null;

		int rowCount = 0;
		for (Iterator iter = allData.iterator(); iter.hasNext();) {
			Object[] row = (Object[]) iter.next();
			if (rowCount == 0) {
				columnNames = (String[]) Arrays.asList(row).toArray(
						new String[row.length]);
				// creating matrix for data
				data = new Object[allData.size() - 1][columnNames.length];
			} else {
				data[rowCount - 1] = row;
			}
			rowCount++;
		}

		JTable table = new JTable(data, columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		return table;
	}

	/**
	 * Method creates JTable representing given result set.
	 * 
	 * @param resultSet result of SQL query
	 * @return table representing given result set.
	 * @throws SQLException
	 */
	public static JTable createResultTable(ResultSet resultSet)
			throws SQLException
	{
		JTable table = createResultTable(getDataFromResultSet(resultSet));
		return table;
	}

	/**
	 * Processes given result set. Returns Collection, which first element is an
	 * array of column names and a selected rows as next elements
	 * 
	 * @param resultSet
	 * @return @throws SQLException
	 */
	public static Collection getDataFromResultSet(ResultSet resultSet)
			throws SQLException
	{
		LinkedList allData = new LinkedList();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] columnNames = new String[columnCount];
		// getting column names
		for (int i = 0; i < columnCount; i++) {
			columnNames[i] = metaData.getColumnLabel(i + 1);
		}
		allData.add(columnNames);
		// getting data
		int size = 0;
		while (resultSet.next()) {
			Object[] row = new Object[columnCount];
			int i = 0;
			for (; i < columnCount; i++) {
				row[i] = resultSet.getObject(i + 1);
			}
			allData.add(row);
			size++;
		}

		// printing result
		StringBuffer buffer = new StringBuffer(512);
		int rowCount = 0;
        buffer.append("\n");
		for (Iterator iter = allData.iterator(); iter.hasNext();) {
			Object[] row = (Object[]) iter.next();
			for (int i = 0; i < row.length; i++) {
				buffer.append(row[i] + "|"); //$NON-NLS-1$
			}
			if (rowCount == 0) {
				buffer.append("\n=============================================\n"); //$NON-NLS-1$
			} else {
				buffer.append("\n"); //$NON-NLS-1$   
			}
			rowCount++;
			_logger.info(buffer);
		}

		return allData;
	}

	/**
	 * Sets parent component. It is used to display messages relativly to it
	 * 
	 * @param parent parent component
	 */
	public static void setParent(JComponent parent)
	{
		getInstance().setParentImpl(parent);
	}

	/**
	 * Shows an error message
	 * 
	 * @param msg text of the message
	 */
	public static void showErrorMessage(String msg)
	{
		getInstance().showErrorMessageImpl(msg);
	}

	/**
	 * Shows an information message
	 * 
	 * @param msg text of the message
	 */
	public static void showInfoMessage(String msg)
	{
		getInstance().showInfoMessageImpl(msg);
	}

	/**
	 * Shows a question message
	 * 
	 * @param title title of message
	 * @param msg text of the message
	 */
	public static boolean showQuestionMessage(String title, String msg)
	{
		return getInstance().showQuestionMessageImpl(title, msg);
	}

	/**
	 * Shows a warning message
	 * 
	 * @param msg text of the message
	 */
	public static boolean showWarningMessage(String msg)
	{
		return getInstance().showWarningMessageImpl(msg);
	}

	private static Utils getInstance()
	{
		if (_instance == null) {
			_instance = new Utils();
		}
		return _instance;
	}

	private static Utils _instance;

	private static Logger _logger = Logger.getLogger(Utils.class);
}