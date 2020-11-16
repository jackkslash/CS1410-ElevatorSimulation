package entities;


// TODO: Auto-generated Javadoc
/**
 * The Class MaintenanceCrew.
 */
public class MaintenanceCrew extends Person implements LeaveBuilding{
	
	/**  This is a random number which will be added onto the current tick value, signalling when the maintenance crew will request the ground floor so they can leave the building. */
	private int leaveAtTick;
	
	/** This is the actual tick when the maintenance crew will request the elevator to ground floor (or if they are already on ground floor then will leave). */
	private int leaveTick;
	
	/** This is used to signal that the maintenance crew is ready to leave the building. */
	private boolean leaveBuilding;

	/** The left building. */
	private boolean leftBuilding = false;
	
	/** The been to top. */
	private boolean beenToTop = false;
	
	
	/**
	 * Instantiates a new maintenance crew.
	 *
	 * @param maxTicks Maximum ticks for the simulation.
	 * @param currentTick the current tick
	 */
	public MaintenanceCrew(int maxTicks, int currentTick) {
		super(maxTicks, currentTick); // calling Person constructor
		
		this.reqCapacity = 4;

		this.reachedDestFloorTick = 10000;
		this.leaveBuilding = false;
		this.leaveAtTick = generateLeaveAtTick();
		this.destFloor = 6;
		this.leaveTick = 10000;
	}
	
	/**
	 * Tick function which implements the maintenance crews functionality.
	 *
	 * @param currentTick The simulations current tick.
	 */
	@Override
	public void tick(int currentTick) {
		if(currentFloor == 6) {
			beenToTop = true;
		}
		
		leaveTick = reachedDestFloorTick+leaveAtTick;
		if(currentTick > 0) {
			goToGround(currentTick);
			if(currentFloor == 0 && leaveBuilding) {
				leftBuilding = leaveBuilding();
			}
		}
		
		// Print debug information, uncomment to see when the program is run.
		/*System.out.println("Person: "+id+" Current floor: "+currentFloor+" Dest floor: "+destFloor+
				" Required space: "+reqCapacity+ " Maintenance crew leave at tick: "+leaveTick+" Generated tick: "+leaveAtTick
				+" Reached dest floor: "+reachedDestFloorTick+ " Joined queue: "+joinedQueue+ " Left queue: "+leftQueue+
				" Queue wait time: "+(leftQueue-joinedQueue));*/
		printInfo();
		/*System.out.println(" MC leave at tick: "+leaveTick+" Generated tick: "+leaveAtTick
				+" Reached dest floor: "+reachedDestFloorTick+ " Joined queue: "+joinedQueue+ " Left queue: "+leftQueue+
				" Queue wait time: "+(leftQueue-joinedQueue)+" Left building: "+leftBuilding+" Leave building: "+leaveBuilding+" Been to top floor: "+beenToTop);*/
	
	}
	
	/**
	 * Generate dest floor.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean generateDestFloor(boolean override) {
		// do nothing
		return false;
	}
	
	/**
	 * Generate leave at tick.
	 *
	 * @return int This is the tick that will be added onto the current tick, therefore determining when the maintenance crew will request to leave.
	 */
	public int generateLeaveAtTick() {
		int newTick = 0;
		
		do {
			newTick = rand.nextInt(241);
		}while(newTick < 120);
		
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
	
	
	/**
	 * Sets the reached dest floor tick.
	 *
	 * @param tick the new reached dest floor tick
	 */
	@Override
	public void setReachedDestFloorTick(int tick) {
		this.reachedDestFloorTick = tick;
	}
}
