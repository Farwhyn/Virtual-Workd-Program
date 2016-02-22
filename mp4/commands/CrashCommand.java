package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.vehicles.ArenaVehicle;

/**
 * A CrashCommand is a {@link Command} which represents a {@link ArenaVehicle}
 * colliding with an {@link Item}.
 */
public final class CrashCommand implements Command {

    private final ArenaVehicle vehicle;
    private final Item victim;

    /**
     * Construct a {@link CrashCommand}, where <code> vehicle </code> is the collider
     * and <code> victim </code> is the collidee. Remember that the victim must be
     * adjacent to the vehicle. If the vehicle has greater strength than the victim, the victim dies. 
     * If the victim has greater strength than the vehicle, the vehicle dies. If the strength 
     * of both are equal, both dies. 
     *
     * @param vehicle
     *            the collider
     * @param victim
     *            : the collidee
     */
    public CrashCommand(ArenaVehicle vehicle, Item victim) {
        this.vehicle = vehicle;
        this.victim = victim;
    }

    @Override
    public void execute(World w) throws InvalidCommandException {
        // cannot crash into non-adjacent items
        if (victim.getLocation().getDistance(vehicle.getLocation()) != 1)
            throw new InvalidCommandException("Invalid CrashCommand: Victim is not adjacent");
        // if vehicle is stronger than the victim, the victim dies
        if (vehicle.getStrength() > victim.getStrength()) {
            victim.loseEnergy(Integer.MAX_VALUE);
            vehicle.changeDirection();// lets vehicle moves to a new random direction
            vehicle.crash(); //resets vehicle's cooldown. 
        }
        // if the vehicles is weaker than the victim, the vehicle dies
        else if (vehicle.getStrength() < victim.getStrength()) {
            vehicle.loseEnergy(Integer.MAX_VALUE);//kills vehicle
        } // if the vehicle is equal in strength to the victim, both die
        else {
            
            victim.loseEnergy(Integer.MAX_VALUE);//kills the victim
            vehicle.loseEnergy(Integer.MAX_VALUE);//kills the vehicle
        }
    }

}
