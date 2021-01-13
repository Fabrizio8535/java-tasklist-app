
import com.dropbox.core.DbxException;
import static com.dropbox.core.v2.teamlog.AssetLogInfo.file;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HarrySmith
 */
public class Admin {
         static  String file_name_full = "";
     public static void admin(DefaultTableModel model) {
        
        List newList = new List();
       
        ///////////// SETTING UP THE GUI /////////////
        
        // Sorting Combo Box
        String[] sort = {"-- No Sort --","Priority", "Date - Ascending", "Date - Descending","Assignee"};
        JComboBox sorting = new JComboBox(sort);
       
        //Filtering by priority Combo Box
        String[] filterByPriority = {"-- No Filter --","1","2","3","4","5","6","7","8","9","10"};
        JComboBox filteringByPriority = new JComboBox(filterByPriority);

        // Filtering by complete Combo Box
        String[] filterByComplete = {"-- No Filter --","Completed","Not Completed"};
        JComboBox filteringByComplete = new JComboBox(filterByComplete);        
       
       // Filtering by Person
        JButton filterPeople = new JButton("Filter people");
        
        // Creating JFrame and JTable
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JTable table = new JTable(); 
        table.setDefaultEditor(Object.class, null);

        // Create Model of Table with own Identifiers
        Object[] columns = {"Task","Assigned To","Priority","Due Date","Status"};
        model.setColumnIdentifiers(columns);
        
        // Set Model for the Table
        table.setModel(model);
        
        // Edit JTable Properties such as Colour 
        table.setBackground(Color.RED);
        table.setForeground(Color.WHITE);
        Font font = new Font("",1,22);
        table.setFont(font);
        table.setRowHeight(30);
        
        // Declare Labels for input fields;
        JLabel taskNameLabel = new JLabel("Task Name: ");
        JLabel assignedToLabel = new JLabel("Assigned to: ");
        JLabel priorityLabel = new JLabel("Priority: ");
        JLabel dueDateLabel = new JLabel("Due Date: ");
        
        // Status Stuff
        JLabel status_label = new JLabel("Status: ");
        String[] status = {"Not Completed","Completed"};
        JComboBox status_comboBox = new JComboBox(status);
        
        //Declare Labels for sort/Filter 
        JLabel sortComboBoxLabel = new JLabel("Sort By: ");
        JLabel filterByPriorityComboBoxLabel = new JLabel("Filter By Priority: ");
        JLabel filterByCompleteComboBoxLabel = new JLabel("Filter By Completed: ");
        JLabel filterByPersonLabel = new JLabel("Filter by Person: ");
        
        // Declare JTextFields for 
        JTextField textTask = new JTextField();
        JTextField textAssignedTo = new JTextField();
        JTextField textPriority = new JTextField();
        JTextField textDate = new JTextField();
        
        JTextField textDateMonth = new JTextField();
        JTextField textDateYear = new JTextField();
        
        JLabel fillerLabel = new JLabel("");
        JLabel fillerLabelTwo = new JLabel("");
        
        // Create JButtons for Data Manipulation
        JButton Add_Button = new JButton("Add");
        JButton Delete_Button = new JButton("Delete");
        JButton Update_Button = new JButton("Update");
        JButton Save_Button = new JButton("Save");
        JButton Load_Button = new JButton("Load");
        JButton Cloud_Button = new JButton("Cloud");
        JButton Email_Button = new JButton("Email");

        // Declare Panels
        JScrollPane pane = new JScrollPane(table);
        JPanel bottomBit = new JPanel();
        JPanel bottomLeft = new JPanel();
        JPanel bottomRight = new JPanel();

        JPanel date = new JPanel();

        //Layout for bottom left
        bottomLeft.setLayout(new GridLayout(5,3));

        bottomLeft.add(taskNameLabel);
        bottomLeft.add(textTask);
        bottomLeft.add(Add_Button);

        bottomLeft.add(assignedToLabel);
        bottomLeft.add(textAssignedTo);
        bottomLeft.add(Update_Button);

        bottomLeft.add(priorityLabel);
        bottomLeft.add(textPriority);
        bottomLeft.add(Delete_Button);
 
        bottomLeft.add(dueDateLabel);
        bottomLeft.add(date, new GridLayout(2,2));
       
        //Layout for date part of bottom Left
        date.add(textDate);
        date.add(textDateMonth);
        date.add(textDateYear);
        
        bottomLeft.add(fillerLabel);
        
        bottomLeft.add(status_label);
        bottomLeft.add(status_comboBox);

        //Layout for bottom Right
        bottomRight.setLayout(new GridLayout(6,2));

        bottomRight.add(sortComboBoxLabel);
        bottomRight.add(sorting);

        bottomRight.add(filterByPriorityComboBoxLabel);
        bottomRight.add(filteringByPriority);
        
        bottomRight.add(filterByCompleteComboBoxLabel);
        bottomRight.add(filteringByComplete);

        bottomRight.add(filterByPersonLabel);
        bottomRight.add(filterPeople);

        bottomRight.add(Save_Button);
        bottomRight.add(Load_Button);
        
        bottomRight.add(Cloud_Button);
        bottomRight.add(Email_Button);
        
        // Add panel to frame
        frame.add(pane, BorderLayout.NORTH);
        frame.add(bottomBit, BorderLayout.SOUTH);
        bottomBit.add(bottomLeft, BorderLayout.WEST);
        bottomBit.add(bottomRight, BorderLayout.EAST);

        frame.setMinimumSize(new Dimension(400,400));

        //Set Placeholder text for dates
        textDate.setText("DD");
        textDateMonth.setText("MM");
        textDateYear.setText("YYYY");

        ///////////// LOGIC FOR THE BUTTONS /////////////

        // Add Row Button Function
        Add_Button.addActionListener(new ActionListener(){
            
         int x = 0;
            @Override
            
            // In case add action performed listen for event
            public void actionPerformed(ActionEvent e) {
             
                final Pattern pattern = Pattern.compile("([A-Z]*[a-z']*)(\\s[A-Z]*[a-z']*)*");
                    if (!pattern.matcher(textAssignedTo.getText()).matches()) {
                         JOptionPane.showMessageDialog(frame, "Task Assigned to section is wrong");
                 throw new IllegalArgumentException("Invalid Task User Assigned");
                }
                    
                 final Pattern pattern_number = Pattern.compile("[0-9]+");
                    if (!pattern_number.matcher(textPriority.getText()).matches()) {
                         JOptionPane.showMessageDialog(frame, "Invalid Priority");
                 throw new IllegalArgumentException("Invalid Priority");
                }
           
              
                    final Pattern pattern_task = Pattern.compile("([A-Z]*[a-z']*)(\\s[A-Z]*[a-z']*)*");
                    if (!pattern_task.matcher(textTask.getText()).matches()) {
                         JOptionPane.showMessageDialog(frame, "Invalid Task Description");
                 throw new IllegalArgumentException("Invalid Task Text");
                }
           
                // Insert New Data into Row
                if (Integer.parseInt(textPriority.getText()) > 10) {
                    JOptionPane.showMessageDialog(frame, "Priority must be a number between 1 and 10");
                }
                else {
                    try {
                    Date ds = new Date(Integer.parseInt(textDate.getText()), Integer.parseInt(textDateMonth.getText()), Integer.parseInt(textDateYear.getText()));
                    Task ts = new Task(textTask.getText(), textAssignedTo.getText(), Integer.parseInt(textPriority.getText()), ds,(String)status_comboBox.getSelectedItem());
                    textTask.setText("");    
                    textAssignedTo.setText("");
                    textPriority.setText("");
                    textDate.setText("");
                    textDateMonth.setText("");
                    textDateYear.setText("");
                    newList.addTask(ts);
                    }
                    catch (NumberFormatException err) {
                        JOptionPane.showMessageDialog(frame, "Date must be in the format: DD/MM/YYYY. "+ System.lineSeparator() + "For Example: 03/07/1998");
                    }
                        model.setRowCount(0);
                        Main.table(newList);
                }
            }
        });
        
        // Remove Row Button Function
        Delete_Button.addActionListener(new ActionListener(){

            @Override
            // Incase delete action is performed listen for event
            public void actionPerformed(ActionEvent e) {
            
                // Set i variable as Specific selected Row within Table
                int i = table.getSelectedRow();
                
                // If i variable is greater than 0 
                if(i >= 0){
                    // Remove that specific row from the table
                    newList.deleteTask(i);
                    
                        textTask.setText("");    
                textAssignedTo.setText("");
                textPriority.setText("");
                textDate.setText("");
                textDateMonth.setText("");
                textDateYear.setText("");
                    model.setRowCount(0);
                    Main.table(newList);
                   // model.removeRow(i);
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Can't Delete Table is empty");
                }
            }
        });
        
        // Pass Row Data to TextField once mouse is clicked on that Row
        table.addMouseListener(new MouseAdapter(){
        
        @Override
        
        public void mouseClicked(MouseEvent e){
            
            // In case there is an event set i variable as current Row
            int i = table.getSelectedRow();
             if(e.getClickCount()==2){
              newList.getTasks().get(i).subtaskTable(i,newList.getTasks().get(i));
             }
            /* Set text of JTextFields as values of selected Row.
            To display it within TextField it is converted to string.
             */
            textTask.setText(model.getValueAt(i, 0).toString());
            textAssignedTo.setText(model.getValueAt(i, 1).toString());
            textPriority.setText(model.getValueAt(i, 2).toString());
            textDate.setText(newList.getTasks().get(i).getDueDate().dayToString());
            textDateMonth.setText(newList.getTasks().get(i).getDueDate().monthToString());
            textDateYear.setText(newList.getTasks().get(i).getDueDate().yearToString());
            status_comboBox.setSelectedItem(newList.getTasks().get(i).getStatus().toString());
            
        }
        });
        
        // Save_Button
        Save_Button.addActionListener(new ActionListener(){
            
            @Override
            
            public void actionPerformed(ActionEvent ae) {
                
              
                int  count = 0;
                
                if (ae.getActionCommand () == Save_Button.getActionCommand())
                {
                    try
                    {
                     String file_name = JOptionPane.showInputDialog("Please input file name");
                     file_name_full = file_name + ".txt";
                     FileWriter fileWriter = new FileWriter (file_name_full);
                     if (file_name.length() <= 0)
                    {
                    JOptionPane.showMessageDialog(frame, "No file inputted!");
                    }else
                     {
                       while(count <= newList.getTasks().size()-1){
                          fileWriter.write(count + 1 + ". " + "Task Title: "+ newList.getTasks().get(count).getTaskTitle() + ", " + "Task Assigned to: " + newList.getTasks().get(count).getPerson() + ", " + "Task Priority : " + newList.getTasks().get(count).getPriority() + ", " + "Task DueDate : " + newList.getTasks().get(count).getDueDate().toString() + ", " + "Task Status : "+ newList.getTasks().get(count).getStatus().toString() + "." + System.lineSeparator());
                          count++;
                       }
                        fileWriter.close();
                        JOptionPane.showMessageDialog(frame, "File Saved");    
                       
                       

                       
                            }
                            }catch(IOException e)
                            {
                                JOptionPane.showMessageDialog(null, e+"");}

                            }

                    }});
                
                  // Load_Button
        Load_Button.addActionListener(new ActionListener(){   
      
            @Override
            
            public void actionPerformed(ActionEvent ae) {
                
                if (ae.getActionCommand () == (Load_Button.getActionCommand()))
                {
                    String file_name = JOptionPane.showInputDialog("Please input file name");
                     file_name_full = file_name + ".txt";
                    File file = new File(file_name_full);
                    if (!file.exists())
                    {
                    JOptionPane.showMessageDialog(frame, "Please select a file to load from");
                    }
                    if (file_name.length() <= 0)
                    {
                    JOptionPane.showMessageDialog(frame, "File doesn't exist");
                    }else
                    {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        DefaultTableModel model = (DefaultTableModel)table.getModel();
                        model.setRowCount(0);
                        int count;
                        Object[] tableLines = br.lines().toArray();
                            for(count=0;count < tableLines.length; count++){
                                String stringLine = tableLines[count].toString().trim();
                                String[] printInRow = stringLine.split(", ");
                                for (int index =0; index < printInRow.length; index++){
                                    printInRow[index] = printInRow[index].replaceAll("[0-9][.][ ]", "");
                                    printInRow[index] = printInRow[index].replaceAll("Task Title: ", "");
                                    printInRow[index] = printInRow[index].replaceAll("Task Assigned to: ", "");
                                    printInRow[index] = printInRow[index].replaceAll("Task Priority : ", "");
                                    printInRow[index] = printInRow[index].replaceAll("Task DueDate : ", "");
                                    printInRow[index] = printInRow[index].replaceAll("Task Status : ", "");
                                    printInRow[index] = printInRow[index].replaceAll("[. ]", "");
                                    printInRow[index] = printInRow[index].replaceAll("NotCompleted", "Not Completed");
                                    }
                                model.addRow(printInRow);
                                br.close();
                            }
                            JOptionPane.showMessageDialog(frame, "Loading complete");
                       } catch (FileNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    
            }
            }
        });           
                
        Cloud_Button.addActionListener(new ActionListener() {

             @Override
             
            public void actionPerformed(ActionEvent e) {
             
                    try {
                    Cloud.Cloud(frame,file_name_full);
                } catch (DbxException | IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
        
        
           Email_Button.addActionListener(new ActionListener() {

             @Override
             
            public void actionPerformed(ActionEvent e) {
              String email = JOptionPane.showInputDialog(frame, "What's your email address?");
                        String password = JOptionPane.showInputDialog(frame, "What's your password?");

                       Email a = new Email();
                        try {
                            a.Email(email,password,file_name_full);
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MessagingException ex) {
                             Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                         }
            }
        });
        
        // Update Row Button
        Update_Button.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
             
                // Incase update button is pressed, set value of i as current selected Row.
                int i = table.getSelectedRow();
                
                // Run below code if table is not empty.
                if(i >= 0) 
                {
                    
                     final Pattern pattern = Pattern.compile("([A-Z]*[a-z']*)(\\s[A-Z]*[a-z']*)*");
                    if (!pattern.matcher(textAssignedTo.getText()).matches()) {
                         JOptionPane.showMessageDialog(frame, "Task Assigned to section is wrong");
                 throw new IllegalArgumentException("Invalid Task User Assigned");
                }
                    
                 final Pattern pattern_number = Pattern.compile("[0-9]+");
                    if (!pattern_number.matcher(textPriority.getText()).matches()) {
                         JOptionPane.showMessageDialog(frame, "Invalid Priority");
                 throw new IllegalArgumentException("Invalid Priority");
                }
           
              
                    final Pattern pattern_task = Pattern.compile("([A-Z]*[a-z']*)(\\s[A-Z]*[a-z']*)*");
                    if (!pattern_task.matcher(textTask.getText()).matches()) {
                         JOptionPane.showMessageDialog(frame, "Invalid Task Description");
                 throw new IllegalArgumentException("Invalid Task Text");
                }
           
                     
                   
                   newList.getTasks().get(i).setTaskTitle(textTask.getText());
                   newList.getTasks().get(i).setPerson(textAssignedTo.getText());
                   newList.getTasks().get(i).setPriority(Integer.parseInt(textPriority.getText()));
                   newList.getTasks().get(i).setStatus((String)status_comboBox.getSelectedItem());
                     
                   
                   try {
                   Date updatedDate = new Date(Integer.parseInt(textDate.getText()), Integer.parseInt(textDateMonth.getText()), Integer.parseInt(textDateYear.getText()));
                   newList.getTasks().get(i).setDueDate(updatedDate);
                   } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(frame, "Invalid Due Date");
                   }
            
                    textTask.setText("");    
                    textAssignedTo.setText("");
                    textPriority.setText("");
                    textDate.setText("");
                    textDateMonth.setText("");
                    textDateYear.setText("");
                
                    model.setRowCount(0);
                    Main.table(newList);
                }
                else{
                    // Cannot Update the table
                    JOptionPane.showMessageDialog(frame, "Can't Update the Table");
                }
            }
        } );
        
          //Sort Function
        
        sorting.addActionListener((ActionEvent e) -> {
            String current = (String)sorting.getSelectedItem();              
              
            if (current.equals("Priority")) {              
    //Bubble Sort to reorder the tasks array based on priority            
                for(int w = 0; w<newList.getTasks().size();w++){
                for(int y = 0; y < newList.getTasks().size(); y ++) {
                     Task task = newList.getTasks().get(y);
                     try {
                         if(task.getPriority() > newList.getTasks().get(y+1).getPriority()){
                             Task temp = task;
                             newList.getTasks().set(y, newList.getTasks().get(y+1));
                             newList.getTasks().set(y+1, temp);
                         }
                        } catch(IndexOutOfBoundsException n){              
                     }
                   }
                }
                model.setRowCount(0);
                Main.table(newList);

            }
            if(current.equals("Date - Ascending")) {
                newList.sortByDateAscending();
                model.setRowCount(0);
                Main.table(newList);
            }
            if(current.equals("Date - Descending")) {
                newList.sortByDateDescending();
                model.setRowCount(0);
                Main.table(newList);
            }
            if(current.equals("Assignee")) {
                newList.sortByAssignee();
                model.setRowCount(0);
                Main.table(newList);
            }
        });
        
        //Filter by priority function
        
        filteringByPriority.addActionListener((ActionEvent e) -> {
            String test = (String)filteringByPriority.getSelectedItem();
            if(!test.equals("No Filter")) {
                int current = Integer.parseInt((String)filteringByPriority.getSelectedItem());
       
                List filteredList = new List();
                int size = newList.getTasks().size();
                for (int x = 0; x < size ; x++) {
                if (newList.getTasks().get(x).getPriority() == current) {
                filteredList.addTask(newList.getTasks().get(x));
                    }
                }
                model.setRowCount(0);
                Main.table(filteredList);
            }
            else {
                model.setRowCount(0);
                Main.table(newList);
            }
        });
        
        //Filtering by Completed
          filteringByComplete.addActionListener((ActionEvent e) -> {
              String selection = (String)filteringByComplete.getSelectedItem();
              List filteredList = new List();
              if(selection.equals("Completed")){
              for(Task task :newList.getTasks()) {
                  if (task.getStatus().equals("Completed")) {
                      filteredList.addTask(task);
                  }
                  model.setRowCount(0);
                  Main.table(filteredList);
              }
              }
              else if (selection.equals("Not Completed")) {
                      for(Task task :newList.getTasks()) {
                  if (!task.getStatus().equals("Completed")) {
                      filteredList.addTask(task);
                  }
                  model.setRowCount(0);
                  Main.table(filteredList);
              }
                  
              }
              else {
                  model.setRowCount(0);
                  Main.table(newList);
              }
        });
          
          
          filterPeople.addActionListener((ActionEvent e) -> {
              String who = JOptionPane.showInputDialog("Who would you like to filter by? To reset filter, leave blank.");
              if(who.isEmpty()) {
                  model.setRowCount(0);
                  Main.table(newList);
              }
              else{
                  if(newList.getTasks().size() == 0) {
                    JOptionPane.showMessageDialog(frame, "Add a task to the list before filtering");
                  }
                  else{
                     List filteredList = new List();
                      for (Task task: newList.getTasks()) {
                        if(who.equals(task.getPerson())) {
                                 filteredList.addTask(task);
                      }
                  }
                  model.setRowCount(0);
                  Main.table(filteredList);
              }
          }
              
     });
          

        //frame
        
        frame.setSize(900,650);
        frame.setMinimumSize(new Dimension(460,630));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
