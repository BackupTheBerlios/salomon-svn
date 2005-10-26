/**
 * 
 */
package salomon.engine.platform.data.tree;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.platform.ManagerEngine;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLInsert;
import salomon.engine.solution.ISolution;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.INode;
import salomon.platform.data.tree.IDataSource;
import salomon.platform.exception.PlatformException;

/**
 * @author Mateusz Nowakowski
 *
 */
public class TreeManagerTest extends TestCase {

	private ManagerEngine manager = null;
	private ITreeManager treeManager = null;
	private static Logger LOGGER = Logger.getLogger(TreeManagerTest.class);
	private DBManager _dbManager = null;
	private DBManager _externalDBManager = null;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		
		PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$ 
		LOGGER.debug("===============SETUP====================");
		manager = new ManagerEngine();
		ISolution [] solutions = manager.getSolutionManager().getSolutions();
		LOGGER.debug("Number of solutions: " + solutions.length );
		treeManager = solutions[1].getDataEngine().getTreeManager(); // 0 - oznacza pierwszy solution (SOLUTION_ID z tabeli SOLUTIONS), musisz sobie wybrac ktory chcesz
		_dbManager = manager.getDbManager();
		
		//podlaczam sie do bazy z danymi drzewek
		_externalDBManager = new DBManager();
		_externalDBManager.connect(null, "db//TREEDECISIONINGDATA.GDB", "SYSDBA", "masterkey");

		// tworze tymczasowa tabele dla testow
		_externalDBManager.executeQuery("CREATE TABLE FIKCYJNA_TABELA ( KOLUMNA_DECISIONED INTEGER NOT NULL, BLA1 INTEGER NOT NULL, BLA2 INTEGER NOT NULL, BLA3 INTEGER NOT NULL, BLA4 VARCHAR(10) NOT NULL)");
		_externalDBManager.commit();
		
		try {
			
			// przygotowuje tree data source'a dla drzewa
			SQLInsert insert = new SQLInsert("TREE_DATA_SOURCES");
			insert.addValue("TDS_NAME","NAZWA TESTOWA DATASOURCEA");
			insert.addValue("TDS_INFO","TEST_ADD_TREE");
			insert.addValue("TDS_SOLUTION_ID",4);
			insert.addValue("TDS_TABLE ","FIKCYJNA_TABELA");
			insert.addValue("TDS_DECISIONED_COLUMN","KOLUMNA_DECISIONED");
			insert.addValue("TDS_DECISIONING_COLUMNS","bla1,bla2,bla3,bla4");
			insert.addValue("TDS_ID",666);
		
			// przygotowuje wartosci w fikcyjnej tabeli
			SQLInsert insert2 = new SQLInsert("FIKCYJNA_TABELA");
			insert2.addValue("KOLUMNA_DECISIONED",1);
			insert2.addValue("BLA1",2);
			insert2.addValue("BLA2",3);
			insert2.addValue("BLA3",4);
			insert2.addValue("BLA4","YES");
		
			
			_dbManager.insert(insert);
			_externalDBManager.insert(insert2);
			_dbManager.commit();
			_externalDBManager.commit();
		
		}catch (Exception e)
		{
			LOGGER.fatal("TreeManagerTest setUp() generated exception: ", e);
		}
	}

	protected void tearDown() throws Exception
	{
		LOGGER.debug("===============TEARDOWN====================");
		_dbManager.executeQuery("DELETE FROM TREE_DATA_SOURCES WHERE TDS_ID = 666");
		_dbManager.commit();
		
		_externalDBManager.executeQuery("DROP TABLE FIKCYJNA_TABELA");
		_externalDBManager.commit();
		
	}
	
	public void testAddTree()
	{
		INode testNode1 = new Node(null, "Node1", Node.Type.VALUE, "1");
		INode testNode2 = new Node(null, "Node2", Node.Type.VALUE, "1->2");
		INode testNode3 = new Node(null, "Node3", Node.Type.VALUE, "1->3");
		INode testNode4 = new Node(null, "Node4", Node.Type.VALUE, "2->4");
		INode testNode5 = new Node(null, "Node5", Node.Type.VALUE, "2->5");
		
		Tree testTree;
		
		testTree = new Tree();
		testTree.setRoot(testNode1);
		testTree.getRoot().addChild(testNode2);
		testTree.getRoot().addChild(testNode3);
		testNode2.addChild(testNode4);
		testNode2.addChild(testNode5);
		
		testTree.setId(666);
		testTree.setInfo("Informacja");
		testTree.setName("Drzewko testowe");
		
		try {
			
			// przypisanie stworzonego drzewa do DataSource'a
			testTree.setDataSource(treeManager.getTreeDataSource(666));
			
			ITree [] treeArray;
			int treeID;
			// dodanie drzewa
			treeID = treeManager.addTree(testTree);
			
			// pobranie istniejacych drzew
			treeArray = treeManager.getTrees();
			LOGGER.debug("TreeArray length:" + treeArray.length);
			LOGGER.debug("treeArray[treeArray.length - 1].getInfo() :"+treeArray[treeArray.length - 1].getInfo());
			
			assertTrue(treeArray[treeArray.length - 1].getInfo().compareTo("Informacja") == 0);
			assertTrue(treeArray[treeArray.length - 1].getName().compareTo("Drzewko testowe") == 0);
			LOGGER.debug("treeArray[treeArray.length - 1].getId():" + treeArray[treeArray.length - 1].getId());
			assertTrue(treeArray[treeArray.length - 1].getId() == treeID);
			assertTrue(treeArray[treeArray.length - 1].getRoot().getValue().compareTo(testNode1.getValue()) == 0 );
			assertTrue(treeArray[treeArray.length - 1].getRoot().getChildren()[0].getValue().compareTo(testNode2.getValue()) == 0 );
			assertTrue(treeArray[treeArray.length - 1].getRoot().getChildren()[1].getValue().compareTo(testNode3.getValue()) == 0 );
			assertTrue(treeArray[treeArray.length - 1].getRoot().getChildren()[0].getChildren()[0].getValue().compareTo(testNode4.getValue()) == 0 );
			assertTrue(treeArray[treeArray.length - 1].getRoot().getChildren()[0].getChildren()[1].getValue().compareTo(testNode5.getValue()) == 0 );
			
			assertTrue(treeArray[treeArray.length - 1].getDataSource().getDecisionedColumn().compareTo("KOLUMNA_DECISIONED") == 0);
			assertTrue(treeArray[treeArray.length - 1].getDataSource().getInfo().compareTo("TEST_ADD_TREE") == 0);
			assertTrue(treeArray[treeArray.length - 1].getDataSource().getName().compareTo("NAZWA TESTOWA DATASOURCEA") == 0);
			assertTrue(treeArray[treeArray.length - 1].getDataSource().getTableName().compareTo("FIKCYJNA_TABELA") == 0);
			LOGGER.debug("treeArray[treeArray.length - 1].getDataSource().getDecioningColumns()[0]:"+ treeArray[treeArray.length - 1].getDataSource().getDecioningColumns()[0]);
			assertTrue(treeArray[treeArray.length - 1].getDataSource().getDecioningColumns()[0].compareTo("bla1") == 0);
			assertTrue(treeArray[treeArray.length - 1].getDataSource().getSolution().getId() == 4);
			
			//	usuniêcie drzewa
			treeManager.removeTree(treeID);
		
		} catch (PlatformException e1) {
			LOGGER.fatal("", e1);
		}
			
	}
	
	public void testGetTree()
	{
		INode testNode1 = new Node(null, "Node1", Node.Type.VALUE, "1");
		INode testNode2 = new Node(null, "Node2", Node.Type.VALUE, "1->2");
		INode testNode3 = new Node(null, "Node3", Node.Type.VALUE, "1->3");
		INode testNode4 = new Node(null, "Node4", Node.Type.VALUE, "2->4");
		INode testNode5 = new Node(null, "Node5", Node.Type.VALUE, "2->5");
		
		int testTreeId=0;
		ITree testTree;
		ITree receivedTree;
		
		testTree = new Tree();
		testTree.setId(666);
		testTree.setInfo("Informacja");
		testTree.setName("Drzewko testowe");
		
		testTree.setRoot(testNode1);
		testTree.getRoot().addChild(testNode2);
		testTree.getRoot().addChild(testNode3);
		testNode2.addChild(testNode4);
		testNode2.addChild(testNode5);
			
		try {
			testTree.setDataSource(treeManager.getTreeDataSource(666));
			testTreeId = treeManager.addTree(testTree);
			receivedTree = treeManager.getTree(testTreeId);
			assertTrue(testTreeId==receivedTree.getId());
			assertEquals(receivedTree.getInfo().compareTo("Informacja"),0);
			assertEquals(receivedTree.getName().compareTo("Drzewko testowe"),0);
			assertEquals(receivedTree.getId(),testTreeId);
			assertEquals(receivedTree.getRoot().getValue(),testTree.getRoot().getValue());
			treeManager.removeTree(testTreeId);
			
			testTree = null;
			assertNull("Adding null tree",treeManager.addTree(testTree));
			fail("Should raise an PlatformException");
			
		}catch (PlatformException e){
			LOGGER.fatal("", e);
		}catch (AssertionFailedError e)
		{
			try {
				treeManager.removeTree(testTreeId);
			} catch (PlatformException e1) {
				LOGGER.fatal("", e1);
			}
			throw e;
		}
	}
	
	public void testGetTreeDataSourceData()
	{
		IDataSource testDSs[];
		
		try 
		{	
			testDSs = treeManager.getTreeDataSources();
			LOGGER.debug("Datasources: " +testDSs.length);
			int location = -1;
			int i = 0;
			for (IDataSource source : testDSs) {
				if(source.getId()==666) location=i;
				else i++;
			}
			
			List<Object []> resultList = treeManager.getTreeDataSourceData(testDSs[location]);
			
			LOGGER.debug("Size of result list:" + resultList.size());
			LOGGER.debug("List:" + resultList.get(0)[0].toString());
		
			assertTrue(resultList.get(0)[0].toString().compareTo("1") == 0);
			assertTrue(resultList.get(0)[1].toString().compareTo("2") == 0);
			assertTrue(resultList.get(0)[2].toString().compareTo("3") == 0);
			assertTrue(resultList.get(0)[3].toString().compareTo("4") == 0);
			assertTrue(resultList.get(0)[4].toString().compareTo("YES") == 0);
					
		} catch (PlatformException e) {	
				LOGGER.fatal("", e);
		}
	}
	
	
}	