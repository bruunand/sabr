import Movement.Aiming.IRotator;
import Movement.Aiming.Rotator;
import lejos.nxt.Button;
import lejos.nxt.Motor;

public class Main {

    private static IRotator _rotater;

    public static void main(String[] args) {
        System.out.println("Iaro ist und faglord");

        _rotater = new Rotator(Motor.A);
        _rotater.turn(200);
    }
}
