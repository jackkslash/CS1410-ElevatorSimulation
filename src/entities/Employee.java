package entities;

/**
 * The Class Employee.
 */
public class Employee extends Person{
	
	
	/**
	 * Instantiates a new employee.
	 *
	 * @param maxTicks The simulations max ticks.
	 */
	public Employee(int maxTicks, int currentTick) {
		super(maxTicks, currentTick); // calling Person constructor
		
		this.maxAllowedFloor = Building.getTopFloor(); // set max allowed floor
		this.minAllowedFloor = 0; // minimum allowed floor is ground floor
	}
}
