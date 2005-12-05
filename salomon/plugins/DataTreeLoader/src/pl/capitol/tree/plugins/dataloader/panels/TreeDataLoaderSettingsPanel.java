package pl.capitol.tree.plugins.dataloader.panels;


import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import salomon.platform.IDataEngine;
import salomon.platform.data.IColumn;
import salomon.platform.data.ITable;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.exception.PlatformException;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleString;


/**
 * @author pmisiuda
 *
 */
public class TreeDataLoaderSettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	//private static final SimpleString[] SimpleString = null;
	private ISettings settings;
	private IDataEngine dataEngine;
	
	private JLabel jLabelTable = null;
	private JLabel jLabelDecisioned = null;
	private JLabel jLabelDecisioning = null;
	
	DefaultListModel dListTable = new DefaultListModel(); 
	DefaultListModel dListDecisioned = new DefaultListModel(); 
	DefaultListModel dListDecisioning = new DefaultListModel();
	
	private JList jListTable = null;
	private JList jListDecisioned = null;
	private JList jListDecisioning = null;
	
	private JScrollPane jScrollListTable = null;
	private JScrollPane jScrollListDecisioned = null;
	private JScrollPane jScrollListDecisioning = null;

	private ITable [] listTable;
	private IColumn [] listDecisioned;
	private IColumn [] listDecisioning;
	private JLabel jLabelRangeFrom = null;
	private JTextField jTextFieldRangeFrom = null;
	private JLabel jLabelRangeTo = null;
	private JTextField jTextFieldRangeTo = null;
	
	private int actualDecisionedColumn = -1;
	private int lastDecisionedColumn = -1;
	private int actualTable = -1;
	
	/**
	 * This is the default constructor
	 * @throws PlatformException 
	 */
	public TreeDataLoaderSettingsPanel(ISettings settings, IDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;
		this.settings = settings;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws PlatformException 
	 */
	private  void initialize() {
		jLabelTable = new JLabel();
		jLabelTable.setBounds(new java.awt.Rectangle(5,5,100,20));
		jLabelTable.setText("Table");

		jLabelDecisioned = new JLabel();
		jLabelDecisioned.setBounds(new java.awt.Rectangle(130,5,120,20));
		jLabelDecisioned.setText("Decisioned column");
		
		jLabelDecisioning = new JLabel();
		jLabelDecisioning.setBounds(new java.awt.Rectangle(260,5,120,20));
		jLabelDecisioning.setText("Decisioning columns");
		
		jLabelRangeFrom = new JLabel();
		jLabelRangeFrom.setBounds(new java.awt.Rectangle(5,145,75,25));
		jLabelRangeFrom.setText(" Range from: ");
	
		jLabelRangeTo = new JLabel();
		jLabelRangeTo.setBounds(new java.awt.Rectangle(155,145,20,25));
		jLabelRangeTo.setText(" to: ");
	
		this.setLayout(null);
		this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		this.setPreferredSize(new java.awt.Dimension(487,188));
		this.setSize(451, 189);
		this.setName("settingsPanel");

		this.add(getJListTable(), null);
		this.add(getJListDecisioned(), null);
		this.add(getJListDecisioning(), null);
		this.add(jLabelTable, null);
		this.add(jLabelDecisioned, null);
		this.add(jLabelDecisioning, null);
		this.add(jLabelRangeFrom, null);
		this.add(getJTextFieldRangeFrom(), null);
		this.add(jLabelRangeTo, null);
		this.add(getJTextFieldRangeTo(), null);
		
		listTable = dataEngine.getMetaData().getTables();
		for (int i=0; i<listTable.length; i++)
		{
			dListTable.addElement(listTable[i].toString());
		}		
	}
	public ISettings getSettings() {
		//TODO umiescic z pol settingsy dla doJob()
		settings.setField("table", new SimpleString(jListTable.getSelectedValue().toString()));
		settings.setField("decisionedColumn", new SimpleString(jListDecisioned.getSelectedValue().toString()));
		
		String decisioningColumn = new String("decisioningColumn"); 
		Object [] selectedValues = jListDecisioning.getSelectedValues();
		for (int i=0; i<selectedValues.length; i++)
		{
			settings.setField(decisioningColumn+i, new SimpleString(selectedValues[i].toString()));
		}

		ITreeManager trm = dataEngine.getTreeManager();
		int tableSize = 0;
		int firstIndex = 0;
		int lastIndex = 0;
		
		try {
			 tableSize= trm.getTableSize(listTable[actualTable].toString());
		} catch (PlatformException e1) {
			e1.printStackTrace();
		}
			
		lastIndex = Integer.parseInt(this.jTextFieldRangeTo.getText());
		firstIndex = Integer.parseInt(this.jTextFieldRangeFrom.getText());
		
		if (lastIndex > tableSize)
			settings.setField("firstIndex", new SimpleInteger(tableSize));
		else
			settings.setField("firstIndex", new SimpleInteger(lastIndex));
		
		
		if (firstIndex < 1)
			settings.setField("lastIndex", new SimpleInteger(1));
		else
			settings.setField("lastIndex", new SimpleInteger(firstIndex));
			
		
		return settings;
	}


	public void setSettings(ISettings settings) {
		this.settings = settings;
	}
	public IDataEngine getDataEngine() {
		return dataEngine;
	}
	public void setDataEngine(IDataEngine dataEngine) {
		this.dataEngine = dataEngine;
	}
	
	/**
	 * This method initializes jListTable	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JScrollPane getJListTable() {
		if (jListTable == null) {
			jListTable = new JList(dListTable);
			jScrollListTable = new JScrollPane(jListTable);
			jScrollListTable.setBounds(new java.awt.Rectangle(5,25,120,100));
			jListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
			
			jListTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					actualTable = jListTable.getSelectedIndex();
					
					actualDecisionedColumn = -1;
					lastDecisionedColumn = -1;
					dListDecisioned.clear();
					dListDecisioning.clear();
					
					if (actualTable>-1){
						listDecisioned = listTable[actualTable].getColumns();
						for (int i=0; i<listDecisioned.length; i++)
						{
							dListDecisioned.addElement(listDecisioned[i].getName());
						}
						
						listDecisioning = listTable[actualTable].getColumns();
						for (int i=0; i<listDecisioning.length; i++)
						{
							dListDecisioning.addElement(listDecisioning[i].getName());
						}
					}
					
					ITreeManager trm = dataEngine.getTreeManager();
					try {
						jTextFieldRangeTo.setText(String.valueOf(trm.getTableSize(listTable[actualTable].toString())));
						jTextFieldRangeFrom.setText("1");
						
					} catch (PlatformException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					

				}
			});
		}
		return jScrollListTable;
	}
	/**
	 * This method initializes jListDecisioned	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JScrollPane getJListDecisioned() {
		if (jListDecisioned == null) {
			jListDecisioned = new JList(dListDecisioned);
			jScrollListDecisioned = new JScrollPane(jListDecisioned);
			jScrollListDecisioned.setBounds(new java.awt.Rectangle(130,25,120,100));
			jListDecisioned.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			jListDecisioned.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					jListDecisioning.clearSelection();
					
					actualDecisionedColumn = jListDecisioned.getSelectedIndex();
					
					if (lastDecisionedColumn>-1 & actualDecisionedColumn>-1){
						dListDecisioning.add(lastDecisionedColumn, listDecisioning[lastDecisionedColumn].getName()); 
					}


					if (actualDecisionedColumn>-1){
						dListDecisioning.remove(actualDecisionedColumn);
						lastDecisionedColumn = actualDecisionedColumn;						
					}

				}
			});
		}
		return jScrollListDecisioned;
	}
	/**
	 * This method initializes jListDecisioning	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JScrollPane getJListDecisioning() {
		if (jListDecisioning == null) {
			jListDecisioning = new JList(dListDecisioning);
			jScrollListDecisioning = new JScrollPane(jListDecisioning);
			jScrollListDecisioning.setBounds(new java.awt.Rectangle(260,25,120,150));
			jListDecisioning.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			
			jListDecisioning.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
				}
			});
		}
		return jScrollListDecisioning;
	}
	/**
	 * This method initializes jTextFieldRangeFrom	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldRangeFrom() {
		if (jTextFieldRangeFrom == null) {
			jTextFieldRangeFrom = new JTextField();
			jTextFieldRangeFrom.setBounds(new java.awt.Rectangle(80,145,75,25));
		}
		return jTextFieldRangeFrom;
	}
	/**
	 * This method initializes jTextFieldRangeTo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldRangeTo() {
		if (jTextFieldRangeTo == null) {
			jTextFieldRangeTo = new JTextField();
			jTextFieldRangeTo.setBounds(new java.awt.Rectangle(175,145,75,25));
		}
		return jTextFieldRangeTo;
	}
	
	
	
 }  //  @jve:decl-index=0:visual-constraint="21,17"