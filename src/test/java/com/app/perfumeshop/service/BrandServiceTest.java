package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.brand.BrandViewDTO;
import com.app.perfumeshop.model.entity.Brand;
import com.app.perfumeshop.model.entity.Category;
import com.app.perfumeshop.model.entity.Product;
import com.app.perfumeshop.model.enums.SizeEnum;
import com.app.perfumeshop.repository.BrandRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandsService brandsService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        brandsService = new BrandsService(brandRepository, modelMapper);
    }

    @AfterEach
    void tearDown() {
        brandRepository.deleteAll();
    }

    @Test
    public void testGetAllBrands() {
        Brand brand1 = createTestBrand(1L, "testBrand1");
        Brand brand2 = createTestBrand(2L, "testBrand2");
        BrandViewDTO brandViewDTO1 = createTestViewBrandDTO("testBrandViewDTO1");
        BrandViewDTO brandViewDTO2 = createTestViewBrandDTO("testBrandViewDTO2");

        List<Brand> mockBrands = Arrays.asList(brand1, brand2);

        List<BrandViewDTO> mockBrandDTOs = Arrays.asList(brandViewDTO1, brandViewDTO2);

        when(brandRepository.findAll()).thenReturn(mockBrands);

        List<BrandViewDTO> result = brandsService.getAllBrands();

        verify(brandRepository, times(1)).findAll();

    }

    @Test
    public void testGetBrandNameById() {
        Long brandId = 1L;
        String expectedBrandName = "TestBrandName";

        Brand mockBrand = createTestBrand(brandId, expectedBrandName);

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(mockBrand));

        String result = brandsService.getBrandNameById(brandId);

        verify(brandRepository, times(1)).findById(brandId);

        assertEquals(expectedBrandName, result);
    }

    private Brand createTestBrand(Long id, String name) {
        Brand brand = new Brand();
        brand.setId(id);
        brand.setName(name);
        return brand;
    }

    private BrandViewDTO createTestViewBrandDTO(String name) {
        BrandViewDTO brandViewDTO = new BrandViewDTO();
        brandViewDTO.setId(1L);
        brandViewDTO.setName(name);
        return brandViewDTO;
    }
}
