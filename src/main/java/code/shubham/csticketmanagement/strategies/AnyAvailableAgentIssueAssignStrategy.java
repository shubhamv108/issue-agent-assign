package code.shubham.csticketmanagement.strategies;

import code.shubham.csticketmanagement.agent.IAgentIssueService;
import code.shubham.csticketmanagement.agent.Agent;
import code.shubham.csticketmanagement.exceptions.AppException;
import code.shubham.csticketmanagement.issue.Issue;

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
