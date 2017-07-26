package film.com.viwafo.example.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Created by minhl on 26/07/2017.
 */

public class IOUtil {
    public static void closeQuiety(InputStream in) {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeQuiety(Reader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
