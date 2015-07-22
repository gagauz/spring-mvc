package org.webservice.test.scenarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webservice.database.dao.PageTemplateDao;
import org.webservice.database.model.PageTemplate;

@Service
public class ScenarioPageTemplate extends DataBaseScenario {
	@Autowired
	private PageTemplateDao templateDao;

	@Override
	@Transactional
	protected void execute() {
		PageTemplate mailTemplate = new PageTemplate();
		mailTemplate.setName("index");
		mailTemplate.setTemplate("<html>" +
				"<header></header>" +
				"<body>" +
				"Path variable = ${variable??}!!!!" +
				"</body>" +
				"</html>");
		templateDao.save(mailTemplate);
	}
}
