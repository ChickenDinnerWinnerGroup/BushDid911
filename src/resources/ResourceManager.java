package resources;

import java.util.ArrayList;

public interface ResourceManager {
	public ArrayList<Resource> getResources();
	public ArrayList<Resource> getResourceByType(Class<Resource> classType);
	public Resource getResourceByID(int id);
	public void addResource(Resource r);
	public void updateResource(int id, Resource r);
	public void removeResource(int id);
	public void reserveResource(int id);
	public void requestResource(int id);
}
