package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;
import jdk.jfr.Event;

import java.awt.*;
import java.security.Key;
import java.util.EventListener;
import java.util.Random;

public class Main extends Application {
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private static final double MARGIN = 50;
    private static final double ARENAWIDTH = WIDTH - 2*MARGIN;
    private static final double ARENAHEIGHT = HEIGHT - 2*MARGIN;
    private static final double ARENAX1 = MARGIN;
    private static final double ARENAY1 = MARGIN;
    private static final double ARENAX2 = ARENAX1 + ARENAWIDTH;
    private static final double ARENAY2 = ARENAY1 + ARENAHEIGHT;
    private static final double R = 10;

//    private double x = ARENAX1 + ARENAWIDTH / 2;
//    private double y = ARENAY1 + ARENAHEIGHT /2;
//    private double vx = 10;
//    private double vy = 4;

    private static final int LICZBAKULEK = 10;
    private double[] x = new double[LICZBAKULEK];
    private double[] y = new double[LICZBAKULEK];
    private double[] vx = new double [LICZBAKULEK];
    private double[] vy = new double[LICZBAKULEK];

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        stage.setTitle("Kulki!");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();

//        gc.setFill(Color.BLACK);
//        gc.fillRect(ARENAX1, ARENAY1, ARENAWIDTH, ARENAHEIGHT);
//
//        gc.setFill((Color.WHITESMOKE));
//        gc.fillOval(ARENAX1 + ARENAWIDTH / 2, ARENAY1 + ARENAHEIGHT / 2, 2 * R, 2 * R);

        Timeline t = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    @FunctionalInterface
    public interface EventHandler<T extends Event> extends EventListener {
        void handle(T event);
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(ARENAX1, ARENAY1, ARENAWIDTH, ARENAHEIGHT);

        for (int i = 0; i < LICZBAKULEK; i++) {
            if((x[i] <= ARENAX1) || ((x[i] >= ARENAX2))) vx[i] = -vx[i];
            if((y[i] <= ARENAY1) || ((y[i] >= ARENAY2))) vy[i] = -vy[i];
        }

        for (int i = 0; i < LICZBAKULEK; i++) {
            x[i] += vx[i];
            x[i] += vy[i];
        }

        for (int i = 0; i < LICZBAKULEK; i++) {
            gc.setFill((Color.WHITESMOKE));
            gc.fillOval(x[i] - R, y[i] - R, 2 * R, 2 * R);
        }
    }

    private void initKula() {
        Random lott = new Random();
        for (int i = 0; i < LICZBAKULEK; i++) {
            x[i] = lott.nextDouble() * ARENAWIDTH + ARENAX1;
            y[i] = lott.nextDouble() * ARENAHEIGHT + ARENAY1;

            vx[i] = 5 + lott.nextDouble() * 20;
            vy[i] = 5 + lott.nextDouble() * 20;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
