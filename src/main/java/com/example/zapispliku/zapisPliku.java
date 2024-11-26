package com.example.zapispliku;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class zapisPliku extends Application {

    private TextField filePathField;
    private TextArea fileContentArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label filePathLabel = new Label("Ścieżka pliku:");
        filePathField = new TextField();
        Button readButton = new Button("Odczyt");

        HBox filePathBox = new HBox(10, filePathField, readButton);
        filePathBox.setPadding(new Insets(5));
        filePathBox.setHgrow(filePathField, Priority.ALWAYS);

        fileContentArea = new TextArea();
        fileContentArea.setPrefHeight(400);

        Button saveButton = new Button("Zapis");
        Button exitButton = new Button("Wyłącz program");

        HBox buttonsBox = new HBox(10, saveButton, exitButton);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-alignment: center;");

        readButton.setOnAction(event -> readFile());
        saveButton.setOnAction(event -> saveFile());
        exitButton.setOnAction(event -> primaryStage.close());

        VBox root = new VBox(10, filePathLabel, filePathBox, fileContentArea, buttonsBox);
        root.setStyle("-fx-padding: 10; -fx-alignment: top-center;");

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setTitle("Edytor plików");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void readFile() {
        String filePath = filePathField.getText();
        if (filePath.isEmpty()) {
            showAlert("Błąd", "Podaj ścieżkę do pliku.");
            return;
        }

        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            fileContentArea.setText(String.join("\n", lines));
        } catch (IOException e) {
            showAlert("Błąd", "Nie można odczytać pliku: " + e.getMessage());
        }
    }

    private void saveFile() {
        String filePath = filePathField.getText();
        if (filePath.isEmpty()) {
            showAlert("Błąd", "Podaj ścieżkę do pliku.");
            return;
        }

        try {
            Path path = Paths.get(filePath);
            Files.write(path, fileContentArea.getText().getBytes());
            showAlert("Sukces", "Dane zapisano do pliku.");
        } catch (IOException e) {
            showAlert("Błąd", "Nie można zapisać pliku: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
