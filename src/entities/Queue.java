package entities;

import java.util.ArrayList;

/**
 * The Class Queue.
 */
public class Queue {
	
	/** Holds the people in the queue. */
	private ArrayList<Person> queue;
	
	/**
	 * Instantiates a new queue.
	 */
	public Queue() {
		this.queue = new ArrayList<Person>();
	}

	/**
	 * Gets the size of the queue.
	 *
	 * @return int This is the size of the queue (how many people are in it).
	 */
	public int getSize() {
		return queue.size();
	}

	/**
	 * Get a person in the queue.
	 *
	 * @param index This is the index of the person in the queue.
	 * @return Person This is the person that was at the index specified by the parameter.
	 */
	public Person getPersonAt(int index) {
		return queue.get(index);
	}
	
	/**
	 * Removes a person from the queue.
	 *
	 * @param index This is the index of the person in the queue that will be removed.
	 */
	public void removePersonAt(int index) {
		queue.remove(index);
	}
	
	/**
	 * Gets the front person.
	 *
	 * @return Person This is the person at the front of the queue.
	 */
	public Person getFrontPerson() {
		return queue.get(0);
	}

	/**
	 * Removes the person at the front of the queue.
	 */
	public void removeFrontPerson() {
		queue.remove(0);
		
	}

	/**
	 * Adds a person.
	 *
	 * @param person This is the person that will be added to the queue.
	 */
	public void addPerson(Person person) {
		queue.add(person);
	}

	/**
	 * Gets the queue.
	 *
	 * @return ArrayList(Person) This returns the queue as an array list
	 */
	public ArrayList<Person> getQueue() {
		return queue;
	}

}
