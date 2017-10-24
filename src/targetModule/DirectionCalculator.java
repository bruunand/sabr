
import java.lang.Math.sqrt;
import ITargetBoxInfo.ITargetBoxInfo;

public class DirectionCalculator implements IDirectionCalculateable
{
    private float const _degreesPerPixel = 0.1017;
    public float CalculateDirection(ITargetBoxInfo target)
    {
        int iterations = target.GetSamples();
        float sumDistances = 0;
        int frameMid = target.GetFrameMid();
        for(int i=0;i<iterations;++i)
        {
            int xTopLeft = target.GetXPostion(i);
            int height = target.GetHeight(i);
            int xCentre = xTopLeft + height/2;
            vec2lineX = (frameMid-xCentre);
            sumDistances += (float)sqrt(vec2lineX*vec2lineX)
        }
        int meanDistance = sumDistances/iterations;
        int angle2Rotate = meanDistance * _degreesPerPixel;
        return angle2Rotate;
    }
}