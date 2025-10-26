package nl.hsleiden.ammaris.controller;

import nl.hsleiden.ammaris.dto.ProductDto;
import nl.hsleiden.ammaris.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Public endpoints: list + details.
 * Now supports ?category=MEN|WOMEN|UNISEX
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<ProductDto>> list(@RequestParam(value = "category", required = false) String category) {
        return ResponseEntity.ok(service.listPublic(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> one(@PathVariable Long id) {
        return service.findOne(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
