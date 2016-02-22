package ca.ubc.ece.cpen221.mp4.vehicles;



import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public interface ArenaVehicle extends MoveableItem, Actor {
    
    /**
     * Resets the vehicle's momentum after a crash
     */
    void crash();
    
    /**
     * 
     * @return a direction
     */
    Direction getDirection();
    
    /**
     * Sets a random direction
     */
    void changeDirection();
}


