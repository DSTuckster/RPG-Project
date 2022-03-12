package sample;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class transData {
    
    ArrayList<String> keylist = new ArrayList<>();
    ArrayList<String> valuelist = new ArrayList<>();

    /**
     * translate the data class to a list
     */
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
     *@param character the character you want to update
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

    //set character a new name
    public void setName(Character character, String name){
        character.name = name;
    }
    //set character a new race
    public void setRace(Character character, String race){
        character.characterFeatures.setRace(race);
    }
    //set character a new hair color
    public void setHairColor(Character character, String hairColor){character.characterFeatures.setHairColor(hairColor);}
    //set character a new hair type
    public void setHairType(Character character, String hairType){
        character.characterFeatures.setHairType(hairType);
    }
    //set character a new eye color
    public void setEyeColor(Character character, String eyeColor){
        character.characterFeatures.setEyeColor(eyeColor);
    }
    //set character a new body type
    public void setBodyType(Character character, String bodyType){
        character.characterFeatures.setBodyType(bodyType);
    }
    //set character a new level
    public void setCharacterLevel(Character character, Integer level){ character.characterStats.setCharacterLevel(level);}
    //set character a new health
    public void setHealth(Character character, Integer health){ character.characterStats.setHealth(health);}
    //set character a new strength
    public void setStr(Character character, Integer str){ character.characterStats.setStr(str);}
    //set character a new dexterity
    public void setDex(Character character, Integer dex){ character.characterStats.setDex(dex);}
    //set character a new constitution
    public void setCon(Character character, Integer con){ character.characterStats.setCon(con);}
    //set character a new wisdom
    public void setWis(Character character, Integer wis){ character.characterStats.setWis(wis);}
    //set character a new intelligence
    public void setInt(Character character, Integer intelligence){ character.characterStats.setInt(intelligence);}
    //set character a new charisma
    public void setCha(Character character, Integer cha){ character.characterStats.setCha(cha);}


}
