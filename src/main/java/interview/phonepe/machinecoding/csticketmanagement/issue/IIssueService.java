package interview.phonepe.machinecoding.csticketmanagement.issue;

import interview.phonepe.machinecoding.csticketmanagement.agent.Agent;

import java.util.List;

public interface IIssueService {

    Issue createIssue(String transactionId, IssueType issueType, String subject, String description, String email);

    Issue updateIssue(String issueId, String status, String resolution);
    boolean resolveIssue(String issueId, String resolution);

    Agent assignIssue(String issueId);

    List<Issue> getIssues(String filter);
}
