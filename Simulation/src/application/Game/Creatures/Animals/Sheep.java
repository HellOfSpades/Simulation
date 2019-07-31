package application.Game.Creatures.Animals;

import java.util.HashSet;
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

public class Sheep extends Animal{

	Grass targetfood = null;
	boolean in_danger = false;
	
	public Sheep(Canvas canvas) {
		super(canvas);
		init();
	}
	
	public Sheep(Canvas canvas, double x, double y) {
		super(canvas,x,y);
		init();
	}
	
	public void init() {
		lifespan = 300;
		nutrients = 40;
		hunger = 30;
		maxhunger = 60;
		consumption = 0.3;
		this.speed = 6;
		width = 20;
		height = 20;
		angle = 0;
	}

	public void update() {
		
		
		lifespan--;
		double energy_used = 0.1;
		//setting the origin for the Game class
		Game.getGame().setoriginpoint(this.x, this.y);
		//checking if their is a woolf nearby
		double radius = 200;
		//if the sheep is allready in danger, the radius becomes bigger
		if(in_danger)radius = 400;
		in_danger = false;
		//a set for all nearby woolfs
		HashSet<Woolf> woolfs = new HashSet<Woolf>();
		//looking through all of the creatures
		Iterator<Animal> animaldangeriterator = Game.getGame().animals.iterator();
		while(animaldangeriterator.hasNext()) {
			Animal creature = animaldangeriterator.next();
			//checking if the animal is a woolf, and if he is close
			Game.getGame().setpoint(creature.x, creature.y);
			if(creature instanceof Woolf && radius>Game.getGame().clossestdist()) {
				//if the woolf is close then the sheep is in danger
				//the woolf is added to the woolf's list
				in_danger = true;
				woolfs.add((Woolf)(creature));
			}
		}
		
		//if the sheep is in danger
		if(in_danger) {
			//finding the best vector from the woolf's and the up or down wall
			double vectorx = 0;
			double vectory = 0;
			//going through all of the woolfs and finding the sum of there vectors
			Iterator<Woolf> woolfsiterator = woolfs.iterator();
			while(woolfsiterator.hasNext()) {
				
				Woolf woolf = woolfsiterator.next();
				Game.getGame().setpoint(woolf.x, woolf.y);
				//the priority of the woolf is quadraticly inversly proporsional to the distance
				double priority = 1/Math.pow(Game.getGame().clossestdist(),2);
				
				vectorx+=(Game.getGame().getVectorX()-this.x)*priority;
				vectory+=(woolf.y-this.y)*priority;
			}
			
			//making it so that the sheep run oway from the top and button planes as well
			//the x for those vectors is the same as the sheeps, so it makes no difference weather we add them or not
			vectory+=(0-this.y)*(1/Math.pow(this.y,2));
			vectory+=(Game.getGame().canvas.getHeight()-this.y)*(1/Math.pow(Game.getGame().canvas.getHeight()-this.y,2));
			
			//changing the vector coordinates(they are based around the sheep in the center) to be normal coordinates
			vectorx+=this.x;
			vectory+=this.y;
			Game.getGame().setpoint(vectorx, vectory);
			angle = Game.getGame().clossestangle()+180;
			
			//moving
			move(speed,angle);
			
		}
		//if the sheep is not in danger
		else {
			//Checking if the sheep is hungry
			if(hunger<=maxhunger/2 || eating) {
				
				//making it so that the sheep is eating and not mating
				eating = true;
				mate = false;
				
				//The variables we need to find the closest piece of grass
				Grass grass = null;
				double dist = 0;
				
				
				//looking through all of the plants to find the clossest piece of grass
				Iterator<Plant> iterator = Game.getGame().plants.iterator();
				while(iterator.hasNext()) {
					
					Plant plant = iterator.next();
					
					//Checking if the plant is a grass
					if(plant instanceof Grass) {
						
						Grass target = (Grass)(plant);
						

						
						//calculating the distance to the selected grass
						Game.getGame().setpoint(target.x, target.y);
						double tempdist = Game.getGame().clossestdist();
						
						//If their is no grass found yet or the distance to the currently checked grass is smaller
						//the selected grass becomes the main target
						if(grass==null || dist>tempdist) {
							
							
							//cheking if any other sheep have decided to eat that grass
							boolean taken = false;
							Iterator<Animal> animaliterator = Game.getGame().animals.iterator();
							while(animaliterator.hasNext()) {
								Creature creature = animaliterator.next();
								if(creature instanceof Sheep && creature!=this) {
									Sheep sheep = (Sheep)creature;
									if(sheep.targetfood==target) {
										taken = true;
										break;
									}
								}
								
							}
							
							//if the grass was not taken by any other sheep, it will mark it
							if(!taken) {
								
								grass = target;
								dist = tempdist;
								
							}
							
						}
						
					}
				}
				
				//moving toward the grass
				//checking for there to be a grass object found
				if(grass!=null) {
					targetfood = grass;
					//finding the angle between the grass and the sheep
					Game.getGame().setpoint(grass.x, grass.y);
					double angle = Game.getGame().clossestangle();
					//making the sheep look in the direction of the grass
					this.angle = angle;
					
					//moving toward the grass
					if(dist<speed) {
						move(dist,angle);
						energy_used+=dist*0.01;
					}else {
						move(speed,angle);
						energy_used+=speed*0.01;
					}
					
					//if the sheep is above the grass it will eat it
					if(new Rectangle(x,y,width,height).intersects(
							new Rectangle(grass.currentCell.x,grass.currentCell.y,Game.getGame().grid.cellwidth,Game.getGame().grid.cellheight).getBoundsInLocal())
							) {
						
						//eating the grass as well the the grass next to it if it is present
						eat(grass);
						if(grass.currentCell.v>0 && Game.getGame().grid.cells[grass.currentCell.v-1][grass.currentCell.h].getPlant()!=null)eat(Game.getGame().grid.cells[grass.currentCell.v-1][grass.currentCell.h].getPlant());
						if(grass.currentCell.v<Game.getGame().grid.cells.length-1 && Game.getGame().grid.cells[grass.currentCell.v+1][grass.currentCell.h].getPlant()!=null)eat(Game.getGame().grid.cells[grass.currentCell.v+1][grass.currentCell.h].getPlant());
						if(grass.currentCell.h>0 && Game.getGame().grid.cells[grass.currentCell.v][grass.currentCell.h-1].getPlant()!=null)eat(Game.getGame().grid.cells[grass.currentCell.v][grass.currentCell.h-1].getPlant());
						if(grass.currentCell.h>Game.getGame().grid.cells[0].length-1 && Game.getGame().grid.cells[grass.currentCell.v][grass.currentCell.h+1].getPlant()!=null)eat(Game.getGame().grid.cells[grass.currentCell.v][grass.currentCell.h+1].getPlant());
						
					}
				}
				
				//if the sheep is full then it is no longer hungry
				if(hunger>=maxhunger) {
					eating=false;
				}
			}
			
			
			
			//if the sheep is not hungry it will try and mate
			
			else {
				
				
				mate = true;
				double dist = 0;
				Sheep mate = null;
				
				//looking for the closest mate
				Iterator<Animal> animaliterator = Game.getGame().animals.iterator();
				while(animaliterator.hasNext()) {
					Animal potential_mate = animaliterator.next();
					
					//ckecking if the animal is another sheep
					if(potential_mate instanceof Sheep && potential_mate.mate) {
						Sheep sheep = (Sheep)(potential_mate);
						
						Game.getGame().setpoint(sheep.x, sheep.y);
						double tempdist = Game.getGame().clossestdist();
						
						//if a mate hasn't been found yet or it was, but this one is closer, it will mark it as the main sheep
						if(sheep!=this && (mate==null || dist>tempdist)) {
							mate = sheep;
							dist = tempdist;
						}
					}
				}
				
				
				//going to the clossest mate, if such exists
				if(mate!=null) {
					
					//determining the angle between our sheep and its mate
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
						
						
						energy_used+=40;
						mate.hunger-=40;
						
						Game.getGame().updater.added.add(new Sheep(canvas,x,y));
						
					}
				}
				
			}
		}
		
		
		
		//detucting the energy used
		hunger-=energy_used;
		
		if(hunger<=0 || lifespan<=0) {
			Game.getGame().updater.removed.add(this);
		}
		
	}
	

	public void draw(GraphicsContext g) {
		g.save();
		g.transform(new Affine(new Rotate(angle, x+width/2, y+height/2)));
		g.setFill(Color.WHITE);
		g.setStroke(Color.BROWN.darker());
		g.fillRect(x, y, width, height);
		g.setFill(Color.BLACK);
		g.fillRect(x+2*width/3, y+height/3, width/3, height/3);
		g.strokeRect(x, y, width, height);
		g.restore();
	}

}
