package application.Game.Creatures.Animals;

import application.Constants;
import application.Game.Game;
import application.Game.Creatures.Creature;
import application.Game.Creatures.Plants.Plant;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;

public abstract class Animal extends Creature{

	protected int speed;
	public int width;
	public int height;
	//how much of the nutrients can the animal absorb from its food
	protected double consumption;
	
	protected double hunger;
	protected double maxhunger;
	protected double angle;
	protected boolean mate = false;
	protected boolean eating = false;
	Timeline timeline;
	
	public Animal(Canvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
	}
	public Animal(Canvas canvas, double x, double y) {
		super(canvas,x,y);
		// TODO Auto-generated constructor stub
	}
	public void eat(Creature food) {
		Game.getGame().updater.removed.add(food);
		hunger+=consumption*food.nutrients;
		if(hunger>maxhunger) {
			hunger = maxhunger;
		}
	}
	
	public void move(double dist, double angle){
		
		angle = angle*Math.PI/180;
		
		
		y = (int) (y+Math.sin(angle)*dist);
		x = (int) (x+Math.cos(angle)*dist);
		
		
		
		
		
		if(x<0) {
			x = canvas.getWidth()+x;
			
		}else if(x>canvas.getWidth()) {
			x = x-canvas.getWidth();
		}
		if(y<0) {
			y = 0;
			
		}else if(y+height>canvas.getHeight()) {
			y = canvas.getHeight()-height;
		}
		
	}

}
