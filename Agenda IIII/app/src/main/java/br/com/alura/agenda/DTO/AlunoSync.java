package br.com.alura.agenda.DTO;

import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by Tiago on 13/02/2018.
 */

public class AlunoSync  {

private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }
}
