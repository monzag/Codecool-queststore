package views;


public class CodecoolerView {

    public static String getString(String keyword) {
        Scanner input = new Scanner(System.in);

        System.out.println("Provide " + keyword + ": ");
        return input.nextLine();

    }


    public static void reportResult(boolean state) {
        if (state) {
            System.out.println("Sucess!");
        } else {
            System.out.println("Failed!");
        }

    }


}
