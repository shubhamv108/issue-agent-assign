package code.shubham.csticketmanagement.strategies;

import code.shubham.csticketmanagement.agent.Agent;
import code.shubham.csticketmanagement.issue.Issue;
import code.shubham.csticketmanagement.issue.IssueType;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AgentIssueStrategy {

    private final Map<IssueType, Set<Agent>> issueTypeAgents = new ConcurrentHashMap<>();

    private final Set<Agent> workedAgents = ConcurrentHashMap.newKeySet();

    public void add(Agent agent) {
        for (IssueType issueType: agent.getIssueTypes())
            this.add(issueType, agent);
    }

    public void add(IssueType issueType, Agent agent) {
        Set<Agent> agents = this.getAgentsForIssueType(issueType);
        synchronized (agents) {
            agents.add(agent);
        }
    }

    public Agent assign(Issue issue) {
        Set<Agent> agents = this.issueTypeAgents.get(issue.getIssueType());
        if (agents == null || agents.isEmpty())
            return null;
        synchronized (agents) {
            for (Agent agent: agents)
                if (agent.getAssignedIssue() == null) {
                    agent.setAssignedIssue(issue);
                    this.workedAgents.add(agent);
                    return agent;
                }
        }
        return null;
    }

    private Set<Agent> getAgentsForIssueType(IssueType issueType) {
        Set<Agent> agents = this.issueTypeAgents.get(issueType);
        if (agents != null)
            return agents;
        synchronized (issueType.name()) {
            agents = this.issueTypeAgents.get(issueType);
            if (agents != null)
                return agents;
            this.issueTypeAgents.put(issueType, agents = ConcurrentHashMap.newKeySet());
        }
        return agents;
    }

    public Set<Agent> getWorkedAgents() {
        return workedAgents;
    }

    public void remove(Agent agent) {
        agent.unassignIssue();
        for (IssueType issueType: agent.getIssueTypes())
            this.remove(issueType, agent);
    }

    public void remove(IssueType issueType, Agent agent) {
        Set<Agent> agents = this.getAgentsForIssueType(issueType);
        synchronized (agents) {
            agents.remove(agent);
        }
    }
}
