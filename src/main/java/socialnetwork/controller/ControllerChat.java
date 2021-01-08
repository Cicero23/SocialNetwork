package socialnetwork.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;
import socialnetwork.utils.events.EntityEventType;
import socialnetwork.utils.events.EventForOb;
import socialnetwork.utils.observer.Observer;


import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @FXML
    Text userText;
    @FXML
    DatePicker id_before;
    @FXML
    DatePicker id_after;


    ObservableList<Label> msg_listView = FXCollections.observableArrayList();



    @FXML
    private void initialize() {
        Utilizator utilizator =utilizatorService.getOne(id_to);
        userText.setText(utilizator.getFirstName() + " " + utilizator.getLastName());
        loadMessages();
    }


    private void loadMessages(){
        msg_listView.clear();
        utilizatorService.getConversatie(id_from,id_to).forEach(x ->{
            MyLabel msg = new MyLabel(x.getData().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm:ss"))+": "+x.getMesaj(),x.getId());
            msg.setPrefWidth(message_listView.getPrefWidth()-20);
            if(x.getFrom() == id_from){
                msg.setTextFill(Color.web("#35e655"));
            }
            else{
                msg.setTextFill(Color.web("#ed4e4e"));
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

    @FXML
    public void handleExport(){
        if(id_after.getValue() != null && id_before.getValue() !=null && id_after.getValue().isAfter(id_before.getValue())) {
            try {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File path = directoryChooser.showDialog(new Stage());
                if (path != null) {
                    System.out.println(path.getPath());
                    String FILE = path.getPath().toString() + "/Messages" + utilizatorService.getOne(id_from).getFirstName() + "_" + utilizatorService.getOne(id_to).getFirstName() + ".pdf";
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(FILE));
                    document.open();
                    generateContinut(document);
                    document.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void generateContinut(Document doc) throws DocumentException {
        Utilizator utilizator_1 = utilizatorService.getOne(id_from);
        Utilizator utilizator_2 = utilizatorService.getOne(id_to);
        Paragraph user = new Paragraph("User:" + utilizator_1.getFirstName() + " " + utilizator_1.getLastName());
        Paragraph desc = new Paragraph("Messages from " + utilizator_2.getFirstName() + " " + utilizator_2.getLastName());
        doc.add(user);
        doc.add(desc);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100); //Width 100%
        table.setSpacingBefore(10f); //Space before table
        table.setSpacingAfter(10f); //Space after table
        PdfPCell messageCell = new PdfPCell(new Paragraph("Message"));
        PdfPCell dateCell =  new PdfPCell(new Paragraph("Date"));
        table.addCell(messageCell);
        table.addCell(dateCell);
        utilizatorService.getMessagesFromTimeInterval(id_to,id_from,id_before.getValue(),id_after.getValue()).forEach(x->{
            PdfPCell messageCellI = new PdfPCell(new Paragraph("\""  + x.getMesaj() + "\""));
            PdfPCell dateCellI =  new PdfPCell(new Paragraph(x.getData().toString()));
            table.addCell(messageCellI);
            table.addCell(dateCellI);

        });


        doc.add(table);
    }
    @Override
    public void update(EventForOb x) {
        if(x.getEntityEventType() == EntityEventType.MESSAGE) {
            loadMessages();
        }
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
