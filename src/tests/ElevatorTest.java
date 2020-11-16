package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import entities.Developer;
import entities.Elevator;

class ElevatorTest {

	// Tests that destinations can be added to the elevator.
	@Test
	void testAddDest() {
		Elevator elevator = new Elevator();
		List<Integer> nums = Arrays.asList(1, 2, 3, 4);
		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.addAll(nums);
		
		elevator.addDest(1);
		elevator.addDest(2);
		elevator.addDest(3);
		elevator.addDest(4);
		
		ArrayList<Integer> actual = elevator.getDestFloorsArrayList();
		
		assertEquals(expected, actual);
	}
	
	// Tests that duplicate destinations wont be accepted.
	@Test
	void testAddDest2() {
		Elevator elevator = new Elevator();
		elevator.addDest(1);
		elevator.addDest(2);
		elevator.addDest(3);
		elevator.addDest(4);
		
		boolean actual = elevator.addDest(4);
		boolean expected = false;
		
		assertEquals(expected, actual);
	}

	// Tests that the elevator will not let people in when its capacity is full.
	@Test
	void testAddPerson() {
		Elevator elevator = new Elevator();
		Developer dev = new Developer(2880, 20);
		
		elevator.addPerson(dev, 20);
		elevator.addPerson(dev, 20);
		elevator.addPerson(dev, 20);
		elevator.addPerson(dev, 20);
		
		boolean actual = elevator.addPerson(dev, 20);
		
		assertEquals(false, actual);
	}
	
	// Tests that people can be added to the elevator when there is enough space inside the elevator.
	@Test
	void testAddPerson2() {
		Elevator elevator = new Elevator();
		Developer dev = new Developer(2880, 20);
		
		elevator.addPerson(dev, 20);
		elevator.addPerson(dev, 20);
		
		boolean actual = elevator.addPerson(dev, 20);
		
		assertEquals(true, actual);
	}
	
	// Tests that people can be removed from the elevator.
	@Test
	void testRemovePerson() {
		Elevator elevator = new Elevator();
		Developer dev = new Developer(2880, 20);
		Developer dev2 = new Developer(2880, 20);
		elevator.addPerson(dev, 20);
		elevator.addPerson(dev2, 20);
		
		elevator.removePerson(0, 20);
		
		assertEquals(1, elevator.getCurrentCapacity());
	}
	
	// Tests whether the elevator can identify when it is able to take in more passengers, based on the fact that there will be a person leaving the elevator.
	@Test
	void testAllowPeopleInLift() {
		Elevator elevator = new Elevator();
		Developer dev = new Developer(2880, 20);
		Developer dev1 = new Developer(2880, 20);
		Developer dev2 = new Developer(2880, 20);
		Developer dev3 = new Developer(2880, 20);
		dev.setDestinationFloor(2);
		dev1.setDestinationFloor(4);
		dev2.setDestinationFloor(4);
		dev3.setDestinationFloor(4);
		
		elevator.closeDoors();
		elevator.setFloor(2);
		elevator.addPerson(dev, 20);
		elevator.addPerson(dev1, 20);
		elevator.addPerson(dev2, 20);
		elevator.addPerson(dev3, 20);
		
		elevator.addDest(1);
		elevator.addDest(2);
		elevator.addDest(3);
		elevator.addDest(4);
		
		boolean actual = elevator.allowPeopleInLift();
		
		assertEquals(true, actual);
	}
	
	// Test if the elevator will let people in when there are people on the same floor as the elevator and there is space in the elevator.
	@Test
	void testAllowPeopleInLift1() {
		Elevator elevator = new Elevator();
		
		elevator.closeDoors();
		elevator.setFloor(2);
		elevator.addDest(2);
		
		boolean actual = elevator.allowPeopleInLift();
		
		assertEquals(true, actual);
	}
	
	// Test whether the elevator will know to go past a requested floor if the capacity is full and nobody in 
	// the elevator needs the requested floor (it is requested by somebody else on the floor).
	@Test
	void testAllowPeopleInLift2() {
		Elevator elevator = new Elevator();
		elevator.closeDoors();
		elevator.setFloor(2);
		elevator.setCurrentCapacity(4);
		
		elevator.addDest(2);
		
		boolean actual = elevator.allowPeopleInLift();
		
		assertEquals(false, actual);
	}
	
	// Test if the elevator knows when the doors are already open.
	@Test
	void testAllowPeopleInLift3() {
		Elevator elevator = new Elevator();
		elevator.openDoors();
		elevator.setFloor(2);
		
		elevator.addDest(2);
		
		boolean actual = elevator.allowPeopleInLift();
		
		assertEquals(false, actual);
	}
	
	// Test if the elevator knows when a person in the elevator needs to leave at the current floor.
	@Test
	void testCurrentFloorRequired() {
		Elevator elevator = new Elevator();
		Developer dev = new Developer(2880, 20);
		dev.setDestinationFloor(2);
		
		elevator.addPerson(dev, 20);
		
		elevator.setFloor(2);
		
		elevator.addDest(2);
		
		boolean actual = elevator.currentFloorRequired();
		
		assertEquals(true, actual);
	}
	
	// Tests that the elevator will go past a floor if it is not requested.
	@Test
	void testCurrentFloorRequired2() {
		Elevator elevator = new Elevator();
		Developer dev = new Developer(2880, 20);
		dev.setDestinationFloor(3);
		
		elevator.addPerson(dev, 20);
		
		elevator.setFloor(2);
		
		elevator.addDest(2);
		
		boolean actual = elevator.currentFloorRequired();
		
		assertEquals(false, actual);
	}
	
}






































