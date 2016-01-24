package org.webservice.scenarios;

import java.io.InputStream;

import org.gagauz.shop.database.dao.ProductCategoryDao;
import org.gagauz.shop.database.dao.ProductDao;
import org.gagauz.shop.database.dao.SellerDao;
import org.gagauz.shop.database.dao.ShopDao;
import org.gagauz.shop.database.model.Seller;
import org.gagauz.shop.database.model.Shop;
import org.gagauz.shop.database.model.enums.Currency;
import org.gagauz.shop.database.model.enums.Gender;
import org.gagauz.shop.services.ShopCategoriesImporter;
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
    @Autowired
    private ShopCategoriesImporter shopCategoriesImporter;

    @Override
    protected void execute() {

        final InputStream in = getClass().getResourceAsStream("/scenarios/market_categories.csv");

        shopCategoriesImporter.importCategories(null, in);

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
        shop.setDefaultCurrency(Currency.USD);

        shopDao.save(shop);

        Shop shop1 = new Shop();
        shop1.setName("Karenminllen");
        shop1.setHost("http://karenminllen.com");
        shop1.setSeller(seller);
        shop1.setDefaultCurrency(Currency.USD);

        shopDao.save(shop1);

        final InputStream in1 = getClass().getResourceAsStream("/scenarios/market_categories1.csv");

        shopCategoriesImporter.importCategories(shop1, in1);

    }

}
