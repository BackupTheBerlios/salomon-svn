package pl.capitol.tree.plugins.test;







import org.apache.log4j.Logger;

import pl.capitol.tree.plugins.test.components.VisResultComponent;
import pl.capitol.tree.plugins.test.components.VisSettingComponent;
import pl.capitol.tree.plugins.util.TreeVisResults;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.tree.INode;
import salomon.platform.data.tree.ITree;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;


//NK
public class VisPlugin implements IPlugin {
	
	

	private static final Logger LOGGER = Logger.getLogger(VisPlugin.class);

	public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings) {
		
		
		int treeId = 0;
		try {
			treeId = Integer.parseInt(((SimpleString)env.getVariable("tree_name").getValue()).getValue());
		} catch (NumberFormatException e1) {
			LOGGER.fatal("", e1);
		} catch (PlatformException e1) {
			LOGGER.fatal("", e1);
		}
		
		ITree myTree = null;
		INode root = null;
		INode rootLeaf = null;
		INode[] children = null;
		boolean notEndTree = true;
		
		String result = "";
		TreeVisResults treeResult = new TreeVisResults();
			try {
				myTree =  eng.getTreeManager().getTree(treeId);
				rootLeaf = myTree.getRoot();
				result = result + rootLeaf.getValue() + "  #  " + "  /  ";
				while(notEndTree)
				{
					notEndTree = false;
					root = rootLeaf;
					children = root.getChildren();
					for(int i = 0;i < children.length;i++){
						if(children[i].isLeaf() == false)
						{
							notEndTree = true;
							rootLeaf = children[i];
						}
						result = result + root.getValue() + "  -  " + children[i].getParentEdge() + "  -  " + children[i].getValue() + "  #  ";
					}
					result = result + "  /  ";
				}
				treeResult.parseResult(result);
			} catch (PlatformException e) {
				LOGGER.fatal(e.getMessage());
			}
			
		
		
		return treeResult;
	}

	public ISettingComponent getSettingComponent() {
		return new VisSettingComponent();
	}

	public IResultComponent getResultComponent() {
		return new VisResultComponent(); 
	}

}
