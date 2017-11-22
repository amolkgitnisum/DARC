package org.hac.drc.service;

import org.hac.drc.dao.DarcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DarcServiceImpl implements DarcService{

	@Autowired
	DarcDao drcDao;
	
	@Override
	public String generateReport() {
		// TODO Auto-generated method stub
		String status=drcDao.generateReport();
		return status;
	}

}
