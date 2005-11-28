/**
 * 
 */
package pl.edu.agh.capitol.c45TreeCreator.logic;

import java.util.Vector;

import pl.edu.agh.capitol.c45TreeCreator.logic.DataItem;

/**
 * Klasa reprezentuj¹ca wêze³ drzewa decyzyjnego
 * 
 * @author Lukasz Ostatek
 */
public class TreeItem {

	/**
	 * 
	 */

	Vector<DataItem> elements = new Vector<DataItem>();

	Vector<String> roadMap = new Vector<String>();

	boolean isLeaf = true;

	TreeItem parent = null;

	double partitionEdge; // granica podzia³u dla ci¹g³ych zmiennych <= - A, >

	// - B

	public TreeItem() {

	}

	/**
	 * Test logiczny czy wêze³ jest liœciem
	 * 
	 * @return wartoœæ T/F
	 */
	public boolean isLeaf() {
		return isLeaf;
	}

	/**
	 * Ustawia wartoœæ logiczn¹ - czy wêze³ jest liœciem
	 * 
	 * @param isLeaf
	 *            wartoœæ logiczna T/F
	 */
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * pobiera elementy wêz³a
	 * 
	 * @return elementy (implementuj¹ce <code>IDataItem</code>) wêz³a
	 */
	public Vector<DataItem> getElements() {
		return elements;
	}

	/**
	 * test logiczny czyc wszystkie elemeny wêz³a s¹ homogeniczne
	 * 
	 * @return wartoœæ T/F
	 */
	public boolean isHomogenous() {
		String value = null;
		for (DataItem dt : elements) {
			if ((value != null) && (!dt.getObjective().equals(value)))
				return false;
			value = dt.getObjective();
		}
		return true;
	}

	/**
	 * Metoda pomocnicza obliczaj¹ca entropiê wêz³a
	 * 
	 * @param classes
	 *            klasy wartoœci
	 * @return entropia
	 */
	public double calculateEntropy(Vector<String> classes) {
		double entropy = 0.0f;
		int total = elements.size();
		for (String klasa : classes) {
			int elemsInClass = 0;
			for (DataItem elem : elements) {
				if (elem.getObjective().equals(klasa))
					elemsInClass++;
			}
			if (elemsInClass > 0)
				entropy += ((-((double) elemsInClass) / ((double) total)) * (Math
						.log10((((double) elemsInClass)) / ((double) total)) / Math
						.log10(2)));
		}
		System.out.println("Entropy=" + entropy);
		return entropy;
	}

	/**
	 * Metoda pomocnicza zwracaj¹ca elementy poddrzewa spe³niaj¹ce kryteria
	 * 
	 * @param attribute
	 *            atrybut
	 * @param value
	 *            wartoœæ
	 * @return element <code>TreeItem</code>
	 */
	public TreeItem subTreeItem(int attribute, String value) {
		TreeItem out = new TreeItem();
		for (DataItem ti : this.elements) {
			if (ti.getAttributeAt(attribute).equals(value))
				out.elements.add(ti);
		}
		return out;
	}

	/**
	 * Metoda pomocnicza zwracaj¹ca elementy mniejsze lub równe <code>val</code>
	 * 
	 * @param attribute
	 *            atrybut
	 * @param value
	 *            wartoœæ
	 * @return element <code>TreeItem</code>
	 */
	public TreeItem subTreeItemLo(int attribute, double val) {
		TreeItem out = new TreeItem();
		for (DataItem ti : this.elements) {
			if ((Double.parseDouble(ti.getAttributeAt(attribute))) <= val)
				out.elements.add(ti);
		}
		return out;
	}

	/**
	 * Metoda pomocnicza zwracaj¹ca elementy wiêkszê od <code>val</code>
	 * 
	 * @param attribute
	 *            atrybut
	 * @param value
	 *            wartoœæ
	 * @return element <code>TreeItem</code>
	 */
	public TreeItem subTreeItemHi(int attribute, double val) {
		TreeItem out = new TreeItem();
		for (DataItem ti : this.elements) {
			if ((Double.parseDouble(ti.getAttributeAt(attribute))) > val)
				out.elements.add(ti);
		}
		return out;
	}

	/**
	 * Pobiera ojca
	 * 
	 * @return ojciec typu <code>TreeItem</code>
	 */
	public TreeItem getParent() {
		return parent;
	}

	/**
	 * Ustawia ojca
	 * 
	 * @param parent
	 *            <code>TreeItem</code> ojciec do ustawienia
	 */
	public void setParent(TreeItem parent) {
		this.parent = parent;
	}

	/**
	 * @deprecated Wypisuje wêze³
	 * 
	 */
	public void print() {
		/*
		 * System.out.println("---TreeItem with parent " + parent + " isLeaf:" +
		 * isLeaf);
		 */
		System.out.println("-TreeItem with roadmap" + this.getRoadMap());
		for (DataItem di : elements)
			di.print();
	}

	/**
	 * Pobiera œcie¿kê do ROOTa
	 * 
	 * @return wektor atrybutów wg których nastêpowa³ podzia³ w drodze do tego
	 *         wêz³a
	 */
	public Vector<String> getRoadMap() {
		return roadMap;
	}

	/**
	 * Ustawia wektor atrybutów wg których nastêpowa³ podzia³ w drodze do tego
	 * wêz³a
	 * 
	 * @param roadMap
	 *            wektor atrybutów
	 */
	public void setRoadMap(Vector<String> roadMap) {
		this.roadMap = roadMap;
	}

	/**
	 * Dodaje do wektora atrybutów nowy atrynut (ten wg którego nast¹pi³
	 * podzia³)
	 * 
	 * @param roadMap
	 */
	public void addToRoadMap(String roadMap) {
		this.roadMap.add(roadMap);
	}
}
