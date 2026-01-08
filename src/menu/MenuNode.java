package menu;

import java.util.Map;

public class MenuNode {
    public interface MenuAction {
        void run(String input);
    }

    String prompt;
    Map<String, MenuNode> actions;
    MenuAction action;

    public MenuNode(String prompt, Map<String, MenuNode> actions) {
        this.prompt = prompt;
        this.actions = actions;
        this.action = (input) -> NodeParser.runNode(this);
    }

    public MenuNode(String prompt, MenuAction action) {
        this.prompt = prompt;
        this.actions = Map.of();
        this.action = action;
    }

    public MenuNode(String prompt, Map<String, MenuNode> actions, MenuAction action) {
        this.prompt = prompt;
        this.actions = actions;
        this.action = action;
    }
}
