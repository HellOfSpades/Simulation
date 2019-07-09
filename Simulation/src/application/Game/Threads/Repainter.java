package application.Game.Threads;

import java.util.Iterator;

import application.Constants;
import application.Game.Game;
import application.Game.Creatures.Plants.Plant;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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
		Iterator<Plant> plantiterator = Game.getGame().plants.iterator();
		while(plantiterator.hasNext()) {
			Plant creature = plantiterator.next();
			creature.draw(g);
		}
	}
	
}
