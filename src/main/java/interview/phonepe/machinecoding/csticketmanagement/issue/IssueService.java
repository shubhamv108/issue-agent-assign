package interview.phonepe.machinecoding.csticketmanagement.issue;

import interview.phonepe.machinecoding.csticketmanagement.agent.Agent;
import interview.phonepe.machinecoding.csticketmanagement.exceptions.RequestException;
import interview.phonepe.machinecoding.csticketmanagement.strategies.AssignAgentToIssueStrategy;
import interview.phonepe.machinecoding.csticketmanagement.strategies.ResolveIssueStrategy;

import java.util.List;

public class IssueService implements IIssueService {

    private final Issues issues;

    private final AssignAgentToIssueStrategy assignAgentToIssueStrategy;
    private final ResolveIssueStrategy resolveIssueStrategy;

    public IssueService(Issues issues, AssignAgentToIssueStrategy assignAgentToIssueStrategy, ResolveIssueStrategy resolveIssueStrategy) {
        this.issues = issues;
        this.assignAgentToIssueStrategy = assignAgentToIssueStrategy;
        this.resolveIssueStrategy = resolveIssueStrategy;
    }

    @Override
    public Issue createIssue(String transactionId, IssueType issueType, String subject, String description, String email) {
        Issue issue = new Issue(transactionId, issueType, subject, description, email);
        return this.issues.add(issue);
    }

    @Override
    public Issue updateIssue(String issueId, String status, String resolution) {
        Issue issue =  this.getById(issueId);
        issue.setStatus(status);
        issue.setResolution(resolution);
        return issue;
    }

    @Override
    public boolean resolveIssue(String issueId, String resolution) {
        Issue issue = this.issues.resolve(issueId, resolution);
        if (issue.isResolved()) {
            this.resolveIssueStrategy.resolveIssue(issue);
            return true;
        }
        return false;
    }

    @Override
    public Agent assignIssue(String issueId) {
        Issue issue = this.getById(issueId);
        this.assignAgentToIssueStrategy.assign(issue);
        return issue.getAssignedTo();
    }

    @Override
    public List<Issue> getIssues(String filter) {
        return null;
    }

    private Issue getById(String id) {
        Issue issue =  this.issues.getById(id);
        if (issue == null)
            throw new RequestException("Issue Not Found");
        return issue;
    }
}
