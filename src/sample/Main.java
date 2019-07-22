package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.chilldren.CellsView;
import sample.chilldren.Conway;
import sample.model.Cells;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private List<Node> nodes = new ArrayList<>();
    private Conway conway = new Conway();
    private Cells cells = new Cells();
    private CellsView cellsView = new CellsView();
    private Cells[][] cellsArray;
    private Stage primaryStage;
    private Scene scene;
    private EventHandler<KeyEvent> keyboardHandler;
    private EventHandler<MouseEvent> mouseHandler;
    private AnimationTimer timer;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private static final double CELL_SIZE = 4;
    private static final long SPEED = 100L;
    private boolean isStopped = false;
    private boolean isGenerationOn = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        setupStuff(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Conway game of life");
        createNodes(nodes, cells, cellsView);
        this.scene = new Scene(new Group(nodes), WIDTH, HEIGHT);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
        this.timer.start();
    }

    private void setupStuff(Stage primaryStage) {
        this.primaryStage = primaryStage;


        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    Thread.sleep(SPEED);
                    updateNodes(nodes, cells, cellsView);
                    scene = new Scene(new Group(nodes), WIDTH, HEIGHT);
                    scene.setFill(Color.BLACK);
                    scene.setOnMouseClicked(mouseHandler);
                    scene.setOnKeyPressed(keyboardHandler);
                    primaryStage.setScene(scene);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        this.keyboardHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode().getName());
                if (!isStopped && event.getCode().getName().equals("Space")) {
                    timer.stop();
                    isStopped = true;
                    System.out.println("Stoped");
                } else if (isStopped && event.getCode().getName().equals("Space")) {
                    timer.start();
                    isStopped = false;
                    System.out.println("Started");
                } else if (event.getCode().getName().equals("Delete")) {
                    clearTheScreen();
                } else if (event.getCode().getName().equals("G")) {
                    if(isGenerationOn) {
                        isGenerationOn = false;
                    } else {
                        isGenerationOn = true;
                    }
                }
            }
        };

        this.mouseHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                int x = (int) (mouseEvent.getX() / CELL_SIZE);
                int y = (int) (mouseEvent.getY() / CELL_SIZE);
                System.out.println(x + " : " + y);
                if (cellsArray[x][y].isAlive()) {
                    cellsArray[x][y].setAlive(false);
                    nodes.clear();
                    nodes.addAll(cellsView.createCells(WIDTH, HEIGHT, CELL_SIZE, cellsArray));
                } else {
                    cellsArray[x][y].setAlive(true);
                    Rectangle rectangle = new Rectangle(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    rectangle.setFill(Color.LAWNGREEN);
                    nodes.add(rectangle);
                }
                scene = new Scene(new Group(nodes), WIDTH, HEIGHT);
                scene.setFill(Color.BLACK);
                scene.setOnKeyPressed(keyboardHandler);
                scene.setOnMouseClicked(mouseHandler);
                primaryStage.setScene(scene);
                primaryStage.show();
                System.out.println(cellsArray[x][y].isAlive());
            }

        };
    }

    private void clearTheScreen() {
        clearCellsArray();
        nodes.clear();
        scene = new Scene(new Group(nodes), WIDTH, HEIGHT);
        scene.setFill(Color.BLACK);
        scene.setOnKeyPressed(keyboardHandler);
        scene.setOnMouseClicked(mouseHandler);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearCellsArray() {
        for(int i = 0 ; i<cellsArray.length; i++){
            for(int j = 0 ; j<cellsArray[i].length; j++){
                cellsArray[i][j].setAlive(false );
            }
        }
    }

    private List<Node> updateNodes(List<Node> nodes, Cells cells, CellsView cellsView) {
        cellsArray = cells.updateCellGeneration(cellsArray);
        nodes.clear();
        nodes.addAll(cellsView.createCells(WIDTH, HEIGHT, CELL_SIZE, cellsArray));
        return nodes;
    }

    private List<Node> createNodes(List<Node> nodes, Cells cells, CellsView cellsView) {
        cellsArray = cells.createMockCells(WIDTH, HEIGHT, CELL_SIZE);
        nodes.clear();
        if (isGenerationOn) {
            nodes.addAll(cellsView.createCells(WIDTH, HEIGHT, CELL_SIZE, cellsArray));
        }
        return nodes;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
