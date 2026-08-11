import java.io.*;
import java.util.*;

public class ObjectPlus implements Serializable {
    private static Map<Class<? extends ObjectPlus>, List<ObjectPlus>> extent = new HashMap<>();
    public static final String EXTENT_NAME = "extent.txt";

    public void addToExtent() {
        List<ObjectPlus> list = extent.computeIfAbsent(this.getClass(), v -> new ArrayList<>());
        list.add(this);
    }

    public void removeFromExtent() {
        List<ObjectPlus> list = extent.get(this.getClass());
        if (list != null) {
            list.remove(this);
        }
    }

    public static void saveExtent() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXTENT_NAME))) {
            oos.writeObject(extent);
            oos.writeInt(Game.getMaximum_ram());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadExtent() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXTENT_NAME))) {
            extent = (Map<Class<? extends ObjectPlus>, List<ObjectPlus>>) ois.readObject();
            Game.setMaximum_ram(ois.readInt());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends ObjectPlus> List<T> getFromExtent(Class<T> clazz) {
        extent.computeIfAbsent(clazz, v -> new ArrayList<>());
        return (List<T>) Collections.unmodifiableList(extent.get(clazz));
    }
}