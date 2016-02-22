package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;

/**
 * The AI of the rabbit. The AI determines what actions the rabbit will take.
 */
public class RabbitAI extends AbstractAI {

    private int closest = 10; // max number; greater than rabbit's view range
    private int temp;
    private boolean foxFound;

    public RabbitAI() {
    }

    /**
     * This method determines the actions taken by a Rabbit
     * 
     * @param world
     *            the current world
     * 
     * @param animal
     *            the current animal
     * 
     * @return the next action for the rabbit
     */
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {

        Direction dir = Util.getRandomDirection();

        // an adjacent empty location from the current location of the rabbit
        Location targetLocation = new Location(animal.getLocation(), dir);
        // all the items visible to the rabbit
        Set<Item> possibleEats = world.searchSurroundings(animal);
        Location current = animal.getLocation();
        Location nextLocation = Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation());
        // list of predators
        List<Item> listPredators = super.getItem(possibleEats, "Fox", "Vehicle");

        // list of prey
        List<Item> listFood = super.getItem(possibleEats, "grass", "grass");

        // while there are predators or food nearby, act accordingly
        while (!listPredators.isEmpty() || !listFood.isEmpty()) {

            // first run from predators
            if (!listPredators.isEmpty()) {
                return runFromPredators(world, animal, current, listPredators, targetLocation);
            }
            // if no predators present, go after food
            else if (!listFood.isEmpty()) {
                // closest food source
                Item closeGrass = super.closestItem(listFood, current);
                Location grass = closeGrass.getLocation();
                Direction towards = Util.getDirectionTowards(current, grass);
                // location of closest food source
                Location dine = new Location(current, towards);
                // if grass is adjacent
                if (current.getDistance(closeGrass.getLocation()) == 1) {
                    // energy is max and there is breeding room, breed instead
                    // of eating
                    if (animal.getEnergy() == animal.getMaxEnergy() && nextLocation != null) {

                        return new BreedCommand(animal, nextLocation);

                    } else
                        // eat the grass if not at max energy
                        return new EatCommand(animal, closeGrass);
                    // if there is grass nearby
                } else {
//move towards closest grass if possible
                    if (Util.isValidLocation(world, dine) && super.isLocationEmpty(world, animal, dine))
                        return new MoveCommand(animal, dine);
                    //breed if possible if cannot move towards food
                    else if ((nextLocation != null) && (animal.getEnergy() >= animal.getMinimumBreedingEnergy())) {
                        return new BreedCommand(animal, nextLocation);
                    //move randomly or wait if none of the above actions are appropriate
                    } else {
                        return super.movingAction(world, animal, targetLocation);
                    }
                }
            }
        }
//move to a random location or wait, when there are no items nearby
        return super.movingAction(world, animal, targetLocation);
    }

}
