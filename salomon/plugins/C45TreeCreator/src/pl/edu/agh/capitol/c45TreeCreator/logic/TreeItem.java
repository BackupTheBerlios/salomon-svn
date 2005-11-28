/**
 * 
 */
package pl.edu.agh.capitol.c45TreeCreator.logic;

import java.util.Vector;

import pl.edu.agh.capitol.c45TreeCreator.logic.DataItem;

/**
 * Klasa reprezentuj�ca w�ze� drzewa decyzyjnego
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

	double partitionEdge; // granica podzia�u dla ci�g�ych zmiennych <= - A, >

	// - B

	public TreeItem() {

	}

	/**
	 * Test logiczny czy w�ze� jest li�ciem
	 * 
	 * @return warto�� T/F
	 */
	public boolean isLeaf() {
		return isLeaf;
	}

	/**
	 * Ustawia warto�� logiczn� - czy w�ze� jest li�ciem
	 * 
	 * @param isLeaf
	 *            warto�� logiczna T/F
	 */
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * pobiera elementy w�z�a
	 * 
	 * @return elementy (implementuj�ce <code>IDataItem</code>) w�z�a
	 */
	public Vector<DataItem> getElements() {
		return elements;
	}

	/**
	 * test logiczny czyc wszystkie elemeny w�z�a s� homogeniczne
	 * 
	 * @return warto�� T/F
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
	 * Metoda pomocnicza obliczaj�ca entropi� w�z�a
	 * 
	 * @param classes
	 *            klasy warto�ci
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
	 * Metoda pomocnicza zwracaj�ca elementy poddrzewa spe�niaj�ce kryteria
	 * 
	 * @param attribute
	 *            atrybut
	 * @param value
	 *            warto��
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
	 * Metoda pomocnicza zwracaj�ca elementy mniejsze lub r�wne <code>val</code>
	 * 
	 * @param attribute
	 *            atrybut
	 * @param value
	 *            warto��
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
	 * Metoda pomocnicza zwracaj�ca elementy wi�ksz� od <code>val</code>
	 * 
	 * @param attribute
	 *            atrybut
	 * @param value
	 *            warto��
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
	 * @deprecated Wypisuje w�ze�
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
	 * Pobiera �cie�k� do ROOTa
	 * 
	 * @return wektor atrybut�w wg kt�rych nast�powa� podzia� w drodze do tego
	 *         w�z�a
	 */
	public Vector<String> getRoadMap() {
		return roadMap;
	}

	/**
	 * Ustawia wektor atrybut�w wg kt�rych nast�powa� podzia� w drodze do tego
	 * w�z�a
	 * 
	 * @param roadMap
	 *            wektor atrybut�w
	 */
	public void setRoadMap(Vector<String> roadMap) {
		this.roadMap = roadMap;
	}

	/**
	 * Dodaje do wektora atrybut�w nowy atrynut (ten wg kt�rego nast�pi�
	 * podzia�)
	 * 
	 * @param roadMap
	 */
	public void addToRoadMap(String roadMap) {
		this.roadMap.add(roadMap);
	}
}
