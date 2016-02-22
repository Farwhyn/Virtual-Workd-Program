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

public class AbstractAI implements AI {

	public Direction oppositeDir(Direction dir) { // returns opposite direction
													// of direction dir
		if (dir == Direction.EAST) {
			return Direction.WEST;
		} else if (dir == Direction.WEST) {
			return Direction.EAST;
		} else if (dir == Direction.SOUTH) {
			return Direction.NORTH;
		} else {
			return Direction.SOUTH; 
		}
	}

	/**
     * This is a helper method
     * 
     * Generates a list of specific items from the general set of all items
     * within the arena animal's view range
     * 
     * @param list
     *            the set of all items within the arena animal's view range
     * @param stringo
     *            the name of a type of item to be added to the list
     * @param stringt
     *            the name of a type of item to be added to the list
     * @return listItems a list of specific items
     * 
     */
	 public List<Item> getItem(Set<Item> list, String stringo, String stringt) {
	        List<Item> listItems = new ArrayList<Item>();
	        Iterator<Item> it = list.iterator();
	        while (it.hasNext()) {
	            Item item = it.next();
	            if (item.getName().equals(stringo) || item.getName().equals(stringt))
	                listItems.add(item);
	            else
	                continue;
	        }

	        return listItems;
	    }

	    
	 /**
	     * This is a helper method
	     * 
	     * Generates the closest item to a location
	     * 
	     * @param items
	     *            a list of items
	     * 
	     * @param loc
	     *            a location
	     * 
	     * @return closeItem item that is closest to the location
	     */
	 public Item closestItem(List<Item> items, Location loc) {
	        if (items.isEmpty())
	            return null;
	        else {
	            Item closeItem = items.get(0);
	            int shortestDistance = loc.getDistance(closeItem.getLocation());
	            for (int i = 1; i < items.size(); i++) {
	                Item tempCloseItem = items.get(i);
	                int tempShort = loc.getDistance(tempCloseItem.getLocation());
	                if (tempShort < shortestDistance) {
	                    shortestDistance = tempShort;
	                    closeItem = items.get(i);
	                } else
	                    continue;
	            }
	            return closeItem;
	        }
	    }
	    
	public boolean isLocationEmpty(ArenaWorld world, ArenaAnimal animal, Location location) { // returns
																								// true
																								// if
																								// location
																								// is
																								// empty
		if (!Util.isValidLocation(world, location)) {
			return false;
		}
		Set<Item> possibleMoves = world.searchSurroundings(animal);
		Iterator<Item> it = possibleMoves.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if (item.getLocation().equals(location)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		return new WaitCommand();
	}
	
	
	/**
     * This is a helper method
     * 
     * Generates the command that lets an ArenaAnimal move if possible. 
     * 
     * @param world
     *            the ArenaWorld
     *          
     *              
     * @param targetLocation
     *              the location it should move to
     * 
     * @return a moveCommand that lets the animal move to a random location. 
     */
	public Command movingAction(ArenaWorld world, ArenaAnimal animal, Location targetLocation)
	{
	    
	    if (Util.isValidLocation(world, targetLocation) && isLocationEmpty(world, animal, targetLocation)) {
            //if the random adjacent location is appropriate, move towards it
	        return new MoveCommand(animal, targetLocation);
        }
        else
            //otherwise wait
            return new WaitCommand();
	}
	
	
	/**
     * This is a helper method
     * 
     * Generates the command that lets it evade from predators. 
     * 
     * @param world
     *            the ArenaWorld
     *            
     * @param animal
     *            the current animal implementing this method
     * 
     * @param current
     *            the location of the animal
     *            
     * @param listPredators
     *              the list of all predators
     *              
     * @param targetLocation
     *              the location it should move to
     * 
     * @return a moveCommand that lets the animal escape from the predator. 
     */
	public Command runFromPredators(ArenaWorld world, ArenaAnimal animal, Location current, List<Item> listPredators, Location targetLocation)
	{
	    //closest predator from animal
	    Item closePredators = closestItem(listPredators, current);
        //locatin of predator
	    Location predator = closePredators.getLocation();
       //direction towards predator
	    Direction towards = Util.getDirectionTowards(current, predator);
        //direction away from predator
	    Direction away = oppositeDir(towards);
        //empty location adjacent from animal that is direction away from predator
	    Location escape = new Location(current, away);
        //if valid, move towards the escape location
	    if (Util.isValidLocation(world, escape) && isLocationEmpty(world, animal, escape))
            return new MoveCommand(animal, escape);
        //move randomly to try to get away
	    else {
            return movingAction(world, animal, targetLocation);
        }
	}

}
