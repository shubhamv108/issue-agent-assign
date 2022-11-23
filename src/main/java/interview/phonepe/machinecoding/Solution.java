package interview.phonepe.machinecoding;

import interview.phonepe.machinecoding.csticketmanagement.IIssueManageSystem;
import interview.phonepe.machinecoding.csticketmanagement.IssueManagementSystem;
import interview.phonepe.machinecoding.csticketmanagement.agent.AgentService;
import interview.phonepe.machinecoding.csticketmanagement.exceptions.AppException;
import interview.phonepe.machinecoding.csticketmanagement.exceptions.RequestException;
import interview.phonepe.machinecoding.csticketmanagement.issue.IIssueService;
import interview.phonepe.machinecoding.csticketmanagement.issue.Issue;
import interview.phonepe.machinecoding.csticketmanagement.issue.IssueService;
import interview.phonepe.machinecoding.csticketmanagement.issue.IssueType;
import interview.phonepe.machinecoding.csticketmanagement.issue.Issues;
import interview.phonepe.machinecoding.csticketmanagement.strategies.AnyAvailableAgentIssueAssignStrategy;
import interview.phonepe.machinecoding.csticketmanagement.strategies.AssignAgentToIssueStrategy;
import interview.phonepe.machinecoding.csticketmanagement.strategies.MarkAgentForAssigningIssueResolveIssueStrategy;
import interview.phonepe.machinecoding.csticketmanagement.strategies.ResolveIssueStrategy;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        try {
            AgentService agentService = new AgentService();

            Issues issues = new Issues();
            AssignAgentToIssueStrategy assignAgentToIssueStrategy = new AnyAvailableAgentIssueAssignStrategy(agentService);
            ResolveIssueStrategy resolveIssueStrategy = new MarkAgentForAssigningIssueResolveIssueStrategy(agentService);
            IIssueService issueService = new IssueService(issues, assignAgentToIssueStrategy, resolveIssueStrategy);

            IIssueManageSystem issueManageSystem = new IssueManagementSystem(issueService, agentService);

            Issue issue = issueManageSystem.createIssue("T1", IssueType.Gold, "subject", "descriptoion", "email");
            issueManageSystem.addAgent("A1@email", "A1", Arrays.asList(IssueType.Gold));
            issueManageSystem.assignIssue(issue.getId());
            issueManageSystem.viewAgentsWorkHistory();
        } catch (RequestException requestException) {
            System.err.println(requestException.getMessage());
        } catch (AppException appException) {
            System.err.println(appException.getMessage());
        }

    }
}
