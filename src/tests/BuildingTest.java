package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import entities.Building;
import entities.Person;

class BuildingTest {
	Building building = new Building(2880, 0.005, 1, 20, 5, 5, 1);
	
	
	// Tests that employees can be generated.
	@Test
	void testGenerateEmployee() {
		Person.clearPeople();
		building.generateEmployee(5, 20);
		
		int actual = Person.getPeopleAmount();
		
		assertEquals(5, actual);
	}
	
	// Tests that clients can be generated.
	@Test
	void testGenerateClient() {
		Person.clearPeople();
		
		building.tick(20, 2880);
		
		int actual = building.getClientsEntered();
		
		assertEquals(1, actual);
	}
	
	// tests that maintenance crews can be generated.
	@Test
	void testGenerateMaintenanceCrew() {
		Person.clearPeople();
		building.setMaintenanceProb(1);
		
		building.tick(20, 2880);
		
		int actual = building.getMaintenanceCrewEntered();
		
		assertEquals(1, actual);
	}

}
