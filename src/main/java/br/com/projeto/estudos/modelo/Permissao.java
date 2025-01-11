package br.com.projeto.estudos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;


@Entity
@Table(name = "permissao", schema = "estudos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permissao implements GrantedAuthority, Serializable {

    @Id
    @Column(name = "id_permissao", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_permissao;

    @Column(name = "nome_permissao")
    private String nome_permissao;

    @Override
    public String getAuthority() {
        return nome_permissao;
    }

    public Permissao(String nome_permissao){
        this.nome_permissao = nome_permissao;
    }

    public Permissao(Long id_permissao){
        this.id_permissao = id_permissao;
    }
}