package code.shubham.csticketmanagement.agent;

import code.shubham.csticketmanagement.exceptions.RequestException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AgentService implements IAgentService {
    private final Map<String, Agent> agents = new ConcurrentHashMap<>();

    @Override
    public Agent getById(String id) {
        return this.agents.get(id);
    }

    @Override
    public Agent add(Agent agent) {
        Agent existing = agents.get(agent.getId());
        if (existing != null)
            throw new RequestException("Agent already exists");
        this.agents.put(agent.getId(), agent);
        return agent;
    }

    @Override
    public void remove(String id) {
        this.agents.remove(id);
    }
}
