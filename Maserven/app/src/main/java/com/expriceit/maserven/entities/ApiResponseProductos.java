package com.expriceit.maserven.entities;

import java.util.List;

/**
 * Created by stalyn on 5/12/2017.
 */

public class ApiResponseProductos {
    private List<ProductosDisplayList> results;

    public ApiResponseProductos(List<ProductosDisplayList> results) {
        this.results = results;
    }

    public List<ProductosDisplayList> getResults() {
        return results;
    }
}
