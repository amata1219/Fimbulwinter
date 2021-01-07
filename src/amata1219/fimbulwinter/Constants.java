package amata1219.fimbulwinter;

import org.bukkit.NamespacedKey;

import java.util.function.Consumer;

public class Constants {

    private static final Consumer<?> NO_OPERATION = it -> {};
    static NamespacedKey NAMESPACED_KEY_OF_EXECUTABLE_ITEM_ID;

    public static <T> Consumer<T> noOperation() {
        return (Consumer<T>) NO_OPERATION;
    }

    public static NamespacedKey namespaced_key_of_executable_item_id() {
        return NAMESPACED_KEY_OF_EXECUTABLE_ITEM_ID;
    }

}
