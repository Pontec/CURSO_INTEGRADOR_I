package com.utp.viacosta.agregates.retrofit;

import com.utp.viacosta.agregates.respuesta.SunatRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SunatService {
    @GET("/v2/sunat/ruc")
    Call<SunatRespuesta> getDatosEmpresa(@Header("Authorization") String token, @Query("numero") String numero);
}
