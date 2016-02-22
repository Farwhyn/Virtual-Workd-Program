package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Lion implements ArenaAnimal {

    private static final int INITIAL_ENERGY = 140;
    private static final int MAX_ENERGY = 160;
    private static final int STRENGTH = 180;
    private static final int VIEW_RANGE = 7;
    private static final int MIN_BREEDING_ENERGY = 20;
    private static final int COOLDOWN = 4;
    private static final ImageIcon lionimage = Util.loadImage("Pyroar.gif");

    private final AI ai;

    private Location location;
    private int energy;
    
    /**
     * Create a new {@link Lion} with an {@link AI} at
     * <code>initialLocation</code>. The <code> initialLocation </code> must be
     * valid and empty
     *
     * @param foxAI
     *            the AI designed for lions
     * @param initialLocation
     *            the location where this Lion will be created
     */
    public Lion(AI lionAI, Location initialLocation) {
        
        this.ai = lionAI;
        this.location = initialLocation;
        this.energy = INITIAL_ENERGY;
    }

    public int getEnergy() {
        
        return energy;
    }

    @Override
    public LivingItem breed() {
        Lion child = new Lion(ai, location);
        child.energy = energy / 2;
        this.energy = energy / 2;
        return child;
    }

    @Override
    public void eat(Food food) {
        // Note that energy does not exceed energy limit.
        energy = Math.min(MAX_ENERGY, energy + food.getMeatCalories());
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }


    @Override
    public ImageIcon getImage() {
        return lionimage;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    @Override
    public int getMeatCalories() {
        // The amount of meat calories it provides is equal to its current
        // energy.
        return energy;
    }

    @Override
    public int getMinimumBreedingEnergy() {
        return MIN_BREEDING_ENERGY;
    }

    @Override
    public int getMovingRange() {
        return 1; // Can only move to adjacent locations.
    }

    @Override
    public String getName() {
        return "Lion";
    }

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        this.energy--; // Loses 1 energy regardless of action.
        return nextAction;
    }

    @Override
    public int getPlantCalories() {
        // This Fox is not a plant.
        return 0;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public int getViewRange() {
        return VIEW_RANGE;
    }

    @Override
    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public void loseEnergy(int energyLoss) {
        this.energy = this.energy - energyLoss;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }

}
