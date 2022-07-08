/*
Copy of Divvier project/program' Div2GUI 220706_2316 ith name changed to Div2GUI
awaiting further modification for use in Divvier2 program/project...

(First) commit date_time  220708_1054
*/

package embyr333.divvier;

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
    
    private JLabel numColJLabel; // label above column of 11 JTextFields for input
    private JLabel[] numberJLabels = new JLabel[11]; // labels to left of individual JTextFields 
    private JTextField[] numberJTextFields = new JTextField[11]; // array of 11 JTextFields for input   
    
    private JLabel allnumbersJLabel; // label above...
    private JTextArea allnumbersJTextArea; // JTextArea that acts as an alternative input interface
    private JScrollPane allnumbersJScrollPane; // ...scrollpane for above    
        
    private JButton turboJButton; // toggles value of 'loops' by 10x, 100x (then back to defailt)

    private JButton clearJButton; // clears input/outputJTextArea fields/areas 

    private JButton submitJButton; // submits input data for number-extraction (this class; --> processing in other classs)
    
    private static JTextArea outputJTextArea; // recieves reports from the processing classes

    private ArrayList<Double> numbers = new ArrayList<Double>(); // stores the input numbers before transfer to processing classes
    
    private boolean ignoreZeros; // for option to ignore any zeros in input (Can alternatively be static - does not seem to make a difference)
    
    private static int loops = 1000; // number of loops to be run by DivFor12plus processing algorithm     
    public static int getLoops(){
        return loops;
    }
    
    public static JTextArea getOutputJTextArea()
    {
        return outputJTextArea;   
    }
    
    // constructor
    public Div2GUI()
    {
        setLayout(null);
        
        instructionsJTextArea = new JTextArea("Allows you to divide a collection of numbers into two subcollections as evenly as possible\n\n"
                + "Enter the numbers (at least four) you want to divvy...");
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
        
        turboJButton = new JButton("Click for 10x turbo");
        turboJButton.setBounds(320, 400, 180, 20);
        turboJButton.addActionListener(
            new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {               
                    if(loops == 1000)
                    {    
                        loops = 10000;
                        turboJButton.setText("Click for 100x turbo");
                    }    
                    else if (loops == 10000)
                    {    
                        loops = 100000;
                        turboJButton.setText("Click for default");
                    }                            
                    else
                    {    
                        loops = 1000;
                        turboJButton.setText("Click for 10x turbo");
                    }                         
                }
            }
        );          
        add(turboJButton);          
        
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
                    
                    // Send data to the processing classes
                    if(numbersCopy.size() < 4) 
                        outputJTextArea.setText(String.format("Sorry, this program is not relevant for collections of less than four numbers\n")); 
                    if(numbersCopy.size() == 4)
                        DivFor4.process(numbersCopy.get(0), numbersCopy.get(1), numbersCopy.get(2), numbersCopy.get(3));
                    if(numbersCopy.size() == 5)
                        DivFor5.process(numbersCopy.get(0), numbersCopy.get(1), numbersCopy.get(2), numbersCopy.get(3), numbersCopy.get(4));
                    if(numbersCopy.size() > 5 && numbersCopy.size() < 12)
                        DivFor6to11.process(numbersCopy);
                    if(numbersCopy.size() >= 12)
                        DivFor12plus.process(numbersCopy);         
                    
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