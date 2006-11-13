package pl.capitol.tree.plugins.util;

import salomon.util.serialization.SimpleStruct;

import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IStruct;

import salomon.plugin.ISettings;

public class TreeVisSettings extends SimpleStruct implements ISettings {
    
	private static final long serialVersionUID = 1L;

	public void init(IObject o)
    {
    	IStruct struct = (IStruct) o;
    	for (String field : struct.getFieldNames()) {
    		this.setField(field, struct.getField(field));
    	}
    }
}
