import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

public class HomingController implements IRobotController {
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

		int direction;
		int randno;

		direction = IRobot.AHEAD;

		//the do while loop was an infinte loop because the robot wasn't advancing

		while (!robot.getLocation().equals(robot.getTargetLocation()) && active) {

			robot.setHeading(determineHeading());
			robot.advance();
		}


		 /*1.4: do { randno = (int) Math.floor(Math.random() * 4);

		  // change the direction based on the random number
		  if (randno == 0) direction= IRobot.LEFT;
		  else if (randno == 1) direction = IRobot.RIGHT;
		  else if (randno == 2) direction = IRobot.BEHIND;
		  else direction = IRobot.AHEAD;
		  //Face the direction
		  robot.face(direction);
		  // wait for a while if we are supposed to
		  if (delay > 0) robot.sleep(delay);
			robot.advance();

		  } while  (robot.look(IRobot.AHEAD) == IRobot.WALL);
		 */

	}

	// this method returns 1 if the target is north of the
	// robot, -1 if the target is south of the robot, or
	// 0 if otherwise.
	public byte isTargetNorth() {
		/*
		 * For testing, I initialized byte variable p so that I can see what
		 * isTargetNorth returns. I also printed the y coordinates of the robot and of
		 * the target to see if the test is correct.
		 */
		byte p1 = 0;
		if (robot.getLocation().y > robot.getTargetLocation().y)
			p1 = 1;
		else if (robot.getLocation().y < robot.getTargetLocation().y)
			p1 = -1;
		else
			p1 = 0;
		return p1;
	}

	// this method returns 1 if the target is east of the
	// robot, -1 if the target is west of the robot, or
	// 0 if otherwise.
	public byte isTargetEast() {
		byte p2 = 0;
		if (robot.getLocation().y < robot.getTargetLocation().x)
			p2 = 1;
		else if (robot.getLocation().y > robot.getTargetLocation().x)
			p2 = -1;
		else
			p2 = 0;
		return p2;
	}

	// this method causes the robot to look to the absolute
	// direction that is specified as argument and returns
	// what sort of square there is
	public int lookHeading(int absoluteDirection) {

		int currentDirection = robot.getHeading();
		robot.setHeading(absoluteDirection);
		int t = robot.look(IRobot.AHEAD);
		robot.setHeading(currentDirection);
		return t;
	}

	public int randomHeading() {
		//I used this method to return a heading for the case in which the absolute direction
		// is not a valid choice
		int heading=IRobot.NORTH;
		int randH=0;
		do { randH = (int) Math.floor(Math.random() * 4);

		  // change the heading based on the random number
		  if (randH == 0) heading= IRobot.NORTH;
		  else if (randH == 1) heading = IRobot.EAST;
		  else if (randH == 2) heading = IRobot.WEST;
		  else heading = IRobot.SOUTH;
		} while(lookHeading(heading) == IRobot.WALL );
		return  heading;

	}


	// this method determines the heading in which the robot
	// should head next to move closer to the target
	public int determineHeading() {
		byte x = isTargetNorth();
		byte y = isTargetEast();
		// in X and Y we store the vertical and horizontal heading of the robot
		int X = 0;
		int Y = 0;
		switch (x) {
		case 1:
			X = IRobot.NORTH;
			break;
		case 0:
			X = 0;
			break;
		case -1:
			X = IRobot.SOUTH;
			break;
		}
		switch (y) {
		case 1:
			Y = IRobot.EAST;
			break;
		case 0:
			Y = 0;
			break;
		case -1:
			Y = IRobot.WEST;
			break;
		}

		//if the robot is on the same horizontal/vertical as the target and the other heading is not
		// blocked by walls, it goes that way
		if (x == 0)
			if(lookHeading(Y) != IRobot.WALL)
			return Y;
			//the targeted heading is blocked, so it picks a random unblocked heading
			else return randomHeading();

		if (y == 0)
			if(lookHeading(X) != IRobot.WALL)
			return X;
			else return randomHeading();

		// if both targeted headings are available, choose randomly between them
		if (lookHeading(X) != IRobot.WALL && lookHeading(Y) != IRobot.WALL)
		{ int random2 = (int) Math.round(Math.random());
			switch (random2) {
			case 0: return X;

			case 1: return Y;

			}
		}
		//if the program gets here, one of the targeted heading is not available,
		// so it tests if the other one is available
		else if (lookHeading(X) != IRobot.WALL)
			return X;
		else if (lookHeading(Y) != IRobot.WALL)
			return Y;
		//none of the targeted headings is available, so it returns a random heading that
		//is not blocked by walls
		return randomHeading();

	}

	// this method returns a description of this controller
	public String getDescription() {
		return "A controller which homes in on the target";
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
}
