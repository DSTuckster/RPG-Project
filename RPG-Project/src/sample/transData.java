package sample;

import java.util.ArrayList;
import java.util.List;

public class transData extends SaveSystem{
    /**
     * create a list contains the data name
     */

    ArrayList<String> keylist;
    ArrayList<String> valuelist;
    public Stats stats= new Stats();
    public Features features= new Features();

    public void CreateKeyList(){

        keylist.add("name");
        keylist.add("Stats");
        keylist.add("Features");
        keylist.add("Story");
        keylist.add("CharacterLevel");
        keylist.add("Health");
        keylist.add("Strength");
        keylist.add("Dexterity");
        keylist.add("Constitution");
        keylist.add("Wisdom");
        keylist.add("Intelligence");
        keylist.add("Charisma");
        keylist.add("race");
        keylist.add("hairColor");
        keylist.add("hairType");
        keylist.add("eyeColor");
        keylist.add("bodyType");

    }

    /**
     * translate the data class to a list
     */


    public void CreateValueList(){
        valuelist.add(Integer.toString(stats.getCharacterLevel()));
        valuelist.add(Integer.toString(stats.getHealth()));
        valuelist.add(Integer.toString(stats.getStr()));
        valuelist.add(Integer.toString(stats.getDex()));
        valuelist.add(Integer.toString(stats.getCon()));
        valuelist.add(Integer.toString(stats.getWis()));
        valuelist.add(Integer.toString(stats.getInt()));
        valuelist.add(Integer.toString(stats.getCha()));
        valuelist.add(features.getRace());
        valuelist.add(features.getHairColor());
        valuelist.add(features.getHairType());
        valuelist.add(features.getEyeColor());
        valuelist.add(features.getBodyType());
    }

    /**
     * set the character data by the file data
     * @param list contain file data
     */
    public void SetValueFromList(ArrayList<String> list){
        stats.setCharacterLevel(Integer.parseInt((list.get(0))));
        stats.setHealth(Integer.parseInt(list.get(1)));
        stats.setStr(Integer.parseInt(list.get(2)));
        stats.setDex(Integer.parseInt(list.get(3)));
        stats.setCon(Integer.parseInt(list.get(4)));
        stats.setWis(Integer.parseInt(list.get(5)));
        stats.setInt(Integer.parseInt(list.get(6)));
        stats.setCha(Integer.parseInt(list.get(7)));
        features.setRace(list.get(8));
        features.setHairColor(list.get(9));
        features.setHairType(list.get(10));
        features.setEyeColor(list.get(11));
        features.setBodyType(list.get(12));
    }


}
