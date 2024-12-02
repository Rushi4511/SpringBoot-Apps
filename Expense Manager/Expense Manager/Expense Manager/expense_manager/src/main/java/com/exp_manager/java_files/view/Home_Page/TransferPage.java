package com.exp_manager.java_files.view.Home_Page;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TransferPage {
    private Stage prStage;
    private final ObservableList<String> friendsList = FXCollections.observableArrayList();
    private final ObservableList<String> groupsList = FXCollections.observableArrayList();
    private Scene TransferScene;

    private Runnable backHandler;

    public void setScene(Scene scene) {
        this.TransferScene = scene;
    }

    public void setStage(Stage prStage) {
        this.prStage = prStage;
    }

    public SplitPane transferPageScene(Runnable backHandler) {

        this.backHandler = backHandler;

        prStage.setTitle("Money Manager");

        // Split Pane
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.5);

        // Friends Pane
        VBox friendsPane = createFriendsPane();
        friendsPane.setStyle("-fx-background-color: #f0fff0;");

        // Groups Pane
        VBox groupsPane = createGroupPane();
        groupsPane.setStyle("-fx-background-color: #f0fff0;");
        splitPane.getItems().addAll(friendsPane, groupsPane);

        // Scene scene = new Scene(splitPane, 1000, 600);
        // // scene.getStylesheets().add("styles.css"); // Add external CSS
        // prStage.setScene(scene);
        // prStage.show();

        // Apply animations

        applyFadeTransition(friendsPane);
        applyFadeTransition(groupsPane);

        return splitPane;
    }

    private VBox createFriendsPane() {
        VBox friendsPane = new VBox(10);
        friendsPane.setPadding(new Insets(20));
        friendsPane.setAlignment(Pos.CENTER);
        friendsPane.getStyleClass().add("pane");

        Label header = new Label("Manage Friends");
        header.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");
        header.getStyleClass().add("header");

        ListView<String> friendsListView = new ListView<>(friendsList);
        friendsListView.setPrefHeight(200);
        friendsListView.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 1;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        TextField friendNameField = new TextField();
        friendNameField.setPromptText("Friend's Name");
        friendNameField.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        TextField friendUsernameField = new TextField();
        friendUsernameField.setPromptText("Friend's Username");
        friendUsernameField.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        Button addFriendButton = new Button("Add Friend");
        applyButtonStyle(addFriendButton);
        addFriendButton.setOnAction(e -> {
            String friendName = friendNameField.getText();
            String friendUsername = friendUsernameField.getText();
            if (!friendName.isEmpty() && !friendUsername.isEmpty()) {
                friendsList.add(friendName + " (" + friendUsername + ")");
                friendNameField.clear();
                friendUsernameField.clear();
            }
        });

        Button removeFriendButton = new Button("Remove Friend");
        applyButtonStyle(removeFriendButton);
        removeFriendButton.setOnAction(e -> {
            String selectedFriend = friendsListView.getSelectionModel().getSelectedItem();
            if (selectedFriend != null) {
                friendsList.remove(selectedFriend);
            }
        });

        friendsListView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                String selectedFriend = friendsListView.getSelectionModel().getSelectedItem();
                if (selectedFriend != null) {
                    showFriendDetails(selectedFriend);
                }
            }
        });

        friendsPane.getChildren().addAll(header, friendsListView, friendNameField, friendUsernameField, addFriendButton,
                removeFriendButton);
        return friendsPane;
    }

    private VBox createGroupPane() {
        VBox groupPane = new VBox(10);
        groupPane.setPadding(new Insets(20));
        groupPane.setAlignment(Pos.CENTER);
        groupPane.getStyleClass().add("pane");

        Label header = new Label("Manage Groups");
        header.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");
        header.getStyleClass().add("header");

        ListView<String> groupsListView = new ListView<>(groupsList);
        groupsListView.setPrefHeight(200);
        groupsListView.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        TextField groupNameField = new TextField();
        groupNameField.setPromptText("Group Name");
        groupNameField.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        Button createGroupButton = new Button("Create Group");
        applyButtonStyle(createGroupButton);
        createGroupButton.setOnAction(e -> {
            String groupName = groupNameField.getText();
            if (!groupName.isEmpty()) {
                groupsList.add(groupName);
                groupNameField.clear();
            }
        });

        groupsListView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                String selectedGroup = groupsListView.getSelectionModel().getSelectedItem();
                if (selectedGroup != null) {
                    showGroupDetails(selectedGroup);
                }
            }
        });

        Button backButton = new Button("Back");
        applyButtonStyle(backButton);
        backButton.setOnAction(e -> backHandler.run());

        VBox vb1 = new VBox(backButton);

        groupPane.getChildren().addAll(header, groupsListView, groupNameField, createGroupButton, vb1);
        return groupPane;
    }

    private void showFriendDetails(String friend) {
        Stage stage = new Stage();
        stage.setTitle("Friend Details - " + friend);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("pane");

        Label header = new Label("Manage Transactions with " + friend);
        header.getStyleClass().add("header");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");
        amountField.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        TextField reasonField = new TextField();
        reasonField.setPromptText("Reason");
        reasonField.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        Button sendButton = new Button("Send Money");
        applyButtonStyle(sendButton);
        sendButton.setOnAction(e -> {
            String amount = amountField.getText();
            String reason = reasonField.getText();
            if (!amount.isEmpty()) {
                // Logic to send money
                showAlert(Alert.AlertType.INFORMATION, "Send Money", "Money sent to " + friend + " for " + reason);
                amountField.clear();
                reasonField.clear();
            }
        });

        Button requestButton = new Button("Request Money");
        applyButtonStyle(requestButton);
        requestButton.setOnAction(e -> {
            String amount = amountField.getText();
            String reason = reasonField.getText();
            if (!amount.isEmpty()) {
                // Logic to request money
                showAlert(Alert.AlertType.INFORMATION, "Request Money",
                        "Money requested from " + friend + " for " + reason);
                amountField.clear();
                reasonField.clear();
            }
        });

        ListView<String> transactionHistory = new ListView<>();
        transactionHistory.setPrefHeight(200);
        transactionHistory.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");
        transactionHistory.getItems().addAll(
                "Sent: ₹1000 for Dinner",
                "Requested: ₹500 for Movie Tickets"); // Placeholder items

        vbox.getChildren().addAll(header, amountField, reasonField, sendButton, requestButton, transactionHistory);

        Scene scene = new Scene(vbox, 400, 500);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.show();
    }

    private void showGroupDetails(String group) {
        Stage stage = new Stage();
        stage.setTitle("Group Details - " + group);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("pane");

        Label header = new Label("Manage Group - " + group);
        header.getStyleClass().add("header");

        ListView<String> groupMembersList = new ListView<>();
        groupMembersList.setPrefHeight(200);
        groupMembersList.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        ComboBox<String> friendsComboBox = new ComboBox<>(friendsList);
        friendsComboBox.setPromptText("Add Friend to Group");

        Button addMemberButton = new Button("Add Member");
        applyButtonStyle(addMemberButton);
        addMemberButton.setOnAction(e -> {
            String selectedFriend = friendsComboBox.getValue();
            if (selectedFriend != null && !groupMembersList.getItems().contains(selectedFriend)) {
                groupMembersList.getItems().add(selectedFriend);
            }
        });

        TextField amountField = new TextField();
        amountField.setPromptText("Total Amount");
        amountField.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        TextField reasonField = new TextField();
        reasonField.setPromptText("Reason");
        reasonField.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        ToggleGroup splitToggleGroup = new ToggleGroup();
        RadioButton equalSplitButton = new RadioButton("Equal Split");
        equalSplitButton.setToggleGroup(splitToggleGroup);
        equalSplitButton.setSelected(true);

        RadioButton customSplitButton = new RadioButton("Custom Split");
        customSplitButton.setToggleGroup(splitToggleGroup);

        Button requestGroupMoneyButton = new Button("Request Money from Group");
        applyButtonStyle(requestGroupMoneyButton);
        requestGroupMoneyButton.setOnAction(e -> {
            String amount = amountField.getText();
            String reason = reasonField.getText();
            if (!amount.isEmpty()) {
                if (equalSplitButton.isSelected()) {
                    // Logic for equal split
                    double totalAmount = Double.parseDouble(amount);
                    int numMembers = groupMembersList.getItems().size();
                    double perPersonAmount = totalAmount / numMembers;
                    StringBuilder message = new StringBuilder("Equal Split:\n");
                    for (String member : groupMembersList.getItems()) {
                        message.append(member).append(": ₹").append(String.format("%.2f", perPersonAmount))
                                .append("\n");
                    }
                    showAlert(Alert.AlertType.INFORMATION, "Equal Split", message.toString());
                } else {
                    // Open custom split window
                    openCustomSplitWindow(groupMembersList.getItems(), amount, reason);

                }
                amountField.clear();
                reasonField.clear();

            }
        });

        vbox.getChildren().addAll(header, friendsComboBox, addMemberButton, groupMembersList, amountField, reasonField,
                equalSplitButton, customSplitButton, requestGroupMoneyButton);

        Scene transfscene = new Scene(vbox, 500, 600);

        stage.setScene(transfscene);
        stage.show();

    }

    private void openCustomSplitWindow(ObservableList<String> members, String totalAmount, String reason) {
        Stage customSplitStage = new Stage();
        customSplitStage.setTitle("Custom Split");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("pane");

        Label header = new Label("Custom Split for: " + reason);
        header.getStyleClass().add("header");

        ListView<HBox> customSplitList = new ListView<>();
        customSplitList.setPrefHeight(200);
        customSplitList.setStyle("-fx-border-color: black;\n" + //
                "    -fx-border-width: 2;\n" + //
                "    -fx-border-radius: 5;\n" + //
                "    -fx-padding: 5;");

        for (String member : members) {
            TextField amountField = new TextField();
            amountField.setPromptText("Amount for " + member);
            amountField.setStyle("-fx-border-color: black;\n" + //
                    "    -fx-border-width: 2;\n" + //
                    "    -fx-border-radius: 5;\n" + //
                    "    -fx-padding: 5;");
            HBox hbox = new HBox(10, new Label(member), amountField);
            hbox.setAlignment(Pos.CENTER_LEFT);
            customSplitList.getItems().add(hbox);
        }

        Button confirmButton = new Button("Confirm Split");
        applyButtonStyle(confirmButton);
        confirmButton.setOnAction(e -> {
            StringBuilder message = new StringBuilder("Custom Split:\n");
            for (HBox hbox : customSplitList.getItems()) {
                String member = ((Label) hbox.getChildren().get(0)).getText();
                String amount = ((TextField) hbox.getChildren().get(1)).getText();
                message.append(member).append(": ₹").append(amount).append("\n");
            }
            showAlert(Alert.AlertType.INFORMATION, "Custom Split", message.toString());
            customSplitStage.close();
        });

        vbox.getChildren().addAll(header, customSplitList, confirmButton);

        Scene scene = new Scene(vbox, 400, 500);

        customSplitStage.setScene(scene);
        customSplitStage.show();

    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void applyFadeTransition(Pane pane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), pane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void applyButtonStyle(Button button) {
        String buttonStyle = "-fx-background-color: BLACK; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; "
                + "-fx-border-radius: 15; -fx-background-radius: 15; "
                + "-fx-padding: 10 20 10 20;";

        button.setStyle(buttonStyle);
    }
}