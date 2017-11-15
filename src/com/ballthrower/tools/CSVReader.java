package com.ballthrower.tools;

import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetBoxInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Used for reading TargetBoxInfo data for unit tests
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
        TargetBoxInfo tbi = new TargetBoxInfo( (byte)objSize );
        float realHeight = Float.parseFloat(objects.get(0).split(",")[0]);

        for (int i = 0; i < objects.size(); i++)
        {
            String[] data = objects.get(i).split(",");

            short xPos = Short.parseShort(data[1]);
            float height = Float.parseFloat(data[4]);
            float width = Float.parseFloat(data[3]);

            tbi.getTargets()[i] = new TargetBox(height, width, xPos);
        }

        return new TestTargetBoxInfo(tbi, realHeight);
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
        String[] objs = raw.split("\n");
        ArrayList<String> toReturn = new ArrayList<String>();
        for (String s : objs) { toReturn.add(s); }

        return toReturn;
    }
}
