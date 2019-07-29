  package application.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import application.Game.Creatures.Creature;
import application.Game.Creatures.Animals.Animal;
import application.Game.Creatures.Animals.Sheep;
import application.Game.Creatures.Animals.Woolf;
import application.Game.Creatures.Plants.Grass;
import application.Game.Creatures.Plants.Plant;
import application.Game.Map.Grid;
import application.Game.Threads.Repainter;
import application.Game.Threads.Updater;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class Game {
	
	public Canvas canvas;
	public volatile CopyOnWriteArraySet<Plant> plants = new CopyOnWriteArraySet<Plant>();
	public volatile CopyOnWriteArraySet<Animal> animals = new CopyOnWriteArraySet<Animal>();
	public Updater updater;
	public Repainter repainter;
	private double x;
	private double y;
	private double originx;
	private double originy;

	static Game game;
	public Grid grid;
	
	static public void newGame(Canvas canvas) {
		game = new Game(canvas);
	}
	
	private Game(Canvas canvas){
		
		System.out.println("Game Created!!");
		plants = new CopyOnWriteArraySet<Plant>();
		animals = new CopyOnWriteArraySet<Animal>();
		this.canvas = canvas;
		updater = new Updater(this);
		repainter = new Repainter(canvas,this);
		grid = new Grid(canvas.getWidth(),canvas.getHeight(),320,130);
		
	}
	
	public static Game getGame() {
		return game;
	}
	
	public void start() {
		System.out.println("Game Started!!");
		updater.start();
		repainter.start();
	}
	
	public void end() {
		updater.interrupt();
		repainter.stop();
		plants.clear();
		animals.clear();
		grid.cells = null;
		game = null;
	}
	
	//these three methods are used to find angles and distances between two points
	//sets the second point
	public void setpoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//setting the point of origin
	public void setoriginpoint(double x, double y) {
		this.originx = x;
		this.originy = y;
	}
	//by giving the point of origin you can find the clossest distance
	//and also change the x coordinate
	public double clossestdist() {
		
		double dist = Math.sqrt(Math.pow(x-originx, 2)+Math.pow(y-originy, 2));
		
		//if the distance is closer when you look in the other direction that it will be the chosen one
		//also the x changes to match the x of the closest dist
		if(dist>Math.sqrt(Math.pow(-(canvas.getWidth()-x)-originx, 2)+Math.pow(y-originy, 2))) {
			x = -(canvas.getWidth()-x);
			dist = Math.sqrt(Math.pow(x-originx, 2)+Math.pow(y-originy, 2));
		}
		if(dist>Math.sqrt(Math.pow(x+canvas.getWidth()-originx, 2)+Math.pow(y-originy, 2))) {
			x = x+canvas.getWidth();
			dist = Math.sqrt(Math.pow(x-originx, 2)+Math.pow(y-originy, 2));
		}
		
		return dist;
	}
	//than we use the x to find the angle
	//the input data is again, the point of origin
	public double clossestangle() {
		clossestdist();
		
		double angle = Math.atan2((y-originy),(x-originx))*180/Math.PI;;
		
		return angle;
	}
	
	//returning the x that is suited for the vector
	public double getVectorX() {
		
		return x;
	}
}
