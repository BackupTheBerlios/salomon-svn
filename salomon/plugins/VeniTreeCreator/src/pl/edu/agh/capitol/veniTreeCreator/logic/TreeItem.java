/**
 * 
 */
package pl.edu.agh.capitol.veniTreeCreator.logic;

import java.util.Vector;

/**
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

    public TreeItem() {

    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Vector<DataItem> getElements() {
        return elements;
    }

    public boolean isHomogenous() {
        String value = null;
        for (DataItem dt : elements) {
            if ((value != null) && (!dt.getObjective().equals(value)))
                return false;
            value = dt.getObjective();
        }
        return true;
    }
    public void draw(int wciecie){
        System.out.println("|-");
        for(int i=0;i<wciecie;i++)
            System.out.println("-");
    }
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
            // TODO
            // sprawdzic
            // ta
            // formule

        }
        System.out.println("Entropy=" + entropy);
        return entropy;
    }

    public TreeItem subTreeItem(int attribute, String value) {
        TreeItem out = new TreeItem();
        for (DataItem ti : this.elements) {
            if (ti.getAttributeAt(attribute).equals(value))
                out.elements.add(ti);
        }
        return out;
    }

    public TreeItem getParent() {
        return parent;
    }

    public void setParent(TreeItem parent) {
        this.parent = parent;
    }

    public void print() {
        /*System.out.println("---TreeItem with parent " + parent + " isLeaf:"
                + isLeaf);*/
        System.out.println("-TreeItem with roadmap"+this.getRoadMap());
        for (DataItem di : elements)
            di.print();
    }

    public Vector<String> getRoadMap() {
        return roadMap;
    }

    public void setRoadMap(Vector<String> roadMap) {
        this.roadMap = roadMap;
    }
    public void addToRoadMap(String roadMap) {
        this.roadMap.add(roadMap);
    }
}
