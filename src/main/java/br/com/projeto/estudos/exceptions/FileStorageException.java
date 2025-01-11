package br.com.projeto.estudos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends RuntimeException implements Serializable {

    public FileStorageException(String ex) {
        super(ex);
    }

    public FileStorageException(String ex, Throwable cause) {
        super(ex, cause);
    }
}
