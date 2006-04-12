
package pl.edu.agh.capitol.veniTreeCreator.util;

import salomon.util.serialization.SimpleStruct;

import salomon.platform.serialization.IObject;

import salomon.plugin.IResult;

/**
 * FIXME
 *
 */
public final class VeniTreeCreatorResult extends SimpleStruct
        implements IResult
{
    private String result = "";

    private boolean successful = false;

    public String getResult()
    {
        return result;
    }

    public void init(IObject object)
    {
        throw new UnsupportedOperationException(
                "Method VeniTreeCreatorResult.init() not implemented yet!");
    }

    public boolean isSuccessful()
    {
        return successful;
    }

    public void parseResult(String result)
    {
        this.setResult(result);
    }

    public String resultToString()
    {
        return this.getResult();
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
