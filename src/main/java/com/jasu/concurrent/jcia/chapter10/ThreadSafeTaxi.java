package com.jasu.concurrent.jcia.chapter10;


import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.HashSet;
import java.util.Set;

/*****************************************
 * @author hjs
 * @date 2020-02-20 15:55
 *****************************************/
@ThreadSafe
public class ThreadSafeTaxi {
    @GuardedBy("this")
    private Point location, destination;
    private final Dispatcher dispatcher;

    public ThreadSafeTaxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        boolean reachedDestination;
        synchronized (this) {
            this.location = location;
            reachedDestination = location.equals(destination);
        }
        if (reachedDestination) {
            dispatcher.notifyAvailable(this);
        }
    }
    @ThreadSafe
    private class Dispatcher {

        @GuardedBy("this")
        private final Set<ThreadSafeTaxi> taxis;
        private final Set<ThreadSafeTaxi> availableTaxis;

        public Dispatcher(Set<ThreadSafeTaxi> taxis, Set<ThreadSafeTaxi> availableTaxis) {
            this.taxis = taxis;
            this.availableTaxis = availableTaxis;
        }

        public synchronized void notifyAvailable(ThreadSafeTaxi threadSafeTaxi) {
            availableTaxis.add(threadSafeTaxi);
        }

        public Image getImage() {
            Set<ThreadSafeTaxi> copy;
            synchronized (this) {
                copy = new HashSet<>(taxis);
            }
            Image image = new Image();
            for (ThreadSafeTaxi taxi : copy) {
                image.drawMarker(taxi.getLocation());
            }
            return image;
        }
    }


    private class Point{

    }

    private class Image {
        public void drawMarker(Point location) {

        }
    }
}
