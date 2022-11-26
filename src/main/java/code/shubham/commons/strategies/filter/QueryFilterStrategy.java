package code.shubham.commons.strategies.filter;

import code.shubham.commons.query.Query;
import code.shubham.commons.query.clauses.from.FromClause;
import code.shubham.commons.query.clauses.select.SelectClause;
import code.shubham.commons.query.clauses.where.Filter;
import code.shubham.commons.query.clauses.where.WhereClause;
import code.shubham.csticketmanagement.issue.Issue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class QueryFilterStrategy implements FilterStrategy<Issue> {

    private final Collection<Issue> issues = new HashSet<>();

    @Override
    public void add(Issue issue) {
        this.issues.add(issue);
    }

    @Override
    public Collection<Issue> filter(Map<String, Object> filterCondition) {
        SelectClause<Issue, Issue> selectClause = new SelectClause((issue) -> issue);
        WhereClause<Issue> whereClause = new WhereClause<>();
        whereClause.add(new Filter<>(filterCondition));
        FromClause<Issue> fromClause = new FromClause<>(issues);
        Query<Issue, Issue> query = new Query<>(selectClause, fromClause, whereClause);
        return query.apply();
    }
}
