package application.Game.Creatures.Plants;

import application.Game.Creatures.Creature;
import application.Game.Map.Cell;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;

public abstract class Plant extends Creature{
	
	public Cell currentCell;
	
	public Plant(Canvas canvas, Cell cell) {
		super(canvas);
		this.currentCell = cell;
		x = cell.x;
		y = cell.y;
		cell.setPlant(this);
	}

}
