package application.Game.Creatures.Animals;

import java.util.Iterator;

import application.Game.Game;
import application.Game.Creatures.Creature;
import application.Game.Creatures.Plants.Grass;
import application.Game.Creatures.Plants.Plant;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Woolf extends Animal{

	public Woolf(Canvas canvas, double x, double y) {
		super(canvas, x, y);
		init();
	}
	
	
	public void init() {
		lifespan = 600;
		nutrients = 10;
		hunger = 50;
		maxhunger = 100;
		consumption = 0.7;
		this.speed = 10;
		width = 15;
		height = 20;
		angle = 0;
	}

	@Override
	public void update() {
		
		
		
		lifespan--;
		double energy_used = 0.1;
		
		//Checking if the woolf is hungry
		if(hunger<=maxhunger/2 || eating) {
			
			//making it so that the woolf is eating and not mating
			eating = true;
			mate = false;
			
			//The variables we need to find the closest sheep
			Sheep prey = null;
			double dist = 0;
			
			
			//looking through all of the animals to find the clossest sheep
			Iterator<Animal> iterator = Game.getGame().animals.iterator();
			while(iterator.hasNext()) {
				
				Animal animal = iterator.next();
				
				//Checking if the animal is a sheep
				if(animal instanceof Sheep) {
					
					Sheep sheep = (Sheep)(animal);
					

					
					//calculating the distance to the selected grass
					double tempdist = Math.sqrt(Math.pow(sheep.x-x, 2)+Math.pow(sheep.y-y, 2));
					
					//If their is no sheep found yet or the distance to the currently checked sheep is smaller
					//it will become the prey
					if(prey==null || dist>tempdist) {
						
						prey = sheep;
						dist = tempdist;
							
					}
					
				}
			}
			
			//moving toward the prey
			//checking for there to be a sheep object found
			if(prey!=null) {
				//finding the angle between the sheep and the woolf
				double angle = Math.atan2((prey.y-y),(prey.x-x))*180/Math.PI;
				
				//making the woolf look in the direction of the sheep
				this.angle = angle;
				
				//moving toward the sheep
				if(dist<speed) {
					move(dist,angle);
					energy_used+=dist*0.01;
				}else {
					move(speed,angle);
					energy_used+=speed*0.01;
				}
				
				//if the woolf is above the sheep it will eat it
				if(new Rectangle(x,y,width,height).intersects(
						new Rectangle(prey.x,prey.y,prey.width,prey.height).getBoundsInLocal())) {
				}
			}
			
			//if the woolf is full, then it is no longer hungry
			if(hunger>=maxhunger) {
				eating=false;
			}
		}
		
		
		
		//if the woolf is not hungry it will try and mate
		
		else {
			
			
			
			
		}
		
		//detucting the energy used
		hunger-=energy_used;
		
		if(hunger<=0 || lifespan<=0) {
			Game.getGame().updater.removed.add(this);
		}
		
	}

	@Override
	public void draw(GraphicsContext g) {
		g.setFill(Color.GREY);
		g.setStroke(Color.BROWN.darker());
		g.fillRect(x, y, width, height);
		g.strokeRect(x, y, width, height);
		
	}

}
