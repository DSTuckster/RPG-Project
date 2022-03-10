package sample;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class transData {
    /**
     * translate the data class to a list
     * @param character the data type from class
     */
    public ArrayList<String> keylist= new ArrayList<>();
    public ArrayList<String> valuelist= new ArrayList<>();

    public ArrayList<String> CreateKeyList(){
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
        return keylist;

    }

    /**
     * translate the data class to a list
     * @return
     */
    public ArrayList<String> CreateValueList(Character character){
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
        return valuelist;
    }

    /**
     * set the values of stats and features
     * @param list the values of stats and features you want to set
     */
    public void SetValueFromList(ArrayList<String> list, Character character){
        character.characterStats.setCharacterLevel(Integer.parseInt((list.get(0))));
        character.characterStats.setHealth(Integer.parseInt(list.get(1)));
        character.characterStats.setStr(Integer.parseInt(list.get(2)));
        character.characterStats.setDex(Integer.parseInt(list.get(3)));
        character.characterStats.setCon(Integer.parseInt(list.get(4)));
        character.characterStats.setWis(Integer.parseInt(list.get(5)));
        character.characterStats.setInt(Integer.parseInt(list.get(6)));
        character.characterStats.setCha(Integer.parseInt(list.get(7)));
        character.characterFeatures.setRace(list.get(8));
        character.characterFeatures.setHairColor(list.get(9));
        character.characterFeatures.setHairType(list.get(10));
        character.characterFeatures.setEyeColor(list.get(11));
        character.characterFeatures.setBodyType(list.get(12));
    }
    public void setName(Character character, String name){
        character.setName(name);
    }
    public void setRace(Character character, String race){
        character.characterFeatures.setRace(race);
    }
    public void setHairColor(Character character, String hairColor){character.characterFeatures.setHairColor(hairColor);}
    public void setHairType(Character character, String hairType){
        character.characterFeatures.setHairType(hairType);
    }
    public void setEyeColor(Character character, String eyeColor){
        character.characterFeatures.setEyeColor(eyeColor);
    }
    public void setBodyType(Character character, String bodyType){
        character.characterFeatures.setBodyType(bodyType);
    }
    public void setCharacterLevel(Character character, Integer level){ character.characterStats.setCharacterLevel(level);}
    public void setHealth(Character character, Integer health){ character.characterStats.setHealth(health);}
    public void setStr(Character character, Integer str){ character.characterStats.setStr(str);}
    public void setDex(Character character, Integer dex){ character.characterStats.setDex(dex);}
    public void setCon(Character character, Integer con){ character.characterStats.setCon(con);}
    public void setWis(Character character, Integer wis){ character.characterStats.setWis(wis);}
    public void setInt(Character character, Integer intelligent){ character.characterStats.setInt(intelligent);}
    public void setCha(Character character, Integer cha){ character.characterStats.setCha(cha);}/**
     * just a little try of pushing from the comand line
     */


}
