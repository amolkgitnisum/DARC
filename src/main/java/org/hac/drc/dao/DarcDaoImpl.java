package org.hac.drc.dao;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hac.drc.core.ConcurrentProcessor;
import org.hac.drc.core.FileSplitter;
import org.hac.drc.core.ProcessingWorker;
import org.hac.drc.core.Worker;
import org.springframework.stereotype.Repository;

@Repository
public class DarcDaoImpl implements DarcDao {

	
	@Override
	public String generateReport() {
		// TODO Auto-generated method stub
		String status="success";
		return status;
	}

	public Map<String, List<String>> checkHeaders(List<String> urls) throws Exception{
		
		List<String> connectionList = new ArrayList<>();
		List<String> eTagList = new ArrayList<>();
		List<String> lastModifiedList = new ArrayList<>();
		List<String> acceptEncodingList = new ArrayList<>();
		List<String> varyList = new ArrayList<>();
		List<String> cacheControlList = new ArrayList<>();
		List<String> statusList = new ArrayList<>();
		Map<String, List<String>> defaulterMap = new HashMap<>();
		
		FileSplitter splitter = new FileSplitter();
		List<List<String>> chunkLists = splitter.splitFile(urls);
		List<Worker> workersList = new ArrayList<>();
		for (List<String> list : chunkLists) {
			workersList.add(new ProcessingWorker(list));
		}
		
		List<Object> responses = ConcurrentProcessor.processWorkers(workersList, workersList.size());
		
		
		return defaulterMap;
	}
}
