package entities;




/**
 * The Class Client.
 */
public class Client extends Person implements LeaveBuilding{
	
	/** This is a random number which will be added onto the current tick value, signalling when the client will request the ground floor so they can leave the building */
	private int leaveAtTick;
	
	/** This is the actual tick when the client will request the elevator to ground floor (or if they are already on ground floor then will leave). */
	private int leaveTick;
	
	/** This is used to signal that the client is ready to leave the building. */
	private boolean leaveBuilding;
	
	/**
	 * Instantiates a new client.
	 *
	 * @param maxTicks Maximum ticks for the simulation.
	 */
	public Client(int maxTicks, int currentTick) {
		super(maxTicks, currentTick); // calling Person constructor
		
		this.leaveBuilding = false;
		this.leaveAtTick = generateLeaveAtTick();
		this.destFloor = rand.nextInt(5);
		this.leaveTick = 10000;
	}
	
	/**
	 * Tick function which implements the clients functionality.
	 *
	 * @param currentTick The simulations current tick.
	 */
	@Override
	public void tick(int currentTick) {
		leaveTick = reachedDestFloorTick+leaveAtTick;
		if(currentTick > 0) {
			goToGround(currentTick);
			if(currentFloor == 0 && leaveBuilding) {
				leaveBuilding();
			}
		}
		
		// Print debug information, uncomment to see when the program is run.
		/*System.out.println("Person: "+id+" Current floor: "+currentFloor+" Dest floor: "+destFloor+
				" Required space: "+reqCapacity+ " Client leave at tick: "+leaveTick+" Generated tick: "+leaveAtTick
				+" Reached dest floor: "+reachedDestFloorTick+ " Joined queue: "+joinedQueue+ " Left queue: "+leftQueue+
				" Queue wait time: "+(leftQueue-joinedQueue));*/
		printInfo();
		System.out.println(" Client leave at tick: "+leaveTick+" Generated tick: "+leaveAtTick
				+" Reached dest floor: "+reachedDestFloorTick+ " Joined queue: "+joinedQueue+ " Left queue: "+leftQueue+
				" Queue wait time: "+(leftQueue-joinedQueue));
	}
	
	/**
	 * Generate dest floor.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean generateDestFloor(boolean override) {
		return false;
	}
	
	/**
	 * Generate leave at tick.
	 *
	 * @return int This is the tick that will be added onto the current tick, therefore determining when the client will request to leave.
	 */
	public int generateLeaveAtTick() {
		int newTick = 0;
		
		do {
			newTick = rand.nextInt(181);
		}while(newTick < 60);
		
		return newTick;
	}
	
	/**
	 * Lets the client request the ground floor when they want to leave the building.
	 *
	 * @param currentTick The current tick.
	 */
	public void goToGround(int currentTick) {
		if(destFloor == currentFloor && leaveTick == currentTick) {
			destFloor = 0;
			leaveBuilding = true;
		}
	}
	
	
	// ---------------------------------------- setters ---------------------------------------- //
	
	/**
	 * Sets the leave tick.
	 *
	 * @param tick the new leave tick
	 */
	public void setLeaveTick(int tick) {
		this.leaveTick = tick;
	}
}
