package com.vmware.acmecatalog.service;

import com.vmware.acmecatalog.model.Product;
import com.vmware.acmecatalog.model.ProductNotFoundException;
import com.vmware.acmecatalog.repository.AcmeCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcmeCatalogService implements IAcmeCatalogService {

    private AcmeCatalogRepository acmeCatalogRepository;

    public AcmeCatalogService(AcmeCatalogRepository acmeCatalogRepository) {
        this.acmeCatalogRepository = acmeCatalogRepository;
    }

    @Override
    public List<Product> getProducts() {
        return acmeCatalogRepository.findAll();
    }

    @Override
    public Product getProduct(String id) {
        return acmeCatalogRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product createProduct(Product newProduct) {
        return acmeCatalogRepository.save(newProduct);
    }
}
