/**
 * 
 */

package salomon.engine.platform.data.tree;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import salomon.TestObjectFactory;
import salomon.engine.platform.data.attribute.AttributeDescription;
import salomon.engine.platform.data.attribute.AttributeManager;
import salomon.engine.platform.data.attribute.AttributeSet;
import salomon.engine.solution.ISolution;
import salomon.platform.IDataEngine;
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.attribute.description.IAttributeDescription;
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
    
    private AttributeManager _attributeManager;
    
    private IMetaData _metaData;

    public TreeManagerTest() throws PlatformException
    {
        ISolution solution = TestObjectFactory.getSolution("Trees");
        IDataEngine dataEngine = solution.getDataEngine();
        _treeManager = (TreeManager) dataEngine.getTreeManager();
        _attributeManager = (AttributeManager) dataEngine.getAttributeManager();
        _metaData = dataEngine.getMetaData();
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
    
    public void testAddTree() throws PlatformException
    {
        IAttributeSet attributeSet = createTestAttributeSet();
        _attributeManager.add(attributeSet);
        // to enforce additional test
        attributeSet = _attributeManager.getAttributeSet(attributeSet.getName());
        
        IAttributeDescription[] descriptions = attributeSet.getDesciptions();
        ITreeNode ageNode = null;
        ITreeNode eduNode = null;
        ITreeNode assignedYes = null;
        ITreeNode assignedNo = null;
        ITreeNode assignedNo2 = null;
        
        Tree tree = (Tree) _treeManager.createTree();
        
        for (int i = 0; i < descriptions.length; ++i) {
            if (descriptions[i].getName().equals("attr_age")) {
                ageNode = _treeManager.createNode(tree, descriptions[i]);
            } else if (descriptions[i].getName().equals("attr_education")) {
                eduNode = _treeManager.createNode(tree, descriptions[i]);
            } else if (descriptions[i].getName().equals("attr_assigned")) {
                assignedYes = _treeManager.createNode(tree, descriptions[i]);
                ((TreeNode)assignedYes).setLeafValue("Y");
                assignedNo = _treeManager.createNode(tree, descriptions[i]);
                ((TreeNode)assignedNo).setLeafValue("N");
                assignedNo2 = _treeManager.createNode(tree, descriptions[i]);
                ((TreeNode)assignedNo2).setLeafValue("N");
            }
        }
        
        tree.setName("test" + System.currentTimeMillis());
        tree.setRootNode(ageNode);
        tree.setAttributeSet(attributeSet);
        ageNode.addChildNode(eduNode, " > 22");
        ageNode.addChildNode(assignedNo, " <= 22"); 
        eduNode.addChildNode(assignedYes, "High school");
        eduNode.addChildNode(assignedNo2, "Primary shool");
        
        LOGGER.debug("Adding tree... ");        
        _treeManager.add(tree);
        LOGGER.debug("Tree added.");
    }
    
    private IAttributeSet createTestAttributeSet()
    {
        ITable table = _metaData.getTable("candidates");
        AttributeDescription age = (AttributeDescription) _attributeManager.createIntegerAttributeDescription(
                "attr_age", table.getColumn("age"));
        age.setOutput(false);
        AttributeDescription education = (AttributeDescription) _attributeManager.createStringAttributeDescription(
                "attr_education", table.getColumn("education"));
        education.setOutput(false);
        AttributeDescription assigned = (AttributeDescription) _attributeManager.createStringAttributeDescription(
                "attr_assigned", table.getColumn("assigned"));
        assigned.setOutput(true);
        
        AttributeDescription[] descriptions = new AttributeDescription[]{age, education, assigned};                

        IAttributeSet attributeSet = _attributeManager.createAttributeSet(descriptions);
        attributeSet.setName("test" + System.currentTimeMillis());
        return attributeSet;
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