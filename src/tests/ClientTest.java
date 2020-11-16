package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import entities.Client;

class ClientTest {
	Client client = new Client(2880, 20);
	
	// Tests that clients will request to leave the building at the correct tick.
	@Test
	void testGoToGround() {
		client.setCurrentFloor(2);
		client.setDestinationFloor(2);
		client.setLeaveTick(100);
		
		client.goToGround(100);
		
		int actual = client.getDestinationFloor();
		
		assertEquals(0, actual);
	}

}
