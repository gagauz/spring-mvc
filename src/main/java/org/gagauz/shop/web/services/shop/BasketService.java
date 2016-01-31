package org.gagauz.shop.web.services.shop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Cookies;
import org.gagauz.shop.database.dao.BasketItemDao;
import org.gagauz.shop.database.dao.ProductDao;
import org.gagauz.shop.database.model.BasketItem;
import org.gagauz.shop.database.model.Buyer;
import org.gagauz.shop.database.model.Product;
import org.gagauz.utils.StringUtils;

public class BasketService {
    private static final String basket_items = "basket_items";
    private static final Basket EMPTY = new Basket() {
        @Override
        public BasketItem addItem(Product product, int count) {
            throw new IllegalStateException();
        }

        @Override
        public java.util.List<BasketItem> getItems() {
            return Collections.emptyList();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    };

    @Inject
    private ApplicationStateManager stateManager;

    @Inject
    private BasketItemDao basketItemDao;

    @Inject
    private ProductDao productDao;

    @Inject
    private Cookies cookies;

    private Basket getBasket(boolean create) {
        Basket basket = stateManager.getIfExists(Basket.class);
        if (null == basket && create) {
            basket = new Basket();
            stateManager.set(Basket.class, basket);
        }
        return null == basket ? EMPTY : basket;
    }

    public BasketItem addItem(Product product, int count) {
        Buyer buyer = stateManager.getIfExists(Buyer.class);
        BasketItem item = getBasket(true).addItem(product, count);
        if (null == buyer) {
            cookies.getBuilder(basket_items, getBasket(false).toString()).setPath("/").setMaxAge(Integer.MAX_VALUE).write();
        }
        return item;
    }

    public List<BasketItem> getItems() {
        return getBasket(false).getItems();
    }

    public boolean isEmpty() {
        return getBasket(false).isEmpty();
    }

    public void restore() {
        Buyer buyer = stateManager.getIfExists(Buyer.class);
        Basket basket = getBasket(true);
        if (null != buyer) {
            basket.getItems().addAll(basketItemDao.findByBuyer(buyer));
        } else {
            String value = cookies.readCookieValue(basket_items);
            String[] ids = value.split(";");
            for (String id : ids) {
                if (!StringUtils.isEmpty(id)) {
                    try {
                        String[] tk = id.split("|");
                        Product product = productDao.findById(NumberUtils.toInt(tk[0]));
                        int count = NumberUtils.toInt(tk[1]);
                        if (count > 0) {
                            basket.addItem(product, count);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public void save() {

    }

    static class Basket {
        private List<BasketItem> items = new ArrayList<>();

        Basket() {
        }

        public List<BasketItem> getItems() {
            return items;
        }

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public BasketItem addItem(Product product, int count) {
            BasketItem item = findItem(product);
            if (null == item) {
                item = new BasketItem();
                item.setProduct(product);
                items.add(item);
            }
            item.setCount(item.getCount() + count);
            if (item.getCount() <= 0) {
                items.remove(item);
            }
            return item;
        }

        public BasketItem findItem(Product product) {
            for (BasketItem item : items) {
                if (item.getProduct().equals(product)) {
                    return item;
                }
            }
            return null;
        }

        public void deleteItem(Product product, int count) {
            if (null == items) {
                items = new ArrayList<>();
            }
            BasketItem item = new BasketItem();
            item.setProduct(product);
            item.setCount(count);
            items.add(item);
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (BasketItem item : items) {
                s.append(item.getId()).append('|').append(item.getCount()).append(';');
            }
            return s.toString();
        }
    }
}
