package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import entities.MaintenanceCrew;

class MaintenanceCrewTest {
	MaintenanceCrew mCrew = new MaintenanceCrew(2880, 20);
	
	// Test that the maintenance crew can request to leave the building.
	@Test
	void testGoToGround() {
		mCrew.setCurrentFloor(2);
		mCrew.setDestinationFloor(2);
		mCrew.setLeaveTick(100);
		
		mCrew.goToGround(100);
		
		int actual = mCrew.getDestinationFloor();
		
		assertEquals(0, actual);
	}

}
