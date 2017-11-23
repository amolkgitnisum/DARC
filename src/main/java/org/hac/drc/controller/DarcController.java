package org.hac.drc.controller;

import java.util.Map;

import org.hac.drc.dao.DarcDaoImpl;
import org.hac.drc.service.DarcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DarcController {
	
	@Autowired
	DarcService dataService;
	
	@Autowired
	DarcDaoImpl darcDaoImpl;
	
	@RequestMapping(value="/generatereport",method=RequestMethod.GET)
	public Map generateReport(@RequestParam("url") String url) throws Exception
	{
		//String url = "https://www.macys.com";
		Map status=darcDaoImpl.getfinalresult(url);
		
		return status;
	}
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView landingPage() 
	{
		return new ModelAndView ("DARC");
		
		
	}
}
