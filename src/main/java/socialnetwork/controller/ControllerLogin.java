package socialnetwork.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
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
        signed_in_account = utilizatorService.signin(username,password);
        if(signed_in_account==null){
            //TO DO: mesaj dragut de eroare
            Alert message=new Alert(Alert.AlertType.ERROR);
            message.setTitle("Mesaj eroare");
            message.showAndWait();
        }
        else {
            try {
                FXMLLoader utilLoader = new FXMLLoader();
                utilLoader.setLocation(getClass().getResource("/views/userView.fxml"));
                ControllerUtilizator controllerUtilizator = new ControllerUtilizator();
                controllerUtilizator.setService(utilizatorService,signed_in_account.getId_user());
                utilLoader.setController(controllerUtilizator);
                AnchorPane userLayout = utilLoader.load();
                sign_inStage.setScene(new Scene(userLayout));


            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
