package application.Game.Interface;

import java.io.IOException;

import application.Main;
import application.Game.Game;
import application.Game.Creatures.Creature;
import application.Game.Creatures.Animals.Sheep;
import application.Game.Creatures.Animals.Woolf;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InterfaceController {
	
	@FXML
	Canvas canvas;
	@FXML
	ChoiceBox creature_to_spawn;
	EventHandler<MouseEvent> handler;
	
	public void initialize() {
		
		//changing the screen to the game screen
		Game.newGame();
		Game.getGame().setCanvas(canvas);
		
		//adding all of the possible values to the choice box
		creature_to_spawn.getItems().add("Grass");
		creature_to_spawn.getItems().add("Sheep");
		creature_to_spawn.getItems().add("Wolf");
		
		//handler for when the mouse is pressed on the game Canvas
		handler = new EventHandler<MouseEvent>(){
			@Override 
			public void handle(MouseEvent e) { 
				System.out.println("mouse clicked on x:"+e.getX()+" y:"+e.getY());
				if(creature_to_spawn.getValue().equals("Grass")) {
					Cell cell =  Game.getGame().grid.getClossestCell(e.getX(), e.getY());
					if(cell.getPlant()==null) {
						Game.getGame().plants.add(new Grass(canvas,cell));
					}
				}
				else if(creature_to_spawn.getValue().equals("Wolf")) {
					Game.getGame().animals.add(new Woolf(canvas,e.getX(),e.getY()));
				}
				else if(creature_to_spawn.getValue().equals("Sheep")){
					Game.getGame().animals.add(new Sheep(canvas,e.getX(),e.getY()));
				}
			} 
		};
		canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
	}
	
	public void StartPress() {
		System.out.println("Started");
		Game.getGame().start();
	}
	
	public void QuitPress(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/Menu/MenuLayout.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			Game.getGame().end();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
