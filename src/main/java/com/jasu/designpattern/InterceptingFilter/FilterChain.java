package com.jasu.designpattern.InterceptingFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @Jasu
 * @date 2018-10-10 11:32
 */
public class FilterChain {
    private List<Filter> filters = new ArrayList<>();
    private Target target;

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void execute(String request) {
        for (Filter filter : filters) {
            filter.execute(request);
        }

        target.execute(request);
    }

    public void setTarget(Target target) {
        this.target = target;
    }
}
