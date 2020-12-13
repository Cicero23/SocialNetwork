package socialnetwork.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Date;
import java.io.File;
import java.util.List;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import socialnetwork.domain.ActionDTO;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ControllerActionHistory {
    private Long id_user;
    private UtilizatorService utilizatorService;
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    private List<ActionDTO> l;
    @FXML
    DatePicker id_before;

    @FXML
    DatePicker id_after;

    @FXML
    Button btnGenerate;

    @FXML
    Pane generatePane;

    @FXML
    Pane exportPane;

    @FXML
    ListView list_viewActions;

    @FXML
    private void initialize() {

    }

    public void setService(UtilizatorService utilizatorService, Long id_user) {
        this.utilizatorService = utilizatorService;
        this.id_user = id_user;


    }
    @FXML
    public void handleGenerate(){
        if(id_before.getValue() != null && id_after.getValue() != null && id_before.getValue().isBefore(id_after.getValue()) ) {
            generatePane.setVisible(false);
            generatePane.setDisable(true);
            l= utilizatorService.getActionsDuringPeriod(id_user,id_before.getValue(),id_after.getValue());
            l.forEach(x->{
                list_viewActions.getItems().add(x.getDesc() + " - " + x.getDate().toString());
            });
            exportPane.setVisible(true);
            exportPane.setDisable(false);
        }
    }

    @FXML
    public void handleExport(){
        try {

            File path = directoryChooser.showDialog(new Stage());
            if (path != null) {
                System.out.println(path.getPath());
                String FILE = path.getPath().toString() + "/Actions_" + utilizatorService.getOne(id_user).getFirstName() + ".pdf";
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

    private void generateContinut(Document doc) throws DocumentException{
        Utilizator  utilizator = utilizatorService.getOne(id_user);
        Paragraph user = new Paragraph("User:" + utilizator.getFirstName() + " " + utilizator.getLastName());
        Paragraph desc = new Paragraph("Messages and new friends between :" + id_before.getValue().toString() + " and " + id_after.getValue().toString());
        doc.add(user);
        doc.add(desc);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100); //Width 100%
        table.setSpacingBefore(10f); //Space before table
        table.setSpacingAfter(10f); //Space after table
        PdfPCell actionCell = new PdfPCell(new Paragraph("Action"));
        PdfPCell dateCell =  new PdfPCell(new Paragraph("Date"));
        table.addCell(actionCell);
        table.addCell(dateCell);
        l.forEach(x->{
            PdfPCell actionCellI = new PdfPCell(new Paragraph(x.getDesc()));
            PdfPCell dateCellI =  new PdfPCell(new Paragraph(x.getDate().toString()));
            table.addCell(actionCellI);
            table.addCell(dateCellI);
        });
        doc.add(table);
    }







}
