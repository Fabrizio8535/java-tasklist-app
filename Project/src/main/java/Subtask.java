/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HarrySmith
 */
public class Subtask {

  private int subtask_number;
  private String subtask_description;


    public Subtask( int subtask_number, String subtask_description) {
        this.subtask_number = subtask_number;
        this.subtask_description = subtask_description;
        
    }
       public String getSubtask_description() {
        return subtask_description;
    }

    public void setSubtask_description(String subtask_description) {
        this.subtask_description = subtask_description;
    }

    public int getSubtask_number() {
        return subtask_number;
    }

    public void setSubtask_number(int subtask_number) {
        this.subtask_number = subtask_number;
    }
    
    @Override
    public String toString(){
       
        return "" + subtask_description + ". " + subtask_number;
    }
    
    
    
}
