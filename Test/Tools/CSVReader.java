package Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Used for reading TargetBoxInfo data for unit tests
 */
public final class CSVReader
{
    /* path should be "\data\test_data_distance.csv" */

    private String getRawData(String path)
    {
        try
        {
            File file = new File("a.txt");
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
}
