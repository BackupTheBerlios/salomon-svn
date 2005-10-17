
package pl.capitol.tree.plugins.test.panels;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import salomon.platform.data.tree.INode;
import salomon.platform.data.tree.ITree;
import salomon.platform.exception.PlatformException;

import pl.capitol.tree.plugins.test.VisPlugin;
import pl.capitol.tree.plugins.util.TreeVisResults;
import salomon.plugin.IResult;

/**
 * @author mnowakowski
 *
 */
public class VisResultPanel extends JScrollPane
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(VisResultPanel.class);

	private IResult result;

	private JLabel resultLabel = null;

	private JTextArea resultText = null;

	/**
	 * This is the default constructor
	 */
	public VisResultPanel(IResult result)
	{
		super();
		this.result = result;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		if (((TreeVisResults) result).resultToString() == "") {
			resultLabel = new JLabel();
			resultLabel.setText("Result");
			resultLabel.setBounds(new java.awt.Rectangle(18, 8, 71, 22));
			this.setLayout(null);
			this.setSize(435, 176);
			this.setPreferredSize(new java.awt.Dimension(435, 176));
			this.add(resultLabel, null);
			String alert = new String(
			"(1)Aby wyœwietliæ drzewo musisz wybraæ w Settings drzewo i zaznaczyæ opcjê \"Dzia³aj samodzielnie\" po czym klikn¹æ Run lub te¿ jeœli chcesz wizualizowaæ wynik algorytmu obliczeniowego klikn¹æ Run.");
			this.add(getResultText(alert), null);
		} else {
			int treeId = Integer.parseInt(((TreeVisResults) result).resultToString());
			if (treeId == -1) {
				resultLabel = new JLabel();
				resultLabel.setText("Result");
				resultLabel.setBounds(new java.awt.Rectangle(18, 8, 71, 22));
				this.setLayout(null);
				this.setSize(435, 176);
				this.setPreferredSize(new java.awt.Dimension(435, 176));
				this.add(resultLabel, null);
				String alert = new String(
				"(2)Aby wyœwietliæ drzewo musisz wybraæ w Settings drzewo i zaznaczyæ opcjê \"Dzia³aj samodzielnie\" po czym klikn¹æ Run lub te¿ jeœli chcesz wizualizowaæ wynik algorytmu obliczeniowego klikn¹æ Run.");
				this.add(getResultText(alert), null);	
			} else {
				ITree myTree = null;
				INode root = null;
				DefaultMutableTreeNode top = null;
				DefaultMutableTreeNode oldTop = null;
				DefaultMutableTreeNode topDown = null;
				DefaultMutableTreeNode edge = null;
				DefaultMutableTreeNode child = null;
				INode rootLeaf = null;
				INode[] children = null;
				boolean notEndTree = true;

				try {
					myTree = VisPlugin.enginik.getTreeManager().getTree(treeId);
					rootLeaf = myTree.getRoot();
					topDown = new DefaultMutableTreeNode(rootLeaf.getValue());
					oldTop = topDown;
					while (notEndTree) {
						notEndTree = false;
						root = rootLeaf;
						top = topDown;
						children = root.getChildren();
						for (int i = 0; i < children.length; i++) {
							edge = new DefaultMutableTreeNode("e. "
									+ children[i].getParentEdge());
							top.add(edge);
							child = new DefaultMutableTreeNode(
									children[i].getValue());
							edge.add(child);
							if (children[i].isLeaf() == false) {
								notEndTree = true;
								rootLeaf = children[i];
								topDown = child;
							}
						}

					}

				} catch (PlatformException e) {
					LOGGER.fatal(e.getMessage());
				}

				JTree tree = new JTree(oldTop);
				this.setViewportView(tree);
			}
		}
	}

	public IResult getResult()
	{
		return result;
	}

	public void setResult(IResult result)
	{
		this.result = result;
	}

	/**
	 * This method initializes resultText	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getResultText(String alert)
	{
		if (resultText == null) {
			resultText = new JTextArea(alert);
			resultText.setBounds(new java.awt.Rectangle(6, 35, 416, 138));
			resultText.setEditable(false);
			resultText.setLineWrap(true);
			resultText.setWrapStyleWord(true);
		}
		return resultText;
	}

}
