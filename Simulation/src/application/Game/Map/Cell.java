package application.Game.Map;

import application.Game.Creatures.Plants.Plant;

public class Cell {
	private Plant plant;
	public int v;
	public int h;
	public double x;
	public double y;
	
	public Cell(double x, double y, int h, int v){
		this.x = x;
		this.y = y;
		this.h = h;
		this.v = v;
	}
	Cell(Plant plant){
		this.plant = plant;
	}
	
	public Plant getPlant() {
		return plant;
	}
	public void setPlant(Plant plant) {
		this.plant = plant;
	}
}
