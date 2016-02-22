package ca.ubc.ece.cpen221.mp4.items.humans;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;

/**
 * Sathish is the ultimate predator. Sathish wants to eat Antonio and Arabelle
 * and actively tries to find and move towards them.
 */

public class Sathish implements ArenaAnimal {

    private static final int INITIAL_ENERGY = 1000;
    private static final int MAX_ENERGY = 2000;
    private static final int STRENGTH = 2500;
    private static final int VIEW_RANGE = 10;
    private static final int COOLDOWN = 1;
    private static final ImageIcon sathishImage = Util.loadImage("hunter.gif");

    private final AI ai;
    private Location location;
    private int energy;
    
    /**
     * Create a new Sathish with an AI at
     * initialLocation. The initialLocation must be
     * valid and empty
     *
     * @param SathishAI
     *            the AI designed for Sathish
     * @param initialLocation
     *            the location where Sathish will be created
     */
    public Sathish(AI SathishAI, Location initialLocation){
        this.ai = SathishAI;
        this.location = initialLocation;
        this.energy = INITIAL_ENERGY;
    }

    @Override
    public LivingItem breed() {
        return null; // Sathish will not breed
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
    public int getEnergy() {
        return energy;
    }

    @Override
    public ImageIcon getImage() {
        return sathishImage;
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
        return MAX_ENERGY + 100; // ensures Sathish will never breed
    }

    @Override
    public int getMovingRange() {
        return 1; // Can only move to adjacent locations.
    }

    @Override
    public String getName() {
        return "Sathish";
    }

    @Override
    public int getPlantCalories() {
        // Sathish is not a plant.
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

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        // Sathish loses 1 energy regardless of action, but cannot die from losing energy
        if (energy > 1)
            energy--;
        return nextAction;
    }
}
