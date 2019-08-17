package com.shopping.thread.communication;

import java.util.concurrent.ArrayBlockingQueue;

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

		private ArrayBlockingQueue<Object> queue1 = new ArrayBlockingQueue<Object>(1);
		private ArrayBlockingQueue<Object> queue2 = new ArrayBlockingQueue<Object>(1);

		public Business() {
			try {
				queue2.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void sub(int i) {

			try {
				queue1.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int j = 1; j <= 10; j++) {
				System.out.println("the sub sequence of " + j + ",loop of " + i);
			}

			try {
				queue2.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public synchronized void main(int i) {

			try {
				queue2.put(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int j = 1; j <= 30; j++) {
				System.out.println("the main sequence of " + j + ",loop of " + i);
			}

			try {
				queue1.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
