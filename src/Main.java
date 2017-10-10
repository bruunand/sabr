import Communication.IQueryable;
import Movement.Aiming.ITurnable;
import Movement.Shooting.IShootable;
import lejos.nxt.Button;

public class Main
{
    private IQueryable _communicator;
    private ITurnable _turner;
    private IShootable _shooter;

    public static void main(String[] args)
    {
        System.out.println("Iaro ist und faglord");
        Button.waitForAnyPress();
    }
}
