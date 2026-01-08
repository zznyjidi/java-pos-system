package menu;

public class NodeParser {
    public static void runNodeOnce(MenuNode node) {
        String input = IO.readln(node.prompt);
        if (node.actions.containsKey(input))
            runNodeOnce(node.actions.get(input));
        else
            node.action.run(input);
    }

    public static void runNode(MenuNode node) {
        while (true) {
            runNodeOnce(node);
        }
    }
}
