package pl.capitol.tree.plugins.treeConclude.util;

import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IStruct;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleStruct;

/**
 * Klasa definiuhe mo¿liwe zmienne u¿ywane w pluginie.
 * 
 * @author Mateusz Nowakowski
 *
 */
public class Settings extends SimpleStruct implements ISettings {
    
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see salomon.plugin.ISettings#init(salomon.platform.serialization.IObject)
	 */
	public void init(IObject o)
    {
    	IStruct struct = (IStruct) o;
    	for (String field : struct.getFieldNames()) {
    		this.setField(field, struct.getField(field));
    	}
    }

	public Settings() {
		this.setField("isAlone",new SimpleInteger(0));
		this.setField("treeId",new SimpleInteger(0));
	}
	
	
}
