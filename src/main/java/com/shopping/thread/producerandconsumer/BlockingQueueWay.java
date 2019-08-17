package com.shopping.thread.producerandconsumer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 线程之间需要通信
 * @author 24077
 *
 */
public class BlockingQueueWay {
	
	public static void main(String[] args) {
		
		final Storage storage=new Storage();
		
		for(int i=0;i<3;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep(1000);
							storage.produce();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		
		for(int i=0;i<3;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep(3000);
							storage.consume();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	static class Storage {

		private LinkedBlockingQueue<Object> datas = new LinkedBlockingQueue<Object>(10);
		
		public void produce() {
			try {
				datas.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("【生产者" + Thread.currentThread().getName() + "】生产一个产品,现库存" + datas.size());
		}
		
		public void consume() {
			try {
				datas.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("【消费者" + Thread.currentThread().getName() + "】消费一个产品,现库存" + datas.size());
		}

	}
}
