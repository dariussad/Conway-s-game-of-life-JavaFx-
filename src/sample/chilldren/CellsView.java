package sample.chilldren;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.model.Cells;

import java.util.ArrayList;
import java.util.List;

public class CellsView {

    public List<Rectangle> createCells(double width, double height, double cellSize, Cells[][] cellsArray) {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < cellsArray.length; i++) {
            for (int j = 0; j < cellsArray[i].length; j++) {
                if (cellsArray[i][j].isAlive()) {
                    Rectangle rectangle = new Rectangle(cellsArray[i][j].getStartX(), cellsArray[i][j].getStartY(), cellSize, cellSize);
                    rectangle.setFill(Color.LAWNGREEN);
                    rectangles.add(rectangle);
                }
            }
        }
        return rectangles;
    }
}
