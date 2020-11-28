package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socialnetwork.domain.Account;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;

import java.awt.*;
import java.io.IOException;


public class ControllerUtilizator {
    Long id_sign_inUtil;

    UtilizatorService utilizatorService;

    ObservableList<HBox> listUsers = FXCollections.observableArrayList();
    ObservableList<HBox> listFriends = FXCollections.observableArrayList();
    ObservableList<HBox> listRequests = FXCollections.observableArrayList();
    @FXML
    AnchorPane users_layout;
    @FXML
    ListView users_listView;
    @FXML
    AnchorPane friends_layout;
    @FXML
    ListView friends_listView;
    @FXML
    AnchorPane requests_layout;
    @FXML
    ListView requests_listView;
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
        utilizatorService.getAll().forEach(x->{
            HBox util = new HBox();
            Label textLabel = new Label(x.getFirstName() + " " + x.getLastName());
            textLabel.getStyleClass().add("textLabel-list");
            util.getStyleClass().add("hbox-list");
            util.getChildren().add(textLabel);
            Button btn = new Button("aaaa");
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert message=new Alert(Alert.AlertType.ERROR);
                    message.setTitle("merge");
                    message.showAndWait();
                }
            });
            util.getChildren().add(btn);
            listUsers.add(util);
        });
        users_listView.getItems().setAll(listUsers);

        utilizatorService.getPrieteni(id_sign_inUtil).forEach(x->{
            HBox util = new HBox();
            Label textLabel = new Label(x.getNume() + " " + x.getPrenume());
            Label textData = new Label("since:" + x.getData().toString());
            textLabel.getStyleClass().add("textLabel-list");
            textData.getStyleClass().add("datatextLabel-list");
            util.getStyleClass().add("hbox-list");

            util.getChildren().add(textLabel);
            util.getChildren().add(textData);
            listFriends.add(util);
        });

        friends_listView.getItems().addAll(listFriends);

        utilizatorService.getInvitatiiPrimite(id_sign_inUtil).forEach(x->{
            Utilizator utilizator =utilizatorService.getOne(x.getId_from());
            HBox util = new HBox();
            Label textLabel = new Label(utilizator.getFirstName()+" " +utilizator.getLastName());
            textLabel.getStyleClass().add("textLabel-list");
            util.getStyleClass().add("hbox-list");
            util.getChildren().add(textLabel);
            listRequests.add(util);
        });

        requests_listView.getItems().addAll(listRequests);

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

    @FXML
    public void moveTofriends(){
        users_layout.setDisable(true);
        users_layout.setVisible(false);
        requests_layout.setDisable(true);
        requests_layout.setVisible(false);

        friends_layout.setDisable(false);
        friends_layout.setVisible(true);
    }
    @FXML
    public void moveTousers(){
        friends_layout.setDisable(true);
        friends_layout.setVisible(false);
        requests_layout.setDisable(true);
        requests_layout.setVisible(false);

        users_layout.setDisable(false);
        users_layout.setVisible(true);

    }
    @FXML
    public void moveTorequests(){
        friends_layout.setDisable(true);
        friends_layout.setVisible(false);
        users_layout.setDisable(true);
        users_layout.setVisible(false);

        requests_layout.setDisable(false);
        requests_layout.setVisible(true);

    }















}
