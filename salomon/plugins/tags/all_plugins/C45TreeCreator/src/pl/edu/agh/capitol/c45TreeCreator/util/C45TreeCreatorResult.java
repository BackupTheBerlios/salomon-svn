
package pl.edu.agh.capitol.c45TreeCreator.util;

import salomon.util.serialization.SimpleStruct;

import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IStruct;

import salomon.plugin.IResult;

/**
 * 
 * Rezultat dzia³ania pluginu (default)
 * 
 * @author Lukasz
 *  FIXME
 */
public class C45TreeCreatorResult extends SimpleStruct implements IResult
{
    private String result = "";

    private boolean successful = false;

    public String getResult()
    {
        return result;
    }

    public void init(IObject o)
    {
        IStruct struct = (IStruct) o;
        for (String field : struct.getFieldNames()) {
            this.setField(field, struct.getField(field));
        }
    }

    public boolean isSuccessful()
    {
        return successful;
    }


    public void setResult(String result)
    {
        this.result = result;
    }

    public void setSuccessful(boolean successful)
    {
        this.successful = successful;
    }
}
