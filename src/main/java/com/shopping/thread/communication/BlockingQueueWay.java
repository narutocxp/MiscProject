package com.shopping.thread.communication;


/**
 * 用两个具有空间为1的阻塞队列进行通信通知
 * 
 * @author 24077
 *
 */
public class BlockingQueueWay {
	public static void main(String[] args) {

		final Business business = new Business();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub(i);
				}
			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			business.main(i);
		}

	}

	static class Business {

		private boolean isShouldSub = true;

		public synchronized void sub(int i) {

			while (!isShouldSub) {
				try {
					super.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			for (int j = 1; j <= 10; j++) {
				System.out.println("the sub sequence of " + j + ",loop of " + i);
			}
			isShouldSub = false;
			super.notify();
		}

		public synchronized void main(int i) {

			while (isShouldSub) {
				try {
					super.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			for (int j = 1; j <= 30; j++) {
				System.out.println("the main sequence of " + j + ",loop of " + i);
			}

			isShouldSub = true;
			super.notify();
		}
	}
}
