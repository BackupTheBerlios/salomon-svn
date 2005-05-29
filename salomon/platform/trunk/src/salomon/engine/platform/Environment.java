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

package salomon.engine.platform;

import java.util.HashMap;
import java.util.Map;

import salomon.platform.IEnvironment;
import salomon.platform.IVariable;
import salomon.platform.exception.PlatformException;

/**
 *  Class represents environment of task execution.
 */
public final class Environment implements IEnvironment
{
	private Map<String, IVariable> _variables = new HashMap<String, IVariable>();

	public void add(IVariable variable) throws PlatformException
	{
		_variables.put(variable.getName(), variable);
	}

	public IVariable createEmpty(String name) throws PlatformException
	{
		throw new UnsupportedOperationException("Method Environment.createEmpty() not implemented yet!");
	}

	public IVariable[] getAll() throws PlatformException
	{
		return _variables.values().toArray(new IVariable[_variables.size()]);
	}

	public IVariable getVariable(String name) throws PlatformException
	{
		return _variables.get(name);
	}

	public void remove(IVariable variable) throws PlatformException
	{
		_variables.remove(variable.getName());
	}

} // class Environment
