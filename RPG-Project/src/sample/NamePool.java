package sample;
import java.util.Random;

public class NamePool {

    private static Random r;

    static String[] names = { "Owensalam", "Sullivanslight", "Undeadwang", "Russellrek", "Rainking", "Hoskelis", "Torreflame",
            "Jobow", "Crookedtho", "Harrin The Ellistt", "Fangporter Pearfur", "Onoryant", "Royanka", "Hydraper",
            "Jonesgan Le Fraser", "Angergan", "Abagibson", "Crocothomas", "Lilles Shafang", "Bigjames", "Wickedagui Aumghlin",
            "Burkeg", "Slimerose", "Dunavaca", "Satanrker", "Piercentress", "Gonden", "Flutterhernandez", "Ifera", "Satanmills",
            "Freesparkle", "Stewartdazzle", "Goncauldron", "Lewisger", "Rodri-in-the-Green", "Fangdoherty Hansenwolf",
            "Hudsonshimmer", "Kinganimated", "Smrett", "Turhuge", "Wendrdan", "Jordanshine", "Rileydalf Collinsotter",
            "Ifblack", "Blakor", "Harstat O'reilick", "Gothlane", "Caranic", "Surfisher", "Wardopogo", "Freemanclop",
            "Olawart", "Fogan Le Campbe", "Neykennedy The Myertt", "Foxpus", "Firemas", "Deadnes" };

    public NamePool() {
        r = new Random();
    }

    protected String fetchName() {
        return names[r.nextInt(names.length)];
    }

    public static void main(String[] args) {
        System.out.println(names.length);
    }

}
