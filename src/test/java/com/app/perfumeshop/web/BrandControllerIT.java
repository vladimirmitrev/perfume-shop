package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.service.BrandsService;
import com.app.perfumeshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BrandControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @MockBean
    private BrandsService brandsService;

    @Test
    public void testViewBrandsPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brands"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("brands"));
    }

    @Test
    public void testGetAllProductsFromThisBrand() throws Exception {

        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductViewDTO> mockProductPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(productService.getAllProductByBrandId(anyLong(), any(Pageable.class))).thenReturn(mockProductPage);
        when(brandsService.getBrandNameById(anyLong())).thenReturn("MockBrand");

        mockMvc.perform(MockMvcRequestBuilders.get("/brand-products/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("brand-products"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products", "brand"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", mockProductPage))
                .andExpect(MockMvcResultMatchers.model().attribute("brand", "MockBrand"));
    }


}
