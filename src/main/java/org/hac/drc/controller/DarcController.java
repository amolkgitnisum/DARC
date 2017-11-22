package org.hac.drc.controller;

import java.net.Authenticator.RequestorType;

import org.hac.drc.service.DarcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DarcController {

	@Autowired
	DarcService dataService;
	
	@RequestMapping(value="generatereport",method=RequestMethod.GET)
	public String generateReport()
	{
		String status=dataService.generateReport();
		
		return status;
	}
}
