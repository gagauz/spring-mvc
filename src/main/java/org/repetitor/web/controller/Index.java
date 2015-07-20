package org.repetitor.web.controller;

import org.repetitor.database.dao.PaymentDao;
import org.repetitor.database.dao.SubjectDao;
import org.repetitor.database.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index {

    @Autowired
    private PaymentDao repetitorDao;

    @Autowired
    private SubjectDao subjectDao;

    @RequestMapping(value = "/index")
    public String handleHtml() {
        return "index";
    }

    @RequestMapping(value = "/index", produces = { "application/json" })
    public ResponseEntity<Order> handleJson() {
        return new ResponseEntity<Order>(HttpStatus.OK);
    }

}
