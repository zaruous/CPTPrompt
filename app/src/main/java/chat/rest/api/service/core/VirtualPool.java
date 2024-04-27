package chat.rest.api.service.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class VirtualPool extends Thread {
	private static VirtualPool pool;
	ThreadFactory factory = Thread.ofVirtual().factory();
	ExecutorService newVirtualThreadPerTaskExecutor;

	public synchronized static VirtualPool newInstance() {
		if (pool == null)
			pool = new VirtualPool();
		return pool;
	}

	private VirtualPool() {
		newVirtualThreadPerTaskExecutor = Executors.newThreadPerTaskExecutor(factory);
		Runtime.getRuntime().addShutdownHook(this);
	}

	public void execute(Runnable r) {
		newVirtualThreadPerTaskExecutor.submit(r);
	}

	void execute(Thread r) {
		newVirtualThreadPerTaskExecutor.submit(r);
	}

	@Override
	public void run() {
		if (newVirtualThreadPerTaskExecutor != null)
			newVirtualThreadPerTaskExecutor.shutdown();
	}

}
