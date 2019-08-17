package com.shopping.thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionWay {
	
	
	public static void main(String[] args) {

		final Business business=new Business();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=1;i<=50;i++){
					business.sub1(i);
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=1;i<=50;i++){
					business.sub2(i);
				}
			}
		}).start();
		
		for(int i=1;i<=50;i++){
			business.main(i);
		}
		
	}

	static class Business {

		private int flag=1;
		Lock lock=new ReentrantLock();
		Condition condition=lock.newCondition();
		Condition condition1=lock.newCondition();
		Condition condition2=lock.newCondition();

		public  void sub1(int i) {

			lock.lock();
			try {
				while (flag!=1) {
					try {
						condition1.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				for (int j = 1; j <= 10; j++) {
					System.out.println("the sub1 sequence of " + j + ",loop of " + i);
				}
				flag = 2;
				condition2.signal();
			} finally {
				lock.unlock();
			}
		}
		
		public  void sub2(int i) {

			lock.lock();
			try {
				while (flag!=2) {
					try {
						condition2.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 20; j++) {
					System.out.println("the sub2 sequence of " + j + ",loop of " + i);
				}
				flag = 0;
				condition.signal();
			} finally {
			   lock.unlock();
			}

			 
		}

		public  void main(int i) {

			lock.lock();
			try {
				while (flag!=0) {
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				for(int j=1;j<=30;j++){
					System.out.println("the main sequence of "+j+",loop of "+i);
				}
				
				flag=1;
				condition1.signal();
			} finally {
				lock.unlock();
			}
		}
	}

}
