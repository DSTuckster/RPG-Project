package sample;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.*;

public class SaveSystem {
    /**
     *save the particular type of data to the file you want to name
     * @param path the file name you want to set
     * @param key list contains the name of the data you want to save
     * @param value list contains the content you want to save
     * @throws FileNotFoundException
     */
    Character character;
    transData transData;

    public void SaveToFile(String path, ArrayList<String> key, ArrayList<String> value) throws FileNotFoundException {
        int length = 0;
        File savefile = new File(path);
        PrintWriter filenName = new PrintWriter(savefile);

        while (length<=4){

            filenName.println(key.get(length)+ " : ");
            if(length==0){
                filenName.println(value.get(0));
            }
            if(length ==1){
                int i=1;
                while (i<=8){
                    filenName.println(key.get(i+3));
                    filenName.println(value.get(i));
                    i++;
                }
            }
            if(length ==2){
                int o =0;
                while (o<=4){
                    filenName.println(key.get(o+12));
                    filenName.println(value.get(o+9));
                    o++;
                }
            }
            length++;

        }
        filenName.close();
    }

    /**
     * load the content of the chose file
     * @param filename file name you want to load
     * @param key list that would contain the data from file
     * @param value list that would contain the data from file
     * @throws FileNotFoundException
     */
    public void LoadFile(String filename, ArrayList<String> key, ArrayList<String> value) throws FileNotFoundException {
        File thefile = new File(filename);
        Scanner scan = new Scanner(thefile);
        int length = 0;
        int i=0;
        while(length>=0){
            key.set(i,scan.nextLine());
            if(length==1){
                while(i<=7){
                    value.set(i,scan.nextLine());
                    i++;
                }
            }
            if(length ==2){
                while(i<=12){
                    value.set(i,scan.nextLine());
                    i++;
                }
            }
            value.set(i,scan.nextLine());
            length++;

        }
        transData.SetValueFromList(value);

    }
}


