/*
 * Created on 2004-05-07
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package ks.plugins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import org.apache.log4j.Logger;
import ks.SQLConsole;
import ks.data.DBConnector;
import ks.data.DataEngine;
import ks.data.Environment;

/**
 * @author nico
 * 
 *  
 */
public class SimpleSQLConsole extends AbstractPlugin
{
	private static Logger _logger = Logger.getLogger(SimpleSQLConsole.class);

	private Result _result = null;

	private JPanel _resultPanel = null;

	JScrollPane _scrlResult = null;

	private JPanel _settingsPanel = null;

	private JTextArea _txtMsgArea = null;

	private JTextArea _txtQueryEditor = null;

	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	public void doJob(DataEngine engine, Environment environment,
			Settings settings)
	{
		_logger.warn("###   doJob()   ###");
		String query = (String)settings.get("query");
		Object queryResult = null;
		_result = new Result();
		DBConnector connector = null;
		try {
			connector = DBConnector.getInstance();
			connector.executeQuery(query);
			ResultSet resultSet = connector.getResultSet();
			if (resultSet == null) {
				int updatedRows = connector.getUpdateCount();
				queryResult = new String("Updated rows: " + updatedRows);
				_result.put("retCode", new Integer(1));
				_result.put("result", queryResult);
			} else {
				queryResult = resultSet;
				_logger.info("Got results");
				_result.put("retCode", new Integer(0));
				_result.put("result", queryResult);
			}
		} catch (Exception e) {
			_logger.fatal("", e);
			queryResult = e.getLocalizedMessage();
			_result.put("retCode", new Integer(-1));
			_result.put("result", queryResult);
		}
		_logger.warn("###   doJob() - finished  ###");
	}

	public Description getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public JPanel getResultPanel()
	{
		if (_resultPanel == null) {
			_resultPanel = new JPanel();
			_resultPanel.setLayout(new BorderLayout());
			_resultPanel.add(new JLabel("SQL Message"), BorderLayout.NORTH);
			_resultPanel.add(getScrlResult(), BorderLayout.CENTER);
			Dimension dim = new Dimension(100, 60);
			_resultPanel.setSize(dim);			
		}
		// if retCode != 0 it means that it was error or query was
		// INSERT/UPDATE...
		int retCode = ((Integer) _result.get("retCode")).intValue();
		Object queryResult = _result.get("result");
		if (retCode != 0) {
			_scrlResult.setViewportView(getMsgArea(queryResult.toString()));
		} else {
			_scrlResult.setViewportView(getTable((ResultSet) queryResult));
		}
		return _resultPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ks.plugins.IGraphicalPlugin#getSettings()
	 */
	public Settings getSettings()
	{
		Settings settings = new Settings();
		settings.put("query", _txtQueryEditor.getText());
		return settings;
	}

	public JPanel getSettingsPanel()
	{
		if (_settingsPanel == null) {
			_settingsPanel = new JPanel();
			_settingsPanel.setLayout(new BorderLayout());
			_settingsPanel.add(new JLabel("SQL Console"), BorderLayout.NORTH);
			_settingsPanel.add(new JScrollPane(getQueryEditor()),
					BorderLayout.CENTER);
			_settingsPanel.setSize(80, 66);
		}
		return _settingsPanel;
	}

	public void initizalize()
	{
		// TODO Auto-generated method stub
	}

	private JTextArea getMsgArea(String msg)
	{
		if (_txtMsgArea == null) {
			_txtMsgArea = new JTextArea(5, 10);
			_txtMsgArea.setEditable(false);
			_txtMsgArea.setBackground(Color.WHITE);
		}
		_txtMsgArea.setText(msg);
		return _txtMsgArea;
	}

	private JTextArea getQueryEditor()
	{
		if (_txtQueryEditor == null) {
			_txtQueryEditor = new JTextArea(5, 10);
		}
		return _txtQueryEditor;
	}

	private JScrollPane getScrlResult()
	{
		if (_scrlResult == null) {
			_scrlResult = new JScrollPane();
			_scrlResult.setSize(100,60);
		}
		return _scrlResult;
	}

	private JComponent getTable(ResultSet resultSet)
	{
		JComponent table = null;
		try {
			table = SQLConsole.createResultTable(resultSet);
			resultSet.close();
		} catch (SQLException e) {
			_logger.fatal("", e);
			table = getMsgArea(e.getLocalizedMessage());
		}
		return table;
	}

	public String toString()
	{
		return "SQL Console";
	}
}
