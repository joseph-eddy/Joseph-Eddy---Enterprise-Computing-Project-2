/* Name: Joseph Eddy
Course: CNT 4714 Spring 2024
Assignment title: Project 2 - Synchronized, Cooperating Threads Under Locking
Due Date: February 11, 2024
*/

import java.util.concurrent.TimeUnit;

public class depositor implements Runnable{
	// create instance of method
	account depositAccount;
	// creates a variable to keep track of the thread number
	int threadNum;
	// calls the depositor method and passes it into depositAccount
	public depositor(account account, int currentNum) {
		depositAccount = account;
		threadNum = currentNum;
	}
	public void run() {
		try {
			while(true) {
				// calls deposit method
				depositAccount.deposit(threadNum);
				// generate random sleep time, then sleep
				long duration = (long)(Math.random() * 10);
				TimeUnit.SECONDS.sleep(duration);
			}
		}
		catch(InterruptedException e) {
	    	System.out.println("Error thrown");
            e.printStackTrace();
		}
	}
}
