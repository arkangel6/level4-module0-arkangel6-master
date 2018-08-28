package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//4. select a random cell to start
		
		int x = (int) (Math.random()*w);
		
		//5. call selectNextPath method with the randomly selected cell
		selectNextPath(maze.getCell(0, 0));
		
		return maze;
	}

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		//A. mark cell as visited
		currentCell.setBeenVisited(true);
		System.out.println(currentCell.getX() + " " + currentCell.getY());
		//B. check for unvisited neighbors using the cell
		ArrayList<Cell> nList = getUnvisitedNeighbors(currentCell);
		//C. if has unvisited neighbors,
		if(nList.size() > 0) {
			//C1. select one at random.
			int x = (int) (Math.random()*nList.size());
			//C2. push it to the stack
			
			uncheckedCells.push(nList.get(x));
			
			//C3. remove the wall between the two cells
			removeWalls(currentCell, nList.get(x));
			//C4. make the new cell the current cell and mark it as visited
			currentCell = nList.get(x);
			currentCell.setBeenVisited(false);
			selectNextPath(currentCell);
		}else {
		//D. if all neighbors are visited
		
			//D1. if the stack is not empty
			if(!uncheckedCells.isEmpty()) {
				// D1a. pop a cell from the stack
				currentCell = uncheckedCells.pop();
				// D1b. make that the current cell
				currentCell.setBeenVisited(false);
				selectNextPath(currentCell);
			}
		}
		
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		
		//System.out.println(c1.getX() + " " + c1.getY() + " c2 " + c2.getX() + " " + c2.getY());
		
		if(c2.getX()+1 != -1) {
		if((c1.getX() == c2.getX()+1) && (c1.getY() == c2.getY())) {
			
			c1.setWestWall(false);
			c2.setEastWall(false);
		}
		}
		
		if(c1.getX()+1 != -1) {
		if((c1.getX()+1 == c2.getX()) && (c1.getY() == c2.getY())) {
			
			c2.setWestWall(false);
			c1.setEastWall(false);
		}
		}
		
		if(c2.getY()+1 != -1) {
		if((c1.getY() == c2.getY()+1) && (c1.getX() == c2.getX())) {
			
			 c2.setSouthWall(false);
			 c1.setNorthWall(false);
		}
		}
		
		if(c1.getY()+1 != -1) {
		if((c1.getY()+1 == c2.getY()) && (c1.getX() == c2.getX())) {
			
			c2.setNorthWall(false);
			c1.setSouthWall(false);
		}
		}
		
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> list = new ArrayList<Cell>();
		
		if(c.getX()+1 != maze.getWidth()) {
		if(!maze.getCell(c.getX()+1, c.getY()).hasBeenVisited()) {
			list.add(maze.getCell(c.getX()+1, c.getY()));
		}
		}
		
		if(c.getX()-1 != -1) {
		if(!maze.getCell(c.getX()-1, c.getY()).hasBeenVisited()) {
			list.add(maze.getCell(c.getX()-1, c.getY()));
		}
		}
		
		if(c.getY()+1 != maze.getHeight()) {
		if(!maze.getCell(c.getX(), c.getY()+1).hasBeenVisited()) {
			list.add(maze.getCell(c.getX(), c.getY()+1));
		}
		}
		
		if(c.getY()-1 != -1) {
		if(!maze.getCell(c.getX(), c.getY()-1).hasBeenVisited()) {
			list.add(maze.getCell(c.getX(), c.getY()-1));
		}
		}

		return list;
	}
}