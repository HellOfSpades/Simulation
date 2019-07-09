package application.Game.Creatures.Animals;

import java.util.Iterator;

import application.Game.Game;
import application.Game.Creatures.Creature;
import application.Game.Creatures.Plants.Grass;
import application.Game.Creatures.Plants.Plant;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Sheep extends Animal{

	double hunger = 14;
	double maxhunger = 16;
	boolean eating = false;
	boolean mate = false;
	
	public Sheep(Canvas canvas) {
		super(canvas);
		this.speed = 20;
	}
	
	public Sheep(Canvas canvas, int x, int y) {
		super(canvas,x,y);
		this.speed = 20;
	}

	public void update() {
		double energy_used = 0.1;
		
		//Checking if the sheep is hungry
		if(hunger<=maxhunger/2 || eating) {
			
			mate = false;
			eating = true;
			double angle = 0;
			double dist = Double.MAX_VALUE;
			Grass target = null;
			
			//looking through all of the creatures, find the closest grass creature
			Iterator<Plant> iterator = Game.getGame().plants.iterator();
			while(iterator.hasNext()) {
				Creature creature = iterator.next();
				
				//cheking if the object is a grass type
				if(creature instanceof Grass) {
					//finding the distance to the grass object
					double tempdist = Math.sqrt((creature.x-this.x)*(creature.x-this.x)+(creature.y-this.y)*(creature.y-this.y));
					
					//cheking if the previus distance is greater than the current
					//if so, replace the target with the grass object being checked
					if(tempdist<dist) {
						dist = tempdist;
						target = (Grass)creature;
						angle = (Math.atan((double)(target.y-this.y)/(double)(target.x-this.x)))*180/Math.PI;
						if(this.x>target.x) {
							angle+=180;
						}
					}
				}
			}
			
			//determining how much to move, and moving
			//adding the energy used to move the the energy total
			if(target!=null) {
				if(speed<dist) {
					move((int)speed,angle);
					energy_used+=speed*0.003;
				}else {
					move((int)dist,angle);
					energy_used+=dist*0.003;
				}
				
				//eat the grass if its less than 10 pixels ovay
				if(Math.sqrt((target.x-this.x)*(target.x-this.x)+(target.y-this.y)*(target.y-this.y))<10) {
					Game.getGame().updater.removed.add(target);
					hunger+=0.5;
				}
			}
			
			if(hunger>=maxhunger)eating = false;
			
		}
		
		
		
		
		
		else {
			//if the sheep is not eating, it will try to mate with another sheep
			System.out.println("Mating");
			mate = true;
			
			double angle = 0;
			double dist = Double.MAX_VALUE;
			Sheep target = null;
			
			//looking through all of the creatures, find the closest sheep creature to mate with
			Iterator<Animal> iterator = Game.getGame().animals.iterator();
			while(iterator.hasNext()) {
				mate = false;
				Creature creature = iterator.next();
				
				//cheking if the object is a sheep type and weather it wants to mate
				if(creature instanceof Sheep && creature!=this) {
					//finding the distance to the sheep object
					double tempdist = Math.sqrt((creature.x-this.x)*(creature.x-this.x)+(creature.y-this.y)*(creature.y-this.y));
					System.out.println("my location is "+this.x+":"+this.y+" and the location of the second sheep is "+creature.x+":"+creature.y);
					System.out.println("The temp dist is equal to "+tempdist);
					//cheking if the previus distance is greater than the current
					//if so, replace the target with the sheep object being checked
					if(tempdist<dist) {
						dist = tempdist;
						System.out.println("The distance changed to "+ dist);
						target = (Sheep)creature;
						angle = (Math.atan((double)(target.y-this.y)/(double)(target.x-this.x)))*180/Math.PI;
						System.out.println("The angle changed to "+ angle);
						if(this.x>target.x) {
							angle+=180;
						}
					}
				}
			}
			//determining how much to move, and moving
			//adding the energy used to move the the energy total
			if(target!=null) {
				System.out.println("found sheep to mate with at " + target.x+":"+target.y);
				System.out.println("The distance is "+dist+" and the angle is "+angle);
				if(speed<dist) {
					move((int)speed,angle);
					energy_used+=speed*0.003;
				}else {
					move((int)dist,angle);
					energy_used+=dist*0.003;
				}
				System.out.println("I am now at " + x+":"+y);
				//mate with the other sheep if its less than 10 pixels ovay
				if(Math.sqrt((target.x-this.x)*(target.x-this.x)+(target.y-this.y)*(target.y-this.y))<10) {
					Sheep child = new Sheep(canvas,(x+target.x)/2,(y+target.y)/2);
					child.hunger = 8;
					Game.getGame().updater.added.add(child);
					energy_used+=4;
					target.hunger-=4;
				}
			}
		}
		
		//detucting the energy used
		hunger-=energy_used;
		
		if(hunger<=0) {
			Game.getGame().updater.removed.add(this);
		}
		
	}

	public void draw(GraphicsContext g) {
		g.setFill(Color.WHITE);
		g.setStroke(Color.BROWN.darker());
		g.fillRect(x, y, 20, 20);
		g.strokeRect(x, y, 20, 20);
	}

}
