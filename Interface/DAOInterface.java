import java.util.ArrayList;
import java.util.List;

public interface DAOInterface {
    default List<ModelObject> getAllObjects() throws Exception {
        return new ArrayList<>();
    }

    default ModelObject getObject(Object key) throws Exception {
        return new Restaurante();
    }

    default void addObject(ModelObject o) throws Exception {
    }

    default void updateObject(ModelObject o) throws Exception {
    }

    default void deleteObject(ModelObject o) throws Exception {
    }
}
