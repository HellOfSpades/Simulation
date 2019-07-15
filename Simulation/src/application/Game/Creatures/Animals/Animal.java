package application.Game.Creatures.Animals;

import application.Game.Game;
import application.Game.Creatures.Creature;
import application.Game.Creatures.Plants.Plant;
import javafx.scene.canvas.Canvas;

public abstract class Animal extends Creature{

	protected int speed;
	public int width;
	public int height;
	//how much of the nutrients can the animal absorb from its food
	protected double consumption;
	
	protected double hunger;
	protected double maxhunger;
	protected double angle;
	
	public Animal(Canvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
	}
	public Animal(Canvas canvas, int x, int y) {
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
			x = 0;
		}else if(x+10>canvas.getWidth()) {
			x = (int) (canvas.getWidth()-10);
		}
		if(y<0) {
			y = 0;
		}else if(y+10>canvas.getHeight()) {
			y = (int) (canvas.getHeight()-10);
		}
	}

}
