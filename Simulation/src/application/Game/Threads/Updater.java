package application.Game.Threads;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

import application.Constants;
import application.Game.Game;
import application.Game.Creatures.Creature;
import application.Game.Creatures.Animals.Animal;
import application.Game.Creatures.Plants.Plant;
import application.Game.Map.Cell;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Updater extends Thread{

	private Game game;
	public Set<Creature> added;
	public Set<Creature> removed;
	
	public Updater( Game game){
		this.game = game;
	}
	
	public void run() {
		
		try {
			
			added = new HashSet<Creature>();
			removed = new HashSet<Creature>();
			
			while(true) {
				
				
				// updating the plants
				Iterator<Plant> plantiterator = Game.getGame().plants.iterator();
				while(plantiterator.hasNext()) {
					Plant creature = plantiterator.next();
					creature.update();
				}
				
				// updating the animals
				Iterator<Animal> animaliterator = Game.getGame().animals.iterator();
				while(animaliterator.hasNext()) {
					Animal creature = animaliterator.next();
					creature.update();
				}
				
				//removing creatures
				Iterator<Creature> iterator = removed.iterator();
				
				while(iterator.hasNext()) {
					Creature creature = iterator.next();
					if(creature instanceof Plant) {
						((Plant) creature).currentCell.setPlant(null);
						Game.getGame().plants.remove(creature);
					}
					else if(creature instanceof Animal) {
						Game.getGame().animals.remove(creature);
					}
					
					iterator.remove();
				}
				
				//adding creatures
				iterator = added.iterator();
				
				while(iterator.hasNext()) {
					Creature creature = iterator.next();
					
					if(creature instanceof Plant) {
						Game.getGame().plants.add((Plant) creature);
					}
					else if(creature instanceof Animal) {
						Game.getGame().animals.add((Animal) creature);
					}
					iterator.remove();
				}
				
				System.gc();
				
				Thread.sleep(Constants.TIMEOUT);
				System.out.println(Game.getGame().plants.size());
				
		}
		}catch(InterruptedException e) {
			System.out.println("Thread finished");
		}
		
	}
}
