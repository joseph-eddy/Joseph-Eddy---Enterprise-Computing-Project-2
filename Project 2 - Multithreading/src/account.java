/* Name: Joseph Eddy
Course: CNT 4714 Spring 2024
Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
Due Date: February 11, 2024
*/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class account {
	// Declare variables:
	
	// balance
	int accountBalance;
	// transaction
	int transactionNum;
	// keeps track of when last internal audit has occurred
	int lastInternalAudit;
	// keeps track of when last treasury audit has occurred
	int lastTreasuryAudit;
	// locking mechanism
	ReentrantLock lock;
	// condition
	Condition condition;
	
	public account() {
		// Initialize variables:
		accountBalance = 0;
		transactionNum = 1;
		lastInternalAudit = 0;
		lastTreasuryAudit = 0;
		lock = new ReentrantLock();
		condition = lock.newCondition();		
	}
	
	public void deposit(int threadNum) throws InterruptedException{
		try {
			// gives deposit function lock
			lock.lock();
			// generates and deposits random num 1-500
			Random random = new Random();
			int randomNum = random.nextInt(500);
			accountBalance += randomNum;
			// print to console
            System.out.println("Agent DT" + threadNum + " deposits $" + randomNum + 
            	"\t\t\t\t\t\t\t" + "(+) Balance is: $" + accountBalance + "\t\t\t\t" + transactionNum);  
            // if the amount deposited is greater than $350, it gets flagged
         	if(randomNum > 350) {
         	// gets the date and time
				ZonedDateTime currentDateTime = ZonedDateTime.now();
				// defines a custom date/time format with timezones and AM/PM
				String datePattern = "dd/MM/yyyy hh:mm:ssa z";
				// customizes date and time format using datePattern
				DateTimeFormatter format = DateTimeFormatter.ofPattern(datePattern);
				// formats the current date and time using formatter
				String currentDate = currentDateTime.format(format);
				
				// append to CSV file
				String filePath = "transaction.csv";
				
				// info to be appended
				String[] infoAppend = {"Depositor Agent DT"+threadNum+" issued deposit of "+"$"+randomNum+".00 "
						+"at: "+currentDate+"\t\t\t\tTransaction Number : "+transactionNum};
				
				try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
					// appends each value to the CSV file
					for(String value : infoAppend) {
						writer.append(value);
					}
					
					// adds a new line after appending values
					writer.append("\n\n");

					// closes writer
					writer.close();
				}
				catch(IOException f) {
					f.printStackTrace();
				}
				
         		// print to console
         		System.out.println("\n* Flagged Transaction - Deposit Agent DT" + threadNum + 
         			" made a deposit in excess of $" + randomNum + ".00 USD - See Flagged Transaction Log *\n");         				
         	}
            // increment transaction number
            transactionNum++;
            // wake up all waiting threads
            condition.signalAll();
            
		}
		finally {
			// unlocks for other threads to use lock
			lock.unlock();
		}
	}
	
	public void withdraw(int threadNum) throws InterruptedException{
		try{
			// gives withdraw function lock
			lock.lock();
			// generate and withdraw random number 1-99
			Random random = new Random();
			int randomNum = random.nextInt(99);
			// if the amount requested is greater than the amount in the account, thread gets disregarded
			while(accountBalance<randomNum) {
				System.out.println("\t\t\t\tAgent WT" + threadNum + " withdraws $" + randomNum +
					"\t\t\t(X) Transaction blocked - insufficient funds!");
				condition.await();
			}
			// update balance
			accountBalance = accountBalance - randomNum;
			// print to console
			System.out.println("\t\t\t\tAgent WT" + threadNum + " withdraws $" + randomNum + 
				"\t\t\t(-) Balance is: $" + accountBalance + "\t\t\t\t" + transactionNum);
			
			// if the amount withdrawn is greater than $75, it gets flagged
			if(randomNum > 75) {
				// gets the date and time
				ZonedDateTime currentDateTime = ZonedDateTime.now();
				// defines a custom date/time format with timezones and AM/PM
				String datePattern = "dd/MM/yyyy hh:mm:ssa z";
				// customizes date and time format using datePattern
				DateTimeFormatter format = DateTimeFormatter.ofPattern(datePattern);
				// formats the current date and time using formatter
				String currentDate = currentDateTime.format(format);
				
				// append to CSV file
				String filePath = "transaction.csv";
				
				// info to be appended
				String[] infoAppend = {"\tWithdrawal Agent WT"+threadNum+" issued withdrawal of "+"$"+randomNum+".00 "
						+"at: "+currentDate+"\t\tTransaction Number : "+transactionNum};
				
				try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
					// appends each value to the CSV file
					for(String value : infoAppend) {
						writer.append(value);
					}
					
					// adds a new line after appending values
					writer.append("\n\n");

					// closes writer
					writer.close();
				}
				catch(IOException f) {
					f.printStackTrace();
				}
				
				// prints to console
				System.out.println("\n* Flagged Transaction - Withdrawal Agent WT" + threadNum + 
					" made a withdrawal in excess of $" + randomNum + ".00 USD - See Flagged Transaction Log *\n");			
			}
			// increment transaction number
			transactionNum++;
		}
		catch(InterruptedException e){
			// error handling
	    	System.out.println("Error thrown");
            e.printStackTrace();

		}
		finally {
			// unlocks for other threads to use lock
			lock.unlock();			
		}
	}
	
	public void internalAudit() throws InterruptedException{
		try {
			// gives audit function the lock
			lock.lock();
			// calculate when last audit has happened
			lastInternalAudit = transactionNum - 1 - lastInternalAudit;
			
			// print to console
			System.out.println("\n***************************************************************************************************************************************************"
					+ "\n\n\t\tINTERNAL BANK AUDITOR FINDS CURRENT ACCOUNT BALANCE TO BE: $" + accountBalance + "\tNumber of transactions since last Internal audit: " 
					+ lastInternalAudit
					+ "\n\n***************************************************************************************************************************************************\n");
		}
		finally {
			// unlocks for other threads to use lock
			lock.unlock();
		}
	}
	public void treasuryAudit() throws InterruptedException{
		try {
			// gives audit function the lock
			lock.lock();
			// calculate when last audit has happened
			lastTreasuryAudit = transactionNum - 1 - lastTreasuryAudit;
			
			// print to console
			System.out.println("\n***************************************************************************************************************************************************"
					+ "\n\n\t\tTREASURY DEPT AUDITOR FINDS CURRENT ACCOUNT BALANCE TO BE: $" + accountBalance + "\tNumber of transactions since last Treasury audit: " 
					+ lastTreasuryAudit
					+ "\n\n***************************************************************************************************************************************************\n");
		}
		finally {
			// unlocks for other threads to use lock
			lock.unlock();
		}
	}
	
}
