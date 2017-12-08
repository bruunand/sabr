package com.ballthrower.tools;

import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Used for reading TargetContainer data for unit tests
 */
public class CSVReader
{
    int objSize = 6;

    /* path should be "...\data\test_data_distance.csv" */
    public ArrayList<TestTargetBoxInfo> getData(String path)
    {
        ArrayList<TestTargetBoxInfo> toReturn = new ArrayList<TestTargetBoxInfo>();

        String raw = getRawData(path);
        ArrayList<String> objects = getObjects(raw);

        for (int i = 1; i < objects.size(); i += objSize)
        {
            toReturn.add(
                    TargetBoxFromData(
                        objects.subList(i, i + objSize)));
        }

        return toReturn;
    }

    private TestTargetBoxInfo TargetBoxFromData(List<String> objects)
    {
        TargetContainer tbi = new TargetContainer( (byte)objSize );
        float realHeight = (split(objects.get(0), ",")[0] != "") ?
                            0 : Float.parseFloat(split(objects.get(0), ",")[0]);

        for (int i = 0; i < objects.size(); i++)
        {
            String[] data = split(objects.get(i), ",");

            short xPos = Short.parseShort(data[1]);
            float height = Float.parseFloat(data[4]);
            float width = Float.parseFloat(data[3]);

            tbi.getTargets()[i] = new TargetBox(height, width, xPos);
        }

        return new TestTargetBoxInfo(tbi, realHeight);
    }

    /**
     * Splits the input string and returns a string array.
     * If splitting by new lines, split by the '\r' character, not '\n'.
     * */
    public String[] split(String str, String regex)
    {
        ArrayList<String> result = new ArrayList<String>();
        int start = 0;
        int pos = str.indexOf(regex);

        while (pos >= start)
        {
            if (pos > start)
            {
                result.add(str.substring(start,pos));
            }
            start = pos + regex.length();
            result.add(regex);
            pos = str.indexOf(regex,start);
        }

        if (start < str.length())
        {
            result.add(str.substring(start));
        }

        /* Get rid of the splitters, null and empty elements */
        result = removeIfEquals(result, regex);
        result = removeIfEquals(result, "");
        result = removeIfEquals(result, null);

        String[] array = result.toArray(new String[0]);
        return array;
    }

    public ArrayList<String> removeIfEquals(ArrayList<String> col, String test)
    {
        ArrayList<String> result = new ArrayList<String>();
        for (String s : col)
        {
            if (!s.equals(test))
                result.add(s);
        }
        return result;
    }

    private String getRawData(String path)
    {
        try
        {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            return new String(data, "UTF-8");

        }
        catch (FileNotFoundException e)
        {
            System.out.println("The file " + path + " was not found. ");
            e.printStackTrace();
            return null;
        }

        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<String> getObjects(String raw)
    {
        String[] objs = split(raw, "\r\n");
        ArrayList<String> toReturn = new ArrayList<String>();
        Collections.addAll(toReturn, objs);

        return toReturn;
    }
}
