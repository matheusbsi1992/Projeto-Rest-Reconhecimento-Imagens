package br.com.projeto.estudos.validacao.validadorUnidade;

import br.com.projeto.estudos.dto.UnidadeDTO;
import br.com.projeto.estudos.repositorio.UnidadeRepositorio;
import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

//@Component
public class UnidadeValidadora implements ConstraintValidator<ValidarErrorUnidade, UnidadeDTO> {

    UnidadeRepositorio unidadeRepositorio;

    UsuarioAutenticado usuarioAutenticado;

    @Autowired
    public UnidadeValidadora(UnidadeRepositorio unidadeRepositorio, UsuarioAutenticado usuarioAutenticado) {
        Objects.requireNonNull(unidadeRepositorio);
        Objects.requireNonNull(usuarioAutenticado);
        this.usuarioAutenticado = usuarioAutenticado;
        this.unidadeRepositorio = unidadeRepositorio;
    }

    public boolean isValid(UnidadeDTO unidadeDTO, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        boolean isValid = true;

        //---Dados para outras integracoes que nao seja atualizacao
        if (usuarioAutenticado.temPermissao("ROLE_ADMIN")) {
            System.out.println("UNIDADE --->>>" + unidadeDTO.getIdUnidade());
            if (unidadeDTO.getIdUnidade() == null || unidadeDTO.getIdUnidade().length()==0) {
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

            //---Dados para outras integracoes que nao seja adicao/insercao
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

        } else {
            return isValid;
        }
        return isValid;
    }

}