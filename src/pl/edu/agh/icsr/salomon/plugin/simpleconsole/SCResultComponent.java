/*
 * Created on 2004-05-22
 *
 */

package pl.edu.agh.icsr.salomon.plugin.simpleconsole;

import java.awt.*;
import javax.swing.*;
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
		switch (scResult.resultType) {
			case SCResult.SC_UNSUPPORTED_QUERY :
				resultComonent = getMsgArea("Unsupported query.\nOnly selection queries are supproted");
				break;
			case SCResult.SC_QUERY_ERROR :
				resultComonent = getMsgArea(scResult.errMessage);
				break;
			case SCResult.SC_OK :
				resultComonent = getScrlResult(scResult.columnNames,
						scResult.data);
				break;
			default :
				resultComonent = getMsgArea("Internal error.\nUnsopported type: "
						+ scResult.resultType);
		}
		return resultComonent;
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
		scrlResult.setSize(200, 100);
		scrlResult.setViewportView(new JTable(data, columnNames));
		return scrlResult;
	}
}