package iagl.idl.simulation.graph;

import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.agent.Agent;
import iagl.idl.simulation.mas.agent.segregation.SegregationAgent;
import iagl.idl.simulation.mas.environment.Environment;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Specific Log4J logger which counts the average of satisfaction and then log it into a file.
 */
public class SatisfactionLogger extends CSVLogger<SegregationAgent> {

    public SatisfactionLogger(MAS<SegregationAgent> mas) throws FileNotFoundException {
        super(mas);
    }

    @Override
    protected void writeLine() {
        Environment<SegregationAgent> environment = mas.getEnvironment();
        long chronons = mas.getScheduling();

        float satisfaction = 0;
        List<SegregationAgent> agents = environment.getAllAgents();
        for(SegregationAgent agent : agents) {
            satisfaction += agent.getSatisfaction();
        }
        satisfaction = satisfaction / agents.size();

        logger.trace(chronons + " " + satisfaction);
    }
}
