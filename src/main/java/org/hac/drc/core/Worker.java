package org.hac.drc.core;

import java.util.concurrent.Callable;

public abstract class Worker implements Callable<Object>{

	protected String workerName;
	
	public final Object call() {
		
		return process();
	}
	
	public abstract Object process();

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	
	
}
