package interview.phonepe.machinecoding.csticketmanagement.strategies;

import interview.phonepe.machinecoding.csticketmanagement.agent.IAgentIssueService;
import interview.phonepe.machinecoding.csticketmanagement.issue.Issue;

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
