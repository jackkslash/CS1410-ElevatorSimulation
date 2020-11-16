package entities;

import java.util.ArrayList;
import java.util.Collections;

// TODO: Auto-generated Javadoc
/**
 * The Class Elevator.
 */
public class Elevator {
	
	/** The elevators. */
	private static ArrayList<Elevator> elevators = new ArrayList<Elevator>();
	
	/** The doors open. */
	private boolean doorsOpen; 	// check if elevator doors are open
	
	/** The floor. */
	private int floor; // keep track of current floor elevator is on
	
	/** The dest floors. */
	private ArrayList<Integer> destFloors; // keeps track of destination floors
	
	/** The last entered. */
	private int lastEntered; // keeps track of the last tick someone entered
	
	/** The last left. */
	private int lastLeft; // keeps track of the last tick someone left
	
	/** The current capacity. */
	private int currentCapacity; // keeps track of current capacity
	
	/** The max capacity. */
	private int maxCapacity; // keeps track of max allowed capacity
	
	/** The in elevator. */
	private ArrayList<Person> inElevator; // stores the people in the elevator
	
	/** The going up. */
	private boolean goingUp; // keeps track of the direction the elevator is going
	
	/**
	 * Instantiates a new elevator.
	 */
	public Elevator() {
		this.doorsOpen = false;
		this.floor = 0;
		this.destFloors = new ArrayList<Integer>();
		this.currentCapacity = 0;
		this.maxCapacity = 4;
		this.inElevator = new ArrayList<Person>();
		this.goingUp = true;
		
		elevators.add(this);
	}
	
	/**
	 * Allow people in lift.
	 *
	 * @return true, if successful
	 */
	public boolean allowPeopleInLift() {
		boolean letPeopleIn = false;
		
		if(!doorsOpen) {
			// check if current floor is a destination
			for(int i = 0; i < destFloors.size(); i++) {
				if(destFloors.get(i) == floor) {
					// check if there is space in the elevator or if people want to leave the elevator
					if(this.currentCapacity < this.maxCapacity || currentFloorRequired() == true) {
						openDoors();
						destFloors.remove(i);
						letPeopleIn = true;
					}else {
						destFloors.remove(i);
					}
				}
			}
			System.out.println("Elevator doors open: "+doorsOpen);
		}
		return letPeopleIn;
	}
	
	/**
	 * Move.
	 *
	 * @param currentTick the current tick
	 */
	// moves the elevator to destination floors
	public void move(int currentTick) {
		Collections.sort(destFloors); // sorts destination floors in numerical order
		// tells the building which way the elevator is going to be moving
		if(this.floor == 0) {
			goingUp = true;
		}
		

		
		// check if doors are closed and if there are destinations
		if(!doorsOpen && destFloors.size()>0) {
			// check if elevator is going up and if there are destinations
			if(goingUp) {
				// iterate through destination floors
				for(int i = 0; i < destFloors.size(); i++) {
					// if there exists a destination that is above this floor
					if(destFloors.get(i) > this.floor) {
						// go up
						goingUp = true;
						floor++;
						allowPeopleInLift();
						break;
					// else if there are no floors above current floor
					}else {
						// tell elevator to go down
						goingUp = false;
					}
				}
			}
			
			// if the elevator is going down
			if(!goingUp) {
				// iterate through destination floors
				for(int i = 0; i < destFloors.size(); i++) {
					// if there is a floor that is below current floor
					if(destFloors.get(i) < this.floor) {
						// go down
						floor--;
						allowPeopleInLift();
						break;
					}else {
						goingUp = true;
						floor++;
						allowPeopleInLift();
						break;
					}
				}
			}
		}
		// check if it has been one or more ticks since the last person entered the elevator & doors are open
		else if((currentTick - lastEntered >= 1) && doorsOpen) {
			// close the doors
			closeDoors();
		}
		else if(!doorsOpen && destFloors.size() == 0 && floor == 0) {
			System.out.println("No requests for lift. Staying on ground floor.");
		}
		else if(!doorsOpen && destFloors.size() == 0 && floor > 0) {
			floor--;
			allowPeopleInLift();
		}
	}
	
	/**
	 * Current floor required.
	 *
	 * @return true, if successful
	 */
	// checks if somebody in the elevator wants to leave on the current floor
	public boolean currentFloorRequired() {
		for(Person person : inElevator) {
			if(person.getDestinationFloor() == floor) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Open doors.
	 */
	// opens elevator doors
	public void openDoors() {
		this.doorsOpen = true;
	}
	
	/**
	 * Close doors.
	 */
	// closes elevators doors
	public void closeDoors() {
		this.doorsOpen = false;
	}
	
	/**
	 * Adds the dest.
	 *
	 * @param dest the dest
	 * @return true, if successful
	 */
	// add destination to elevator to elevator
	public boolean addDest(int dest) {
		boolean addFloor = true;
		
		for(int floor : destFloors) {
			if(floor == dest) {
				addFloor = false;
			}
		}
		
		if(addFloor) {
			this.destFloors.add(dest);
		}
		
		return addFloor;
	}
	
	/**
	 * Adds the person.
	 *
	 * @param person the person
	 * @param currentTick the current tick
	 * @return true, if successful
	 */
	// adds person to the elevator
	public boolean addPerson(Person person, int currentTick) {
		boolean personAdded = false;
		
		if(currentCapacity + person.getReqCapacity() <= maxCapacity) {
			inElevator.add(person);
			this.currentCapacity = inElevator.size();
			lastEntered = currentTick;
			personAdded = true;
		}
		
		return personAdded;
	}
	
	/**
	 * Removes the person.
	 *
	 * @param index the index
	 * @param currentTick the current tick
	 */
	// removes person from the elevator
	public void removePerson(int index, int currentTick) {
		inElevator.remove(index);
		this.currentCapacity = inElevator.size();
		lastEntered = currentTick;
	}
	
	/**
	 * Clear elevators.
	 */
	// clear elevators
	public static void clearElevators() {
		elevators.clear();
	}
	
	// ------------- Getters ----------------------------------------------------------------//
	
	/**
	 * Gets the people in elevator.
	 *
	 * @return the people in elevator
	 */
	// returns an array list of all people in the elevator
	public ArrayList<Person> getPeopleInElevator(){
		return inElevator;
	}
	
	/**
	 * Gets the floor.
	 *
	 * @return the floor
	 */
	public int getFloor() {
		return this.floor;
	}
	
	/**
	 * Gets the current capacity.
	 *
	 * @return the current capacity
	 */
	public int getCurrentCapacity() {
		return this.currentCapacity;
	}
	
	/**
	 * Gets the max capacity.
	 *
	 * @return the max capacity
	 */
	public int getMaxCapacity() {
		return this.maxCapacity;
	}
	
	/**
	 * Gets the elevator.
	 *
	 * @return the elevator
	 */
	public static Elevator getElevator() {
		return elevators.get(0);
	}
	
	/**
	 * Gets the dest floors.
	 *
	 * @return the dest floors
	 */
	public String getDestFloors() {
		String floors = "";
		
		for(int floor : destFloors) {
			floors+=floor+", ";
		}
		
		return floors;
	}
	
	/**
	 * Gets the dest floors array list.
	 *
	 * @return the dest floors array list
	 */
	public ArrayList<Integer> getDestFloorsArrayList(){
		return destFloors;
	}
	
	/**
	 * Doors open.
	 *
	 * @return true, if successful
	 */
	public boolean doorsOpen() {
		return doorsOpen;
	}
	
	// ------------- Setters ----------------------------------------------------------------//
	
	/**
	 * Sets the floor.
	 *
	 * @param floor the new floor
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}
	
	/**
	 * Sets the current capacity.
	 *
	 * @param cap the new current capacity
	 */
	public void setCurrentCapacity(int cap) {
		this.currentCapacity = cap;
	}
	
	/**
	 * Tick.
	 *
	 * @param currentTick the current tick
	 */
	// ------- main tick method --------------------------------------------------------------//
	public void tick(int currentTick) {
		if(currentTick > 0) {
			this.currentCapacity = inElevator.size();
			move(currentTick);
		}
		
		// print stats about elevator
		System.out.println(inElevator.size());
		System.out.println("Elevator currently at floor: "+floor+" People in lift: "+currentCapacity+ " Destinations: "+getDestFloors());
	}
}
