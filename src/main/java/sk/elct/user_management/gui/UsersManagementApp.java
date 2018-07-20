package sk.elct.user_management.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UsersManagementApp extends Application {
	
	public void start(Stage stage) throws Exception {
	 PrvyController controller = new PrvyController();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui.fxml"));
		fxmlLoader.setController(controller);
		Parent rootPane = fxmlLoader.load();
		
		
//		Parent rootPane = FXMLLoader.load(getClass().getResource("gui.fxml"));

		Scene scene = new Scene(rootPane);
		stage.setTitle("User management");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
