package sample.model;

import com.sun.deploy.util.ArrayUtil;
import javafx.scene.control.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Cells {

    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private boolean isAlive;

    public Cells(double startX, double startY, double endX, double endY, boolean isAlive) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.isAlive = isAlive;
    }

    public Cells() {
    }

    public Cells[][] createMockCells(double width, double height, double cellSize) {
        int x = (int) (width / cellSize);
        int y = (int) (height / cellSize);
        Cells[][] cellsArray = new Cells[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                cellsArray[i][j] = new Cells(i * cellSize, j * cellSize, x * cellSize, y * cellSize, new Random().nextBoolean());
            }
        }
        return cellsArray;
    }

    public Cells[][] updateCellGeneration(Cells[][] cells) {
        Cells[][] newCellsArray = cells;
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                newCellsArray[x][y].setAlive(isCellHaveLessThanTwoNeighbors(x, y, cells));
            }
        }
        return newCellsArray;
    }

    private boolean isCellHaveLessThanTwoNeighbors(int x, int y, Cells[][] cells) {
        if (cells.length != 0) {
            List<Cells> possibleNeighbors = getPossibleNeigbors(x, y, cells);
            int liveNeigbors = 0;
            for (Cells neighbors : possibleNeighbors) {
                if (neighbors.isAlive) {
                    liveNeigbors++;
                }
            }
            if (cells[x][y].isAlive) {
                if (liveNeigbors < 2) {
                    return false;
                } else if (cells[x][y].isAlive && liveNeigbors > 3) {
                    return false;
                } else if (cells[x][y].isAlive && liveNeigbors == 2 || liveNeigbors == 3) {
                    return true;
                }
            } else if (!cells[x][y].isAlive && liveNeigbors == 3) {
                return true;
            }
        }
        return false;
    }

    private List<Cells> getPossibleNeigbors(int x, int y, Cells[][] cells) {
        List<Cells> cellsList = new ArrayList<>();
        if (x > 0 && x != cells.length - 1 && y > 0 && y != cells[x].length - 1) { //Global center
            cellsList.add(cells[x - 1][y - 1]);
            cellsList.add(cells[x][y - 1]);
            cellsList.add(cells[x + 1][y - 1]);
            cellsList.add(cells[x + 1][y]);
            cellsList.add(cells[x + 1][y + 1]);
            cellsList.add(cells[x][y + 1]);
            cellsList.add(cells[x - 1][y + 1]);
            cellsList.add(cells[x - 1][y]);
        } else if (x == 0 && y == 0) { //Left top
            cellsList.add(cells[x + 1][y]);
            cellsList.add(cells[x + 1][y + 1]);
            cellsList.add(cells[x][y + 1]);
        } else if (x > 0 && x != cells.length - 1 && y == 0) { //Center top
            cellsList.add(cells[x - 1][y]);
            cellsList.add(cells[x - 1][y + 1]);
            cellsList.add(cells[x][y + 1]);
            cellsList.add(cells[x + 1][y + 1]);
            cellsList.add(cells[x + 1][y]);
        } else if (x == cells.length - 1 && y == 0) { //Right top
            cellsList.add(cells[x - 1][y]);
            cellsList.add(cells[x - 1][y + 1]);
            cellsList.add(cells[x][y + 1]);
        } else if (x == cells.length - 1 && y > 0 && y != cells[x].length - 1) { //Right center
            cellsList.add(cells[x][y - 1]);
            cellsList.add(cells[x - 1][y - 1]);
            cellsList.add(cells[x - 1][y]);
            cellsList.add(cells[x - 1][y + 1]);
            cellsList.add(cells[x][y + 1]);
        } else if (x == cells.length - 1 && y == cells[x].length - 1) { //Right bottom
            cellsList.add(cells[x][y - 1]);
            cellsList.add(cells[x - 1][y - 1]);
            cellsList.add(cells[x - 1][y]);
        } else if (x > 0 && x != cells.length - 1 && y == cells[0].length - 1) { //Bottom center
            cellsList.add(cells[x - 1][y]);
            cellsList.add(cells[x - 1][y - 1]);
            cellsList.add(cells[x][y - 1]);
            cellsList.add(cells[x + 1][y - 1]);
            cellsList.add(cells[x + 1][y]);
        } else if (x > 0 && x != cells.length - 1 && y == cells[x].length - 1) { //Right bottom
            cellsList.add(cells[x][y - 1]);
            cellsList.add(cells[x + 1][y - 1]);
            cellsList.add(cells[x + 1][y]);
        } else if (x == 0 && y > 0 && y != cells[x].length - 1) { //Left center
            cellsList.add(cells[x][y - 1]);
            cellsList.add(cells[x + 1][y - 1]);
            cellsList.add(cells[x + 1][y]);
            cellsList.add(cells[x + 1][y + 1]);
            cellsList.add(cells[x][y + 1]);
        }
        return cellsList;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
