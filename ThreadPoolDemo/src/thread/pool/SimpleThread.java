package thread.pool;

class SimpleThread extends Thread {
	private boolean runningFlag;
	private String argument;

	public boolean isRunning() {
		return runningFlag;
	}

	public synchronized void setRunning(boolean flag) {
		runningFlag = flag;
		if (flag)
			this.notify();
	}

	public String getArgument() {
		return this.argument;
	}

	public void setArgument(String string) {
		argument = string;
	}

	public SimpleThread(int threadNumber) {
		runningFlag = false;
		System.out.println("thread " + threadNumber + "started.");
	}

	public synchronized void run() {
		try {
			while (true) {
				if (!runningFlag) {
					this.wait();
				} else {
					System.out.println("processing " + getArgument() + "... done.");
					sleep(5000);
					System.out.println("Thread is sleeping...");
					setRunning(false);
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupt");
		}
	}
}