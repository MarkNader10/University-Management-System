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
public class Course_Instructor extends Frame{
    College_Main_Page mainPage;
      Connection con = DBConnection.getConnection();
    TextField tfCourse_ID, tfINST_ID;
    Button insertButton, updateButton, deleteButton, searchButton, backButton, exitBtn;
    Label titleLabel, courseIDLabel, instructorIDLabel;
    public Course_Instructor(College_Main_Page mainPage) {
        this.mainPage = mainPage;
        setTitle("Course Instructor Management");
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
        titleLabel = new Label("Course Instructor");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(250, 50, 400, 30);
        add(titleLabel);
        courseIDLabel = new Label("Course ID:");
        courseIDLabel.setForeground(Color.WHITE);
        courseIDLabel.setBounds(100, 120, 100, 30);
        add(courseIDLabel);
        instructorIDLabel = new Label("Instructor ID:");
        instructorIDLabel.setForeground(Color.WHITE);
        instructorIDLabel.setBounds(100, 170, 100, 30);
        add(instructorIDLabel);
        // TextFields
        tfCourse_ID = new TextField();
        tfCourse_ID.setBounds(220, 120, 200, 30);
        add(tfCourse_ID);
        tfINST_ID = new TextField();
        tfINST_ID.setBounds(220, 170, 200, 30);
        add(tfINST_ID);
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
        insertButton.addActionListener(e -> insertCourseInstructor());
        updateButton.addActionListener(e -> updateCourseInstructor());
        deleteButton.addActionListener(e -> deleteCourseInstructor());
        searchButton.addActionListener(e -> searchCourseInstructor());
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
private void insertCourseInstructor() {
    try {
        int courseID = Integer.parseInt(tfCourse_ID.getText());
        int instID = Integer.parseInt(tfINST_ID.getText());
        String query = "INSERT INTO Course_Instructor (Course_ID, INST_ID) VALUES (?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, courseID);
        pst.setInt(2, instID);
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            showMessage("Instructor assigned to course successfully!");
            clearFields();
        } else {
            showMessage("Insertion failed.");
        }
    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        showMessage("Invalid input or database error.");
    }
}
private void updateCourseInstructor() {
    try {
        int oldCourseID = Integer.parseInt(tfCourse_ID.getText());
        int oldInstID = Integer.parseInt(tfINST_ID.getText());
        // إذا حابب تحدث حاجة معينة غير المفتاح الأساسي، 
        // يلزمك تأخذ ID قديم وID جديد (لكن هنا أنت محدثش فعلياً لأن المفتاح الأساسي نفسه Course_ID, INST_ID)
        // لذلك مافيش حاجة تتحدث حرفياً غير لو ضفت أكواد جديدة
        showMessage("Primary keys cannot be updated directly. Please delete and re-insert if needed.");
    } catch (NumberFormatException e) {
        e.printStackTrace();
        showMessage("Invalid input.");
    }
}
private void deleteCourseInstructor() {
    try {
        int courseID = Integer.parseInt(tfCourse_ID.getText());
        int instID = Integer.parseInt(tfINST_ID.getText());
        String query = "DELETE FROM Course_Instructor WHERE Course_ID = ? AND INST_ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, courseID);
        pst.setInt(2, instID);
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            showMessage("Instructor removed from course successfully!");
            clearFields();
        } else {
            showMessage("Deletion failed.");
        }
    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        showMessage("Invalid input or database error.");
    }
}
private void searchCourseInstructor() {
    try {
        int courseID = Integer.parseInt(tfCourse_ID.getText());
        int instID = Integer.parseInt(tfINST_ID.getText());
        String query = "SELECT * FROM Course_Instructor WHERE Course_ID = ? AND INST_ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, courseID);
        pst.setInt(2, instID);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            showMessage("Instructor found for course!");
        } else {
            showMessage("Instructor not found for course.");
        }
    } catch (SQLException | NumberFormatException e) {
        e.printStackTrace();
        showMessage("Error during search.");
    }
}
    void clearFields() {
        tfCourse_ID.setText("");
        tfINST_ID.setText("");
    }
    void showMessage(String message) {
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