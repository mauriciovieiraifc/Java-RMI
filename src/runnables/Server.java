package runnables;

import utils.PhoneBook;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Stub;

/**
 *
 * @author mauricio
 */
public class Server extends UnicastRemoteObject implements Stub {

    FileWriter writer = null;
    FileReader reader = null;
    String dir = "contacts.json";

    JSONParser parser = new JSONParser();
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();

    public Server() throws RemoteException {}

    /**
     * Method responsible for reading the file containing the saved contacts
     * @return
     */    
    @Override
    public ArrayList<PhoneBook> getPhoneBook() {
        try {
            reader = new FileReader(dir);
            jsonArray = (JSONArray) parser.parse(reader);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jsonArray;
    }

    /**
     * Method responsible for saving the added contact to a json file
     * @param entry 
     */
    @Override
    public void addEntry(PhoneBook entry) {
        jsonObject.put("Name", entry.getName());
        jsonObject.put("LastName", entry.getLastName());
        jsonObject.put("Number", entry.getNumber());

        jsonArray.add(jsonObject);

        try {
            writer = new FileWriter(dir);
            writer.write(jsonArray.toJSONString());

            writer.flush();
            writer.close();

            System.out.println("Write complete " + jsonArray.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();

            /**
             * Registers the stub so that it can be obtained by clients
             */
            Registry registry = LocateRegistry.createRegistry(5099);
            registry.rebind("Phones", server);

            System.out.println("Server ready");

        } catch (RemoteException ex) {
            System.err.println(ex);
        }
    }
}
