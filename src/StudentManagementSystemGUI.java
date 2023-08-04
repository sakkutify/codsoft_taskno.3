import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManagementSystemGUI extends JFrame {
    private StudentManagementSystem studentManagementSystem;

    private JLabel nameLabel;
    private JLabel rollNumberLabel;
    private JLabel gradeLabel;
    private JTextField nameTextField;
    private JTextField rollNumberTextField;
    private JTextField gradeTextField;
    private JButton addButton;
    private JButton removeButton;
    private JButton searchButton;
    private JButton displayAllButton;
    private JTextArea outputTextArea;

    public StudentManagementSystemGUI() {
        studentManagementSystem = new StudentManagementSystem();

        setTitle("Student Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        nameLabel = new JLabel("Name:");
        rollNumberLabel = new JLabel("Roll Number:");
        gradeLabel = new JLabel("Grade:");
        nameTextField = new JTextField(15);
        rollNumberTextField = new JTextField(5);
        gradeTextField = new JTextField(5);
        addButton = new JButton("Add Student");
        removeButton = new JButton("Remove Student");
        searchButton = new JButton("Search Student");
        displayAllButton = new JButton("Display All Students");
        outputTextArea = new JTextArea(10, 30);

        add(nameLabel);
        add(nameTextField);
        add(rollNumberLabel);
        add(rollNumberTextField);
        add(gradeLabel);
        add(gradeTextField);
        add(addButton);
        add(removeButton);
        add(searchButton);
        add(displayAllButton);
        add(new JScrollPane(outputTextArea));

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeStudent();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        displayAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllStudents();
            }
        });
    }

    private void addStudent() {
        String name = nameTextField.getText();
        String rollNumberText = rollNumberTextField.getText();
        String grade = gradeTextField.getText();

        if (name.isEmpty() || rollNumberText.isEmpty() || grade.isEmpty()) {
            showMessage("All fields are required.");
            return;
        }

        int rollNumber;
        try {
            rollNumber = Integer.parseInt(rollNumberText);
        } catch (NumberFormatException ex) {
            showMessage("Invalid Roll Number. Please enter a valid number.");
            return;
        }

        Student student = new Student(name, rollNumber, grade);
        studentManagementSystem.addStudent(student);
        showMessage("Student added successfully!");
        clearFields();
    }

    private void removeStudent() {
        String rollNumberText = rollNumberTextField.getText();

        if (rollNumberText.isEmpty()) {
            showMessage("Roll Number is required.");
            return;
        }

        int rollNumber;
        try {
            rollNumber = Integer.parseInt(rollNumberText);
        } catch (NumberFormatException ex) {
            showMessage("Invalid Roll Number. Please enter a valid number.");
            return;
        }

        Student student = studentManagementSystem.searchStudent(rollNumber);
        if (student != null) {
            studentManagementSystem.removeStudent(student);
            showMessage("Student removed successfully!");
        } else {
            showMessage("Student not found!");
        }
        clearFields();
    }

    private void searchStudent() {
        String rollNumberText = rollNumberTextField.getText();

        if (rollNumberText.isEmpty()) {
            showMessage("Roll Number is required.");
            return;
        }

        int rollNumber;
        try {
            rollNumber = Integer.parseInt(rollNumberText);
        } catch (NumberFormatException ex) {
            showMessage("Invalid Roll Number. Please enter a valid number.");
            return;
        }

        Student student = studentManagementSystem.searchStudent(rollNumber);
        if (student != null) {
            outputTextArea.setText(student.toString());
        } else {
            showMessage("Student not found!");
            outputTextArea.setText("");
        }
        clearFields();
    }

    private void displayAllStudents() {
        StringBuilder sb = new StringBuilder();
        for (Student student : studentManagementSystem.getAllStudents()) {
            sb.append(student.toString()).append("\n");
        }
        outputTextArea.setText(sb.toString());
    }

    private void clearFields() {
        nameTextField.setText("");
        rollNumberTextField.setText("");
        gradeTextField.setText("");
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentManagementSystemGUI().setVisible(true);
            }
        });
    }
}
