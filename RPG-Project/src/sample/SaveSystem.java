package sample;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveSystem {
    /**
     *save the particular type of data to the file you want to name
     * @param Filename the file name you want to set
     * @param character the character want to save
     */
    public static void SaveToFile(String Filename, Character character) throws FileNotFoundException {
        transData data=new transData();
        ArrayList<String> key=data.CreateKeyList();
        ArrayList<String> value=data.CreateValueList(character);
        int length = 0;
        File savefile = new File(Filename);
        PrintWriter fileName = new PrintWriter(savefile);
        while (length<4){
            fileName.println(key.get(length)+ " : ");
            if(length ==0){
                fileName.println(value.get(length));
            }
            if(length ==1){
                int i=0;
                while (i<=7){
                    fileName.println(key.get(i+4)+" :");
                    fileName.println(value.get(i+1));
                    i++;
                }
            }
            if(length ==2){
                int o =0;
                while (o<=4){
                    fileName.println(key.get(o+12)+" :");
                    fileName.println(value.get(o+9));
                    o++;
                }
            }
            if(length ==3){
                fileName.println(value.get(value.size()-1));
            }
            length++;
        }
        fileName.close();
    }

    /**
     * load the content of the chose file
     * @param filename file name you want to load
     * @param character the character would contain the data from the file
     */
    public static void LoadFile(String filename, Character character) throws FileNotFoundException {

        transData data =new transData();
        ArrayList<String> value = data.CreateValueList(character);
        ArrayList<String> key = data.CreateKeyList();
        File thefile = new File(filename);
        Scanner scan = new Scanner(thefile);
        int length = 0;
        int i=0;
        while(length<=3){
            key.set(length,scan.nextLine());
            if(length==0){
                value.set(length,scan.nextLine());
            }

            if(length==1){
                while(i<=7){
                    i++;
                    scan.nextLine();
                    value.set(i,scan.nextLine());
                }
            }
            if(length ==2){
                while(i<=12){
                    i++;
                    scan.nextLine();
                    value.set(i,scan.nextLine());
                }
            }
            if(length==3){
                i++;
                value.set(i,scan.nextLine());
            }
            length++;
        }
        data.SetValueFromList(value,character);

    }
}

