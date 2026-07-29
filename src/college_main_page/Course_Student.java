package college_main_page;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class Course_Student extends Frame{
    College_Main_Page mainPage;
      Connection con = DBConnection.getConnection();
    TextField tfCourse_ID, tfST_ID;
    Button insertButton, updateButton, deleteButton, searchButton, backButton, exitBtn;
    Label titleLabel, courseIDLabel, studentIDLabel;
    public Course_Student(College_Main_Page mainPage) {
        this.mainPage = mainPage;
        setTitle("Course Student Management");
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
        titleLabel = new Label("Course Student");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(250, 50, 400, 30);
        add(titleLabel);
        courseIDLabel = new Label("Course ID:");
        courseIDLabel.setForeground(Color.WHITE);
        courseIDLabel.setBounds(100, 120, 100, 30);
        add(courseIDLabel);
        studentIDLabel = new Label("Student ID:");
        studentIDLabel.setForeground(Color.WHITE);
        studentIDLabel.setBounds(100, 170, 100, 30);
        add(studentIDLabel);
        // TextFields
        tfCourse_ID = new TextField();
        tfCourse_ID.setBounds(220, 120, 200, 30);
        add(tfCourse_ID);
        tfST_ID = new TextField();
        tfST_ID.setBounds(220, 170, 200, 30);
        add(tfST_ID);
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
        insertButton.addActionListener(e -> insertCourseStudent());
        updateButton.addActionListener(e -> updateCourseStudent());
        deleteButton.addActionListener(e -> deleteCourseStudent());
        searchButton.addActionListener(e -> searchCourseStudent());
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
private void insertCourseStudent() {
    try {
        System.out.println("Inserting...");
        int courseID = Integer.parseInt(tfCourse_ID.getText());
        int stID = Integer.parseInt(tfST_ID.getText());
        String query = "INSERT INTO Course_Student (Course_ID, ST_ID) VALUES (?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, courseID);
        pst.setInt(2, stID);
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            showMessage("Student enrolled in course successfully!");
            clearFields();
        } else {
            showMessage("Insertion failed.");
        }
    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        showMessage("Invalid input or database error.");
    }
}
private void updateCourseStudent() {
    try {
        // المفتاح الأساسي (Course_ID وST_ID) لا يتم تعديله
        showMessage("Primary keys cannot be updated directly. Please delete and re-insert if needed.");
    } catch (Exception e) {
        e.printStackTrace();
        showMessage("Error during update.");
    }
}
private void deleteCourseStudent() {
    try {
        int courseID = Integer.parseInt(tfCourse_ID.getText());
        int stID = Integer.parseInt(tfST_ID.getText());
        String query = "DELETE FROM Course_Student WHERE Course_ID = ? AND ST_ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, courseID);
        pst.setInt(2, stID);
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            showMessage("Student removed from course successfully!");
            clearFields();
        } else {
            showMessage("Deletion failed.");
        }
    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        showMessage("Invalid input or database error.");
    }
}
private void searchCourseStudent() {
    try {
        int courseID = Integer.parseInt(tfCourse_ID.getText());
        int stID = Integer.parseInt(tfST_ID.getText());
        String query = "SELECT * FROM Course_Student WHERE Course_ID = ? AND ST_ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, courseID);
        pst.setInt(2, stID);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            showMessage("Student found in course!");
        } else {
            showMessage("Student not found in course.");
        }
    } catch (SQLException | NumberFormatException e) {
        e.printStackTrace();
        showMessage("Error during search.");
    }
}
private void clearFields() {
    tfCourse_ID.setText("");
    tfST_ID.setText("");
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