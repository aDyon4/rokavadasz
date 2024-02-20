package com.example.vadasz;

import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class HelloController {
    @FXML private Pane pnJatek;
    @FXML private Label lbLoves;
    @FXML private Label lbRoka;

    private String[] nevek = {"dark", "dead", "fox", "home", "tree"};
    private Image[] icon = new Image[5];

    private final int DARK = 0;

    private ImageView[][] it = new ImageView[16][32];
    private int[][] t = new int[16][32];

    public void initialize(){
        for(int i = 0; i<5;i++) icon[i] = new Image(getClass().getResourceAsStream(nevek[i]+".png" ));
        for(int s = 0;s<16;s++){
            for(int o = 0;o<32;o++){
                it[s][o] = new ImageView(icon[DARK]);
                it[s][o].setLayoutX(10+o*48);
                it[s][o].setLayoutY(10+s*48);
                pnJatek.getChildren().add(it[s][o]);
            }
        }
    }
}