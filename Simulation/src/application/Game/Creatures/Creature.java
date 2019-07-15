package application.Game.Creatures;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public abstract class Creature {
	
	public double x;
	public double y;
	protected Canvas canvas;
	public int lifespan;
	public double nutrients;
	
	public Creature(Canvas canvas){
		Random random = new Random();
		x = random.nextInt(40);
		y = random.nextInt(40);
		this.canvas = canvas;
	}
	public Creature(Canvas canvas, int x, int y){
		Random random = new Random();
		this.x = x;
		this.y = y;
		this.canvas = canvas;
	}
	
	public abstract void update();
	
	public abstract void draw(GraphicsContext g);
	
	
}
