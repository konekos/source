package com.jasu.concurrent.jcia.chapter10;

import javax.annotation.concurrent.GuardedBy;
import java.util.HashSet;
import java.util.Set;

/*****************************************
 * @author hjs
 * @date 2020-02-20 2:36
 *****************************************/
public class Taxi {
    @GuardedBy("this")
    private Point location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
    
    
    public synchronized Point getLocation(){
        return location;
    }

    public synchronized void setLocation(Point location) {
        this.location = location;
        if (location.equals(destination)) {
            dispatcher.notifyAvailable(this);
        }
    }

    private class Point{
        
    }

    private class Dispatcher {
        @GuardedBy("this")
        private final Set<Taxi> taxis;
        @GuardedBy("this")
        private final Set<Taxi> availableTaxis;

        public Dispatcher() {
            this.taxis = new HashSet<>();
            this.availableTaxis = new HashSet<>();
        }

        private synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }
        
        public synchronized Image getImage() {
            Image image = new Image();
            for (Taxi taxi : taxis) {
                image.drawMarker(taxi.getLocation());
            }
            return image;
        }
    }

    private class Image {
        public void drawMarker(Point location) {
        }
    }
}
