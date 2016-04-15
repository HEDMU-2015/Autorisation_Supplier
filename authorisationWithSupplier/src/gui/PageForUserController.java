package gui;

import java.net.URL;
import java.util.List;
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
	private TreeView <String> treeview = new TreeView <String>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getTreeView(organisation.getAllOrganisation());
	}

	private void getTreeView (List<Organisation> allOrganisation){
		TreeItem<String> rootItem = new TreeItem<>("Organisation");
		rootItem.setExpanded(true);
		
		for (Organisation o : allOrganisation){
			TreeItem<String> child = new TreeItem<String>(o.getName());
			boolean found = false;

			for(TreeItem<String> parent : rootItem.getChildren()){

				if(parent.getValue().contentEquals(o.getParentName())){
					parent.getChildren().add(child);
					found = true;
					break;
				}
			}

			if(!found){
				
				TreeItem<String> parent = new TreeItem<String>(
						o.getParentName()						
						);
				
				rootItem.getChildren().add(parent);
				parent.getChildren().add(child);
			
			}

			treeview.setRoot(rootItem);

		}			

	}	
		
		
		

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
