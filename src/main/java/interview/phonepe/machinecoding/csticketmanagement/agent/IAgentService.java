package interview.phonepe.machinecoding.csticketmanagement.agent;

import interview.phonepe.machinecoding.csticketmanagement.issue.Issue;
import interview.phonepe.machinecoding.csticketmanagement.issue.IssueType;

import java.util.List;
import java.util.Map;

public interface IAgentService {
    Agent addAgent(String agentEmail, String agentName , List<IssueType> issueTypes);
    Map<Agent, List<Issue>> viewAgentsWorkHistory();
}
