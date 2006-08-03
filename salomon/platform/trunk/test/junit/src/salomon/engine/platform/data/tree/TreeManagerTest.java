/**
 * 
 */

package salomon.engine.platform.data.tree;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import salomon.TestObjectFactory;
import salomon.engine.platform.data.attribute.AttributeSet;
import salomon.engine.solution.ISolution;
import salomon.platform.IDataEngine;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeEdge;
import salomon.platform.data.tree.ITreeNode;
import salomon.platform.exception.PlatformException;

/**
 * @author Mateusz Nowakowski
 *
 */
public class TreeManagerTest extends TestCase
{
    private static final Logger LOGGER = Logger.getLogger(TreeManagerTest.class);

    private TreeManager _treeManager;

    public TreeManagerTest() throws PlatformException
    {
        ISolution solution = TestObjectFactory.getSolution("Trees");
        IDataEngine dataEngine = solution.getDataEngine();
        _treeManager = (TreeManager) dataEngine.getTreeManager();
        LOGGER.info("Connected");
    }
    
    public void testGetAll() throws PlatformException
    {
        LOGGER.info("AttributeManagerTest.testGetAll()");
        ITree[] trees = null;
        trees = _treeManager.getAll();
        for (ITree tree : trees) {
            LOGGER.info("walking tree: " + ((Tree) tree).getInfo());            
            walkTree(tree);
        }
    }
    
    private void walkTree(ITree tree) {
        ITreeNode rootNode = tree.getRootNode();
        walkNode(rootNode);        
    }
    
    private void walkNode(ITreeNode node) {
        if (!node.isLeaf()) {
            LOGGER.debug("node: " + node);
            ITreeEdge[] edges = node.getChildrenEdges();
            for (ITreeEdge edge : edges){
                LOGGER.debug("edge: " + edge);                
                walkNode(edge.getChildNode());
            }
        }        
    }

}