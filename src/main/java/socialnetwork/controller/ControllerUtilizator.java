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
import socialnetwork.domain.*;
import socialnetwork.repository.database.PrieteniiDBrepo;
import socialnetwork.service.UtilizatorService;
import socialnetwork.utils.observer.Observer;

import java.awt.*;
import java.io.IOException;


public class ControllerUtilizator extends Observer {
    Long id_sign_inUtil;

    UtilizatorService utilizatorService;
    ObservableList<Utilizator> listUsers = FXCollections.observableArrayList();
    ObservableList<PrietenDTO> listFriends = FXCollections.observableArrayList();
    ObservableList<Invitatie> listRequests = FXCollections.observableArrayList();
    ObservableList<HBox> listUsersBox = FXCollections.observableArrayList();
    ObservableList<HBox> listFriendsBox = FXCollections.observableArrayList();
    ObservableList<HBox> listRequestsBox = FXCollections.observableArrayList();
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

        utilizatorService.getAll().forEach(x->listUsers.add(x));
        utilizatorService.getPrieteni(id_sign_inUtil).forEach(x->listFriends.add(x));
        utilizatorService.getInvitatiiPrimite(id_sign_inUtil).forEach(x->listRequests.add(x));

        listUsers.stream().forEach(x->{
            if(x.getId()!= id_sign_inUtil) {
                HBox util = new HBox();
                Label textLabel = new Label(x.getFirstName() + " " + x.getLastName());
                textLabel.getStyleClass().add("textLabel-list");
                util.getStyleClass().add("hbox-list");
                util.getChildren().add(textLabel);


                if (utilizatorService.getPrietenie(new Tuple<>(id_sign_inUtil, x.getId())) == null && utilizatorService.getPrietenie(new Tuple<>(x.getId(), id_sign_inUtil)) == null) {
                    Button btn = new Button("Send request");
                    btn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            utilizatorService.sendInvidatie(id_sign_inUtil,x.getId());
                            btn.setDisable(true);
                            btn.setVisible(false);
                        }
                    });
                    util.getChildren().add(btn);
                }

                listUsersBox.add(util);
            }
        });
        users_listView.getItems().setAll(listUsersBox);

        initModel();



        Utilizator x = utilizatorService.getOne(id_sign_inUtil);
        first_name_text.setText(x.getFirstName());
        last_name_text.setText(x.getLastName());

    }

    private void initModel(){

        loadFriends();
        loadRequests();
    }

    private void loadUsers(){

    }

    private void loadRequests(){
        listRequestsBox.clear();
        utilizatorService.getInvitatiiPrimite(id_sign_inUtil).forEach(x->{
            Utilizator utilizator =utilizatorService.getOne(x.getId_from());
            HBox util = new HBox();
            Label textLabel = new Label(utilizator.getFirstName()+" " +utilizator.getLastName());
            textLabel.getStyleClass().add("textLabel-list");
            util.getStyleClass().add("hbox-list");
            util.getChildren().add(textLabel);
            listRequestsBox.add(util);
        });
        requests_listView.getItems().clear();
        requests_listView.getItems().addAll(listRequestsBox);
    }

    private void loadFriends(){
        listFriendsBox.clear();
        utilizatorService.getPrieteni(id_sign_inUtil).forEach(x->{
            HBox util = new HBox();
            Label textLabel = new Label(x.getNume() + " " + x.getPrenume());
            Label textData = new Label("since:" + x.getData().toString());
            textLabel.getStyleClass().add("textLabel-list");
            textData.getStyleClass().add("datatextLabel-list");
            util.getStyleClass().add("hbox-list");

            util.getChildren().add(textLabel);
            util.getChildren().add(textData);
            listFriendsBox.add(util);
        });
        friends_listView.getItems().clear();
        friends_listView.getItems().addAll(listFriendsBox);
    }




    public void setService(UtilizatorService utilizatorService, Long utilizator) {
        this.utilizatorService = utilizatorService;
        this.id_sign_inUtil = utilizator;
        utilizatorService.addObserver(this);
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
        loadRequests();

        requests_layout.setDisable(false);
        requests_layout.setVisible(true);


    }


    @Override
    public void update() {
        initModel();
    }
}
