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

public class Sheep extends Animal{


	boolean eating = false;
	boolean mate = false;
	
	public Sheep(Canvas canvas) {
		super(canvas);
		init();
	}
	
	public Sheep(Canvas canvas, int x, int y) {
		super(canvas,x,y);
		init();
	}
	
	public void init() {
		nutrients = 20;
		hunger = 30;
		maxhunger = 60;
		consumption = 0.3;
		this.speed = 20;
		width = 20;
		height = 20;
		angle = 0;
	}

	public void update() {
		
		double energy_used = 0.1;
		
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
					double tempdist = Math.sqrt(Math.pow(target.currentCell.x-x, 2)+Math.pow(target.currentCell.y-y, 2));
					
					//If their is no grass found yet or the distance to the currently checked grass is smaller
					//the selected grass becomes the main target
					if(grass==null || dist>tempdist) {
						grass = target;
						dist = tempdist;
					}
					
				}
			}
			
			//moving toward the grass
			//checking for there to be a grass object found
			if(grass!=null) {
				
				//finding the angle between the grass and the sheep
				double angle = Math.atan2((grass.currentCell.y-y),(grass.currentCell.x-x))*180/Math.PI;
				
				System.out.println("My current position is x:"+x+" y:"+y);
				System.out.println("The position of the grass is x:"+grass.currentCell.x+" y:"+grass.currentCell.y);
				System.out.println("The angle is "+angle);
				
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
						new Rectangle(grass.currentCell.x,grass.currentCell.y,Game.getGame().grid.cellwidth,Game.getGame().grid.cellheight).getBoundsInLocal())) {
					
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
		g.fillRect(x, y, width, height);
		g.strokeRect(x, y, width, height);
	}

}
