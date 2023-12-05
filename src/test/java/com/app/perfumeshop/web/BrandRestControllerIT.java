package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.brand.BrandViewDTO;
import com.app.perfumeshop.service.BrandsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BrandRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandsService brandsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetBrands() throws Exception {
        List<BrandViewDTO> mockBrands = Collections.singletonList(new BrandViewDTO());
        when(brandsService.getAllBrands()).thenReturn(mockBrands);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/all-brands")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mockBrands)));
    }
}
