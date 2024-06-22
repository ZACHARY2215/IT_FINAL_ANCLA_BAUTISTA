import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MainProgram extends MyFrame implements ActionListener, MouseListener, KeyListener, WindowListener {
    private JLabel lblSearch;
    private JTextField txtSearch;
    private JTable tbl_Employee, tbl_Department, tbl_Position;
    private DefaultTableModel model_employee, model_department, model_position;
    private Vector<String> columns, rowData;
    private TableRowSorter<DefaultTableModel> tbl_sort;
    private JLabel lblID, lblName, lblGender, lblDepartment, lblPosition, lblSalary;
    private JTextField txtID, txtName, txtSalary;
    private JComboBox<String> cboGender, cboDepartment, cboPosition;
    private Font f = new Font("Arial", Font.BOLD, 16);
    private JButton btnAdd, btnClear, btnUpdate, btnDelete, btnClose;
    private Database db;
    private JPanel panelEmployeeInfo, panelButtons, panelSearch, panelTable, panelRight, panelListButtons;
    private boolean hasEmployees = false; // Flag to check if there are employees

    public MainProgram() {
        initializeComponents();
        employeeInfo();
        add(panelEmployeeInfo).setBounds(10, 10, 300, 200);
        employeeButtons();
        add(panelButtons).setBounds(10, 220, 300, 50); // Adjusted position and width
        add(panelEmployeeSearch()).setBounds(320, 10, 300, 30);
        add(panelEmployeeTable()).setBounds(320, 50, 543, 290);
        listButtons();
        add(panelListButtons).setBounds(320, 350, 543, 50); // Added new panel for Update, Delete, and Close buttons
        add(panelRight).setBounds(700, 10, 300, 330); // Adjusted to fit the department and position tables
        // add(setBackgroundImage("IMAGES/bg.jpeg"));
        setMyFrame("One Touch Employee Network", 900, 450, true); // Adjusted frame size
        setLocationRelativeTo(null);
        txtID.setText(getRowCount());
        btnAdd.addActionListener(this);
        btnClear.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClose.addActionListener(this);
        cboDepartment.addActionListener(this);
        cboPosition.addActionListener(this);
        tbl_Employee.addMouseListener(this);
        txtSearch.addKeyListener(this);
        db = new Database("Employee.txt");
        db.displayRecords(model_employee);
        hasEmployees = model_employee.getRowCount() > 0; // Check if there are employees
        toggleDepartmentPositionTables();
        resetComponents();
    }

    public void initializeComponents() {
        lblID = new JLabel("ID: ");
        lblName = new JLabel("Name: ");
        lblGender = new JLabel("Gender:");
        lblDepartment = new JLabel("Department:");
        lblPosition = new JLabel("Position:");
        lblSalary = new JLabel("Salary:");
        txtID = new JTextField(20);
        txtName = new JTextField(20);
        txtSalary = new JTextField(20);
        cboGender = new JComboBox<>();
        cboDepartment = new JComboBox<>();
        cboPosition = new JComboBox<>();
        loadToComboBox();
        btnAdd = new JButton("Add New", new ImageIcon("IMAGES/icon/add_user.png"));
        btnClear = new JButton("Clear", new ImageIcon("IMAGES/icon/clear.png"));
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClose = new JButton("Close");

        // Initialize the panelRight which contains department and position tables
        panelRight = new JPanel(new GridLayout(2, 1, 10, 10));
        panelRight.setOpaque(false);
    }

    public void employeeInfo() {
        panelEmployeeInfo = new JPanel();
        panelEmployeeInfo.setBorder(BorderFactory.createTitledBorder("Employee Information"));
        panelEmployeeInfo.setLayout(new GridLayout(6, 2));
        panelEmployeeInfo.setFont(f);
        panelEmployeeInfo.setOpaque(false);
        panelEmployeeInfo.add(lblID);
        panelEmployeeInfo.add(txtID);
        panelEmployeeInfo.add(lblName);
        panelEmployeeInfo.add(txtName);
        panelEmployeeInfo.add(lblGender);
        panelEmployeeInfo.add(cboGender);
        panelEmployeeInfo.add(lblDepartment);
        panelEmployeeInfo.add(cboDepartment);
        panelEmployeeInfo.add(lblPosition);
        panelEmployeeInfo.add(cboPosition);
        panelEmployeeInfo.add(lblSalary);
        panelEmployeeInfo.add(txtSalary);
    }

    public void loadToComboBox() {
        cboGender.addItem("Male");
        cboGender.addItem("Female");
        db = new Database("Department.txt");
        db.fillToComboBox(cboDepartment);
        db = new Database("Position.txt");
        db.fillToComboBox(cboPosition);
    }

    public void employeeButtons() {
        panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 3, 4, 2)); // Adjusted layout
        panelButtons.add(btnAdd);
        panelButtons.add(btnClear);
        panelButtons.add(new JLabel("")); // Placeholder
    }

    public JPanel panelEmployeeSearch() {
        panelSearch = new JPanel();
        lblSearch = new JLabel("Search");
        txtSearch = new JTextField(10);
        panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 1));
        panelSearch.add(lblSearch);
        panelSearch.add(txtSearch);
        panelSearch.setOpaque(false);
        return panelSearch;
    }

    public JPanel panelEmployeeTable() {
        panelTable = new JPanel(new BorderLayout());

        // Employee Table
        tbl_Employee = new JTable();
        model_employee = new DefaultTableModel();
        String[] cols = { "ID", "Name", "Gender", "Department", "Position", "Salary" };
        columns = new Vector<>();
        for (String val : cols) {
            columns.add(val);
        }
        model_employee.setColumnIdentifiers(columns);
        tbl_Employee.setModel(model_employee);
        tbl_Employee.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Adjust column widths to fit headers
        for (int i = 0; i < tbl_Employee.getColumnCount(); i++) {
            TableColumn column = tbl_Employee.getColumnModel().getColumn(i);
            column.setPreferredWidth(column.getHeaderValue().toString().length() * 15);
        }

        JScrollPane scrollEmployee = new JScrollPane(tbl_Employee);

        panelTable.add(scrollEmployee, BorderLayout.CENTER);

        return panelTable;
    }

    public void listButtons() {
        panelListButtons = new JPanel();
        panelListButtons.setLayout(new GridLayout(1, 3, 4, 2)); // Adjusted layout
        panelListButtons.add(btnUpdate);
        panelListButtons.add(btnDelete);
        panelListButtons.add(btnClose);
    }

    public String getRowCount() {
        return String.valueOf(model_employee.getRowCount());
    }

    public void getData() {
        rowData = new Vector<>();
        rowData.add(txtID.getText());
        rowData.add(txtName.getText().toUpperCase());
        rowData.add(cboGender.getSelectedItem().toString());
        rowData.add(cboDepartment.getSelectedItem().toString());
        rowData.add(cboPosition.getSelectedItem().toString());
        rowData.add(txtSalary.getText());
    }

    public void resetComponents() {
        txtID.setText(getRowCount());
        btnAdd.setEnabled(true);
        txtName.setText("");
        txtSalary.setText("");
        cboGender.setSelectedIndex(0);
        cboDepartment.setSelectedIndex(0);
        cboPosition.setSelectedIndex(0);
        txtName.requestFocus();
    }

    public void updateRecords() {
        Vector<String> updatedData = new Vector<>();
        for (int i = 0; i < model_employee.getRowCount(); i++) {
            Vector<String> row = new Vector<>();
            for (int j = 0; j < model_employee.getColumnCount(); j++) {
                row.add(model_employee.getValueAt(i, j).toString());
            }
            updatedData.add(String.join("#", row));
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("Employee.txt"))) {
            for (String record : updatedData) {
                pw.println(record);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        db = new Database("Employee.txt");
        if (e.getSource() == btnAdd) {
            if (areFieldsEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isNumeric(txtSalary.getText())) {
                JOptionPane.showMessageDialog(this, "Salary must be a numeric value.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else if (isNumeric(txtName.getText())) {
                JOptionPane.showMessageDialog(this, "Name must be a string.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            getData();
            db.storeToFile(String.join("#", rowData));
            model_employee.addRow(rowData);
            updateRecords();
            resetComponents();
            hasEmployees = true;
        } else if (e.getSource() == btnClear) {
            resetComponents();
        } else if (e.getSource() == btnUpdate) {
            int selectedRow = tbl_Employee.getSelectedRow();
            if (selectedRow != -1) {
                if (areFieldsEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!isNumeric(txtSalary.getText())) {
                    JOptionPane.showMessageDialog(this, "Salary must be a numeric value.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (isNumeric(txtName.getText())) {
                    JOptionPane.showMessageDialog(this, "Name must be a string.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                getData();
                for (int i = 0; i < model_employee.getColumnCount(); i++) {
                    model_employee.setValueAt(rowData.get(i), selectedRow, i);
                }
                updateRecords();
                resetComponents();
            }
        } else if (e.getSource() == btnDelete) {
            int selectedRow = tbl_Employee.getSelectedRow();
            if (selectedRow != -1) {
                model_employee.removeRow(selectedRow);
                updateRecords();
                resetComponents();
                hasEmployees = model_employee.getRowCount() > 0;
            }
        } else if (e.getSource() == btnClose) {
            System.exit(0);
        }
        toggleDepartmentPositionTables(); // Update table visibility after actions
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == txtSearch) {
            String text = txtSearch.getText().toUpperCase();
            tbl_sort = new TableRowSorter<>(model_employee);
            tbl_Employee.setRowSorter(tbl_sort);
            tbl_sort.setRowFilter(RowFilter.regexFilter(text));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = tbl_Employee.getSelectedRow();
        if (selectedRow != -1) {
            txtID.setText(model_employee.getValueAt(selectedRow, 0).toString());
            txtName.setText(model_employee.getValueAt(selectedRow, 1).toString());
            cboGender.setSelectedItem(model_employee.getValueAt(selectedRow, 2).toString());
            cboDepartment.setSelectedItem(model_employee.getValueAt(selectedRow, 3).toString());
            cboPosition.setSelectedItem(model_employee.getValueAt(selectedRow, 4).toString());
            txtSalary.setText(model_employee.getValueAt(selectedRow, 5).toString());
            btnAdd.setEnabled(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    private void toggleDepartmentPositionTables() {
        panelRight.setVisible(hasEmployees);
        panelRight.revalidate();
        panelRight.repaint();
    }

    private boolean areFieldsEmpty() {
        return txtName.getText().isEmpty() || txtSalary.getText().isEmpty();
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainProgram().setVisible(true));
    }
}
