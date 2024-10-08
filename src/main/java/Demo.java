public class Demo {
    public static void main(String[] args) {
        DailyPlan w1 = new DailyPlan("resources/workOrder1.txt");
        System.out.println("REPORT");
        System.out.println(w1);
        System.out.println("DETAILS");
//        System.out.println(w1.getDetails());
        System.out.println("\uD83C\uDF3F Please consider the environment " +
                "before printing this report. \uD83C\uDF3F");
    }
}
