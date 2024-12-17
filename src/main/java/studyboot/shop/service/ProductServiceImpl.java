package studyboot.shop.service;

import org.springframework.stereotype.Service;
import studyboot.shop.model.dto.ProductDTO;
import studyboot.shop.model.entity.Product;
import studyboot.shop.repository.ProductRepository;
import studyboot.shop.service.interfaces.ProductService;
import studyboot.shop.service.mapping.ProductMappingService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMappingService mappingService;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDto) {
        Product product = mappingService.mapDtoToEntity(productDto);
        product.setactive(true);
        return mappingService.mapEntityToDto(repository.save(product));
    }

    @Override
    public List<ProductDTO> getAllActiveProducts() {
        return repository.findAll().stream()
                .filter(Product::isactive)
                .map(mappingService::mapEntityToDto)
                .toList();
        // .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product == null || !product.isactive()) {
            return null;
        }
        return mappingService.mapEntityToDto(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO product) {
        return null;
    }

    @Override
    public ProductDTO deleteProduct(Long id) {
        return null;
    }

    @Override
    public ProductDTO deleteProductByTitle(String title) {
        return null;
    }

    @Override
    public ProductDTO restoreProductById(Long id) {
        return null;
    }

    @Override
    public long getProductsCount() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAveragePrice() {
        return null;
    }
}


