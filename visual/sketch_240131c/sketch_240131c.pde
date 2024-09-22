int GRID_SIZE = 200;
int NUM_TERMITES = 100;
int MAX_CHIPS = 500;

char[][] grid = new char[GRID_SIZE][GRID_SIZE];

void setup() {
  size(800, 800);
  initializeGrid();
  initializeTermites();
  initializeChips();
}

void draw() {
  background(255);
  moveTermites();
  drawGrid();
}

void initializeGrid() {
  for (int i = 0; i < GRID_SIZE; i++) {
    for (int j = 0; j < GRID_SIZE; j++) {
      grid[i][j] = '.';
    }
  }
}

void initializeTermites() {
  for (int i = 0; i < NUM_TERMITES; i++) {
    int startX = int(random(GRID_SIZE));
    int startY = int(random(GRID_SIZE));
    grid[startX][startY] = 'T';
  }
}

void initializeChips() {
  int countChips = 0;
  while (countChips < MAX_CHIPS) {
    int startX = int(random(GRID_SIZE));
    int startY = int(random(GRID_SIZE));
    if (grid[startX][startY] == '.') {
      grid[startX][startY] = '#';
      countChips++;
    }
  }
}

int newDirection(int direction) {
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

  int randIndex = int(random(newarr.length));
  direction = newarr[randIndex];

  return direction;
}

void moveTermites() {
  for (int i = 0; i < GRID_SIZE; i++) {
    for (int j = 0; j < GRID_SIZE; j++) {
      if (grid[i][j] == 'T' || grid[i][j] == '*') {
        moveTermite(i, j);
      }
    }
  }
}

void moveTermite(int i, int j) {
  int newX = i;
  int newY = j;

  int direction = int(random(4)); // 0: up, 1: down, 2: left, 3: right
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
  if (grid[newX][newY] == '.' && grid[i][j] == 'T') {
    grid[newX][newY] = 'T';
    grid[i][j] = '.';
  } else if (grid[newX][newY] == '.' && grid[i][j] == '*') {
    grid[newX][newY] = '*';
    grid[i][j] = '.';
  } else if (grid[newX][newY] == '#') {
    if (grid[i][j] == 'T') {
      // If the termite carries a wood chip, it drops the chip and continues wandering
      grid[newX][newY] = '*';
      grid[i][j] = '.';
    } else {
      if (grid[newX][newY] == '#' && grid[i][j] == '*') {
        int newDirection = newDirection(direction);
        if (newDirection == 0) { // Move up
          newX = (i - 1 + GRID_SIZE) % GRID_SIZE;
        } else if (newDirection == 1) { // Move down
          newX = (i + 1) % GRID_SIZE;
        } else if (newDirection == 2) { // Move left
          newY = (j - 1 + GRID_SIZE) % GRID_SIZE;
        } else if (newDirection == 3) { // Move right
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

void drawGrid() {
  float cellSize = width / GRID_SIZE;
  for (int i = 0; i < GRID_SIZE; i++) {
    for (int j = 0; j < GRID_SIZE; j++) {
      float x = i * cellSize;
      float y = j * cellSize;

      if (grid[i][j] == 'T') {
        fill(255, 0, 0); // Red for termites
      } else if (grid[i][j] == '#') {
        fill(0, 0, 255); // Blue for wood chips
      } else if (grid[i][j] == '*') {
        fill(0, 0, 0);
      } else {
        fill(255); // White for empty cells
      }

      rect(x, y, cellSize, cellSize);
    }
  }
}
