package szkeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AsteroidView {
	private java.util.Map<Asteroid, ArrayList<Place>> neighbours = new HashMap<>();
	private java.util.Map<Asteroid, Integer> layers = new HashMap<>();
	private java.util.Map<Asteroid, Integer> resource = new HashMap<>();
	private java.util.Map<Asteroid, Boolean> blownUp = new HashMap<>();
	
	public void updateAsteroid(Asteroid a) {
		neighbours.put(a, a.GetAllNeighbors());
		ArrayList<Integer> resourceList = new ArrayList<Integer>();
		Resource r = a.getResource();
		if(r == null) {
			resource.put(a, 100);
		}
		else {
			resourceList = a.getResource().AddToList(resourceList);
			resource.put(a, resourceList.get(0));
		}
		
		layers.put(a, a.GetLayers());
		blownUp.put(a, a.GetBlownUp());
	}

	AsteroidView(){
	
	}

	public Map<Asteroid, Boolean> getBlownUp() {
		return blownUp;
	}

	public Map<Asteroid, ArrayList<Place>> getNeighbours() {
		return neighbours;
	}

	public Map<Asteroid, Integer> getLayers() {
		return layers;
	}

	public Map<Asteroid, Integer> getResource() {
		return resource;
	}
}
