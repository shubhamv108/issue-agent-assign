package interview.phonepe.machinecoding.csticketmanagement.agent;

import interview.phonepe.machinecoding.csticketmanagement.issue.IssueType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Agents {
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private Set<Agent> availableAgentForIssue = new LinkedHashSet<>();
    private Map<IssueType, Set<Agent>> availableAgentsForIssueType = new HashMap<>(); // move this to seperate strategy
    private Set<Agent> assignedIssue = new HashSet<>();

    private Set<Agent> hasWorked = new HashSet<>();

    private Map<Agent, ReentrantReadWriteLock> agentLocks = new ConcurrentHashMap<>();

    private ReentrantReadWriteLock getLockForAgent(Agent agent) {
        var lock = agentLocks.get(agent);
        if (lock != null)
            return lock;
        synchronized (agent) {
            lock = new ReentrantReadWriteLock();
            this.agentLocks.put(agent, lock);
        }
        return lock;
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
            //read lock
            if (!this.assignedIssue.contains(agent)) {
                this.assignedIssue.add(agent);
                this.availableAgentForIssue.remove(agent);
                return agent;
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
        // read lock
        this.assignedIssue.remove(agent);
        return this.availableAgentForIssue.add(agent);
    }

    public boolean remove(Agent agent) {
        // read lock
        this.assignedIssue.remove(agent);
        return this.availableAgentForIssue.remove(agent);
//        for ()
    }
}
