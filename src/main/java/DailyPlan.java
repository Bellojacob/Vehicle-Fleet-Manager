import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DailyPlan {
    private String filepath;
    private String fileContents;

    public DailyPlan(String filepath) {
        this.filepath = filepath;
    }

    public static Scanner fileReader(String filePath) {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("File not found or could not be opened.");
            System.exit(0);
        }
        System.out.println(input);
        return input;
    }

    public String toString(){
        return "Empty for now";
    }
}