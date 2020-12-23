package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.*;
import socialnetwork.repository.factory.FactoryPrietenie;
import socialnetwork.repository.factory.FactoryUtilizatorDB;
import socialnetwork.service.UtilizatorService;
import socialnetwork.ui.UiSimplu;

import java.time.LocalDate;

public class Main2 {
    public static void main(String[] args) {
        String url= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        String username= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username");
        String password= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password");
        Repository<Long, Utilizator>  usersDBrepo = new UsersDBrepo(url, username, password, new UtilizatorValidator());
        Repository<Tuple<Long,Long>, Prietenie> prieteniiDBrepo = new PrieteniiDBrepo(url, username, password, new PrietenieValidator());
        Repository<Long, Message> messageDBrepository = new MessageDBRepo(url, username,password);
        Repository<Long, Invitatie> invitatieDBrepository = new InvitatieDBrepo(url, username,password);
        AccountDBrepo accountDBrepo = new AccountDBrepo(url,username,password);
        //UtilizatorService utilizatorService  = new UtilizatorService(usersDBrepo,prieteniiDBrepo,new FactoryUtilizatorDB(" "),new FactoryPrietenie(" "), messageDBrepository,invitatieDBrepository,accountDBrepo);
        //UiSimplu uiSimplu = new UiSimplu(utilizatorService);
        //uiSimplu.run();
    }
}
