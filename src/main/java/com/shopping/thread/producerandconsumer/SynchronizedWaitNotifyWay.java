package com.shopping.thread.producerandconsumer;

import java.util.LinkedList;

/**
 * 线程之间需要通信
 * @author 24077
 *
 */
public class SynchronizedWaitNotifyWay {

	public static void main(String[] args) {

		final Storage storage = new Storage();

		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						storage.product();
					}
				}
			}).start();
		}

		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						storage.consume();
					}
				}
			}).start();
		}

	}

	static class Storage {
		private final int MAX_SIZE = 10;
		 private LinkedList<Object> datas = new LinkedList<Object>();

		public void product() {

			synchronized (datas) {
				while (datas.size() + 1 > MAX_SIZE) {
					System.out.println("【生产者"+Thread.currentThread().getName()+"】仓库已满。");
					try {
						datas.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				datas.add(new Object());
				System.out.println("【生产者"+Thread.currentThread().getName()+"】生产一条数据，目前仓库大小为" + datas.size());
				datas.notify();
			}
		}

		public void consume() {

			synchronized (datas) {
				while (datas.size() == 0) {
					System.out.println("【消费者"+Thread.currentThread().getName()+"】，仓库已空。");
					try {
						datas.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				datas.remove();
				System.out.println("【消费者"+Thread.currentThread().getName()+"】消费一条数据,目前仓库大小为" + datas.size());
				datas.notify();
			}
		}
	}
}
