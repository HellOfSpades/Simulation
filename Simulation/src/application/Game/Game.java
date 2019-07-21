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
	
	Canvas canvas;
	public volatile CopyOnWriteArraySet<Plant> plants = new CopyOnWriteArraySet<Plant>();
	public volatile CopyOnWriteArraySet<Animal> animals = new CopyOnWriteArraySet<Animal>();
	public Updater updater;
	public Repainter repainter;

	static Game game;
	public Grid grid;
	
	static public void newGame() {
		game = new Game();
	}
	
	private Game(){
		System.out.println("Game Created!!");
		
	}
	
	public static Game getGame() {
		return game;
	}
	
	public void setCanvas(Canvas canvas) {
		plants = new CopyOnWriteArraySet<Plant>();
		animals = new CopyOnWriteArraySet<Animal>();
		System.out.println("Canvas Set!!");
		this.canvas = canvas;
		updater = new Updater(this);
		repainter = new Repainter(canvas,this);
		grid = new Grid(canvas.getWidth(),canvas.getHeight(),320,130);
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
}
