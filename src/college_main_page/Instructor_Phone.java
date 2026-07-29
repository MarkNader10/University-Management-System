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
public class Instructor_Phone extends Frame{
      College_Main_Page mainPage;
    Connection con = DBConnection.getConnection();
    TextField tfINST_ID;
    TextArea tfPhone;
    Button insertButton, updateButton, deleteButton, searchButton, backButton, exitBtn;
    Label titleLabel, instIDLabel, phoneLabel;
    public Instructor_Phone(College_Main_Page mainPage) {
        this.mainPage = mainPage;
        setTitle("Instructor Phone Management");
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
        titleLabel = new Label("Instructor Phone");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setBounds(250, 50, 400, 30);
        add(titleLabel);
        instIDLabel = new Label("Instructor ID:");
        instIDLabel.setForeground(Color.WHITE);
        instIDLabel.setBounds(100, 120, 100, 30);
        add(instIDLabel);
        phoneLabel = new Label("Phone(s):");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setBounds(100, 170, 100, 30);
        add(phoneLabel);
        // TextFields
        tfINST_ID = new TextField();
        tfINST_ID.setBounds(220, 120, 200, 30);
        add(tfINST_ID);
        tfPhone = new TextArea();
        tfPhone.setBounds(220, 170, 200, 100);
        add(tfPhone);
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
        insertButton.addActionListener(e -> insertInstructorPhone());
        updateButton.addActionListener(e -> updateInstructorPhone());
        deleteButton.addActionListener(e -> deleteInstructorPhone());
        searchButton.addActionListener(e -> searchInstructorPhone());
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
    private void insertInstructorPhone() {
        try {
            int instID = Integer.parseInt(tfINST_ID.getText());
            String[] phones = tfPhone.getText().split("\\n");
            String query = "INSERT INTO Instructor_Phone (INST_ID, Phone) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            int inserted = 0;
            for (String phone : phones) {
                phone = phone.trim();
                if (!phone.isEmpty()) {
                    pst.setInt(1, instID);
                    pst.setString(2, phone);
                    pst.executeUpdate();
                    inserted++;
                }
            }
            if (inserted > 0) {
                showMessage("Inserted " + inserted + " phone(s) successfully!");
                clearFields();
            } else {
                showMessage("No phone numbers to insert.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            showMessage("Invalid input or database error.");
        }
    }
    private void updateInstructorPhone() {
        try {
            int instID = Integer.parseInt(tfINST_ID.getText());
            String[] phones = tfPhone.getText().split("\\n");
            // Delete old phones
            String deleteQuery = "DELETE FROM Instructor_Phone WHERE INST_ID = ?";
            PreparedStatement deleteStmt = con.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, instID);
            deleteStmt.executeUpdate();
            // Insert new phones
            String insertQuery = "INSERT INTO Instructor_Phone (INST_ID, Phone) VALUES (?, ?)";
            PreparedStatement insertStmt = con.prepareStatement(insertQuery);
            int inserted = 0;
            for (String phone : phones) {
                phone = phone.trim();
                if (!phone.isEmpty()) {
                    insertStmt.setInt(1, instID);
                    insertStmt.setString(2, phone);
                    insertStmt.executeUpdate();
                    inserted++;
                }
            }
            if (inserted > 0) {
                showMessage("Updated with " + inserted + " phone(s).");
                clearFields();
            } else {
                showMessage("No phones entered. All old phones deleted.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            showMessage("Error updating phones.");
        }
    }
    private void deleteInstructorPhone() {
        try {
            int instID = Integer.parseInt(tfINST_ID.getText());
            String query = "DELETE FROM Instructor_Phone WHERE INST_ID = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, instID);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                showMessage("Instructor phone(s) deleted successfully!");
                clearFields();
            } else {
                showMessage("Deletion failed.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            showMessage("Invalid input or database error.");
        }
    }
    private void searchInstructorPhone() {
        try {
            int instID = Integer.parseInt(tfINST_ID.getText());
            String query = "SELECT Phone FROM Instructor_Phone WHERE INST_ID = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, instID);
            ResultSet rs = pst.executeQuery();
            StringBuilder phones = new StringBuilder();
            while (rs.next()) {
                phones.append(rs.getString("Phone")).append("\n");
            }
            if (phones.length() > 0) {
                tfPhone.setText(phones.toString().trim());
                showMessage("Phone numbers loaded.");
            } else {
                tfPhone.setText("");
                showMessage("No phone numbers found.");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showMessage("Error during search.");
        }
    }
    private void clearFields() {
        tfINST_ID.setText("");
        tfPhone.setText("");
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