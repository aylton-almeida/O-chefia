import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosRestaurante implements DAOInterface {
    private List<ModelObject> list;
    private BufferedReader reader;
    private PrintWriter writer;
    private String line;
    private ObjectMapper mapper;

    @Override
    public List<ModelObject> getAllObjects() throws Exception {
        list = new ArrayList<>();
        mapper = new ObjectMapper();
        reader = new BufferedReader(new FileReader("arquivos/usuariosRestaurantes"));
        while ((line = reader.readLine()) != null) {
            list.add(mapper.readValue(line, UsuarioRestaurante.class));
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
            if (((UsuarioRestaurante) l).getEmail().equals(key))
                return l;
        return null;
    }

    @Override
    public void addObject(ModelObject o) throws Exception {
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuariosRestaurantes", true)));
        writer.println(((UsuarioRestaurante)o).toJson());
        if (writer != null)
            writer.close();
    }

    @Override
    public void updateObject(ModelObject o) throws Exception {
        List<ModelObject> list = this.getAllObjects();
        for (int i = 0; i < list.size(); i++) {
            if (((UsuarioRestaurante) list.get(i)).getEmail().equals(((UsuarioRestaurante) o).getEmail())) {
                list.remove(i);
                list.add(o);
            }
        }
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuariosRestaurantes")));
        for (ModelObject obj : list)
            writer.println(((UsuarioRestaurante) obj).toJson());
        if (writer != null)
            writer.close();
    }

    @Override
    public void deleteObject(ModelObject o) throws Exception {
        list = this.getAllObjects();
        for (int i = 0; i < list.size(); i++) {
            if (((UsuarioRestaurante) list.get(i)).getEmail().equals(((UsuarioRestaurante) o).getEmail()))
                list.remove(i);
        }
        writer = new PrintWriter(new BufferedWriter(new FileWriter("arquivos/usuariosRestaurantes")));
        for (ModelObject obj : list)
            writer.println(((UsuarioRestaurante) obj).toJson());
        if (writer != null)
            writer.close();
    }
}
