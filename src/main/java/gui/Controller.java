package gui;

import Creature.Creature;
import battlefield.Battlefield;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.io.FileWriter;
public class Controller {
    @FXML
    private Pane mainpane;

    @FXML
    private GridPane fieldpane;

    @FXML
    private Button StartButton;

    @FXML
    private Button HistoryButton;

    @FXML
    private Button ExitButton;


    @FXML
    private Button RepeatButton;

    @FXML
    private Button SaveButton;

    @FXML
    private Button troopC0;

    @FXML
    private Button troopC1;

    @FXML
    private Button troopC2;

    @FXML
    private Button troopC3;

    @FXML
    private Button troopC4;

    @FXML
    private Button troopC5;

    @FXML
    private Button troopC6;

    @FXML
    private Button troopC7;

    @FXML
    private Label LabelS;

    @FXML
    private Label LabelL;

    @FXML
    private Label LabelE;

    @FXML
    private Label LabelSpace;


    private Battlefield field;

    private Image empty;

    private Boolean running;
    private String historyFile;
    private int scenenum;
    private int choose;

    @FXML
    private ImageView background;

    @FXML
    private ImageView CalaImage;

    @FXML
    private ImageView MonImage;

    @FXML
    private ImageView titleimage;

    public void Initial(){
        running = false;
        choose = 0;
        field = null;

        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
        background.setImage(new Image(this.getClass().getResourceAsStream("/background.jpg")));
        titleimage.setVisible(true);
        empty = new Image(this.getClass().getResourceAsStream("/None.png"));
        //WinnerImage.setImage(new Image(this.getClass().getResourceAsStream("/None.png")));
        CalaImage.setVisible(false);
        MonImage.setVisible(false);

    }

    private void showbattle(){
        if(field == null)
            return;
        field.setDisplay(true);
        if(running)
            field.SaveField();
        if(field.judgefinished() == 1){
            for(int i = 0; i < field.lenx; i++) {
                for (int j = 0; j < field.leny; j++) {
                    ImageView tmp = (ImageView) fieldpane.getChildren().get(field.leny * i+ j);
                    tmp.setImage(empty);

                }
            }
            CalaImage.setVisible(true);

        }
        else if(field.judgefinished() == -1){
            for(int i = 0; i < field.lenx; i++) {
                for (int j = 0; j < field.leny; j++) {
                    ImageView tmp = (ImageView) fieldpane.getChildren().get(field.leny * i+ j);
                    tmp.setImage(empty);

                }
            }

            MonImage.setVisible(true);
        }
        else{
            CalaImage.setVisible(false);
            MonImage.setVisible(false);
        }

        for(int i = 0; i < field.lenx; i++) {
            for (int j = 0; j < field.leny; j++) {
                if(field.getcreature(i,j).type != Creature.Type.NONEXISTENET && !field.getcreature(i,j).isalive) {
                    ImageView tmp = (ImageView) fieldpane.getChildren().get(field.leny * i+ j);
                    tmp.setImage(field.getcreature(i, j).getNowimage());

                    field.timecount[i][j]++;
                    if(field.timecount[i][j] == 20) {
                        field.Remove(i, j);
                        field.timecount[i][j]=0;
                    }

                }
            }
        }
        for(int i = 0; i < field.lenx; i++) {
            for (int j = 0; j < field.leny; j++) {
                if(field.getcreature(i,j).type == Creature.Type.NONEXISTENET || field.getcreature(i,j).isalive) {
                    ImageView tmp = (ImageView) fieldpane.getChildren().get(field.leny * i+ j);
                    tmp.setImage(field.getcreature(i, j).getNowimage());
                }
            }
        }
        field.setDisplay(false);
    }

    @FXML
    private void clickStartButton(MouseEvent event){
        RepeatButton.setVisible(false);
        SaveButton.setVisible(false);
        StartButton.setVisible(false);
        HistoryButton.setVisible(false);
        ExitButton.setVisible(false);
        titleimage.setVisible(false);
        fieldpane.setVisible(true);
        fieldpane.setDisable(false);
        LabelS.setVisible(false);
        LabelE.setVisible(false);
        LabelL.setVisible(false);

        LabelSpace.setVisible(true);

        troopC0.setVisible(true);
        troopC1.setVisible(true);
        troopC2.setVisible(true);
        troopC3.setVisible(true);
        troopC4.setVisible(true);
        troopC5.setVisible(true);
        troopC6.setVisible(true);
        troopC7.setVisible(true);
        background.setImage(new Image(this.getClass().getResourceAsStream("/battlescene.png")));

        field = new Battlefield();
        choose = 1;
        field.init();
        running = false;
        showbattle();
        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }


    @FXML
    private void clickHistoryButton(MouseEvent event){

        /*JFileChooser jFileChooser = new JFileChooser(".");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.showOpenDialog(null);
        File file = jFileChooser.getSelectedFile();*/
        FileChooser filechooser = new FileChooser();
        filechooser.setInitialDirectory(new File("."));
        File file = filechooser.showOpenDialog(mainpane.getScene().getWindow());
        if(file != null&& file.exists()){
            RepeatButton.setVisible(false);
            SaveButton.setVisible(false);
            StartButton.setVisible(false);
            HistoryButton.setVisible(false);
            ExitButton.setVisible(false);
            titleimage.setVisible(false);
            LabelS.setVisible(false);
            LabelE.setVisible(false);
            LabelL.setVisible(false);
            LabelSpace.setVisible(true);
            fieldpane.setVisible(true);
            fieldpane.setDisable(false);
            field = new Battlefield();
            choose = 2;
            field.init();
            historyFile = file.getAbsolutePath();
            field.readHistory(historyFile,0);
            showbattle();

            scenenum = 0;
            background.setImage(new Image(this.getClass().getResourceAsStream("/battlescene.png")));
        }
        else {

            if(field != null){
                String temp = field.getSavedhistory();
                Initial();
                field = new Battlefield();
                field.setSavedhistory(temp);
                StartButton.setVisible(true);
                HistoryButton.setVisible(true);
                ExitButton.setVisible(true);
                RepeatButton.setVisible(true);
                SaveButton.setVisible(true);
                titleimage.setVisible(true);
                LabelS.setVisible(true);
                LabelE.setVisible(true);
                LabelL.setVisible(true);
            }
            else {
                //Initial();
                //field = new Battlefield();


                StartButton.setVisible(true);
                HistoryButton.setVisible(true);
                ExitButton.setVisible(true);
                titleimage.setVisible(true);
                LabelS.setVisible(true);
                LabelE.setVisible(true);
                LabelL.setVisible(true);
            }

        }

        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }

    @FXML
    private void clickExitButton(MouseEvent event){
        System.exit(0);
    }



    @FXML
    private void clickRepeatButton(MouseEvent event){
        RepeatButton.setVisible(false);
        SaveButton.setVisible(false);
        StartButton.setVisible(false);
        HistoryButton.setVisible(false);
        ExitButton.setVisible(false);
        titleimage.setVisible(false);
        LabelS.setVisible(false);
        LabelE.setVisible(false);
        LabelL.setVisible(false);
        LabelSpace.setVisible(true);
        fieldpane.setVisible(true);
        fieldpane.setDisable(false);
        String temp = field.getSavedhistory();

        field = new Battlefield();
        field.setSavedhistory(temp);
        choose = 5;
        field.init();
        field.repeatHistory(0);

        showbattle();
        scenenum = 0;
        background.setImage(new Image(this.getClass().getResourceAsStream("/battlescene.png")));
        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }

    @FXML
    private void clickSaveButton(MouseEvent event){
        FileChooser filechooser = new FileChooser();
        filechooser.setInitialDirectory(new File("."));
        File file = filechooser.showSaveDialog(mainpane.getScene().getWindow());

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(file.getAbsolutePath());
            fwriter.write(field.getSavedhistory());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }

    @FXML
    private void clicktroopc0(MouseEvent event){
        field.setMchoose(0);
        field.init();
        showbattle();
        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }

    @FXML
    private void clicktroopc1(MouseEvent event){
        field.setMchoose(1);
        field.init();
        showbattle();
        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }

    @FXML
    private void clicktroopc2(MouseEvent event){
        field.setMchoose(2);
        field.init();
        showbattle();
        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }

    @FXML
    private void clicktroopc3(MouseEvent event){
        field.setMchoose(3);
        field.init();
        showbattle();
        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }

    @FXML
    private void clicktroopc4(MouseEvent event){
        field.setCchoose(4);
        field.init();
        showbattle();
        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }

    @FXML
    private void clicktroopc5(MouseEvent event){
        field.setCchoose(5);
        field.init();
        showbattle();
        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }

    @FXML
    private void clicktroopc6(MouseEvent event){
        field.setCchoose(6);
        field.init();
        showbattle();
        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }

    @FXML
    private void clicktroopc7(MouseEvent event){
        field.setCchoose(7);
        field.init();
        showbattle();
        mainpane.setFocusTraversable(true);
        mainpane.requestFocus();
    }

    @FXML
    private void pressKeyHandler(KeyEvent keyEvent) {

        switch (choose) {
            case 0: {
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    clickStartButton(null);
                }
                else if(keyEvent.getCode() == KeyCode.L){
                    clickHistoryButton(null);
                }
                else if(keyEvent.getCode() == KeyCode.E)
                    clickExitButton(null);
                break;

            }
            case 1: {
                if (keyEvent.getCode() == KeyCode.SPACE) {

                    troopC0.setVisible(false);
                    troopC1.setVisible(false);
                    troopC2.setVisible(false);
                    troopC3.setVisible(false);
                    troopC4.setVisible(false);
                    troopC5.setVisible(false);
                    troopC6.setVisible(false);
                    troopC7.setVisible(false);
                    String temp = "";

                    field.setSavedhistory(temp);
                    if(!running) {

                        LabelSpace.setVisible(false);
                        troopC0.setVisible(false);
                        troopC1.setVisible(false);
                        troopC2.setVisible(false);
                        troopC3.setVisible(false);
                        troopC4.setVisible(false);
                        troopC5.setVisible(false);
                        troopC6.setVisible(false);
                        troopC7.setVisible(false);
                        running = true;
                        showbattle();
                        field.start();

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                showbattle();
                                if(field.judgefinished() != 0){
                                    this.cancel();
                                    running = false;
                                    choose = 3;
                                    if(field.judgefinished() == 1){
                                        CalaImage.setVisible(true);

                                    }
                                    else if(field.judgefinished() == -1){

                                        MonImage.setVisible(true);
                                    }
                                }

                            }
                        }, 0, 50);
                    }
                }
                break;
            }
            case 2: {
                if(keyEvent.getCode() == KeyCode.SPACE){
                    if(!running){
                        LabelSpace.setVisible(false);
                        running = true;
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (!field.readHistory(historyFile,scenenum++)) {
                                    this.cancel();
                                }
                                else {
                                    showbattle();
                                    if (field.judgefinished() != 0) {
                                        this.cancel();
                                        running = false;

                                        showbattle();
                                        choose = 3;
                                        if(field.judgefinished() == 1){
                                            CalaImage.setVisible(true);

                                        }
                                        else if(field.judgefinished() == -1){

                                            MonImage.setVisible(true);
                                        }
                                    }
                                }

                            }
                        }, 0, 50);
                    }
                }
                break;
            }
            case 3:{
                if(keyEvent.getCode() == KeyCode.S){
                    clickSaveButton(null);
                }
                if(keyEvent.getCode() == KeyCode.SPACE){
                    background.setImage(new Image(this.getClass().getResourceAsStream("/background.jpg")));
                    for(int i = 0; i < field.lenx; i++) {
                        for (int j = 0; j < field.leny; j++) {
                            ImageView tmp = (ImageView) fieldpane.getChildren().get(field.leny * i+ j);
                            tmp.setImage(empty);

                        }
                    }
                    field.setRunthreads(false);
                    String temp = field.getSavedhistory();
                    Initial();
                    field = new Battlefield();

                    field.setSavedhistory(temp);
                    StartButton.setVisible(true);
                    HistoryButton.setVisible(true);
                    ExitButton.setVisible(true);
                    RepeatButton.setVisible(true);
                    SaveButton.setVisible(true);
                    titleimage.setVisible(true);
                    LabelS.setVisible(true);
                    LabelE.setVisible(true);
                    LabelL.setVisible(true);

                }
                break;
            }
            case 5: {
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    if (!running) {
                        LabelSpace.setVisible(false);
                        running = true;
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (!field.repeatHistory(scenenum++)) {
                                    this.cancel();
                                } else {
                                    showbattle();
                                    if (field.judgefinished() != 0) {
                                        this.cancel();
                                        running = false;

                                        showbattle();
                                        choose = 3;
                                        if (field.judgefinished() == 1) {
                                            CalaImage.setVisible(true);
                                        } else if (field.judgefinished() == -1) {
                                            MonImage.setVisible(true);
                                        }
                                    }
                                }

                            }
                        }, 0, 50);
                    }

                }
                break;
            }
        }
    }



}
