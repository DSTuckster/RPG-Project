package sample;

import java.util.ArrayList;

public class CreditsModel {

    protected static CreditsView creditsView;
    ArrayList<CreditsSubscriber> subscribers;

    public CreditsModel() {
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(CreditsSubscriber sub) {
        subscribers.add(sub);
    }

    public void notifySubscribers() {
        subscribers.forEach(CreditsSubscriber::modelChanged);
    }
}
