package com.test;

import com.sabr.exceptions.AssertException;

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
        float obj = (float)_obj;
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
            throw new AssertException("Objects are not equal (" + _obj + " and " + obj + ")",
                                      _methodName);
        }

        return this;
    }

    public NXTAssert containsAll(float[] arr) throws AssertException
    {
        boolean foundEqual = false;
        float misMatch;

        for (float o : arr)
        {
            foundEqual = false;
            for (float _o : (float[])_obj)
            {
                if (_o == (o))
                    foundEqual = true;
            }

            if (!foundEqual)
                throw new AssertException("Original collection did not contain following element: " + o,
                                          _methodName);
        }

        return this;
    }

    public NXTAssert isIn(Object[] collection) throws AssertException
    {
        boolean isIn = false;
        for (Object o : collection)
        {
            if (_obj.equals(o))
                isIn = true;
        }

        if (!isIn)
            throw new AssertException("Object is not in collection", _methodName);

        return this;
    }

    public NXTAssert isNotNull() throws AssertException
    {
        if (_obj == null)
            throw new AssertException("Object is null", _methodName);

        return this;
    }
}
