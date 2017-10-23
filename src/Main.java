import Movement.Aiming.IRotator;
import Movement.Aiming.Rotator;
import Movement.MovementController;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;

public class Main {

    private static MovementController movementController;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iaro ist und faglord");

        movementController = new MovementController(Motor.C, Motor.B);
        movementController.getRotator().turnDegrees(90);

        Motor.A.setSpeed(720);// 2 RPM
        Motor.B.setSpeed(720);
        Motor.A.backward();
        Motor.B.backward();
        Thread.sleep (1000);
        Motor.A.stop();
        Motor.B.stop();
    }
}
