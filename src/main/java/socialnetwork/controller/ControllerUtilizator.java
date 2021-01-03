package socialnetwork.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.control.TextArea;
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
import javafx.util.Duration;
import socialnetwork.domain.*;

import socialnetwork.repository.database.PrieteniiDBrepo;
import socialnetwork.service.UtilizatorService;
import socialnetwork.utils.events.EntityEventType;
import socialnetwork.utils.events.EventForOb;
import socialnetwork.utils.events.EventType;
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
    ObservableList<Pane> listEventsBox = FXCollections.observableArrayList();
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
    Pane createEventPane;
    @FXML
    Text last_name_text;

    @FXML
    Label label_current_tab;
    @FXML
    AnchorPane events_layout;
    @FXML
    ListView events_listView;
    @FXML
    TextField pageNumber;

    @FXML
    TextField eventNameField;
    @FXML
    TextArea eventDescriptionArea;
    @FXML
    DatePicker eventDateField;

    @FXML
    private void initialize() {
        utilizatorService.setPageSize(7);
        initModel();
        Utilizator x = utilizatorService.getOne(id_sign_inUtil);
        first_name_text.setText(x.getFirstName());
        last_name_text.setText(x.getLastName());

    }

    private void initModel(){
        loadUsers();
        loadFriends();
        loadRequests();
        loadEvents(1);
    }

    private void loadUsers(){
        listUsersBox.clear();
        utilizatorService.getAll().forEach(x->{
            if(x.getId()!= id_sign_inUtil) {
                UserPane util =new UserPane(x);
                listUsersBox.add(util);
            }
        });
        users_listView.getItems().clear();
        users_listView.getItems().setAll(listUsersBox);
    }

    private void loadRequests(){
        listRequestsBox.clear();
        utilizatorService.getInvitatiiPrimite(id_sign_inUtil).forEach(x->{
            Utilizator utilizator =utilizatorService.getOne(x.getId_from());
            RequestPane  util = new RequestPane(utilizator,x,0);
            listRequestsBox.add(util);
        });

        utilizatorService.getInvitatiiTrimise(id_sign_inUtil).forEach(x->{
            Utilizator utilizator =utilizatorService.getOne(x.getId_to());
            RequestPane  util = new RequestPane(utilizator,x,1);
            listRequestsBox.add(util);
        });

        requests_listView.getItems().clear();
        requests_listView.getItems().addAll(listRequestsBox);
    }

    private void loadFriends(){
        listFriendsBox.clear();
        utilizatorService.getPrieteni(id_sign_inUtil).forEach(x->{
            FriendPane util = new FriendPane(x);
            listFriendsBox.add(util);
        });
        friends_listView.getItems().clear();
        friends_listView.getItems().addAll(listFriendsBox);
    }

    public void loadEvents(int page){
        listEventsBox.clear();

        utilizatorService.getEventsOnPage(page).forEach(x->{
            EventPane eventPane = new EventPane(x);
            listEventsBox.add(eventPane);
        });
        events_listView.getItems().clear();
        events_listView.getItems().addAll(listEventsBox);
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
        events_layout.setDisable(true);
        events_layout.setVisible(false);

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
        events_layout.setDisable(true);
        events_layout.setVisible(false);

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
        events_layout.setDisable(true);
        events_layout.setVisible(false);

        requests_layout.setDisable(false);
        requests_layout.setVisible(true);
        label_current_tab.setText("Requests");

    }

    @FXML
    public void moveToevents(){
        friends_layout.setDisable(true);
        friends_layout.setVisible(false);
        users_layout.setDisable(true);
        users_layout.setVisible(false);
        requests_layout.setDisable(true);
        requests_layout.setVisible(false);

        events_layout.setDisable(false);
        events_layout.setVisible(true);
        label_current_tab.setText("Events");

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
            e.printStackTrace();
        }
    }

    @FXML
    public void handleNextPage(){
        int pNumber = Integer.parseInt(pageNumber.getText());
        pNumber++;
        pageNumber.setText(String.valueOf(pNumber));
        loadEvents(pNumber);


    }

    @FXML
    public void handlePreviousPage(){
        int pNumber = Integer.parseInt(pageNumber.getText());
        if(pNumber > 1) {
            pNumber--;
            pageNumber.setText(String.valueOf(pNumber));
            loadEvents(pNumber);
        }
    }

    @FXML
    public void handleCreateEvent(){
        createEventPane.setLayoutY(620);
        createEventPane.setVisible(true);
        createEventPane.setDisable(false);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue kvcreateEventPane= new KeyValue(createEventPane.layoutYProperty(), 270);
        KeyFrame kfcreateEventPane = new KeyFrame(Duration.millis(500), kvcreateEventPane);
        timeline.getKeyFrames().add(kfcreateEventPane);
        timeline.play();
    }

    @FXML
    public void handleBack(){
        createEventPane.setDisable(true);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue kvcreateEventPane= new KeyValue(createEventPane.layoutYProperty(), 620);
        KeyFrame kfcreateEventPane = new KeyFrame(Duration.millis(500), kvcreateEventPane);
        timeline.getKeyFrames().add(kfcreateEventPane);
        timeline.play();
    }
    @FXML
    public void handleEventCreate(){
        if(eventNameField.getText()!=null && eventDateField.getValue() !=null && eventDescriptionArea.getText()!= null ){
            if(utilizatorService.createEvent(eventNameField.getText(),eventDescriptionArea.getText(),eventDateField.getValue().atStartOfDay(),id_sign_inUtil) == null){
                createEventPane.setDisable(true);
                Timeline timeline = new Timeline();
                timeline.setCycleCount(1);
                KeyValue kvcreateEventPane= new KeyValue(createEventPane.layoutYProperty(), 620);
                KeyFrame kfcreateEventPane = new KeyFrame(Duration.millis(500), kvcreateEventPane);
                timeline.getKeyFrames().add(kfcreateEventPane);
                timeline.play();
            }
        }
    }



    @Override
    public void update(EventForOb x) {
        if(x.getEntityEventType() == EntityEventType.FRIEND){
            loadFriends();
        }
        else if(x.getEntityEventType() == EntityEventType.REQUEST){
            if(x.getEventType() == EventType.ADD){
                loadRequests();
            }
            else if(x.getEventType() == EventType.UPDATE){
                loadRequests();
                loadFriends();
            }
            else if(x.getEventType() == EventType.REMOVE) {
                loadRequests();
            }
        }

    }




    public class UserPane extends Pane{
        //utilizator
        Utilizator x;
        //imaginea
        ImageView imgV;
        //numele utilizator
        Label textLabel;
        //btn chat
        Button btnChat;
        //btn add friend
        Button btnFriend =null;

        public UserPane(Utilizator utilizator){
            this.x = utilizator;
            super.autosize();
            loadImage();
            loadName();
            loadButtonChat();
            loadBtnFriend();
        }

        private void loadImage() {
            imgV =  new ImageView();
            Image image = new Image("others/user52x50.png");
            imgV.setImage(image);
            imgV.setLayoutX(6);
            imgV.setLayoutY(15);
            super.getChildren().add(imgV);
        }

        private void loadName() {
            textLabel = new Label(x.getFirstName() + " " + x.getLastName());
            textLabel.getStyleClass().add("textUser-list");
            textLabel.setLayoutX(62);
            textLabel.setLayoutY(14);
            super.getChildren().add(textLabel);
        }

        private void loadButtonChat() {
            btnChat = new Button();
            Tooltip tpbtnChat = new Tooltip();
            tpbtnChat.setText("Start Converstation");
            tpbtnChat.setShowDelay(new Duration(10));
            btnChat.setTooltip(tpbtnChat);
            btnChat.getStyleClass().add("btnChatUser-list");
            btnChat.setLayoutX(455);
            btnChat.setLayoutY(14);
            btnChat.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FXMLLoader chatLoader = new FXMLLoader();
                        chatLoader.setLocation(getClass().getResource("/views/chatView.fxml"));
                        ControllerChat controllerChat = new ControllerChat();
                        controllerChat.setService(utilizatorService, id_sign_inUtil, x.getId());
                        chatLoader.setController(controllerChat);
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        AnchorPane chatLayout = chatLoader.load();

                        Scene scene = new Scene(chatLayout);
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        System.out.println("ceva");
                        e.printStackTrace();
                    }
                }
            });

            super.getChildren().add(btnChat);
        }

        private void loadBtnFriend() {
            if ((utilizatorService.getPrietenie(new Tuple<>(id_sign_inUtil, x.getId())) == null && utilizatorService.getPrietenie(new Tuple<>(x.getId(), id_sign_inUtil)) == null && !utilizatorService.isApendingRequest(x.getId(), id_sign_inUtil))) {
                btnFriend = new Button();
                btnFriend.getStyleClass().add("btnFriendUser-list");
                btnFriend.setLayoutX(390);
                btnFriend.setLayoutY(12);
                btnFriend.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        utilizatorService.sendInvidatie(id_sign_inUtil, x.getId());
                        UserPane.super.getChildren().remove(btnFriend);
                    }
                });
                super.getChildren().add(btnFriend);
            }
        }

        public Utilizator getUtilizator() {
            return x;
        }
    }

    public class RequestPane extends Pane{
        //utilizator
        Utilizator utilizator;
        //
        Invitatie x;
        //tipul invitatie 1-> trimis, 0-> primita
        int type;
        //imaginea
        ImageView imgV;
        //numele utilizator
        Label textLabel;
        //stare request
        Label textStare;
        //btoane cereri primite
        Button btnDeny;
        Button btnAprove;
        //buton delete cerere
        Button btnDelete;

        public RequestPane(Utilizator utilizator,Invitatie invitatie, int type){
            this.utilizator = utilizator;
            this.x = invitatie;
            this.type = type;
            loadImage();
            loadName();
            loadStare();
            if(type == 0){
                loadButtonAcceptAndDeny();
            }
            loadBtnDelete();
        }

        private void loadImage() {
            imgV = new ImageView();
            Image image = new Image("others/chain.png");
            imgV.setImage(image);
            imgV.setFitHeight(40);
            imgV.setFitWidth(41);
            imgV.setLayoutX(14);
            imgV.setLayoutY(20);
            super.getChildren().add(imgV);
        }

        private void loadName() {
            textLabel = new Label(utilizator.getFirstName() + " " + utilizator.getLastName());
            textLabel.getStyleClass().add("textRequest-list");
            textLabel.setLayoutX(70);
            textLabel.setLayoutY(0);
            super.getChildren().add(textLabel);
        }

        private void loadStare() {
            textStare = new Label();
            textStare.getStyleClass().add("textTypeRequest-list");
            textStare.setLayoutX(70);
            textStare.setLayoutY(30);
            if (x.getStare() == 1) {
                textStare.setText("Pending");

            } else if (x.getStare() == 3) {
                textStare.setText("Aproved");

            } else {
                textStare.setText("Rejected");

            }

            super.getChildren().add(textStare);
        }
        private void loadButtonAcceptAndDeny() {
            if (x.getStare() == 1) {
                btnAprove = new Button();
                btnAprove.getStyleClass().add("btnAproveRequest-list");
                btnAprove.setLayoutX(350);
                btnAprove.setLayoutY(14);
                btnAprove.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        utilizatorService.acceptInvitatie(id_sign_inUtil, x.getId());
                    }
                });
                super.getChildren().add(btnAprove);

                btnDeny = new Button();
                btnDeny.getStyleClass().add("btnDenyRequest-list");
                btnDeny.setLayoutX(390);
                btnDeny.setLayoutY(14);
                btnDeny.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        utilizatorService.rejectInvitatie(id_sign_inUtil, x.getId());
                    }
                });
                super.getChildren().add(btnDeny);

            }
        }

        private void loadBtnDelete() {
            btnDelete = new Button();
            btnDelete.getStyleClass().add("btnDeleteRequest-list");
            btnDelete.setLayoutX(455);
            btnDelete.setLayoutY(14);
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    utilizatorService.removeInvitatie(x.getId());
                }
            });
            super.getChildren().add(btnDelete);
        }
    }

    public class FriendPane extends Pane{

        //imaginea
        ImageView imgV;
        //nume utilizator
        Label textLabel;
        //data utilizator
        Label textData;
        //brn remove friend
        Button btnRemoveFriend;
        PrietenDTO x;
        public FriendPane(PrietenDTO prieten){
            this.x = prieten;
            loadImage();
            loadName();
            loadTime();
            loadButton();
        }
        private void loadImage() {
            imgV = new ImageView();
            Image image = new Image("others/user52x50.png");
            imgV.setImage(image);
            imgV.setLayoutX(6);
            imgV.setLayoutY(15);
            super.getChildren().add(imgV);
        }

        private void loadName() {
           textLabel = new Label(x.getNume() + " " + x.getPrenume());
           textLabel.getStyleClass().add("textFriend-list");
           textLabel.setLayoutX(70);
           textLabel.setLayoutY(0);
           super.getChildren().add(textLabel);
        }

        private void loadTime() {
            textData = new Label("since: " + x.getData().toString());
            textData.getStyleClass().add("textDateFriend-list");
            textData.setLayoutX(70);
            textData.setLayoutY(30);
            super.getChildren().add(textData);
        }
        private  void loadButton() {
            btnRemoveFriend = new Button();
            btnRemoveFriend.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    utilizatorService.removePrietenie(x.getId());
                }
            });
            btnRemoveFriend.getStyleClass().add("btnRemoveFriend-list");
            btnRemoveFriend.setLayoutX(455);
            btnRemoveFriend.setLayoutY(20);
            super.getChildren().add(btnRemoveFriend);
        }
    }

    public class EventPane extends Pane{
        //utilizator
        socialnetwork.domain.Event x;
        //imaginea
        ImageView imgV;
        //numele utilizator
        Label textLabel;
        //btn chat
        Button btnGo;
        //btn add friend
        Button btnFriend =null;

        public EventPane(socialnetwork.domain.Event x){
            this.x = x;
            loadImage();
            loadName();
            if(utilizatorService.isAParticipant(id_sign_inUtil,x.getId()))
                loadButtonGO();

        }

        private void loadImage() {
            imgV =  new ImageView();
            Image image = new Image("others/event.png");
            imgV.setImage(image);
            imgV.setLayoutX(3);
            imgV.setLayoutY(10);
            super.getChildren().add(imgV);
        }

        private void loadName() {
            textLabel = new Label(x.getName());
            textLabel.getStyleClass().add("textEvent-list");
            textLabel.setLayoutX(62);
            textLabel.setLayoutY(14);
            super.getChildren().add(textLabel);
        }

        private void loadButtonGO() {
            btnGo = new Button();

            btnGo.setLayoutX(455);
            btnGo.setLayoutY(14);
            btnGo.setText("GO");
            btnGo.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    utilizatorService.addParticipant(id_sign_inUtil,x.getId());
                    btnGo.setVisible(false);
                    btnGo.setDisable(true);
                }
            });

            super.getChildren().add(btnGo);
        }




    }
}
