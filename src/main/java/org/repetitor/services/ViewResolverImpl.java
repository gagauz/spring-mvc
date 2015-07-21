package org.repetitor.services;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.repetitor.services.template.PageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

@Service
public final class ViewResolverImpl implements ViewResolver {

	@Autowired
	private PageTemplateService freemarkerService;

	@Override
	public View resolveViewName(final String viewName, Locale locale) throws Exception {
		return new View() {

			@Override
			public String getContentType() {
				return "text/html; charset=utf-8";
			}

			@Override
			public void render(Map<String, ?> model, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				freemarkerService.parseTemplate(viewName, model, response.getWriter());
			}
		};
	}
}