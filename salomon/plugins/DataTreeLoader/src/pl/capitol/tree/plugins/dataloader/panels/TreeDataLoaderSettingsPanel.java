package pl.capitol.tree.plugins.dataloader.panels;


import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import salomon.platform.IDataEngine;
import salomon.platform.data.IColumn;
import salomon.platform.data.ITable;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;
import javax.swing.JTextField;


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
	
	private int actualDecisionedColumn = -2;
	private int lastDecisionedColumn = -2;
	/**
	 * This is the default constructor
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
		
		/*
		 * nie dzialalo:/
		Object [] selectedValues = jListDecisioning.getSelectedValues();
		
		String [] selectedValuesS = new String[selectedValues.length];
		for (int i=0; i<selectedValues.length; i++)
		{
			selectedValuesS[i] = (String)selectedValues[i];
			System.out.println(selectedValuesS[i]);
		}
		
		
		SimpleString [] selectedValuesSS = new SimpleString[selectedValuesS.length];
		for (int i=0; i<selectedValuesS.length; i++)
		{
			selectedValuesSS[i] = new SimpleString(selectedValuesS[i]);
			System.out.println(selectedValuesSS[i].getValue());
		}
		
		System.out.println("aaaaa"); 
		
		SimpleArray selectedValuesSA = new SimpleArray(selectedValuesSS);
		
		System.out.println("bbbbb");
		
		SimpleString [] decisioningColumnsSS = (SimpleString[]) selectedValuesSA.getValue();
		
		System.out.println("cccccc");
		
		for (int i=0; i<decisioningColumnsSS.length; i++)
		{
			System.out.println(decisioningColumnsSS[i].getValue());
		}
		
		settings.setField("decisioningColumns", selectedValuesSA);
		 * 
		 */
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
					listDecisioned = listTable[jListTable.getSelectedIndex()].getColumns();
					dListDecisioned.clear();
					for (int i=0; i<listDecisioned.length; i++)
					{
						dListDecisioned.addElement(listDecisioned[i].getName());
					}
					
					listDecisioning = listTable[jListTable.getSelectedIndex()].getColumns();
					dListDecisioning.clear();
					for (int i=0; i<listDecisioning.length; i++)
					{
						dListDecisioning.addElement(listDecisioning[i].getName());
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
					System.out.println("mouseClicked()"); 
					
					jListDecisioning.clearSelection();
					
					actualDecisionedColumn = jListDecisioned.getSelectedIndex();
					
					if (lastDecisionedColumn>-2 & actualDecisionedColumn!=lastDecisionedColumn){
						dListDecisioning.add(lastDecisionedColumn, listDecisioning[lastDecisionedColumn].getName()); 
					}


					if (actualDecisionedColumn>-2){
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
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
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