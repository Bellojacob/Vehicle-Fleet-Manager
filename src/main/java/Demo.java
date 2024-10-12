// Title:   Demo.java
// Author:  Jacob Bello
// Course:  CST 336
// Date:    10/7/2024
// Abstract: Demo file to run all code

public class Demo {
    public static void main(String[] args) {
        DailyPlan w1 = new DailyPlan("resources/workOrder2.txt");
        System.out.println("REPORT");
        System.out.println(w1);
        System.out.println("\nDETAILS");
        System.out.println(w1.getDetails());
        System.out.println("\uD83C\uDF3F Please consider the environment " +
                "before printing this report. \uD83C\uDF3F");
    }
}
