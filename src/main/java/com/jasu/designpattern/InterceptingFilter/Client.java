package com.jasu.designpattern.InterceptingFilter;

/**
 * @author @Jasu
 * @date 2018-10-10 11:38
 */
public class Client {
    FilterManager filterManager;

    public void setFilterManager(FilterManager filterManager) {
        this.filterManager = filterManager;
    }

    public void sendRequest(String request) {
        filterManager.filterRequest(request);
    }
}
