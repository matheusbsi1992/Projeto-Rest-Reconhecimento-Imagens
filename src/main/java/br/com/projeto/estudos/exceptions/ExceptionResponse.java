package br.com.projeto.estudos.exceptions;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ExceptionResponse implements Serializable {

    private String mensagem;
    private String detalhes;
    private Date dataMensagem;

}
