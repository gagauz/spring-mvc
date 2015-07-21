package org.repetitor.web.controller;

import org.repetitor.database.dao.PaymentDao;
import org.repetitor.database.dao.SubjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class Index {

	@Autowired
	private PaymentDao repetitorDao;

	@Autowired
	private SubjectDao subjectDao;

	@RequestMapping(value = "/index", produces = "text/html")
	@ResponseStatus(HttpStatus.OK)
	public void handleHtml() {
	}

	@RequestMapping(value = "/index", produces = "application/json")
	public @ResponseBody Object handleJson() {
		return new Object();
	}
}
