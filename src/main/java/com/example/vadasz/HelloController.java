package com.example.vadasz;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private final int DEAD = 1;
    private final int FOX = 2;
    private final int HOME = 3;
    private final int TREE = 4;

    private int rokaDb = 0;
    private int rokaMax = 0;
    private int es = 0;
    private int eo = 0;
    private int lovesDb = 0;
    private int talaltDb = 0;

    private AnimationTimer timer = null;
    private long most = 0;
    private long tt = 0;

    private ImageView[][] it = new ImageView[16][32];
    private int[][] t = new int[16][32];

    public void initialize(){
        for(int i = 0; i<5;i++) icon[i] = new Image(getClass().getResourceAsStream(nevek[i]+".png" ));
        for(int s = 0;s<16;s++){
            for(int o = 0;o<32;o++){
                int ss = s, oo = o;
                it[s][o] = new ImageView(icon[DARK]);
                it[s][o].setLayoutX(10+o*48);
                it[s][o].setLayoutY(10+s*48);
                it[s][o].setOnMouseEntered(e -> vilagit(ss, oo));
                it[s][o].setOnMousePressed(e -> loves(ss, oo));
                pnJatek.getChildren().add(it[s][o]);
            }
        }
        gondol();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                most = now;
                if(now > tt) elbujik();
            }
        };
        timer.start();
    }


    private void gondol(){
        for(int s = 0;s<16;s++){
            for(int o = 0;o<32;o++){
                if(Math.random() < 0.1) { t[s][o] = FOX; rokaDb++; }
                else t[s][o] = TREE;
            }
        }
        rokaMax = rokaDb;
        lbRoka.setText(rokaDb + " / " + rokaMax);
    }

    private void vilagit(int s, int o){
        if(s!=es || o!=eo){
            for(int dS=-2; dS <= 2; dS++){
                for(int dO=-2; dO <= 2; dO++){
                    int ss = es+dS, oo = eo+dO;
                    if(ss>=0 && ss<=15 && oo>=0 && oo<=31 && !(Math.abs(dS)==2 && Math.abs(dO)==2)){
                        it[ss][oo].setImage(icon[DARK]);
                    }
                }
            }

            for(int dS=-2; dS <= 2; dS++){
                for(int dO=-2; dO <= 2; dO++){
                    int ss = s+dS, oo = o+dO;
                    if(ss>=0 && ss<=15 && oo>=0 && oo<=31 && !(Math.abs(dS)==2 && Math.abs(dO)==2)){
                        it[ss][oo].setImage(icon[t[ss][oo]]);
                    }
                }
            }
            es = s; eo = o;
            tt = most + 500_000_000; // ns
        }

    }

    private void elbujik(){
        for(int dS=-2; dS <= 2; dS++){
            for(int dO=-2; dO <= 2; dO++){
                int ss = es+dS, oo = eo+dO;
                if(ss>=0 && ss<=15 && oo>=0 && oo<=31 && !(Math.abs(dS)==2 && Math.abs(dO)==2) && t[ss][oo] == FOX){
                    it[ss][oo].setImage(icon[HOME]);
                    t[ss][oo] = HOME;
                    rokaDb--;
                }
            }
        }

    }

    private void loves(int s, int o){
        if(t[s][o] == FOX){
            it[s][o].setImage(icon[DEAD]);
            t[s][o] = DEAD;
            rokaDb--;
            talaltDb++;
        }
        lovesDb++;
        lbRoka.setText(rokaDb + " / " + rokaMax);
        lbLoves.setText(lovesDb+" lövés / "+talaltDb+" találat");
    }

    private void felfed(){
        for(int s = 0;s<16;s++){
            for(int o = 0;o<32;o++){
                it[s][o].setImage(icon[t[s][o]]);
            }
        }
    }

    private void vege(){
        if(rokaDb==0) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("GYŐZTÉL");
            a.setHeaderText(null);
            String txt = String.format("%d lövésből %d talált, ami %d%%\n", lovesDb, talaltDb, talaltDb*100/lovesDb);
            txt += String.format("%d rókából %d lett lelőve, ami %d%%", rokaMax, talaltDb, talaltDb*100/rokaMax);
            a.setContentText(txt);

            a.getButtonTypes().removeAll();
            a.getButtonTypes().add(new ButtonType("Újra"));
            a.onCloseRequestProperty(e -> gondol());
            a.show();
        }
    }

}
