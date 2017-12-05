import com.ballthrower.Robot;
import com.ballthrower.communication.ConnectionFactory;
import com.test.NXTTest;
import lejos.nxt.LCD;

public class Main
{
	public static void main(String[] args)
	{
	    Robot robot = Robot.getInstance();
	    robot.addButtonListeners();
	    robot.awaitConnection(new ConnectionFactory());

	    while(true);


		//runTests();
		//while (true){}
    }

    public static void runTests()
	{
		NXTTest tests = new NXTTest();
		tests.runAllTests();
	}
}