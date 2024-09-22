import java.util.Random;

public class FinalVersion {
    private static final int GRID_SIZE = 10;
    private static final int NUM_TERMITES = 10;
    private static final int MAX_STEPS = 30;
    private static final int MAX_CHIPS = 23;

    private static char[][] grid = new char[GRID_SIZE][GRID_SIZE];
    private static Random random = new Random();

    public static void main(String[] args) {
        initializeGrid();
        initializeTermites();
        initializeChips();

        for (int step = 0; step < MAX_STEPS; step++) {
            moveTermites();
            printGrid();
            // Uncomment the line below if you want to slow down the simulation
            // try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private static void initializeGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = '.';
            }
        }
    }

    private static void initializeTermites() {
        for (int i = 0; i < NUM_TERMITES; i++) {
            int startX = random.nextInt(GRID_SIZE);
            int startY = random.nextInt(GRID_SIZE);
            grid[startX][startY] = 'T';
        }
    }

    private static void initializeChips (){
        int countchips = 0;
        while (countchips < MAX_CHIPS){
            int startX = random.nextInt(GRID_SIZE);
            int startY = random.nextInt(GRID_SIZE);
            if (grid [startX][startY] =='T'){
                continue;
            }
            else {
                grid[startX][startY] = '#';
                countchips += 1;
            }
        }

    }

    private static int newdirection(int direction){

        int[] arr = {0, 1, 2, 3};

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == direction) {
                arr[i] = -1;
            }
        }

        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                count++;
            }
        }

        int[] newarr = new int[count];

        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                newarr[index++] = arr[i];
            }
        }

        int randIndex = random.nextInt(newarr.length);
        direction = newarr[randIndex];

        return direction;
    }


    private static void moveTermites() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 'T'|| grid[i][j] == '*') {
                    moveTermite(i, j);
                }
            }
        }
    }
    
    private static void moveTermite(int i, int j) {
        int direction = random.nextInt(4); // 0: up, 1: down, 2: left, 3: right
        int newX = i;
        int newY = j;

        // Update position based on the chosen direction
        if (direction == 0) { // Move up
            newX = (i - 1 + GRID_SIZE) % GRID_SIZE;
          } else if (direction == 1) { // Move down
            newX = (i + 1) % GRID_SIZE;
          } else if (direction == 2) { // Move left
            newY = (j - 1 + GRID_SIZE) % GRID_SIZE;
          } else if (direction == 3) { // Move right
            newY = (j + 1) % GRID_SIZE;
          }

        // motion of the termite with the wood chip and without the wood chip
        if (grid[newX][newY] == '.'&& grid[i][j] == 'T') {
            grid[newX][newY] = 'T';
            grid[i][j] = '.';
        } else if (grid[newX][newY] == '.'&& grid[i][j] == '*') {
            grid[newX][newY] = '*';
            grid[i][j] = '.';
        } else if (grid[newX][newY] == '#') {
            if (grid[i][j] == 'T') {
                // If the termite carries a wood chip, it drops the chip and continues wandering
                grid[newX][newY] = '*';
                grid[i][j] = '.';
            } else {

                if (grid[newX][newY] == '#' && grid[i][j] == '*') {
                    
                    int newdirection = newdirection(direction);
                    if (newdirection == 0) { // Move up
                        newX = (i - 1 + GRID_SIZE) % GRID_SIZE;
                    } else if (newdirection == 1) { // Move down
                        newX = (i + 1) % GRID_SIZE;
                    } else if (newdirection == 2) { // Move left
                        newY = (j - 1 + GRID_SIZE) % GRID_SIZE;
                    } else if (newdirection == 3) { // Move right
                        newY = (j + 1) % GRID_SIZE;
                    }
                    if (grid[newX][newY] == '.') {
                        grid[newX][newY] = 'T';
                        grid[i][j] = '#';
                    } else if (grid[newX][newY] == '#') {
                        grid[newX][newY] = '*';
                        grid[i][j] = '#';
                        
                    }

                }
            }
        }
    }


    


    private static void printGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}