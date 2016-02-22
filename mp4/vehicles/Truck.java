package ca.ubc.ece.cpen221.mp4.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.VehicleAI;
import ca.ubc.ece.cpen221.mp4.commands.Command;

/**
 * The truck is an ArenaVehicle that gains momentum as it travels in a
 * direction. If it hits something of lower strength, the truck destroys the
 * victim and begins traveling in a new direction at the original momentum. If
 * the truck hits something of equal or greater strength, the truck and victim
 * both get destroyed.
 */

public class Truck implements ArenaVehicle {

    private static final int STRENGTH = 1000;
    private static final int INITIAL_COOLDOWN = 7;
    private static final ImageIcon truckImage = Util.loadImage("trucks.gif");

    private final VehicleAI ai;

    private Location location;
    private int cooldown;
    private boolean crashed;
    private Direction direction;

    /**
     * Create a new truck with an AI at initialLocation. The initialLocation
     * must be valid and empty.
     * 
     * @param VehicleAI
     *            the AI designed for all vehicles
     * @param initialLocation
     *            the location where this truck will be created
     */
    public Truck(VehicleAI truckAI, Location initialLocation) {
        this.ai = truckAI;
        this.location = initialLocation;
        this.cooldown = INITIAL_COOLDOWN;
        this.crashed = false;
        this.direction = Util.getRandomDirection();
    }

    @Override
    public int getCoolDownPeriod() {
        return cooldown;
    }

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        if (cooldown > 0) {
            this.cooldown--; // Loses 1 energy regardless of action.
        }
        return nextAction;
    }

    @Override
    public ImageIcon getImage() {
        return truckImage;
    }

    @Override
    public String getName() {
        return "Vehicle";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public void loseEnergy(int energy) {
        crashed = true;
    }

    @Override
    public boolean isDead() {
        return crashed;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;

    }

    @Override
    public int getMovingRange() {
        return 1;
    }

    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }

    @Override
    public void crash() {
        cooldown = INITIAL_COOLDOWN;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void changeDirection() {
        direction = Util.getRandomDirection();
    }
}