package org.webservice.test.scenarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webservice.database.dao.*;
import org.webservice.database.model.*;
import org.webservice.database.model.enums.Gender;

@Service
public class ScenarioShop extends DataBaseScenario {

    @Autowired
    private ShopDao shopDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    @Override
    protected void execute() {
        ShopCategory seeds = new ShopCategory();
        seeds.setName("Seeds");

        ShopCategory clothes = new ShopCategory();
        clothes.setName("Clothes");

        shopCategoryDao.save(seeds);
        shopCategoryDao.save(clothes);

        Seller seller = new Seller();
        seller.setEnabled(true);
        seller.setFirstName("FirstName");
        seller.setLastName("LastName");
        seller.setEmail("1@mail.ru");
        seller.setGender(Gender.MALE);
        seller.setPhone("89022342345");
        seller.createPassword("111");

        sellerDao.save(seller);

        Shop shop = new Shop();
        shop.setName("IvegaSementi");
        shop.setHost("http://ivegasementi.com");
        shop.setCategory(seeds);
        shop.setSeller(seller);

        shopDao.save(shop);

        Shop shop1 = new Shop();
        shop1.setName("Karenminllen");
        shop1.setHost("http://karenminllen.com");
        shop1.setCategory(clothes);
        shop1.setSeller(seller);

        shopDao.save(shop1);

        ProductCategory pc1 = createCategory("Tomato", seeds);
        createProduct("Tomato P1", shop, pc1);
        createProduct("Tomato P2", shop, pc1);
        ProductCategory pc2 = createCategory("Cucumber", seeds);
        createProduct("Cucumber P1", shop, pc2);
        createProduct("Cucumber P2", shop, pc2);

        ProductCategory pc11 = createCategory("Top", clothes);
        ProductCategory pc12 = createCategory("Bottom", clothes);
        ProductCategory pc13 = createCategory("Shoes", clothes);
        ProductCategory pc111 = createCategory("T-Shirt", clothes, pc11);
        ProductCategory pc112 = createCategory("Pullover", clothes, pc11);
        ProductCategory pc121 = createCategory("Jeans", clothes, pc12);
        ProductCategory pc122 = createCategory("Skirts", clothes, pc12);
        createProduct("T-Shirt", shop1, pc111);
        createProduct("Pullover", shop1, pc112);
        createProduct("Jeans", shop1, pc121);
        createProduct("Skirts", shop1, pc122);
    }

    private ProductCategory createCategory(String name, ShopCategory category, ProductCategory... parents) {
        ProductCategory pc = new ProductCategory();
        pc.setShopCategory(category);
        pc.setName(name);
        if (parents.length > 0) {
            pc.setParent(parents[0]);
        }
        productCategoryDao.save(pc);

        return pc;
    }

    private void createProduct(String name, Shop shop, ProductCategory pc) {
        Product p = new Product();
        p.setName(name);
        p.setShop(shop);
        p.setProductCategory(pc);
        productDao.save(p);
    }
}
