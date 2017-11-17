package com.test;

/**
 * Assertions boi
 */
public class NXTAssert
{
    private Object _obj;
    private String _methodName;

    public NXTAssert assertThat(Object obj, String fromMethod)
    {
        _obj = obj;
        _methodName = fromMethod;
        return this;
    }

    public NXTAssert()
    {

    }

    public NXTAssert isTrue() throws AssertException
    {
        if (_obj != null &&
            _obj instanceof Boolean)
        {
            if (!(boolean)_obj)
                throw new AssertException("Expected true, got false",
                                          _methodName);
        }

        return this;
    }

    public NXTAssert isFalse() throws AssertException
    {
        if (_obj != null &&
                _obj instanceof Boolean)
        {
            if ((boolean)_obj)
                throw new AssertException("Expected false, got true",
                                          _methodName);
        }

        return this;
    }

    public NXTAssert isNull() throws AssertException
    {
        if (_obj == null)
            throw new AssertException("Object is null",
                                      _methodName);

        return this;
    }

    public NXTAssert isInRangeOf(float target, float range) throws AssertException
    {
        double obj = (double)_obj;
        if (  (obj < target - range) || obj > target + range  )
        {
            throw new AssertException("Object not in range",
                                      _methodName);
        }

        return this;
    }

    public NXTAssert isEqualTo(Object obj) throws AssertException
    {
        if (!_obj.equals(obj))
        {
            throw new AssertException("Objects are not equal",
                                      _methodName);
        }

        return this;
    }
}
