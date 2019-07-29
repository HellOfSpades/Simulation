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
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public class Woolf extends Animal{

	Sheep targetfood = null;
	
	public Woolf(Canvas canvas, double x, double y) {
		super(canvas, x, y);
		init();
	}
	
	
	public void init() {
		lifespan = 600;
		nutrients = 10;
		hunger = 20;
		maxhunger = 40;
		consumption = 0.7;
		this.speed = 8;
		width = 20;
		height = 15;
		angle = 0;
	}

	@Override
	public void update() {
		
		
		
		lifespan--;
		double energy_used = 0.1;
		
		//setting the origin point
		Game.getGame().setoriginpoint(this.x, this.y);
		
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
					Game.getGame().setpoint(sheep.x, sheep.y);
					double tempdist = Game.getGame().clossestdist();
					
					//If their is no sheep found yet or the distance to the currently checked sheep is smaller
					//it will become the prey
					if(prey==null || dist>tempdist) {
						
						
						//cheking if any other woolf have decided to eat that sheep
						boolean taken = false;
						Iterator<Animal> animaliterator = Game.getGame().animals.iterator();
						while(animaliterator.hasNext()) {
							Creature creature = animaliterator.next();
							
							if(creature instanceof Woolf && creature!=this) {
								Woolf woolf = (Woolf)creature;
								if(woolf.targetfood==sheep) {
									taken = true;
									break;
								}
							}
							
						}
						
						if(!taken) {
							prey = sheep;
							dist = tempdist;
						}
						
							
					}
					
				}
			}
			
			//moving toward the prey
			//checking for there to be a sheep object found
			if(prey!=null) {
				
				targetfood=prey;
				
				//finding the angle between the sheep and the woolf
				Game.getGame().setpoint(prey.x, prey.y);
				double angle = Game.getGame().clossestangle();
				
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
					
					eat(prey);
				}
			}
			
			//if the woolf is full, then it is no longer hungry
			if(hunger>=maxhunger) {
				eating=false;
			}
		}
		
		
		
		//if the woolf is not hungry it will try and mate
		
		else {
			
			mate = true;
			double dist = 0;
			Woolf mate = null;
			
			//looking for the closest mate
			Iterator<Animal> animaliterator = Game.getGame().animals.iterator();
			while(animaliterator.hasNext()) {
				Animal potential_mate = animaliterator.next();
				
				//ckecking if the animal is another sheep
				if(potential_mate instanceof Woolf && potential_mate.mate) {
					Woolf woolf = (Woolf)(potential_mate);
					
					Game.getGame().setpoint(woolf.x, woolf.y);
					double tempdist = Game.getGame().clossestdist();
					
					//if a mate hasn't been found yet or it was, but this one is closer, it will mark it as the main sheep
					if(woolf!=this && (mate==null || dist>tempdist)) {
						mate = woolf;
						dist = tempdist;
					}
				}
			}
			
			
			//going to the clossest mate, if such exists
			if(mate!=null) {
				
				//determining the angle between our woolf and its mate
				Game.getGame().setpoint(mate.x, mate.y);
				double angle = Game.getGame().clossestangle();
				
				this.angle = angle;
				
				//moving toward the mate
				if(dist<speed) {
					move(dist,angle);
					energy_used+=dist*0.01;
				}else {
					move(speed,angle);
					energy_used+=speed*0.01;
				}
				
				//if the mate and our sheep intersect, they will mate
				if(new Rectangle(x,y,width,height).intersects(
						new Rectangle(mate.x,mate.y,mate.width,mate.height).getBoundsInLocal())) {
					
					energy_used+=20;
					mate.hunger-=20;
					
					Game.getGame().updater.added.add(new Woolf(canvas,x,y));
					
				}
			}
			
			
		}
		
		//detucting the energy used
		hunger-=energy_used;
		
		if(hunger<=0 || lifespan<=0) {
			Game.getGame().updater.removed.add(this);
		}
		
	}

	@Override
	public void draw(GraphicsContext g) {
		g.save();
		g.transform(new Affine(new Rotate(angle, x+width/2, y+height/2)));
		g.setFill(Color.ORANGE);
		g.setStroke(Color.RED);
		g.fillRect(x, y, width, height);
		g.strokeRect(x, y, width, height);
		g.restore();
	}

}
