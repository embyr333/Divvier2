/*
Copy of Divvier2b 220716_1721 modified to removing the intermediate map and just 
work directly with lists. I do not expect this to change the output from 2b version 
(split found for a given input), though it might change efficiency - vague thoughts 
so far: perhaps some improvement in speed and/or space use from not having the 
list>map>list conversions? perhaps less efficient if input has a long run of replicate
values (so that 'going to the next lowest value' would take longer than in a map).

Next:
Test the three Divvier2 variants (original, b and c) against each other (and 
original Divvier) for output splits for a wide range of input collections; 
especially curious to see if I ccan identify inputs for which Divvier2b and 
Divvier2c outperform, in terms of finding more equitable split than, Divvier2.
Performance in terms of time efficiency also of interest.

Commit date_time  220718_0054
 */

package embyr333.divvier2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Divvier2c
{
    static void process(List<Double> itemList) 
    {         
        itemList.sort(Comparator.reverseOrder()); // --now sorting the list directly 
        // rather than amking a sorted map from it
        
        // Calculate sum of item values in itemList (decided to do via Streams API)
//        double total = itemList.stream().reduce(0.0, (a, b) -> a + b); // ...or...
        double total = itemList.stream().reduce(0.0, Double::sum); 
//        Div2GUI.getOutputJTextArea().append("total: " + total + "\n"); // --temporary statement to check intermediate

        double half = total / 2; // Calculate half the sum of the itemList
        
        double div1 = 0; // Sum of items assigned to first of two new lists  
        // representing as-equitable-as-possible division of the original list
        
        List<Double> itemsUsed = new ArrayList<>();         

        for (int i = 0; i < itemList.size(); ++i) 
        {    
            // To store 'current-best' split data
            double tempDiv1 = 0.0;
            List<Double> tempItemsUsed = new ArrayList<>();            
            
            // Now fill tempDiv1 as near as possible to half without exceeding 
            for (int j = i; j < itemList.size() - i; ++j)
            {
                double item = itemList.get(j);
                
                if (tempDiv1 <= half - item)
                {    
                    tempDiv1 += item;        
                    tempItemsUsed.add(item);   
                }    
            }    

            // Store 'best' split so far
            if (tempDiv1 > div1)
            {
                div1 = tempDiv1;
                itemsUsed = new ArrayList<>(tempItemsUsed);  
            }    
        }

        List<Double> itemsNotUsed = new ArrayList<>(itemList); // First make a copy
        // of itemList passed as arg...as it was using Arrays.asList() it cannot be modified
        // and its is best to allow that flexibility for testing for the moment at least

        for (int i = 0; i < itemsUsed.size(); ++i)
        {
            itemsNotUsed.remove(itemsUsed.get(i));
        } // NOW itemsNotUsed is properly nnamed
        // (Alternative would be to build the reciprocal list to itemsUsed in the nested loop above)
        
        // Just for convenience of viewing, decided to arrange itemsNotUsed
        // (previously unordered, unless input data happens to be in order)
        // in ascending (strictly, non-descending) order also..
        itemsNotUsed.sort(Comparator.naturalOrder());
        
        // Also decided to also dispaly itemsUsed in ascending order
        Collections.reverse(itemsUsed);
        
        Div2GUI.getOutputJTextArea().append(String.format("Divvier2b smallest difference found: %.1f\n", (total - (2 * div1)))); 
        Div2GUI.getOutputJTextArea().append(String.format("between sub-collection            %s\n", itemsUsed)); 
        Div2GUI.getOutputJTextArea().append(String.format("(totalling  %.1f)\n", div1)); 
        Div2GUI.getOutputJTextArea().append(String.format("and reciprocal sub-collection  %s\n", itemsNotUsed)); 
        Div2GUI.getOutputJTextArea().append(String.format("(totalling  %.1f)\n", (total - div1))); 

        Div2GUI.getOutputJTextArea().append("\n\n");
    }
}

