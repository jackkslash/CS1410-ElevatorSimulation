package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * The Class Person.
 */
public abstract class Person {
	
	/** Holds all people in the simulation. */
	private static HashMap<Integer, Person> people = new HashMap<Integer, Person>();
	
	/** Holds the ID's of the people that need to be removed from the simulation. */
	private static ArrayList<Integer> removeThesePeople = new ArrayList<Integer>();
	
	/** Keeps track of how many people have been in the simulation. */
	private static int peopleInSimulation = 0;
	
	/** Used to identify each person. */
	protected int id;
	
	/** This is used to generate a random integer number */
	public Random rand;
	
	/** Keeps track of current floor. */
	protected int currentFloor;
	
	/** Keeps track of destination floor. */
	protected int destFloor;
	
	/** Stores the required elevator capacity for this person. */
	protected int reqCapacity;
	
	/** Stores the max floor the person is allowed to go to. */
	protected int maxAllowedFloor;
	
	/** Stores the lowest floor (besides ground floor) that the person is allowed to go to. */
	protected int minAllowedFloor;
	
	/** Stores the tick when the person reaches their destination floor. */
	protected int reachedDestFloorTick;
	
	/** Stores the tick when person joined queue. */
	protected float joinedQueue;
	
	/**  Stores the tick when person left the queue. */
	protected float leftQueue;
	
	/** Takes note of whether person is currently in the queue or not. */
	protected boolean inQueue;
	
	
	/**
	 * Instantiates a new person.
	 *
	 * @param maxTicks The max ticks.
	 */
	public Person(int maxTicks, int currentTick) {
		this.inQueue = false;
		this.joinedQueue = 0;
		this.leftQueue = 0;
		this.reachedDestFloorTick = currentTick;
		this.id = peopleInSimulation;
		this.currentFloor = 0;
		this.reqCapacity = 1;
		this.maxAllowedFloor = 5;
		this.minAllowedFloor = 1;
		this.rand = new Random(this.id);
		this.destFloor = 0;
		generateDestFloor(true);
		
		people.put(this.id, this);
		peopleInSimulation++;
	}
	
	
	/**
	 * Clears people from the simulation.
	 */
	public static void clearPeople() {
		people.clear();
	}
	
	/**
	 * 	Generates a random destination floor
	 *
	 * @return boolean This will be true if a new destination floor is generated, false if not.
	 */
	public boolean generateDestFloor(boolean override) {
		boolean newFloorChosen  = false;
		
		if((Building.getRandomNum() < Building.getP() && destFloor == currentFloor) || override) {
			Random randFloor = new Random(this.id);
			do {
				this.destFloor = randFloor.nextInt(maxAllowedFloor+1);
			}while(destFloor < minAllowedFloor);
			
			newFloorChosen = true;
		}
		
		//System.out.println("P: "+Building.getP()+" Random num: "+Building.getRandomNum());
		return newFloorChosen;
	}
	

	
	/**
	 * Iterates through each person and executes their tick method.
	 *
	 * @param currentTick The current tick.
	 * @param maxTick The max tick.
	 */
	public static void tickAllPeople(int currentTick, int maxTick) {
		for(int i = 0; i < removeThesePeople.size(); i++) {
			people.remove(removeThesePeople.get(i));
		}
		Iterator<Integer> itr = people.keySet().iterator();
		while(itr.hasNext()) {
			people.get(itr.next()).tick(currentTick);
		}
	}
	
	/**
	 * Removes this person from the simulation.
	 */
	public boolean leaveBuilding() {		
		Building.removePerson(this);
		removeThesePeople.add(this.id);
		return true;
	}
	
	/**
	 * Calculates the amount of ticks the person waited for the elevator.
	 */
	public void recordWaitingTime() {
		float waitingTime = leftQueue - joinedQueue;
		Building.addWaitingTime(waitingTime);
	}
	
	/**
	 * Wipes static variables so a newly created building doesn't have any issues.
	 */
	public static void wipePeople() {
		removeThesePeople = new ArrayList<Integer>();
		peopleInSimulation=0;
	}
	
	/**
	 * Prints information about the current user.
	 */
	public void printInfo() {
		System.out.println(""+this.getClass().getSimpleName()+" | ID: "+id+" | Current floor: "+currentFloor+" | Dest floor: "+destFloor);
		System.out.println("---------------------------------------------------");
	}
	
	// -------- Main tick method ----------------------------------------------------------------- //
	
	/**
	 * Tick method for the person to keep them in sync with the rest of the simulation.
	 *
	 * @param currentTick The current tick.
	 */
	public void tick(int currentTick) {
		if(currentTick > 0) {
			// generates a new destination floor if the person is currently on their destination floor
			generateDestFloor(false);
		}
		
		// debug info, uncomment to see the information the next time the program is run.
		/*System.out.println("Person: "+id+" Current floor: "+currentFloor+" Dest floor: "+destFloor+
				" Required space: "+reqCapacity+ " Joined queue: "+joinedQueue+ " Left queue: "+leftQueue+
				" Queue wait time: "+(leftQueue-joinedQueue)+this.getClass());*/
		printInfo();
	
	}
	
	// -------- Getters -------------------------------------------------------------------------- //
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Gets the current floor.
	 *
	 * @return the current floor
	 */
	public int getCurrentFloor() {
		return this.currentFloor;
	}
	
	/**
	 * Gets the destination floor.
	 *
	 * @return the destination floor
	 */
	public int getDestinationFloor() {
		return this.destFloor;
	}
	
	/**
	 * Gets the req capacity.
	 *
	 * @return the req capacity
	 */
	public int getReqCapacity() {
		return this.reqCapacity;
	}
	
	/**
	 * Gets the people amount.
	 *
	 * @return the people amount
	 */
	public static int getPeopleAmount() {
		return people.size();
	}
	
	/**
	 * Gets the total people amount.
	 *
	 * @return the total people amount
	 */
	public static int getTotalPeopleAmount() {
		return peopleInSimulation;
	}
	
	/**
	 * Gets the joined queue.
	 *
	 * @return the joined queue
	 */
	public float getJoinedQueue() {
		return this.joinedQueue;
	}
	
	/**
	 * Gets the left queue.
	 *
	 * @return the left queue
	 */
	public float getLeftQueue() {
		return this.leftQueue;
	}
	
	
	// -------- Setters -------------------------------------------------------------------------- //
	
	/**
	 * Sets the current floor.
	 *
	 * @param floor the new current floor
	 */
	public void setCurrentFloor(int floor) {
		this.currentFloor = floor;
	}
	
	/**
	 * Sets the destination floor.
	 *
	 * @param floor the new destination floor
	 */
	public void setDestinationFloor(int floor) {
		this.destFloor = floor;
	}
	
	/**
	 * Sets the reached dest floor tick.
	 *
	 * @param tick the new reached dest floor tick
	 */
	public void setReachedDestFloorTick(int tick) {
		this.reachedDestFloorTick = tick;
	}
	
	/**
	 * Sets the joined queue.
	 *
	 * @param tick the new joined queue
	 */
	public void setJoinedQueue(int tick) {
		this.joinedQueue = tick;
	}
	
	/**
	 * Sets the left queue.
	 *
	 * @param tick the new left queue
	 */
	public void setLeftQueue(int tick) {
		this.leftQueue = tick;
	}
	
	/**
	 * Sets the in queue.
	 *
	 * @param inQueue the new in queue
	 */
	public void setInQueue(boolean inQueue) {
		this.inQueue = inQueue;
	}
}
