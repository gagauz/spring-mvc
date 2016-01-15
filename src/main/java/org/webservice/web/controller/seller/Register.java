package org.webservice.web.controller.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.webservice.database.dao.ShopCategoryDao;
import org.webservice.database.model.Seller;
import org.webservice.database.model.ShopCategory;

import java.util.List;

@Controller
public class Register {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @RequestMapping(method = RequestMethod.GET, value = "/seller/register", produces = "text/html; charset=utf-8")
    @ResponseStatus(HttpStatus.OK)
    public String handleGetHtml(Model model) {
        return "seller/register";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/seller/register", produces = "text/html; charset=utf-8")
    @ResponseStatus(HttpStatus.OK)
    public String handlePostHtml(Model model, @ModelAttribute Seller seller) {
        System.out.println(seller);
        return "seller/register";
    }

    @RequestMapping(value = "/seller/register/data-get", produces = "application/json; charset=utf-8")
    public @ResponseBody
    List<ShopCategory> dataGet(String get) {
        List<ShopCategory> list = shopCategoryDao.findAll();
        shopCategoryDao.unproxy(list);
        return list;
    }
}
