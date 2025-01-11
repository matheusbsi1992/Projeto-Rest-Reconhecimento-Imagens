    package br.com.projeto.estudos.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.mapstruct.Mapper;
    import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class UnidadeFilialPessoaLocalDTO {

        private String idPessoa;
        private String idConvenio;
        private String unidadeRepresentativa;
        private String nome;
        private String razaoSocial;
        private String documento;
        private String telefone;
        private String email;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        private Date dataCadastro;
        private String local;

        public UnidadeFilialPessoaLocalDTO(UnidadeFilialPessoaLocalDTO unidadeFilialPessoaLocalDTO) {
            this.idPessoa = unidadeFilialPessoaLocalDTO.getIdPessoa();
            this.idConvenio = unidadeFilialPessoaLocalDTO.getIdConvenio();
            this.unidadeRepresentativa = unidadeFilialPessoaLocalDTO.getUnidadeRepresentativa();
            this.nome = unidadeFilialPessoaLocalDTO.getNome();
            this.razaoSocial = unidadeFilialPessoaLocalDTO.getRazaoSocial();
            this.documento = unidadeFilialPessoaLocalDTO.getDocumento();
            this.telefone = unidadeFilialPessoaLocalDTO.getTelefone();
            this.email = unidadeFilialPessoaLocalDTO.getEmail();
            this.dataCadastro = unidadeFilialPessoaLocalDTO.getDataCadastro();
            this.local = unidadeFilialPessoaLocalDTO.getLocal();
        }

        /*public interface UnidadeFilialPessoaLocalProjection {
            String getIdPessoa();

            String getIdConvenio();

            String getUnidadeRepresentativa();

            String getNome();

            String getRazaoSocial();

            String getDocumento();

            String getTelefone();

            String getEmail();

            String getDataCadastro();

            String getLocal();
        }
*/
    }

