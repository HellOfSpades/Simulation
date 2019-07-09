package application;
	
import application.Game.Game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{
	
	public static Stage stage;
	public static Game game;

	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.stage = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("/application/Menu/MenuLayout.fxml"));
			Scene scene = new Scene(root);
			
			//setting the program to close when the stage is closed
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){

				@Override
				public void handle(WindowEvent arg0) {
					System.exit(0);
				}
				
			});
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
