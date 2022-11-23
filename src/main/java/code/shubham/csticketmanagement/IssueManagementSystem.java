package code.shubham.csticketmanagement;

import code.shubham.csticketmanagement.agent.Agent;
import code.shubham.csticketmanagement.agent.IAgentService;
import code.shubham.csticketmanagement.issue.IIssueService;
import code.shubham.csticketmanagement.issue.Issue;
import code.shubham.csticketmanagement.issue.IssueType;

import java.util.List;
import java.util.Map;

public class IssueManagementSystem implements IIssueManageSystem {

    private final IIssueService issueService;
    private final IAgentService agentService;

    public IssueManagementSystem(IIssueService issueService, IAgentService agentService) {
        this.issueService = issueService;
        this.agentService = agentService;
    }

    @Override
    public Agent assignIssue(String issueId) {
        Agent agent = this.issueService.assignIssue(issueId);
        System.out.println(String.format("Issue %s assigned to agent %s", issueId, agent.getId()));
        return agent;
    }

    @Override
    public List<Issue> getIssues(String filter) {
        return null;
    }

    @Override
    public Map<Agent, List<Issue>> viewAgentsWorkHistory() {
        Map<Agent, List<Issue>> result = this.agentService.viewAgentsWorkHistory();
        System.out.println(result);
        return result;
    }

    @Override
    public Agent addAgent(String agentEmail, String agentName, List<IssueType> issueTypes) {
        Agent agent = this.agentService.addAgent(agentEmail, agentName, issueTypes);
        System.out.println(String.format("Agent %s created", agent.getId()));
        return agent;
    }

    @Override
    public Issue createIssue(String transactionId, IssueType issueType, String subject, String description, String email) {
        Issue issue = this.issueService.createIssue(transactionId, issueType, subject, description, email);
        System.out.println(String.format("Issue %s created against transaction %s", issue.getId(), transactionId));
        return issue;
    }

    @Override
    public Issue updateIssue(String issueId, String status, String resolution) {
        Issue issue = this.updateIssue(issueId, status, resolution);
        System.out.println(String.format("%s issue updated", issue));
        return issue;
    }

    @Override
    public boolean resolveIssue(String issueId, String resolution) {
        boolean result = this.issueService.resolveIssue(issueId, resolution);
        if (result)
            System.out.println(String.format("%s issue marked resolved", issueId));
        return result;
    }
}
