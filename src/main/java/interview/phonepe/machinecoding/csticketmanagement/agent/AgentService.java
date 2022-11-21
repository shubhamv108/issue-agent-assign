package interview.phonepe.machinecoding.csticketmanagement.agent;

import interview.phonepe.machinecoding.csticketmanagement.issue.Issue;
import interview.phonepe.machinecoding.csticketmanagement.issue.IssueType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentService implements IAgentService, IAgentIssueService {
    private final Agents agents = new Agents();
    @Override
    public Agent addAgent(String agentEmail, String agentName, List<IssueType> issueTypes) {
        Agent agent = new Agent(agentName, agentEmail, issueTypes);
        return this.agents.add(agent);
    }

    @Override
    public Map<Agent, List<Issue>> viewAgentsWorkHistory() {
        Collection<Agent> agents = this.agents.getHasWorked();
        Map<Agent, List<Issue>> worked = new HashMap<>();
        for (Agent agent: agents)
            worked.put(agent, new ArrayList<>(agent.getWorkedOn()));
        return worked;
    }

    @Override
    public Agent markFirstAgentForAssingingIssue(IssueType issueType) {
        return this.agents.markFirstAgentForAssingingIssue(issueType);
    }

    @Override
    public void markhasWorked(Agent agent) {
        this.agents.hasWorked(agent);
    }

    @Override
    public boolean returnAgentForAssigingIssue(Agent agent) {
        agent.setAvailableForAssiginignIssue();
        return this.agents.returnAgentForAssigningIssue(agent);
    }
}
