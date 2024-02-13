/* Name: Joseph Eddy
Course: CNT 4714 Spring 2024
Assignment title: Project 2 - Synchronized, Cooperating Threads Under Locking
Due Date: February 11, 2024
*/

import java.util.concurrent.TimeUnit;

public class withdrawal implements Runnable{
	// create instance of method
	account withdrawAccount;
	// creates a variable to keep track of the thread number
	int threadNum;
	// calls the withdraw method and passes it into withdrawAccount
	public withdrawal(account account, int currentNum) {
		withdrawAccount = account;
		threadNum = currentNum;
	}
	public void run() {
		try {
			while(true) {
				// calls withdraw method
				withdrawAccount.withdraw(threadNum);
				// generate random sleep time and put it to sleep
				long duration = (long)(Math.random() * 5);
				TimeUnit.SECONDS.sleep(duration);	            			   

			}
		}
		catch (InterruptedException e) {
			System.out.println("Error thrown");
	        e.printStackTrace();
		}
	}
}
