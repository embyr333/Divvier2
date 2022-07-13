/*
Originated a copy of Divvier project/program' Div2Launch 220706_2316 

Further changes made here:
- Package name to reflect new location
- New program/project name in window title

Commit date_time   220713_344
*/

package embyr333.divvier2; // --location in this project

class Div2Launch
{
    public static void main(String[] args) 
    {        
        Div2GUI window = new Div2GUI();
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE); 
        window.setTitle("Divvier2"); // --updated text
        window.setSize(600, 960);
        window.setLocationRelativeTo(null);
        window.setVisible(true);        
    }
} 
