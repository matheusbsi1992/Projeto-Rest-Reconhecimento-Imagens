package br.com.projeto.estudos.exceptions.utilexceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class UtilMyBindingResult {

    public static Map<String, String> resulTerror(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getAllErrors().forEach(error -> {
            String defaultMessage = error.getDefaultMessage();
            String fieldName = ((FieldError) error).getField();
            errors.put(fieldName, defaultMessage);
        });
        return errors;
    }
}
