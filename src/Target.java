/**
 * Created by Anders Brams on 10/9/2017.
 * Used for representing the targets in 2D..
 */
public class Target {
    public float x;
    public float y;

    public Target(float _x, float _y) {
        x = _x;
        y = _y;
    }

    public Target() {
        x = 0;
        y = 0;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
