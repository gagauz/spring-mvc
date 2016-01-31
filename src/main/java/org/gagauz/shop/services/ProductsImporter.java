package org.gagauz.shop.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.gagauz.shop.database.dao.ManufacturerDao;
import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.dao.ProductDao;
import org.gagauz.shop.database.model.Manufacturer;
import org.gagauz.shop.database.model.Product;
import org.gagauz.shop.database.model.ProductAttribute;
import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.enums.Currency;
import org.gagauz.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsImporter extends AbstractCsvImporter {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ManufacturerDao manufacturerDao;

    private Shop shop;

    private Map<String, ProductCategory> nameToCategory;
    private Map<String, ProductCategory> idToCategory;
    private Map<String, Product> idToProduct;
    private Map<String, Manufacturer> nameToManufacturer;

    private int index = 0;

    public synchronized void importProducts(Shop shop, File file) {
        this.shop = shop;
        if (file.getName().toLowerCase().endsWith(".csv")) {
            InputStream is = null;
            try {
                is = new FileInputStream(file);
                importCsvFile(is, "\"?\t\"?");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(is);
            }
        } else if (file.getName().toLowerCase().endsWith(".xlsx")) {
            importXlsFile(file);
        }
    }

    @Override
    void init() {
        index = 0;
        idToProduct = new HashMap<>();
        nameToCategory = new HashMap<>();
        idToCategory = new HashMap<>();
        nameToManufacturer = new HashMap<>();
        for (ProductCategory c : productCategoryDao.findByShop(shop)) {
            nameToCategory.put(c.getHierarchyName(), c);
            idToCategory.put(c.getExternalId(), c);
        }

        for (Product p : productDao.findByShop(shop)) {
            idToProduct.put(p.getArticle(), p);
        }

        for (Manufacturer m : manufacturerDao.findByShop(shop)) {
            nameToManufacturer.put(m.getName(), m);
        }
    }

    @Override
    void process(String[] ids) {
        index++;
        if (index == 1) {
            return;
        }
        Product p = idToProduct.get(ids[0]);
        if (null == p) {
            p = new Product();
            p.setArticle(ids[0]);
            idToProduct.put(p.getArticle(), p);
        }

        p.setName(ids[1]);
        p.setCategory(parseCategory(ids[2]));
        p.setManufacturer(parseManufacturer(ids[3]));
        p.setPrice(new BigDecimal(ids[4]));
        p.setCurrency(Currency.valueOf(ids[5].toUpperCase()));
        p.setUnit(ids[6]);
        p.setDiscount(NumberUtils.toInt(ids[7]));
        p.setDescription(ids[8]);
        p.setShop(shop);
        if (ids.length > 9) {
            p.setImages(ids[9]);
            if (ids.length > 10) {
                p.setAttributes(parseAttributes(ids[10]));
            }
        }

    }

    @Override
    void commit() {
        productDao.save(idToProduct.values());
        idToCategory = null;
        nameToCategory = null;
        nameToManufacturer = null;
        idToProduct = null;
        shop = null;
        manufacturerDao.flush();
    }

    private ProductCategory parseCategory(String string) {
        ProductCategory pc = idToCategory.get(string);
        if (null == pc) {
            pc = nameToCategory.get(string);
        }
        if (null == pc) {
            throw new RuntimeException("Неизвестный идентификатор категории :" + string);
        }
        return pc;
    }

    private Manufacturer parseManufacturer(String string) {
        if (!StringUtils.isBlank(string)) {
            Manufacturer manufacturer = nameToManufacturer.get(string);
            if (null == manufacturer) {
                manufacturer = new Manufacturer();
                manufacturer.setName(string);
                manufacturer.setShop(shop);
                manufacturerDao.saveNoCommit(manufacturer);
                nameToManufacturer.put(string, manufacturer);
            }
            return manufacturer;
        }
        return null;
    }

    private List<ProductAttribute> parseAttributes(String string) {
        return null;
    }

}
