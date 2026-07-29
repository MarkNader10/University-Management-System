package college_main_page;
import java.awt.*;
import java.awt.event.*;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
public class Course extends Frame {
College_Main_Page mainPage;
    Connection con = DBConnection.getConnection();
    TextField tfCID, tfCName, tfDuration, tfInstID, tfDepartmentID;
    Button insertButton, updateButton, deleteButton, searchButton, backButton, exitBtn;
    Label titleLabel, cidLabel, cnameLabel, durationLabel, deptIdLabel, instIDLabel;
    public Course(College_Main_Page mainPage) {
        this.mainPage = mainPage;
        setTitle("Course Management");
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
        titleLabel = new Label("Course");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(300, 50, 400, 30);
        add(titleLabel);
        cidLabel = new Label("Course ID:");
        cidLabel.setForeground(Color.WHITE);
        cidLabel.setBounds(100, 120, 100, 30);
        add(cidLabel);
        cnameLabel = new Label("Course Name:");
        cnameLabel.setForeground(Color.WHITE);
        cnameLabel.setBounds(100, 170, 100, 30);
        add(cnameLabel);
        durationLabel = new Label("Duration:");
        durationLabel.setForeground(Color.WHITE);
        durationLabel.setBounds(100, 220, 100, 30);
        add(durationLabel);
        instIDLabel = new Label("Instructor ID:");
        instIDLabel.setForeground(Color.WHITE);
        instIDLabel.setBounds(100, 270, 100, 30);
        add(instIDLabel);
        deptIdLabel = new Label("Department ID:");
        deptIdLabel.setForeground(Color.WHITE);
        deptIdLabel.setBounds(100, 320, 100, 30);
        add(deptIdLabel);
        // TextFields
        tfCID = new TextField();
        tfCID.setBounds(220, 120, 200, 30);
        add(tfCID);
        tfCName = new TextField();
        tfCName.setBounds(220, 170, 200, 30);
        add(tfCName);
        tfDuration = new TextField();
        tfDuration.setBounds(220, 220, 200, 30);
        add(tfDuration);
        tfInstID = new TextField();
        tfInstID.setBounds(220, 270, 200, 30);
        add(tfInstID);
        tfDepartmentID = new TextField();
        tfDepartmentID.setBounds(220, 320, 200, 30);
        add(tfDepartmentID);
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
        insertButton.addActionListener(e -> insertCourse());
        updateButton.addActionListener(e -> updateCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        searchButton.addActionListener(e -> searchCourse());
        backButton.addActionListener(e -> {
            mainPage.setVisible(true);
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }
    private void insertCourse() {
        try {
            int courseID = Integer.parseInt(tfCID.getText());
            String courseName = tfCName.getText();
            int duration = Integer.parseInt(tfDuration.getText());
            int instID = Integer.parseInt(tfInstID.getText());
            int deptID = Integer.parseInt(tfDepartmentID.getText());
            String query = "INSERT INTO Course (C_ID, C_Name, Duration, INST_ID, Department_ID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, courseID);
            pst.setString(2, courseName);
            pst.setInt(3, duration);
            pst.setInt(4, instID);
            pst.setInt(5, deptID);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                showMessage("Course inserted successfully!");
                clearFields();
            } else {
                showMessage("Insertion failed.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            showMessage("Invalid input or database error.");
        }
    }
    private void updateCourse() {
        try {
            int courseID = Integer.parseInt(tfCID.getText());
            String courseName = tfCName.getText();
            int duration = Integer.parseInt(tfDuration.getText());
            int instID = Integer.parseInt(tfInstID.getText());
            int deptID = Integer.parseInt(tfDepartmentID.getText());
            String query = "UPDATE Course SET C_Name = ?, Duration = ?, INST_ID = ?, Department_ID = ? WHERE C_ID = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, courseName);
            pst.setInt(2, duration);
            pst.setInt(3, instID);
            pst.setInt(4, deptID);
            pst.setInt(5, courseID);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                showMessage("Course updated successfully!");
                clearFields();
            } else {
                showMessage("Update failed.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            showMessage("Invalid input or database error.");
        }
    }
    private void deleteCourse() {
        try {
            int courseID = Integer.parseInt(tfCID.getText());
            String query = "DELETE FROM Course WHERE C_ID = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, courseID);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                showMessage("Course deleted successfully!");
                clearFields();
            } else {
                showMessage("Deletion failed.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            showMessage("Invalid input or database error.");
        }
    }
    private void searchCourse() {
        try {
            int courseID = Integer.parseInt(tfCID.getText());
            String query = "SELECT * FROM Course WHERE C_ID = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, courseID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tfCName.setText(rs.getString("C_Name"));
                tfDuration.setText(String.valueOf(rs.getInt("Duration")));
                tfInstID.setText(String.valueOf(rs.getInt("INST_ID")));
                tfDepartmentID.setText(String.valueOf(rs.getInt("Department_ID")));
                showMessage("Course found!");
            } else {
                showMessage("Course not found.");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showMessage("Error during search.");
        }
    }
    private void clearFields() {
        tfCID.setText("");
        tfCName.setText("");
        tfDuration.setText("");
        tfInstID.setText("");
        tfDepartmentID.setText("");
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