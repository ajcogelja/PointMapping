import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.stage.*;
import javafx.scene.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Window extends Application {

    final double THRESHOLD = 25.0; //radius in which shifted points can exist
    Scene scene;
    Group root = new Group();
    Point2D[][] points = new Point2D[16][16]; //the points used to create the shapes

    public void start(Stage primaryStage) throws Exception{

        primaryStage = new Stage(StageStyle.DECORATED);
        scene = new Scene(root, 800, 800);
        Random random = new Random();
        primaryStage.setScene(scene);
        for(int i = 50; i < 800; i += 50){
            for(int j = 50; j < 800; j += 50){

                //(random - random) allows shift to be negative so the shapes dont exclusively get larger, although it does cause some warping on smaller polygons
                points[(i)/50][(j)/50]= new Point2D(i + ((int)((random.nextDouble() - random.nextDouble()) * THRESHOLD)), j + ((int)((random.nextDouble() - random.nextDouble())*THRESHOLD)));
                System.out.println("x: " + points[i/50][j/50].getX() + ", y: " + points[i/50][j/50].getY() + " i: " + i/50 + ", j: " + j/50); //shows location of generated points

            }
        }

        for(int i = 2; i < points.length - 2; i++){
            for (int j = 2; j < points[i].length - 2; j++){
                Point2D topLeft = points[i - 1][j - 1];
                Point2D topRight = points[i][j - 1];
                Point2D bottomRight = points[i][j];
                Point2D bottomLeft = points[i - 1][j];
                Polygon poly = new Polygon();
                poly.getPoints().addAll(topLeft.getX(), topLeft.getY(), topRight.getX(), topRight.getY(), bottomRight.getX(), bottomRight.getY(), bottomLeft.getX(), bottomLeft.getY());
                poly.setFill((Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble())));
                poly.setSmooth(true);
                root.getChildren().add(poly);
            }
        }
        primaryStage.show();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                randomize();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 1);
    }

    //makes the colors strobe wildly
    public void randomize(){
        Random r = new Random();
        int rand = r.nextInt(root.getChildren().size() - 1);
        Shape shape = (Shape) root.getChildren().get(rand);
        shape.setFill(Color.color(r.nextDouble(), r.nextDouble(), r.nextDouble()));
    }

    public static void main(String[] args){
        launch(args);

    }

}
