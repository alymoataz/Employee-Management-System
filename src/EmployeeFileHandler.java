import java.io.*;
import java.util.ArrayList;

public class EmployeeFileHandler {
    private static final String FILE_NAME="employees.ser";
    public static void saveEmployees(ArrayList<Employee> e) throws IOException{
        FileOutputStream fileOut=new FileOutputStream(FILE_NAME);
        ObjectOutputStream objectOut=new ObjectOutputStream(fileOut);
        objectOut.writeObject(e);
        objectOut.close();
        fileOut.close();
    }
    public static ArrayList<Employee> loadEmployees() throws IOException,ClassNotFoundException{
        File file=new File(FILE_NAME);
        if(!file.exists()){
            return new ArrayList<Employee>();
        }
        FileInputStream fileIn=new FileInputStream(FILE_NAME);
        ObjectInputStream objectIn=new ObjectInputStream(fileIn);
        ArrayList<Employee>e=(ArrayList<Employee>) objectIn.readObject();
        objectIn.close();
        fileIn.close();
        return e;
    }

}
