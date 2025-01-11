package br.com.projeto.estudos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException implements Serializable {

    public MyFileNotFoundException(String ex) {
        super(ex);
    }

    public MyFileNotFoundException(String ex, Throwable cause) {
        super(ex, cause);
    }
}
