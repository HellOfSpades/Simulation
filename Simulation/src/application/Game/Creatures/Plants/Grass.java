package application.Game.Creatures.Plants;

import java.util.ArrayList;
import java.util.Random;

import application.Game.Game;
import application.Game.Creatures.Creature;
import application.Game.Map.Cell;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grass extends Plant{
	
	int level = 1;
	static final int maxlevel = 4;
	
	public Grass(Canvas canvas, Cell cell) {
		super(canvas,cell);
		init();
	}
	
	private void init() {
		lifespan = 30;
		nutrients = 1;
	}

	@Override
	public void update()  {
		
		
		
		if(lifespan>0) {
			
			Random random = new Random();
			
			if(random.nextInt(10)==1) {
				ArrayList<Cell> directions = new ArrayList<Cell>();
				
				//Checking the left
				if(currentCell.h>0 && Game.getGame().grid.cells[currentCell.v][currentCell.h-1].getPlant()==null)
					directions.add(Game.getGame().grid.cells[currentCell.v][currentCell.h-1]);
				//if the grass is on the edge, it will grow to the other side, only horizontally
				else if(currentCell.h==0 && Game.getGame().grid.cells[currentCell.v][Game.getGame().grid.cells[currentCell.v].length-1].getPlant()==null) {
					directions.add(Game.getGame().grid.cells[currentCell.v][Game.getGame().grid.cells[currentCell.v].length-1]);
				}
				
				//Checking the right
				if(currentCell.h<Game.getGame().grid.cells[0].length-1 && Game.getGame().grid.cells[currentCell.v][currentCell.h+1].getPlant()==null)
					directions.add(Game.getGame().grid.cells[currentCell.v][currentCell.h+1]);
				//if the grass is on the edge, it will grow to the other side, only horizontally
				else if(currentCell.h==Game.getGame().grid.cells[0].length-1 && Game.getGame().grid.cells[currentCell.v][0].getPlant()==null) {
					directions.add(Game.getGame().grid.cells[currentCell.v][0]);
				}
				
				//Checking the up
				if(currentCell.v>0 && Game.getGame().grid.cells[currentCell.v-1][currentCell.h].getPlant()==null)
					directions.add(Game.getGame().grid.cells[currentCell.v-1][currentCell.h]);
				
				//Checking the down
				if(currentCell.v<Game.getGame().grid.cells.length-1 && Game.getGame().grid.cells[currentCell.v+1][currentCell.h].getPlant()==null)
					directions.add(Game.getGame().grid.cells[currentCell.v+1][currentCell.h]);

				
				if(directions.size()>0) {
					int chosen = random.nextInt(directions.size());
					Cell cell = directions.get(chosen);
					Grass thegrass = new Grass(canvas,cell);
					Game.getGame().updater.added.add(thegrass);
					cell.setPlant(thegrass);
				}else if(level<maxlevel){
					level++;
				}
				
			}
		}
		
		if(lifespan<=-5) {
			Game.getGame().updater.removed.add(this);
		}
		
		lifespan--;
	}
	public void draw(GraphicsContext g) {
		Color c = Color.GREEN.brighter().brighter().brighter();
		
		if(lifespan<=0) {
			c = Color.ORANGE.darker();
		}
		
		for(int i = 1;i<level;i++) {
			c = c.darker();
		}
		
		
		g.setFill(c);
		g.setStroke(Color.GREEN.darker().darker());
		g.fillRect(currentCell.x, currentCell.y, Game.getGame().grid.cellwidth, Game.getGame().grid.cellheight);
		
	}
	
	
	
}
