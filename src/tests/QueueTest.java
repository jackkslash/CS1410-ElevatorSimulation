package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import entities.Developer;
import entities.Queue;

class QueueTest {
	Queue queue = new Queue();

	// Test that a person can be removed from the front of a queue.
	@Test
	void testRemoveFrontPerson() {
		Developer dev = new Developer(2880, 20);
		Developer dev2 = new Developer(2880, 20);
		
		queue.addPerson(dev);
		queue.addPerson(dev2);
		
		queue.removeFrontPerson();
		
		int actual = queue.getSize();
		
		assertEquals(1, actual);
	}

}
