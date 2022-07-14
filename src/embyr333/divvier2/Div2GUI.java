/*
This class originated as copy of Divvier project/program' Div2GUI 220706_2316  
to Div2GUI; in process of modification for use in Divvier2 program/project... 

Further changes made here:
- Removed the JTextFields on left and associated toggle with use of input JTetArea
(Used the field set in earlier development of original Divvier incarnation,
but it's easier to enter collection in single text area. After adding the latter,
kept the former more as an excuse to setup an automatic toggle between use of
one and the other (entering in first disable second and vice-verse, for fun.)
- Changed position of components (and idened input textarea) to make ue of vacated space
- Removed unneeded labels

To-do's include:
- Update infoButton message, or remove button/popup and put decription in instructionsJTextArea

Commit date_time   220715_0031
*/

package embyr333.divvier2; 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Div2GUI extends JFrame
{
    private JTextArea instructionsJTextArea;
    
    private JButton infoJButton;
    
    private JTextArea allnumbersJTextArea; // JTextArea for user input
    private JScrollPane allnumbersJScrollPane; // ...scrollpane for above    
        
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
        
        instructionsJTextArea = new JTextArea("Allows you to divide a collection "
                + "of numbers into two subcollections as evenly as possible\n\n"
                + "Enter the numbers you want to divvy, seperated by spaces / tabs / returns"); // --altered
        instructionsJTextArea.setBounds(20, 20, 500, 80); // --poition, dimensions altered
        instructionsJTextArea.setEditable(false);
        instructionsJTextArea.setOpaque(false);
        add(instructionsJTextArea);          
           
        allnumbersJTextArea = new JTextArea(10, 20); // could clarify significance of these row,col parameters & whether want to alter
        allnumbersJTextArea.setLineWrap(true);
        allnumbersJTextArea.setWrapStyleWord(true); 
        allnumbersJScrollPane = new JScrollPane(allnumbersJTextArea);
        allnumbersJScrollPane.setBounds(20, 100, 540, 100); // --poition, dimensions altered
        add(allnumbersJScrollPane);        
        
        infoJButton = new JButton("What is this nonsense?");
        infoJButton.setBounds(200, 230, 180, 20); // --poition, dimensions altered
        infoJButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {                    
                    JOptionPane.showMessageDialog(Div2GUI.this /* or: rootPane */, 
                        "<html><p style='width:380px; font-weight:normal'><br>"
                                + "***updated decription to be added*** " // --placeholder text for updating
                                + "<br><br></p></HTML>", 
                            "What this nonsense is", JOptionPane.INFORMATION_MESSAGE); 
                }
            }
        );          
        add(infoJButton);         
                
        clearJButton = new JButton("Clear");
        clearJButton.setBounds(240, 280, 100, 20); // --poition, dimensions altered
        clearJButton.addActionListener(
            new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {                                        
                    allnumbersJTextArea.setText("");
                    outputJTextArea.setText("");                                
                }
            }
        );          
        add(clearJButton);          
        
        // Create the submitJButton with anonymous event handler
        submitJButton = new JButton("Submit");
        submitJButton.setBounds(200, 330, 180, 40); // --poition, dimensions altered
        submitJButton.addActionListener(new ActionListener() 
            { 
                public void actionPerformed(ActionEvent e) 
                {                    
                    numbers.clear();

                    double value = 0;
                    
                    // If there is input in the JTextArea, extract that into 'numbers' ArrayList for processing
                    if (!allnumbersJTextArea.getText().equals(""))
                    {   
                        String[] alltogether = allnumbersJTextArea.getText().trim().split("\\s+");
                 
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
        outputJTextArea.setBounds(20, 400, 560, 300); // --poition, dimensions altered
        outputJTextArea.setLineWrap(true);
        outputJTextArea.setWrapStyleWord(true); 
        outputJTextArea.setEditable(false);
        outputJTextArea.setOpaque(false);
        add(outputJTextArea);         
    } // End constructor

} // End class Div2GUI