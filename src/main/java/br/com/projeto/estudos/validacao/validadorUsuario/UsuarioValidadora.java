package br.com.projeto.estudos.validacao.validadorUsuario;

import br.com.projeto.estudos.dto.UsuarioDTO;
import br.com.projeto.estudos.repositorio.UsuarioRepositorio;
import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.ValidationAnnotationUtils;

import java.util.Objects;

//@Component
public class UsuarioValidadora implements ConstraintValidator<ValidarErrorUsuario, UsuarioDTO> {

    UsuarioRepositorio usuarioRepositorio;
    UsuarioAutenticado usuarioAutenticado;

    @Autowired
    public UsuarioValidadora(UsuarioRepositorio usuarioRepositorio, UsuarioAutenticado usuarioAutenticado) {
        Objects.requireNonNull(usuarioRepositorio);
        Objects.requireNonNull(usuarioAutenticado);

        this.usuarioAutenticado = usuarioAutenticado;
        this.usuarioRepositorio = usuarioRepositorio;
    }

  /*  public boolean isValid(UnidadeDTO unidadeDTO, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        boolean isValid = true;


        //---Dados para outras integracoes que nao seja atualizacao
        System.out.println("UNIDADE --->>>" + unidadeDTO.getIdUnidade());
        if (unidadeDTO.getIdUnidade() == null || unidadeDTO.getIdUnidade() == 0) {
            if (unidadeRepositorio.buscarNomeFantasiaUnidade(unidadeDTO.getNomeFantasiaUnidade())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Nome de Fantasia Já Existe!!!")
                        .addPropertyNode("nomeFantasiaUnidade").addConstraintViolation();
                isValid = false;
            }
            if (unidadeRepositorio.buscarRazaoSocialUnidade(unidadeDTO.getRazaoSocialUnidade())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Razão Social Já Cadastrada !!!")
                        .addPropertyNode("razaoSocialUnidade").addConstraintViolation();
                isValid = false;
            }
            if (unidadeRepositorio.buscarDocumentoUnidade(unidadeDTO.getDocumentoUnidade())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Documento da Unidade Já Cadastrado !!!")
                        .addPropertyNode("documentoUnidade").addConstraintViolation();
                isValid = false;
            }
            if (unidadeRepositorio.buscarEmailUnidade(unidadeDTO.getEmailUnidade())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Email da Unidade Já Cadastrado !!!")
                        .addPropertyNode("emailUnidade").addConstraintViolation();
                isValid = false;
            }
            return isValid;
        }

        //--- Verificar se o nome fantasia já está sendo usado por outra unidade (excluindo a própria unidade)
        if (unidadeRepositorio.buscarNomeFantasiaUnidade(unidadeDTO.getNomeFantasiaUnidade(), unidadeDTO.getIdUnidade())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Nome de Fantasia Já Existe!!!")
                    .addPropertyNode("nomeFantasiaUnidade").addConstraintViolation();
            isValid = false;
        }

        //--- Verificar se a razão social já está sendo usada por outra unidade (excluindo a própria unidade)
        if (unidadeRepositorio.buscarRazaoSocialUnidade(unidadeDTO.getRazaoSocialUnidade(), unidadeDTO.getIdUnidade())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Razão Social Já Cadastrada !!!")
                    .addPropertyNode("razaoSocialUnidade").addConstraintViolation();
            isValid = false;
        }
        //--- Verificar se o documento (CPF/CNPJ) já está sendo usado por outra unidade (excluindo a própria unidade)
        if (unidadeRepositorio.buscarDocumentoUnidade(unidadeDTO.getDocumentoUnidade(), unidadeDTO.getIdUnidade())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Documento da Unidade Já Cadastrado !!!")
                    .addPropertyNode("documentoUnidade").addConstraintViolation();
            isValid = false;
        }
        if (unidadeRepositorio.buscarEmailUnidade(unidadeDTO.getEmailUnidade(), unidadeDTO.getIdUnidade())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Email da Unidade Já Cadastrado !!!")
                    .addPropertyNode("emailUnidade").addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }*/

    @Override
    public boolean isValid(UsuarioDTO usuarioDTO, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        
        boolean isValid = true;

        if (usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            if (usuarioDTO.getIdUsuario() == null || usuarioDTO.getIdUsuario().length() == 0) {
                if (usuarioRepositorio.buscarUsuario(usuarioDTO.getUsuario())) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Usuário Já Existe !!!")
                            .addPropertyNode("usuario").addConstraintViolation();
                    isValid = false;
                }
                return isValid;
            }

            if (usuarioRepositorio.buscarUsuario(usuarioDTO.getUsuario(), usuarioDTO.getIdUsuario())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Usuário Já Existe !!!")
                        .addPropertyNode("usuario").addConstraintViolation();
                isValid = false;
            }

        }
        return isValid;
    }
}