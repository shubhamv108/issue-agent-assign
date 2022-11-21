package interview.phonepe.machinecoding.csticketmanagement.strategies;

import interview.phonepe.machinecoding.csticketmanagement.agent.Agent;
import interview.phonepe.machinecoding.csticketmanagement.agent.IAgentIssueService;
import interview.phonepe.machinecoding.csticketmanagement.exceptions.AppException;
import interview.phonepe.machinecoding.csticketmanagement.issue.Issue;

public class AnyAvailableAgentIssueAssignStrategy implements AssignAgentToIssueStrategy {

    private final IAgentIssueService agentIssueService;

    public AnyAvailableAgentIssueAssignStrategy(IAgentIssueService agentIssueService) {
        this.agentIssueService = agentIssueService;
    }

    public void assign(Issue issue) {
        Agent agent = this.agentIssueService.markFirstAgentForAssingingIssue(issue.getIssueType());
        if (agent == null)
            throw new AppException("No Agent Available");
        agent.setAssignedIssue(issue);
        this.agentIssueService.markhasWorked(agent);
    }
}
