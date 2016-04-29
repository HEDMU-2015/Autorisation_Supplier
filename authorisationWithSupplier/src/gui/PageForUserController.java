package gui;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import api.OrganisationAPI;
import api.OrganisationAPIImpl;
import domain.Organisation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class PageForUserController implements Initializable {

	OrganisationAPI organisation = new OrganisationAPIImpl();
		
	
	@FXML
	private TreeView<String> treeView;

	private TreeItem<String> root = new TreeItem<>("Organisation");
	private TreeItem<String> leaf;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		

	TreeBuilder treeBuilder = new TreeBuilder();
	treeBuilder.buildTree(makeMap());
	buildTreeView(makeMap());
//	root.setExpanded(true);
//	treeView.setRoot(root);
	}
	
	private Map<Integer, Organisation> makeMap(){
		List<Organisation> organisationList = organisation.getAllOrganisation();
		
		Map<Integer, Organisation> tree = new HashMap<>();
		
		for(Organisation o : organisationList){
			tree.put(o.getId(), o);
		}
	
		return tree;
	}
	
	public void buildTreeView(Map<Integer, Organisation> tree) {
		TreeItem<String> rootItem = new TreeItem<>("Organisation");
		rootItem.setExpanded(true);
		
		Iterator<Integer> itr = tree.keySet().iterator();
		while (itr.hasNext()) {
			Organisation d = tree.get(itr.next());

			TreeItem<String> leaf = new TreeItem<String>(d.getName());
			for (Organisation o : organisation.getAllOrganisation()) {
				if (o.getParentID() == d.getId()) {
					TreeItem<String> skillLeaf = new TreeItem<String>(o.getName());
					leaf.getChildren().add(skillLeaf);
				}
			}
			root.getChildren().add(leaf);
			if (!d.getChildren().isEmpty()) {
				buildTreeView(d.getChildren());
			}
		}
	}
	
	

//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		getTreeView(organisation.getAllOrganisation());
//	}
//
//	private void getTreeView (List<Organisation> allOrganisation){
//		TreeItem<String> rootItem = new TreeItem<>("Organisation");
//		rootItem.setExpanded(true);
//		
//		for (Organisation o : allOrganisation){
//			TreeItem<String> child = new TreeItem<String>(o.getName());
//			boolean found = false;
//
//			for(TreeItem<String> parent : rootItem.getChildren()){
//
//				if(parent.getValue().contentEquals(o.getParentName())){
//					parent.getChildren().add(child);
//					found = true;
//					break;
//				}
//			}
//
//			if(!found){
//				
//				TreeItem<String> parent = new TreeItem<String>(
//						o.getParentName()						
//						);
//				
//				rootItem.getChildren().add(parent);
//				parent.getChildren().add(child);
//			
//			}
//
//			treeview.setRoot(rootItem);
//
//		}			

//	}	
		
		
		

//		for (Organisation o : allOrganisation){
//			TreeItem<String> child = new TreeItem<String>(o.getName());
//			boolean found = false;
//
//			for(TreeItem<String> parent : rootItem.getChildren()){
//
//				if(parent.getValue().contentEquals(Integer.toString(o.getLevel()))){
//					parent.getChildren().add(child);
//					found = true;
//					break;
//				}
//			}
//
//			if(!found){
//				
//				TreeItem<String> parent = new TreeItem<String>(
//						Integer.toString(o.getLevel())
//						);
//				
//				rootItem.getChildren().add(parent);
//				parent.getChildren().add(child);
//			
//			}
//
//			treeview.setRoot(rootItem);
//
//		}			
//
//	}	


}
