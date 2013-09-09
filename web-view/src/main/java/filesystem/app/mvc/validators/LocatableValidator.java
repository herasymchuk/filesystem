package filesystem.app.mvc.validators;

import filesystem.app.model.entity.Locatable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 07.09.13
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
@Component
public class LocatableValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        //just validate the FileUpload instances
        return Locatable.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Locatable item = (Locatable)target;

        if(!isNameValid(item.getName())) {
            errors.rejectValue("name", "label.error.required.folderName");
        }
    }

    private boolean isNameValid(String name) {
        return !(name == null ||
                name.isEmpty() ||
                name.contains("/") ||
                name.contains("\\") ||
                name.contains("?") ||
                name.contains(","));
    }
}
