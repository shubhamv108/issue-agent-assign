package code.shubham.csticketmanagement.agent;

public interface IAgentService {

    Agent add(Agent agent);

    Agent remove(String id);
}
