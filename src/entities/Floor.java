package entities;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Class Floor.
 */
public class Floor {
	
	/** Keeps track of floors that have been created. */
	private static ArrayList<Floor> floors = new ArrayList<Floor>();
	
	/** Queue to hold people on this floor & don't need the elevator. */
	private ArrayList<Person> peopleOnFloor; 
	
	/** Create a queue to hold people that are on this floor and are waiting for the elevator. */
	private Queue elevatorQueue;
	
	/** This floors number. */
	private int floorNum;
	
	/**
	 * Instantiates a new floor.
	 */
	public Floor() {
		this.peopleOnFloor = new ArrayList<Person>();
		this.floorNum = floors.size();
		this.elevatorQueue = new Queue();
		
		floors.add(this);
	}
	
	/**
	 * Moves the people from the elevator to this floor.
	 *
	 * @param currentTick The current tick.
	 * @param maxTick The max tick.
	 */
	public void elevatorToFloor(int currentTick, int maxTick) {
		// create a variable to hold the people in the elevator
		ArrayList<Person> people = Elevator.getElevator().getPeopleInElevator();
		// iterate through all people in the elevator
		for(int i = 0; i < people.size(); i++) {
			// if the person is supposed to be on this floor
			if(people.get(i).getDestinationFloor() == this.floorNum) {
				// update their current floor variable
				people.get(i).setCurrentFloor(floorNum);
				// update the persons tick variable when they reach dest floor
				people.get(i).setReachedDestFloorTick(currentTick);
				// add the person to this floor
				peopleOnFloor.add(people.get(i));
				// remove the person from the elevator
				Elevator.getElevator().removePerson(i, currentTick);
			}
		}
	}
	
	/**
	 * 	Move people from the floor to the elevator queue when they have a new destination.
	 *
	 * @param currentTick The current tick.
	 */
	public void floorToQueue(int currentTick) {
		ArrayList<Person> copy = peopleOnFloor;
		// iterate through people on this floor
		for(int i = 0; i < peopleOnFloor.size(); i++) {
			// store instance of person at index i
			Person person = peopleOnFloor.get(i);
			// if the persons destination is different to current floor
			if(person.getDestinationFloor() != person.getCurrentFloor()) {
				// add the person to the elevator queue
				elevatorQueue.addPerson(peopleOnFloor.get(i));
				// update the tick when they joined the queue
				person.setJoinedQueue(currentTick);
				// remove the person from floor
				copy.remove(i);
			}
		}
		
		peopleOnFloor = copy;
	}
	
	/**
	 * 	Move people from the queue to the elevator.
	 *
	 * @param currentTick the current tick
	 */
	public void queueToElevator(int currentTick) {
		int liftMaxCap = Elevator.getElevator().getMaxCapacity(); // stores the max capacity of the elevator
		int liftCurrentCap = Elevator.getElevator().getCurrentCapacity(); // stores the current capacity of the elevator
		ArrayList<Integer> removeAtIndexs = new ArrayList<Integer>(); // keeps track of the people that need to be removed from the elevator queue once they have been added to the elevator
		
		int i = 0; // counter for while loop
		// while the lift has room and there are people waiting for the lift
		while(Elevator.getElevator().getCurrentCapacity() < Elevator.getElevator().getMaxCapacity() && i < elevatorQueue.getSize()) {
			Person person = elevatorQueue.getPersonAt(i); // create instance of the person at index i
			int personReqCap = person.getReqCapacity(); // stores the capacity required by the person
			
			// if the person is able to fit in the elevator
			if(personReqCap <= liftMaxCap-liftCurrentCap) {
				// add the person to the elevator
				Elevator.getElevator().addPerson(person, currentTick);
				// take note of the tick when the person left the queue
				person.setLeftQueue(currentTick);
				// record the amount of time the person waited for the elevator
				person.recordWaitingTime();
				// makes note of the person that needs to be removed from the queue
				removeAtIndexs.add(i);
				liftCurrentCap = Elevator.getElevator().getCurrentCapacity(); // stores the current capacity of the elevator
			}
			
			// increment counter
			i++;
		}
		
		Collections.sort(removeAtIndexs, Collections.reverseOrder());
		for(int index : removeAtIndexs) {
			elevatorQueue.removePersonAt(index);
		}
	}
	
	/**
	 * Calls the elevator when there are people in this floors elevator queue.
	 */
	public void callElevator() {
		for(Person person : elevatorQueue.getQueue()) {
			Elevator.getElevator().addDest(floorNum);
			Elevator.getElevator().addDest(person.getDestinationFloor());
		}
	}
	
	/**
	 * Calls the tick function for each floor.
	 *
	 * @param currentTick The current tick.
	 * @param maxTick The max tick.
	 */
	public static void tickAllFloors(int currentTick, int maxTick) {
		for(Floor floor : floors) {
			floor.tick(currentTick, maxTick);
		}
	}
	
	/**
	 * Adds a person to ground floor.
	 *
	 * @param person The person to be added to the floor.
	 * @param currentTick The current tick.
	 */
	public static void addToGroundFloor(Person person, int currentTick) {
		floors.get(0).peopleOnFloor.add(person);
		System.out.println("Added person: "+person.getId()+" to ground floor");
	}
	
	/**
	 * Removes a person based on their id number.
	 *
	 * @param id The ID number of the person that should be removed from the floor.
	 * @return boolean This returns true if the person was removed, false if the person wasn't removed.
	 */
	public boolean removeFloorPerson(int id) {
		boolean personRemoved = false;
		
		for (int i = 0; i < peopleOnFloor.size(); i++) {
			if(peopleOnFloor.get(i).getId() == id) {
				peopleOnFloor.remove(i);
				personRemoved = true;
			}
		}
		return personRemoved;
	}
	
	/**
	 * Adds a person to the current floor.
	 *
	 * @param person The person to be added to the floor.
	 */
	public void addToFloor(Person person) {
		this.peopleOnFloor.add(person);
	}
	
	/**
	 * Clears the static array list.
	 */
	public static void wipeFloors() {
		floors.clear();
	}
	
	/**
	 * 	Add person to elevator queue.
	 *
	 * @param person The person to be added to the queue.
	 */
	public void addToQueue(Person person) {
		this.elevatorQueue.addPerson(person);
	}
	
	
	// -------- getters --------------------------------------------------------------------- //
	
	/**
	 * Gets the floors size.
	 *
	 * @return the floors size
	 */
	public static int getFloorsSize() {
		return floors.size();
	}
	
	/**
	 * Gets the floor.
	 *
	 * @param floorNum the floor num
	 * @return the floor
	 */
	public static Floor getFloor(int floorNum) {
		return floors.get(floorNum);
	}
	
	// -------- setters --------------------------------------------------------------------- //
	
	/**
	 * Sets the floor num.
	 *
	 * @param floor the new floor num
	 */
	public void setFloorNum(int floor) {
		this.floorNum = floor;
	}
	
	
	// -------- main tick method ------------------------------------------------------------ //
	
	/**
	 * Tick method to keep the floors running in sync with the rest of the building.
	 *
	 * @param currentTick The current tick.
	 * @param maxTick The max tick.
	 * @return int This indicates which of the two if statements within the function were able to be executed. (For testing purposes)
	 */
	public int tick(int currentTick, int maxTick) {
		int ifsComplete = 0;
		
		if(currentTick > 0) {
			floorToQueue(currentTick); // check if people need to be added to elevator queue
			callElevator(); // call elevator
			ifsComplete = 1;
			// if lift is currently on this floor
			if(Elevator.getElevator().getFloor() == this.floorNum) {
				elevatorToFloor(currentTick, maxTick); // check if anybody in the elevator needs to be moved to this floor
				queueToElevator(currentTick); // check if anybody can be moved to the elevator
				ifsComplete = 2;
			}
		}
		
		// print stats about floor
		System.out.println("Floor: "+floorNum+" - People on floor: "+peopleOnFloor.size()+" - People in elevator queue: "+elevatorQueue.getSize());
		/*for(Person person : peopleOnFloor) {
			System.out.println(person.getClass().getSimpleName()+" Current floor: "+person.getCurrentFloor()+" Destination floor: "+
		person.getDestinationFloor()+" ID: "+person.getId());
		}*/
		/*System.out.println("People on floor: "+peopleOnFloor.size());
		System.out.println("People in elevator queue: "+elevatorQueue.getSize());
		System.out.println("--------------------------------------------");*/
		
		return ifsComplete;
	}
}
