package ca.ubc.ece.cpen221.mp4;

import javax.swing.SwingUtilities;

import ca.ubc.ece.cpen221.mp4.ai.*;
import ca.ubc.ece.cpen221.mp4.items.Gardener;
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.animals.*;
import ca.ubc.ece.cpen221.mp4.items.humans.Antonio;
import ca.ubc.ece.cpen221.mp4.items.humans.Arabelle;
import ca.ubc.ece.cpen221.mp4.items.humans.Sathish;
import ca.ubc.ece.cpen221.mp4.staff.WorldImpl;
import ca.ubc.ece.cpen221.mp4.staff.WorldUI;
import ca.ubc.ece.cpen221.mp4.vehicles.Bike;
import ca.ubc.ece.cpen221.mp4.vehicles.Car;
import ca.ubc.ece.cpen221.mp4.vehicles.Truck;

/**
 * The Main class initialize a world with some {@link Grass}, {@link Rabbit}s,
 * {@link Fox}es, {@link Gnat}s, {@link Gardener}, etc.
 *
 * You may modify or add Items/Actors to the World.
 *
 */
public class Main {

    static final int X_DIM = 40;
    static final int Y_DIM = 40;
    static final int SPACES_PER_GRASS = 7;
    static final int INITIAL_GRASS = X_DIM * Y_DIM / SPACES_PER_GRASS;
    static final int INITIAL_GNATS = INITIAL_GRASS / 4;
    static final int INITIAL_RABBITS = INITIAL_GRASS / 4;
    static final int INITIAL_FOXES = INITIAL_GRASS / 32;
    static final int INITIAL_LIONS = INITIAL_GRASS / 32;
    static final int INITIAL_TIGERS = INITIAL_GRASS / 32;
    static final int INITIAL_ELEPHANTS = INITIAL_GRASS / 32;
    static final int INITIAL_BEARS = INITIAL_GRASS / 40;
    static final int INITIAL_HYENAS = INITIAL_GRASS / 32;
    static final int INITIAL_CARS = INITIAL_GRASS / 100;
    static final int INITIAL_TRUCKS = INITIAL_GRASS / 150;
    static final int INITIAL_MOTORCYCLES = INITIAL_GRASS / 100;
    static final int INITIAL_MANS = INITIAL_GRASS / 150;
    static final int INITIAL_WOMANS = INITIAL_GRASS / 100;
    static final int INITIAL_HUNTERS = INITIAL_GRASS / 150;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().createAndShowWorld();
            }
        });
    }

    public void createAndShowWorld() {
        World world = new WorldImpl(X_DIM, Y_DIM);
        initialize(world);
        new WorldUI(world).show();
    }

    public void initialize(World world) {
        addGrass(world);
        world.addActor(new Gardener());

        addGnats(world);
        addRabbits(world);
        addFoxes(world);
        addLions(world);
        addElephants(world);
        addTrucks(world);
        addBikes(world);
        addCars(world);
        addArabelle(world);
        addSathish(world);
        addAntonio(world);
        addRat(world);
        // TODO: You may add your own creatures here!
    }

    private void addGrass(World world) {
        for (int i = 0; i < INITIAL_GRASS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            world.addItem(new Grass(loc));
        }
    }

    private void addGnats(World world) {
        for (int i = 0; i < INITIAL_GNATS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Gnat gnat = new Gnat(loc);
            world.addItem(gnat);
            world.addActor(gnat);
        }
    }

    private void addElephants(World world) {
        for (int i = 0; i < INITIAL_ELEPHANTS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Elephant elephant = new Elephant(loc);
            world.addItem(elephant);
            world.addActor(elephant);
        }
    }

    private void addFoxes(World world) {
        FoxAI foxAI = new FoxAI();
        for (int i = 0; i < INITIAL_FOXES; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Fox fox = new Fox(foxAI, loc);
            world.addItem(fox);
            world.addActor(fox);
        }
    }

    private void addLions(World world) {
        LionAI lionAI = new LionAI();
        for (int i = 0; i < INITIAL_LIONS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Lion lion = new Lion(lionAI, loc);
            world.addItem(lion);
            world.addActor(lion);
        }
    }

    private void addRabbits(World world) {
        RabbitAI rabbitAI = new RabbitAI();
        for (int i = 0; i < INITIAL_RABBITS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Rabbit rabbit = new Rabbit(rabbitAI, loc);
            world.addItem(rabbit);
            world.addActor(rabbit);
        }
    }

    private void addTrucks(World world) {
        AbstractVehicleAI truckAI = new AbstractVehicleAI();
        for (int i = 0; i < INITIAL_TRUCKS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Truck truck = new Truck(truckAI, loc);
            world.addItem(truck);
            world.addActor(truck);
        }
    }

    private void addBikes(World world) {
        AbstractVehicleAI bikeAI = new AbstractVehicleAI();
        for (int i = 0; i < INITIAL_MOTORCYCLES; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Bike bike = new Bike(bikeAI, loc);
            world.addItem(bike);
            world.addActor(bike);
        }
    }

    private void addCars(World world) {
        AbstractVehicleAI carAI = new AbstractVehicleAI();
        for (int i = 0; i < INITIAL_CARS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Car car = new Car(carAI, loc);
            world.addItem(car);
            world.addActor(car);
        }
    }

    private void addArabelle(World world) {
        ArabelleAI araAI = new ArabelleAI();
        for (int i = 0; i < INITIAL_HUNTERS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Arabelle ara = new Arabelle(araAI, loc);
            world.addItem(ara);
            world.addActor(ara);
        }
    }

    private void addSathish(World world) {
        SathishAI satAI = new SathishAI();
        for (int i = 0; i < INITIAL_HUNTERS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Sathish sat = new Sathish(satAI, loc);
            world.addItem(sat);
            world.addActor(sat);
        }
    }

    private void addAntonio(World world) {
        AntonioAI atAI = new AntonioAI();
        for (int i = 0; i < INITIAL_MANS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Antonio at = new Antonio(atAI, loc);
            world.addItem(at);
            world.addActor(at);
        }
    }

    private void addRat(World world) {
        for (int i = 0; i < INITIAL_HYENAS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Rat rat = new Rat(loc);
            world.addItem(rat);
            world.addActor(rat);
        }
    }
}

