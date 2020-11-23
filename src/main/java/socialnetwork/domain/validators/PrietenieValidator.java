package socialnetwork.domain.validators;

import socialnetwork.domain.Prietenie;

public class PrietenieValidator implements Validator<Prietenie> {

    @Override
    public void validate(Prietenie entity) throws ValidationException {
        String errors = "";
        if(entity.getId().getLeft() == null){
            errors = errors + "Id-ul primului trebuie sa fie intreg si sa nu fie null\n";
        }
        if(entity.getId().getRight() == null){
            errors = errors + "Id-ul celui de-al doilea trebuie sa fie intreg si sa nu fie null\n";
        }
        if(entity.getDate() ==null){
            errors =  errors + "Data nu poate fi nulla\n";
        }
        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
}
