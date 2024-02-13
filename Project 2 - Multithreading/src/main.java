/* Name: Joseph Eddy
Course: CNT 4714 Spring 2024
Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
Due Date: February 11, 2024
*/

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class main {
	
	public static void main(String[] args) throws FileNotFoundException {
		// prints to output file instead of terminal
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
		System.setOut(out);

		// print out header to console:
		System.out.println("*** SIMULATION BEGINS...\n");
		System.out.println("Deposit Agents\t\t\tWithdrawal Agents\t\t\tBalance\t\t\t\t\t\tTransaction Number\n");
		System.out.println("---------------\t\t\t------------------\t\t\t--------\t\t\t\t\t-------------------\t\n"); 
		
		// controls the number of threads
		ExecutorService executor = Executors.newFixedThreadPool(15);
		// creates an instance of the account class
		account account = new account();
		
		// create the depositor/withdrawal/auditor instances at the same time with account passed into them
	        
	    withdrawal withdrawalAgent0 = new withdrawal(account, 0);
	    withdrawal withdrawalAgent1 = new withdrawal(account, 1);
	    withdrawal withdrawalAgent2 = new withdrawal(account, 2);
	    withdrawal withdrawalAgent3 = new withdrawal(account, 3);
	    withdrawal withdrawalAgent4 = new withdrawal(account, 4);
	    withdrawal withdrawalAgent5 = new withdrawal(account, 5);
	    withdrawal withdrawalAgent6 = new withdrawal(account, 6);
	    withdrawal withdrawalAgent7 = new withdrawal(account, 7);
	    withdrawal withdrawalAgent8 = new withdrawal(account, 8);
	    withdrawal withdrawalAgent9 = new withdrawal(account, 9);
	       
	    depositor depositAgent0 = new depositor(account, 0);
	    depositor depositAgent1 = new depositor(account, 1);
	    depositor depositAgent2 = new depositor(account, 2);
	    depositor depositAgent3 = new depositor(account, 3);
	    depositor depositAgent4 = new depositor(account, 4);
	    
	    internalAuditor internalAuditorAgent = new internalAuditor(account);
	    treasuryAuditor treasuryAuditorAgent = new treasuryAuditor(account);
	    
	    // submitting task to service's execute method
		executor.execute(withdrawalAgent0);
		executor.execute(withdrawalAgent1);
		executor.execute(withdrawalAgent2);
		executor.execute(withdrawalAgent3);
		executor.execute(withdrawalAgent4);
		executor.execute(withdrawalAgent5);
		executor.execute(withdrawalAgent6);
		executor.execute(withdrawalAgent7);
		executor.execute(withdrawalAgent8);
		executor.execute(withdrawalAgent9);
		
	    executor.execute(internalAuditorAgent);
		executor.execute(treasuryAuditorAgent);
		
		executor.execute(depositAgent0);
		executor.execute(depositAgent1);
		executor.execute(depositAgent2);
		executor.execute(depositAgent3);
		executor.execute(depositAgent4);
		
		
	}

}
