/*
Thi sclas originated as copy of Divvier project/program' Div2GUI 220706_2316  
to Div2GUI; in process of modification for use in Divvier2 program/project... 

Further changes made here:
- Package rename to reflect new location
- Removed turboJButton and associated items (loops variable, getLoops() 
pertaining to use of Divvier' DivFor1plu class
- Removed use Divvier program 4-, 5-, 6-11 and plus -procesing clases' (files not 
present) process() methods, calling intead Divvier2's process() (will work in Divvier2b 
and any subsequent later; alo might or might not restore use of the 4- and 5- classes)
- (Changed Divvier2, Divvier2b clases to print to GUI rather than console)
- Removed the 'at least 4 items' stipulation (not esential to remind user that 
dividing smaller collections i strivial/irrelevant!), and might be ueful to
allo wproceing of 2 or 3 item collection sin further teting)

To-do' sinclude:
- Remove the JTextFields on left and aociated toggle with use of input JTetArea
(Used the field set in earlier development of original Divvier incarnation,
but it's easier to enter collection in single text area. After adding the latter,
kept the former more as an excuse to setup an automatic toggle between use of
one and the other (entering in firt diable second and vice-verse, for fun.)
- Update infoButton message, or remove button/popup
- Update any labels /other diplayed tet a needed
- Make sure can see both Divvier2 and Divvier2b outputs when have removed unneeded 
stuff from window, modifying organiation of window as needed

Commit date_time   220713_344
*/

package embyr333.divvier2; // --location in this project

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Div2GUI extends JFrame
{
    private JTextArea instructionsJTextArea;
    
    private JButton infoJButton;
    
    private JLabel numColJLabel; // Label above column of 11 JTextFields for input
    private JLabel[] numberJLabels = new JLabel[11]; // Labels to left of individual JTextFields 
    private JTextField[] numberJTextFields = new JTextField[11]; // Array of 11 JTextFields for input   
    
    private JLabel allnumbersJLabel; // label above...
    private JTextArea allnumbersJTextArea; // JTextArea that acts as an alternative input interface
    private JScrollPane allnumbersJScrollPane; // ...scrollpane for above    
        
//    private JButton turboJButton; // Toggles value of 'loops' by 10x, 100x (then back to defailt)

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
                + "Enter the numbers you want to divvy..."); // --removed the "at least 4" intruction
        instructionsJTextArea.setBounds(20, 40, 500, 80);
        instructionsJTextArea.setEditable(false);
        instructionsJTextArea.setOpaque(false);
        add(instructionsJTextArea);          
        
        numColJLabel = new JLabel("...here");
        numColJLabel.setBounds(95, 130, 50, 20);
        add(numColJLabel); 
        
        // Inactivates the JTextFields if anything is entered in the JTextArea
        //    and reactivates them if emptied, and vice-verse
        InputChoiceHandler inputChoiceHandler = new InputChoiceHandler();        
        
        // Create input JTextFields and their labels, register with inputChoiceHandler
        for(int i = 0; i < 11; i++)
        {    
            numberJLabels[i] = new JLabel("Number " + (i + 1));
            numberJLabels[i].setBounds(20, 160 + i*40, 80, 20);
            add(numberJLabels[i]);
        }    
        for(int i = 0; i < 11; i++)
        {    
            numberJTextFields[i] = new JTextField();
            numberJTextFields[i].setBounds(95, 160 + i*40, 100, 20);            
            numberJTextFields[i].getDocument().addDocumentListener(inputChoiceHandler);
            add(numberJTextFields[i]);
        }          
        
        // Create scrollable input JTextArea and its label, register with inputChoiceHandler
        allnumbersJLabel = new JLabel("...or here, seperated by spaces / tabs / returns");
        allnumbersJLabel.setBounds(260, 130, 300, 20);
        add(allnumbersJLabel);         
        allnumbersJTextArea = new JTextArea(10, 20); // could clarify significance of these row,col parameters & whether want to alter
        allnumbersJTextArea.setLineWrap(true);
        allnumbersJTextArea.setWrapStyleWord(true); 
        allnumbersJTextArea.getDocument().addDocumentListener(inputChoiceHandler);
        allnumbersJScrollPane = new JScrollPane(allnumbersJTextArea);
        allnumbersJScrollPane.setBounds(260, 160, 300, 100);
        add(allnumbersJScrollPane);        
        
        infoJButton = new JButton("What is this nonsense?");
        infoJButton.setBounds(320, 320, 180, 20);
        infoJButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {                    
                    JOptionPane.showMessageDialog(Div2GUI.this /* or: rootPane */, 
                        "<html><p style='width:380px; font-weight:normal'><br>"
                                + "Ever needed to divide a small number of items "
                                + "(e.g. pieces of fruit of random weight) into two collections, "
                                + "as evenly as possible? In the case of 3 items, the answer is, "
                                + "of course, always to divide as “biggest vs. other two”. <br><br> "
                                + "Here is a small tool to take the guesswork, or tedious comparison "
                                + "of the various combinations, out of the situation for larger collections. <br><br>"
                                + "Over 5 items, there is a random component to the algorithms used "
                                + "(not all combinations may be sampled), however the process for "
                                + "6-11 is quite reliable in identifying the best split. "
                                + "The process used for 12 or more items is not guarenteed to do so, "                                
                                + "though the 'turbo' button can be used to increase the reliability, "                                
                                + "at the expense of speed - it cycles between 1x, 10x and 100x the default "
                                + "number of times that the processing algorithm loops for collections of >11. " 
                                + "<br><br></p></HTML>", 
                            "What this nonsense is", JOptionPane.INFORMATION_MESSAGE); 
                }
            }
        );          
        add(infoJButton);         
                
        clearJButton = new JButton("Clear");
        clearJButton.setBounds(360, 460, 100, 20);
        clearJButton.addActionListener(
            new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {                                        
                    for(int i = 0; i < 11; i++)
                        numberJTextFields[i].setText("");
         
                    allnumbersJTextArea.setText("");

                    outputJTextArea.setText("");                                
                }
            }
        );          
        add(clearJButton);          
        
        // Create the submitJButton with anonymous event handler
        submitJButton = new JButton("Submit");
        submitJButton.setBounds(320, 540, 180, 40);
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
                    // otherwise extract entries in JTextField 
                    else
                        try
                        {
                            for(int i = 0; i < numberJTextFields.length; i++)
                                if(!numberJTextFields[i].getText().equals(""))
                                {    
                                    value = Double.parseDouble(numberJTextFields[i].getText());
                                    
                                    if(ignoreZeros == false)
                                        numbers.add(value);
                            
                                    else if(value != 0)
                                        numbers.add(value);    
                                }
                                ignoreZeros = false; // reset to default behaviour
                        }
                        catch(NumberFormatException numberFormatException)
                        {
                            JOptionPane.showMessageDialog(Div2GUI.this, "Please enter only numbers",
                                    "Invalid Input...", JOptionPane.INFORMATION_MESSAGE);  
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
        outputJTextArea.setBounds(20, 620, 560, 300);
        outputJTextArea.setLineWrap(true);
        outputJTextArea.setWrapStyleWord(true); 
        outputJTextArea.setEditable(false);
        outputJTextArea.setOpaque(false);
        add(outputJTextArea);         
    } // End constructor
    
    private class InputChoiceHandler implements DocumentListener
    {
        public void insertUpdate(DocumentEvent e) 
        {
            if( !numberJTextFields[0].getText().equals("") ||
                !numberJTextFields[1].getText().equals("") ||
                !numberJTextFields[2].getText().equals("") ||
                !numberJTextFields[3].getText().equals("") ||
                !numberJTextFields[4].getText().equals("") ||
                !numberJTextFields[5].getText().equals("") ||
                !numberJTextFields[6].getText().equals("") ||
                !numberJTextFields[7].getText().equals("") ||
                !numberJTextFields[8].getText().equals("") ||
                !numberJTextFields[9].getText().equals("") ||
                !numberJTextFields[10].getText().equals("") 
               )
                { 
                    allnumbersJTextArea.setEditable(false);
                    allnumbersJTextArea.setOpaque(false);
                    // any way to make the border grey, as for disabled JTestField?
                }    
            // ...this is not elegant but saves on processing cf checking in a loop of straight 'if' statements
            
            if(!allnumbersJTextArea.getText().equals(""))
               for(int i = 0; i < 11; i++)
                {    
                    numberJTextFields[i].setEditable(false);
                }                       
            // ...this works fine, but is there way to avoid the wasted computation involved in calling
            //    it every time there is a new character inserted in the TextArea (after the first)?...
        } // End method insertUpdate

        public void removeUpdate(DocumentEvent e) 
        {
            if( numberJTextFields[0].getText().equals("") &&
                numberJTextFields[1].getText().equals("") &&
                numberJTextFields[2].getText().equals("") &&
                numberJTextFields[3].getText().equals("") &&
                numberJTextFields[4].getText().equals("") &&
                numberJTextFields[5].getText().equals("") &&
                numberJTextFields[6].getText().equals("") &&
                numberJTextFields[7].getText().equals("") &&
                numberJTextFields[8].getText().equals("") &&
                numberJTextFields[9].getText().equals("") &&
                numberJTextFields[10].getText().equals("") 
               )
                { 
                    allnumbersJTextArea.setEditable(true);
                    allnumbersJTextArea.setOpaque(true);
                }               
                                    
            if (allnumbersJTextArea.getText().equals(""))
            {
                for(int i = 0; i < 11; i++)
                {    
                    numberJTextFields[i].setEditable(true);
                }                    
            }
        } // End method removeUpdate              

        public void changedUpdate(DocumentEvent e) { }                

    } // End inner class inputChoiceHandler
    
} // End class Div2GUI