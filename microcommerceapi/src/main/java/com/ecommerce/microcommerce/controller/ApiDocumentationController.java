package com.ecommerce.microcommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/documentation")
public class ApiDocumentationController {

	@GetMapping
	public ModelAndView getDocumentations() {
		return new ModelAndView("redirect:/swagger-ui.html#");
	}
}
