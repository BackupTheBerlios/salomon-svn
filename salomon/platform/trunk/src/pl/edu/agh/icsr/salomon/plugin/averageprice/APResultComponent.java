/*
 * Created on 2004-05-22
 *
 */

package pl.edu.agh.icsr.salomon.plugin.averageprice;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

/**
 * @author nico
 *  
 */
public class APResultComponent implements IResultComponent
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IResultComponent#getComponent(salomon.plugin.IResult)
	 */
	public Component getComponent(IResult result)
	{
		JPanel resultPanel = new JPanel(new BorderLayout());
		resultPanel.add(new JLabel("Age Counter result"), BorderLayout.NORTH);
		String text = Double.toString(((APResult) result).getAveragePrice());
		resultPanel.add(new JTextField(text), BorderLayout.CENTER);
		resultPanel.setSize(70, 70);
		return resultPanel;
	}

	public IResult getDefaultResult()
	{
		APResult result = new APResult();
		result.setAveragePrice(0);
		result.setSuccessful(true);
		return result;
	}
}