package org.hac.drc.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ConcurrentProcessor {

	private static List<Object> processWorkers(List<Worker> workers, String poolType, int threadsCountInPool){
		
		List<Object> responseList = new ArrayList<>();
		
		ExecutorService threadPool = ThreadPoolFactory.getThreadPool(poolType, threadsCountInPool);
		
		List<Future<Object>> results = null;
		
		try {
			
			results = threadPool.invokeAll(workers);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		threadPool.shutdown();
		
		for (Future<Object> result : results){
			try{
				responseList.add(result.get());
			}catch (InterruptedException e){
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		threadPool = null;
		
		return responseList;
	}
	
	public static List<Object> processWorkers(List<Worker> workers, ExecutorService threadPool){
		
		List<Object> responseList = new ArrayList<>();
		List<Future<Object>> results = null;
		try{
			results = threadPool.invokeAll(workers);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		for(Future<Object> result : results){
			try {
				responseList.add(result.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		threadPool = null;
		return responseList;
	}
	
	public static List<Object> processWorkers(List<Worker> workers){
		return processWorkers(workers, "FIXED", 0);
	}
	
	public static List<Object> processWorkers(List<Worker> workers,int poolSize){
		return processWorkers(workers, "FIXED", 0);
	}
}
