/*
 * Created on 2004-05-22
 *
 */

package pl.edu.agh.icsr.salomon.plugin.simpleconsole;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

/**
 * @author nico
 *  
 */
public class SCResultComponent implements IResultComponent
{
	/**
	 *  
	 */
	public Component getComponent(IResult result)
	{
		SCResult scResult = (SCResult) result;
		Component resultComonent = null;
		switch (scResult.getResultType()) {
			case SCResult.SC_UNSUPPORTED_QUERY :
				// its not required now
				resultComonent = getMsgArea("Unsupported query.\nOnly selection queries are supproted");
				break;
			case SCResult.SC_QUERY_ERROR :
				resultComonent = getMsgArea(scResult.getErrorMessage());
				break;
			case SCResult.SC_UPDATE :
				resultComonent = getMsgArea(scResult.getErrorMessage());
				break;
			case SCResult.SC_OK :
				resultComonent = getScrlResult(scResult.getColumnNames(),
						scResult.getData());
				break;
			default :
				resultComonent = getMsgArea("Internal error.\nUnsupported type: "
						+ scResult.getResultType());
		}
		return resultComonent;
	}

	public IResult getDefaultResult()
	{
		SCResult result = new SCResult();
		result.setErrorMessage("Default result");
		result.setResultType(SCResult.SC_OK);
		result.setSuccessful(true);
		return result;
	}

	private JTextArea getMsgArea(String msg)
	{
		JTextArea txtMsgArea = new JTextArea(5, 10);
		txtMsgArea.setEditable(false);
		txtMsgArea.setBackground(Color.WHITE);
		txtMsgArea.setText(msg);
		return txtMsgArea;
	}

	private JScrollPane getScrlResult(String[] columnNames, Object[][] data)
	{
		JScrollPane scrlResult = new JScrollPane();
		scrlResult.setPreferredSize(new Dimension(300, 200));
		JTable table = new JTable(data, columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrlResult.setViewportView(table);
		return scrlResult;
	}
}