package org.webservice.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webservice.database.model.Region;
import org.webservice.web.request.DataGetResponse;
import org.webservice.web.request.MenuItem;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Index {

    @RequestMapping(value = "/index", produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    public String handleHtml(Model model) {
        model.addAttribute("someVariable", "Yahohoho!!!!");
        return "index";
    }

    @RequestMapping(value = "/index", produces = "application/json")
    public @ResponseBody
    Region handleJson() {
        Region region = new Region();
        return region;
    }

    @RequestMapping(value = "/data-get", produces = "application/json")
    public @ResponseBody
    DataGetResponse dataGet(String get) {
        DataGetResponse responce = new DataGetResponse();
        List<MenuItem> topMenu = new ArrayList<>();
        topMenu.add(new MenuItem("Home", "/"));
        topMenu.add(new MenuItem("Catalog", "/catalog"));
        topMenu.add(new MenuItem("Gallery", "/gallery"));
        topMenu.add(new MenuItem("About Us", "/about"));
        responce.mainMenu = topMenu;
        return responce;
    }
}
