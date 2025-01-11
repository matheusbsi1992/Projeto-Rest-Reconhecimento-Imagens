package br.com.projeto.estudos.modelo;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario", schema = "estudos")
@Data
public class Usuario implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false, columnDefinition = "CHAR(26) DEFAULT estudos.generate_ulid()")
    private String id_usuario;

   /* @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_unidade")
    private Unidade unidade;*/

    @Column(name = "nome_usuario")
    private String nome_usuario;

    @Column(name = "usuario", unique = true, nullable = false)
    private String usuario;

    @Column(name = "senha_usuario", nullable = false)
    private String senha_usuario;

    @Column(name = "fone_usuario")
    private String fone_usuario;

    @Column(name = "account_non_expired_usuario")
    private Boolean account_non_expired_usuario;

    @Column(name = "account_non_locked_usuario")
    private Boolean account_non_locked_usuario;

    @Column(name = "credentials_non_expired_usuario")
    private Boolean credentials_non_expired_usuario;

    @Column(name = "status_usuario")
    private Boolean status_usuario;

    @Column(name = "data_cadastro_usuario")
    private LocalDateTime data_cadastro_usuario;

    @Column(name = "data_atualizado_usuario")
    private LocalDateTime data_atualizado_usuario;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_uniao_permissao",
            schema = "estudos",
            joinColumns = {@JoinColumn(name = "id_usuario")},
            inverseJoinColumns = {@JoinColumn(name = "id_permissao")})
    private List<Permissao> permissaoList;


    public List<String> getRegras() {
        List<String> roles = new ArrayList<>();
        for (Permissao permissao : permissaoList) {
            roles.add(permissao.getNome_permissao());
        }
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissaoList;
    }

    @Override
    public String getPassword() {
        return this.senha_usuario;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.account_non_expired_usuario;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.account_non_locked_usuario;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentials_non_expired_usuario;
    }

    @Override
    public boolean isEnabled() {
        return this.status_usuario;
    }

    @PrePersist
    public void prePersist() {
        this.data_cadastro_usuario = LocalDateTime.now();  // Define a data de cadastro na criação
        this.status_usuario = Boolean.TRUE;  // Define o status do usuario como verdadeiro
        // Identificar credenciais como ativas
        this.account_non_expired_usuario = Boolean.TRUE;
        this.account_non_locked_usuario = Boolean.TRUE;
        this.credentials_non_expired_usuario = Boolean.TRUE;
    }

    @PreUpdate
    public void preUpdate() {
        this.data_atualizado_usuario = LocalDateTime.now();  // Atualiza a data de modificação ao atualizar
    }

}