package salomon.engine.platform.data.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.project.ProjectManagerTest;

import salomon.platform.data.tree.INode;

import junit.framework.TestCase;

public class NodeTest extends TestCase
{
	private static Logger LOGGER = Logger.getLogger(ProjectManagerTest.class);
	private Node testNode1, testNode2, testNode3, testNode4, testNode5, testNode6, testNode7, testNode8;
	
	protected void setUp() throws Exception
	{
		PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
		
		testNode1 = new Node(null, "Node1", Node.Type.VALUE, "1");
		testNode2 = new Node(null, "Node2", Node.Type.VALUE, "2");
		testNode3 = new Node(null, "Node3", Node.Type.VALUE, "3");
		testNode4 = new Node(null, "Node4", Node.Type.VALUE, "4");
		testNode5 = new Node(null, "Node5", Node.Type.VALUE, "5");
		
		testNode6 = new Node(null, "Node6", Node.Type.VALUE, "6");
		testNode7 = new Node(testNode6, "Node7", Node.Type.VALUE, "7");
		testNode8 = new Node(testNode6, "Node8", Node.Type.COLUMN, "8");
		
		super.setUp();
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.Node(int, INode, String, Type, String)'
	 */
	public void testNodeIntINodeStringTypeString()
	{
		Node tempNode6 = new Node(1,null, "Temp node 6", Node.Type.VALUE, "test");
		Node tempNode7 = new Node(2,tempNode6, "Temp node 7", Node.Type.VALUE, "abc");
		Node tempNode8 = new Node(3,tempNode6, "Temp node 8", Node.Type.COLUMN, "123456");
	
		// Dlaczego id zwraca zero w momencie gdy atrybut id nie zostal w ogóle zdefiniowany?
		assertTrue(tempNode6.getId() == 1);
		assertTrue(tempNode6.getParentEdge()== null);
		assertTrue(tempNode6.getValue() == "test");
		assertTrue(tempNode6.getRoot() == tempNode6);
		assertTrue(tempNode6.getType() == Node.Type.VALUE);
		
		assertTrue(tempNode7.getId() == 2);
		assertTrue(tempNode7.getParentEdge()== "Temp node 7");
		assertTrue(tempNode7.getValue() == "abc");
		assertTrue(tempNode7.getRoot() == tempNode6);
		assertTrue(tempNode7.getType() == Node.Type.VALUE);
		
		assertTrue(tempNode8.getId() == 3);
		assertTrue(tempNode8.getParentEdge()== "Temp node 8");
		assertTrue(tempNode8.getValue() == "123456");
		assertTrue(tempNode8.getRoot() == tempNode6);
		assertTrue(tempNode8.getType() == Node.Type.COLUMN);
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.Node(INode, String, Type, String)'
	 */
	public void testNodeINodeStringTypeString()
	{
		Node tempNode6 = new Node(null, "Temp node 6", Node.Type.VALUE, "test");
		Node tempNode7 = new Node(tempNode6, "Temp node 7", Node.Type.VALUE, "abc");
		Node tempNode8 = new Node(tempNode6, "Temp node 8", Node.Type.COLUMN, "123456");
	
		// Dlaczego id zwraca zero w momencie gdy atrybut id nie zostal w ogóle zdefiniowany?
		assertTrue(tempNode6.getId() == 0);
		assertTrue(tempNode6.getParentEdge()== null);
		assertTrue(tempNode6.getValue() == "test");
		assertTrue(tempNode6.getRoot() == tempNode6);
		assertTrue(tempNode6.getType() == Node.Type.VALUE);
		
		assertTrue(tempNode7.getId() == 0);
		assertTrue(tempNode7.getParentEdge()== "Temp node 7");
		assertTrue(tempNode7.getValue() == "abc");
		assertTrue(tempNode7.getRoot() == tempNode6);
		assertTrue(tempNode7.getType() == Node.Type.VALUE);
		
		assertTrue(tempNode8.getId() == 0);
		assertTrue(tempNode8.getParentEdge()== "Temp node 8");
		assertTrue(tempNode8.getValue() == "123456");
		assertTrue(tempNode8.getRoot() == tempNode6);
		assertTrue(tempNode8.getType() == Node.Type.COLUMN);
		
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.setParent(INode)'
	 */
	public void testSetParent()
	{
		Node tempNode6 = new Node(null, "Temp node 6", Node.Type.VALUE, "test");
		Node tempNode7 = new Node(tempNode6, "Temp node 7", Node.Type.VALUE, "abc");
		Node tempNode8 = new Node(tempNode6, "Temp node 8", Node.Type.COLUMN, "123456");
	
		assertTrue(tempNode7.getParent() == tempNode6);
		assertTrue(tempNode6.getParent() == null);
		assertTrue(tempNode8.getParent() == tempNode6);
		
		tempNode6.setParent(testNode1);
		testNode1.setParent(testNode2);
		testNode2.setParent(testNode3);
		
		assertTrue(tempNode6.getParent() == testNode1);
		assertTrue(testNode1.getParent() == testNode2);
		assertTrue(testNode2.getParent() == testNode3);
		assertTrue(testNode3.getParent() == null);
		
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.addChild(INode)'
	 */
	public void testAddChild()
	{
		LOGGER.info("NodeTest.testAddChild()");
	
		Node testChild1 = new Node(null, "Rodzic", Node.Type.VALUE, "1");
		Node testChild2 = new Node(null, "Dziecko1", Node.Type.VALUE, "2");
		Node testChild3 = new Node(null, "Dziecko2", Node.Type.VALUE, "3");
		Node testChild4 = new Node(null, "Dziecko4", Node.Type.VALUE, "4");
		
		testChild1.addChild(testChild2);
		assertTrue(testChild1.getChildren()[testChild1.getChildren().length - 1] == testChild2);
	    
		testChild1.addChild(testChild3);
	    assertTrue(testChild1.getChildren()[testChild1.getChildren().length - 2] == testChild2);
	    assertTrue(testChild1.getChildren()[testChild1.getChildren().length - 1] == testChild3);
		
	    testChild2.addChild(testChild4);
	    assertTrue(testChild1.getChildren()[testChild1.getChildren().length - 2].getChildren()[0] == testChild4);
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.addChildren(INode[])'
	 */
	public void testAddChildren()
	{
		Node testArray2[] = new Node[3];
		testArray2[0] = testNode1;
		testArray2[1] = testNode2;
		testArray2[2] = testNode3;
		
		testNode4.addChildren(testArray2);
		assertTrue(testNode4.getChildren()[0].getType() == Node.Type.VALUE);
		assertTrue(testNode4.getChildren()[1].getType() == Node.Type.VALUE);
		assertTrue(testNode4.getChildren()[2].getType() == Node.Type.VALUE);
		assertTrue(testNode4.getChildren()[0].getValue() == "1");
		assertTrue(testNode4.getChildren()[1].getValue() == "2");
		assertTrue(testNode4.getChildren()[2].getValue() == "3");
		// because constructor parameter parent was null
		// and parent edge was not set
		assertTrue(testNode4.getChildren()[0].getParentEdge() == null);
		
		
		Node testArray[];
		List<Node> testList =  new ArrayList<Node>(Arrays.asList(testNode1,testNode2,testNode3));
		testArray = testList.toArray(new Node[3]);
		testNode5.addChildren(testArray);
		assertTrue(testNode4.getChildren()[0].getType() == Node.Type.VALUE);
		assertTrue(testNode4.getChildren()[1].getType() == Node.Type.VALUE);
		assertTrue(testNode4.getChildren()[2].getType() == Node.Type.VALUE);
		assertTrue(testNode4.getChildren()[0].getValue() == "1");
		assertTrue(testNode4.getChildren()[1].getValue() == "2");
		assertTrue(testNode4.getChildren()[2].getValue() == "3");
		// because constructor parameter parent was null
		// and parent edge was not set
		assertTrue(testNode4.getChildren()[0].getParentEdge() == null);
	
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.setChildren(INode[])'
	 */
	public void testSetChildren()
	{
		Node temp1 = new Node(1, null, "Temp1", Node.Type.VALUE, "1");
		Node temp2 = new Node(2, null, "Temp2", Node.Type.VALUE, "2");
		Node temp3 = new Node(3, null, "Temp3", Node.Type.VALUE, "3");
		Node tempArray[] = new Node[3];
		tempArray[0] = temp1;
		tempArray[1] = temp2;
		tempArray[2] = temp3;
		testNode1.setChildren(tempArray);
		assertTrue(testNode1.getChildren()[0].getParent() == testNode1);
		assertTrue(testNode1.getChildren()[1].getParent() == testNode1);
		assertTrue(testNode1.getChildren()[2].getParent() == testNode1);
		assertTrue(temp1.getParent()== testNode1);
		assertTrue(temp2.getParent()== testNode1);
		assertTrue(temp3.getParent()== testNode1);
		assertTrue(testNode1.getChildren()[0] == temp1);
		assertTrue(testNode1.getChildren()[1] == temp2);
		assertTrue(testNode1.getChildren()[2] == temp3);
		
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.getLeafs()'
	 */
	public void testGetLeafs()
	{
		INode result[] = testNode6.getLeafs();
		assertTrue(result.length == 2);
		assertTrue(result[0].getLeafs().length == 1);
		assertTrue(testNode1.getLeafs().length == 1);
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.getRoot()'
	 */
	public void testGetRoot()
	{
		Node temp1 = new Node(12, testNode7, "Temp1", Node.Type.VALUE, "1");
		Node temp2 = new Node(12, temp1, "Temp1", Node.Type.VALUE, "1");
		assertSame(temp2.getRoot(),temp1.getRoot());
		assertNotNull(testNode7.getRoot());
		assertTrue(testNode1.getRoot() == testNode1);
		assertTrue(testNode2.getRoot() == testNode2);
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.isLeaf()'
	 */
	public void testIsLeaf()
	{
		assertTrue(testNode7.isLeaf());
		assertFalse(testNode6.isLeaf());
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.isRoot()'
	 */
	public void testIsRoot()
	{
		assertTrue(testNode6.isRoot());
		assertTrue(testNode1.isRoot());
		assertTrue(testNode3.isRoot());
		assertTrue(testNode5.isRoot());
		assertFalse(testNode7.isRoot());
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.getChildren()'
	 */
	public void testGetChildren()
	{
		assertTrue(testNode1.getChildren().length == 0);
		testNode1.addChild(testNode2);
		assertTrue(testNode1.getChildren().length == 1);
		testNode1.addChild(testNode3);
		assertTrue(testNode1.getChildren().length == 2);
		
		assertTrue(testNode6.getChildren().length == 2);
		assertTrue(testNode7.getChildren().length == 0);
		
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.getParent()'
	 */
	public void testGetParent()
	{
		assertTrue(testNode6.getParent() == null);
		assertTrue(testNode7.getParent() == testNode6);
		assertTrue(testNode8.getParent() == testNode6);
			
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.getParentEdge()'
	 */
	public void testGetParentEdge()
	{
		assertTrue(testNode6.getParentEdge() == null);
		assertTrue(testNode7.getParentEdge() == "Node7");
		assertTrue(testNode8.getParentEdge() == "Node8");
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.setParentEdge(String)'
	 */
	public void testSetParentEdge()
	{
		testNode1.setParentEdge("Jakas tam krawedz");
		assertTrue(testNode1.getParentEdge() == "Jakas tam krawedz");
		
		String aaa = "abcdefg1234";
		testNode2.setParentEdge(aaa);
		assertTrue(testNode2.getParentEdge() == "abcdefg1234");
		
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.getType()'
	 */
	public void testGetType()
	{
		assertTrue(testNode1.getType() == Node.Type.VALUE);
		assertFalse(testNode8.getType() == Node.Type.VALUE);
		testNode8.setType(Node.Type.VALUE);
		assertFalse(testNode8.getType() == Node.Type.COLUMN);
		
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.setType(Type)'
	 */
	public void testSetType()
	{
		assertTrue(testNode1.getType() == Node.Type.VALUE);
		assertFalse(testNode8.getType() == Node.Type.VALUE);
		testNode8.setType(Node.Type.VALUE);
		assertFalse(testNode8.getType() == Node.Type.COLUMN);
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.getValue()'
	 */
	public void testGetValue()
	{
		assertTrue(testNode1.getValue()=="1");
		assertTrue(testNode2.getValue()=="2");
		assertTrue(testNode3.getValue()=="3");
		testNode1.setValue("Example");
		assertTrue(testNode1.getValue()=="Example");
		
		testNode1.setValue("The quick fox jumpes over the stone :)");
		assertTrue(testNode1.getValue()=="The quick fox jumpes over the stone :)");
		
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.setValue(String)'
	 */
	public void testSetValue()
	{
		assertTrue(testNode1.getValue()=="1");
		assertTrue(testNode2.getValue()=="2");
		assertTrue(testNode3.getValue()=="3");
		testNode1.setValue("Example");
		assertTrue(testNode1.getValue()=="Example");
		
		testNode1.setValue("The quick fox jumpes over the stone :)");
		assertTrue(testNode1.getValue()=="The quick fox jumpes over the stone :)");
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.getId()'
	 */
	public void testGetId()
	{
		testNode1.setId(1);
		testNode2.setId(-3456);
		testNode3.setId(12345678);
		
		assertTrue(testNode1.getId()==1);
		assertTrue(testNode2.getId()==-3456);
		assertTrue(testNode3.getId()==12345678);
		
		Node testChild1 = new Node(256*-256,null, "Rodzic", Node.Type.VALUE, "1");
		assertTrue(testChild1.getId()==-65536);
		assertFalse(testChild1.getId()==65536);
		
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.Node.setId(int)'
	 */
	public void testSetId()
	{
		Node temp1 = new Node(1, null, "Temp1", Node.Type.VALUE, "1");
		Node temp2 = new Node(2, null, "Temp2", Node.Type.VALUE, "2");
		Node temp3 = new Node(3, null, "Temp3", Node.Type.VALUE, "3");
		
		assertTrue(temp1.getId()==1);
		assertTrue(temp2.getId()==2);
		assertTrue(temp3.getId()==3);
		
		temp1.setId(-345);
		assertTrue(temp1.getId()==-345);
		
		temp1.setId(111222333);
		assertTrue(temp1.getId()==111222333);
		
		temp2.setId(1);
		temp2.setId(3);
		temp3.setId(10);
		assertTrue(temp2.getId()==3);
		assertTrue(temp3.getId()==10);
		
		
	}

}
