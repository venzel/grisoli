package br.com.venzel.product.modules.product.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.venzel.product.modules.product.dtos.ProductDTO;
import br.com.venzel.product.modules.product.dtos.CreateProductDTO;
import br.com.venzel.product.modules.product.dtos.ListProductDTO;
import br.com.venzel.product.modules.product.dtos.UpdateProductDTO;
import br.com.venzel.product.modules.product.models.Product;

@Component
public class ProductMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public ListProductDTO toListDTO(Product product) {
        return modelMapper.map(product, ListProductDTO.class);
    }

    public List<ListProductDTO> toCollectionModel(List<Product> products) {
        return products.stream()
                    .map(e -> toListDTO(e))
                    .collect(Collectors.toList());
    }

    public Page<ListProductDTO> toCollectionPageModel(Page<Product> products) {
        return products.map(e -> toListDTO(e));
    }

    public Product toEntity(CreateProductDTO dto) {
        return modelMapper.map(dto, Product.class);
    }

    public void toCopyEntity(UpdateProductDTO dto, Product product) {
        modelMapper.map(dto, product);
    }
}