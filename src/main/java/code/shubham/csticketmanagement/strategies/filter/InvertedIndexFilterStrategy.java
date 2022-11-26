package code.shubham.csticketmanagement.strategies.filter;

import code.shubham.commons.index.inverted.InvertedIndex;
import code.shubham.csticketmanagement.issue.Issue;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class InvertedIndexFilterStrategy implements FilterStrategy<Issue> {

    private final InvertedIndex invertedIndex = new InvertedIndex();
    private final Map<String, Issue> issues;

    public InvertedIndexFilterStrategy(Map<String, Issue> issues) {
        this.issues = issues;
    }

    @Override
    public void add(Issue issue) {
        this.invertedIndex.index(issue.getId(), issue);
    }

    @Override
    public Collection<Issue> filter(Map<String, Object> filter) {
        return this.invertedIndex.filter(filter).
                stream().
                map(issues::get).
                collect(Collectors.toList());
    }
}
