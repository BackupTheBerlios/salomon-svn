/**
 * 
 */
package pl.edu.agh.capitol.veniTreeCreator.logic;

import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * @author Lukasz Ostatek
 *
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

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public Vector<String> getAttributes() {
		return attributes;
	}

	public String getAttributeAt(int index) {
		return attributes.elementAt(index);
	}

	public void pushAttribute(String attrib) {
		attributes.add(attrib);
	}

	public int getAttributeCount() {
		return attributes.size();
	}

	/**
	 * @deprecated
	 * @param data
	 * @param index
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
	 * 
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

	public void print() {
		String message = "--DataItem with objective=" + objective + " name "
				+ name + " and attributes ";
		for (String att : attributes)
			message += att + ", ";
		LOGGER.info(message);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
