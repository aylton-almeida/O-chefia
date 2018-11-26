import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Usuarios implements DAOInterface {

    private List<ModelObject> list;
    private BufferedReader reader;
    private PrintWriter writer;
    private String line;
    private ObjectMapper mapper;

    @Override
    public List<ModelObject> getAllObjects() throws Exception {
        list = new ArrayList<>();
        mapper = new ObjectMapper();
        reader = new BufferedReader(new FileReader("arquivos/usuarios"));
        while ((line = reader.readLine()) != null) {
            list.add(mapper.readValue(line, Usuario.class));
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public ModelObject getObject(Object key) throws Exception {
        list = this.getAllObjects();
        for (ModelObject l : list)
            if (((Usuario) l).getEmail().equals(key))
                return l;
        return null;
    }

    @Override
    public void addObject(ModelObject o) throws Exception {
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuarios", true)));
        writer.println(((Usuario) o).toJson());
        if (writer != null)
            writer.close();
    }

    @Override
    public void updateObject(ModelObject o) throws Exception {
        List<ModelObject> list = this.getAllObjects();
        for (int i = 0; i < list.size(); i++) {
            if (((Usuario) list.get(i)).getEmail().equals(((Usuario) o).getEmail())) {
                list.remove(i);
                list.add(o);
            }
        }
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuarios")));
        for (ModelObject obj : list)
            writer.println(((Usuario) obj).toJson());
        if (writer != null)
            writer.close();
    }

    @Override
    public void deleteObject(ModelObject o) throws Exception {
        list = this.getAllObjects();
        for (int i = 0; i < list.size(); i++) {
            if (((Usuario) list.get(i)).getEmail().equals(((Usuario) o).getEmail()))
                list.remove(i);
        }
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuarios")));
        for (ModelObject obj : list)
            writer.println(((Usuario) obj).toJson());
        if (writer != null)
            writer.close();
    }
}
