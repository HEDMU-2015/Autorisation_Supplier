package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import domain.Organisation;

public class TreeBuilder {
	private List<Integer> keysToRemove = new ArrayList<>();

	public void buildTree(Map<Integer, Organisation> map) {
		buildTree(0, map);
		for (Integer integer : keysToRemove) {
			map.remove(integer);
		}
	}

	private void buildTree(int i, Map<Integer, Organisation> map) {
		Iterator<Integer> itr = map.keySet().iterator();
		while (itr.hasNext()) {
			Organisation organisation = map.get(itr.next());
			int parent_id = organisation.getParentID();
			if (map.containsKey(parent_id)) {
				map.get(parent_id).addChild(organisation.getId(), organisation);
				if (parent_id > 0) {
					keysToRemove.add(organisation.getId());
				}
				buildTree(i++, map.get(parent_id).getChildren());
			}
		}
	}
}
