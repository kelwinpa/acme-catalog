package com.vmware.acmecatalog.service;

import com.vmware.acmecatalog.model.Product;

import java.util.List;

public interface IAcmeCatalogService {

    List<Product> getProducts();

    Product getProduct(String id);

    Product createProduct(Product product);
}
