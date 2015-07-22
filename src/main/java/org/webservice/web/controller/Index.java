package org.webservice.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webservice.database.dao.PaymentDao;
import org.webservice.database.dao.SubjectDao;

@Controller
public class Index {

	@Autowired
	private PaymentDao repetitorDao;

	@Autowired
	private SubjectDao subjectDao;

	@RequestMapping(value = "/index", produces = "text/html")
	@ResponseStatus(HttpStatus.OK)
	public String handleHtml(Model model) {
		model.addAttribute("someVariable", "Yahohoho!!!!");
		return "index";
	}

	@RequestMapping(value = "/index", produces = "application/json")
	public @ResponseBody String handleJson() {
		return "{}";
	}
}
