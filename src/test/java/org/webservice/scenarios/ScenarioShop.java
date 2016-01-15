package org.webservice.scenarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webservice.database.dao.*;
import org.webservice.database.model.*;
import org.webservice.database.model.enums.Gender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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

        final InputStream in = getClass().getResourceAsStream("/scenarios/taxonomy-with-ids.ru-RU.csv");

        System.out.println(in);

        Map<String, ShopCategory> shopCats = new HashMap<>();
        Map<String, Map<String, ProductCategory>> productCats = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String l;
        try {

            while ((l = reader.readLine()) != null) {
                String[] ids = l.split(";");
                ShopCategory shopCat = shopCats.get(ids[1]);
                if (null == shopCat) {
                    shopCat = createShopCategory(ids[1].replace("\"", ""), ids[0]);
                    shopCats.put(ids[1], shopCat);
                }

                if (null != shopCat && ids.length > 2) {
                    Map<String, ProductCategory> map = productCats.get(shopCat.getExternalId());
                    if (null == map) {
                        map = new HashMap<>();
                        productCats.put(shopCat.getExternalId(), map);
                    }
                    for (int i = 2; i < ids.length; i++) {

                        ProductCategory pc = map.get(ids[i]);
                        if (null == pc) {
                            ProductCategory pa = null;
                            if (i > 2) {
                                pa = map.get(ids[i - 1]);
                            }
                            pc = createCategory(ids[i].replace("\"", ""), ids[0], shopCat, pa);
                            map.put(ids[i], pc);
                        }
                    }
                }
            }
        } catch (IOException e) {
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
        shop.setCategory(shopCats.get("\"Дом и сад\""));
        shop.setSeller(seller);

        shopDao.save(shop);

        Shop shop1 = new Shop();
        shop1.setName("Karenminllen");
        shop1.setHost("http://karenminllen.com");
        shop1.setCategory(shopCats.get("\"Предметы одежды и принадлежности\""));
        shop1.setSeller(seller);

        shopDao.save(shop1);

        shopCategoryDao.flush();
        productCategoryDao.flush();

    }

    private ShopCategory createShopCategory(String name, String extId) {
        ShopCategory pc = new ShopCategory();
        pc.setName(name);
        pc.setExternalId(extId);

        shopCategoryDao.saveNoCommit(pc);

        return pc;
    }

    private ProductCategory createCategory(String name, String extId, ShopCategory category, ProductCategory parents) {
        ProductCategory pc = new ProductCategory();
        pc.setShopCategory(category);
        pc.setName(name);
        pc.setExternalId(extId);
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
