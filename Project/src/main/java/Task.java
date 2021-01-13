/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HarrySmith
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Task {
    
        private String person;
        private int priority;
        private Date dueDate;
        private String taskTitle;
        private String status;
        private  ArrayList<Subtask> subtasks = new ArrayList<>();
       
    
    public Task(String taskTitle, String person, int priority, Date dueDate,String status) {
        this.person = person;
        this.priority = priority;
        this.dueDate = dueDate;
        this.taskTitle = taskTitle;
        this.status = status;

    }

  @Override
    public String toString() {
        
        return "Task: " + person + ", " + taskTitle + ", " + dueDate + ", " + priority;
        
    }
        
    public void setPriority(int priority) {
        this.priority = priority;
    }

      public void setPerson(String person) {
        this.person = person;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
    
      public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskTitle() {
        return taskTitle;
    }
    public String getPerson() {
        return person;
    }
      
    public int getPriority() {
        return priority;
    }
    
    
    public Date getDueDate() {
        return dueDate;
    }
    
      public String getStatus() {
        return status;
    }

    
    public void addSubtask(Subtask instance) {
 
        subtasks.add(instance);
 
    }
    
     
    public void removeSubtask(int subTaskNumber) {
 
        subtasks.remove(subTaskNumber);
 
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }
    


    DefaultTableModel subtaskModel = new DefaultTableModel();

    public void table(Task task){
        int m = 0;
        Object[] Table_Row = new Object[4];

        while(m <= task.subtasks.size()-1){
            Table_Row[0] = task.subtasks.get(m).getSubtask_number();
            Table_Row[1] = task.subtasks.get(m).getSubtask_description();
            m++;
            // Add Row to Table Model
            subtaskModel.addRow(Table_Row);
        };
    }


    public void subtaskTable(int i, Task task) {

        // Creating JFrame and JTable
        JFrame subtaskFrame = new JFrame();
        subtaskFrame.setLayout(new BorderLayout());
        JTable subtaskTable = new JTable();
        subtaskTable.setDefaultEditor(Object.class, null);
        JPanel bottomBitSubtask = new JPanel();
        bottomBitSubtask.setLayout(new GridLayout(3,3));


        // Create Model of Table with own Identifiers
        Object[] subtaskColumns = {"Subtask Number", "Subtask Name"};

        subtaskModel.setColumnIdentifiers(subtaskColumns);

        // Set Model for the Table
        subtaskTable.setModel(subtaskModel);

        subtaskTable.setBackground(Color.BLUE);
        subtaskTable.setForeground(Color.WHITE);
        Font subFont = new Font("", 1, 22);
        subtaskTable.setFont(subFont);
        subtaskTable.setRowHeight(30);

        JLabel subTaskNumberLabel = new JLabel("Subtask Number: ");
        JLabel subTaskNameLabel = new JLabel("Subtask Name: ");

        JTextField subtaskNumberText = new JTextField();
        JTextField subtaskNameText = new JTextField();

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton editButton = new JButton("Update");
        
        JLabel fillerLabel = new JLabel("");
        JLabel fillerLabelTwo = new JLabel("");

        bottomBitSubtask.add(subTaskNameLabel);
        bottomBitSubtask.add(subtaskNameText);
        bottomBitSubtask.add(addButton);
        
        bottomBitSubtask.add(subTaskNumberLabel);
        bottomBitSubtask.add(subtaskNumberText);
        bottomBitSubtask.add(deleteButton);
        
        bottomBitSubtask.add(fillerLabel);
        bottomBitSubtask.add(fillerLabelTwo);
        bottomBitSubtask.add(editButton);


        JScrollPane subtaskScroll = new JScrollPane(subtaskTable);

        subtaskFrame.add(subtaskScroll, BorderLayout.NORTH);
        subtaskFrame.add(bottomBitSubtask, BorderLayout.SOUTH);

        subtaskFrame.setSize(new Dimension(880, 550));
        subtaskFrame.setMinimumSize(new Dimension(500,530));
        
      
   


        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - subtaskFrame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - subtaskFrame.getHeight()) / 2);
        subtaskFrame.setLocation(x, y);

        subtaskFrame.setResizable(true);
        subtaskFrame.setVisible(true);
        subtaskFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add Row Button Function
        addButton.addActionListener(new ActionListener() {
            int x = 0;

            @Override

            // In case add action performed listen for event
            public void actionPerformed(ActionEvent e) {

                final Pattern pattern_number = Pattern.compile("[0-9]+");
                if (!pattern_number.matcher(subtaskNumberText.getText()).matches()) {
                    JOptionPane.showMessageDialog(subtaskFrame, "Invalid Priority");
                    throw new IllegalArgumentException("Invalid Priority");
                }


                final Pattern pattern_task = Pattern.compile("([A-Z]*[a-z']*)(\\s[A-Z]*[a-z']*)*");
                if (!pattern_task.matcher(subtaskNameText.getText()).matches()) {
                    JOptionPane.showMessageDialog(subtaskFrame, "Invalid Task Description");
                    throw new IllegalArgumentException("Invalid Task Text");
                }

                // Insert New Data into Row

                Subtask newSubtask = new Subtask(Integer.parseInt(subtaskNumberText.getText()), subtaskNameText.getText());
                subtaskNumberText.setText("");    
                subtaskNameText.setText("");
                task.subtasks.add(newSubtask);

                subtaskModel.setRowCount(0);
                table(task);


            }
        });
        
        
        
        
        
        
        
        
        //////////////////////////////
        
        editButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
             
                // Incase update button is pressed, set value of i as current selected Row.
                int i = subtaskTable.getSelectedRow();
                
                // Run below code if table is not empty.
                if(i >= 0) 
                {
                    // Set value of Cells within the Row to values of JTextFields
                 //  model.setValueAt(textTaskNumber.getText(), i, 2);
                 //  model.setValueAt(textAssignedTo.getText(), i, 1);
                 //  model.setValueAt(textTask.getText(), i, 0);
                    
                     final Pattern pattern = Pattern.compile("([A-Z]*[a-z']*)(\\s[A-Z]*[a-z']*)*");
                    if (!pattern.matcher(subtaskNameText.getText()).matches()) {
                         JOptionPane.showMessageDialog(subtaskFrame, "Task Assigned to section is wrong");
                 throw new IllegalArgumentException("Invalid Task User Assigned");
                }
                    
                 final Pattern pattern_number = Pattern.compile("[0-9]+");
                    if (!pattern_number.matcher(subtaskNumberText.getText()).matches()) {
                         JOptionPane.showMessageDialog(subtaskFrame, "Invalid Priority");
                 throw new IllegalArgumentException("Invalid Priority");
                }
           
              
                  
                     
                   
              
                  task.subtasks.get(i).setSubtask_description(subtaskNameText.getText());
                  
                    task.subtasks.get(i).setSubtask_number(Integer.parseInt(subtaskNumberText.getText()));
                     
                   
                  
              subtaskNumberText.setText("");    
                subtaskNameText.setText("");
                
                    subtaskModel.setRowCount(0);
                table(task);

      
                }
                else{
                    // Cannot Update the table
                    JOptionPane.showMessageDialog(subtaskFrame, "Can't Update the Table");
                }
            }
        } );
        
        
        subtaskTable.addMouseListener(new MouseAdapter(){
        
        @Override
        
        public void mouseClicked(MouseEvent e){
            
            // In case there is an event set i variable as current Row
            int i = subtaskTable.getSelectedRow();
             
            subtaskNumberText.setText(subtaskModel.getValueAt(i, 0).toString());
            subtaskNameText.setText(subtaskModel.getValueAt(i, 1).toString());
            
            
        }
        });
        
        
        
         deleteButton.addActionListener(new ActionListener(){

            @Override
            // Incase delete action is performed listen for event
            public void actionPerformed(ActionEvent e) {
            
                // Set i variable as Specific selected Row within Table
                int i = subtaskTable.getSelectedRow();
                
                // If i variable is greater than 0 
                if(i >= 0){
                    // Remove that specific row from the table
                    task.removeSubtask(i);
                    
                      subtaskNumberText.setText("");    
                subtaskNameText.setText("");
                    subtaskModel.setRowCount(0);
                    table(task);
                   // model.removeRow(i);
                }
                else{
                    JOptionPane.showMessageDialog(subtaskFrame, "Can't Delete Table is empty");
                }
            }
        });
        
    }
}
