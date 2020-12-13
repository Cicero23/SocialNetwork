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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.io.FileInputStream;
import java.io.IOException;


public class ControllerUtilizator extends Observer {
    Long id_sign_inUtil;

    UtilizatorService utilizatorService;
    ObservableList<Utilizator> listUsers = FXCollections.observableArrayList();
    ObservableList<PrietenDTO> listFriends = FXCollections.observableArrayList();
    ObservableList<Invitatie> listRequests = FXCollections.observableArrayList();
    ObservableList<Pane> listUsersBox = FXCollections.observableArrayList();
    ObservableList<Pane> listFriendsBox = FXCollections.observableArrayList();
    ObservableList<Pane> listRequestsBox = FXCollections.observableArrayList();
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
    Label label_current_tab;


    @FXML
    private void initialize() {

        //utilizatorService.getAll().forEach(x->listUsers.add(x));
       // utilizatorService.getPrieteni(id_sign_inUtil).forEach(x->listFriends.add(x));
        //utilizatorService.getInvitatiiPrimite(id_sign_inUtil).forEach(x->listRequests.add(x));
        initModel();
        Utilizator x = utilizatorService.getOne(id_sign_inUtil);
        first_name_text.setText(x.getFirstName());
        last_name_text.setText(x.getLastName());

    }

    private void initModel(){
        loadUsers();
        loadFriends();
        loadRequests();
    }

    private void loadUsers(){
        listUsersBox.clear();
        utilizatorService.getAll().forEach(x->{
            if(x.getId()!= id_sign_inUtil) {
                Pane util = new Pane();

                //imaginea
                ImageView imgV =  new ImageView();
                Image image = new Image("others/user52x50.png");
                imgV.setImage(image);
                imgV.setLayoutX(6);
                imgV.setLayoutY(15);
                util.getChildren().add(imgV);

                //numele utilizator
                Label textLabel = new Label(x.getFirstName() + " " + x.getLastName());
                textLabel.getStyleClass().add("textUser-list");
                textLabel.setLayoutX(62);
                textLabel.setLayoutY(14);
                util.getChildren().add(textLabel);

                //btn chat
                Button btnChat = new Button();
                btnChat.getStyleClass().add("btnChatUser-list");
                btnChat.setLayoutX(460);
                btnChat.setLayoutY(14);
                btnChat.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            FXMLLoader chatLoader = new FXMLLoader();
                            chatLoader.setLocation(getClass().getResource("/views/chatView.fxml"));
                            ControllerChat controllerChat = new ControllerChat();
                            controllerChat.setService(utilizatorService,id_sign_inUtil,x.getId());
                            chatLoader.setController(controllerChat);
                            Stage stage = new Stage();
                            AnchorPane chatLayout = chatLoader.load();

                            Scene scene =new Scene(chatLayout);
                            stage.setScene(scene);
                            stage.show();

                        }
                        catch (IOException e){
                            System.out.println("ceva");
                            e.printStackTrace();
                        }
                    }
                });

                util.getChildren().add(btnChat);

                //btn add prieten
                if ((utilizatorService.getPrietenie(new Tuple<>(id_sign_inUtil, x.getId())) == null && utilizatorService.getPrietenie(new Tuple<>(x.getId(), id_sign_inUtil)) == null && !utilizatorService.isApendingRequest(x.getId(),id_sign_inUtil))) {
                    Button btnFriend = new Button();
                    btnFriend.getStyleClass().add("btnFriendUser-list");
                    btnFriend.setLayoutX(399);
                    btnFriend.setLayoutY(12);
                    btnFriend.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            utilizatorService.sendInvidatie(id_sign_inUtil,x.getId());
                            btnFriend.setVisible(false);
                        }
                    });
                    util.getChildren().add(btnFriend);
                }

                listUsersBox.add(util);
            }
        });users_listView.getItems().clear();
        users_listView.getItems().setAll(listUsersBox);
    }

    private void loadRequests(){
        listRequestsBox.clear();
        utilizatorService.getInvitatiiPrimite(id_sign_inUtil).forEach(x->{
            Utilizator utilizator =utilizatorService.getOne(x.getId_from());
            Pane  util = new Pane();
            //imaginea
            ImageView imgV =  new ImageView();
            Image image = new Image("others/chain.png");
            imgV.setImage(image);
            imgV.setFitHeight(40);
            imgV.setFitWidth(41);
            imgV.setLayoutX(14);
            imgV.setLayoutY(20);
            util.getChildren().add(imgV);


            //nume utilizator
            Label textLabel = new Label(utilizator.getFirstName() + " " + utilizator.getLastName());
            textLabel.getStyleClass().add("textRequest-list");
            textLabel.setLayoutX(70);
            textLabel.setLayoutY(0);
            util.getChildren().add(textLabel);

            //data utilizator
            Label textStare = new Label();
            textStare.getStyleClass().add("textTypeRequest-list");
            textStare.setLayoutX(70);
            textStare.setLayoutY(30);
            if(x.getStare() == 1){
                textStare.setText("Pending");

            }
            else if(x.getStare() == 3){
                textStare.setText("Aproved");

            }
            else {
                textStare.setText("Rejected");

            }
            util.getChildren().add(textStare);

            if(x.getStare() == 1){
                Button btnAprove = new Button();
                btnAprove.getStyleClass().add("btnAproveRequest-list");
                btnAprove.setLayoutX(350);
                btnAprove.setLayoutY(14);
                btnAprove.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        utilizatorService.acceptInvitatie(id_sign_inUtil,x.getId());
                    }
                });

                util.getChildren().add(btnAprove);

                Button btnDeny = new Button();
                btnDeny.getStyleClass().add("btnDenyRequest-list");
                btnDeny.setLayoutX(390);
                btnDeny.setLayoutY(14);
                btnDeny.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        utilizatorService.rejectInvitatie(id_sign_inUtil,x.getId());
                    }
                });
                util.getChildren().add(btnDeny);

            }

            Button btnDelete = new Button();
            btnDelete.getStyleClass().add("btnDeleteRequest-list");
            btnDelete.setLayoutX(430);
            btnDelete.setLayoutY(14);
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    utilizatorService.removeInvitatie(x.getId());
                }
            });
            util.getChildren().add(btnDelete);



            listRequestsBox.add(util);
        });

        utilizatorService.getInvitatiiTrimise(id_sign_inUtil).forEach(x->{
            Utilizator utilizator =utilizatorService.getOne(x.getId_to());
            Pane  util = new Pane();
            //imaginea
            ImageView imgV =  new ImageView();
            Image image = new Image("others/chain.png");
            imgV.setImage(image);
            imgV.setFitHeight(40);
            imgV.setFitWidth(41);
            imgV.setLayoutX(14);
            imgV.setLayoutY(20);
            util.getChildren().add(imgV);


            //nume utilizator
            Label textLabel = new Label(utilizator.getFirstName() + " " + utilizator.getLastName());
            textLabel.getStyleClass().add("textRequest-list");
            textLabel.setLayoutX(70);
            textLabel.setLayoutY(0);
            util.getChildren().add(textLabel);

            //data utilizator
            Label textStare = new Label();
            textStare.getStyleClass().add("textTypeRequest-list");
            textStare.setLayoutX(70);
            textStare.setLayoutY(30);
            if(x.getStare() == 1){
                textStare.setText("Pending");

            }
            else if(x.getStare() == 3){
                textStare.setText("Aproved");

            }
            else {
                textStare.setText("Rejected");

            }
            util.getChildren().add(textStare);

            Button btnDelete = new Button();
            btnDelete.getStyleClass().add("btnDeleteRequest-list");
            btnDelete.setLayoutX(430);
            btnDelete.setLayoutY(14);
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    utilizatorService.removeInvitatie(x.getId());
                }
            });
            util.getChildren().add(btnDelete);
            listRequestsBox.add(util);
        });

        requests_listView.getItems().clear();
        requests_listView.getItems().addAll(listRequestsBox);
    }

    private void loadFriends(){
        listFriendsBox.clear();
        utilizatorService.getPrieteni(id_sign_inUtil).forEach(x->{
            Pane util = new Pane();

            //imaginea
            ImageView imgV =  new ImageView();
            Image image = new Image("others/user52x50.png");
            imgV.setImage(image);
            imgV.setLayoutX(6);
            imgV.setLayoutY(15);
            util.getChildren().add(imgV);


            //nume utilizator
            Label textLabel = new Label(x.getNume() + " " + x.getPrenume());
            textLabel.getStyleClass().add("textFriend-list");
            textLabel.setLayoutX(70);
            textLabel.setLayoutY(0);
            util.getChildren().add(textLabel);

            //data utilizator
            Label textData = new Label("since: " + x.getData().toString());
            textData.getStyleClass().add("textDateFriend-list");
            textData.setLayoutX(70);
            textData.setLayoutY(30);
            util.getChildren().add(textData);

            //brn remove friend
            Button btnRemoveFriend = new Button();
            btnRemoveFriend.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    utilizatorService.removePrietenie(x.getId());
                }
            });
            btnRemoveFriend.getStyleClass().add("btnRemoveFriend-list");
            btnRemoveFriend.setLayoutX(448);
            btnRemoveFriend.setLayoutY(20);
            util.getChildren().add(btnRemoveFriend);


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
        label_current_tab.setText("Friends");
    }
    @FXML
    public void moveTousers(){
        friends_layout.setDisable(true);
        friends_layout.setVisible(false);
        requests_layout.setDisable(true);
        requests_layout.setVisible(false);

        users_layout.setDisable(false);
        users_layout.setVisible(true);
        label_current_tab.setText("Users");
    }
    @FXML
    public void moveTorequests(){
        friends_layout.setDisable(true);
        friends_layout.setVisible(false);
        users_layout.setDisable(true);
        users_layout.setVisible(false);

        requests_layout.setDisable(false);
        requests_layout.setVisible(true);
        label_current_tab.setText("Requests");

    }


    @FXML
    public void handleActionHistoryButton(){
        try {
            FXMLLoader actionLoader = new FXMLLoader();
            actionLoader.setLocation(getClass().getResource("/views/actionsView.fxml"));
            ControllerActionHistory controllerActionHistory =  new ControllerActionHistory();
            controllerActionHistory.setService(utilizatorService,id_sign_inUtil);
            actionLoader.setController(controllerActionHistory);
            Stage stage = new Stage();
            AnchorPane actionLayout = actionLoader.load();
            Scene scene =new Scene(actionLayout);

            stage.setScene(scene);

            stage.show();

        }
        catch (IOException e){
            System.out.println("ceva");
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        initModel();
    }
}
