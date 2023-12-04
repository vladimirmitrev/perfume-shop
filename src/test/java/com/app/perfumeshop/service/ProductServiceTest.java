package com.app.perfumeshop.service;

import com.app.perfumeshop.model.dto.product.AddProductDTO;
import com.app.perfumeshop.model.dto.product.EditProductDTO;
import com.app.perfumeshop.model.dto.product.ProductViewDTO;
import com.app.perfumeshop.model.entity.Brand;
import com.app.perfumeshop.model.entity.Category;
import com.app.perfumeshop.model.entity.Product;
import com.app.perfumeshop.model.enums.CategoryNameEnum;
import com.app.perfumeshop.model.enums.SizeEnum;
import com.app.perfumeshop.model.mapper.ProductMapper;
import com.app.perfumeshop.repository.BrandRepository;
import com.app.perfumeshop.repository.CategoryRepository;
import com.app.perfumeshop.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductService productService;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, cloudinaryService,
                modelMapper, brandRepository,
               categoryRepository, productMapper);
    }
        @AfterEach
    void tearDown() {
        productRepository.deleteAll();
//        testDataUtils.cleanUpDatabase();
    }
    @Test
    void testGetAllProducts() {

        List<Product> productList = new ArrayList<>();
        productList.add(createTestProduct("Product1"));
        productList.add(createTestProduct("Product2"));

        PageImpl<Product> pageImpl = new PageImpl<>(productList);

        when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(pageImpl);

        Page<ProductViewDTO> result = productService.getAllProducts(mock(Pageable.class));

        assertEquals(productList.size(), result.getContent().size());

    }
    @Test
    void testAddProduct() throws IOException {
        AddProductDTO addProductDTO = new AddProductDTO();
        addProductDTO.setName("TestProduct");
        addProductDTO.setBrand("TestBrand");
        addProductDTO.setCategory(CategoryNameEnum.MEN);
        addProductDTO.setDescription("TestDescription");
        addProductDTO.setMilliliters(SizeEnum.HUNDRED);
        addProductDTO.setPrice(BigDecimal.TEN);

        MultipartFile photo = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", "test".getBytes());
        addProductDTO.setPhoto(photo);

        Brand brand = new Brand();
        brand.setName("TestBrand");

        Category category = new Category();
        category.setName(CategoryNameEnum.MEN);

        Product product = createTestProduct("addProductTest");

        when(brandRepository.findByName("TestBrand")).thenReturn(brand);
        when(categoryRepository.findByName(CategoryNameEnum.MEN)).thenReturn(category);
        when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

        productService.addProduct(addProductDTO);


        verify(brandRepository, times(1)).findByName("TestBrand");
        verify(categoryRepository, times(1)).findByName(CategoryNameEnum.MEN);
        verify(productRepository).save(ArgumentMatchers.any());
    }
    @Test
    public void testEditProduct() throws IOException {

        EditProductDTO editProductDTO = new EditProductDTO();
        editProductDTO.setBrand("TestBrand");
        editProductDTO.setCategory(CategoryNameEnum.WOMEN);
        editProductDTO.setName("EditedProduct");
        editProductDTO.setDescription("EditedDescription");
        editProductDTO.setMilliliters(SizeEnum.HUNDRED);
        editProductDTO.setPrice(BigDecimal.valueOf(50));
        editProductDTO.setImageUrl("editedImageUrl");

        Long productId = 1L;

        Brand brand = new Brand();
        brand.setName("TestBrand");

        Category category = new Category();
        category.setName(CategoryNameEnum.WOMEN);

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setBrand(brand);
        existingProduct.setCategory(category);
        existingProduct.setName("OriginalProduct");
        existingProduct.setDescription("OriginalDescription");
        existingProduct.setMilliliters(SizeEnum.FIFTY);
        existingProduct.setPrice(BigDecimal.valueOf(25));
        existingProduct.setImageUrl("originalImageUrl");

        when(brandRepository.findByName("TestBrand")).thenReturn(brand);
        when(categoryRepository.findByName(CategoryNameEnum.WOMEN)).thenReturn(category);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.editProduct(editProductDTO, productId);

        verify(brandRepository, times(1)).findByName("TestBrand");
        verify(categoryRepository, times(1)).findByName(CategoryNameEnum.WOMEN);

        assertEquals("EditedProduct", existingProduct.getName());
        assertEquals("EditedDescription", existingProduct.getDescription());
        assertEquals(SizeEnum.HUNDRED, existingProduct.getMilliliters());
        assertEquals(BigDecimal.valueOf(50), existingProduct.getPrice());
        assertEquals("editedImageUrl", existingProduct.getImageUrl());
    }

    @Test
    public void testFindProductById() {
        Long productId = 1L;

        Brand brand = new Brand();
        brand.setName("TestBrand");

        Category category = new Category();
        category.setName(CategoryNameEnum.WOMEN);

        Product product = new Product();
        product.setId(productId);
        product.setBrand(brand);
        product.setCategory(category);
        product.setName("TestProduct");
        product.setDescription("TestDescription");
        product.setMilliliters(SizeEnum.FIFTY);
        product.setPrice(BigDecimal.valueOf(25));
        product.setImageUrl("testImageUrl");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<ProductViewDTO> result = productService.findProductById(productId);


        verify(productRepository, times(1)).findById(productId);

        assertTrue(result.isPresent());
        ProductViewDTO productViewDTO = result.get();
        assertEquals("TestBrand", productViewDTO.getBrand());
        assertEquals("TestProduct", productViewDTO.getName());
        assertEquals(CategoryNameEnum.WOMEN, productViewDTO.getCategory());
        assertEquals("TestDescription", productViewDTO.getDescription());
        assertEquals(BigDecimal.valueOf(25), productViewDTO.getPrice());
        assertEquals(SizeEnum.FIFTY, productViewDTO.getMilliliters());
        assertEquals("testImageUrl", productViewDTO.getImageUrl());
    }
    @Test
    public void testDeleteProductById() {

        Long productId = 1L;

        productService.deleteProductById(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product mockProduct = new Product();

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Product result = productService.getProductById(productId);

        verify(productRepository, times(1)).findById(productId);

        assertEquals(mockProduct, result);
    }
    @Test
    public void testGetAllProductByBrandId() {
        Long brandId = 1L;
        Pageable pageable = PageRequest.of(0, 10); // Example Pageable

        Product mockProduct = new Product();
        ProductViewDTO mockProductDTO = new ProductViewDTO();
        Page<Product> mockProductPage = new PageImpl<>(List.of(mockProduct));

        when(productRepository.findProductsByBrand_Id(eq(brandId), eq(pageable))).thenReturn(mockProductPage);
        when(productMapper.productEntityToProductViewDTO(mockProduct)).thenReturn(mockProductDTO);

        Page<ProductViewDTO> resultPage = productService.getAllProductByBrandId(brandId, pageable);

        verify(productRepository, times(1)).findProductsByBrand_Id(eq(brandId), eq(pageable));
        verify(productMapper, times(1)).productEntityToProductViewDTO(mockProduct);

        assertEquals(1, resultPage.getTotalElements());
        assertEquals(mockProductDTO, resultPage.getContent().get(0));
    }
    @Test
    public void testSearchProducts() {
        String keyword = "test";
        Pageable pageable = PageRequest.of(0, 10);
        Product testSearchProd1 = createTestProduct("test1Search");
        Product testSearchProd2 = createTestProduct("test2Search");
        ProductViewDTO mockProductDTO1 = createTestProductViewDTO("test1SearchDTO");
        ProductViewDTO mockProductDTO2 = createTestProductViewDTO("tes2SearchDTO");

        List<Product> mockProducts = Arrays.asList(testSearchProd1, testSearchProd2);

        List<ProductViewDTO> mockProductDTOs = Arrays.asList(mockProductDTO1,mockProductDTO2);

        when(productRepository.searchProductsByBrandOrNameOrDescription(keyword, pageable)).thenReturn(new PageImpl<>(mockProducts));

        when(productMapper.productEntityToProductViewDTO(testSearchProd1)).thenReturn(mockProductDTO1);

        Page<ProductViewDTO> result = productService.searchProducts(keyword, pageable);

        verify(productRepository, times(1)).searchProductsByBrandOrNameOrDescription(keyword, pageable);
        verify(productMapper, times(1)).productEntityToProductViewDTO(testSearchProd1);
    }

    private Product createTestProduct(String name) {
        Product product = new Product();
        product.setId(1L);
        product.setName(name);
        product.setBrand(new Brand());
        product.setCategory(new Category());
        product.setMilliliters(SizeEnum.HUNDRED);
        product.setImageUrl("testImageUrl");
        product.setDescription("testDescription");
        product.setPrice(BigDecimal.TEN);
        return product;
    }
    private ProductViewDTO createTestProductViewDTO(String name) {
        ProductViewDTO productViewDTO = new ProductViewDTO();
        productViewDTO.setId(1L);
        productViewDTO.setName(name);
        productViewDTO.setBrand("testBrand");
        productViewDTO.setCategory(CategoryNameEnum.WOMEN);
        productViewDTO.setMilliliters(SizeEnum.HUNDRED);
        productViewDTO.setImageUrl("testImageUrl");
        productViewDTO.setDescription("testDescription");
        productViewDTO.setPrice(BigDecimal.TEN);

        return productViewDTO;
    }
}
