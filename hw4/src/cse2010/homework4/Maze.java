package cse2010.homework4;
/*
 * CSE2010 Homework #4:
 * Problem 3: Maze
 *
 * Complete the code below.
 */

import java.util.Arrays;



public class Maze {
    private int numRows;
    private int numCols;

    private int[][] maze;
    private boolean[][] visited = null;

    private Location entry; // Entry Location
    private Location exit;  // Exit Location

    public Maze(int[][] maze, Location entry, Location exit) {

        this.maze = maze;
        numRows = maze.length;
        numCols = maze[0].length;
        visited = new boolean[numRows][numCols]; // initialized to false

        this.entry = entry;
        this.exit = exit;
    }

    public void printMaze() {

        System.out.println("Maze[" + numRows + "][" + numCols + "]");
        System.out.println("Entry index = (" + entry.row + ", " + entry.col + ")");
        System.out.println("Exit index = (" + exit.row + ", " + exit.col + ")" + "\n");

        for (int i = 0; i < numRows; i++) {
            System.out.println(Arrays.toString(maze[i]));
        }
        System.out.println();
    }

    public boolean findPath() {

        return moveTo(entry.row, entry.col);
    }

    private boolean moveTo(int row, int col) {
    	int North = row -1;
    	int East = col + 1;
    	int South = row + 1;
    	int West = col - 1;
    	//finish
    	if(row == this.exit.row && col == this.exit.col) return true;
    	
    	//North
    	if(North >= 0 && this.visited[North][col] == false && this.maze[North][col] != 1) {	
    		this.visited[North][col] = true;
    		boolean move = moveTo(North, col);
    		if(move) return true;
    	}
    	//East
    	if(East < this.numCols && this.visited[row][East] == false && this.maze[row][East] != 1) {
    		this.visited[row][East] = true;
    		boolean move = moveTo(row,East);
    		if(move) return true;
    	}
    	//South
    	if(South < this.numRows && this.visited[South][col] == false&& this.maze[South][col] != 1) {
    		this.visited[South][col] = true;
    		boolean move = moveTo(South, col);
    		if(move) return true;
    	}
    	//West
    	if(West >= 0 && this.visited[row][West] == false && this.maze[row][West] != 1) {
    		this.visited[row][West] = true;
    		boolean move = moveTo(row, West);
    		if(move) return true;
    	}
    	
        return false;
    }

}