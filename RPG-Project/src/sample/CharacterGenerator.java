package sample;

import java.util.ArrayList;

public class CharacterGenerator{
    Character character;
    ArrayList<CharacterSubscribers> subs;

    public CharacterGenerator() {
        subs = new ArrayList<>();
    }


    public void generateRandom() {
        character = new Character();
        notifySubscribers();
    }

    public void addSubscriber (CharacterSubscribers sub) {subs.add(sub);}

    public void notifySubscribers(){
        for (CharacterSubscribers sub : subs){
            sub.modelChanged();
        }
    }
}
