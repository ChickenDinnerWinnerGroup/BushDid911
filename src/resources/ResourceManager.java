package resources;

import java.util.ArrayList;
/**
 *
 * @author
 *
 */
public interface ResourceManager {
	/**
	 *
	 * @return
	 */
	public ArrayList<Resource> getResources();
	/**
	 *
	 * @param classType
	 * @return
	 */
	public ArrayList<Resource> getResourceByType(Class<Resource> classType);
	/**
	 *
	 * @param id
	 * @return
	 */
	public Resource getResourceByID(int id);
	/**
	 *
	 * @param r
	 */
	public void addResource(Resource r);
	/**
	 *
	 * @param id
	 * @param r
	 */
	public void updateResource(int id, Resource r);
	/**
	 *
	 * @param id
	 */
	public void removeResource(int id);
	/**
	 *
	 * @param id
	 */
	public void reserveResource(int id);
	/**
	 *
	 * @param id
	 */
	public void requestResource(int id);
}
