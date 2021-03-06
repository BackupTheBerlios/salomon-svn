/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.datasetunion.result;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import salomon.util.serialization.SimpleString;

import salomon.platform.IDataEngine;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

/**
 * 
 * TODO: add comment.
 * @author kuba
 * 
 */
public final class UResultComponent implements IResultComponent
{

	/**
	 * @see salomon.plugin.IResultComponent#getComponent(salomon.plugin.IResult, salomon.platform.IDataEngine)
	 */
	public Component getComponent(IResult result, IDataEngine dataEngine)
	{

		JPanel resultPanel = new JPanel(new BorderLayout());
		resultPanel.add(new JLabel("New DataSet"), BorderLayout.NORTH);

		//TODO add proper values
		String text = null;
		if (result != null) {
			text = ((UResult) result).resultToString();
		} else {
			text = "";
		}

		if (text.equals("ERROR")) {
			String error = ((SimpleString) ((UResult) result).getField(UResult.ERROR_MESSAGE)).getValue();
			text += " - " + error;
		}

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
