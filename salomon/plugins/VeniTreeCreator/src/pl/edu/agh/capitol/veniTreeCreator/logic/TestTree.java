/**
 * 
 */
package pl.edu.agh.capitol.veniTreeCreator.logic;

/**
 * @author Lukasz Ostatek
 *
 */
public class TestTree {

    /**
     * @param args
     */
    public static void main(String[] args) {
        TreeConstructionTask tc = new TreeConstructionTask();
        tc.loadFromFile("dane.txt",4);
        tc.createTree();
    }

}
