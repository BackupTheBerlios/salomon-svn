/**
 * 
 */
package pl.edu.agh.capitol.veniTreeCreator.logic;

import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * @author Lukasz Ostatek
 * Klasa b�d�ca abstrakcj� elementu danych u�ywanych do tworzenia drzewa 
 * - na wewn�trzne potrzeby algorytmu ID3
 */
public class DataItem {

	static int counter = 0;

	private static final Logger LOGGER = Logger.getLogger(DataItem.class);

	/**
	 * 
	 */
	private Vector<String> attributes = new Vector<String>();

	private String objective;

	private String name;

	/**
	 * @return zwraca warto�c zmiennej decyzyjnej 
	 */
	public String getObjective() {
		return objective;
	}

	/**
	 * Ustawia warto�� zmiennej decyzyjnej
	 * @param objective
	 */
	public void setObjective(String objective) {
		this.objective = objective;
	}

	/**
	 * Pobiera atrybuty <code>DataItem</code>
	 * @return wektor atrybut�w
	 */
	public Vector<String> getAttributes() {
		return attributes;
	}

	/**
	 * Ustawia atrybuty <code>DataItem</code> o numerze <code>indeks</code>
	 * @param index
	 * @return warto�� atrybutu
	 */
	public String getAttributeAt(int index) {
		return attributes.elementAt(index);
	}

	/**
	 * Ustawia atrybuty <code>DataItem</code> na ostatniej pozycji
	 * @param attrib atrybut do ustawienia
	 */
	public void pushAttribute(String attrib) {
		attributes.add(attrib);
	}

	/**
	 * @return ilo�� atrybut�w
	 */
	public int getAttributeCount() {
		return attributes.size();
	}

	/**
	 * @deprecated Metoda u�ywana we wczesnej fazie implementacji do 
	 * test�w dzia�ania algorytmu. Parsuje zawarto�� pliku tekstowego. 
	 * @param data Zawarto�� do sparsowania
	 * @param index ilo�� atrybut�w
	 */
	public DataItem(String data, int index) {
		String pom = data;
		int ttt;
		int indexNo = 0;
		if ((ttt = pom.indexOf(";")) >= 0) {
			this.setName(pom.substring(0, ttt).trim());
			pom = pom.substring(ttt + 1);
		}
		while ((ttt = pom.indexOf(";")) >= 0) {
			if (indexNo == index)
				this.setObjective(pom.substring(0, ttt).trim());
			else
				this.pushAttribute(pom.substring(0, ttt).trim());
			pom = pom.substring(ttt + 1);
			indexNo++;
		}
		if (indexNo == index)
			this.setObjective(pom.trim());
		else
			this.pushAttribute(pom.trim());
	}

	/**
	 * Konstruktor klasy na potrzeby pluginu <code>VeniTreeCreator</code>
	 * @param name - nazwa (opcjonalna)
	 * @param attributes - atrybuty (zawsze podawac w tej samej kolejnosci)
	 * @param objective - kolumna decydujaca
	 */
	public DataItem(String name, String[] attributes, String objective) {
		this.setName(name);
		for (String s : attributes)
			this.pushAttribute(s);
		this.setObjective(objective);
	}

	/**
	 * 
	 * @param attributes - atrybuty (zawsze podawac w tej samej kolejnosci)
	 * @param objective - kolumna decydujaca
	 */
	public DataItem(String[] attributes, String objective) {
		this.setName("Element " + (counter++));
		for (String s : attributes)
			this.pushAttribute(s);
		this.setObjective(objective);
	}

	/**
	 * Wypisuje dan� zmienn�
	 *
	 */
	public void print() {
		String message = "--DataItem with objective=" + objective + " name "
				+ name + " and attributes ";
		for (String att : attributes)
			message += att + ", ";
		LOGGER.info(message);
	}

	/**
	 * 
	 * @return nazwa <code>DataItem</code>'a (opcjonana)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Ustania nazw� <code>DataItem</code>'a
	 * @param name nazwa do ustawienia
	 */
	public void setName(String name) {
		this.name = name;
	}

}
