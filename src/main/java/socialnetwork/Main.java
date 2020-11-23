package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.factory.Factory;
import socialnetwork.repository.factory.FactoryPrietenie;
import socialnetwork.repository.factory.FactoryUtiliziator;
//
import socialnetwork.repository.file.FileRepository;
//import socialnetwork.repository.file.PrietenieFile;
//import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.service.UtilizatorService;
import socialnetwork.ui.UiSimplu;

public class Main {
    public static void main(String[] args) {
        /*String fileNameUtil=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String fileNamePriet=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.prietenii");
        //String fileName="data/users.csv";
        Repository<Long,Utilizator> userFileRepository = new FileRepository<Long,Utilizator>(fileNameUtil, new UtilizatorValidator(),new FactoryUtiliziator(";"));
        Repository<Tuple<Long, Long>, Prietenie> prietenieFileRepository = new FileRepository<Tuple<Long,Long>,Prietenie>(fileNamePriet, new PrietenieValidator(),new FactoryPrietenie(";"));

        UtilizatorService utilizatorService  = new UtilizatorService(userFileRepository,prietenieFileRepository,new FactoryUtiliziator(""),new FactoryPrietenie(" "));
        UiSimplu uiSimplu = new UiSimplu(utilizatorService);
        uiSimplu.run();
        */
    }
}


