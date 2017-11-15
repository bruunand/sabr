package Tools.unitTests.com.ballthrower;

import com.ballthrower.tools.CSVReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.ballthrower.Robot;

class RobotTest {
    @BeforeEach
    void setUp() {
        System.out.println();
        String path = System.getProperty("user.dir") + "\\data\\test_data_distance.csv";
        CSVReader cr = new CSVReader();
        System.out.println(cr.getData(path));
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * Should return a robot instance
     */
    @Test
    void getInstance() {
        assertTrue(Robot.getInstance() instanceof Robot);
    }

    /*@Test
    void addButtonListeners() {
         Not able to test
    }*/

    /*@Test
    void locateAndShoot()
    {

    }*/

    @Test
    void awaitConnection() {

    }

    @Test
    void abort() {

    }

    @Test
    void abort1() {

    }

}