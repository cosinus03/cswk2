import uk.ac.warwick.dcs.maze.logic.*;

import java.awt.Point;

public class RandomController implements IRobotController {
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

		// loop while we haven't found the exit and the agent
		// has not been interrupted
		boolean move = false;
		int k = 0;
		while (!robot.getLocation().equals(robot.getTargetLocation()) && active) {
			move = false;
			//robot makes a random move every 8 moves
			if (k % 8 == 0) {
				int rand = (int) Math.floor(Math.random() * 4);
				switch (rand) {
				case 0:
					if (robot.look(IRobot.AHEAD) != IRobot.WALL) {
						robot.face(IRobot.AHEAD);
						move = true;
						robot.getLogger().log(IRobot.AHEAD);
					} else
						break;
					break;
				case 1:

					if (robot.look(IRobot.LEFT) != IRobot.WALL) {
						move = true;
						robot.face(IRobot.LEFT);
						robot.getLogger().log(IRobot.LEFT);
					} else
						break;
					break;
				case 2:

					if (robot.look(IRobot.RIGHT) != IRobot.WALL) {
						move = true;
						robot.face(IRobot.RIGHT);
						robot.getLogger().log(IRobot.RIGHT);
					} else
						break;
					break;
				case 3:

					if (robot.look(IRobot.BEHIND) != IRobot.WALL) {
						move = true;
						robot.face(IRobot.BEHIND);
						robot.getLogger().log(IRobot.BEHIND);
					} else
						break;
					break;
				}
			}

			else {

				// if there is no wall ahead, the robot keeps advancing
				if (robot.look(IRobot.AHEAD) != IRobot.WALL) {
					robot.advance();
					robot.getLogger().log(IRobot.AHEAD);
				} else {

					/*
					 * 1.2.2 Because there are more doubles that round up to 1 and 2, most of the
					 * directions will be left and right. For the chances of each case to be equal,
					 * we will generate a random number from 0 to 4 and calculate its floor int rand
					 * = (int)Math.floor(Math.random()*4);
					 */
					/*
					 * 1.3.3 If the program gets here, it means that there is a wall ahead, so we
					 * will only need 3 random numbers
					 */
					int rand = (int) Math.floor(Math.random() * 3);

					/*
					 * 1.3.1 As we cannot go ahead, we only need 3 directions: 0: behind 1:left
					 * 2:right
					 */

					switch (rand) {
					/*
					 * for every value of rand, we check if there is wall in the respective
					 * direction. If there is not, we change direction, change the value of move and
					 * document the move. Else, we break from the switch.
					 *
					 *
					 */
					/*
					 *
					 * case 0:
					 *
					 *
					 * if(robot.look(IRobot.AHEAD)!=IRobot.WALL) { robot.face(IRobot.AHEAD);
					 * move=true; robot.getLogger().log(IRobot.AHEAD); } else break; break; case 1:
					 *
					 * if(robot.look(IRobot.LEFT)!=IRobot.WALL) { move=true;
					 * robot.face(IRobot.LEFT); robot.getLogger().log(IRobot.LEFT); } else break;
					 * break; case 2:
					 *
					 * if(robot.look(IRobot.RIGHT)!=IRobot.WALL) { move=true;
					 * robot.face(IRobot.RIGHT); robot.getLogger().log(IRobot.RIGHT); } else break;
					 * break; case 3:
					 *
					 * if(robot.look(IRobot.BEHIND)!=IRobot.WALL) { move=true;
					 * robot.face(IRobot.BEHIND); robot.getLogger().log(IRobot.BEHIND); } else
					 * break; break; }
					 */

					case 0:

						if (robot.look(IRobot.BEHIND) != IRobot.WALL) {
							robot.face(IRobot.BEHIND);
							move = true;
							robot.getLogger().log(IRobot.BEHIND);
						} else
							break;
						break;
					case 1:

						if (robot.look(IRobot.LEFT) != IRobot.WALL) {
							move = true;
							robot.face(IRobot.LEFT);
							robot.getLogger().log(IRobot.LEFT);
						} else
							break;
						break;
					case 2:

						if (robot.look(IRobot.RIGHT) != IRobot.WALL) {
							move = true;
							robot.face(IRobot.RIGHT);
							robot.getLogger().log(IRobot.RIGHT);
						} else
							break;
						break;
					}
				}
			}

			// move one step into the direction the robot is facing if the direction has
			// been updated
			if (move)
				robot.advance();

			// wait for a while if we are supposed to
			if (delay > 0)
				robot.sleep(delay);
			k++;
		}

	}

	// this method returns a description of this controller
	public String getDescription() {
		return "A controller which randomly chooses where to go";
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
