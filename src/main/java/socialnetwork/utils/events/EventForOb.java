package socialnetwork.utils.events;

public class EventForOb {
    private EntityEventType entityEventType;
    private EventType eventType;

    public EntityEventType getEntityEventType() {
        return entityEventType;
    }

    public void setEntityEventType(EntityEventType entityEventType) {
        this.entityEventType = entityEventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventForOb(EntityEventType entityEventType, EventType eventType) {
        this.entityEventType = entityEventType;
        this.eventType = eventType;
    }
}
