package amata1219.fimbulwinter;

import amata1219.fimbulwinter.dsl.ExecutableItem;

import java.util.HashMap;

public class ExecutableItemRegistry {

    private final HashMap<String, ExecutableItem> state = new HashMap<>();

    public void register(ExecutableItem item) {
        if (state.containsKey(item.id)) throw new IllegalArgumentException(item.id + " has already been registered.");
        state.put(item.id, item);
    }

    public void unregister(ExecutableItem item) {
        state.remove(item.id);
    }

    public ExecutableItem executableItem(String executableItemId) {
        return state.get(executableItemId);
    }

}
