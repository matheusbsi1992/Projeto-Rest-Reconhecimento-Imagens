package br.com.projeto.estudos.validacao.validadorServico;

import br.com.projeto.estudos.dto.ServicoDTO;
import br.com.projeto.estudos.dto.UnidadeDTO;
import br.com.projeto.estudos.repositorio.ServicoRepositorio;
import br.com.projeto.estudos.repositorio.UnidadeRepositorio;
import br.com.projeto.estudos.seguranca.jwt.UsuarioAutenticado;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

//@Component
public class ServicoValidadora implements ConstraintValidator<ValidarErrorServico, ServicoDTO> {

    ServicoRepositorio servicoRepositorio;

    UsuarioAutenticado usuarioAutenticado;

    @Autowired
    public ServicoValidadora(ServicoRepositorio servicoRepositorio, UsuarioAutenticado usuarioAutenticado) {
        Objects.requireNonNull(servicoRepositorio);
        Objects.requireNonNull(usuarioAutenticado);
        this.usuarioAutenticado = usuarioAutenticado;
        this.servicoRepositorio = servicoRepositorio;
    }

    @Override
    public boolean isValid(ServicoDTO servicoDTO, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        boolean isValid = true;

        //---Dados para outras integracoes que nao seja atualizacao
        if (usuarioAutenticado.getUsuarioLogado().getRegras().contains("ROLE_ADMIN")) {
            System.out.println("servico --->>>" + servicoDTO.getIdServico());
            if (servicoDTO.getIdServico() == null || servicoDTO.getIdServico() == 0) {
                if (servicoRepositorio.buscarNomeServico(servicoDTO.getNomeServico().toUpperCase())) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Nome de Serviço Já Existe!!!")
                            .addPropertyNode("nomeServico").addConstraintViolation();
                    isValid = false;
                }
                return isValid;
            }

            //---Dados para outras integracoes que nao seja adicao/insercao
            //--- Verificar se o nome de serviço já está sendo usado por outro serviço (excluindo o próprio Serviço)
            if (servicoRepositorio.buscarNomeServico(servicoDTO.getNomeServico().toUpperCase(), servicoDTO.getIdServico())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Nome de Serviço Já Existe!!!")
                        .addPropertyNode("nomeServico").addConstraintViolation();
                isValid = false;
            }

        } else {
            return isValid;
        }
        return isValid;
    }

}