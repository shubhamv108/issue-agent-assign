package code.shubham.csticketmanagement.strategies.filter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FilterStrategy<Data> {
    void add(Data data);
    Collection<Data> filter(Map<String, Object> filter);
}
