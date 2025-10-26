package nl.hsleiden.ammaris.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.dto.AdminProductCreateRequest;
import nl.hsleiden.ammaris.dto.AdminProductUpdateRequest;
import nl.hsleiden.ammaris.dto.ProductDto;
import nl.hsleiden.ammaris.service.AdminProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    // NEW: list all products (including archived)
    @GetMapping
    public ResponseEntity<List<ProductDto>> list() {
        List<ProductDto> items = adminProductService.list();
        return ResponseEntity.ok(items);
    }

    // NEW: get one by id (admin view)
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        ProductDto dto = adminProductService.get(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody AdminProductCreateRequest body) {
        ProductDto created = adminProductService.create(body);
        return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id,
                                             @Valid @RequestBody AdminProductUpdateRequest body) {
        ProductDto updated = adminProductService.update(id, body);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<Void> archive(@PathVariable Long id) {
        adminProductService.archive(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/unarchive")
    public ResponseEntity<Void> unarchive(@PathVariable Long id) {
        adminProductService.unarchive(id);
        return ResponseEntity.noContent().build();
    }
}
