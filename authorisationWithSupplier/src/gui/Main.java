package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
	private Stage stage;
	private BorderPane mainWindow;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		this.stage.setTitle("");

		initMainWindow();
		loadPane();

	}
	
	private void initMainWindow() {
		FXMLLoader loader = new FXMLLoader();

		loader.setLocation(Main.class.getResource("MainWindow.fxml"));
		try {
			mainWindow = (BorderPane) loader.load();
			Scene scene = new Scene(mainWindow);
			stage.setScene(scene);
			stage.show();
		} catch (IOException exc) {
		}
	}
	
	
	private void loadPane() throws IOException{
		FXMLLoader pageForUser = new FXMLLoader();
		pageForUser.setLocation(Main.class.getResource("PageForUser.fxml"));
		AnchorPane PageForUser = (AnchorPane) pageForUser.load();
		mainWindow.setCenter(PageForUser);
	
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Stage getStage() {
		return stage;
	}
}
