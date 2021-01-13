
import com.dropbox.core.DbxException;
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
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
public class User {
     public static void user(DefaultTableModel model) {
         
        List newList = new List();
        
         ///////////// SETTING UP THE GUI /////////////
         
        //Sorting Combo Box
        String[] sort = {"-- No Sort --","Priority", "Date - Ascending", "Date - Descending"};
        JComboBox sorting = new JComboBox(sort);
       
        //Filtering by priority Combo Box
        String[] filterByPriority = {"-- No Filter --","1","2","3","4","5","6","7","8","9","10"};
        JComboBox filteringByPriority = new JComboBox(filterByPriority);

         //Filtering by complete Combo Box
        String[] filterByComplete = {"-- No Filter --","Completed","Not Completed"};
        JComboBox filteringByComplete = new JComboBox(filterByComplete);        
        
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
        
        //Declare Labels for comboboxes
        
        JLabel sortComboBoxLabel = new JLabel("Sort By:");
        JLabel filterByPriorityComboBoxLabel = new JLabel("Filter By Priority:");
        JLabel filterByCompleteComboBoxLabel = new JLabel("Filter By Completed:");
 
        JButton Save_Button = new JButton("Save");
        JButton Load_Button = new JButton("Load");
        
        //Set boundaries of labels

        // Declare Panels
        JScrollPane pane = new JScrollPane(table);
        JPanel bottomBit = new JPanel();

        //Layout for bottom Right
        bottomBit.setLayout(new GridLayout(4,2));

        bottomBit.add(sortComboBoxLabel);
        bottomBit.add(sorting);

        bottomBit.add(filterByPriorityComboBoxLabel);
        bottomBit.add(filteringByPriority);
        
        bottomBit.add(filterByCompleteComboBoxLabel);
        bottomBit.add(filteringByComplete);

        bottomBit.add(Save_Button);
        bottomBit.add(Load_Button);

        
        // Add panel to frame
        frame.add(pane, BorderLayout.NORTH);
        frame.add(bottomBit, BorderLayout.SOUTH);
        
        JOptionPane.showMessageDialog(frame,"Don't forget to load in your tasks for the day!");

        frame.setMinimumSize(new Dimension(400,400));

        ///////////// LOGIC FOR THE BUTTONS /////////////
                
        // Pass Row Data to TextField once mouse is clicked on that Row
        table.addMouseListener(new MouseAdapter(){
        
        @Override
        
        public void mouseClicked(MouseEvent e){
            
            // In case there is an event set i variable as current Row
            int i = table.getSelectedRow();
             if(e.getClickCount()==2){
              newList.getTasks().get(i).subtaskTable(i,newList.getTasks().get(i));
             }
            
        }
        });
        
       
        // Save_Button
        Save_Button.addActionListener(new ActionListener(){
            
            @Override
            
            public void actionPerformed(ActionEvent ae) {
                
              
                int  count = 0;
                String file_name_full = "";
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
                String file_name_full = "";
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
        
        
        
                
       
          //Sort by priority Function
        
        sorting.addActionListener((ActionEvent e) -> {
            String current = (String)sorting.getSelectedItem();   
                   if(current.equals("Assignee")) {
                newList.sortByAssignee();
                model.setRowCount(0);
                Main.table(newList);
            }
              
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
          
        //frame
        
        frame.setSize(900,560);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
