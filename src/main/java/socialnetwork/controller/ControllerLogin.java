package socialnetwork.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import socialnetwork.domain.Account;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.IOException;

public class ControllerLogin {
    UtilizatorService utilizatorService;
    Account signed_in_account = null;
    Stage sign_inStage;
    @FXML
    Button sign_in_button;

    @FXML
    TextField username_field;

    @FXML
    PasswordField password_field;

    @FXML
    AnchorPane loginPane;

    @FXML
    AnchorPane registerPane;

    @FXML
    TextField username_registerField;

    @FXML
    PasswordField password_registerField;

    @FXML
    TextField first_nameField;

    @FXML
    TextField last_nameField;

    @FXML
    private void initialize() {
    }

    public void setService(UtilizatorService utilizatorService,Stage sign_inStage) {
        this.utilizatorService = utilizatorService;
        this.sign_inStage = sign_inStage;
    }

    @FXML
    public void handleSignIn(){
        String username = username_field.getText();
        String password = password_field.getText();
        if(username != null && password != null) {
            signed_in_account = utilizatorService.signin(username, password);
            if (signed_in_account == null) {
                //TO DO: mesaj dragut de eroare
                Alert message = new Alert(Alert.AlertType.ERROR);
                message.setTitle("Mesaj eroare");
                message.showAndWait();
            } else {
                try {
                    FXMLLoader utilLoader = new FXMLLoader();
                    utilLoader.setLocation(getClass().getResource("/views/userView.fxml"));
                    ControllerUtilizator controllerUtilizator = new ControllerUtilizator();
                    controllerUtilizator.setService(utilizatorService, signed_in_account.getId_user());
                    utilLoader.setController(controllerUtilizator);
                    AnchorPane userLayout = utilLoader.load();
                    Scene scene = new Scene(userLayout);
                    scene.getStylesheets().add("/CSS/listviewStyle.css");
                    sign_inStage.setScene(scene);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
    @FXML
    public void handleRegister() {
        loginPane.setDisable(true);
        registerPane.setLayoutY(650);
        registerPane.setDisable(false);
        registerPane.setVisible(true);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue kvloginPane = new KeyValue(loginPane.layoutYProperty(), 650);
        KeyFrame kfloginPane = new KeyFrame(Duration.millis(500), kvloginPane);
        KeyValue kvregisterPane = new KeyValue(registerPane.layoutYProperty(), 0);
        KeyFrame kfregisterPane = new KeyFrame(Duration.millis(500), kvregisterPane);
        timeline.getKeyFrames().add(kfloginPane);
        timeline.getKeyFrames().add(kfregisterPane);
        timeline.play();
    }

    @FXML
    public void handleBack(){

        loginPane.setDisable(false);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue kvloginPane = new KeyValue(loginPane.layoutYProperty(), 0);
        KeyFrame kfloginPane = new KeyFrame(Duration.millis(500), kvloginPane);
        KeyValue kvregisterPane = new KeyValue(registerPane.layoutYProperty(), 650);
        KeyFrame kfregisterPane = new KeyFrame(Duration.millis(500), kvregisterPane);
        timeline.getKeyFrames().add(kfloginPane);
        timeline.getKeyFrames().add(kfregisterPane);
        timeline.play();
        registerPane.setDisable(true);

    }

    @FXML
    public void handleRegisterUser() {
        String username = username_registerField.getText();
        String password = password_registerField.getText();
        String first_name = first_nameField.getText();
        String last_name = last_nameField.getText();
        if (username != null && password != null && first_name != null && last_name != null) {
            if(utilizatorService.register(username, password, first_name, last_name) == null){
                loginPane.setDisable(false);
                Timeline timeline = new Timeline();
                timeline.setCycleCount(1);
                KeyValue kvloginPane = new KeyValue(loginPane.layoutYProperty(), 0);
                KeyFrame kfloginPane = new KeyFrame(Duration.millis(500), kvloginPane);
                KeyValue kvregisterPane = new KeyValue(registerPane.layoutYProperty(), 650);
                KeyFrame kfregisterPane = new KeyFrame(Duration.millis(500), kvregisterPane);
                timeline.getKeyFrames().add(kfloginPane);
                timeline.getKeyFrames().add(kfregisterPane);
                timeline.play();
                registerPane.setDisable(true);
            }

        }
    }
}
