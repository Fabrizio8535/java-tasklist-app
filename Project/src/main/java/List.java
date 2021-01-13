/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */

import java.util.ArrayList;

public class List {

    private ArrayList<Task> tasks= new ArrayList<>();
    
    
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    
    public void addTask(Task instance) {
        tasks.add(instance);
    }
    
    public void deleteTask(int taskNumber){
        tasks.remove(taskNumber);
    }
    
    public void sortByDateAscending() {
        
 //THIS SORTS THE DAY      
          for(int outerDay = 0; outerDay< tasks.size(); outerDay ++) {
                for(int innerDay = 0; innerDay< tasks.size(); innerDay ++) {
                        try {
                         if(tasks.get(innerDay).getDueDate().getDay() > tasks.get(innerDay+1).getDueDate().getDay()) {
                             Task temp = tasks.get(innerDay);
                            tasks.set(innerDay, tasks.get(innerDay+1));
                            tasks.set(innerDay+1, temp);
                         }
                         } 
                     catch(IndexOutOfBoundsException n){
                         
                    }   
            
        }
            
        }
            

  //THIS SORTS THE MONTH AFTER THE DAY HAS BEEN SORTED
          for(int outerMonth = 0; outerMonth< tasks.size(); outerMonth ++) {
                for(int innerMonth = 0; innerMonth< tasks.size(); innerMonth ++) {
                        try {
                         if(tasks.get(innerMonth).getDueDate().getMonth() > tasks.get(innerMonth+1).getDueDate().getMonth()) {
                             Task temp = tasks.get(innerMonth);
                            tasks.set(innerMonth, tasks.get(innerMonth+1));
                            tasks.set(innerMonth+1, temp);
                         }
                         } 
                     catch(IndexOutOfBoundsException n){
                         
                    }   
            
        }
            
        }
          
 //THIS SORTS THE YEAR AFTER THE MONTH HAS BEEN SORTED   
          for(int outerYear = 0; outerYear< tasks.size();outerYear++){
                for(int innerYear = 0; innerYear <tasks.size(); innerYear ++) {
                     try {
                         if(tasks.get(innerYear).getDueDate().getYear() > tasks.get(innerYear+1).getDueDate().getYear()){
                            Task temp = tasks.get(innerYear);
                            tasks.set(innerYear, tasks.get(innerYear+1));
                            tasks.set(innerYear+1, temp);
                            }
                         } 
                     catch(IndexOutOfBoundsException n){
                         
                    }        
                }
            }
 
    }
    
        public void sortByDateDescending() {
            
//THIS SORTS THE DAY      
          for(int outerDay = 0; outerDay< tasks.size(); outerDay ++) {
                for(int innerDay = 0; innerDay< tasks.size(); innerDay ++) {
                        try {
                         if(tasks.get(innerDay).getDueDate().getDay() < tasks.get(innerDay+1).getDueDate().getDay()) {
                             Task temp = tasks.get(innerDay);
                            tasks.set(innerDay, tasks.get(innerDay+1));
                            tasks.set(innerDay+1, temp);
                         }
                         } 
                     catch(IndexOutOfBoundsException n){
                         
                    }   
            
        }
            
        }
            

  //THIS SORTS THE MONTH AFTER THE DAY HAS BEEN SORTED
          for(int outerMonth = 0; outerMonth< tasks.size(); outerMonth ++) {
                for(int innerMonth = 0; innerMonth< tasks.size(); innerMonth ++) {
                        try {
                         if(tasks.get(innerMonth).getDueDate().getMonth() < tasks.get(innerMonth+1).getDueDate().getMonth()) {
                             Task temp = tasks.get(innerMonth);
                            tasks.set(innerMonth, tasks.get(innerMonth+1));
                            tasks.set(innerMonth+1, temp);
                         }
                         } 
                     catch(IndexOutOfBoundsException n){
                         
                    }   
            
        }
            
        }
          
 //THIS SORTS THE YEAR AFTER THE MONTH HAS BEEN SORTED   
          for(int outerYear = 0; outerYear< tasks.size();outerYear++){
                for(int innerYear = 0; innerYear <tasks.size(); innerYear ++) {
                     try {
                         if(tasks.get(innerYear).getDueDate().getYear() < tasks.get(innerYear+1).getDueDate().getYear()){
                            Task temp = tasks.get(innerYear);
                            tasks.set(innerYear, tasks.get(innerYear+1));
                            tasks.set(innerYear+1, temp);
                            }
                         } 
                     catch(IndexOutOfBoundsException n){
                         
                    }        
                }
            }
        }
        
        public void sortByAssignee(){
            for (int outer = 0; outer< tasks.size();outer++) {
                for (int inner = outer; inner<tasks.size();inner ++) {
                    try{
                        if (tasks.get(inner).getPerson().compareTo(tasks.get(inner+1).getPerson())>0){
                            Task temp = tasks.get(inner);
                            tasks.set(inner, tasks.get(inner+1));
                            tasks.set(inner+1, temp);
                        }
                      }
                      catch(IndexOutOfBoundsException n){
                         
                    }                            
                }
            }
        }
     

}
