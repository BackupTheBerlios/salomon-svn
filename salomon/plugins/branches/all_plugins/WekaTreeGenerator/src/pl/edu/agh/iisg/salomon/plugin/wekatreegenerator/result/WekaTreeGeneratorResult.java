/*
 * Copyright (C) 2006 WekaTreeGenerator Team
 *
 * This file is part of WekaTreeGenerator.
 *
 * WekaTreeGenerator is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * WekaTreeGenerator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with WekaTreeGenerator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.wekatreegenerator.result;

import salomon.platform.serialization.IObject;
import salomon.plugin.IResult;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 * 
 */
public final class WekaTreeGeneratorResult extends SimpleStruct
        implements IResult
{
    private boolean _isSuccessful = false;

    private String _output;

    /**
     * Returns the output.
     *
     * @return The output
     */
    public final String getOutput()
    {
        return _output;
    }

    /**
     * @see salomon.plugin.IResult#init(salomon.platform.serialization.IObject)
     */
    public void init(IObject object)
    {
        SimpleStruct struct = (SimpleStruct) object;

        // setting struct fields
        SimpleString output = (SimpleString) struct.getField(OUTPUT);
        setField(OUTPUT, output);
        _output = output.getValue();
    }

    /**
     * @see salomon.plugin.IResult#isSuccessful()
     */
    public boolean isSuccessful()
    {
        return _isSuccessful;
    }

    /**
     * Set the value of output field.
     *
     * @param output The output to set
     */
    public final void setOutput(String output)
    {
        _output = output;
        setField(OUTPUT, new SimpleString(output));

    }

    /**
     * Set the value of isSuccessful field.
     *
     * @param isSuccessful The isSuccessful to set
     */
    public final void setSuccessful(boolean isSuccessful)
    {
        _isSuccessful = isSuccessful;
    }

    private static final String OUTPUT = "output";

}
