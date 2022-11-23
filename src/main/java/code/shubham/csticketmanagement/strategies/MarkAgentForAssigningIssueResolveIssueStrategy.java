package code.shubham.csticketmanagement.strategies;

import code.shubham.csticketmanagement.agent.IAgentIssueService;
import code.shubham.csticketmanagement.issue.Issue;

public class MarkAgentForAssigningIssueResolveIssueStrategy implements ResolveIssueStrategy {

    private final IAgentIssueService agentIssueService;

    public MarkAgentForAssigningIssueResolveIssueStrategy(IAgentIssueService agentIssueService) {
        this.agentIssueService = agentIssueService;
    }

    @Override
    public void resolveIssue(Issue issue) {
        this.agentIssueService.returnAgentForAssigingIssue(issue.getAssignedTo());
        issue.setAssignedTo(null);
    }
}
