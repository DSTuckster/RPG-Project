package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Names {
    protected String name;
    protected ArrayList<String> first;
    protected ArrayList<String> last;
    protected Random r;
    protected String[] firstNames = {"Joe", "Jack", "The", "Melon", "Lemon", "Rage", "Pop", "Random", ""};
    protected String[] lastNames = {"Thunder", "Rowdy", "Constructor", "Maker", "Tab", "Fluff"};

    public Names(){
        first = new ArrayList<>();
        last = new ArrayList<>();
        first.addAll(List.of(firstNames));
        last.addAll(List.of(lastNames));

        r = new Random();
        name = first.get(r.nextInt(firstNames.length)) + " " + last.get((r.nextInt(lastNames.length)));
    }
}
