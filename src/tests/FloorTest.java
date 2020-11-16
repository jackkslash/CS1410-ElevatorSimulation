package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import entities.Developer;
import entities.Elevator;
import entities.Floor;

class FloorTest {
	Developer dev = new Developer(2880, 20);
	Developer dev2 = new Developer(2880, 20);
	Developer dev3 = new Developer(2880, 20);

	// Tests that people can be moved from the elevator to the floor if it is their destination floor.
	@Test
	void testElevatorToFloor() {
		Elevator.clearElevators();
		Elevator elevator = new Elevator();
		int fl = 2;
		
		Floor floor = new Floor();
		floor.setFloorNum(fl);
		
		dev.setDestinationFloor(fl);
		elevator.addPerson(dev, 20);
		elevator.setFloor(fl);
		
		floor.elevatorToFloor(20, 2880);
		
		int actual = dev.getCurrentFloor();
		
		assertEquals(fl, actual);
	}
	
	// Tests that the person will not be removed from the elevator if the elevator is not on their destination floor.
	@Test
	void testElevatorToFloor2() {
		Elevator.clearElevators();
		Elevator elevator = new Elevator();
		
		Floor floor = new Floor();
		floor.setFloorNum(3);
		
		dev.setDestinationFloor(5);
		elevator.addPerson(dev, 20);
		elevator.setFloor(2);
		
		floor.elevatorToFloor(20, 2880);
		
		int actual = dev.getCurrentFloor();
		
		assertEquals(0, actual);
	}

	// Tests that a person can be added to the elevator queue when they have a destination floor different to their current floor.
	@Test
	void testFloorToQueue() {
		Elevator.clearElevators();
		Elevator elevator = new Elevator();
		
		Floor floor = new Floor();
		floor.setFloorNum(3);
		
		dev2.setDestinationFloor(4);
		floor.addToFloor(dev2);
		
		floor.floorToQueue(20);
		
		float actual = dev2.getJoinedQueue();
		
		assertEquals(20, actual);
	}
	
	// Test that a person can be added to the elevator.
	@Test
	void testQueueToElevator() {
		Elevator.clearElevators();
		Elevator elevator = new Elevator();
		Developer dev = new Developer(2880, 20);
		
		Floor floor = new Floor();
		floor.addToQueue(dev);
		
		floor.queueToElevator(20);
		
		assertEquals(20, dev.getLeftQueue());
	}
	
	// Tests that the destinations of the people who are in the elevator queue are being added to the elevators destinations.
	@Test
	void testCallElevator() {
		Elevator.clearElevators();
		Elevator elevator = new Elevator();
		Developer dev = new Developer(2880, 20);
		dev.setDestinationFloor(4);
		
		Floor floor = new Floor();
		floor.addToQueue(dev);
		floor.setFloorNum(2);
		
		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.add(2);
		expected.add(4);
		
		floor.callElevator();
		
		ArrayList<Integer> actual = elevator.getDestFloorsArrayList();
		
		
		assertEquals(expected, actual);
	}
	
	// Test that a person can be removed from the floor.
	@Test
	void testRemoveFloorPerson() {
		Elevator.clearElevators();
		Elevator elevator = new Elevator();
		Developer dev = new Developer(2880, 20);
		int devId = dev.getId();
		
		Floor floor = new Floor();
		floor.addToFloor(dev);
		
		boolean actual = floor.removeFloorPerson(devId);
		
		
		assertEquals(true, actual);
	}
	
	// Test that the floor performs the correct instructions based on the current tick and whether the current floor is a destination for the elevator.
	@Test
	void testTick() {
		Elevator.clearElevators();
		Elevator elevator = new Elevator();
		elevator.setFloor(2);
		
		Floor floor = new Floor();
		floor.setFloorNum(2);
		
		int actual = floor.tick(20, 2880);
		
		
		assertEquals(2, actual);
	}
	
	

}
