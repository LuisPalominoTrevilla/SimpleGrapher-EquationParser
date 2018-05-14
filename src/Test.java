
public class Test {

    public static void main(String[] args) {
        Equation e = new Equation("5+(-3)");
        System.out.println(e.evalEquation(-2));
        System.out.println("5".matches("-?\\d+(\\.\\d+)?"));
    }

}
