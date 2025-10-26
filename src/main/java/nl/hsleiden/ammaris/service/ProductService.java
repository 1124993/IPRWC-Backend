package nl.hsleiden.ammaris.service;

import nl.hsleiden.ammaris.dto.ProductDto;
import nl.hsleiden.ammaris.entity.Product;
import nl.hsleiden.ammaris.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /** Public listing: optionally filter by category (MEN/WOMEN/UNISEX). */
    @Transactional(readOnly = true)
    public List<ProductDto> listPublic(String categoryOpt) {
        List<Product> products;
        if (categoryOpt != null && !categoryOpt.isBlank()) {
            Product.Category cat = parseCategory(categoryOpt);
            products = repository.findByArchivedFalseAndCategory(cat);
        } else {
            products = repository.findByArchivedFalse();
        }
        return products.stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Optional<ProductDto> findOne(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    // ---------- mapping helpers ----------
    private ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());
        dto.setStock(p.getStock());
        dto.setImageUrl(p.getImageUrl());
        dto.setDescription(p.getDescription());
        dto.setCategory(p.getCategory() != null ? p.getCategory().name() : null);
        return dto;
    }

    private Product.Category parseCategory(String in) {
        try {
            return Product.Category.valueOf(in.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown category: " + in + " (use MEN, WOMEN, UNISEX)");
        }
    }
}
