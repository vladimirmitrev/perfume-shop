package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.brand.BrandViewDTO;
import com.app.perfumeshop.service.BrandsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BrandRestController {

    private final BrandsService brandsService;

    public BrandRestController(BrandsService brandsService) {
        this.brandsService = brandsService;
    }
    @GetMapping("/all-brands")
    public ResponseEntity<List<BrandViewDTO>> getBrands() {

        List<BrandViewDTO> allBrands = brandsService.getAllBrands();

        return ResponseEntity.ok(allBrands);
    }
}
