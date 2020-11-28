package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socialnetwork.domain.Account;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.IOException;


public class ControllerUtilizator {
    Long id_sign_inUtil;

    UtilizatorService utilizatorService;

    ObservableList<Button> list = FXCollections.observableArrayList();

    @FXML
    AnchorPane users_layout;
    @FXML
    AnchorPane friends_layout;
    @FXML
    AnchorPane requests_layout;
    @FXML
    Pane users_button;
    @FXML
    Pane friends_button;
    @FXML
    Pane requests_button;
    @FXML
    Text first_name_text;

    @FXML
    Text last_name_text;

    @FXML
    private void initialize() {
        /*utilizatorService.getAll().forEach(x->{
            Button btn =new Button();
            btn.setText(x.getFirstName());
            list.add(btn);
        });*/
        //users_layout.getChildren().setAll(list);
        Utilizator x = utilizatorService.getOne(id_sign_inUtil);
        if(x!= null) {
            first_name_text.setText(x.getFirstName());
            last_name_text.setText(x.getLastName());
        }
    }

    public void setService(UtilizatorService utilizatorService, Long utilizator) {
        this.utilizatorService = utilizatorService;
        this.id_sign_inUtil = utilizator;
    }


}
