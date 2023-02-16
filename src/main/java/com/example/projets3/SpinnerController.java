package com.example.projets3;

import javafx.animation.RotateTransition;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SpinnerController {
    private final Text label;
    private final RotateTransition rotateTransition;

    public SpinnerController(Text label) {
        this.label = label;
        rotateTransition = new RotateTransition(Duration.seconds(2), label);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
    }

    public void start() {
        label.setOnMouseEntered(event -> rotateTransition.play());
        label.setOnMouseExited(event -> rotateTransition.stop());
    }
}

