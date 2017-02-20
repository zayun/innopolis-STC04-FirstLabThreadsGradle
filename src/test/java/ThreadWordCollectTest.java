import com.innopolis.smoldyrev.collector.ThreadWordCollect;
import com.innopolis.smoldyrev.collector.WordCollector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;

/**
 * Created by smoldyrev on 14.02.17.
 */
class ThreadWordCollectTest {

    private static WordCollector wordCollector;
    final static String FILE_PATH = "resources/test.txt";

    @BeforeAll
    public static void init() {

        ParsedTextFileTest.newFile("один, два три");
        wordCollector= new WordCollector();

    }

    @Test
    void setError() {

        Object obj = new ThreadWordCollect("", wordCollector);
        try {

            Field threadError = obj.getClass().getDeclaredField("error");
            threadError.setAccessible(true);

            ThreadWordCollect.setError(true);
            Boolean errorValue = new Boolean(threadError.get(obj).toString());
            assertTrue(errorValue);

            ThreadWordCollect.setError(false);
            errorValue = new Boolean(threadError.get(obj).toString());
            assertFalse(errorValue);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void run() {

        Thread t1 = new Thread(new ThreadWordCollect(FILE_PATH, wordCollector));
        Thread t2 = new Thread(new ThreadWordCollect(FILE_PATH, wordCollector));
        Thread t3 = new Thread(new ThreadWordCollect(FILE_PATH, wordCollector));

        ThreadWordCollect.setError(true);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t3.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(0, wordCollector.getWordQty("один").intValue());
        assertEquals(0, wordCollector.getWordQty("два").intValue());
        assertEquals(0, wordCollector.getWordQty("три").intValue());

        t1 = new Thread(new ThreadWordCollect(FILE_PATH, wordCollector));
        t2 = new Thread(new ThreadWordCollect(FILE_PATH, wordCollector));
        t3 = new Thread(new ThreadWordCollect(FILE_PATH, wordCollector));

        ThreadWordCollect.setError(false);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t3.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(3, wordCollector.getWordQty("один").intValue());
        assertEquals(3, wordCollector.getWordQty("два").intValue());
        assertEquals(3, wordCollector.getWordQty("три").intValue());

    }

}