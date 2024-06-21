import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Database {
    private String fileName;

    public Database(String fileName) {
        this.fileName = fileName;
    }

    public void fillToComboBox(JComboBox<String> cbo) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                cbo.addItem(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void storeToFile(String records) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println(records);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void displayRecords(DefaultTableModel model) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("#");
                Vector<String> rowData = new Vector<>();
                Collections.addAll(rowData, data);
                model.addRow(rowData);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
