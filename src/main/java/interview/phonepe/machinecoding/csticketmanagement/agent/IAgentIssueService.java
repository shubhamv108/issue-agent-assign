package interview.phonepe.machinecoding.csticketmanagement.agent;

import interview.phonepe.machinecoding.csticketmanagement.issue.IssueType;

public interface IAgentIssueService {

    Agent markFirstAgentForAssingingIssue(IssueType issueType);

    void markhasWorked(Agent agent);

    boolean returnAgentForAssigingIssue(Agent agent);
}
