/*
 *
 * Author: Jakub Pawlowski
 * Created on:2004-12-19 13:03:44
 */

package pl.edu.agh.iisg.salomon.plugin.datasetunion;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

/**
 * 
 * TODO: add comment.
 * @author kuba
 * 
 */
public class UResultComponent implements IResultComponent
{

	/**
	 * @see salomon.plugin.IResultComponent#getComponent(salomon.plugin.IResult)
	 */
	public Component getComponent(IResult result)
	{
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(new JLabel("New DataSet"), BorderLayout.NORTH);
        //TODO add proper values
        String text = "new_data_set";//((UResult) result).toString();
        resultPanel.add(new JTextField(text), BorderLayout.CENTER);
        resultPanel.setSize(70, 70);
        return resultPanel;
	}

	/**
	 * @see salomon.plugin.IResultComponent#getDefaultResult()
	 */
	public IResult getDefaultResult()
	{
        IResult result = new UResult();
		return result;
	}

}
