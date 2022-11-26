package code.shubham.csticketmanagement;

import code.shubham.csticketmanagement.agent.Agent;
import code.shubham.csticketmanagement.agent.AgentFactory;
import code.shubham.csticketmanagement.agent.IAgentService;
import code.shubham.csticketmanagement.issue.IIssueService;
import code.shubham.csticketmanagement.issue.Issue;
import code.shubham.csticketmanagement.issue.IssueFactory;
import code.shubham.csticketmanagement.issue.IssueType;
import code.shubham.csticketmanagement.strategies.AgentIssueStrategy;
import code.shubham.csticketmanagement.response.IssueResolveResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueManagementSystem implements IIssueManageSystem {

    private final IIssueService issueService;
    private final IAgentService agentService;

    private final IssueFactory issueFactory;
    private final AgentFactory agentFactory;

    private final AgentIssueStrategy agentIssueStrategy;

    private final Gson GSON = new Gson();

    public IssueManagementSystem(IssueFactory issueFactory, AgentFactory agentFactory,
                                 IIssueService issueService, IAgentService agentService,
                                 AgentIssueStrategy agentIssueStrategy) {
        this.issueFactory = issueFactory;
        this.agentFactory = agentFactory;
        this.issueService = issueService;
        this.agentService = agentService;
        this.agentIssueStrategy = agentIssueStrategy;
    }

    @Override
    public void createIssue(String transactionId, IssueType issueType, String subject, String description, String email) {
        Issue issue = this.issueService.create(this.issueFactory.get(transactionId, issueType, subject, description, email));
        System.out.println(String.format("Issue %s created against transaction %s", issue.getId(), transactionId));
    }

    @Override
    public void addAgent(String agentEmail, String agentName, List<IssueType> issueTypes) {
        Agent agent = this.agentFactory.get(agentName, agentEmail, issueTypes);
        this.agentService.add(agent);
        this.agentIssueStrategy.add(agent);
        System.out.println(String.format("Agent %s created", agent.getId()));
    }

    @Override
    public void removeAgent(String agentId) {
        Agent agent = this.agentService.remove(agentId);
        this.agentIssueStrategy.remove(agent);
        System.out.println(String.format("Agent %s removed", agent));
    }

    @Override
    public void assignIssue(String issueId) {
        Issue issue = this.issueService.getById(issueId);
        Agent agent = this.agentIssueStrategy.assign(issue);
        System.out.println(String.format("Issue %s assigned to agent %s", issueId, agent.getId()));
    }

    @Override
    public void getIssues(String filter) {
        Map<String, Object> filterMap = GSON.fromJson(filter, Map.class);
        Collection<Issue> issues = this.issueService.get(filterMap);
        System.out.println(issues);
    }

    @Override
    public void viewAgentsWorkHistory() {
        Collection<Agent> agents = this.agentIssueStrategy.getWorkedAgents();
        Map<Agent, List<Issue>> worked = new HashMap<>();
        for (Agent agent: agents)
            worked.put(agent, new ArrayList<>(agent.getWorkedOn()));
        System.out.println(worked);
    }

    @Override
    public void updateIssue(String issueId, String status, String resolution) {
        Issue issue = this.issueService.update(issueId, status, resolution);
        System.out.println(String.format("%s issue updated", issue));
    }

    @Override
    public void resolveIssue(String issueId, String resolution) {
        IssueResolveResponse response = this.issueService.resolve(issueId, resolution);
        this.agentIssueStrategy.add(response.getIssueType(), response.getAgent());
        System.out.println(String.format("%s issue marked resolved", issueId));
    }
}
