package org.webservice.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webservice.database.dao.ShopCategoryDao;
import org.webservice.database.model.ShopCategory;

import java.util.List;

@Controller
public class ShopCategories {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @RequestMapping(value = "/shopcategories", produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    public String handleHtml(Model model) {
        model.addAttribute("someVariable", "Yahohoho!!!!");
        return "shopcategories";
    }

    @RequestMapping(value = "/shopcategories/data-get", produces = "application/json; charset=utf-8")
    public @ResponseBody
    List<ShopCategory> dataGet(String get) {
        List<ShopCategory> list = shopCategoryDao.findAll();
        shopCategoryDao.unproxy(list);
        return list;
    }
}
