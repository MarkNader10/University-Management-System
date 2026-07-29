package college_main_page;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class Student extends Frame {
       College_Main_Page mainPage;
     Connection con = DBConnection.getConnection();
    TextField tfStudentID, tfFName, tfLName;
    Button insertButton, updateButton, deleteButton, searchButton, backButton, exitBtn;
    Label titleLabel, studentIDLabel, fnameLabel, lnameLabel;
    public Student(College_Main_Page mainPage) {
        this.mainPage = mainPage;
        setTitle("Student Management");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = 800;
        int frameHeight = 600;
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        setBounds(x, y, frameWidth, frameHeight);
        setLayout(null);
        setBackground(Color.DARK_GRAY);
        // Labels
        titleLabel = new Label("Student");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(300, 50, 400, 30);
        add(titleLabel);
        studentIDLabel = new Label("Student ID:");
        studentIDLabel.setForeground(Color.WHITE);
        studentIDLabel.setBounds(100, 120, 100, 30);
        add(studentIDLabel);
        fnameLabel = new Label("First Name:");
        fnameLabel.setForeground(Color.WHITE);
        fnameLabel.setBounds(100, 170, 100, 30);
        add(fnameLabel);
        lnameLabel = new Label("Last Name:");
        lnameLabel.setForeground(Color.WHITE);
        lnameLabel.setBounds(100, 220, 100, 30);
        add(lnameLabel);
        // TextFields
        tfStudentID = new TextField();
        tfStudentID.setBounds(220, 120, 200, 30);
        add(tfStudentID);
        tfFName = new TextField();
        tfFName.setBounds(220, 170, 200, 30);
        add(tfFName);
        tfLName = new TextField();
        tfLName.setBounds(220, 220, 200, 30);
        add(tfLName);
        // Buttons
        insertButton = new Button("Insert");
        insertButton.setBounds(500, 120, 100, 40);
        insertButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(insertButton);
        updateButton = new Button("Update");
        updateButton.setBounds(500, 180, 100, 40);
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(updateButton);
        deleteButton = new Button("Delete");
        deleteButton.setBounds(500, 240, 100, 40);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(deleteButton);
        searchButton = new Button("Search");
        searchButton.setBounds(500, 300, 100, 40);
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(searchButton);
        backButton = new Button("Back");
        backButton.setBounds(20, 530, 100, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(backButton);
        exitBtn = new Button("Exit");
        exitBtn.setBounds(680, 520, 100, 40);
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        add(exitBtn);
        // Event Listeners
        exitBtn.addActionListener(e -> {
            System.exit(0);
            dispose();
        });
        insertButton.addActionListener(e -> insertStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        searchButton.addActionListener(e -> searchStudent());
        backButton.addActionListener(e -> {
            mainPage.setVisible(true);
            dispose();
        });
        // Window Closing Event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }
private void insertStudent() {
    try {
        int studentID = Integer.parseInt(tfStudentID.getText());
        String fname = tfFName.getText();
        String lname = tfLName.getText();
        String query = "INSERT INTO Student (Student_ID, FName, LName) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, studentID);
        pst.setString(2, fname);
        pst.setString(3, lname);
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            showMessage("Student inserted successfully!");
            clearFields();
        } else {
            showMessage("Insertion failed.");
        }
    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        showMessage("Invalid input or database error.");
    }
}
private void updateStudent() {
    try {
        int studentID = Integer.parseInt(tfStudentID.getText());
        String fname = tfFName.getText();
        String lname = tfLName.getText();
        String query = "UPDATE Student SET FName = ?, LName = ? WHERE Student_ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, fname);
        pst.setString(2, lname);
        pst.setInt(3, studentID);
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            showMessage("Student updated successfully!");
            clearFields();
        } else {
            showMessage("Update failed.");
        }
    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        showMessage("Invalid input or database error.");
    }
}
private void deleteStudent() {
    try {
        int studentID = Integer.parseInt(tfStudentID.getText());
        String query = "DELETE FROM Student WHERE Student_ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, studentID);
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            showMessage("Student deleted successfully!");
            clearFields();
        } else {
            showMessage("Deletion failed.");
        }
    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        showMessage("Invalid input or database error.");
    }
}
private void searchStudent() {
    try {
        int studentID = Integer.parseInt(tfStudentID.getText());
        String query = "SELECT * FROM Student WHERE Student_ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, studentID);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            tfFName.setText(rs.getString("FName"));
            tfLName.setText(rs.getString("LName"));
            showMessage("Student found!");
        } else {
            showMessage("Student not found.");
        }
    } catch (SQLException | NumberFormatException e) {
        e.printStackTrace();
        showMessage("Error during search.");
    }
}
private void clearFields() {
    tfStudentID.setText("");
    tfFName.setText("");
    tfLName.setText("");
}
private void showMessage(String message) {
    Dialog d = new Dialog(this, "Message", true);
    d.setLayout(new FlowLayout());
    Label l = new Label(message);
    l.setFont(new Font("Arial", Font.PLAIN, 14));
    d.add(l);
    Button b = new Button("OK");
    b.setFont(new Font("Arial", Font.BOLD, 14));
    b.addActionListener(e -> d.setVisible(false));
    d.add(b);
    d.setSize(300, 150);
    d.setLocationRelativeTo(this);
    d.setVisible(true);
}
}