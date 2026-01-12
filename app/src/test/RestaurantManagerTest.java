import main.App;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;

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

    // static String input = """
    // 1
    // 01/01/26 5:00PM Abby Johnson 6

    // 1
    // 01/01/26 5:00PM Abby Johnson 6
    // 1
    // 01/01/26 5:00PM Shawn Dieter 6

    // 1
    // 01/01/26 5:00PM Abby Johnson 6
    // 1
    // 01/01/26 6:00PM Abby Johnson 6

    // 1
    // 01/01/26 5:00PM Abby Johnson 6
    // 1
    // 02/01/26 5:00PM Abby Johnson 6

    // 1
    // 01/01/26 5:00PM Abby Johnson 6
    // 1
    // 01/01/26 5:00PM Shawn Dieter 6
    // 1
    // 01/01/26 5:00PM James Lorenz 6
    // 1
    // 01/01/26 5:00PM David Johnson 6
    // 1
    // 01/01/26 5:00PM Chelsey Nuttall 6
    // 1
    // 01/01/26 5:00PM Walter Rohrbaugh 6
    // 1
    // 01/01/26 5:00PM Judith Coleman 6
    // 1
    // 01/01/26 5:00PM David Armour 6
    // 1
    // 01/01/26 5:00PM Catherine Netherton 6

    // 1
    // 01/01/26 5:00PM Catherine Netherton 49
    // 1
    // 01/01/26 5:00PM Abby Johnson 48

    // 1
    // 01/01/26 5:00PM Abby Johnson 12
    // 1
    // 01/01/26 5:30PM Catherine Netherton 1

    // 1
    // 01/01/26 5:00PM Abby Johnson 12
    // 2
    // 01/01/26 Abby Johnson

    // 1
    // 01/01/26 5:00PM Abby Johnson 12
    // 1
    // 02/01/26 5:00PM Abby Johnson 12
    // 2
    // 01/01/26 Abby Johnson

    // 1
    // 01/01/26 5:00PM Abby Johnson 12
    // 2
    // 01/01/26 Jeff Johnson

    // 3
    // double
    // triple

    // 3
    // double
    // triple
    // hamburger

    // """;

    // @BeforeAll
    // public static void setup() {
    // System.setIn(new ByteArrayInputStream(input.getBytes()));
    // }

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

    @Test
    public void test01() {
        clear_file(reservationsFile);

        String input = """
                1
                01/01/26 5:00PM Abby Johnson 6
                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(reservationsFile).hasSameTextualContentAs(test1File);
    }

    @Test
    public void test02() {
        clear_file(reservationsFile);

        String input = """
                1
                01/01/26 5:00PM Abby Johnson 6
                1
                01/01/26 5:00PM Shawn Dieter 6
                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(reservationsFile).hasSameTextualContentAs(test2File);
    }

    @Test
    public void test03() {
        clear_file(reservationsFile);

        String input = """
                1
                01/01/26 5:00PM Abby Johnson 6
                1
                01/01/26 6:00PM Abby Johnson 6
                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(reservationsFile).hasSameTextualContentAs(test1File);
    }

    @Test
    public void test04() {
        clear_file(reservationsFile);

        String input = """
                1
                01/01/26 5:00PM Abby Johnson 6
                1
                02/01/26 5:00PM Abby Johnson 6
                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(reservationsFile).hasSameTextualContentAs(test3File);
    }

    @Test
    public void test05() {
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
                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(reservationsFile).hasSameTextualContentAs(test4File);
    }

    @Test
    public void test06() {
        clear_file(reservationsFile);

        String input = """
                1
                01/01/26 5:00PM Catherine Netherton 49
                1
                01/01/26 5:00PM Abby Johnson 48
                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(reservationsFile).hasSameTextualContentAs(test5File);
    }

    @Test
    public void test07() {
        clear_file(reservationsFile);

        String input = """
                1
                01/01/26 5:00PM Abby Johnson 12
                1
                01/01/26 5:30PM Catherine Netherton 1
                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(reservationsFile).hasSameTextualContentAs(test6File);
    }

    @Test
    public void test08() {
        clear_file(reservationsFile);

        String input = """
                1
                01/01/26 5:00PM Abby Johnson 12
                2
                01/01/26 Abby Johnson
                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(reservationsFile).hasSameTextualContentAs(test7File);
    }

    @Test
    public void test09() {
        clear_file(reservationsFile);

        String input = """
                1
                01/01/26 5:00PM Abby Johnson 12
                1
                02/01/26 5:00PM Abby Johnson 12
                2
                01/01/26 Abby Johnson
                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(reservationsFile).hasSameTextualContentAs(test8File);
    }

    @Test
    public void test10() {
        clear_file(reservationsFile);

        String input = """
                1
                01/01/26 5:00PM Abby Johnson 12
                2
                01/01/26 Jeff Johnson
                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(reservationsFile).hasSameTextualContentAs(test9File);
    }

    @Test
    public void test11() {
        reset_inventory();
        reset_profit();

        String input = """
                3
                double
                triple

                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(inventoryFile).hasSameTextualContentAs(test10File);
        assertThat(profitFile).hasSameTextualContentAs(test11File);
    }

    @Test
    public void test12() {
        reset_inventory();
        reset_profit();

        String input = """
                3
                double
                triple
                hamburger

                exit
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        App.main(args);
        assertThat(inventoryFile).hasSameTextualContentAs(test10File);
        assertThat(profitFile).hasSameTextualContentAs(test11File);
    }
}
