package college_main_page;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Instructor extends Frame {
    College_Main_Page mainPage;
      Connection con = DBConnection.getConnection();
    // Define UI elements
    TextField tfInstructorID, tfFName, tfLName, tfDepartmentID;
    Button insertBtn, updateBtn, deleteBtn, searchBtn, backBtn, exitBtn;
    public Instructor(College_Main_Page mainPage) {
        this.mainPage = mainPage;
        setTitle("Instructor Management");      
        // Set the frame size and position
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
        setVisible(true);      
        // Connect to the database
        con = DBConnection.getConnection();      
        // Title label
        Label titleLabel = new Label("Instructor");
        titleLabel.setBounds(300, 40, 400, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);      
        // Instructor ID label and text field
        Label idLabel = new Label("Instructor ID:");
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(80, 120, 150, 30);
        add(idLabel);
        tfInstructorID = new TextField();
        tfInstructorID.setBounds(230, 120, 200, 30);
        add(tfInstructorID);     
        // First Name label and text field
        Label fnameLabel = new Label("First Name:");
        fnameLabel.setForeground(Color.WHITE);
        fnameLabel.setBounds(80, 170, 150, 30);
        add(fnameLabel);
        tfFName = new TextField();
        tfFName.setBounds(230, 170, 200, 30);
        add(tfFName);
        // Last Name label and text field
        Label lnameLabel = new Label("Last Name:");
        lnameLabel.setForeground(Color.WHITE);
        lnameLabel.setBounds(80, 220, 150, 30);
        add(lnameLabel);
        tfLName = new TextField();
        tfLName.setBounds(230, 220, 200, 30);
        add(tfLName);
        // Department ID label and text field
        Label departmentIDLabel = new Label("Department ID:");
        departmentIDLabel.setForeground(Color.WHITE);
        departmentIDLabel.setBounds(80, 270, 150, 30);
        add(departmentIDLabel);
        tfDepartmentID = new TextField();
        tfDepartmentID.setBounds(230, 270, 200, 30);
        add(tfDepartmentID);
        // Buttons
        insertBtn = new Button("Insert");
        insertBtn.setBounds(500, 120, 120, 40);
        styleButton(insertBtn);
        add(insertBtn);
        updateBtn = new Button("Update");
        updateBtn.setBounds(500, 180, 120, 40);
        styleButton(updateBtn);
        add(updateBtn);
        deleteBtn = new Button("Delete");
        deleteBtn.setBounds(500, 240, 120, 40);
        styleButton(deleteBtn);
        add(deleteBtn);
        searchBtn = new Button("Search");
        searchBtn.setBounds(500, 300, 120, 40);
        styleButton(searchBtn);
        add(searchBtn);
        backBtn = new Button("Back");
        backBtn.setBounds(20, 520, 100, 40);
        styleButton(backBtn);
        add(backBtn);
        exitBtn = new Button("Exit");
        exitBtn.setBounds(680, 520, 100, 40);
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        add(exitBtn);
        // Action Listeners
        insertBtn.addActionListener(e -> insertInstructor());
        updateBtn.addActionListener(e -> updateInstructor());
        deleteBtn.addActionListener(e -> deleteInstructor());
        searchBtn.addActionListener(e -> searchInstructor());
        backBtn.addActionListener(e -> {
            mainPage.setVisible(true);
            dispose();
        });
        exitBtn.addActionListener(e -> {
            System.exit(0);
            dispose();
        });
        // Window closing behavior
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });   setVisible(true);
    }
public void insertInstructor() {
    try {
        // تحقق إن كل الحقول مليانة
        if (tfInstructorID.getText().trim().isEmpty() || tfFName.getText().trim().isEmpty() ||
            tfLName.getText().trim().isEmpty() || tfDepartmentID.getText().trim().isEmpty()) {
            showMessage("Please fill in all fields.");
            return;
        }
        // تحويل البيانات لأرقام بعد التأكد من وجودها
        int instructorID = Integer.parseInt(tfInstructorID.getText().trim());
        int departmentID = Integer.parseInt(tfDepartmentID.getText().trim());
        // إعداد الاستعلام
        String query = "INSERT INTO Instructor (Instructor_ID, FName, LName, Department_ID) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, instructorID);
        pst.setString(2, tfFName.getText().trim());
        pst.setString(3, tfLName.getText().trim());
        pst.setInt(4, departmentID);
        pst.executeUpdate();

        showMessage("Instructor inserted successfully!");
        clearFields();
    } catch (NumberFormatException e) {
        showMessage("Instructor ID and Department ID must be numbers.");
    } catch (SQLException e) {
        e.printStackTrace();
        showMessage("Error inserting Instructor.");
    }
}
public void updateInstructor() {
    try {
        if (tfInstructorID.getText().trim().isEmpty() || tfFName.getText().trim().isEmpty() ||
            tfLName.getText().trim().isEmpty() || tfDepartmentID.getText().trim().isEmpty()) {
            showMessage("Please fill in all fields.");
            return;
        }
        int instructorID = Integer.parseInt(tfInstructorID.getText().trim());
        int departmentID = Integer.parseInt(tfDepartmentID.getText().trim());

        String query = "UPDATE Instructor SET FName = ?, LName = ?, Department_ID = ? WHERE Instructor_ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, tfFName.getText().trim());
        pst.setString(2, tfLName.getText().trim());
        pst.setInt(3, departmentID);
        pst.setInt(4, instructorID);
        int rows = pst.executeUpdate();
        if (rows > 0) {
            showMessage("Instructor updated successfully!");
        } else {
            showMessage("Instructor ID not found!");
        }
    } catch (NumberFormatException e) {
        showMessage("Instructor ID and Department ID must be numbers.");
    } catch (SQLException e) {
        e.printStackTrace();
        showMessage("Error updating Instructor.");
    }
}
public void deleteInstructor() {
    try {
        if (tfInstructorID.getText().trim().isEmpty()) {
            showMessage("Please enter Instructor ID.");
            return;
        }
        int instructorID = Integer.parseInt(tfInstructorID.getText().trim());
        String query = "DELETE FROM Instructor WHERE Instructor_ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, instructorID);
        int rows = pst.executeUpdate();
        if (rows > 0) {
            showMessage("Instructor deleted successfully!");
            clearFields();
        } else {
            showMessage("Instructor ID not found!");
        }
    } catch (NumberFormatException e) {
        showMessage("Instructor ID must be a number.");
    } catch (SQLException e) {
        e.printStackTrace();
        showMessage("Error deleting Instructor.");
    }
}
public void searchInstructor() {
    try {
        if (tfInstructorID.getText().trim().isEmpty()) {
            showMessage("Please enter Instructor ID.");
            return;
        }
        int instructorID = Integer.parseInt(tfInstructorID.getText().trim());
        String query = "SELECT * FROM Instructor WHERE Instructor_ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, instructorID);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            tfFName.setText(rs.getString("FName"));
            tfLName.setText(rs.getString("LName"));
            tfDepartmentID.setText(rs.getString("Department_ID"));
            showMessage("Instructor found.");
        } else {
            showMessage("Instructor ID not found!");
        }
    } catch (NumberFormatException e) {
        showMessage("Instructor ID must be a number.");
    } catch (SQLException e) {
        e.printStackTrace();
        showMessage("Error searching Instructor.");
    }
}
    // Style buttons
    private void styleButton(Button button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
}
       // Clear fields after an operation
          private void clearFields() {
          tfInstructorID.setText("");
          tfFName.setText("");
          tfLName.setText("");
          tfDepartmentID.setText("");
}
    // Show message dialog
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