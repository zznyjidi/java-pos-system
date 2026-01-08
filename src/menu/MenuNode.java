package menu;

import java.util.Map;

public class MenuNode implements RunnableNode {
    String prompt;
    Map<String, RunnableNode> actions;
    MenuAction action;

    public interface MenuAction {
        void run(String input);
    }

    public MenuNode(String prompt, Map<String, RunnableNode> actions) {
        this.prompt = prompt;
        this.actions = actions;
        this.action = (input) -> {
        };
    }

    public MenuNode(String prompt, MenuAction action) {
        this.prompt = prompt;
        this.actions = Map.of();
        this.action = action;
    }

    public MenuNode(String prompt, Map<String, RunnableNode> actions, MenuAction action) {
        this.prompt = prompt;
        this.actions = actions;
        this.action = action;
    }

    @Override
    public void runNode() {
        String input = IO.readln(prompt);
        if (actions.containsKey(input))
            actions.get(input).runNode();
        else
            action.run(input);
    }
}
