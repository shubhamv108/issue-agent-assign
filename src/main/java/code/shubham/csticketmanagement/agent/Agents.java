package code.shubham.csticketmanagement.agent;

import code.shubham.commons.LockService;
import code.shubham.csticketmanagement.issue.IssueType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Agents {
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final Set<Agent> availableAgentForIssue = ConcurrentHashMap.newKeySet();
    private final Map<IssueType, Set<Agent>> availableAgentsForIssueType = new ConcurrentHashMap<>(); // move this to seperate strategy
    private final Set<Agent> assignedIssue = ConcurrentHashMap.newKeySet();

    private final Set<Agent> hasWorked = ConcurrentHashMap.newKeySet();

    private final LockService<Agent> agentLockService;

    public Agents(LockService<Agent> agentLockService) {
        this.agentLockService = agentLockService;
    }

    public Agent add(Agent agent) {
        agent.setId("A" + idGenerator.getAndIncrement());
        this.availableAgentForIssue.add(agent);
        for (IssueType issueType: agent.getIssueTypes()) {
            Set<Agent> agents = this.availableAgentsForIssueType.get(issueType);
            if (agents == null)
                this.availableAgentsForIssueType.put(issueType, agents = new HashSet<>());
            agents.add(agent);
        }
        return agent;
    }

    public Agent markFirstAgentForAssingingIssue(IssueType issueType) {
        Set<Agent> agents = this.availableAgentsForIssueType.get(issueType);
        if (agents == null || agents.isEmpty())
            return null;
        for (Agent agent: agents) {
            try {
                this.agentLockService.getLock(agent).writeLock().lock();
                if (!this.assignedIssue.contains(agent)) {
                    this.assignedIssue.add(agent);
                    this.availableAgentForIssue.remove(agent);
                    return agent;
                }
            } finally {
                this.agentLockService.getLock(agent).writeLock().unlock();
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
            this.agentLockService.getLock(agent).writeLock().lock();
            this.assignedIssue.remove(agent);
            return this.availableAgentForIssue.add(agent);
        } finally {
            this.agentLockService.getLock(agent).writeLock().unlock();
        }
    }

    public boolean remove(Agent agent) {
        try {
            this.agentLockService.getLock(agent).writeLock().lock();
            this.assignedIssue.remove(agent);
            for (IssueType issueType: agent.getIssueTypes())
                this.availableAgentsForIssueType.get(issueType).remove(agent);
            return this.availableAgentForIssue.remove(agent);
        } finally {
            this.agentLockService.getLock(agent).writeLock().unlock();
        }
    }
}
