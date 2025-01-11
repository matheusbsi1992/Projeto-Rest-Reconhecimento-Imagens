package br.com.projeto.estudos.validacao.validadorFilial;

import br.com.projeto.estudos.dto.FilialDTO;
import br.com.projeto.estudos.repositorio.FilialRepositorio;
import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

//@Component
public class FilialValidadora implements ConstraintValidator<ValidarErrorFilial, FilialDTO> {

    FilialRepositorio filialRepositorio;

    UsuarioAutenticado usuarioAutenticado;

    //PessoaDTO pessoaDTO;

    @Autowired
    public FilialValidadora(FilialRepositorio filialRepositorio, UsuarioAutenticado usuarioAutenticado) {//, PessoaDTO pessoaDTO) {
        Objects.requireNonNull(filialRepositorio);
        Objects.requireNonNull(usuarioAutenticado);
        //  Objects.requireNonNull(pessoaDTO);
        this.usuarioAutenticado = usuarioAutenticado;
        this.filialRepositorio = filialRepositorio;
        // this.pessoaDTO = new PessoaDTO();
    }

    public boolean isValid(FilialDTO filialDTO, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        boolean isValid = true;

        //---Dados para outras integracoes que nao seja atualizacao
        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN") ){//&& pessoaDTO.getLocalTipo().equalsIgnoreCase("FILIAL")) {
            System.out.println("Filial --->>>" + filialDTO.getIdFilial());
            if (filialDTO.getIdFilial() == null || filialDTO.getIdFilial().length()==0) {
                if (filialRepositorio.buscarNomeFantasiaFilial(filialDTO.getNomeFantasiaFilial())) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Nome de Fantasia da Filial Já Existe!!!")
                            .addPropertyNode("nomeFantasiaFilial").addConstraintViolation();
                    isValid = false;
                }
                if (filialRepositorio.buscarRazaoSocialFilial(filialDTO.getRazaoSocialFilial())) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Razão Social da Filial Já Cadastrada !!!")
                            .addPropertyNode("razaoSocialFilial").addConstraintViolation();
                    isValid = false;
                }
                if (filialRepositorio.buscarDocumentoFilial(filialDTO.getDocumentoFilial())) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Documento da Filial Já Cadastrado !!!")
                            .addPropertyNode("documentoFilial").addConstraintViolation();
                    isValid = false;
                }
                if (filialRepositorio.buscarEmailFilial(filialDTO.getEmailFilial())) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Email da Filial Já Cadastrado !!!")
                            .addPropertyNode("emailFilial").addConstraintViolation();
                    isValid = false;
                }
                return isValid;
            }

            //---Dados para outras integracoes que nao seja adicao/insercao
            //--- Verificar se o nome fantasia já está sendo usado por outra Filial (excluindo a própria Filial)
            if (filialRepositorio.buscarNomeFantasiaFilial(filialDTO.getNomeFantasiaFilial(), filialDTO.getIdFilial())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Nome de Fantasia da Filial Já Existe!!!")
                        .addPropertyNode("nomeFantasiaFilial").addConstraintViolation();
                isValid = false;
            }

            //--- Verificar se a razão social já está sendo usada por outra Filial (excluindo a própria Filial)
            if (filialRepositorio.buscarRazaoSocialFilial(filialDTO.getRazaoSocialFilial(), filialDTO.getIdFilial())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Razão Social da Filial Já Cadastrada !!!")
                        .addPropertyNode("razaoSocialFilial").addConstraintViolation();
                isValid = false;
            }
            //--- Verificar se o documento (CPF/CNPJ) já está sendo usado por outra Filial (excluindo a própria Filial)
            if (filialRepositorio.buscarDocumentoFilial(filialDTO.getDocumentoFilial(), filialDTO.getIdFilial())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Documento da Filial Já Cadastrado !!!")
                        .addPropertyNode("documentoFilial").addConstraintViolation();
                isValid = false;
            }
            if (filialRepositorio.buscarEmailFilial(filialDTO.getEmailFilial(), filialDTO.getIdFilial())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Email da Filial Já Cadastrado !!!")
                        .addPropertyNode("emailFilial").addConstraintViolation();
                isValid = false;
            }
        } else {
            return isValid;
        }
        return isValid;
    }

}