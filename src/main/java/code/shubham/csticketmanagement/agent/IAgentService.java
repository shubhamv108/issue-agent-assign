package code.shubham.csticketmanagement.agent;

import code.shubham.csticketmanagement.issue.Issue;
import code.shubham.csticketmanagement.issue.IssueType;

import java.util.List;
import java.util.Map;

public interface IAgentService {
    Agent addAgent(String agentEmail, String agentName , List<IssueType> issueTypes);
    Map<Agent, List<Issue>> viewAgentsWorkHistory();
}
