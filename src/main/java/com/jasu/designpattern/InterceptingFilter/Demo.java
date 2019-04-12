package com.jasu.designpattern.InterceptingFilter;

/**
 * @author @Jasu
 * @date 2018-10-10 11:40
 */
public class Demo {
    public static void main(String[] args) {

        FilterManager filterManager = new FilterManager(new Target());
        filterManager.setFilter(new AuthenticationFilter());
        filterManager.setFilter(new DebugFilter());

        Client client = new Client();
        client.setFilterManager(filterManager);
        client.sendRequest("111111111111111");
    }
}
