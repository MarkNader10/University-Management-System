package college_main_page;
import java.awt.*;
import java.util.*;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Label;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.*;
import java.sql.Connection;
public class College_Main_Page extends Frame implements ActionListener {
  Connection con = DBConnection.getConnection();
    public College_Main_Page() {
     setTitle("Main Page");
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
     setBackground(new Color(50, 50, 50));
     setVisible(true);
        Label welcomeLabel = new Label("Welcome to Main Page", Label.CENTER);
        welcomeLabel.setBounds(200, 50, 400, 50);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.RED);
        add(welcomeLabel);
        Button bDepartment = new Button("Department");
        bDepartment.setBounds(100, 150, 150, 50);
        styleButton(bDepartment);
        add(bDepartment);
        bDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Department pageOne = new Department(College_Main_Page.this);
                pageOne.setVisible(true);
                setVisible(false);
            }
        });
    
         Button bInstructor = new Button("Instructor");
        bInstructor.setBounds(325, 150, 150, 50);
        styleButton(bInstructor);
        add(bInstructor);
        bInstructor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instructor pageTwo = new Instructor(College_Main_Page.this);
                pageTwo.setVisible(true);
                 setVisible(false);
            }
        });
        Button bCourse = new Button("Course");
        bCourse.setBounds(550, 150, 150, 50);
        styleButton(bCourse);
        add(bCourse);
       bCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Course pageThree = new Course(College_Main_Page.this);
                pageThree.setVisible(true);
                 setVisible(false);
            }
        });
        Button bStudent = new Button("Student");
        bStudent.setBounds(100, 250, 150, 50);
        styleButton(bStudent);
        add(bStudent);
       bStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student pageFour = new Student(College_Main_Page.this);
                pageFour.setVisible(true);
                 setVisible(false);
            }
        });
        Button bStudent_Phone = new Button("Student_Phone");
        bStudent_Phone.setBounds(325, 250, 150, 50);
        styleButton(bStudent_Phone);
        add(bStudent_Phone);
        bStudent_Phone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student_Phone pageFive = new Student_Phone(College_Main_Page.this);
                pageFive.setVisible(true);
                 setVisible(false);
            }
        });
        Button bInstructor_Phone = new Button("Instructor_Phone");
       bInstructor_Phone.setBounds(550, 250, 150, 50);
        styleButton(bInstructor_Phone);
        add(bInstructor_Phone);
        bInstructor_Phone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instructor_Phone pageSix = new Instructor_Phone(College_Main_Page.this);
                pageSix.setVisible(true);
                 setVisible(false);
            }
        });
        Button bCourse_Student = new Button("Course_Student");
       bCourse_Student.setBounds(100, 350, 150, 50);
        styleButton(bCourse_Student);
        add(bCourse_Student);
        bCourse_Student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Course_Student pageSeven = new Course_Student(College_Main_Page.this);
                pageSeven.setVisible(true);
                 setVisible(false);
            }
        });
        Button bCourse_Instructor = new Button("Course_Instructor");
        bCourse_Instructor.setBounds(325, 350, 150, 50);
        styleButton(bCourse_Instructor);
        add(bCourse_Instructor);
        bCourse_Instructor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Course_Instructor pageEight = new Course_Instructor(College_Main_Page.this);
                pageEight.setVisible(true);
                 setVisible(false);
            }
        });
    
         Button bExit = new Button("Exit");
        bExit.setBounds(550, 350, 150, 50);
        bExit.setBackground(Color.RED);
        bExit.setForeground(Color.WHITE);
        bExit.setFont(new Font("Arial", Font.BOLD, 20));
        add(bExit);
        bExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    addWindowListener(new WindowAdapter(){
    public void windowClosing(WindowEvent we){
    System.exit(0);
    }
    });
    }
       private void styleButton(Button button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    public static void main(String[] args) {
      College_Main_Page MainPage = new College_Main_Page();
    }

    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}