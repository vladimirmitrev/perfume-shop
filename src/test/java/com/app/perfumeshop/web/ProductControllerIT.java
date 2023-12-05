package com.app.perfumeshop.web;

import com.app.perfumeshop.model.dto.product.AddProductDTO;
import com.app.perfumeshop.model.dto.product.EditProductDTO;
import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.model.entity.Product;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.enums.CategoryNameEnum;
import com.app.perfumeshop.model.enums.SizeEnum;
import com.app.perfumeshop.repository.ProductRepository;
import com.app.perfumeshop.service.BrandsService;
import com.app.perfumeshop.service.ProductService;
import com.app.perfumeshop.service.ShoppingCartService;
import com.app.perfumeshop.service.UserService;
import com.app.perfumeshop.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private UserService userService;
    @MockBean
    private BrandsService brandsService;
    @MockBean
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    public void testGetAllProducts() throws Exception {

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("test@test.com");

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@test.com");
        ShoppingCart mockShoppingCart = new ShoppingCart();
        mockShoppingCart.setTotalItems(5);
        Page<ProductViewDTO> mockProducts = new PageImpl<>(Collections.emptyList());

        Mockito.when(productService.getAllProducts(Mockito.any(Pageable.class))).thenReturn(mockProducts);
        Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(mockUser);
        Mockito.when(shoppingCartService.findByUserId(Mockito.anyLong())).thenReturn(mockShoppingCart);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/all")
                        .principal(mockPrincipal))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("products"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("emptyShop", "Shop is empty"));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"ADMIN", "EMPLOYEE"})
    public void testAddProductGet() throws Exception {
        Mockito.when(brandsService.getAllBrands()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/products/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product-add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("addProductModel"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("brands"));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"ADMIN", "EMPLOYEE"})
    public void testAddProductPost() throws Exception {
        Mockito.doNothing().when(productService).addProduct(Mockito.any(AddProductDTO.class));
//        MockMultipartFile mockPhoto = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", "test".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                        .param("name", "Test Product")
                        .param("brand", "Test Brand")
                        .param("category", "MEN")
                        .param("milliliters", "FIFTY")
                        .param("description", "Test Description")
                        .param("price", "100")
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products/all"));
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"ADMIN", "EMPLOYEE"})
    public void testAddProductPostUnsuccessful() throws Exception {
        Mockito.doNothing().when(productService).addProduct(Mockito.any(AddProductDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                                .param("name", "Test Product")
                                .param("brand", "Test Brand")
                                .param("category", "MEN")
//                        .param("milliliters", "FIFTY")
                                .param("description", "Test Description")
                                .param("price", "100")
                                .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products/add"));
    }

    @Test
    public void testGetProductDetails() throws Exception {
        Long productId = 1L;
        ProductViewDTO productViewDTO = createProductViewDTO(productId);

        Mockito.when(productService.findProductById(productId)).thenReturn(Optional.of(productViewDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/details/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product-details"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", productViewDTO));
    }

    @Test
    public void testGetProductDetailsNotFound() throws Exception {
        Long productId = 1L;
        ProductViewDTO productViewDTO = createProductViewDTO(productId);

        Mockito.when(productService.findProductById(productId)).thenReturn(Optional.of(productViewDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/details/{id}", 2L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"ADMIN", "EMPLOYEE"})
    public void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.post("/products/delete/{id}", productId)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products/all"))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(productService, Mockito.times(1)).deleteProductById(productId);
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"ADMIN", "EMPLOYEE"})
    public void testEditProductGet() throws Exception {
        Long productId = 1L;
        ProductViewDTO productViewDTO = createProductViewDTO(productId);

        Mockito.when(productService.findProductById(productId)).thenReturn(java.util.Optional.of(productViewDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/edit/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product-edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("brands", "editProductModel"))
                .andExpect(MockMvcResultMatchers.model().attribute("editProductModel", productViewDTO))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"ADMIN", "EMPLOYEE"})
    public void testEditProductGetNotFound() throws Exception {
        Long productId = 1L;
        ProductViewDTO productViewDTO = createProductViewDTO(productId);

        Mockito.when(productService.findProductById(productId)).thenReturn(java.util.Optional.of(productViewDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/edit/{id}", 2L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"ADMIN", "EMPLOYEE"})
    public void testEditProductPost() throws Exception {
        Long productId = 1L;
        EditProductDTO editProductDTO = new EditProductDTO();

        ProductViewDTO productViewDTO = new ProductViewDTO();
        Mockito.when(productService.findProductById(productId)).thenReturn(java.util.Optional.of(productViewDTO));

        Mockito.doNothing().when(productService).editProduct(editProductDTO, productId);

        mockMvc.perform(MockMvcRequestBuilders.patch("/products/edit/{id}", productId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Test Product")
                        .param("brand", "Test Brand")
                        .param("category", "MEN")
                        .param("milliliters", "FIFTY")
                        .param("description", "Test Description")
                        .param("imageUrl", "imageUrl.com")
                        .param("price", "100")
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/products/all"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"ADMIN", "EMPLOYEE"})
    public void testEditProductPostBindingResult() throws Exception {
        Long productId = 1L;
        EditProductDTO editProductDTO = new EditProductDTO();

        ProductViewDTO productViewDTO = new ProductViewDTO();
        Mockito.when(productService.findProductById(productId)).thenReturn(java.util.Optional.of(productViewDTO));

        Mockito.doNothing().when(productService).editProduct(editProductDTO, productId);

        mockMvc.perform(MockMvcRequestBuilders.patch("/products/edit/{id}", productId)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("name", "Test Product")
                                .param("brand", "Test Brand")
                                .param("category", "MEN")
//                        .param("milliliters", "FIFTY")
                                .param("description", "Test Description")
                                .param("imageUrl", "imageUrl.com")
                                .param("price", "100")
                                .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/products/edit/{id}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "test@test.com", roles = {"ADMIN", "EMPLOYEE"})
    public void testEditProductPostNotFound() throws Exception {
        Long productId = 1L;
        EditProductDTO editProductDTO = new EditProductDTO();

        ProductViewDTO productViewDTO = new ProductViewDTO();
        Mockito.when(productService.findProductById(productId)).thenReturn(java.util.Optional.of(productViewDTO));

        Mockito.doNothing().when(productService).editProduct(editProductDTO, productId);

        mockMvc.perform(MockMvcRequestBuilders.patch("/products/edit/{id}", 2L)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Test Product")
                        .param("brand", "Test Brand")
                        .param("category", "MEN")
                        .param("milliliters", "FIFTY")
                        .param("description", "Test Description")
                        .param("imageUrl", "imageUrl.com")
                        .param("price", "100")
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testSearchGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("search"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("noProductsFound"))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testSearchPost() throws Exception {
        String query = "Test Product";
        PageRequest pageable = PageRequest.of(0, 10);
        ProductViewDTO productViewDTO1 = createProductViewDTO(1L);
        ProductViewDTO productViewDTO2 = createProductViewDTO(2L);
        List<ProductViewDTO> searchResults = List.of(productViewDTO1, productViewDTO2);

        PageImpl<ProductViewDTO> mockPage = new PageImpl<>(searchResults, pageable, searchResults.size());
        Mockito.when(productService.searchProducts(query, pageable)).thenReturn(mockPage);


        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                        .param("query", query)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("search"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("noProductsFound"))
                .andDo(MockMvcResultHandlers.print());
    }

    private ProductViewDTO createProductViewDTO(Long productId) {
        BigDecimal productPrice = BigDecimal.valueOf(50.0);
        ProductViewDTO productViewDTO = new ProductViewDTO();
        productViewDTO.setId(productId)
                .setBrand("Test Brand")
                .setName("Test Product")
                .setPrice(productPrice)
                .setCategory(CategoryNameEnum.MEN)
                .setImageUrl("image.jpg")
                .setDescription("Test Description")
                .setMilliliters(SizeEnum.FIFTY);

        return productViewDTO;
    }
}
