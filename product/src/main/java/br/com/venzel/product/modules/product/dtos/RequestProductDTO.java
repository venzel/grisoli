package br.com.venzel.product.modules.product.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class RequestProductDTO {
    
    @NotBlank
    private String name;

    @NotNull
    private Integer categoryId;

    @NotNull
    private Integer supplierId;

    @NotNull
    private Integer quantityAvailable;
}
