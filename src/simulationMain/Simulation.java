package simulationMain;
import java.util.Scanner;

import entities.Person;
import entities.Building;


/**
 * Simulation which simulates people in a building and how they interact with the elevator.
 *
 * @author Team 42 (Nathan Byrne, Jack Faulkner, Elliot Willis, Mohammed Farooq, Abdul Majid)
 */
public class Simulation {
	
	/** The tick number, keeps track of the current tick. */
	private static int tickNumber = 0;
	
	/** The Constant maxTicks, stores the maximum tick the simulation is allowed to go to before a new simulation is prompted. */
	public static final int maxTicks = 2880;
	
	/** Probability of p (EMPLOYEES & DEVS). */
	private static double p = 0;
	
	/** Probability of q (CLIENTS). */
	private static double q = 0;
	
	/** The building. */
	private static Building building;
	
	/** Stores the number of employees to be put into the simulation. */
	private static int numOfEmp = 0;
	
	/**  Stores the number of developers to be put into the simulation. */
	private static int numOfDev = 0;
	
	/** The seed to be used for the random number generator. */
	private static int seed = 0;
	
	/**
	 * The main method.
	 * Calls the tick method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		tick(maxTicks);
	}
	
	
	/**
	 * Sets the building.
	 * Used to reset the values of the simulation.
	 */
	public static void setBuilding() {
		numOfEmp = readInput("How many employees do you want in the simulation?");
		numOfDev = readInput("How many developers do you want in the simulation?");
		seed = readInput("Please choose the value for the seed.");
		building = new Building(maxTicks, p, q, tickNumber, numOfEmp, numOfDev, seed);
		
		System.out.println("P: "+p+" Q: "+q+" Seed: "+seed);
	}
	
	/**
	 * Gets the tick number.
	 *
	 * @return int value of the current tick.
	 */
	public static int getTickNumber() {
		return tickNumber;
	}
	
	/**
	 * Read input.
	 *
	 * @param message This is used to display a message to the user so they know which input is required.
	 * @return int This returns the users input as long as it is larger than 0.
	 */
	// reads user input for tick increment
	public static int readInput(String message) {
		int increment = 0;
		Scanner scan = new Scanner(System.in);
		
		do {
			
			System.out.println(message);
			increment = scan.nextInt();
			
		}while(increment < 0);
	
		
		return increment;
	}
	
	/**
	 * Read PQ.
	 *
	 * @param message This is used to display a message to the user so they know which input is required.
	 * @return double This returns the users input as long as it is larger than 0.
	 */
	public static double readPQ(String message) {
		double num = 0; // initialise return value
		Scanner scan = new Scanner(System.in); // create scan object
		
		do {
			
			System.out.println(message); // print message to user
			num = scan.nextDouble(); // retrieve and store user input
			
		}while(num < 0);
		
		return num; // return value
	}
	
	/**
	 * Check PQ.
	 * Prompts the user for two double values.
	 *
	 * @return true This returns true if the two values are both larger than 0.
	 */
	public static boolean checkPQ() {
		boolean bool = true;
		
		// prompt user for p & q
		p = readPQ("Please input value for P");
		q = readPQ("Please input value for Q");
		if(p == 0 || q == 0) {
			bool = false;
			System.out.println("Please give a valid P & Q value.");
		} // check if either values are zero
		
		return bool;
	}
	
	/**
	 * This runs the simulation for as long as the current tick is less than the max tick, and as long as the user inputs a valid P & Q when prompted.
	 *
	 * @param maxTicks This is the maximum tick the simulation is allowed to go up to. Once max tick is reached, user will be prompted to create a new simulation.
	 * @return int This returns an int value which is based on how many times the simulation is ran.
	 */
	public static int tick(int maxTicks) {
		int timesRan = 0;
		
		boolean cont = true;
		
		cont = checkPQ();
		if(cont) {
			setBuilding();
		}
		
		while(cont) {
			while(tickNumber <= maxTicks) {
				int increment = readInput("Choose a value to increment the simulation by: ");
				if(increment == 0) {break;}
				for (int i = 0; i < increment; i++) {
					if(tickNumber <= maxTicks) {
						// insert individual tick methods here
						System.out.println("Tick: "+tickNumber);
						building.tick(tickNumber, maxTicks); // building tick method
						tickNumber++;
					}
				}
			}
			// print information about waiting times etc
			for(float tick : Building.getWaitingTimes()) {
				System.out.println(tick);
			}
			System.out.println("P: "+p+" Q: "+q+" Seed: "+seed);
			System.out.println("AVERAGE WT: "+Building.getAverageWaitingTime());
			System.out.println("THIS SIMULATION HAD: "+Person.getTotalPeopleAmount()+" PEOPLE");
			timesRan++;
			// prompt user for p & q
			// check if user chose to exit (set "cont" to false if they chose to exit)
			cont = checkPQ();
			if(cont) {
				tickNumber = 0;
				Person.wipePeople();
				Building.clearWaitTimes();
				setBuilding();
			}else {
				break;
			}
		}
		
		return timesRan;
	}
	
	// Alternative tick method used for automating the process of printing the results (prints the results as a CSV text file)------------------------------------------------------------------------------------
	// CHANGE "filePath" VARIABLE TO YOUR OWN FILE PATH
	// Uncomment this function and comment out the ABOVE tick function to make this one run!!!!
	
	/*public static int tick(int maxTicks) {
		String filePath = "C:\\Users\\Natha\\Documents\\file.txt"; // file path of source file to print data to
		
		int timesRan = 0;
		
		boolean cont = true;
		
		double[] pArr = {0.001, 0.002, 0.003, 0.004, 0.005};
		double[] qArr = {0.002, 0.004, 0.006, 0.008, 0.01};
		int[] seedArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		
		for(int seedIndex = 0; seedIndex < seedArr.length; seedIndex++) {
			seed=seedArr[seedIndex];
			for(int pIndex = 0; pIndex < pArr.length; pIndex++) {
				p=pArr[pIndex];
				for(int qIndex = 0; qIndex < qArr.length; qIndex++) {
					q=qArr[qIndex];
					
					// setting up building
					tickNumber = 0;
					Person.wipePeople();
					Building.clearWaitTimes();
					building = new Building(maxTicks, p, q, tickNumber, 10, 10, seed);
					
					// --------------------- execute sim start
					while(tickNumber <= maxTicks) {
						int increment = 2900;
						if(increment == 0) {break;}
						for (int i = 0; i < increment; i++) {
							if(tickNumber <= maxTicks) {
								// insert individual tick methods here
								System.out.println("Tick: "+tickNumber);
								System.out.println("P: "+p+" Q: "+q+" Seed: "+seed);
								building.tick(tickNumber, maxTicks); // building tick method
								tickNumber++;
							}
						}
					}
					// --------------------- execute sim end
					
					// output information
					System.out.println("P: "+p+" Q: "+q+" Seed: "+seed);
					System.out.println("AVERAGE WT: "+Building.getAverageWaitingTime());
					System.out.println("THIS SIMULATION HAD: "+Person.getTotalPeopleAmount()+" PEOPLE");
					
					String output = "";
					output+=p+","+q+","+seed+","+Building.getAverageWaitingTime()+","+Person.getTotalPeopleAmount();
					
					timesRan++;
					
					// writing to file start ------------------------------------
					try {
					      FileWriter myWriter = new FileWriter(filePath, true);
					      //myWriter.write("Files in Java might be tricky, but it is fun enough!");
					      myWriter.append(output+"\n");
					      myWriter.close();
					      System.out.println("Successfully wrote to the file.");
					    } catch (IOException e) {
					      System.out.println("An error occurred.");
					      e.printStackTrace();
					    }
					// writing to file end ------------------------------------
					
				}
			}
		}
		
		return timesRan;
	}*/
}
