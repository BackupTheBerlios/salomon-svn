package salomon.engine.platform.data.tree;

import java.util.Date;

import salomon.engine.database.DBManager;
import salomon.engine.solution.ShortSolutionInfo;

import junit.framework.TestCase;

public class DataSourceTest extends TestCase
{
	Date data;
	String columns[];
	ShortSolutionInfo sInfo;
	DBManager _manager;
	
	protected void setUp() throws Exception
	{
		data = new Date();
		columns = new String[3];
		columns[0] = "Kolumna pierwsza";
		columns[1] = "Kolumna druga";
		columns[2] = "Kolumna trzecia";
		
		ShortSolutionInfo sInfo;
		//sInfo = new ShortSolutionInfo(1);
		
		_manager = new DBManager();
		_manager.connect();
		
		super.setUp();
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.DataSource(Date, String[], String, int, String, String, ShortSolutionInfo)'
	 */
	public void testDataSourceDateStringArrayStringIntStringStringShortSolutionInfo()
	{
		// jak stworzyc sobie obiekt klasy ShortSolutionInfo
		//DataSource test1 = new DataSource(data, columns, "Decisioned column", 1, "Informacja", "Nazwa", new ShortSolutionInfo(1));
		
		
		
	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.DataSource(ShortSolutionInfo)'
	 */
	public void testDataSourceShortSolutionInfo()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.getTableName()'
	 */
	public void testGetTableName()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.setTableName(String)'
	 */
	public void testSetTableName()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.setId(int)'
	 */
	public void testSetId()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.getCreateDate()'
	 */
	public void testGetCreateDate()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.setCreateDate(Date)'
	 */
	public void testSetCreateDate()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.getDecioningColumns()'
	 */
	public void testGetDecioningColumns()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.setDecioningColumns(String[])'
	 */
	public void testSetDecioningColumns()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.getDecisionedColumn()'
	 */
	public void testGetDecisionedColumn()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.setDecisionedColumn(String)'
	 */
	public void testSetDecisionedColumn()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.getId()'
	 */
	public void testGetId()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.getInfo()'
	 */
	public void testGetInfo()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.setInfo(String)'
	 */
	public void testSetInfo()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.getName()'
	 */
	public void testGetName()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.setName(String)'
	 */
	public void testSetName()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.getSolution()'
	 */
	public void testGetSolution()
	{

	}

	/*
	 * Test method for 'salomon.engine.platform.data.tree.DataSource.setSolution(ShortSolutionInfo)'
	 */
	public void testSetSolution()
	{

	}

}
