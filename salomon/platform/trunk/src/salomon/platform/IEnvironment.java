package salomon.platform;

/**
 * 
 * TODO: add comment.
 * @author krzychu
 * 
 */
public interface IEnvironment
{
	public abstract void put(String key, String value);

	public abstract String get(String key);
}