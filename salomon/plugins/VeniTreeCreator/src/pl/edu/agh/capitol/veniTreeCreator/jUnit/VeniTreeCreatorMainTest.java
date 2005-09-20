package pl.edu.agh.capitol.veniTreeCreator.jUnit;

import java.util.Vector;

import pl.edu.agh.capitol.veniTreeCreator.VeniTreeCreatorPlugin;
import pl.edu.agh.capitol.veniTreeCreator.logic.DataItem;
import pl.edu.agh.capitol.veniTreeCreator.logic.TreeItem;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.IVariable;
import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.ITree;
import salomon.platform.exception.PlatformException;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;
import junit.framework.TestCase;

public class VeniTreeCreatorMainTest extends TestCase{

	
	public void testLogicBasis(){
		
		// testy data itemów
		DataItem di = new DataItem("name",new String[]{"test3","test3"},"obj");
		assertEquals("name",di.getName());
		assertEquals(di.getAttributes(),new String[]{"test3","test3"});
		assertEquals(di.getObjective(),"obj");
		
		di.setName("test");
		di.setObjective("ob");
		di.pushAttribute("test3");

		assertEquals("test",di.getName());
		assertEquals(di.getAttributes(),new String[]{"test3","test3","test3"});
		assertEquals(di.getObjective(),"ob");

		// testy TreeItemów
		
		TreeItem ti = new TreeItem();
		ti.setLeaf(true);
		ti.setParent(ti);
		Vector<String> v = new Vector<String>();
		v.add("1");
		v.add("2");
		ti.setRoadMap(v);
		
		assertEquals(true,ti.isLeaf());
		assertEquals(v,ti.getRoadMap());
		
		ti.addToRoadMap("3");
		v.add("3");
		
		assertEquals(v,ti.getRoadMap());
		
	}
	
	//FIXME ustawiæ poni¿sze jakoœ sensownie
	IDataEngine eng;
	IEnvironment env;
	ISettings settings;
	
	/*
	 * Zeby to testowaæ trzeba poustawiaæ powy¿sze zmienne - jak to 
	 * sensownie zrealizowaæ - nie wiem ;)
	 */
	public void testLogicExtensions(){
		
		
		try {
			// test plugina - czy stabilny
			VeniTreeCreatorPlugin vtcp = new VeniTreeCreatorPlugin();
			// poni¿sze 3 linie do ustawiania w œrodowisku id'ka DataSource'a do  
			// tworzenia drzewa (powinien to robic 1-szy plugin)
			IVariable iva = env.createEmpty("ds_name");
			iva.setValue(new SimpleString("18"));
			env.add(iva);
			
			IDataSource dane = vtcp.getIDataSourceFromEnv(eng, env);
			int result = vtcp.performCalculations(dane, eng);
			
			VeniTreeCreatorPlugin vtcp2 = new VeniTreeCreatorPlugin();
			IDataSource dane2 = vtcp2.getIDataSourceFromEnv(eng, env);
			int result2 = vtcp2.performCalculations(dane2, eng);
			
			assertEquals(result,result2);
			
			//test Environmentu
			IVariable iv = env.createEmpty("t1");
			iv.setValue(new SimpleString("t1"));
			
			env.add(iv);
			
			assertEquals(env.getVariable("t1"),iv);
		} catch (PlatformException e) {
		}
	}

	
	public static void main(String[] args) {
	
	}

}
