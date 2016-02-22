package ca.ubc.ece.cpen221.mp4.ai;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

/**
 * Your Lion AI.
 */
public class LionAI extends AbstractAI{


        public LionAI() {
        }
        /**
         * This method determines the actions taken by a lion
         * 
         * @param world
         *            the current world
         * 
         * @param animal
         *            the current animal
         * 
         * @return the next action for the lion
         */
        @Override
        public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
            ArenaAnimalAI a = new ArenaAnimalAI(animal.getEnergy());
            
            return a.getNextAction(world, animal);
        }

}
