package code.shubham.csticketmanagement.agent;

public interface IAgentService {

    Agent add(Agent agent);

    void remove(String id);

    Agent getById(String agentId);
}
