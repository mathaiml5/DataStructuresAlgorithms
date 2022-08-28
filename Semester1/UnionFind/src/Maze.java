/**
 * The Maze class generates a random maze of a specific size taking as input number of rows and columns of cells in the maze
 * uses Weighted Quick Union Find with Path compression (WQUFWPC) Algorithm on a set of cells with each cell representing a particular
 * row and column location in the grid of cells.
 *
 * Maze generation process:
 * 1. User inputs grid size: M rows and N columns in maze. To make the printing of the grid work we limit the rows and cols each to >=2 and <=50
 * 2. The maze is composed of objects from private inner class Cell which has 3 properties: a numeric cellIndex which represents its position (and also its name!)
 * boolean variables horizontal and vertical which are true if the walls exist in that direction.
 * 3. We start by initializing MxN grid of cells which are all disconnected. This means each cell has all 4 walls except the starting cell (top left labeled 'S')
 * and ending cell (bottom right labeled 'E'). We map these cells to the UnionFind Disjoint Set data structure and initialize each cell as its own singleton set with
 * its parent pointing to itself (i.e) we will start with M*N disjoint sets where each set Si is a singleton with the i-th node only
 * 4. The maze is constructed by gradually breaking down the walls one at a time by choosing a cell randomly and an orientation randomly.
 * (note: Since we need to connect top left to bottom right cell to form the maze, we prefer to break the walls that are either at the bottom or right
 * and use the horizontal and vertical boolean flag to indicate which wall is broken. Since we have 2 horizontal and 2 vertical walls for each cell we may end up
 * picking the same cell randomly and need to handle breaking the 2nd wall in the same horizontal or vertical orientation even if one is already broken)
 * 5. As we break down each wall, we take the 2 cells between which a wall is broken and merge the 2 sets that they are part of into 1 set using union find algorithm
 * with path compression. As we merge we take care to check the parent of each set being merged and make the smaller subset's parent point to the larger subset's
 * parent (Weighted Union). At every step we print out which cell is chosen and which wall orientation is chosen to be broken and the disjoint sets as well as print out
 * the maze at this step to show how we are progressing towards creating the fully connected maze
 * 6. As we break the cells, we handle for the boundary conditions of left and right columns and top and bottom rows and the starting and ending cells
 * 7. We have a valid fully connected maze when all the cells are in the same set (i.e.) count of disjoint sets is 1 indicating we have a fully connected maze where
 * each cell is connected to every other cell in the maze.
 *
 * @author: Vishak Srikanth
 * @version: 10/04/2021
 * Note: Maze will be assumed to always start from cell in top left corner and end at the cell in the bottom right corner
 */

import java.util.*;


public class Maze {

    //The Maze is stored as an ArrayList of ArrayLists for convenience with access and appending/updating
    private ArrayList<ArrayList<Cell>> maze = new ArrayList<ArrayList<Cell>>();
    //Size of the grid of cells numRows x numCols
    private int numRows;
    private int numCols;


    /**
     * Initializes a maze of specified size with all the cells disconnected, resulting in each cell being in its own set (disjoint from one another)
     *
     * @param rows total number of rows of cells in the maze
     * @param cols total number of columns of cells in the maze
     */
    private void initializeMaze(int rows, int cols) {

        //cells are numbered consecutively from 0...(rows*cols -1)
        int cellIndex = 0;
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> rowOfCells = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                rowOfCells.add(new Cell(cellIndex++, true, true));
                ; //Stores current index as cellIndex of the node
            }
            maze.add(rowOfCells);
        }
        numRows = rows;
        numCols = cols;
    }

    /**
     * Prints the maze
     */
    private void printMaze() {

        //Leave the top left corner cell as open and print Start marker
        System.out.print(" S ");
        for (int j = 1; j < numCols; j++) {
            System.out.print(" __"); //Print the topmost line
        }

        System.out.println();

        for (int i = 0; i < numRows; i++) {

            if (i == 0) {
                //Label the top left corner cell as starting cell
                System.out.print(" ");
            } else {
                System.out.print("|"); //Print the leftmost line
            }

            for (int j = 0; j < numCols; j++) {
                //If bottom right cell then print End marker
                if ((i == (numRows - 1)) && (j == (numCols - 1))) {
                    System.out.print(" E|");
                    continue;
                }
                //Check which walls are broken and set flags on each cell in grid
                if (maze.get(i).get(j).horizontal) {
                    System.out.print("__"); //Horizontal wall exists and is not broken
                } else {
                    System.out.print("  "); //Horizontal maze wall is broken
                }

                if (maze.get(i).get(j).vertical) {
                    System.out.print("|"); //Vertical wall exists and is not broken

                } else {
                    System.out.print(" "); //Vertical maze wall is broken
                }
            }

            System.out.println();
        }

    }

    /**
     * Creates the maze using disjoint set
     *
     * @param rows total rows
     * @param cols total columns
     */
    public void createMaze(int rows, int cols) {

        int total = rows * cols;
        //Create a disjoint set object for WQUFWPC algorithm
        WeightedQuickUnionFindWithPathCompression ds = new WeightedQuickUnionFindWithPathCompression(total); //Create an object of the disjoint set class


        initializeMaze(rows, cols); //Initialize the maze

        System.out.println("Initial Maze: ");
        printMaze();
        Random rand = new Random();
        int step = 1;
        //If there is 1 path between the starting and ending cell we are done
        // So we run this loop until we can get both starting and ending cell
        while (ds.count() != 1) {

            //Pick a random row number
            int currRow = rand.nextInt(rows);
            //Pick a random column number
            int currCol = rand.nextInt(cols);
            //Get the cell at the random location chosen
            Cell currCell = maze.get(currRow).get(currCol); //Current node(cell)
            //
            int currIndex = currCell.cellIndex;


            //Find the current cell's parent
            int root1 = ds.find(currIndex); //Find on current node(cell)


            int root2;
            //Initialize horizontal wall removal to false
            boolean removeHorizontal = false;

            //Nothing to do if the current cell is the bottom-right or ending cell
            if (currIndex == total - 1) {
                continue;
            }

            //Special Handling for last row
            if (currRow == rows - 1) {
                //We can only break the right wall for bottom row, we can't break the wall at the cell's bottom!
                root2 = ds.find(currIndex + 1);
            }
            //Special Handling for last column
            else if (currCol == cols - 1) {
                //If we are at the last column we can only break the wall at the bottom, can't break the right wall
                root2 = ds.find(currIndex + cols);
                removeHorizontal = true;
            }
            //If we are somehwere in middle of grid (i.e not at last row or last column
            else {

                //Randomly select either right wall or wall to the bottom to break
                boolean selectRight;
                selectRight = rand.nextBoolean();

                if (selectRight) {
                    root2 = ds.find(currIndex + 1);
                } else {
                    root2 = ds.find(currIndex + cols);
                    removeHorizontal = true;
                }
            }

            //Finds resulted in different sets
            if (root1 != root2) {

                ds.union(root1, root2); //Take union of the two sets

                if (removeHorizontal) {
                    currCell.horizontal = false; //Break the horizontal wall
                } else {
                    currCell.vertical = false; //Break the vertical wall
                }
            }
            System.out.println("********************************************************************************");
            System.out.println("Step: " + step + " Chosen cell: " + currIndex + " Breaking Wall: " + (currCell.horizontal == true ? "1st horizontal" : (currCell.vertical == true ? "1st vertical" : "2nd horizontal or vertical wall")));
            System.out.println("Total number of disjoint sets: " + ds.count());
            System.out.println(ds.printDisjSets());
            System.out.println("********************************************************************************");
            System.out.println("Current Maze:");
            System.out.println();
            printMaze();
            System.out.println();
            step++;
        }
        System.out.println("Fully connected maze completed after breaking " + (step - 1) + " walls !");
        //Leave the bottom right corner open
        Cell destination = maze.get(rows - 1).get(cols - 1);
        destination.horizontal = false;
        destination.vertical = false;
        System.out.println();
        System.out.println("********************************************************************************");
        System.out.println("***************************** FINAL MAZE ***************************************");
        System.out.println();
        printMaze();
        System.out.println();
        System.out.println("********************************************************************************");

//		printMaze(); //Print the final maze
    }

    /**
     * Private class to store a particular node (cell) of the maze
     */
    private class Cell {
        //The cell's index will be used as its unique id
        int cellIndex;
        //variable for horizontal walls
        boolean horizontal;
        //variable for vertical walls
        boolean vertical;


        /** Constructor to create a cell in the grid
         * @param value cellIndex or cell's id (name)
         * @param horizontal horizontal wall exists
         * @param vertical vertical wall exists
         */
        Cell(int value, boolean horizontal, boolean vertical) {
            this.cellIndex = value;
            this.horizontal = horizontal;
            this.vertical = vertical;

        }
    }

    //Main method which takes user input and creates a maze showing each step along the way and finally giving the time elapsed to create and print maze
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("********************************************************************************");
        System.out.println("***************************** MAZE GENERATOR ***********************************");
        System.out.println("********************************************************************************");
        System.out.println("Maze generator program uses Weighted Union Find Algorithm with Path Compression");
        System.out.println("to create a random fully connected maze of a given grid size that you specify!");
        System.out.println("********************************************************************************");
        System.out.println();
        int rows = 0, cols = 0;
        boolean invalidInput = true;
        boolean done = false;
        boolean bValidRows = false;
        boolean bValidCols = false;
        String inStr;
        // Keep prompting for a valid row, col values until user enters valid values that are between 2 and 50 (inclusive)
        while (!bValidRows) {

            System.out.println("Enter how many rows you need in the grid of the maze: ");
            inStr = sc.nextLine();
            if (inStr.matches("[\\d]+$")) {

                rows = Integer.parseInt(inStr);
                if (rows <= 50 && rows >= 2) {
                    bValidRows = true;
                }
            }
            if (!bValidRows) {
                System.out.println("Invalid input. Please enter a valid positive integer between 2 to 50 for number of rows in grid");
            }
        }
        while (!bValidCols) {
            System.out.println("Enter how many columns you need in the grid of the maze: ");
            inStr = sc.nextLine();
            if (inStr.matches("[\\d]+$")) {

                cols = Integer.parseInt(inStr);
                if (cols <= 50 && cols >= 2) {
                    bValidCols = true;
                }

            }
            if (!bValidCols) {
                System.out.println("Invalid input. Please enter a valid positive integer between 2 to 50 for number of columns in grid");
            }
        }

        //Gives the time taken to create and print maze
        System.out.println("********************************************************************************");
        long start = System.currentTimeMillis();
        Maze m = new Maze();
        m.createMaze(rows, cols);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Maze creation and printing for " + rows + "x" + cols + " grid took " + timeElapsed + " milliseconds!");
        sc.close();
    }

}

