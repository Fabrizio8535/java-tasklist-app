/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */

import com.dropbox.core.DbxException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Main {
   
   static DefaultTableModel model = new DefaultTableModel();

          public static void table(List list){
            int m = 0;
               Object[] Table_Row = new Object[5];
              
             while(m <= list.getTasks().size()-1){
                Table_Row[0] = list.getTasks().get(m).getTaskTitle();
                Table_Row[1] = list.getTasks().get(m).getPerson();
                Table_Row[2] = list.getTasks().get(m).getPriority();
                Table_Row[3] = list.getTasks().get(m).getDueDate().toString();
                Table_Row[4] = list.getTasks().get(m).getStatus();
                m++;
                // Add Row to Table Model
                model.addRow(Table_Row);
             }
          }        
          
    public static void main (String[] args) {
        
        
        
        JFrame mainDisplay = new JFrame("User or Admin?");
        mainDisplay.setLayout(new BorderLayout());
        
        
        JButton admin = new JButton("Admin");
        JButton user = new JButton("User");
   
        
        mainDisplay.add(admin, BorderLayout.WEST);
        mainDisplay.add(user, BorderLayout.EAST);
    
        admin.addActionListener((ActionEvent e) -> {
            Admin.admin(model);
            mainDisplay.setVisible(false);
        });
           
        user.addActionListener((ActionEvent e) -> {
            User.user(model);
            mainDisplay.setVisible(false);
        });
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - mainDisplay.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - mainDisplay.getHeight()) / 2);
        mainDisplay.setLocation(x, y);
        
        mainDisplay.setSize(170,100);
        mainDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainDisplay.setVisible(true);
   
    }

}
        