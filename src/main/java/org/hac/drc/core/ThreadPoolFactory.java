package org.hac.drc.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolFactory {

	final static int FIXED_THREAD_POOL_DEFAUL_SIZE=7;
	
	public static ExecutorService getThreadPool(String poolType, int poolSize){
		
		ExecutorService threadPool = null;
		
		if("FIXED".equalsIgnoreCase(poolType)){
			poolSize = (poolSize <= 0)?FIXED_THREAD_POOL_DEFAUL_SIZE:poolSize;
			threadPool = Executors.newFixedThreadPool(poolSize);
		}
		
		return threadPool;
	}
	
	public static ExecutorService getThreadPool(String poolType){
		if("FIXED".equalsIgnoreCase(poolType)){
			return Executors.newFixedThreadPool(FIXED_THREAD_POOL_DEFAUL_SIZE);
		}
		else{
			return null;
		}
	}
}
