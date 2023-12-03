package com.app.perfumeshop.util;

import com.app.perfumeshop.model.entity.*;
import com.app.perfumeshop.model.enums.CategoryNameEnum;
import com.app.perfumeshop.model.enums.OrderStatusEnum;
import com.app.perfumeshop.model.enums.SizeEnum;
import com.app.perfumeshop.model.enums.UserRoleEnum;
import com.app.perfumeshop.repository.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Component
public class TestDataUtils {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderDetailRepository orderDetailRepository;


    public TestDataUtils(UserRepository userRepository, UserRoleRepository userRoleRepository, ProductRepository productRepository, BrandRepository brandRepository, ShoppingCartRepository shoppingCartRepository, CategoryRepository categoryRepository, OrderRepository orderRepository, CartItemRepository cartItemRepository, OrderDetailRepository orderDetailRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderDetailRepository = orderDetailRepository;
    }


    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRole adminRole = new UserRole().setUserRole(UserRoleEnum.ADMIN);
            UserRole userRole = new UserRole().setUserRole(UserRoleEnum.USER);
            UserRole employeeRole = new UserRole().setUserRole(UserRoleEnum.EMPLOYEE);

            userRoleRepository.save(adminRole);
            userRoleRepository.save(userRole);
            userRoleRepository.save(employeeRole);
        }
    }

    public User createTestAdmin(String email, String username) {

        initRoles();

        User admin = new User()
                .setEmail(email)
                .setUsername(username)
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setPassword("password23")
                .setOrders(new ArrayList<>())
                .setPhoneNumber("0888123456")
                .setUserRoles(userRoleRepository.findAll());

        return userRepository.save(admin);

    }

    public User createTestEmployee(String email, String username) {

        initRoles();

        User employee = new User()
                .setEmail(email)
                .setFirstName("Rabotnik")
                .setLastName("Rabotnikov")
                .setUsername(username)
                .setPassword("password23")
                .setOrders(new ArrayList<>())
                .setPhoneNumber("0888123456")
                .setUserRoles(userRoleRepository
                        .findAll().stream().
                        filter(r -> r.getUserRole() == UserRoleEnum.EMPLOYEE).
                        toList());

        return userRepository.save(employee);
    }

    public User createTestUser(String email, String username) {

        initRoles();

        User user = new User()
                .setEmail(email)
                .setUsername(username)
                .setFirstName("UserTest")
                .setLastName("UserovTest")
                .setPassword("password23")
                .setOrders(new ArrayList<>())
                .setPhoneNumber("0888123456")
                .setUserRoles(userRoleRepository
                        .findAll().stream().
                        filter(r -> r.getUserRole() == UserRoleEnum.USER).
                        toList());

        return userRepository.save(user);
    }

    public ShoppingCart createShoppingCart(User customer, int totalItems) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setTotalItems(totalItems);
        shoppingCart.setCustomer(customer);
        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }

    public Product createTestProduct(Brand brand, Category category, User user) {

        Product product = new Product();
        product.setId(1L);
        product
                .setBrand(brand)
                .setName("Number 5Test")
                .setPrice(BigDecimal.valueOf(99.00))
                .setCategory(category)
                .setMilliliters(SizeEnum.HUNDRED)
                .setDescription("Great smell")
                .setImageUrl("http://image.com/image.png");

        return productRepository.save(product);
    }

    public Brand createTestBrand() {
        Brand brand = new Brand().
                setName("ChanelTest");

        return brandRepository.save(brand);
    }

    public Category createTestCategory() {
        Category category = new Category()
                .setName(CategoryNameEnum.WOMEN);

        return categoryRepository.save(category);
    }

    public Order createTestOrder(User customer) {

        Order order = new Order();
        order.setId(1L);
        order
                .setCustomer(customer)
                .setPostCode("1000")
                .setCourier("SPEEDY")
                .setCity("Sofia")
                .setShippingAddress("Vitosha 5")
                .setStatus(OrderStatusEnum.PROCESSING)
                .setPaymentMethod("CASH")
                .setTotalPrice(BigDecimal.TEN)
                .setShipped(false)
                .setCreatedOn(new Date());

        return orderRepository.save(order);
    }
    public OrderDetail createOrderDetail(Order order, Product product) {

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1L);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        return orderDetailRepository.save(orderDetail);
    }

    public void cleanUpDatabase() {
        orderDetailRepository.deleteAll();
        orderRepository.deleteAll();
        cartItemRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        productRepository.deleteAll();
        userRoleRepository.deleteAll();
        userRepository.deleteAll();
        brandRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}
