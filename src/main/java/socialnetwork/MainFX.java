package socialnetwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import socialnetwork.config.ApplicationContext;
import socialnetwork.controller.ControllerLogin;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.*;
import socialnetwork.repository.factory.FactoryPrietenie;
import socialnetwork.repository.factory.FactoryUtilizatorDB;
import socialnetwork.service.UtilizatorService;

import java.io.IOException;

public class MainFX extends Application {
    Repository<Long, Utilizator> usersDBrepo;
    Repository<Tuple<Long,Long>, Prietenie> prieteniiDBrepo;
    Repository<Long, Message> messageDBrepository;
    Repository<Long, Invitatie> invitatieDBrepository;
    EventsDBrepo eventsDBrepository;
    AccountDBrepo accountDBrepo;
    UtilizatorService utilizatorService;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        String url= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        String username= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username");
        String password= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password");
        usersDBrepo = new UsersDBrepo(url, username, password, new UtilizatorValidator());
        prieteniiDBrepo= new PrieteniiDBrepo(url, username, password, new PrietenieValidator());
        messageDBrepository= new MessageDBRepo(url, username,password);
        invitatieDBrepository= new InvitatieDBrepo(url, username,password);
        eventsDBrepository = new EventsDBrepo(url,username,password);
        accountDBrepo= new AccountDBrepo(url,username,password);
        utilizatorService = new UtilizatorService(usersDBrepo,prieteniiDBrepo,new FactoryUtilizatorDB(" "),new FactoryPrietenie(" "), messageDBrepository,invitatieDBrepository,accountDBrepo,eventsDBrepository);
        initView(primaryStage);
        primaryStage.show();


    }

    private void initView(Stage primaryStage) throws IOException {

        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("/views/loginView.fxml"));
        AnchorPane loginLayout = loginLoader.load();
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(new Scene(loginLayout));

        ControllerLogin controllerLogin =loginLoader.getController();
        controllerLogin.setService(utilizatorService,primaryStage);

    }


}
