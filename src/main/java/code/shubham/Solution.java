package code.shubham;

import code.shubham.csticketmanagement.IIssueManageSystem;
import code.shubham.csticketmanagement.IssueManagementSystem;
import code.shubham.csticketmanagement.agent.AgentFactory;
import code.shubham.csticketmanagement.agent.AgentService;
import code.shubham.csticketmanagement.exceptions.AppException;
import code.shubham.csticketmanagement.exceptions.RequestException;
import code.shubham.csticketmanagement.issue.IIssueService;
import code.shubham.csticketmanagement.issue.Issue;
import code.shubham.csticketmanagement.issue.IssueFactory;
import code.shubham.csticketmanagement.issue.IssueService;
import code.shubham.csticketmanagement.issue.IssueType;
import code.shubham.csticketmanagement.strategies.AgentIssueStrategy;
import code.shubham.commons.strategies.filter.FilterStrategy;
import code.shubham.commons.strategies.filter.InvertedIndexFilterStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {
        try {
            final IssueFactory issueFactory = new IssueFactory();
            final AgentFactory agentFactory = new AgentFactory();
            final AgentIssueStrategy agentIssueStrategy = new AgentIssueStrategy();
            final AgentService agentService = new AgentService();

            final Map<String, Issue> issues = new HashMap<>();
            final FilterStrategy<Issue> filterStrategy = new InvertedIndexFilterStrategy(issues);
            final IIssueService issueService = new IssueService(issues, filterStrategy);

            final IIssueManageSystem issueManageSystem = new IssueManagementSystem(
                    issueFactory, agentFactory, issueService, agentService, agentIssueStrategy);

            issueManageSystem.createIssue("T1", IssueType.Gold, "subject", "description", "email");
            issueManageSystem.addAgent("A1@email", "A1", Arrays.asList(IssueType.Gold));
            issueManageSystem.assignIssue("I1");
            issueManageSystem.updateIssue("I1", "REOPEN", "Reopened after resolution completion.");
            issueManageSystem.viewAgentsWorkHistory();
            issueManageSystem.getIssues("{\"email\":\"email\"}");
        } catch (RequestException requestException) {
            System.err.println(requestException.getMessage());
        } catch (AppException appException) {
            System.err.println(appException.getMessage());
        }
    }
}
