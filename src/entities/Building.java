package entities;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Class Building, this is used to hold the rest of the entities within the simulation so they can work together properly.
 *
 * @author Team --
 */
public class Building {
	
	/** Elevator which will be used to carry people up and down the building. */
	Elevator lift;
	
	/** The max ticks. */
	private int maxTicks;
	
	/** Keeps track of how many floors are in the building. */
	private static int floors;
	
	/** The probability of maintenance crew arriving. */
	private static double maintenanceProb = 0.005; 
	
	/** The probability of employees and developers wanting to change floor. */
	private static double p;
	
	/** The The probability of clients arriving in the building. */
	private static double q;
	
	/** Stores the waiting time of each person whilst waiting for the elevator. */
	private static ArrayList<Float> waitingTimes = new ArrayList<Float>();
	
	/** Random number generator to test against the probability values. */
	private static Random rand;
	
	/** The value generated form the random number generator. */
	private static double randomNum;
	
	/** Keeps track of how many clients have entered the building. */
	private int clientsEntered;
	
	/** Keeps track of how many maintenance crews have entered the building. */
	private int maintenanceCrewEntered;
	/** Keeps track of the random number generator seed. */
	private static int seed;
	
	
	/**
	 * Instantiates a new building.
	 *
	 * @param maxTicks This is the maximum tick value.
	 * @param p This is the p probability.
	 * @param q This is the q probability.
	 * @param currentTick This is the current tick value.
	 * @param numOfEmp This is the number of employees to be added to the building.
	 * @param numOfDev This is the number of developers to be added to the building.
	 * @param seed This is the seed to be used for the random number generator.
	 */
	public Building(int maxTicks, double p, double q, int currentTick, int numOfEmp, int numOfDev, int seed) {
		Floor.wipeFloors();
		Elevator.clearElevators();
		Person.clearPeople();
		this.seed = seed;
		rand = new Random(seed);
		randomNum = generateNum();
		floors = 7;
		this.clientsEntered = 0;
		this.maintenanceCrewEntered = 0;
		this.p = p;
		this.q = q;
		this.maxTicks = maxTicks;
		lift = new Elevator();
		generateFloors(floors);
		generateEmployee(numOfEmp, currentTick);
		generateDeveloper(numOfDev, currentTick);
	}
	
	/**
	 * Generate employees based on user input.
	 *
	 * @param numOfEmp The amount of employees to generate.
	 * @param currentTick The current tick of the simulation.
	 */
	public void generateEmployee(int numOfEmp, int currentTick) {
		for(int i = 0; i < numOfEmp; i++) {
			Employee employee = new Employee(maxTicks, currentTick);
			Floor.addToGroundFloor(employee, currentTick);
		}
		System.out.println("Created "+numOfEmp+" employees");
	}
	
		/**
	 * Generate developer.
	 *
	 * @param numOfDev The amount of developers to generate.
	 * @param currentTick The current tick of the simulation.
	 */
		public void generateDeveloper(int numOfDev, int currentTick) {
			for(int i = 0; i < numOfDev; i++) {
				Developer developer = new Developer(maxTicks, currentTick);
				Floor.addToGroundFloor(developer, currentTick);
			}
			System.out.println("Created "+numOfDev+" developers");
		}
	
	/**
	 * Generates a client when the randomly generated number is less than q.
	 *
	 * @param currentTick The current tick of the simulation.
	 */
	public void generateClient(int currentTick) {
		
		if(randomNum < q) {
			Client client = new Client(maxTicks, currentTick);
			Floor.addToGroundFloor(client, currentTick);
			this.clientsEntered++;
			System.out.println("Created a client");
		}
	}
	
	/**
	 * Generate maintenance crew when the randomly generated number is less than the maintenance crew probability.
	 *
	 * @param currentTick The current tick of the simulation.
	 */
	public void generateMaintenanceCrew(int currentTick) {
		
		if(randomNum < maintenanceProb) {
			MaintenanceCrew mC = new MaintenanceCrew(maxTicks, currentTick);
			Floor.addToGroundFloor(mC, currentTick);
			this.maintenanceCrewEntered++;
			System.out.println("Created a maintenance crew");
		}
	}	
	
	/**
	 * Generate a random double number, used to compare against the probability values.
	 *
	 * @return double This is the number that is generated.
	 */
	public static double generateNum() {
		double random = 0;
		
		random = rand.nextDouble();
		
		return random;
	}
	
	/**
	 * Generates the floors that will be used in the building.
	 *
	 * @param floors This is an integer value depicting how many floors there should be in the building.
	 */
	public void generateFloors(int floors) {
		for(int i = 0; i < floors; i++) {
			Floor floor = new Floor();
		}
		System.out.println("Created "+Floor.getFloorsSize()+" floors");
	}
	
	/**
	 * Removes a person from the bottom floor.
	 *
	 * @param person This is the person that will be removed from the floor.
	 */
	public static void removePerson(Person person) {
		Floor.getFloor(0).removeFloorPerson(person.getId());
	}
	
	/**
	 * Adds a waiting time to the waiting time array list.
	 *
	 * @param waitingTime This is the time to be added.
	 */
	public static void addWaitingTime(float waitingTime) {
		waitingTimes.add(waitingTime);
	}
	
	/**
	 * Clear the waiting times array list.
	 */
	public static void clearWaitTimes() {
		waitingTimes.clear();
	}
	
	/**
	 * This ticks the building and keeps the rest of the program in sync.
	 *
	 * @param tickNumber This is the current tick.
	 * @param maxTick This is the max tick.
	 */
	public void tick(int tickNumber, int maxTick) {
		randomNum = generateNum();
		generateClient(tickNumber);
		generateMaintenanceCrew(tickNumber);
		Person.tickAllPeople(tickNumber, maxTick);
		Floor.tickAllFloors(tickNumber, maxTick);
		lift.tick(tickNumber);
		System.out.println("---------------------------------------------------------------------------------------------");
		System.out.println("");
	}

	// ----------- getters -------------------------------------- //
	
	/**
	 * Gets the random num.
	 *
	 * @return the random num
	 */
	public static double getRandomNum() {
		return randomNum;
	}
	
	/**
	 * Gets the top floor.
	 *
	 * @return the top floor
	 */
	public static int getTopFloor() {
		return floors-1;
	}
	
	/**
	 * Gets the p.
	 *
	 * @return the p
	 */
	public static double getP() {
		return p;
	}
	
	/**
	 * Gets the q.
	 *
	 * @return the q
	 */
	public static double getQ() {
		return q;
	}
	
	/**
	 * Gets the waiting times.
	 *
	 * @return the waiting times
	 */
	public static ArrayList<Float> getWaitingTimes() {
		return waitingTimes;
	}
	
	/**
	 * Calculates the average waiting time based on the individual times and how many there are.
	 *
	 * @return double This is the calculated average waiting time.
	 */
	public static double getAverageWaitingTime() {
		float avg = 0;
		// calculate average waiting time
		float total = 0;
		
		for(float time : waitingTimes) {
			total+=time;
		}
		
		avg = total/waitingTimes.size();
		
		return avg;
	}
	
	/**
	 * Gets the clients entered.
	 *
	 * @return the clients entered
	 */
	public int getClientsEntered() {
		return this.clientsEntered;
	}
	
	/**
	 * Gets the maintenance crew entered.
	 *
	 * @return the maintenance crew entered.
	 */
	public int getMaintenanceCrewEntered() {
		return this.maintenanceCrewEntered;
	}
	
	/**
	 * Gets the current seed.
	 *
	 * @return int This is the seed used for the random number generator.
	 */
	public static int getSeed() {
		return seed;
	}
	
	public static int getRandNum(int max) {
		return rand.nextInt(max);
	}
	
	// ----------- setters -------------------------------------- //
	
	/**
	 * Sets the random num.
	 *
	 * @param num the new random num
	 */
	public void setRandomNum(double num) {
		randomNum = num;
	}
	
	/**
	 * Sets the maintenance prob.
	 *
	 * @param prob the new maintenance prob
	 */
	public void setMaintenanceProb(double prob) {
		maintenanceProb = prob;
	}
}
