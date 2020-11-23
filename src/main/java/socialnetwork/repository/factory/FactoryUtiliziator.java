package socialnetwork.repository.factory;

import socialnetwork.domain.Utilizator;


import javax.xml.parsers.ParserConfigurationException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class FactoryUtiliziator extends Factory<Long,Utilizator> {

    public FactoryUtiliziator(String separator) {
        super(separator);
    }

    @Override
    public Utilizator extractEntity(String s) {
        List<String> atr = Arrays.asList(s.split(separator));
        Long id;
        String nume;
        String prenume;
        try {
             id = Long.parseLong(atr.get(0));
        }
        catch (NumberFormatException e){
            id = null;
        }
        catch (ArrayIndexOutOfBoundsException e){
            id = null;
        }

        try{
            nume = atr.get(1);
        }
        catch (ArrayIndexOutOfBoundsException e){
            nume = null;
        }

        try{
            prenume = atr.get(2);
        }
        catch (ArrayIndexOutOfBoundsException e){
            prenume = null;
        }
        Utilizator x= new Utilizator( nume , prenume);
        x.setId(id);
        return x;
    }

    @Override
    public String createEntityAsString(Utilizator entity) {
        return entity.getId()+separator+entity.getFirstName()+separator+entity.getLastName();
    }
}
