/*
Objective and proposed approach for the Divvier2 program/project a a whole: 
See comment atop first commit (220619_1442) of the Divvier2 clas/file

Changes made here:
- Removed the last statement for the GUI output diplay tetarea (tranferred to
Div2GUI to appear just once, after main outputs from this class and Divvier2b)
- Output wording tweak

Commit date_time  220716_1721
 */

package embyr333.divvier2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class Divvier2
{   
    static void process(List<Double> itemList) 
    { 
        double total = 0; // To hold sum of itemList (input list item numerical values)   
        
        Map<Double, Integer> bigToSmallItemCounts = new TreeMap<>(Comparator.reverseOrder()); 
        
        // Fill the map by iterating over the list, and also sum the itemList
        for(Double item : itemList) 
        { 
            // Count by assigning item sizes to occurances 
            bigToSmallItemCounts.put(item, 
                    bigToSmallItemCounts.containsKey(item) ?
                    bigToSmallItemCounts.get(item) + 1 : 
                    1);
            
            // Also calculate the total of input items
            total += item;
        }    
        
        double half = total / 2; // Calculate half the sum of the itemList
        
        double div1 = 0; // Sum of items assigned to first of two new lists  
        // representing as-equitable-as-possible division of the original list

        // (Have a feeling there might be better Map methods to use below? and/or could 
        // apply stream approaches, but will keep this approach for the moment...)
        
        List<Double> itemsUsed = new ArrayList<>();         

        // (Maybe rethink later how many times it is necessary for the outer loop to iterate below)

        int outerLimit = bigToSmallItemCounts.size();         
        
        for (int a = 0; a < outerLimit; ++a) 
        {    
            // To store 'current-best' split data
            double tempDiv1 = 0.0;
            List<Double> tempItemsUsed = new ArrayList<>();            
            
            // Now fill tempDiv1 as near as possible to half without exceeding 
            for (Double item : bigToSmallItemCounts.keySet()) 
            {            
                for (int i = 0; i < bigToSmallItemCounts.get(item); ++i)
                {
                    if (tempDiv1 <= half - item)
                    {    
                        tempDiv1 += item;
                        tempItemsUsed.add(item);                     
                    }    
                    else // (Not essential, but more efficient to include)
                        break;
                }    
            }    

            // Store 'best' split so far
            if (tempDiv1 > div1)
            {
                div1 = tempDiv1;
                itemsUsed = new ArrayList<>(tempItemsUsed);  
            }    
                        
            // Remove the first (largest) item from the map before next iteration of outer loop
            // so that process can begin at subsequent item, which might otherwise be missed
            // as a source of possible best-division initial item
            bigToSmallItemCounts.remove(
                    bigToSmallItemCounts.keySet().stream().findFirst().get());
        }

        List<Double> itemsNotUsed = new ArrayList<>(itemList); // First make a copy
        // of itemList passed as arg...as it was using Arrays.asList() it cannot be modified
        // and its is best to allow that flexibility for testing for the moment at least

        for (int i = 0; i < itemsUsed.size(); ++i)
        {
            itemsNotUsed.remove(itemsUsed.get(i));
        } // NOW itemsNotUsed is properly nnamed
        // (Alternative would be to build the reciprocal list to itemsUsed in 
        // the nested loop above if the else...break statement is removed)
        
        
        // Just for convenience of viewing, decided to arrange itemsNotUsed
        // (previously unordered, unless input data happens to be in order)
        // in ascending (strictly, non-descending) order also..
        itemsNotUsed.sort(Comparator.naturalOrder());
        
        // Also decided to also dispaly itemsUsed in ascending order
        Collections.reverse(itemsUsed);
        
        // ...I realise at this point there may be many redundancies in sorting 
        // and ordering along the way, and if/when existing code is verified
        // as reliable for optimal split, will go back and try to improve
        // conciseness + efficiency!

        Div2GUI.getOutputJTextArea().append(String.format("Divvier2 smallest difference found: %.1f\n", (total - (2 * div1)))); // --woeding
        Div2GUI.getOutputJTextArea().append(String.format("between sub-collection            %s\n", itemsUsed)); 
        Div2GUI.getOutputJTextArea().append(String.format("(totalling  %.1f)\n", div1)); 
        Div2GUI.getOutputJTextArea().append(String.format("and reciprocal sub-collection  %s\n", itemsNotUsed)); 
        Div2GUI.getOutputJTextArea().append(String.format("(totalling  %.1f)\n", (total - div1))); 

        Div2GUI.getOutputJTextArea().append("\n\n");
    }
}