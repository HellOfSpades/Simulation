package application.Menu;

import java.io.IOException;

import application.Main;
import application.Game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {
	
	public void PlayPress(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/Game/Interface/GameScreen.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/Game/Interface/interface.css").toExternalForm());
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void OptionsPress(ActionEvent event) {
		System.out.println("This feature is not yet available");
	}
	public void QuitPress(ActionEvent event) {
		System.exit(0);
	}
}
