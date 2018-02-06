package com.expriceit.maserven.entities;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by stalyn on 4/12/2017.
 */

public interface ProductoServicios {
   /* @GET("ConsultaItemsWS")
    void getProductos(Callback<List<Productos>> Callback);
    tipo=DES&cod_int=&cod_alt=&descripcion=kit&linea=*/
    @GET("ConsultaItemsWS")
    Call<ApiResponseProductos> getAppointments(@Query("tipo") String tipo,
                                               @Query("cod_int") String cod_interno,
                                               @Query("cod_alt") String cod_alterno,
                                               @Query("descripcion") String descripcion,
                                               @Query("linea") String linea);
}
