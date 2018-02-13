package br.com.alura.agenda.services;

import java.util.List;

import br.com.alura.agenda.DTO.AlunoSync;
import br.com.alura.agenda.modelo.Aluno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Tiago on 11/02/2018.
 */

public interface AlunoService {
    @POST("aluno")
    Call<Void> insere(@Body Aluno aluno);

    @GET("aluno")
    Call<AlunoSync> lista();
}
