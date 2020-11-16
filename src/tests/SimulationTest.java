package tests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import simulationMain.Simulation;

class SimulationTest {
	
	// Test that only the correct input is accepted.
	@Test
	void testReadInput() {
		// requires input in command line when running command
		System.out.println("-------------------------- input a number above 0 --------------------------");
		
		
		Simulation sim = new Simulation();
		int input = sim.readInput("Please input a number");
		
		boolean actual = false;
		
		if(input >= 0) {
			actual = true;
		}
		
		assertEquals(true, actual);
	}
	
	// Test that only the correct input is accepted.
	@Test
	void testReadPQ() {
		// requires input in command line when running command
		System.out.println("-------------------------- input a decimal number above 0 --------------------------");
		
		Simulation sim = new Simulation();
		double input = sim.readPQ("Input a decimal number");
		
		boolean actual = false;
		
		if(input >= 0) {
			actual = true;
		}
		
		assertEquals(true, actual);
	}
	
	// Test to check if the simulation will stop when the user inputs certain values.
	@Test
	void testCheckPQ() {
		// requires input in command line when running command
		System.out.println("-------------------------- input two decimal numbers above 0 --------------------------");
		Simulation sim = new Simulation();
		
		boolean actual = sim.checkPQ();
		
		
		assertEquals(true, actual);
	}
	
	// Test that the simulation can keep track of how many times it is ran.
	@Test
	void testTick() {
		// requires input in command line when running command
		System.out.println("-------------------------- run the sim twice --------------------------");
		
		
		
		Simulation sim = new Simulation();
		int actual = sim.tick(5);
		
		
		assertEquals(2, actual);
	}

}
