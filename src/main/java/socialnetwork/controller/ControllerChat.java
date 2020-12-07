package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import socialnetwork.service.UtilizatorService;
import socialnetwork.utils.observer.Observer;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControllerChat extends Observer {
    UtilizatorService utilizatorService;
    Long id_from;
    Long id_to;
    @FXML
    ListView message_listView;
    @FXML
    Button btn_sendMessage;
    @FXML
    TextField messageField;

    ObservableList<Label> msg_listView = FXCollections.observableArrayList();



    @FXML
    private void initialize() {
        loadMessages();
    }


    private void loadMessages(){
        msg_listView.clear();
        utilizatorService.getConversatie(id_from,id_to).forEach(x ->{
            MyLabel msg = new MyLabel(x.getMesaj(),x.getId());
            msg.setPrefWidth(message_listView.getPrefWidth()-20);
            if(x.getFrom() == id_from){
                msg.setAlignment(Pos.CENTER_RIGHT);
            }
            msg_listView.add(msg);
        });
        message_listView.getItems().setAll(msg_listView);
    }

    public void setService(UtilizatorService utilizatorService, Long id_from,Long id_to) {
        this.utilizatorService = utilizatorService;
        this.id_from = id_from;
        this.id_to = id_to;
        utilizatorService.addObserver(this);

    }

    @FXML
    public void handleSendMessage(){
        if(!messageField.getText().isEmpty()){
            MyLabel selected = (MyLabel) message_listView.getSelectionModel().getSelectedItem();
            if(selected != null){
                utilizatorService.replayMessage(selected.getIdMessage(),id_from,LocalDateTime.now(),messageField.getText());
            }
            else{
                List<Long> listTo = new ArrayList<>();
                listTo.add(id_to);
                utilizatorService.sendMessage(id_from, LocalDateTime.now(),listTo,messageField.getText());
            }
            messageField.clear();
        }
    }


    @Override
    public void update() {
        loadMessages();
    }


    class MyLabel extends Label{
        Long idMessage;

        public MyLabel() {
        }

        public MyLabel(String text,Long id) {
            super(text);
            this.idMessage = id;
        }


        public Long getIdMessage() {
            return idMessage;
        }
    }

}
