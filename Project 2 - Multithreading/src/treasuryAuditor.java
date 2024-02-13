/* Name: Joseph Eddy
Course: CNT 4714 Spring 2024
Assignment title: Project 2 - Synchronized, Cooperating Threads Under Locking
Due Date: February 11, 2024
*/

import java.util.concurrent.TimeUnit;

public class treasuryAuditor implements Runnable{
	// create instance of method
	account auditAccount;
	// calls the audit method and passes it into auditAccount
	public treasuryAuditor(account account) {
		auditAccount = account;
	}
	public void run() {
		try {
			while(true) {
				// calls audit method
				auditAccount.treasuryAudit();
				// set to sleep for random amount of time
				long duration = (long)(Math.random() * 30);
	            TimeUnit.SECONDS.sleep(duration);
			}
		}
		catch(InterruptedException e) {
			System.out.println("Error thrown");
            e.printStackTrace();
		}
	}
}
