package application.Game.Threads;

import java.util.Iterator;

import application.Constants;
import application.Game.Game;
import application.Game.Creatures.Animals.Animal;
import application.Game.Creatures.Plants.Plant;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Repainter extends AnimationTimer{
	
	private Canvas canvas;
	private GraphicsContext g;
	private Game game;
	
	public Repainter(Canvas canvas, Game game){
		this.canvas = canvas;
		g = canvas.getGraphicsContext2D();
		this.game = game;
	}
	
	public void handle(long now) {
		
		//drawing the background
		g.setFill(Color.BLACK);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//drawing all of the plants
		Iterator<Plant> plantiterator = Game.getGame().plants.iterator();
		while(plantiterator.hasNext()) {
			Plant creature = plantiterator.next();
			creature.draw(g);
		}
		//drawing all of the animals
		Iterator<Animal> animaliterator = Game.getGame().animals.iterator();
		while(animaliterator.hasNext()) {
			Animal creature = animaliterator.next();
			creature.draw(g);
		}
	}
	
}
