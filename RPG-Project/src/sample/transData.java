package sample;

import java.io.FileNotFoundException;
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
    Character character;

    public transData(Character c) throws FileNotFoundException {
        character = c;

        keylist = new ArrayList<>();
        this.CreateKeyList();

        valuelist = new ArrayList<>();
        this.CreateValueList();

        this.SaveToFile("save.txt", keylist, valuelist);
    }

    public void CreateKeyList(){

        keylist.add("Name");
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
        keylist.add("Race");
        keylist.add("Hair Color");
        keylist.add("Hair Type");
        keylist.add("Eye Color");
        keylist.add("Body Type");

    }

    /**
     * translate the data class to a list
     */


    public void CreateValueList(){
        valuelist.add("NAME");
        valuelist.add(Integer.toString(character.characterStats.getCharacterLevel()));
        valuelist.add(Integer.toString(character.characterStats.getHealth()));
        valuelist.add(Integer.toString(character.characterStats.getStr()));
        valuelist.add(Integer.toString(character.characterStats.getDex()));
        valuelist.add(Integer.toString(character.characterStats.getCon()));
        valuelist.add(Integer.toString(character.characterStats.getWis()));
        valuelist.add(Integer.toString(character.characterStats.getInt()));
        valuelist.add(Integer.toString(character.characterStats.getCha()));
        valuelist.add(character.characterFeatures.getRace());
        valuelist.add(character.characterFeatures.getHairColor());
        valuelist.add(character.characterFeatures.getHairType());
        valuelist.add(character.characterFeatures.getEyeColor());
        valuelist.add(character.characterFeatures.getBodyType());
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
