package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.InvitatieDBrepo;
import socialnetwork.repository.database.MessageDBRepo;
import socialnetwork.repository.database.PrieteniiDBrepo;
import socialnetwork.repository.database.UsersDBrepo;
import socialnetwork.repository.factory.FactoryPrietenie;
import socialnetwork.repository.factory.FactoryUtilizatorDB;
import socialnetwork.service.UtilizatorService;
import socialnetwork.ui.UiSimplu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class main3 {
    public static void main(String[] args) {
        String url= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        String username= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username");
        String password= ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password");
        Repository<Long, Invitatie> invitatieDBrepo = new InvitatieDBrepo(url,username,password);
        invitatieDBrepo.save(new Invitatie(19,29,LocalDate.now(),1));
    }
}
