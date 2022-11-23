package code.shubham;

import code.shubham.commons.LockService;
import code.shubham.csticketmanagement.IssueManagementSystem;
import code.shubham.csticketmanagement.exceptions.RequestException;
import code.shubham.csticketmanagement.strategies.AnyAvailableAgentIssueAssignStrategy;
import code.shubham.csticketmanagement.strategies.AssignAgentToIssueStrategy;
import code.shubham.csticketmanagement.strategies.MarkAgentForAssigningIssueResolveIssueStrategy;
import code.shubham.csticketmanagement.strategies.ResolveIssueStrategy;
import code.shubham.csticketmanagement.IIssueManageSystem;
import code.shubham.csticketmanagement.agent.Agent;
import code.shubham.csticketmanagement.agent.AgentService;
import code.shubham.csticketmanagement.agent.Agents;
import code.shubham.csticketmanagement.exceptions.AppException;
import code.shubham.csticketmanagement.issue.IIssueService;
import code.shubham.csticketmanagement.issue.Issue;
import code.shubham.csticketmanagement.issue.IssueService;
import code.shubham.csticketmanagement.issue.IssueType;
import code.shubham.csticketmanagement.issue.Issues;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        try {
            Agents agents = new Agents();
            AgentService agentService = new AgentService(agents);

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
