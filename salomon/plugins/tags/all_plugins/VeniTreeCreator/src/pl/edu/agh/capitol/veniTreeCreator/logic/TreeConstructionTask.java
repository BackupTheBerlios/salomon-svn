/**
 * 
 */
package pl.edu.agh.capitol.veniTreeCreator.logic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import pl.edu.agh.capitol.veniTreeCreator.logic.DataItem;
import pl.edu.agh.capitol.veniTreeCreator.logic.TreeItem;

import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.INode;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.exception.PlatformException;

/**
 * @author Lukasz Ostatek Klasa b�d�ca abstrakcj� zadania tworzenia drzewa
 *         decyzyjnego.
 */
public class TreeConstructionTask {

	TreeItem root = null;

	Vector<TreeItem> treeElements = new Vector<TreeItem>();

	DataItem descriptions;

	Vector<Vector<String>> distinctClasses = new Vector<Vector<String>>();

	Vector<String> distinctObjectives = new Vector<String>();

	Hashtable<String, Hashtable<String, String>> propertyValues = new Hashtable<String, Hashtable<String, String>>();

	Vector<Boolean> used = new Vector<Boolean>();

	private static final Logger LOGGER = Logger
			.getLogger(TreeConstructionTask.class);

	public TreeConstructionTask() {

	}

	/**
	 * Pobiera warto�� atrybutu <code>prop</code> dla elementu o id
	 * <code>id</code>
	 * 
	 * @param id -
	 *            id elementu
	 * @param prop -
	 *            nazwa atrybutu
	 * @return warto�� atrybutu
	 */
	public String getValueOfProp(String id, String prop) {

		try {
			return propertyValues.get(id).get(prop).toString();
		} catch (Throwable th) {
			return "undef";
		}
	}

	/**
	 * Zwraca rezultat oblicze� w postaci obiektu implementuj�cego interfejs
	 * <code>ITree</code>
	 * 
	 * @param iTreeManager -
	 *            manager drzewek - coby mo�naby�o zapisa� do bazy
	 * @param ds -
	 *            <code>IDataSource</code> na podstawie ktorego stworzone
	 *            drzewko
	 * @return obiekt implementuj�cy intefejs <code>ITree</code>
	 * @throws PlatformException
	 */
	public int returnResult(ITreeManager iTreeManager, IDataSource ds)
			throws PlatformException {
		ITree gefco = iTreeManager.createTree();
		gefco.setDataSource(ds);
		gefco.setCreateDate(new Date(System.currentTimeMillis()));
		gefco.setInfo("Created with VeniTreeCreator");
		gefco.setName("VeniTreeCreator generated tree");

		// przepisanie node'ow

		// strasznie mi�kkie :/
		HashMap hm = new HashMap<TreeItem, INode>();

		for (TreeItem ti : treeElements) {
			INode parent = null;
			if (ti.parent != null) {
				parent = (INode) hm.get(ti.getParent());
			}
			INode in = null;
			if (ti.getRoadMap().size() > 0) {// nie ROOT
				if (ti.getElements().size() > 0) {// niepusty
					in = iTreeManager
							.createNode(
									parent,
									ti.getRoadMap().lastElement()
											+ " = "
											+ ti
													.getElements()
													.elementAt(0)
													.getAttributeAt(
															descriptions
																	.getAttributes()
																	.indexOf(
																			ti
																					.getRoadMap()
																					.lastElement())),
									(ti.isLeaf() ? INode.Type.VALUE
											: INode.Type.COLUMN),
									 ti.isLeaf() ?
													ti.elements.elementAt(0).getObjective():"");
				}
			} else {// ROOT
				in = iTreeManager.createNode(parent, "",
						(ti.isLeaf() ? INode.Type.VALUE : INode.Type.COLUMN),
						"");
				gefco.setRoot(in);
			}
			hm.put(ti, in);
		}
		int id = iTreeManager.addTree(gefco);

		return id;
	}

	/**
	 * Inicjalizuje zadanie tworzenia drzewa danymi z IDataSource'a
	 * 
	 * @param ds
	 *            obiekt implementuj�cy interfejs IDataSource
	 * @param data
	 *            dane zwr�cene przez <code>ITreeManager</code>'a
	 */
	public void loadFromDataSource(IDataSource ds, List<Object[]> data) {
		root = new TreeItem();
		treeElements.add(root);
		String[] decisioningColumns = ds.getDecioningColumns();
		String decisionedColumn = ds.getDecisionedColumn();
		descriptions = new DataItem(decisioningColumns, decisionedColumn);
		for (Object[] objs : data) {
			Hashtable<String, String> hT = new Hashtable<String, String>();
			String dec = objs[0].toString();
			String[] decs = new String[objs.length - 1];
			for (int i = 1; i < objs.length; i++) {
				decs[i - 1] = objs[i].toString();
				hT.put(decisioningColumns[i - 1], objs[i].toString());
			}
			DataItem di = new DataItem(decs, dec);
			root.elements.add(di);
			propertyValues.put(di.getName(), hT);
		}
		for (int i = 0; i < root.elements.elementAt(0).getAttributeCount(); i++) {
			distinctClasses.add(getDistinctClasses(i));
			used.add(false);
		}
		distinctObjectives.addAll(getDistinctClassesFromObjetives());
	}

	/**
	 * Metoda u�ywana we wczesnej fazie rozwoju pluginu. Laduje dane z pliku
	 * tekstowego.
	 * 
	 * @deprecated
	 * @param filename
	 * @param objectiveIndex
	 */
	public void loadFromFile(String filename, int objectiveIndex) {
		String thisLine;
		root = new TreeItem();
		treeElements.add(root);
		try {
			FileInputStream fin = new FileInputStream(filename);
			BufferedReader myInput = new BufferedReader(new InputStreamReader(
					fin));
			if ((thisLine = myInput.readLine()) != null) {
				System.out.println(thisLine);
				descriptions = new DataItem(thisLine, objectiveIndex);
			}
			while ((thisLine = myInput.readLine()) != null) {
				System.out.println(thisLine);
				root.elements.add(new DataItem(thisLine, objectiveIndex));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < root.elements.elementAt(0).getAttributeCount(); i++) {
			distinctClasses.add(getDistinctClasses(i));
			used.add(false);
		}
		distinctObjectives.addAll(getDistinctClassesFromObjetives());
	}

	/**
	 * Pomocnicza metoda sprawdzaj�ca czy wszystkie w�z�y s� homogeniczne
	 * 
	 * @return warto�� logiczna T/F
	 */
	boolean isAllHomogenous() {
		for (TreeItem ti : treeElements)
			if (ti.isLeaf())
				if (!ti.isHomogenous())
					return false;
		return true;
	}

	/**
	 * Metoda pomocnicza dokonuj�ca ekstrakcji roz��cznych klas atrybut�w z
	 * danych
	 * 
	 * @param attributeIndex -
	 *            ilo�� atrybut�w
	 * @return klasy atrybut�w
	 */
	Vector<String> getDistinctClasses(int attributeIndex) {
		Vector<String> out = new Vector<String>();
		String pom = null;
		for (DataItem di : root.elements) {
			if (!out.contains((pom = di.getAttributeAt(attributeIndex))))
				out.add(pom);
		}
		return out;
	}

	/**
	 * Metoda pomocnicza dokonuj�ca ekstrakcji roz��cznych klas warto�ci z
	 * danych
	 * 
	 * @return klasy warto�ci
	 */
	Vector<String> getDistinctClassesFromObjetives() {
		Vector<String> out = new Vector<String>();
		String pom = null;
		for (DataItem di : root.elements) {
			if (!out.contains((pom = di.getObjective())))
				out.add(pom);
		}
		return out;
	}

	/**
	 * Dokonuje rozwini�cia drzewa wg zadanego atrybutu
	 * 
	 * @param vt
	 *            wektor element�w drzewa decyzyjnego
	 * @param attribute -
	 *            indeks atrybutu wzgledem kt�rego rozwijamy drzewo
	 * @return wektor element�w nowego drzewa
	 */
	Vector<TreeItem> splitBy(Vector<TreeItem> vt, int attribute) {
		Vector<TreeItem> out = new Vector<TreeItem>();
		for (TreeItem ti : vt) {
			out.addAll(splitBy(ti, attribute));
		}
		return out;
	}

	/**
	 * Metoda pomocnicza dokonuj�ca rozwini�cia pojedynczego elementu drzewa
	 * decyzjnego
	 * 
	 * @param ti
	 * @param attribute
	 * @return nowy wektor element�w drzewa powsta�y z podzia�u li�cia
	 */
	Vector<TreeItem> splitBy(TreeItem ti, int attribute) {
		Vector<TreeItem> out = new Vector<TreeItem>();
		for (String klasa : distinctClasses.elementAt(attribute)) {
			out.add(ti.subTreeItem(attribute, klasa));
		}
		return out;
	}

	/**
	 * Metoda matematyczna obliczaj�ca �redni� entropi� w drzewie wzgledem
	 * zadanego atrybutu
	 * 
	 * @param vt
	 *            wektor element�w drzewa
	 * @param attribute
	 *            indeks atrybutu do oblicze�
	 * @return warto�� <0,1>
	 */
	double calculateAverageEntropy(Vector<TreeItem> vt, int attribute) {
		double entropy = 0.0f;
		int totalSize = 0;
		for (TreeItem ti : vt) {
			totalSize += ti.elements.size();
		}
		for (TreeItem ti : splitBy(vt, attribute)) {
			if (ti.isLeaf()) {
				entropy += (((double) ti.elements.size()) / ((double) totalSize))
						* ti.calculateEntropy(distinctObjectives);
			} else {
				System.out.println("Improper state");
			}
		}
		System.out
				.println("Average entropy for attrib="
						+ descriptions.getAttributeAt(attribute) + " equals "
						+ entropy);
		return entropy;
	}

	/**
	 * Metoda pomocnicza zwracaj�ca wszystkie li�cie drzewa
	 * 
	 * @return wektor li�ci
	 */
	Vector<TreeItem> getLeafs() {
		Vector<TreeItem> out = new Vector<TreeItem>();
		for (TreeItem ti : treeElements) {
			if (ti.isLeaf())
				out.add(ti);
		}
		return out;
	}

	/**
	 * Metoda dokonuj�ca rozwini�cia wg zadanego atrybutu.
	 * 
	 * @param attribute -
	 *            indeks atrybutu
	 */
	void expandTree(int attribute) {
		System.out.println("Expanding by attr:" + attribute);
		for (TreeItem ti : this.getLeafs()) {
			if (!ti.isHomogenous()) { // nie rozwijam homogenicznych node'�w
				// TODO zeby w ogole ich nie brac przy wyliczaniu entropii

				Vector<TreeItem> exps = splitBy(ti, attribute);
				for (TreeItem tiIn : exps) {
					tiIn.setParent(ti);
					for (String elem : ti.getRoadMap())
						tiIn.addToRoadMap(elem);
					tiIn.addToRoadMap(descriptions.getAttributeAt(attribute));
				}
				treeElements.addAll(exps);
				ti.setLeaf(false);
			}
		}
		used.setElementAt(true, attribute);
	}

	/**
	 * Metoda wybieraj�ca najlepszy dost�pny atrybut (ten kt�ry minimalizuje
	 * entropi�)
	 * 
	 * @return indewks atrybutu
	 */
	int getBestAvailableAttribute() {
		int bestNo = -1;
		double bestEntropy = 2;
		double tempEntropy;
		for (int i = 0; i < used.size(); i++) {
			if (!used.elementAt(i))
				if ((tempEntropy = calculateAverageEntropy(getLeafs(), i)) < bestEntropy) {
					bestEntropy = tempEntropy;
					bestNo = i;
				}
		}
		System.out.println("BestAvailable =" + bestNo + " (" + bestEntropy
				+ ")");
		return bestNo;
	}

	/**
	 * Test logiczny czy jest mo�liwe jeszcze rozwijanie wzgl�dem jakiegokolwiek
	 * atrybutu.
	 * 
	 * @return warto�� T/F
	 */
	boolean anyAvailable() {
		for (Boolean us : used) {
			if (!us)
				return true;
		}
		return false;
	}

	/**
	 * Metoda inicjalizuj�ca tworzenia drzwa
	 * 
	 */
	public void createTree() {
		LOGGER.info("----Tree creation start----");
		while ((anyAvailable()) && (!isAllHomogenous())) {
			LOGGER.info("----Tree creation - in loop----");
			expandTree(getBestAvailableAttribute());
		}
		LOGGER.info("----Tree creation end----");
		this.printLeavesOnly();
	}

	/**
	 * Metoda wypisuj�ca dzewo decyzyjne
	 * 
	 */
	public void print() {
		LOGGER.info("----Tree start----");
		for (TreeItem ti : treeElements)
			ti.print();
		LOGGER.info("----Tree end----");
	}

	/**
	 * Metoda wypisuj�ca drzwo decyzyjne (tylko li�cie)
	 * 
	 */
	public void printLeavesOnly() {
		LOGGER.info("----Tree start----");
		for (TreeItem ti : treeElements)
			if (ti.isLeaf())
				ti.print();
		LOGGER.info("----Tree end----");
	}
}
