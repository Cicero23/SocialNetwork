package socialnetwork.domain.validators;

import socialnetwork.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String errors = "";
        if(entity.getLastName()== null || entity.getLastName().isEmpty()){
            errors = errors + "Numele nu poate fi null\n";
        }
        if(entity.getFirstName()==null || entity.getFirstName().isEmpty()){
            errors =  errors + "Prenumele nu poate fi null\n";
        }
        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
}
