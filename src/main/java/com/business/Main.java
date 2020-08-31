package com.business;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.Arrays.asList;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.TOP_CENTER;

public class Main extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        UserSavedInfoService userInfoToFileSaver = new UserSavedInfoService();
        PhoneAddressValidator phoneAddressValidator = new PhoneAddressValidator();
        Label phoneInterfaceAddressInputLabel = getPhoneInterfaceAddressInputLabel();
        TextField phoneInterfaceAddressInput = new TextField();
        Label musicDownloadedPathLabel = getMusicDownloadedFolder();
        TextField musicDownloadedPathInput = new TextField();

        Label successInputLabel = getSuccessInputLabel();
        Button doneButton = getPhoneInterfaceAddressButton();

        Label tempLabel = new Label();

        doneButton.setOnAction(action -> {
            if (!phoneAddressValidator.validate(phoneInterfaceAddressInput.getText())){
                successInputLabel.setTextFill(Color.web("#ff0000", 1.0));
                successInputLabel.setText("Неправильно введён адрес интерфейса Вашего телефона");
                return;
            }
            userInfoToFileSaver.save(phoneInterfaceAddressInput.getText());
            phoneInterfaceAddressInput.setEditable(false);

            // попробавать передать сцену внутрь и там лейбл менять
            new ThreadProxyFolderUpdateListener(phoneInterfaceAddressInput.getText(), musicDownloadedPathInput.getText()).start();

            successInputLabel.setTextFill(Color.web("#008000", 1.0));
            successInputLabel.setText("TURNED ON");
        });



        List<Node> primaryStageChildren = asList(
                phoneInterfaceAddressInputLabel,
                phoneInterfaceAddressInput,
                musicDownloadedPathLabel,
                musicDownloadedPathInput,
                successInputLabel,
                doneButton,
                tempLabel
        );

        start(primaryStageChildren, primaryStage);
    }

    private void start(List<Node> primaryStageChildren, Stage primaryStage) {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(primaryStageChildren);
        startWithDefaults(primaryStage, vBox);
    }

    private void startWithDefaults(Stage primaryStage, VBox vBox) {
        primaryStage.setTitle("Download music to your IPhone!");
        Scene scene = new Scene(vBox, 370, 250);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    private Label getSuccessInputLabel() {
        Label successInputLabel = new Label();
        successInputLabel.setMinHeight(50.0);
        successInputLabel.setMinWidth(50.0);
        return successInputLabel;
    }

    private Button getPhoneInterfaceAddressButton() {
        Button phoneInterfaceAddressButton = new Button();
        phoneInterfaceAddressButton.setText("DONE");
        return phoneInterfaceAddressButton;
    }

    private Label getPhoneInterfaceAddressInputLabel() {
        Label label = new Label();
        label.setText("Введите адрес с экрана вашего IPhone:");
        return label;
    }

    private Label getMusicDownloadedFolder() {
        Label label = new Label();
        label.setText("Путь до папки, куда загружается музыка формата .mp3:");
        return label;
    }
}
