/*
 * Copyright (C) 2008 Salomon Team
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
/**
 * 
 */
package salomon.plugin.result;

import java.awt.Component;

import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

/**
 * @author Nikodem.Jura
 *
 */
public class DummyResultComponent implements IResultComponent {

	/* (non-Javadoc)
	 * @see salomon.plugin.IResultComponent#getComponent(salomon.plugin.IResult, salomon.platform.IDataEngine)
	 */
	public Component getComponent(IResult result, IDataEngine engine)
			throws PlatformException {
		throw new UnsupportedOperationException(
				"Method DummyResultComponent.getComponent() is not implemented yet!");
	}

	/* (non-Javadoc)
	 * @see salomon.plugin.IResultComponent#getDefaultResult()
	 */
	public IResult getDefaultResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
