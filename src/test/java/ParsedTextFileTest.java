import com.innopolis.smoldyrev.exeptions.IllegalStringLineExeption;
import com.innopolis.smoldyrev.resource.ParsedTextFile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by smoldyrev on 13.02.17.
 */
class ParsedTextFileTest {

    private static ParsedTextFile ptf;
    private static FileWriter writer;
    final static String FILE_PATH = "resources/test.txt";


    @BeforeAll
    public static void init() {

        try {
            writer = new FileWriter(FILE_PATH, false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getWords() throws IOException, IllegalStringLineExeption {

        /*правильное заполенение массива словами из текста*/
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("один");
        testArr.add("два");
        testArr.add("три");
        testArr.add("четыре-пять");
        testArr.add("шесть");
        testArr.add("семь");
        testArr.add("восемь");
        testArr.add("девять");
        testArr.add("ноль");

        newFile("один, два три четыре-пять, шесть семь восемь девять ноль");
        ptf = new ParsedTextFile(FILE_PATH);
        assertArrayEquals(testArr.toArray(), ptf.getWords().toArray());

        /*эксепшн при наличии не кириллических символов*/
        newFile("один, два три four-five, шесть семь восемь девять ноль");
        assertThrows(IllegalStringLineExeption.class, () -> new ParsedTextFile(FILE_PATH));

        /*эксепшн при неправильном пути к файлу*/
        String wrongFilePath = "wrongFilePath";
        assertThrows(IOException.class, () -> new ParsedTextFile(wrongFilePath));

    }



    @Test
    void getFilePath() {

        try {
            newFile("тест");
            ptf = new ParsedTextFile(FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStringLineExeption illegalStringLineExeption) {
            illegalStringLineExeption.printStackTrace();
        }

        assertTrue(FILE_PATH.equals(ptf.getFilePath()));

    }

    @Test
    void privateMethodsTest() {

        try {
            newFile("");
            ptf = new ParsedTextFile(FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStringLineExeption illegalStringLineExeption) {
            illegalStringLineExeption.printStackTrace();
        }

        try {

            Class[] paramTypes = new Class[]{String.class};
            Method isValidValue = ptf.getClass().getDeclaredMethod("isValidValue", paramTypes);
            isValidValue.setAccessible(true);

            String testedString = "123456789"
                    +"абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
                    +"/_,.\\-—?!№%\":*();`";

            for (int i = 0; i < testedString.length(); i++) {
                Object[] params = {new String(String.valueOf(testedString.charAt(i)))};
                assertTrue((Boolean) isValidValue.invoke(ptf, params));
            }

            testedString = "abcdefghijklmnopqrstuvwxyz";

            for (int i = 0; i < testedString.length(); i++) {
                Object[] params = {new String(String.valueOf(testedString.charAt(i)))};
                assertFalse((Boolean) isValidValue.invoke(ptf, params));
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected static void newFile(String textInFile) {
        try {
            writer = new FileWriter(FILE_PATH, false);
            writer.write(textInFile);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}