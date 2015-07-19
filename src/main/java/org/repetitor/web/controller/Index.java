package org.repetitor.web.controller;

import java.util.List;

import org.repetitor.database.dao.PaymentDao;
import org.repetitor.database.dao.SubjectDao;
import org.repetitor.database.model.Payment;
import org.repetitor.database.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Index {

    @Autowired
    private PaymentDao repetitorDao;

    @Autowired
    private SubjectDao subjectDao;

    @RequestMapping(value = "/")
    public ResponseEntity<String> handle() {
        return new ResponseEntity<String>("Yau!!!", HttpStatus.OK);
    }

    @RequestMapping(value = "/1", produces = { "application/json" })
    @Transactional
    public @ResponseBody
    List<Payment> handle1() {
        return repetitorDao.findAll();
    }

    @RequestMapping(value = "/2", produces = { "application/json" })
    @ResponseBody
    @Transactional
    public List<Subject> handle2() {
        List<Subject> list = subjectDao.findAll();
        System.out.println(list);
        return list;
    }
}
