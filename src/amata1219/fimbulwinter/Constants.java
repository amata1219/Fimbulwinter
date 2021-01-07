package amata1219.fimbulwinter;

import org.bukkit.NamespacedKey;

import java.util.function.BiConsumer;

public class Constants {

    private static final BiConsumer<?, ?> NO_OPERATION = (t, u) -> {};
    static NamespacedKey NAMESPACED_KEY_OF_EXECUTABLE_ITEM_ID;

    public static <T, U> BiConsumer<T, U> noOperation() {
        return (BiConsumer<T, U>) NO_OPERATION;
    }

    public static NamespacedKey namespaced_key_of_executable_item_id() {
        return NAMESPACED_KEY_OF_EXECUTABLE_ITEM_ID;
    }

}
