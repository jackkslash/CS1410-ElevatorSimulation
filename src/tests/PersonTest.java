package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import entities.Building;
import entities.Developer;

class PersonTest {
	Building building = new Building(2880, 1, 1, 20, 5, 5, 1);
	
	// Test that a person can generate a new floor.
	@Test
	void testGenerateDestFloor() {
		Developer dev = new Developer(2880, 20);
		dev.setDestinationFloor(1);
		dev.setCurrentFloor(1);
		
		boolean actual = dev.generateDestFloor(false);
		
		assertEquals(true, actual);
	}

	/*@Test
	void testLeaveBuilding() {
		fail("Not yet implemented");
	}*/

}
