package studyboot.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import studyboot.shop.model.dto.ProductDTO;
import studyboot.shop.service.interfaces.ProductService;

//ProductService jako serwis zawierający logikę biznesową.

import java.math.BigDecimal;
import java.util.List;

//Tutaj znajduje się kontroler dla Spring Boot, zarządza zasobami związanymi z produktami

//Ten kontroler definiuje kompletny CRUD (Create, Read, Update, Delete) dla zasobów Product w aplikacji. Obsługuje różne operacje:
//
//Dodawanie produktów (POST).
//Pobieranie produktów (GET).
//Aktualizację danych (PUT).
//Usuwanie produktów (DELETE).


//Tworzę 3 poziomy dostępu - otrzymanie wszystkich produktów (anonim), otrzymanie produktow po Id (user), zapisanie w DB (admin)


// http://localhost:8080/products

// /login - фронт endpoint
// /api/login
// /api/products

@RestController //klasa jest kontrolerem REST. Wszystkie metody zwracają dane (np. JSON).
@RequestMapping("/products") //Wszystkie endpointy w tej klasie będą miały prefiks /products
@Tag(name = "Product controller", description = "Controller for operations with products")
public class ProductController {

    //Aby otrzymać bean
    private final ProductService productService; //obsługuje operacje związane z produktami

    //Konstruktor: Klasa ProductController korzysta z wstrzykiwania zależności poprzez konstruktor.
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//Metody API
    // Create: POST -> /products
    @Operation(summary = "Create product", description = "Add new product", tags = {"Product"})
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = {@Content(mediaType = "application/jason", schema = @Schema(implementation = ProductDTO.class)),
                                    @Content(mediaType = "application/jason", schema = @Schema(implementation = ProductDTO.class)),

                            })
            })

    //Create:  POST /product/?title=rr&price=564
    @PostMapping //Mapuje żądania POST na adres /products.
    public ProductDTO saveProduct(@RequestBody ProductDTO productDto) {
        return productService.saveProduct(productDto);
        //zapisanie produktu w DB
    }

    // Получение ресурса
    // GET /products/5
    // GET /products/1565
    // GET /products/55

    @Operation(summary = "Get product by id",  tags = {"Product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
    @GetMapping("/{productId}")
    public ProductDTO getById(
            @Parameter(description = "The id that needs to be fetched", required = true)
            @PathVariable("productId")
            Long id) {
        return productService.getProductById(id);
    }

    // get:  GET /products
    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.getAllActiveProducts();
    }

    //products?id=3
//    @GetMapping
//    public Product getById(@RequestParam Long id) {
//    return null;
//    }

    // Update: PUT -> /products/5
    @PutMapping("/{id}") //mapuje żądanie PUT na  /products/{id}
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        return productService.updateProduct(id, productDTO); //modyfikuje produkt o danym ID
    }

    // Delete: DELETE -> /products/3
    @DeleteMapping("/{id}")
    public ProductDTO remove(@PathVariable Long id) {
        return productService.deleteProduct(id); //Usuwa produkt o podanym ID.
    }

    // Delete: DELETE -> /products/by-title?title=Banana
    @DeleteMapping("/by-title")
    public ProductDTO deleteProductByTitle(@RequestParam String title) {
        return productService.deleteProductByTitle(title); //usuwa produkt po nazwie
    }

    // PUT -> /products/restore/25
    @PutMapping("/restore/{id}")
    public ProductDTO restoreProductById(@PathVariable Long id) {
        return productService.restoreProductById(id); //przywraca produkt o danym ID
    }

    @GetMapping("/count")
    public long getProductsCount() {
        return productService.getProductsCount(); //zwraca liczbę produktów w systemie
    }

    @GetMapping("/total-price")
    public BigDecimal getTotalPrice() {
        return productService.getTotalPrice(); //zwraca całkowitę sumę cen
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice() {
        return productService.getAveragePrice(); //oblicza srednią produktów
    }
}
