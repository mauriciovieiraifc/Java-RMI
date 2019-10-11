package utils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author mauricio
 */
public interface Stub extends Remote {
    
    public ArrayList<PhoneBook> getPhoneBook() throws RemoteException;
    
    public void addEntry(PhoneBook entry) throws RemoteException;
    
}
