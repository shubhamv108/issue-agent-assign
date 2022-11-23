package code.shubham.csticketmanagement.agent;

import code.shubham.csticketmanagement.issue.IssueType;

public interface IAgentIssueService {

    Agent markFirstAgentForAssingingIssue(IssueType issueType);

    void markhasWorked(Agent agent);

    boolean returnAgentForAssigingIssue(Agent agent);
}
