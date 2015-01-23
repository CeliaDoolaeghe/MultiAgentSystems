package iagl.idl.simulation.mas.agent.pacman;

import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Predator extends PacManAgent {
	
	public Predator(Environment<PacManAgent> environment) {
		super(environment);
	}
	
	@Override
	public boolean canStopSimulation() {
		return true;
	}

	@Override
	public void doIt() {
		List<List<Integer>> dijkstra = new ArrayList<>();
		List<PacManAgent> agents = environment.getAllAgents();
		Coordinate nextMove = null;
		int nextMoveNumber = Integer.MAX_VALUE;
		List<Coordinate> neighborsCoordinates = environment.neighborsCoordinates(environment.getCoordinateOf(this));

        // move closer to the closest eatable Agent by computing Dijkstra for each of them
		for(PacManAgent agent : agents) {
			if(agent.isEatable()) {
				computeDijkstra(dijkstra, agent);
				for(Coordinate c : neighborsCoordinates) {
					if(dijkstra.get(c.getY()).get(c.getX()) != 0 && dijkstra.get(c.getY()).get(c.getX()) < nextMoveNumber) {
						nextMoveNumber = dijkstra.get(c.getY()).get(c.getX());
						nextMove = c;
					}
				}
			}
		}
		if(nextMove != null) {
            // Move to square
			environment.move(this, nextMove);

            // Eat all eatable neighbors
            for(PacManAgent neighbor : environment.getNeighbors(this).values()) {
                if(neighbor.isEatable()) {
                    environment.removeAgent(neighbor);
                }
            }
		}
	}

	@Override
	public Color getColor() {
		return Color.YELLOW;
	}

	@Override
	public boolean isEatable() {
		return false;
	}
}
