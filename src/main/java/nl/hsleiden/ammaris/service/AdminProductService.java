package nl.hsleiden.ammaris.service;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.dto.AdminProductCreateRequest;
import nl.hsleiden.ammaris.dto.AdminProductUpdateRequest;
import nl.hsleiden.ammaris.dto.ProductDto;
import nl.hsleiden.ammaris.entity.Product;
import nl.hsleiden.ammaris.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final ProductRepository products;

    // ✅ NEW: List all products (including archived)
    @Transactional(readOnly = true)
    public List<ProductDto> list() {
        return products.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ NEW: Get one product by id
    @Transactional(readOnly = true)
    public ProductDto get(Long id) {
        Product p = products.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return toDto(p);
    }

    @Transactional
    public ProductDto create(AdminProductCreateRequest req) {
        Product p = new Product();
        p.setName(req.getName());
        p.setPrice(req.getPrice());
        p.setStock(req.getStock());
        p.setArchived(false); // default new product is active
        p.setImageUrl(req.getImageUrl());
        p.setDescription(req.getDescription());
        p.setCategory(req.getCategory() != null
                ? parseCategory(req.getCategory())
                : Product.Category.UNISEX);

        Product saved = products.save(p);
        return toDto(saved);
    }

    @Transactional
    public ProductDto update(Long id, AdminProductUpdateRequest req) {
        Product p = products.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        p.setName(req.getName());
        p.setPrice(req.getPrice());
        p.setStock(req.getStock());
        p.setImageUrl(req.getImageUrl());
        p.setDescription(req.getDescription());
        if (req.getCategory() != null) {
            p.setCategory(parseCategory(req.getCategory()));
        }
        Product saved = products.save(p);
        return toDto(saved);
    }

    @Transactional
    public void archive(Long id) {
        Product p = products.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        p.setArchived(true);
        products.save(p);
    }

    @Transactional
    public void unarchive(Long id) {
        Product p = products.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        p.setArchived(false);
        products.save(p);
    }

    // --- helpers ---
    private ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());
        dto.setStock(p.getStock());
        dto.setImageUrl(p.getImageUrl());
        dto.setDescription(p.getDescription());
        dto.setCategory(p.getCategory() != null ? p.getCategory().name() : null);
        dto.setArchived(p.isArchived()); // ✅ include archive flag
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
