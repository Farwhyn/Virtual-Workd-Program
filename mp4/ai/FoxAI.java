package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.*;

/**
 * The AI of the fox, that allows the AI to rank the commands in order and
 * perform them.
 */
public class FoxAI extends AbstractAI {
    private int closest = 2; // max number; greater than fox's view range

    public FoxAI() {

    }

    /**
     * This method determines the actions taken by a fox
     * 
     * @param world
     *            the current world
     * 
     * @param animal
     *            the current animal
     * 
     * @return the next action for the fox
     */
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        Direction dir = Util.getRandomDirection();
        Location targetLocation = new Location(animal.getLocation(), dir);
        Set<Item> possibleEats = world.searchSurroundings(animal);
        Location current = animal.getLocation();
        Location nextLocation = Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation());
        // fox wants to escape from vehicles and lions
        List<Item> listPredators = super.getItem(possibleEats, "Vehicle", "Lion");

        // the fox wants to eat rabbits and gnats
        List<Item> listFood = super.getItem(possibleEats, "Rabbit", "Gnat");

        // while there is nearby food or predator
        while (!listFood.isEmpty() || !listPredators.isEmpty()) {
            // first priority is to run from predators. move in opposite
            // directin from predator
            if (!listPredators.isEmpty())
                return runFromPredators(world, animal, current, listPredators, targetLocation);

            // second priority is to eat once no predators are nearby
            else if (!listFood.isEmpty()) {
                // closest food
                Item closeRabbit = super.closestItem(listFood, current);
                // food location
                Location rabbit = closeRabbit.getLocation();
                Direction towards = Util.getDirectionTowards(current, rabbit);
                Location dine = new Location(current, towards);
                // if the fox is adjacent to the food source, act accordingly
                if (current.getDistance(closeRabbit.getLocation()) == 1) {
                    // if energy is at max, wait
                    if (animal.getEnergy() == animal.getMaxEnergy())
                        return new WaitCommand();
                    else

                        // eat food
                        return new EatCommand(animal, closeRabbit);

                } else {
                    // if no food is adjacent, move towards nearest food in view
                    // range
                    if (Util.isValidLocation(world, dine) && super.isLocationEmpty(world, animal, dine))
                        return new MoveCommand(animal, dine);

                    else {
                        // if fox cannot move to food source, fox can breed at
                        // an empty adjacent location provided it has the
                        // appropriate energy
                        if ((nextLocation != null) && (animal.getEnergy() >= animal.getMaxEnergy() / 4)) {
                            return new BreedCommand(animal, nextLocation);
                        }
                        // move to a random location
                        else {
                            return super.movingAction(world, animal, targetLocation);
                        }
                    }
                }
            }
        }
        // when no predators or food nearby, move to a random location or wait
        return super.movingAction(world, animal, targetLocation);
    }

}
