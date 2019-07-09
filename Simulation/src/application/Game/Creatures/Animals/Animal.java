package application.Game.Creatures.Animals;

import application.Game.Creatures.Creature;
import javafx.scene.canvas.Canvas;

public abstract class Animal extends Creature{

	public Animal(Canvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
	}
	public Animal(Canvas canvas, int x, int y) {
		super(canvas,x,y);
		// TODO Auto-generated constructor stub
	}
	
	public void move(int dist, double angle){
		
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
