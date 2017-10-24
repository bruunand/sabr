

public class TargetBoxInfo implements ITargetBoxInfo
{
    TargetBoxInfo(int samples)
    {
        this.samples = samples;
        _boxWidths = new float[samples];
        _boxHeights = new float[samples];
        _xPositions = new int[samples];
    }
    private int _samples;
    private float[] _boxWidths;
    private float[] _boxHeights;
    private int[] _xPositions;
    private float _frameMid;

    public void SetWidth(int index, float val)
    {
        _boxWidths[index] = val;
    }
    public float SetHeight(int index, float val)
    {
        _boxHeights[index] = val;
    }
    public int SetXTopPos(int index, int val)
    {
        _xPositions[index] = val;
    }
    public float SetFrameMid(int val)
    {
        _frameMid = val;
    }
    public float GetWidth(int index)
    {
        return _boxWidths[index];
    }
    public float GetHeight(int index)
    {
        return _boxHeights[index];
    }
    public int GetXTopPos(int index)
    {
        return _xPositions[index];
    }
    public float GetFrameMid()
    {
        return _frameMid;
    }
    public int GetSamples()
    {
        return _samples;
    }
}