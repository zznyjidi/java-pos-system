package tests;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantManagerTest {
    static File reservationsFile = new File("src/reservations.txt");
    static File inventoryFile = new File("src/inventory.txt");
    static File profitFile = new File("src/profit.txt");
    static File test1File = new File("src/test1.txt");
    static File test2File = new File("src/test2.txt");
    static File test3File = new File("src/test3.txt");
    static File test4File = new File("src/test4.txt");
    static File test5File = new File("src/test5.txt");
    static File test6File = new File("src/test6.txt");

    public static void clear_file(File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("");
        } catch (Exception e) {
            IO.println(e);
        }
    }

    public static long compare_files(File file1, File file2) {
        long compare = 0;
        try {
            compare = Files.mismatch(file1.toPath(), file2.toPath());
        } catch (Exception e) {
            IO.println(e);
        }
        return compare;
    }

    @Test
    public void test1() {
        clear_file(reservationsFile);
        String input = """
                1
                01/01/26 5:00PM Abby Johnson 6

                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        RestaurantManager.main();
        assertEquals(-1, compare_files(reservationsFile, test1File));
    }

    @Test
    public void test2() {
        clear_file(reservationsFile);
        String input = """
                1
                01/01/26 5:00PM Abby Johnson 6
                1
                01/01/26 5:00PM Shawn Dieter 6

                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        RestaurantManager.main();
        assertEquals(-1, compare_files(reservationsFile, test2File));
    }

    @Test
    public void test3() {
        clear_file(reservationsFile);
        String input = """
                1
                01/01/26 5:00PM Abby Johnson 6
                1
                01/01/26 6:00PM Abby Johnson 6

                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        RestaurantManager.main();
        assertEquals(-1, compare_files(reservationsFile, test1File));
    }

    @Test
    public void test4() {
        clear_file(reservationsFile);
        String input = """
                1
                01/01/26 5:00PM Abby Johnson 6
                1
                02/01/26 5:00PM Abby Johnson 6

                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        RestaurantManager.main();
        assertEquals(-1, compare_files(reservationsFile, test3File));
    }

    @Test
    public void test5() {
        clear_file(reservationsFile);
        String input = """
                1
                01/01/26 5:00PM Abby Johnson 6
                1
                01/01/26 5:00PM Shawn Dieter 6
                1
                01/01/26 5:00PM James Lorenz 6
                1
                01/01/26 5:00PM David Johnson 6
                1
                01/01/26 5:00PM Chelsey Nuttall 6
                1
                01/01/26 5:00PM Walter Rohrbaugh 6
                1
                01/01/26 5:00PM Judith Coleman 6
                1
                01/01/26 5:00PM David Armour 6
                1
                01/01/26 5:00PM Catherine Netherton 6

                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        RestaurantManager.main();
        assertEquals(-1, compare_files(reservationsFile, test4File));
    }

    @Test
    public void test6() {
        clear_file(reservationsFile);
        String input = """
                1
                01/01/26 5:00PM Abby Johnson 6
                1
                01/01/26 5:00PM Shawn Dieter 6
                1
                01/01/26 5:00PM James Lorenz 6
                1
                01/01/26 5:00PM David Johnson 6
                1
                01/01/26 5:00PM Chelsey Nuttall 6
                1
                01/01/26 5:00PM Walter Rohrbaugh 6
                1
                01/01/26 5:00PM Judith Coleman 6
                1
                01/01/26 5:00PM David Armour 6
                1
                01/01/26 5:01PM Catherine Netherton 6

                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        RestaurantManager.main();
        assertEquals(-1, compare_files(reservationsFile, test4File));
    }

    @Test
    public void test7() {
        clear_file(reservationsFile);
        String input = """
                1
                01/01/26 5:00PM Catherine Netherton 49
                1
                01/01/26 5:00PM Abby Johnson 48

                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        RestaurantManager.main();
        assertEquals(-1, compare_files(reservationsFile, test5File));
    }

    @Test
    public void test8() {
        clear_file(reservationsFile);
        String input = """
                1
                01/01/26 5:00PM Abby Johnson 12
                1
                01/01/26 5:30PM Catherine Netherton 1

                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        RestaurantManager.main();
        assertEquals(-1, compare_files(reservationsFile, test6File));
    }
}