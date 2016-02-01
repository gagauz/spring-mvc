package org.gagauz.shop.services;

import org.apache.commons.io.IOUtils;
import org.gagauz.shop.database.dao.*;
import org.gagauz.shop.database.model.*;
import org.gagauz.shop.database.model.enums.Currency;
import org.gagauz.shop.database.model.enums.ProductUnit;
import org.gagauz.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.gagauz.shop.services.Columns.*;

@Service
public class ProductsImporter extends AbstractCsvImporter {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ManufacturerDao manufacturerDao;

    @Autowired
    private ProductGroupDao productGroupDao;

    @Autowired
    private ProductAttributeDao productAttrDao;

    private Shop shop;

    private Map<String, ProductCategory> nameToCategory;
    private Map<String, ProductCategory> idToCategory;
    private Map<String, Product> idToProduct;
    private Map<String, Manufacturer> nameToManufacturer;
    private Map<String, ProductGroup> productToGroupsMap;
    private Map<String, ProductAttribute> productToAttributeMap;

    private int index = 0;

    private String[] row;

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
        productToGroupsMap = new HashMap<>();
        productToAttributeMap = new HashMap<>();
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

        for (ProductGroup g : productGroupDao.findByShop(shop)) {
            productToGroupsMap.put(g.getName(), g);
        }

        for (ProductAttribute a : productAttrDao.findByShop(shop)) {
            productToAttributeMap.put(a.getName(), a);
        }
    }

    @Override
    void process(String[] ids) {
        row = ids;
        index++;
        if (index == 1) {
            return;
        }
        String article = getString(ARTICLE);
        Product p = idToProduct.get(article);
        if (null == p) {
            p = new Product();
            p.setArticle(article);
            idToProduct.put(p.getArticle(), p);
        }

        p.setName(getString(NAME));
        p.setCategory(parseCategory(getString(CATEGORY)));
        p.setManufacturer(parseManufacturer(getString(MANUFACTURER)));
        p.setPrice(getBigDecimal(PRICE));
        p.setCurrency(getEnum(CURRENCY, Currency.class));
        p.setUnit(getEnum(UNIT, ProductUnit.class));
        p.setDiscount(getInteger(DISCOUNT));
        p.setDescription(getString(DESCRIPTION));
        p.setShop(shop);
        p.setImages(getString(IMAGES));
        p.setAttributes(parseAttributes(getStrings(ATTRIBUTES)));
        p.setVariants(parseGroups(p, getStrings(GROUPS), getStrings(VARIANTS)));
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
        productAttrDao.flush();
        productGroupDao.flush();
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

    private List<ProductAttribute> parseAttributes(String[] strings) {
        List<ProductAttribute> productAttributes = new ArrayList<>();
        for (String string : strings) {
            ProductAttribute attr = productToAttributeMap.get(string);
            if (null == attr) {
                attr = new ProductAttribute();
                attr.setName(string);
                attr.setShop(shop);
                productAttrDao.saveNoCommit(attr);
                productToAttributeMap.put(string, attr);
            }
            productAttributes.add(attr);
        }
        return productAttributes;
    }

    private List<ProductVariant> parseGroups(Product product, String[] strings, String[] variants) {
        List<ProductVariant> productVariants = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            String variant = variants[i];
            ProductGroup group = productToGroupsMap.get(string);
            if (null == group) {
                group = new ProductGroup();
                group.setName(string);
                group.setShop(shop);
                productGroupDao.saveNoCommit(group);
                productToGroupsMap.put(string, group);
            }
            ProductVariant var = new ProductVariant(group, product);
            var.setName(variant);
            productVariants.add(var);
        }
        return productVariants;
    }

    String getString(Columns column) {
        return null == row[column.ordinal()] ? "" : row[column.ordinal()];
    }

    String[] getStrings(Columns column) {
        String string = getString(column);
        return null == string ? new String[0] : string.split(",");
    }

    BigDecimal getBigDecimal(Columns column) {
        return new BigDecimal(getString(column));
    }

    <E extends Enum<E>> E getEnum(Columns column, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equalsIgnoreCase(getString(column))) {
                return e;
            }
        }
        return null;
    }

    Integer getInteger(Columns column) {
        return Integer.parseInt(getString(column));
    }
}
