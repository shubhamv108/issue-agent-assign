package code.shubham.csticketmanagement;

import code.shubham.csticketmanagement.issue.IssueType;

import java.util.List;

public interface IIssueManageSystem {
    void createIssue(String transactionId, IssueType issueType, String subject, String description, String email);

    void updateIssue(String issueId, String status, String resolution);
    void resolveIssue(String issueId, String resolution);

    void assignIssue(String issueId);

    void getIssues(String filter);

    void addAgent(String agentEmail, String agentName , List<IssueType> issueTypes);
    void viewAgentsWorkHistory();

    void removeAgent(String agentId);
}
