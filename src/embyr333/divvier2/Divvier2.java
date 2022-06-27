/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Did here: 
Unfortunately, I have realised that the switch to use of the words 'item(s)' asmain   
component of many variable names, while good for generality, does not promote clarity,
as there are at least two numerical aspects to the data being processed: the values 
of the numbers in the input list, and the item of occurrances of each value.
Therefore I decided to switch again, relacing the word 'item' that describes the 
list elements etc. with the word 'item'.

Next: 
- Convert the content of the console prints to match those for GUI display in
the original Divvier program
- Then the class to replace the Divvier_to_11_IG and Divvier_unlimited_IG classes used there

Seventh commit, at date_time  220627_1319
 */

package embyr333.divvier2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

class Divvier2
{
    public static void main(String[] args)
    {
        // --Comments after these calls now show the expected difference 
        // between the two splits
        process(Arrays.asList(1.0, 1.0, 1.0, 5.0)); // 2.0
        process(Arrays.asList(1.0, 1.0, 1.0, 5.0, 5.0)); // 1.0
        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 0.0)); // 0.0
        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 1.0
        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 1.0
        process(Arrays.asList(1.0, 1.0, 1.0, 5.0, 2.0, 2.0, 2.0)); // 0.0
        process(Arrays.asList(7.0, 5.0, 2.0, 2.0, 2.0)); // 0.0

    }    

    static void process(List<Double> itemList) // --changed method name name 'makeBricks' to 'process'
    { // ...and also the word 'numbers' to 'item' in parameter name

        Set<Double> itemSet = new HashSet(); // --changed variable name 'numberSet' to 'itemSet'
        itemSet.addAll(itemList);

        Map<Double, Integer> bigToSmallItemCounts = new TreeMap<>(Comparator.reverseOrder());
        // --changed variable name 'bigToSmallNumberCounts' to 'bigToSmallItemCounts'
        
        for(Double item : itemSet) // --changed variable name 'item' to 'item'
            bigToSmallItemCounts.put(item, 0); // Initialize with each item count at zero     
        
        // --changed variable name from 'totalNumbersLength' to just 'total'
        double total = 0; // To hold sum of itemList (input list item values)
        
        // Fill the map by iterating over the list, and also sum the itemList
        for(Double item : itemList) // --changed variable name 'number' to 'item'
        { 
            // Count by assigning item sizes to occurances 
            bigToSmallItemCounts.replace(item, bigToSmallItemCounts.get(item) + 1);    
            
            // Also calculate the total input item lenght if arranged in a line
            total += item;
        }    
        System.out.println(bigToSmallItemCounts); // (intermediate check)
        System.out.println("total " + total); // (intermediate check)

        // Calculate half the sum of the itemList
        double half = total / 2; // --changed variable name from 'halfNumbersLength' to just 'half'
        System.out.println("half " + half); // (intermediate check)

        // (Have a feeling there might be better Map methods to use? and/or could 
        // apply stream approaches, but will keep this approach for the moment...)
        
        // --changed variable name from '' to 'div1', 
        double div1 = 0; // --changed variable name from 'numbersLine' to 'div1'
        // to represent sum of items assigned to first of two new lists representing 
        // as-equitable-as-possible division of the original list

        List<Double> itemsUsed = new ArrayList<>(); // --changed variable name from 'numbersUsed' to 'itemsUsed'
        
        // Now fill div1 as near as possible to half without exceeding 
        for (Double item : bigToSmallItemCounts.keySet()) // --changed variable name 'number' to 'item'
        {            
            for (int i = 0; i < bigToSmallItemCounts.get(item); ++i)
            {
                if (div1 <= half - item)
                {    
                    div1 += item;
                    itemsUsed.add(item);                     
                }    
                else // (Not essential, but more efficient to include)
                    break;
            }    
        }          
        System.out.println("div1 " + div1); // (intermediate check)        

        // --changed variable name from 'numbersNotUsed' to 'itemsNotUsed'...
        List<Double> itemsNotUsed = new ArrayList<>(itemList); // First make a copy
        // of itemList passed as arg...as it was using Arrays.asList() it cannot be modified
        // and its is best to allow that flexibility for testing for the moment at least

        for (int i = 0; i < itemsUsed.size(); ++i)
        {
            itemsNotUsed.remove(itemsUsed.get(i));
        } // NOW itemsNotUsed is properly nnamed
        // (Alternative would be to build the reciprocal list to itemsUsed in 
        // the nested loop above if the else...break statement is removed)
  
        System.out.println("Smallest difference from half is: " + (half - div1));
        System.out.println("Items used: " + itemsUsed); 
        System.out.println("...total length: " + div1);
        System.out.println("Items not used: " + itemsNotUsed); 
        System.out.println("...total length: " + (total - div1)); 
        System.out.println("Difference between itemsUsed and itemssNotUsed: " + (total - (2 * div1)));
        System.out.println("");
        
    }
}
