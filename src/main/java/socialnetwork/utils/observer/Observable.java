package socialnetwork.utils.observer;


import socialnetwork.utils.events.EventForOb;

public abstract class Observable {
    public abstract void addObserver(Observer e);
    public abstract void removeObserver(Observer e);
    public abstract void notifyObservers(EventForOb x);
}
