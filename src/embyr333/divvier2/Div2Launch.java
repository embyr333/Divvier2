/*
Copy of Divvier project/program' Div2Launch 220706_2316 ith name changed to Div2Launch
awaiting further modification for use in Divvier2 program/project...

(First) commit date_time  220708_1054
*/

package embyr333.divvier;

class Div2Launch
{
    public static void main(String[] args) 
    {        
        Div2GUI window = new Div2GUI();
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE); 
        window.setTitle("Divvier");
        window.setSize(600, 960);
        window.setLocationRelativeTo(null);
        window.setVisible(true);        
    }
} // End class Div2Launch

/*
"To Do" for the program (written in 2015):
-- (Consider any new ideas for improved algorithm to replace Divvier_to_11_IG and Divvier_unlimited_IG)
-- (Maybe at some point make a "web" version (e.g. with JSF)? However until I have a web host 
   that can run it, such a project would be for little more than JSF-coding experience). And/or Android? 
-- (Could add an option to ignore negitive values, if I could envisage or was made aware of
   a scenario in which that would be useful or)
*/
