package socialnetwork.repository.factory;

import socialnetwork.domain.Utilizator;

import java.util.Arrays;
import java.util.List;

public class FactoryUtilizatorDB extends Factory<Long,Utilizator>{
    public FactoryUtilizatorDB (String separator) {
        super(separator);
    }

    @Override
    public Utilizator extractEntity(String s) {
        List<String> atr = Arrays.asList(s.split(separator));
        String nume;
        String prenume;


        try{
            nume = atr.get(0);
        }
        catch (ArrayIndexOutOfBoundsException e){
            nume = null;
        }

        try{
            prenume = atr.get(1);
        }
        catch (ArrayIndexOutOfBoundsException e){
            prenume = null;
        }
        Utilizator x= new Utilizator( nume , prenume);

        return x;
    }

    @Override
    public String createEntityAsString(Utilizator entity) {
        return entity.getId()+separator+entity.getFirstName()+separator+entity.getLastName();
    }
}
