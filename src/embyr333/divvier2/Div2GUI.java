/*
This class originated as copy of Divvier project/program' Div2GUI 220706_2316  
to Div2GUI; in process of modification for use in Divvier2 program/project... 

Changes made here:
- Tranferred the last statement from the Divvier2/Divvier2b classes to run just 
once after the process() calls for those classe here
- Changed variable name allNumberJTextArea to inputJTextArea, 
and allNumberJScrollPane to inputJScrollPane
- Expanded output area down to near frame bottom
- Updated decriptions in instructionsJTextArea and infoButton message

To-do's include:
- Updated decriptions in instructionsJTextArea and infoButton message

Commit date_time  220716_1721
*/

package embyr333.divvier2; 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Div2GUI extends JFrame
{
    private JTextArea instructionsJTextArea;
    
    private JButton infoJButton;
    
    private JTextArea inputJTextArea; // JTextArea for user input
    private JScrollPane inputJScrollPane; // ...scrollpane for above    
        
    private JButton clearJButton; // Clears input/outputJTextArea fields/areas 

    private JButton submitJButton; // Submits input data for number-extraction (this class; --> processing in other classs)
    
    private static JTextArea outputJTextArea; // Recieves reports from the processing classes

    private ArrayList<Double> numbers = new ArrayList<Double>(); // Stores the input numbers before transfer to processing classes
    
    private boolean ignoreZeros; // For option to ignore any zeros in input (Can alternatively be static - does not seem to make a difference)
    
    public static JTextArea getOutputJTextArea()
    {
        return outputJTextArea;   
    }
    
    // Constructor
    public Div2GUI()
    {
        setLayout(null);
        
        instructionsJTextArea = new JTextArea("Aiming to divide a collection " // --provisional update of description
                + "of numbers into two subcollections as evenly as possible.\n"
                + "This version of 'Divvier', in progress, is a place where I'm trying out alternatives to the\n"
                + "approaches in my original program.\n\n"
                + "Enter the numbers you want to divvy, seperated by spaces / tabs / returns"); 
        instructionsJTextArea.setBounds(20, 20, 530, 80); // --height altered
        instructionsJTextArea.setEditable(false);
        instructionsJTextArea.setOpaque(false);
        add(instructionsJTextArea);          
           
        inputJTextArea = new JTextArea(10, 20); 
        inputJTextArea.setLineWrap(true);
        inputJTextArea.setWrapStyleWord(true); 
        inputJScrollPane = new JScrollPane(inputJTextArea);
        inputJScrollPane.setBounds(20, 100, 540, 100); 
        add(inputJScrollPane);        
        
        infoJButton = new JButton("What is this nonsense?");
        infoJButton.setBounds(200, 230, 180, 20); 
        infoJButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {                    
                    JOptionPane.showMessageDialog(Div2GUI.this /* or: rootPane */, 
                        "<html><p style='width:380px; font-weight:normal'><br>"
                                + "On doing CodingBat's MakeBricks problem, I wondered if "
                                + "I could use an approach similar to that of the (very simple) "
                                + "solution I made there to make a definitive (non-random, "
                                + "guaranteed minimal difference for unlimited collection size) "
                                + "alternative to my Divvier program (see http://embyrne.c1.biz/ "
                                + "and https://github.com/embyr333/Divvier). <br><br>"
                                + "Unfortunately, this is not quite working out so far, "
                                + "though the Divvier2 programs do get an exact split for "
                                + "some large input collections quicker than Divvier; the latter, "
                                + "conversely, finds better splits for some large inputs, "
                                + "but may have to be run a few times to get those as its ‘over-11’ "
                                + "algorithm operates by random sampling." 
                                + "<br><br></p></HTML>", 
                            "What this nonsense is", JOptionPane.INFORMATION_MESSAGE); 
                }
            }
        );          
        add(infoJButton);         
                
        clearJButton = new JButton("Clear");
        clearJButton.setBounds(240, 280, 100, 20); 
        clearJButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {                                        
                    inputJTextArea.setText("");
                    outputJTextArea.setText("");                                
                }
            }
        );          
        add(clearJButton);          
        
        // Create the submitJButton with anonymous event handler
        submitJButton = new JButton("Submit");
        submitJButton.setBounds(200, 330, 180, 40);
        submitJButton.addActionListener(new ActionListener() 
            { 
                public void actionPerformed(ActionEvent e) 
                {                    
                    numbers.clear();

                    double value = 0;
                    
                    // If there is input in the JTextArea, extract that into 'numbers' ArrayList for processing
                    if (!inputJTextArea.getText().equals(""))
                    {   
                        String[] alltogether = inputJTextArea.getText().trim().split("\\s+");
                 
                        try
                        {
                            for(int i = 0; i < alltogether.length; i++)
                            {                    
                                value = Double.parseDouble(alltogether[i]);
                                
                                if(ignoreZeros == false)
                                    numbers.add(value);    

                                else if(value != 0)
                                    numbers.add(value);    
                            }    
                            ignoreZeros = false; // Reset to default behaviour                            
                        }
                        catch(NumberFormatException numberFormatException)
                        {
                            JOptionPane.showMessageDialog(Div2GUI.this, "Please enter only numbers",
                                    "Invalid Input...", JOptionPane.INFORMATION_MESSAGE);                            
                        }
                    }  
                    
                    // Preserve integrity of 'numbers' by making a 'deep' copy to send to procession classes 
                    // (_to_11, at least, alters the ArrayList during calculations
                    ArrayList<Double> numbersCopy = new ArrayList<Double>();
                    for(int i = 0; i < numbers.size(); i++)
                        numbersCopy.add(numbers.get(i));

                    // Send data to the processing classes --now just using the new approach(e)
                    Divvier2.process(numbersCopy);                         
                    Divvier2b.process(numbersCopy);      
                    
                    
                    // --moved from copie in Divvier2 and Divvier2b classes
                    getOutputJTextArea().append("(However there may be other combinations that give the same or more \n"
                            + "equitable split which could be searched for with the originl 'Divvier' program,\n"
                            + "which uses random sampling for input collections of >5 items)");         
                    
                    // --temporary print lines for testing area depth
//                    getOutputJTextArea().append("\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12"); 
                            

                    // Popup if there are values of zero in the input number collection 
                    for(int i = 0; i < numbers.size(); i++)
                    {          
                        if(numbers.get(i) == 0)
                        {                
                            int response = JOptionPane.showConfirmDialog(null, 
                                    "Your input number collection included zeros.\nDo you want to repeat the process ignoring the zeros,\n"
                                            + "to ensure the best alogrithm is applied for the remaining number-collection\n"
                                            + "size and to give a cleaner display of the subcollections in the outputJTextArea?\n",
                                    "Zeros", JOptionPane.YES_NO_OPTION);                   

                            if (response == JOptionPane.YES_OPTION) 
                            {
                                ignoreZeros = true;
                                submitJButton.doClick();
                            }
                            break;
                        }    
                    } // End popup block
                } // End method actionPerformed  for submitJButton
            } // End ActionListener for submitJButton
        ); // Closing parenthesis for argument of method addActionListener for submitJButton
        add(submitJButton);      
        
        outputJTextArea = new JTextArea();
//        outputJTextArea.setBounds(20, 400, 560, 300); 
        outputJTextArea.setBounds(20, 400, 560, 490); // --made deeper (lower boundary a few mm from frame boundary)
        outputJTextArea.setLineWrap(true);
        outputJTextArea.setWrapStyleWord(true); 
        outputJTextArea.setEditable(false);
        outputJTextArea.setOpaque(false); 
        add(outputJTextArea);         
    } // End constructor

} // End class Div2GUI