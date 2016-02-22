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

/**
 * Arabelle eats grass.
 */

public class Arabelle implements ArenaAnimal {

    private static final int INITIAL_ENERGY = 600;
    private static final int MAX_ENERGY = 1200;
    private static final int STRENGTH = 400;
    private static final int VIEW_RANGE = 5;
    private static final int COOLDOWN = 3;
    private static final ImageIcon ArabelleImage = Util.loadImage("woman.gif");
    private final AI ai;
    private Location location;
    private int energy;

    /**
     * Create a new {@link Arabelle} with an {@link AI} at
     * <code>initialLocation</code>. The <code> initialLocation </code> must be
     * valid and empty
     *
     * @param ArabelleAI
     *            the AI designed for Arabelle
     * @param initialLocation
     *            the location where Arabelle will be created
     */
    public Arabelle(AI ArabelleAI, Location initialLocation) {
        this.ai = ArabelleAI;
        this.location = initialLocation;
        this.energy = INITIAL_ENERGY;
    }

    @Override
    public LivingItem breed() {
        return null; // Arabelle will not breed
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
        return ArabelleImage;
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
    public int getMovingRange() {
        return 1; // Can only move to adjacent locations.
    }

    @Override
    public String getName() {
        return "Arabelle";
    }

    @Override
    public int getPlantCalories() {
        // Arabelle is not a plant.
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
    public int getMinimumBreedingEnergy() {
        return MAX_ENERGY + 100; // ensures Arabelle will never breed
    }

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        // Arabelle loses 1 energy regardless of action, but cannot die from
        // loss
        // of energy
        if (energy > 1)
            this.energy--;
        return nextAction;
    }
}
