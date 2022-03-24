package com.vmware.acmecatalog.controller;

import com.vmware.acmecatalog.model.Product;
import com.vmware.acmecatalog.service.AcmeCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AcmeCatalogController {

    private AcmeCatalogService acmeCatalogService;

    public AcmeCatalogController(AcmeCatalogService acmeCatalogService) {
        this.acmeCatalogService = acmeCatalogService;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return acmeCatalogService.getProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable String id) {
        return acmeCatalogService.getProduct(id);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product newProduct) {
        return acmeCatalogService.createProduct(newProduct);
    }
}
