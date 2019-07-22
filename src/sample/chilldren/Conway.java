package sample.chilldren;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class Conway {

    public List<Line> createConway(double width, double height, double cellSize) {
        List<Line> lines = createLines(width, height, cellSize);
        return lines;
    }

    private List<Line> createLines(double width, double height, double cellSize) {
        List<Line> lines = new ArrayList<>();
        for (double x = 0; x < width; x = x + cellSize) {
            for (double y = 0; y < height; y = y + cellSize) {
                lines.add(new Line(0, y, width, y));
                lines.add(new Line(x, 0, x, height));
            }
        }
        return lines;
    }

    private Group createGroup(List<Line> chilldren) {
        Group group = new Group();
        group.getChildren().addAll(chilldren);
        return group;
    }

}
