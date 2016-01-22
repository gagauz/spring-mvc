package org.webservice.scenarios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.dao.ProductDao;
import org.gagauz.shop.database.dao.SellerDao;
import org.gagauz.shop.database.dao.ShopDao;
import org.gagauz.shop.database.model.Product;
import org.gagauz.shop.database.model.ProductCategory;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScenarioShop extends DataBaseScenario {

    @Autowired
    private ShopDao shopDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    @Override
    protected void execute() {

        final InputStream in = getClass().getResourceAsStream("/scenarios/market_categories.csv");

        System.out.println(in);

        Map<String, Map<String, ProductCategory>> productCats = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String l;
        try {

            while ((l = reader.readLine()) != null) {
                String[] ids = l.split("/");

                Map<String, ProductCategory> map = productCats.get(ids[0]);
                if (null == map) {
                    map = new HashMap<>();
                    productCats.put(ids[0], map);
                }
                for (int i = 0; i < ids.length; i++) {

                    ProductCategory pc = map.get(ids[i]);
                    if (null == pc) {
                        ProductCategory pa = null;
                        if (i > 1) {
                            pa = map.get(ids[i - 1]);
                        }
                        pc = createCategory(ids[i], pa);
                        map.put(ids[i], pc);
                    }
                }
            }
        } catch (

        IOException e)

        {
            e.printStackTrace();
        }

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
        shop.setSeller(seller);

        shopDao.save(shop);

        Shop shop1 = new Shop();
        shop1.setName("Karenminllen");
        shop1.setHost("http://karenminllen.com");
        shop1.setSeller(seller);

        shopDao.save(shop1);

        productCategoryDao.flush();

    }

    private ProductCategory createCategory(String name, ProductCategory parents) {
        ProductCategory pc = new ProductCategory();
        pc.setName(name);
        if (null != parents) {
            pc.setParent(parents);
        }
        productCategoryDao.saveNoCommit(pc);

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
