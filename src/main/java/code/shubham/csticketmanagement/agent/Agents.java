package code.shubham.csticketmanagement.agent;

import code.shubham.commons.AbstractRepository;
import code.shubham.csticketmanagement.issue.IssueType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Agents extends AbstractRepository<Agent, String> {
    private final Set<Agent> availableAgentForIssue = ConcurrentHashMap.newKeySet();
    private final Map<IssueType, Set<Agent>> availableAgentsForIssueType = new ConcurrentHashMap<>(); // move this to seperate strategy
    private final Set<Agent> assignedIssue = ConcurrentHashMap.newKeySet();

    private final Set<Agent> hasWorked = ConcurrentHashMap.newKeySet();


    @Override
    protected void setId(Agent agent) {
        agent.setId("A" + idGenerator.getAndIncrement());
    }

    @Override
    protected void addToIndexes(Agent agent) {
        for (IssueType issueType : agent.getIssueTypes()) {
            Set<Agent> agents = this.availableAgentsForIssueType.get(issueType);
            if (agents == null)
                this.availableAgentsForIssueType.put(issueType, agents = new HashSet<>());
            agents.add(agent);
        }
        this.availableAgentForIssue.add(agent);
    }

    @Override
    protected void removeFromIndexes(Agent agent) {
        this.assignedIssue.remove(agent);
        agent.getAssignedIssue().setAssignedTo(null);
        for (IssueType issueType: agent.getIssueTypes())
            this.availableAgentsForIssueType.get(issueType).remove(agent);
        this.availableAgentForIssue.remove(agent);
    }

    public Agent markFirstAgentForAssingingIssue(IssueType issueType) {
        Set<Agent> agents = this.availableAgentsForIssueType.get(issueType);
        if (agents == null || agents.isEmpty())
            return null;
        for (Agent agent: agents) {
            try {
                this.keyLockService.getLock(agent.getId()).writeLock().lock();
                if (!this.availableAgentsForIssueType.get(issueType).contains(agent))
                    continue;
                if (!this.assignedIssue.contains(agent)) {
                    this.assignedIssue.add(agent);
                    this.availableAgentForIssue.remove(agent);
                    return agent;
                }
            } finally {
                this.keyLockService.getLock(agent.getId()).writeLock().unlock();
            }
        }
        return null;
    }

    public void hasWorked(Agent agent) {
        this.hasWorked.add(agent);
    }

    public List<Agent> getHasWorked() {
        return new ArrayList<>(hasWorked);
    }

    public boolean returnAgentForAssigningIssue(Agent agent) {
        try {
            this.lockWrite(agent);
            this.assignedIssue.remove(agent);
            return this.availableAgentForIssue.add(agent);
        } finally {
            this.unlockWrite(agent);
        }
    }
}
