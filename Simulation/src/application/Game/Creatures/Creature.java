package application.Game.Creatures;

import java.util.Random;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
		x = random.nextInt((int)canvas.getWidth());
		y = random.nextInt((int)canvas.getHeight());
		this.canvas = canvas;
	}
	public Creature(Canvas canvas, double x2, double y2){
		Random random = new Random();
		this.x = x2;
		this.y = y2;
		this.canvas = canvas;
	}
	
	public abstract void update();
	
	public abstract void draw(GraphicsContext g);
	
}
