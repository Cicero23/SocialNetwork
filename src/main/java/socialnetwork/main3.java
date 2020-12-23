package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.*;
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
        Repository<Long, Event> repo = new EventsDBrepo(url,username,password);
        AccountDBrepo accountDBrepo = new AccountDBrepo(url,username,password);
    }
}
