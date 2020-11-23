package socialnetwork.repository.factory;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;

import javax.xml.parsers.ParserConfigurationException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class FactoryPrietenie extends Factory<Tuple<Long,Long>, Prietenie> {

    public FactoryPrietenie(String separator) {
        super(separator);
    }

    @Override
    public Prietenie extractEntity(String s) {
        List<String> atr = Arrays.asList(s.split(separator));
        Long id1;
        Long id2;
        LocalDate date;
        try {
            id1 = Long.parseLong(atr.get(0));
        }
        catch (NumberFormatException e){
            id1 = null;
        }
        catch (ArrayIndexOutOfBoundsException e){
            id1 = null;
        }

        try {
            id2 = Long.parseLong(atr.get(1));
        }
        catch (NumberFormatException e){
            id2 = null;
        }
        catch (ArrayIndexOutOfBoundsException e){
            id2 = null;
        }
        try{
            date = LocalDate.parse(atr.get(2));

        }
        catch (ArrayIndexOutOfBoundsException e){
            date = null;
        }
        catch (DateTimeException e){
            date = null;
        }
        Prietenie prietenie = new Prietenie(date);
        prietenie.setId(new Tuple<Long,Long>(id1,id2));
        return prietenie;
    }

    @Override
    public String createEntityAsString(Prietenie entity) {
        return entity.getId().getLeft().toString() + ";" + entity.getId().getRight().toString() + ";" + entity.getDate().toString();
    }


}

