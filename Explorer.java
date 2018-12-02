import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;
import java.util.Arrays;

public class Explorer implements IRobotController {
    // the robot in the maze
    private IRobot robot;
    // a flag to indicate whether we are looking for a path
    private boolean active = false;
    // a value (in ms) indicating how long we should wait
    // between moves
    private int delay;

    // this method is called when the "start" button is clicked
    // in the user interface
    public void start() {
        this.active = true;

        while(!robot.getLocation().equals(robot.getTargetLocation()) && active) {
            // wait for a while if we are supposed to
            if (delay > 0)
                robot.sleep(delay);
            int exits=nonwallExits();
            int direction;
            switch(exits) {
              case 1: direction = deadEnd();
                break;
              case 2: direction = corridor();
                break;
              case 3: direction = junction();
                break;
              case 4: direction = crossroad();
                break;
              default: direction = surrounded();
              }

        }
    }

    // returns a number indicating how many non-wall exits there
    // are surrounding the robot's current position
    public int nonwallExits() {
        int noWalls=0;
        if (robot.look(IRobot.LEFT) != IRobot.WALL) noWalls++;
        if (robot.look(IRobot.RIGHT) != IRobot.WALL) noWalls++;
        if (robot.look(IRobot.AHEAD) != IRobot.WALL) noWalls++;
        if (robot.look(IRobot.BEHIND) != IRobot.WALL) noWalls++;
        return noWalls;
        //make a test for this!!
    }

    // this method returns a description of this controller
    public String getDescription() {
       return "A controller which explores the maze in a structured way";
    }

    // sets the delay
    public void setDelay(int millis) {
       delay = millis;
    }

    // gets the current delay
    public int getDelay() {
       return delay;
    }

    // stops the controller
    public void reset() {
       active = false;
    }

    // sets the reference to the robot
    public void setRobot(IRobot robot) {
       this.robot = robot;
    }
    
    //if the robot is in a dead end, it chooses the only available option
    public int deadEnd() {
      if (robot.look(IRobot.LEFT) != IRobot.WALL) return IRobot.LEFT;
      if (robot.look(IRobot.RIGHT) != IRobot.WALL) return IRobot.RIGHT;
      if (robot.look(IRobot.AHEAD) != IRobot.WALL) return IRobot.AHEAD;
      if (robot.look(IRobot.BEHIND) != IRobot.WALL) return IRobot.BEHIND;
      return IRobot.AHEAD;
    }
    
    //if the robot is in a corridor, it chooses the non-visited path
    public int corridor() {
       if(robot.look(IRobot.LEFT) != IRobot.WALL && robot.look(IRobot.LEFT) != IRobot.BEENBEFORE) return IRobot.LEFT;
       if(robot.look(IRobot.RIGHT) != IRobot.WALL && robot.look(IRobot.RIGHT) != IRobot.BEENBEFORE) return IRobot.RIGHT;
       if(robot.look(IRobot.AHEAD) != IRobot.WALL && robot.look(IRobot.AHEAD) != IRobot.BEENBEFORE) return IRobot.AHEAD;
       if(robot.look(IRobot.BEHIND) != IRobot.WALL && robot.look(IRobot.BEHIND) != IRobot.BEENBEFORE) return IRobot.BEHIND;
       return IRobot.AHEAD;
    }
    
    public int passageExits() {
    	int[] PASSAGE = new int [4];
    	int numberOfPassage=-1;
        if(robot.look(IRobot.LEFT) != IRobot.WALL)
          if(robot.look(IRobot.LEFT) != IRobot.BEENBEFORE) return IRobot.LEFT;
          else int PASSAGE[++numberOfPassage]=IRobot.LEFT;
        if(robot.look(IRobot.RIGHT) != IRobot.WALL)
          if(robot.look(IRobot.RIGHT) != IRobot.BEENBEFORE) return IRobot.RIGHT;
          else int PASSAGE[++numberOfPassage]=IRobot.RIGHT;
        if(robot.look(IRobot.AHEAD) != IRobot.WALL)
          if(robot.look(IRobot.AHEAD) != IRobot.BEENBEFORE) return IRobot.AHEAD;
          else int PASSAGE[++numberOfPassage]=IRobot.AHEAD;
        if(robot.look(IRobot.BEHIND) != IRobot.WALL)
          if(robot.look(IRobot.BEHIND) != IRobot.BEENBEFORE) return IRobot.BEHIND;
          else int PASSAGE[++numberOfPassage]=IRobot.BEHIND;
       
        if(numberOfPassage!=-1) {
        	int rand = (int) Math.floor(Math.random() * (numberOfPassage+1));
            boolean loop=1;
            while(loop)
              switch(rand) {
                case 0: return PASSAGE[0];
                  break;
                case 1: return PASSAGE[1];
                  break;
                case 2: return PASSAGE[2];
                  break;
                case 3: return PASSAGE[3];
        }
    	
    }
    
    //
    public int junction() {
      int[] PASSAGE = new int [3];
      int numberOfPassage=-1;
      if(robot.look(IRobot.LEFT) != IRobot.WALL)
        if(robot.look(IRobot.LEFT) != IRobot.BEENBEFORE) return IRobot.LEFT;
        else int PASSAGE[++numberOfPassage]=IRobot.LEFT;
      if(robot.look(IRobot.RIGHT) != IRobot.WALL)
        if(robot.look(IRobot.RIGHT) != IRobot.BEENBEFORE) return IRobot.RIGHT;
        else int PASSAGE[++numberOfPassage]=IRobot.RIGHT;
      if(robot.look(IRobot.AHEAD) != IRobot.WALL)
        if(robot.look(IRobot.AHEAD) != IRobot.BEENBEFORE) return IRobot.AHEAD;
        else int PASSAGE[++numberOfPassage]=IRobot.AHEAD;
      if(robot.look(IRobot.BEHIND) != IRobot.WALL)
        if(robot.look(IRobot.BEHIND) != IRobot.BEENBEFORE) return IRobot.BEHIND;
        else int PASSAGE[++numberOfPassage]=IRobot.BEHIND;

      int rand = (int) Math.floor(Math.random() * 3);
      boolean loop=1;
      while(loop)
        switch(rand) {
          case 0: return PASSAGE[0];
            break;
          case 1: return PASSAGE[1];
            break;
          case 2: return PASSAGE[2];
            break;
        }
    }

    public int crossroad() {
       return delay;
    }

    public int surrounded() {
       return delay;
    }
}
#   c s 2  
 