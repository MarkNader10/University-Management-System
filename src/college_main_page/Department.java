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
public class Department extends Frame  {
    College_Main_Page mainPage;
    TextField tfDID, tfDName, tfDLocation, tfHeatID;
    Button insertBtn, updateBtn, deleteBtn, searchBtn, backBtn,exitBtn;
    Connection con = DBConnection.getConnection();
    public Department(College_Main_Page mainPage) {
        this.mainPage = mainPage;    
        setTitle("Department Management");
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
        Label titleLabel = new Label("Department Management");
        titleLabel.setBounds(250, 40, 400, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);
        Label idLabel = new Label("Department:");
        idLabel.setBounds(80, 120, 150, 30);
        idLabel.setForeground(Color.WHITE);
        add(idLabel);
        tfDID = new TextField();
        tfDID.setBounds(230, 120, 200, 30);
        add(tfDID);
        Label nameLabel = new Label("Department Name:");
        nameLabel.setBounds(80, 170, 150, 30);
        nameLabel.setForeground(Color.WHITE);
        add(nameLabel);
        tfDName = new TextField();
        tfDName.setBounds(230, 170, 200, 30);
        add(tfDName);
        Label locationLabel = new Label("Department Location:");
        locationLabel.setBounds(80, 220, 150, 30);
        locationLabel.setForeground(Color.WHITE);
        add(locationLabel);
        tfDLocation = new TextField();
        tfDLocation.setBounds(230, 220, 200, 30);
        add(tfDLocation);
        Label heatIDLabel = new Label("Heat_ID FK:");
        heatIDLabel.setBounds(80, 270, 150, 30);
        heatIDLabel.setForeground(Color.WHITE);
        add(heatIDLabel);
        tfHeatID = new TextField();
        tfHeatID.setBounds(230, 270, 200, 30);
        add(tfHeatID);
        insertBtn = new Button("Insert");
        insertBtn.setBounds(500, 120, 120, 40);
        styleButton(insertBtn);
        insertBtn.setFont(new Font("Arial", Font.BOLD,16));
        add(insertBtn);
        updateBtn = new Button("Update");
        updateBtn.setBounds(500, 180, 120, 40);
        styleButton(updateBtn);
        updateBtn.setFont(new Font("Arial", Font.BOLD,16));
        add(updateBtn);
        deleteBtn = new Button("Delete");
        deleteBtn.setBounds(500, 240, 120, 40);
        styleButton(deleteBtn);
        deleteBtn.setFont(new Font("Arial", Font.BOLD,16));
        add(deleteBtn);
        searchBtn = new Button("Search");
        searchBtn.setBounds(500, 300, 120, 40);
        styleButton(searchBtn);
        searchBtn.setFont(new Font("Arial", Font.BOLD,16));
        add(searchBtn);
        backBtn = new Button("Back");
        backBtn.setBounds(20, 520, 100, 40);
        styleButton(backBtn);
        backBtn.setFont(new Font("Arial", Font.BOLD,16));
        add(backBtn);   
        exitBtn = new Button("Exit");
        exitBtn.setBounds(680, 520, 100, 40); // مكانه عاليمين
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Arial", Font.BOLD,16));
        add(exitBtn);   
        exitBtn.addActionListener(e -> {
          System.exit(0);
          dispose();
           });
        // Actions
        insertBtn.addActionListener(e -> insertDepartment());
        updateBtn.addActionListener(e -> updateDepartment());
        deleteBtn.addActionListener(e -> deleteDepartment());
        searchBtn.addActionListener(e -> searchDepartment());
        backBtn.addActionListener(e -> {
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
  private void styleButton(Button button) {
    button.setBackground(Color.WHITE);   // خلفية بيضاء
    button.setForeground(Color.BLACK);   // كتابة سوداء
    button.setFont(new Font("Arial", Font.PLAIN, 16));
}
    void insertDepartment() {
        try {
            PreparedStatement pst = con.prepareStatement(
                "INSERT INTO Department (D_ID, D_Name, D_Location, HEAT_ID) VALUES (?, ?, ?, ?)"
            );
            pst.setInt(1, Integer.parseInt(tfDID.getText()));
            pst.setString(2, tfDName.getText());
            pst.setString(3, tfDLocation.getText());
            String heatIdText = tfHeatID.getText();
            if (heatIdText.isEmpty()) {
                pst.setNull(4, Types.INTEGER);
            } else {
                pst.setInt(4, Integer.parseInt(heatIdText));
            }
            pst.executeUpdate();
            showMessage("Department inserted successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            showMessage("Error inserting Department!");
        }
    }
    void updateDepartment() {
        try {
            PreparedStatement pst = con.prepareStatement(
                "UPDATE Department SET D_Name = ?, D_Location = ?, HEAT_ID = ? WHERE D_ID = ?"
            );
            pst.setString(1, tfDName.getText());
            pst.setString(2, tfDLocation.getText());
            String heatIdText = tfHeatID.getText();
            if (heatIdText.isEmpty()) {
                pst.setNull(3, Types.INTEGER);
            } else {
                pst.setInt(3, Integer.parseInt(heatIdText));
            }
            pst.setInt(4, Integer.parseInt(tfDID.getText()));
            int rows = pst.executeUpdate();
            if (rows > 0) {
                showMessage("Department updated successfully!");
            } else {
                showMessage("Department ID not found!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showMessage("Error updating Department!");
        }
    }
    void deleteDepartment() {
        try {
            PreparedStatement pst = con.prepareStatement(
                "DELETE FROM Department WHERE D_ID = ?"
            );
            pst.setInt(1, Integer.parseInt(tfDID.getText()));
            int rows = pst.executeUpdate();
            if (rows > 0) {
                showMessage("Department deleted successfully!");
                clearFields();
            } else {
                showMessage("Department ID not found!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showMessage("Error deleting Department!");
        }
    }
    void searchDepartment() {
        try {
            PreparedStatement pst = con.prepareStatement(
                "SELECT * FROM Department WHERE D_ID = ?"
            );
            pst.setInt(1, Integer.parseInt(tfDID.getText()));
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tfDName.setText(rs.getString("D_Name"));
                tfDLocation.setText(rs.getString("D_Location"));
                int heatId = rs.getInt("HEAT_ID");
                if (!rs.wasNull()) {
                    tfHeatID.setText(String.valueOf(heatId));
                } else {
                    tfHeatID.setText("");
                }
                showMessage("Department found!");
            } else {
                showMessage("Department ID not found!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showMessage("Error searching Department!");
        }
    }
    void clearFields() {
        tfDID.setText("");
        tfDName.setText("");
        tfDLocation.setText("");
        tfHeatID.setText("");
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