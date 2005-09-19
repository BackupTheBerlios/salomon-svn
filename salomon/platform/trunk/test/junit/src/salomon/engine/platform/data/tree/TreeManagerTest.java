/**
 * 
 */
package salomon.engine.platform.data.tree;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.platform.ManagerEngine;
import salomon.engine.solution.ISolution;
import salomon.platform.data.tree.ITreeManager;

/**
 * @author Mateusz Nowakowski
 *
 */
public class TreeManagerTest extends TestCase {

	private ManagerEngine manager = null;
	private ITreeManager treeManager = null;
	private static Logger LOGGER = Logger.getLogger(TreeManagerTest.class);
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$   
		manager = new ManagerEngine();
		ISolution [] solutions = manager.getSolutionManager().getSolutions();
		treeManager = solutions[0].getDataEngine().getTreeManager(); // 0 - oznacza pierwszy solutuio, m usuisz sobie wybrac ktory chcesz
	}


}
