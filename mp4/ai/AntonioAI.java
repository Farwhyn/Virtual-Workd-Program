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
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public class AntonioAI extends AbstractAI {
    public AntonioAI() {

    }


    /**
     * This method determines the actions taken by Antonio
     * 
     * @param world
     *            the current world
     * 
     * @param animal
     *            the current animal
     * 
     * @return the next action for Antonio
     */
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        Direction dir = Util.getRandomDirection();
        Location targetLocation = new Location(animal.getLocation(), dir);
        Set<Item> possibleEats = world.searchSurroundings(animal);
        Location current = animal.getLocation();
        Location nextLocation = Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation());
        // Antonio wants to eat Arabelle
        List<Item> listFood = super.getItem(possibleEats, "Arabelle", "Elephant");

        while (!listFood.isEmpty()) {
            Item closeTA = super.closestItem(listFood, current);
            Location TA = closeTA.getLocation();
            Direction towards = Util.getDirectionTowards(current, TA);
            Location dine = new Location(current, towards);
            if (current.getDistance(closeTA.getLocation()) == 1) {
                // if animal has maximum energy, then wait
                if (animal.getEnergy() == animal.getMaxEnergy())
                    return new WaitCommand();
                // eat if not at maximum energy
                else
                    return new EatCommand(animal, closeTA);
            } else {
                // move towards the prey if possible
                if (Util.isValidLocation(world, dine) && super.isLocationEmpty(world, animal, dine))
                    return new MoveCommand(animal, dine);
                // if not possible to move towards prey, then wait
                else {
                    return new WaitCommand();
                }
            }
        }
        // move in random direction if there is no prey available
        if (Util.isValidLocation(world, targetLocation) && super.isLocationEmpty(world, animal, targetLocation)) {
            return new MoveCommand(animal, targetLocation);
        }
        // if not possible to move in a random direction, then wait
        return new WaitCommand();
    }
}
