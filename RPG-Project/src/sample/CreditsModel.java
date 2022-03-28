package sample;

import java.util.ArrayList;

public class CreditsModel {

    ArrayList<CreditsSubs> subscribers;

    public CreditsModel() {
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(CreditsSubs sub) {
        subscribers.add(sub);
    }

    public void notifySubscribers() {
        subscribers.forEach(CreditsSubs::modelChanged);
    }
}
