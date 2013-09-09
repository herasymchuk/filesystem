package filesystem.app.mvc.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 07.09.13
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
@Component
public class FileValidator implements Validator{

    @Override
    public boolean supports(Class clazz) {
        //just validate the FileUpload instances
        return MultipartFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MultipartFile file = (MultipartFile)target;

        if(file.getSize() == 0){
            errors.rejectValue("data", "label.error.required.fileUpload");
        }
    }
}
