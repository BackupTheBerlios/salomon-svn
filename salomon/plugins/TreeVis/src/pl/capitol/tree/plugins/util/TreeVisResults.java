
package pl.capitol.tree.plugins.util;

import salomon.util.serialization.SimpleStruct;

import salomon.platform.serialization.IObject;

import salomon.plugin.IResult;
/**
 * FIXME
 *
 */
public final class TreeVisResults extends SimpleStruct implements IResult
{

    private String result = "";

    public void init(IObject object)
    {
        throw new UnsupportedOperationException("Method TreeVisResults.init() not implemented yet!");
    }

    public boolean isSuccessful()
    {
        return true;
    }

    public void parseResult(String result)
    {
        this.result = result;
    }

    public String resultToString()
    {
        return this.result;
    }

}
