package socialnetwork.repository.file;


import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import javax.swing.text.DateFormatter;

import java.time.LocalDate;

import java.util.List;


/*public class PrietenieFile extends AbstractFileRepository<Tuple<Long,Long>, Prietenie> {


    public PrietenieFile(String fileName, Validator<Prietenie> validator) {
        super(fileName, validator);

    }

    @Override
    public Prietenie extractEntity(List<String> attributes) {
        try {
            LocalDate date = LocalDate.parse(attributes.get(2));
            Prietenie prietenie = new Prietenie(date);
            prietenie.setId(new Tuple<Long, Long>(Long.parseLong(attributes.get(0)), Long.parseLong(attributes.get(1))));
            return prietenie;
        }
        catch (NullPointerException e){
            return null;
        }
    }

    @Override
    protected String createEntityAsString(Prietenie entity) {
        return entity.getId().getLeft().toString() + ";" + entity.getId().getRight().toString() + ";" + entity.getDate().toString();
    }
}
*/