import java.io.*;
import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

public class BLOBmanagement {
    public static byte[] AccountsToBinaryArray(ArrayList<Account> accounts){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos;

        try {
            oos = new ObjectOutputStream(outputStream);
            oos.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        byte[] bytes = outputStream.toByteArray();
        return bytes;
    }

    public static ArrayList<Account> BinaryArrayToAccounts(byte[] bytes){

        ArrayList<Account>accounts;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream ois;

        try {
           ois = new ObjectInputStream(inputStream);
           accounts = (ArrayList<Account>) ois.readObject();

           return accounts;
        }
        catch (Exception e){
            return null;
        }
    }
}
