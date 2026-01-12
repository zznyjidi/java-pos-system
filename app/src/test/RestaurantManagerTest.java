package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.App;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantManagerTest {
    static File reservationsFile = new File("reservations.table.txt");
    static File inventoryFile = new File("inventory.table.txt");
    static File profitFile = new File("profit.table.txt");
    static File test1File = new File("testFiles/test1.txt");
    static File test2File = new File("testFiles/test2.txt");
    static File test3File = new File("testFiles/test3.txt");
    static File test4File = new File("testFiles/test4.txt");
    static File test5File = new File("testFiles/test5.txt");
    static File test6File = new File("testFiles/test6.txt");
    static File test7File = new File("testFiles/test7.txt");
    static File test8File = new File("testFiles/test8.txt");
    static File test9File = new File("testFiles/test9.txt");
    static File test10File = new File("testFiles/test10.txt");
    static File test11File = new File("testFiles/test11.txt");

    static String[] args = new String[0];

    static String input = """
            1
            01/01/26 5:00PM Abby Johnson 6

            1
            01/01/26 5:00PM Abby Johnson 6
            1
            01/01/26 5:00PM Shawn Dieter 6

            1
            01/01/26 5:00PM Abby Johnson 6
            1
            01/01/26 6:00PM Abby Johnson 6

            1
            01/01/26 5:00PM Abby Johnson 6
            1
            02/01/26 5:00PM Abby Johnson 6

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

            1
            01/01/26 5:00PM Catherine Netherton 49
            1
            01/01/26 5:00PM Abby Johnson 48

            1
            01/01/26 5:00PM Abby Johnson 12
            1
            01/01/26 5:30PM Catherine Netherton 1

            1
            01/01/26 5:00PM Abby Johnson 12
            2
            01/01/26 Abby Johnson

            1
            01/01/26 5:00PM Abby Johnson 12
            1
            02/01/26 5:00PM Abby Johnson 12
            2
            01/01/26 Abby Johnson

            1
            01/01/26 5:00PM Abby Johnson 12
            2
            01/01/26 Jeff Johnson

            3
            double
            triple


            3
            double
            triple
            hamburger


            """;

    @BeforeAll
    public static void setup() {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    public static void clear_file(File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("");
        } catch (Exception e) {
            IO.println(e);
        }
    }

    public static void reset_inventory() {
        try (FileWriter fileWriter = new FileWriter(inventoryFile)) {
            fileWriter.write("beef 5\n");
            fileWriter.write("lettuce 5\n");
            fileWriter.write("tomato 5\n");
            fileWriter.write("bun 5\n");
            fileWriter.write("cheese 5\n");
            fileWriter.write("bacon 5\n");
            fileWriter.write("bean 5\n");
            fileWriter.write("onion 5\n");
            fileWriter.write("fish 5\n");
            fileWriter.write("mayo 5\n");
            fileWriter.write("potato 5\n");
            fileWriter.write("gravy 5\n");
        } catch (Exception e) {
            IO.println(e);
        }
    }

    public static void reset_profit() {
        try (FileWriter fileWriter = new FileWriter(profitFile)) {
            fileWriter.write("0");
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
    public void test01() {
        clear_file(reservationsFile);
        /*
         * String input =
         * """
         * 1
         * 01/01/26 5:00PM Abby Johnson 6
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(reservationsFile, test1File));
    }

    @Test
    public void test02() {
        clear_file(reservationsFile);
        /*
         * String input =
         * """
         * 1
         * 01/01/26 5:00PM Abby Johnson 6
         * 1
         * 01/01/26 5:00PM Shawn Dieter 6
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(reservationsFile, test2File));
    }

    @Test
    public void test03() {
        clear_file(reservationsFile);
        /*
         * String input =
         * """
         * 1
         * 01/01/26 5:00PM Abby Johnson 6
         * 1
         * 01/01/26 6:00PM Abby Johnson 6
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(reservationsFile, test1File));
    }

    @Test
    public void test04() {
        clear_file(reservationsFile);
        /*
         * String input =
         * """
         * 1
         * 01/01/26 5:00PM Abby Johnson 6
         * 1
         * 02/01/26 5:00PM Abby Johnson 6
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(reservationsFile, test3File));
    }

    @Test
    public void test05() {
        clear_file(reservationsFile);
        /*
         * String input =
         * """
         * 1
         * 01/01/26 5:00PM Abby Johnson 6
         * 1
         * 01/01/26 5:00PM Shawn Dieter 6
         * 1
         * 01/01/26 5:00PM James Lorenz 6
         * 1
         * 01/01/26 5:00PM David Johnson 6
         * 1
         * 01/01/26 5:00PM Chelsey Nuttall 6
         * 1
         * 01/01/26 5:00PM Walter Rohrbaugh 6
         * 1
         * 01/01/26 5:00PM Judith Coleman 6
         * 1
         * 01/01/26 5:00PM David Armour 6
         * 1
         * 01/01/26 5:00PM Catherine Netherton 6
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(reservationsFile, test4File));
    }

    @Test
    public void test06() {
        clear_file(reservationsFile);
        /*
         * String input =
         * """
         * 1
         * 01/01/26 5:00PM Catherine Netherton 49
         * 1
         * 01/01/26 5:00PM Abby Johnson 48
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(reservationsFile, test5File));
    }

    @Test
    public void test07() {
        clear_file(reservationsFile);
        /*
         * String input =
         * """
         * 1
         * 01/01/26 5:00PM Abby Johnson 12
         * 1
         * 01/01/26 5:30PM Catherine Netherton 1
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(reservationsFile, test6File));
    }

    @Test
    public void test08() {
        clear_file(reservationsFile);
        /*
         * String input =
         * """
         * 1
         * 01/01/26 5:00PM Abby Johnson 12
         * 2
         * 01/01/26 Abby Johnson
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(reservationsFile, test7File));
    }

    @Test
    public void test09() {
        clear_file(reservationsFile);
        /*
         * String input =
         * """
         * 1
         * 01/01/26 5:00PM Abby Johnson 12
         * 1
         * 02/01/26 5:00PM Abby Johnson 12
         * 2
         * 01/01/26 Abby Johnson
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(reservationsFile, test8File));
    }

    @Test
    public void test10() {
        clear_file(reservationsFile);
        /*
         * String input =
         * """
         * 1
         * 01/01/26 5:00PM Abby Johnson 12
         * 2
         * 01/01/26 Jeff Johnson
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(reservationsFile, test9File));
    }

    @Test
    public void test11() {
        reset_inventory();
        reset_profit();
        /*
         * String input =
         * """
         * 3
         * double
         * triple
         * 
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(inventoryFile, test10File));
        assertEquals(-1, compare_files(profitFile, test11File));
    }

    @Test
    public void test12() {
        reset_inventory();
        reset_profit();
        /*
         * String input =
         * """
         * 3
         * double
         * triple
         * hamburger
         * 
         * 
         * """;
         * System.setIn(new ByteArrayInputStream(input.getBytes()));
         */
        App.main(args);
        assertEquals(-1, compare_files(inventoryFile, test10File));
        assertEquals(-1, compare_files(profitFile, test11File));
    }
}