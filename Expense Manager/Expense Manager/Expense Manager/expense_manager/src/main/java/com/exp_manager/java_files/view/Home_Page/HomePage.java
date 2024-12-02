package com.exp_manager.java_files.view.Home_Page;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HomePage {

    private Stage prStage;
    private Scene mainpageScene, homScene, piePageScene, catPageScene, transferPageScene, setTargetPageScene,
            homepageScene;

    public void setScene(Scene scene) {
        this.homepageScene = scene;
    }

    public void setStage(Stage prStage) {
        this.prStage = prStage;
    }

    public StackPane hPageScene(Runnable backHandler) {
        prStage.setTitle("EXPENSE MANAGER");
        prStage.setHeight(1000);
        prStage.setWidth(2000);
        prStage.setResizable(true);
        prStage.setFullScreen(true);

        // final MainPage mainPage = new MainPage();

        // Load images
        Image categoryImage = new Image("images/category.png"); // Update the path to your image file
        Image transferImage = new Image("images/transfer.jpg"); // Update the path to your image file
        Image addExpenseImage = new Image("images/add.png"); // Update the path to your image file
        Image setTargetImage = new Image("images/target.jpg"); // Update the path to your image file
        Image savingImage = new Image("images/savings.png"); // Update the path to your image file
        Image backgroundImage = new Image("images/background.jpg"); // Update the path to your background image file
        ImageView backgrImageView = new ImageView(backgroundImage);
        backgrImageView.setFitHeight(1000);
        backgrImageView.setFitWidth(2500);
        backgrImageView.setPreserveRatio(false);

        // Create buttons with images
        Button catbtn = new Button("Category");
        ImageView categoryImageView = new ImageView(categoryImage);
        setupButtonWithImage(catbtn, categoryImageView);
        catbtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Categorise categorise = new Categorise();
                // categorise.start(prStage);
                categoryPageScene();
            }
        });

        Button trabtn = new Button("Transfer");
        ImageView transferImageView = new ImageView(transferImage);
        setupButtonWithImage(trabtn, transferImageView);
        trabtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // TransferPage transferPage = new TransferPage();
                // transferPage.start(prStage);
                transferPageScene();
            }
        });

        // final MainPage mainPage = new MainPage();

        Button addExpbtn = new Button("Add Expense");
        ImageView addExpenseImageView = new ImageView(addExpenseImage);
        setupButtonWithImage(addExpbtn, addExpenseImageView);
        addExpbtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // MainPage mainPage = new MainPage();
                // if (mainPage == null) {
                // MainEntry mainPage = new MainEntry();
                // } else {
                // mainPage.start(prStage);
                // }
                // initializeMainPage();
                mainPageScene();
            }

        });

        Button setTarbtn = new Button("Set Target");
        ImageView setTargetImageView = new ImageView(setTargetImage);
        setupButtonWithImage(setTarbtn, setTargetImageView);
        setTarbtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // SetTarget setTarget = new SetTarget();
                // setTarget.start(prStage);
                setTargetPageScene();
            }
        });

        Button savbtn = new Button("Visual Report");
        ImageView savingImageView = new ImageView(savingImage);
        setupButtonWithImage(savbtn, savingImageView);
        savbtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // PieChart1 pieChart1 = new PieChart1();
                // System.out.println(FirebaseInit.amount);
                // try {
                // pieChart1.start(prStage);
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
                piePageScene();

            }
        });

        // Layout
        HBox homehb = new HBox(50, catbtn, trabtn, addExpbtn, setTarbtn, savbtn);
        homehb.setAlignment(Pos.BOTTOM_CENTER);

        Label tranlbel = new Label("EXPENSE MANAGER");
        // tranlbel.setFont(new Font(20));
        // tranlbel.setTranslateX(100);
        // tranlbel.setTranslateY(00);
        // tranlbel.setAlignment(Pos.TOP_CENTER);

        tranlbel.setStyle("-fx-text-fill: BLACK; -fx-font-weight: bold; -fx-font-width:30; -fx-font-size:40");

        VBox root = new VBox(0, homehb, tranlbel);
        root.setAlignment(Pos.CENTER);

        root.setSpacing(50);

        // root.setAlignment(Pos.TOP_CENTER);
        // root.setAlignment(Pos.CENTER);
        HBox hb = new HBox(root);
        hb.setAlignment(Pos.CENTER);

        // BackgroundImage myBI = new BackgroundImage(backgroundImage,
        // BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        // BackgroundPosition.CENTER,
        // new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false,
        // true, false));

        // Set background image

        BackgroundImage myBI = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        // root.setBackground(new Background(myBI));
        // hb.setBackground(new Background(myBI));

        StackPane sp1 = new StackPane();
        sp1.getChildren().addAll(backgrImageView, hb);

        return sp1;

        // Scene homescene = new Scene(sp1);

        // homescene.setFill(Color.BLACK);
        // homScene = homescene;
        // prStage.setScene(homescene);
        // this.prStage = prStage;
        // prStage.show();
    }

    private void mainPageScene() {
        MainPage mainpage = new MainPage();
        mainpage.setStage(prStage);
        mainpageScene = new Scene(mainpage.mainPageScene(this::handleback), 1200, 1000);
        mainpage.setScene(mainpageScene);
        prStage.setScene(mainpageScene);
        prStage.setTitle("EXPENSE MANAGER");
        // prStage.show();
    }

    private void piePageScene() {
        PieChart1 pieChart1 = new PieChart1();
        pieChart1.setStage(prStage);
        piePageScene = new Scene(pieChart1.pieChartScene(this::handleback), 1200, 1000);
        pieChart1.setScene(piePageScene);

        prStage.setScene(piePageScene);
        prStage.setTitle("EXPENSE MANAGER");
        // prStage.show();
    }

    private void categoryPageScene() {
        Categorise categorise = new Categorise();
        categorise.setStage(prStage);
        catPageScene = new Scene(categorise.catPageScene(this::handleback), 1200, 1000);
        categorise.setScene(catPageScene);
        prStage.setScene(catPageScene);
        prStage.setTitle("EXPENSE MANAGER");
        // prStage.show();
    }

    private void transferPageScene() {
        TransferPage transfer = new TransferPage();
        transfer.setStage(prStage);
        transferPageScene = new Scene(transfer.transferPageScene(this::handleback), 1200, 1000);
        transfer.setScene(transferPageScene);
        prStage.setScene(transferPageScene);
        prStage.setTitle("EXPENSE MANAGER");
        // prStage.show();
    }

    private void setTargetPageScene() {
        SetTarget setTarget = new SetTarget();
        setTarget.setStage(prStage);
        setTargetPageScene = new Scene(setTarget.setTargetScene(this::handleback), 1200, 1000);
        setTarget.setScene(setTargetPageScene);
        prStage.setScene(setTargetPageScene);
        prStage.setTitle("EXPENSE MANAGER");
        // prStage.show();
    }

    public void handleback() {
        prStage.setScene(homepageScene);
    }

    private void setupButtonWithImage(Button button, ImageView imageView) {
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        button.setFont(new Font(20));
        button.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        button.setGraphic(imageView);
        button.setPrefWidth(200); // Set preferred width
        button.setPrefHeight(200); // Set preferred height
        button.setStyle(
                "-fx-background-color:#2C3E50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10;");
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: BLACK; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10;"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #2C3E50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10;"));
    }

}
