package socialnetwork.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import socialnetwork.domain.Account;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.awt.*;

public class ControllerLogin {
    UtilizatorService utilizatorService;
    Account signed_in_account = null;
    @FXML
    Button sign_in_button;

    @FXML
    TextField username_field;

    @FXML
    PasswordField password_field;



    @FXML
    private void initialize() {
    }

    public void setService(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }

    @FXML
    public void handleSignIn(){
        String username = username_field.getText();
        String password = password_field.getText();
        signed_in_account = utilizatorService.signin(username,password);
        if(signed_in_account==null){
            Alert message=new Alert(Alert.AlertType.ERROR);
            message.setTitle("Mesaj eroare");
            message.showAndWait();
        }
        else {
            Alert message=new Alert(Alert.AlertType.ERROR);
            message.setTitle(signed_in_account.getUsername());
            message.showAndWait();
        }

    }

}
