package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.CrashCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.staff.WorldImpl;
import ca.ubc.ece.cpen221.mp4.vehicles.ArenaVehicle;

/**
 * This is the AI for the all vehicles. This AI determines what actions the
 * vehicles will take.
 */
public class AbstractVehicleAI implements VehicleAI {

    public AbstractVehicleAI() {

    }

    /**
     * This method is a helper method. It determines what items are in the same
     * direction that the vehicle is traveling
     * 
     * @param set
     *            a set of items around the vehicle
     * @param carDir
     *            the direction the vehicle is traveling in
     * @param vehicle
     *            the vehicle
     * @return item: an item that is in the direction the vehicle is traveling
     *         in
     */
    private Item getSameDirItem(Set<Item> set, Direction carDir, Location location) {
        Iterator<Item> iterator = set.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            Direction compareDir = Util.getDirectionTowards(location, item.getLocation());
            if (compareDir == carDir) {
                return item;
            } else
                continue;
        }

        return null;
    }

    /**
     * This method determines the vehicle's next action.
     * 
     * @param world
     *            the world the vehicle is in
     * @param vehicle
     *            the vehicle
     * @return a command for the vehicle to follow
     */
    @Override
    public Command getNextAction(World world, ArenaVehicle vehicle) {

        // Direction chargeDirection = getADirection();
        Location targetLocation = new Location(vehicle.getLocation(), vehicle.getDirection());
        // move to an empty location in one direction
        if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty((World) world, targetLocation))
            return new MoveCommand(vehicle, targetLocation);
        else {

            Set<Item> crashVictims = world.searchSurroundings(vehicle.getLocation(), 1);

            Item crashVictim = getSameDirItem(crashVictims, vehicle.getDirection(), vehicle.getLocation());
            // Crash into an item adjacent to it in the direction it is
            // travelling
            if (crashVictim != null && vehicle.getLocation().getDistance(crashVictim.getLocation()) == 1) {
                return new CrashCommand(vehicle, crashVictim);

            }

            // if there are no items, change direction and reset cooldown.
            else {
                vehicle.changeDirection();
                vehicle.crash();
                return new WaitCommand();
            }

        }
    }
}
