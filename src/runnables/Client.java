package runnables;

import utils.PhoneBook;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import utils.Stub;

/**
 *
 * @author mauricio
 */
public class Client {

    public static void main(String[] args) throws MalformedURLException {
        try {
            /**
             * Get server stub
             */
            Stub stub = (Stub) Naming.lookup("rmi://localhost:5099/Phones");

            PhoneBook contact = new PhoneBook();
            ArrayList<PhoneBook> msg;

            Scanner input = new Scanner(System.in);
            int option;

            do {
                System.out.print("ADD (1) / List (2) / Exit ");
                option = input.nextInt();

                if (option == 1) {
                    System.out.print("Name: ");
                    contact.setName(new Scanner(System.in).nextLine());

                    System.out.print("Last Name: ");
                    contact.setLastName(new Scanner(System.in).nextLine());

                    System.out.print("Phone: ");
                    contact.setNumber(new Scanner(System.in).nextInt());

                    stub.addEntry(contact);
                }

                if (option == 2) {
                    msg = stub.getPhoneBook();
                    System.out.println("List: " + msg);
                }
                
                System.out.println("");
            } while (option == 1 || option == 2);

        } catch (RemoteException | NotBoundException ex) {
            System.err.println(ex);
        }
    }
}
