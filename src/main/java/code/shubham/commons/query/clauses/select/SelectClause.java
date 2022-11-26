package code.shubham.commons.query.clauses.select;

import code.shubham.commons.query.result.AbstractQueryResult;

import java.util.function.Function;

public class SelectClause<Data, Response> implements Function<Data, Response> {

    private Function<Data, Response> selector;

    public SelectClause(Function<Data, Response> selector) {
        this.selector = selector;
    }

    public Response apply(Data data) {
        return this.selector.apply(data);
    }

}
