/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Done here: 
- Simplified the 'map setup' code

Next: 
- Further testing...
- If get to a point where the program seems like it might handle any input, 
 then use the class to replace the Divvier_to_11_IG and Divvier_unlimited_IG classes used there

13th commit, at date_time  220628_1854
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
        // Comments after these test calls show the expected difference 
        // between the two splits
        
        process(Arrays.asList(1.0, 1.0, 1.0, 5.0)); // 2.0
        process(Arrays.asList(1.0, 1.0, 1.0, 5.0, 5.0)); // 1.0
        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 0.0)); // 0.0
        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 1.0
        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 1.0
        process(Arrays.asList(1.0, 1.0, 1.0, 5.0, 2.0, 2.0, 2.0)); // 0.0
        process(Arrays.asList(7.0, 5.0, 2.0, 2.0, 2.0)); // 0.0

        process(Arrays.asList(9.0, 6.0, 7.0, 11.0)); // 1.0

    }    

    static void process(List<Double> itemList) 
    { 
        double total = 0; // To hold sum of itemList (input list item numerical values)

        
        // --made the map setup more concise, (and maybe a little more efficient?),
        // by combining the map 'initialisation' and filling
        // so no longer need...
//        Set<Double> itemSet = new HashSet(); 
//        itemSet.addAll(itemList);        
        
        Map<Double, Integer> bigToSmallItemCounts = new TreeMap<>(Comparator.reverseOrder());
        
        // ...and no longer need...
//        for(Double item : itemSet) 
//            bigToSmallItemCounts.put(item, 0); // Initialize with each item (key) count value at zero      
                
        // Fill the map by iterating over the list, and also sum the itemList
        for(Double item : itemList) 
        { 
            // Count by assigning item sizes to occurances 
            
//            bigToSmallItemCounts.replace(item, bigToSmallItemCounts.get(item) + 1); 
            // ...replacement statement...
            bigToSmallItemCounts.put(item, 
                    bigToSmallItemCounts.containsKey(item) ?
                    bigToSmallItemCounts.get(item) + 1 :
                    1);
            // (--thought use of putIfAbsent() might be applicable, but realised
            // not in a straughtforward way that came to mind, but the above does the job)
           
            
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
        
        // --since I got rid of itemSet in the map setup simplification above,
        // now replacing   itemSet.size()   limit expression below with outerLimit 
        // defined before the loop...
         int outerLimit = bigToSmallItemCounts.keySet().size(); 
//        for (int a = 0; a < itemSet.size(); ++a) 
        for (int a = 0; a < outerLimit; ++a) 
        {    
            // To store 'current-best' split data
            double tempDiv1 = 0.0;
            List<Double> tempItemsUsed = new ArrayList<>();            
            
            System.out.println("bigToSmallItemCounts " + bigToSmallItemCounts); // (temp internediate check)

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
            
            System.out.println("bigToSmallItemCounts " + bigToSmallItemCounts); // (temp internediate check)
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

        // (Can add (back) extra line-breaks displaying the strings below in 
        // TextArea on integration into the preexisting GUI setup)
        System.out.print(String.format("Smallest difference is: %.1f\n", (total - (2 * div1)))); 
        System.out.print(String.format("between sub-collection        %s\n", itemsUsed)); 
        System.out.print(String.format("(totalling  %.1f)\n", div1)); 
        System.out.print(String.format("and reciprocal sub-collection %s\n", itemsNotUsed)); 
        System.out.print(String.format("(totalling  %.1f)\n", (total - div1))); 
        // ...and also add this note (disabled here for output economy while testing)
//        System.out.println("(However there may be other combinations that give the same split,\n"
//                + "which could be searched for with the originl 'Divvier' program,\n"
//                + "which uses random sampling for input collections of >5 items)"); 
        System.out.println("");
    }
}
