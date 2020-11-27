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
        MainFX.main(args);
    }
}


