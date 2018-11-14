import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Restaurantes implements DAOInterface {

    private List<ModelObject> list;
    private BufferedReader reader;
    private PrintWriter writer;
    private String line;
    private ObjectMapper mapper;

    @Override
    public List<ModelObject> getAllObjects() throws Exception {
        list = new ArrayList<>();
        mapper = new ObjectMapper();
        reader = new BufferedReader(new FileReader("arquivos/restaurantes"));
        while ((line = reader.readLine()) != null) {
            list.add(mapper.readValue(line, Restaurante.class));
        }
        if (reader != null) {
            reader.close();
        }
        return list;
    }

    @Override
    public ModelObject getObject(Object key) throws Exception {
        list = this.getAllObjects();
        for (ModelObject l : list)
            if (((Restaurante) l).getCnpj().equals(key))
                return l;
        return null;
    }

    @Override
    public void addObject(ModelObject o) throws Exception {
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/restaurantes", true)));
        writer.println(((Restaurante) o).toJson());
        if (writer != null)
            writer.close();
    }

    @Override
    public void updateObject(ModelObject o) throws Exception {
        list = this.getAllObjects();
        for (int i = 0; i < list.size(); i++) {
            if (((Restaurante) list.get(i)).getCnpj().equals(((Restaurante) o).getCnpj())) {
                list.remove(i);
                list.add(o);
            }
        }
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/restaurantes")));
        for (ModelObject obj : list)
            writer.println(((Restaurante) obj).toJson());
        if (writer != null)
            writer.close();
    }

    @Override
    public void deleteObject(ModelObject o) throws Exception {
        list = this.getAllObjects();
        for (int i = 0; i < list.size(); i++) {
            if (((Restaurante) list.get(i)).getCnpj().equals(((Restaurante) o).getCnpj()))
                list.remove(i);
        }
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/restaurantes")));
        for (ModelObject obj : list)
            writer.println(((Restaurante) obj).toJson());
        if (writer != null)
            writer.close();
    }
}
