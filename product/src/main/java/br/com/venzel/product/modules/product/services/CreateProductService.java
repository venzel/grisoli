package br.com.venzel.product.modules.product.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.venzel.product.modules.category.exceptions.CategoryNotFoundException;
import br.com.venzel.product.modules.category.models.Category;
import br.com.venzel.product.modules.category.repositories.CategoryRepository;
import br.com.venzel.product.modules.category.utils.CategoryMessageUtil;
import br.com.venzel.product.modules.product.dtos.ProductStockDTO;
import br.com.venzel.product.modules.product.dtos.RequestCreateProductDTO;
import br.com.venzel.product.modules.product.dtos.ResponseProductDTO;
import br.com.venzel.product.modules.product.exceptions.ProductAlreadyExistsException;
import br.com.venzel.product.modules.product.mappers.ProductMapper;
import br.com.venzel.product.modules.product.models.Product;
import br.com.venzel.product.modules.product.repositories.ProductRepository;
import br.com.venzel.product.modules.product.utils.ProductMessageUtil;
import br.com.venzel.product.modules.supplier.exceptions.SupplierNotFoundException;
import br.com.venzel.product.modules.supplier.models.Supplier;
import br.com.venzel.product.modules.supplier.repositories.SupplierRepository;
import br.com.venzel.product.modules.supplier.utils.SupplierMessageUtil;

@Service
public class CreateProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;
    
    @Autowired
    private ProductMapper productMapper;

    @Transactional
    public ResponseProductDTO execute(RequestCreateProductDTO req) {

        /* Verify product existence with name */

        Boolean optionalEntity = productRepository.existsByName(req.getName());

        /*  Guard strategy */

        if (optionalEntity) {
            throw new ProductAlreadyExistsException(ProductMessageUtil.PRODUCT_ALREADY_EXISTS);
        }

        /* Find category by id */

        Optional<Category> existsCategory = categoryRepository.findOneById(req.getCategoryId());

        /*  Guard strategy */

        if (existsCategory.isEmpty()) {
            throw new CategoryNotFoundException(CategoryMessageUtil.CATEGORY_NOT_FOUND);
        }

        /* Find supplier by id */

        Optional<Supplier> existsSupplier = supplierRepository.findOneById(req.getSupplierId());

        /*  Guard strategy */

        if (existsSupplier.isEmpty()) {
            throw new SupplierNotFoundException(SupplierMessageUtil.SUPPLIER_NOT_FOUND);
        }

        /* Create object */

        Product product = Product.create(req.getName(), req.getQuantityAvailable(), existsCategory.get(), existsSupplier.get());

        /* Save data in repository */

        Product productSaved = productRepository.save(product);

        /* Parse entity to dto and return */
        
        ResponseProductDTO productDTO = productMapper.toDTO(productSaved);
        
        /* Return product dto */

        return productDTO;
    }

    public void updateProductStock(ProductStockDTO productStockDTO) {}
}
