package salomon.platform;

import salomon.platform.data.attribute.IAttributeManager;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.data.rule.IRuleSetManager;

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
	public abstract IDataSetManager getDataSetManager();

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public abstract IRuleSetManager getRuleSetManager();

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public abstract IAttributeManager getAttributeManager();
}