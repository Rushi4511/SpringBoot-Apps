package com.exp_manager.java_files.view.Home_Page;

import com.exp_manager.java_files.control.FirebaseInit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginPage extends Application {
    private Stage prStage;
    private Scene loginScene, registerScene, homeScene, homepageScene;

    @Override
    public void start(Stage primaryStage) {
        this.prStage = primaryStage; // Initialize prStage here

        prStage.setHeight(1000);
        prStage.setWidth(2000);
        prStage.setResizable(true);
        prStage.setFullScreen(true);

        Image backgroundImage = new Image("images/background.jpg"); // Update the path to your background image file
        ImageView backgrImageView = new ImageView(backgroundImage);
        backgrImageView.setFitHeight(1000);
        backgrImageView.setFitWidth(2500);
        backgrImageView.setPreserveRatio(false);

        // Create home scene
        HomePage homePage = new HomePage();
        homeScene = null; // Placeholder, assuming you'll set it up elsewhere

        // Create login scene
        loginScene = createLoginScene();

        // Show login scene
        prStage.setTitle("EXPENSE MANAGER");
        prStage.setScene(loginScene);
        prStage.setHeight(1000);
        prStage.setWidth(1200);
        prStage.setResizable(true);
        prStage.show();
        prStage.getIcons().add(new Image("images/savings.png"));
    }

    Scene createLoginScene() {
        GridPane grid = createGridPane();
        Image backgroundImage = new Image("images/background.jpg");
        ImageView backgrImageView = new ImageView(backgroundImage);
        backgrImageView.setFitHeight(1000);
        backgrImageView.setFitWidth(2500);
        backgrImageView.setPreserveRatio(false);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPrefWidth(250); // Change the width
        emailField.setPrefHeight(40);
        emailField.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.8);-fx-text-fill: black;-fx-font-size: 14px;-fx-padding: 10px;-fx-border-radius: 5;-fx-background-radius: 5;");
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.8);-fx-text-fill: black;-fx-font-size: 14px;-fx-padding: 10px;-fx-border-radius: 5;-fx-background-radius: 5;");

        Button loginButton = new Button("Login");
        applyButtonStyle(loginButton);

        Button registerButton = new Button("Register");
        applyButtonStyle(registerButton);

        loginButton.setOnAction(e -> handleLogin(emailField.getText(), passwordField.getText()));
        registerButton.setOnAction(e -> prStage.setScene(createRegisterScene()));

        GridPane.setMargin(emailField, new Insets(10, 0, 10, 0)); // Add vertical spacing
        GridPane.setMargin(passwordField, new Insets(10, 0, 10, 0)); // Add vertical spacing

        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        HBox buttonBox = new HBox(10, loginButton, registerButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 1, 2);

        StackPane sp1 = new StackPane();
        sp1.getChildren().addAll(backgrImageView, grid);

        return new Scene(sp1, 300, 200);
    }

    void applyButtonStyle(Button button) {
        String buttonStyle = "-fx-background-color: BLACK; -fx-text-fill: white; -fx-font-size: 8px; -fx-font-weight: bold; "
                + "-fx-border-radius: 15; -fx-background-radius: 15; "
                + "-fx-padding: 10 20 10 20;";

        button.setStyle(buttonStyle);
    }

    private Scene createRegisterScene() {
        GridPane grid = createGridPane();

        Image backgroundImage = new Image("images/background.jpg");
        ImageView backgrImageView = new ImageView(backgroundImage);
        backgrImageView.setFitHeight(1000);
        backgrImageView.setFitWidth(2500);
        backgrImageView.setPreserveRatio(false);

        Label emailLabel = new Label("Email:");

        TextField emailField = new TextField();
        emailField.setPrefWidth(250); // Change the width
        emailField.setPrefHeight(40);
        emailField.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.8);-fx-text-fill: black;-fx-font-size: 14px;-fx-padding: 10px;-fx-border-radius: 5;-fx-background-radius: 5;");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(250); // Change the width
        passwordField.setPrefHeight(40);
        passwordField.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.8);-fx-text-fill: black;-fx-font-size: 14px;-fx-padding: 10px;-fx-border-radius: 5;-fx-background-radius: 5;");

        Label confirmPasswordLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPrefWidth(250); // Change the width
        confirmPasswordField.setPrefHeight(40);
        confirmPasswordField.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.8);-fx-text-fill: black;-fx-font-size: 14px;-fx-padding: 10px;-fx-border-radius: 5;-fx-background-radius: 5;");

        Button registerButton = new Button("Register");
        applyButtonStyle(registerButton);
        Button backButton = new Button("Back");
        applyButtonStyle(backButton);

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                    if (FirebaseInit.signUp(emailField.getText(), passwordField.getText())) {
                        System.out.println("Done!");
                        prStage.setScene(loginScene);
                    }
                }
            }
        });
        backButton.setOnAction(e -> prStage.setScene(loginScene));

        GridPane.setMargin(emailField, new Insets(10, 0, 10, 0)); // Add vertical spacing
        GridPane.setMargin(passwordField, new Insets(10, 0, 10, 0));
        GridPane.setMargin(confirmPasswordField, new Insets(10, 0, 10, 0));

        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(confirmPasswordLabel, 0, 2);
        grid.add(confirmPasswordField, 1, 2);

        HBox buttonBox = new HBox(10, registerButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 1, 3);

        StackPane sp2 = new StackPane();
        sp2.getChildren().addAll(backgrImageView, grid);

        return new Scene(sp2, 300, 250);
    }

    private void homePageScene() {
        HomePage homePage = new HomePage();
        homePage.setStage(prStage);
        homepageScene = new Scene(homePage.hPageScene(this::handleback), 2000, 1000);
        homePage.setScene(homepageScene);
        prStage.setScene(homepageScene);
        prStage.setTitle("EXPENSE MANAGER");

    }

    public void handleback() {
        prStage.setScene(homeScene);
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        return grid;
    }

    private void handleLogin(String email, String password) {
        if (FirebaseInit.login(email, password)) {
            homePageScene(); // Correctly switch to homePageScene on successful login
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

}
