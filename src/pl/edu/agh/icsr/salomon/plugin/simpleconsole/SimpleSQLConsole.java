/*
 * Created on 2004-05-07
 *
 */

package pl.edu.agh.icsr.salomon.plugin.simpleconsole;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import salomon.core.data.DataEngine;
import salomon.core.data.Environment;
import salomon.core.data.dataset.DataSet;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * @author nico
 * 
 *  
 */
public class SimpleSQLConsole implements IPlugin
{
	private static Logger _logger = Logger.getLogger(SimpleSQLConsole.class);

	private JPanel _resultPanel = null;

	private JScrollPane _scrlResult = null;

	private JPanel _settingsPanel = null;

	private JTextArea _txtMsgArea = null;

	private JTextArea _txtQueryEditor = null;

	private JTable _resultTable = null;

	private Description _description = null;

	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	/**
	 *  
	 */
	public SimpleSQLConsole()
	{
		initizalize();
		String info = "Simple SQL Console.\nOperates on current data set.";
		_description = new Description("SimpleSQLConsole", info);
	}

	/**
	 * Doesn't support queries other than SELECT
	 */
	public IResult doJob(DataEngine engine, Environment environment,
			ISettings settings)
	{
		_logger.warn("###   doJob()   ###");
		SCSettings scSettings = (SCSettings) settings;
		String query = scSettings.getQuery().trim().toLowerCase();
		SCResult scResult = new SCResult();
		if (query.length() > 0 && query.indexOf("select") != -1) {
			ResultSet resultSet = null;
			try {
				//
				// getting current data set
				//
				String curDataSetName = environment.get("CURRENT_DATA_SET");
				_logger.info("current data set name: " + curDataSetName);
				DataSet curDataSet = engine.getTestingDataSetManager().getDataSetForName(
						curDataSetName);
				//
				// selecting data
				//
				resultSet = curDataSet.selectData(query);
				_logger.info("Got results");
				//
				// getting column names
				//
				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();
				String[] columnNames = new String[columnCount];
				// getting column names
				for (int i = 0; i < columnCount; i++) {
					columnNames[i] = metaData.getColumnLabel(i + 1);
				}
				//
				// getting data
				//
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
				//
				// creating data object
				//
				Object[][] data = new Object[size][columnCount];
				for (int i = 0; i < size; i++) {
					data[i] = (Object[]) rows.get(i);
				}
				//
				// printing result
				//
				StringBuffer buffer = new StringBuffer(512);
				for (int i = 0; i < columnCount; i++) {
					buffer.append(columnNames[i] + " ");
				}
				buffer.append("\n=============================================\n");
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < columnCount; j++) {
						buffer.append(data[i][j] + " | ");
					}
					buffer.append("\n");
				}
				_logger.info(buffer);
				//
				// creating result object
				//
				scResult.setResultType(SCResult.SC_OK);
				scResult.setData(columnNames, data);
				scResult.setSuccessful(true);
			} catch (Exception e) {
				_logger.fatal("", e);
				scResult.setResultType(SCResult.SC_QUERY_ERROR);
				scResult.setErrorMessage(e.getLocalizedMessage());
			}
			// if query doesn't have "select" phrase
		} else {
			scResult.setResultType(SCResult.SC_UNSUPPORTED_QUERY);
			scResult.setErrorMessage("Unsupported query type: " + query);
		}
		_logger.warn("###   doJob() - finished  ###");
		return scResult;
	}

	public void initizalize()
	{
		// TODO Auto-generated method stub
	}

	public String toString()
	{
		return _description.getName();
	}

	public Description getDescription()
	{
		return _description;
	}

	public ISettingComponent getSettingComponent()
	{
		return new SCSettingComponent();
	}

	public IResultComponent getResultComponent()
	{
		return new SCResultComponent();
	}

}