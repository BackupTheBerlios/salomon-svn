
package salomon.engine.platform.data.tree;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(
                "Test for salomon.engine.platform.data.tree");
        //$JUnit-BEGIN$
        suite.addTestSuite(DataSourceTest.class);
        suite.addTestSuite(TreeTest.class);
        suite.addTestSuite(TreeManagerTest.class);
        suite.addTestSuite(NodeTest.class);
        //$JUnit-END$
        return suite;
    }

}
