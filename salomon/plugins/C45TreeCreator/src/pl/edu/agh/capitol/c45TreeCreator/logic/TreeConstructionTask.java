/**
 * 
 */
package pl.edu.agh.capitol.c45TreeCreator.logic;

import java.sql.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import pl.edu.agh.capitol.c45TreeCreator.logic.DataItem;
import pl.edu.agh.capitol.c45TreeCreator.logic.TreeItem;

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

	private double confidenceLevel = 1;

	TreeItem root = null;

	Vector<TreeItem> treeElements = new Vector<TreeItem>();

	DataItem descriptions;

	Vector<Vector<String>> distinctClasses = new Vector<Vector<String>>();

	Vector<String> distinctObjectives = new Vector<String>();

	Hashtable<String, Hashtable<String, String>> propertyValues = new Hashtable<String, Hashtable<String, String>>();

	Vector<Boolean> used = new Vector<Boolean>();

	Vector<Boolean> isContignous = new Vector<Boolean>();

	boolean multipleContinuousAttributeExpansions = true;// czy mozna

	// dokonywac
	// podzialow
	// wzgledem
	// atrybutow
	// ciaglych
	// wielokrotnie

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
		gefco.setInfo("Created with C45TreeCreator");
		gefco.setName("C45TreeCreator generated tree");

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
									ti.isLeaf() ? (ti.getParent() != null
											&& ti.getParent().partitionEdge != Double.NaN ? ti.sign
											+ Double
													.toString(ti.getParent().partitionEdge)
											: ti.elements.elementAt(0)
													.getObjective())
											: "");
					// bra�em edge z parenta (czyli np. podzia� wg granicy 75) a
					// znak z dzieci (czyli np. ">=")
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
				if (objs[i] != null) {
					decs[i - 1] = objs[i].toString();
					hT.put(decisioningColumns[i - 1], objs[i].toString());
				}
			}
			DataItem di = new DataItem(decs, dec);
			root.elements.add(di);
			propertyValues.put(di.getName(), hT);
		}
		for (int i = 0; i < root.elements.elementAt(0).getAttributeCount(); i++) {
			distinctClasses.add(getDistinctClasses(i));
			used.add(false);
		}
		// FIXME przekazywanie ci�g�o�ci powinno by� za pomoc� DS'a - do poprawy
		// po przeniesieniu na managera atybut�w
		// bo to jest bardzo sztuczne ;)
		for (int i = 0; i < root.elements.elementAt(0).getAttributeCount(); i++) {
			if (ifContinuous(i))
				isContignous.add(new Boolean(true));
			else
				isContignous.add(new Boolean(false));
		}

		distinctObjectives.addAll(getDistinctClassesFromObjetives());
	}

	/**
	 * Meteda decyduje czy traktowa� atrybut jako ci�g�y
	 * 
	 * @param i
	 * @return
	 */
	private boolean ifContinuous(int i) {
		if (distinctClasses.elementAt(i).size() * 2 <= root.elements.size())
			return false;
		try {
			Double.parseDouble(root.elements.elementAt(0).getAttributeAt(i));
		} catch (Throwable th) {
			return false;
		}
		return true;
	}

	/**
	 * Pomocnicza metoda sprawdzaj�ca czy wszystkie w�z�y s� homogeniczne
	 * 
	 * @return warto�� logiczna T/F
	 */
	boolean isAllHomogenous(double confidenceLevel) {
		for (TreeItem ti : treeElements)
			if (ti.isLeaf())
				if (!ti.isHomogenous(confidenceLevel))
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
	 * Dokonuje rozwini�cia drzewa wg zadanego atrybutu ci�g�ego
	 * 
	 * @param vt
	 *            wektor element�w drzewa decyzyjnego
	 * @param attribute -
	 *            indeks atrybutu wzgledem kt�rego rozwijamy drzewo
	 * @param enge
	 *            granica podzia�u
	 * @return wektor element�w nowego drzewa
	 */
	Vector<TreeItem> splitBy(Vector<TreeItem> vt, int attribute, double edge) {
		Vector<TreeItem> out = new Vector<TreeItem>();
		for (TreeItem ti : vt) {
			out.addAll(splitBy(ti, attribute, edge));
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
	 * Metoda pomocnicza dokonuj�ca rozwini�cia pojedynczego elementu drzewa
	 * decyzjnego wg granicy (element ci�g�y)
	 * 
	 * @param ti
	 * @param attribute
	 * @return nowy wektor element�w drzewa powsta�y z podzia�u li�cia
	 */
	Vector<TreeItem> splitBy(TreeItem ti, int attribute, double edge) {
		Vector<TreeItem> out = new Vector<TreeItem>();
		out.add(ti.subTreeItemHi(attribute, edge));
		out.add(ti.subTreeItemLo(attribute, edge));
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

	double calculateAverageEntropyCont(Vector<TreeItem> vt, int attribute) {
		double bestEntr = Double.MAX_VALUE;
		for (TreeItem ti : this.getLeafs()) {
			if (!ti.isHomogenous(getConfidenceLevel())) { // nie rozwijam
				// homogenicznych
				// node'�w
				// TODO zeby w ogole ich nie brac przy wyliczaniu entropii

				for (DataItem di : ti.elements) {
					double tmpEdge = Double.parseDouble(di
							.getAttributeAt(attribute));
					/*
					 * TreeItem hi = ti.subTreeItemHi(attribute, tmpEdge);
					 * TreeItem lo = ti.subTreeItemLo(attribute, tmpEdge);
					 */
					Vector<TreeItem> vtT = splitBy(ti, attribute, tmpEdge);
					double ent = (vtT.elementAt(0).elements.size() / (vtT
							.elementAt(0).elements.size() + vtT.elementAt(1).elements
							.size()))
							* vtT.elementAt(0).calculateEntropy(
									distinctObjectives)
							+ (vtT.elementAt(1).elements.size() / (vtT
									.elementAt(0).elements.size() + vtT
									.elementAt(1).elements.size()))
							* vtT.elementAt(1).calculateEntropy(
									distinctObjectives);
					if (ent < bestEntr) {
						bestEntr = ent;
					}
				}
			}
		}
		return bestEntr;
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
	double calculateAverageEntropy(Vector<TreeItem> vt, int attribute,
			double edge) {
		double entropy = 0.0f;
		int totalSize = 0;
		for (TreeItem ti : vt) {
			totalSize += ti.elements.size();
		}
		Vector<TreeItem> ti = splitBy(vt, attribute, edge);
		// dla hi i dla lo
		entropy += (((double) ti.size()) / ((double) totalSize))
				* ti.elementAt(0).calculateEntropy(distinctObjectives);
		entropy += (((double) ti.size()) / ((double) totalSize))
				* ti.elementAt(1).calculateEntropy(distinctObjectives);

		System.out
				.println("Average entropy (cont) for attrib="
						+ descriptions.getAttributeAt(attribute) + " equals "
						+ entropy);
		return entropy;
	}

	/**
	 * Metoda matematyczna obliczaj�ca informacj� w drzewie
	 * 
	 * @param vt
	 *            wektor element�w drzewa
	 * @param attribute
	 *            indeks atrybutu do oblicze�
	 * @return warto�� <0,1>
	 */
	double calculateInfo(Vector<TreeItem> vt) {
		double info = 0.0f;
		int totalSize = 0;
		for (TreeItem ti : vt) {
			totalSize += ti.elements.size();
		}
		for (TreeItem ti : vt) {
			if (ti.isLeaf()) {
				info += (((double) ti.elements.size()) / ((double) totalSize))
						* ti.calculateEntropy(distinctObjectives);
			} else {
				System.out.println("Improper state");
			}
		}
		System.out.println("Info equals " + info);
		return info;
	}

	/**
	 * Metoda matematyczna obliczaj�ca SplitInfo
	 * 
	 * @param vt
	 *            wektor element�w drzewa
	 * @param attribute
	 *            indeks atrybutu do oblicze�
	 * @return warto�� <0,1>
	 */
	double calculateSplitInfo(Vector<TreeItem> vt) {
		double splitInfo = 0.0f;
		int totalSize = 0;
		for (TreeItem ti : vt) {
			totalSize += ti.elements.size();
		}
		for (TreeItem ti : vt) {
			if (ti.isLeaf()) {
				splitInfo += (((double) ti.elements.size()) / ((double) totalSize))
						* (Math.log10((((double) ti.elements.size()))
								/ ((double) totalSize)) / Math.log10(2));
			} else {
				System.out.println("Improper state");
			}
		}
		System.out.println("Split info equals " + splitInfo);
		return splitInfo;
	}

	/**
	 * Metoda obliczaj�ca warto�� Gain
	 * 
	 * @param vt
	 *            wektor element�w drzewa
	 * @param attribute
	 *            indeks atrybutu do oblicze�
	 * @return
	 */
	double calculateGain(Vector<TreeItem> vt, int attribute) {
		return calculateAverageEntropy(vt, attribute) / calculateInfo(vt);
	}

	/**
	 * Metoda obliczaj�ca warto�� <code>Gain</code>
	 * 
	 * @param vt
	 *            wektor element�w drzewa
	 * @param attribute
	 *            indeks atrybutu do oblicze�
	 * @return
	 */
	double calculateGainForContignous(Vector<TreeItem> vt, int attribute,
			double edge) {
		return calculateAverageEntropy(vt, attribute, edge) / calculateInfo(vt);
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
		if (isContignous.elementAt(attribute)) {
			System.out.println("Attr is contignous");
			for (TreeItem ti : this.getLeafs()) {
				if (!ti.isHomogenous(getConfidenceLevel())) { // nie rozwijam
					// homogenicznych
					// node'�w
					// TODO zeby w ogole ich nie brac przy wyliczaniu entropii

					double bestEdge = -1;
					double bestEntr = Double.MAX_VALUE;
					for (DataItem di : ti.elements) {
						double tmpEdge = Double.parseDouble(di
								.getAttributeAt(attribute));
						/*
						 * TreeItem hi = ti.subTreeItemHi(attribute, tmpEdge);
						 * TreeItem lo = ti.subTreeItemLo(attribute, tmpEdge);
						 */
						Vector<TreeItem> vtT = splitBy(ti, attribute, tmpEdge);
						double ent = ((double) vtT.elementAt(0).elements.size() / ((double) vtT
								.elementAt(0).elements.size() + (double) vtT
								.elementAt(1).elements.size()))
								* vtT.elementAt(0).calculateEntropy(
										distinctObjectives)
								+ ((double) vtT.elementAt(1).elements.size() / ((double) vtT
										.elementAt(0).elements.size() + (double) vtT
										.elementAt(1).elements.size()))
								* vtT.elementAt(1).calculateEntropy(
										distinctObjectives);
						if (ent < bestEntr) {
							bestEntr = ent;
							bestEdge = tmpEdge;
						}
					}
					Vector<TreeItem> exps = splitBy(ti, attribute, bestEdge);

					exps.elementAt(0).setParent(ti);
					for (String elem : ti.getRoadMap())
						exps.elementAt(0).addToRoadMap(elem);
					exps.elementAt(0).addToRoadMap(
							descriptions.getAttributeAt(attribute));

					exps.elementAt(1).setParent(ti);
					for (String elem : ti.getRoadMap())
						exps.elementAt(1).addToRoadMap(elem);
					exps.elementAt(1).addToRoadMap(
							descriptions.getAttributeAt(attribute));

					treeElements.addAll(exps);
					ti.setLeaf(false);
					ti.partitionEdge = bestEdge;
					exps.elementAt(0).sign = ">";
					exps.elementAt(1).sign = "<=";
				}
			}
			if (!multipleContinuousAttributeExpansions)
				used.setElementAt(true, attribute);

		} else {
			System.out.println("Attr is not contignous");
			for (TreeItem ti : this.getLeafs()) {
				if (!ti.isHomogenous(getConfidenceLevel())) { // nie rozwijam
					// homogenicznych
					// node'�w
					// TODO zeby w ogole ich nie brac przy wyliczaniu entropii

					Vector<TreeItem> exps = splitBy(ti, attribute);
					for (TreeItem tiIn : exps) {
						tiIn.setParent(ti);
						for (String elem : ti.getRoadMap())
							tiIn.addToRoadMap(elem);
						tiIn.addToRoadMap(descriptions
								.getAttributeAt(attribute));
					}
					treeElements.addAll(exps);
					ti.setLeaf(false);
				}
			}
			used.setElementAt(true, attribute);

		}
	}

	/**
	 * Metoda wybieraj�ca najlepszy dost�pny atrybut (ten kt�ry minimalizuje
	 * entropi�)
	 * 
	 * @return indewks atrybutu
	 */
	int getBestAvailableAttribute() {
		// FIXME sprawdzanie dla ci�g�ych atrybut�w
		int bestNo = -1;
		double bestEntropy = 2;
		double tempEntropy;
		for (int i = 0; i < used.size(); i++) {
			if (!used.elementAt(i))
				if (isContignous.elementAt(i)) {
					if ((tempEntropy = calculateAverageEntropyCont(getLeafs(),
							i)) < bestEntropy) {
						bestEntropy = tempEntropy;
						bestNo = i;
					}
				} else {
					if ((tempEntropy = calculateAverageEntropy(getLeafs(), i)) < bestEntropy) {
						bestEntropy = tempEntropy;
						bestNo = i;
					}
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
		// FIXME sprawdzanie ciaglych atrybutow - zeby nie bylo nieskonczonych
		// poszukiwan
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
		while ((anyAvailable()) && (!isAllHomogenous(getConfidenceLevel()))) {
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

	public void setConfidenceLevel(double confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	public double getConfidenceLevel() {
		return confidenceLevel;
	}
}
