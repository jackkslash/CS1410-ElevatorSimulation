package entities;

/**
 * The Class Developer.
 */
public class Developer extends Person{
	
	/** The team. */
	private String team; // TODO dev team functionality
	
	/**
	 * Instantiates a new developer.
	 *
	 * @param maxTicks The simulations max ticks.
	 */
	public Developer(int maxTicks, int currentTick) {
		super(maxTicks, currentTick); // calling Person constructor
		
		this.maxAllowedFloor = Building.getTopFloor(); // set max allowed floor
		this.minAllowedFloor = (Building.getTopFloor()-1)/2; // minimum allowed floor is the middle floor
	}
}
