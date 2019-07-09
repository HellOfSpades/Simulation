package application.Game.Interface;

import java.io.IOException;

import application.Main;
import application.Game.Game;
import application.Game.Creatures.Creature;
import application.Game.Creatures.Plants.Grass;
import application.Game.Map.Cell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InterfaceController {
	
	@FXML
	Canvas canvas;
	EventHandler<MouseEvent> handler;
	
	public void initialize() {
		Main.game = Game.getGame();
		Game.getGame().setCanvas(canvas);
		
		handler = new EventHandler<MouseEvent>(){
			@Override 
			public void handle(MouseEvent e) { 
				
				
				System.out.println("mouse clicked on x:"+e.getX()+" y:"+e.getY());
				Cell cell =  Game.getGame().grid.getClossestCell(e.getX(), e.getY());
				if(cell.getPlant()==null) {
					Game.getGame().plants.add(new Grass(canvas,cell));
				}
				
			} 
		};
		canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
	}
	
	public void StartPress() {
		System.out.println("Started");
		Main.game.start();
	}
	
	public void QuitPress(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/Menu/MenuLayout.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			Main.game.end();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
