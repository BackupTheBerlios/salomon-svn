package salomon.platform;

import salomon.engine.platform.data.attribute.AttributeManager;
import salomon.engine.platform.data.dataset.DataSetManager;
import salomon.engine.platform.data.rule.RuleSetManager;

/**
 * 
 * TODO: add comment.
 * @author krzychu
 * 
 */
public interface IDataEngine
{
	/**
	 * Does ...
	 * 
	 * @return
	 */
	public abstract DataSetManager getDataSetManager();

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public abstract RuleSetManager getRuleSetManager();

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public abstract AttributeManager getAttributeManager();
}