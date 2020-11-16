package entities;


/**
 * This interface allows the person to request to leave the building.
 */
public interface LeaveBuilding {

	/**
	 * Generate leave at tick.
	 *
	 * @return int This is the tick that will be added onto the current tick, therefore determining when the person will request to leave.
	 */
	public int generateLeaveAtTick();
	
	/**
	 * Lets the person request the ground floor when they want to leave the building.
	 *
	 * @param currentTick The current tick.
	 */
	public void goToGround(int currentTick);
}
