package studyboot.shop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import studyboot.shop.model.dto.ProductDTO;
import studyboot.shop.model.entity.Role;
import studyboot.shop.model.entity.User;
import studyboot.shop.repository.RoleRepository;
import studyboot.shop.repository.UserRepository;
import studyboot.shop.security.dto.LoginRequestDTO;
import studyboot.shop.security.dto.TokenResponseDTO;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate template;
    private HttpHeaders headers;

    private ProductDTO testProduct;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private String adminAccessToken;
    private String userAccessToken;

    private static final String TEST_PRODUCT_TITLE = "Test Product";
    private static final int TEST_PRODUCT_PRICE = 777;
    private static final String TEST_ADMIN_NAME ="Test Admin";
    private static final String TEST_USER_NAME ="Test User";
    private static final String TEST_PASSWORD ="Test password";
    private static final String TEST_ROLE_ADMIN ="ROLE_ADMIN";
    private static final String TEST_ROLE_USER ="ROLE_USER";

    private static final String URL_PREFIX = "http://localhost:";
    private static final String AUTH_RESOURCE = "/api/auth";
    private static final String PRODUCTS_RESOURCE = "/api/products";
    private static final String LOGIN_ENDPOINT = "/login";

    private static final String BEARER_PREFIX = "Bearer ";

    //przed każdym testem uruchamia sie BeforeEach
    @BeforeEach
    public void setUp() {
        template = new TestRestTemplate();

        headers = new HttpHeaders();

        //Tworzenie testowanego produktu
        testProduct = new ProductDTO();
        testProduct.setTitle(TEST_PRODUCT_TITLE);
        testProduct.setPrice(new BigDecimal(TEST_PRODUCT_PRICE));

        Role roleAdmin;
        Role roleUser = null;

        User admin = userRepository.findByUsername(TEST_ADMIN_NAME).orElse(null);
        User user = userRepository.findByUsername(TEST_USER_NAME).orElse(null);

        if (admin == null) {
            roleAdmin = roleRepository.findRoleByTitle(TEST_ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not founded"));
            roleUser = roleRepository.findRoleByTitle(TEST_ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role USER not founded"));

            admin = new  User();
            admin.setUsername(TEST_ADMIN_NAME);
            admin.setPassword(encoder.encode(TEST_PASSWORD));
            admin.setRoles(Set.of(roleAdmin, roleUser));

            userRepository.save(admin);
        }

        if (user == null) {
            //tworzenie uzytkownika do testowania
         roleUser = (roleUser==null)? roleRepository.findRoleByTitle(TEST_ROLE_USER)
                 .orElseThrow(() -> new RuntimeException("Role USER not founded")) : roleUser;


         user = new User();
         user.setUsername(TEST_USER_NAME);
         user.setPassword(encoder.encode(TEST_PASSWORD));
         user.setRoles(Set.of(roleUser));

         userRepository.save(user);
        }

        // POST http://localhost:88888/api/auth/login

        LoginRequestDTO loginAdminDTO = new LoginRequestDTO(TEST_ADMIN_NAME, TEST_PASSWORD);
        LoginRequestDTO loginUserDTO = new LoginRequestDTO(TEST_USER_NAME, TEST_PASSWORD);

        String authUrl = URL_PREFIX + port + AUTH_RESOURCE + LOGIN_ENDPOINT;

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(loginAdminDTO);

        ResponseEntity<TokenResponseDTO> response = template.exchange(
            authUrl,
            HttpMethod.POST,
            request,
            TokenResponseDTO.class
        );
        assertTrue(response.hasBody(), "Authorization response is empty");

        TokenResponseDTO tokenResponse = response.getBody();
        adminAccessToken = BEARER_PREFIX + tokenResponse.getAccessToken();
        }

    @Test
    public void test(){

    }
}