package org.gagauz.shop.services;

import org.gagauz.shop.database.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsProvider {
    @Autowired
    private ProductDao productDao;

}
