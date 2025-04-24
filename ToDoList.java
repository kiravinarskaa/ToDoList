import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Custom class for tasks
class Task {
    String name;
    boolean done;

    Task(String name) {
        this.name = name;
        this.done = false;
    }

    public String toString() {
        return (done ? "[✔] " : "[ ] ") + name;
    }
}

public class ToDoList {
    public static void main(String[] args) {
        JFrame frame = new JFrame("To-Do List");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultListModel<Task> taskModel = new DefaultListModel<>();
        JList<Task> taskList = new JList<>(taskModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Task) {
                    setText(value.toString());
                }
                return c;
            }
        });

        // Toggle done on double click
        taskList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = taskList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        Task task = taskModel.getElementAt(index);
                        task.done = !task.done;
                        taskModel.set(index, task); // trigger update
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(taskList);

        JTextField taskInput = new JTextField();
        JButton addButton = new JButton("Add Task");
        JButton deleteButton = new JButton("Delete Selected");

        // Panel for input and buttons
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(taskInput, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add task
        addButton.addActionListener(e -> {
            String taskText = taskInput.getText().trim();
            if (!taskText.isEmpty()) {
                taskModel.addElement(new Task(taskText));
                taskInput.setText("");
            }
        });

        // Delete selected task
        deleteButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                taskModel.remove(index);
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
