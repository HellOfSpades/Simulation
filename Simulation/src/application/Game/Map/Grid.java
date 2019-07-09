package application.Game.Map;

import application.Game.Creatures.Plants.Plant;

public class Grid {
	
	public Cell[][] cells;
	public double cellwidth;
	public double cellheight;
	
	public Grid(double screenwidth, double screenheight, int cellshorizontal, int cellsvertical){
		this.cells = new Cell[cellsvertical][cellshorizontal];
		
		this.cellwidth = screenwidth/cellshorizontal;
		this.cellheight = screenheight/cellsvertical;
		
		for(int i = 0;i<cellsvertical;i++) {
			for(int n = 0;n<cellshorizontal;n++) {
				cells[i][n] = new Cell(n*cellwidth,i*cellheight,n,i);
			}
		}
	}
	
	
	public Cell getClossestCell(double x, double y) {
		
		int w = (int) ((x/cellwidth));
		int h = (int) ((y/cellheight));
				
		return cells[h][w];
	}
}
