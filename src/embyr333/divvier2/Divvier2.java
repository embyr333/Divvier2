/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Did here: 
- Converted the content of the console prints to match those for GUI display in
the original Divvier program
- Started some extra testing, immediaely uncovering a major logic flaw!!!

Next: 
- See if can fix code to deal with diverse input lists
- If so, then use the class to replace the Divvier_to_11_IG and Divvier_unlimited_IG classes used there

Tenth commit, at date_time  220627_1902
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
        // Comments after these calls show the expected difference 
        // between the two splits
        
        process(Arrays.asList(1.0, 1.0, 1.0, 5.0)); // 2.0
        process(Arrays.asList(1.0, 1.0, 1.0, 5.0, 5.0)); // 1.0
        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 0.0)); // 0.0
        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 1.0
        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 1.0
        process(Arrays.asList(1.0, 1.0, 1.0, 5.0, 2.0, 2.0, 2.0)); // 0.0
        process(Arrays.asList(7.0, 5.0, 2.0, 2.0, 2.0)); // 0.0

        // --adding some more 'challenging' inputs, for which I show diffs
        // supplied by original Divvier, which gives definitive answers for 
        // lists of 4 and 5, 'fairly confident' answers for 6-11,
        // and 'possibly not minimal diff' answers for lists of >11...
        process(Arrays.asList(9.0, 6.0, 7.0, 11.0)); // 1.0
        // ...oops, it seems I immediately expose a major logic flaw
        // as this code returns 11.00 ...realise why...see if can fix!...

    }    

    static void process(List<Double> itemList) 
    { 
        Set<Double> itemSet = new HashSet(); 
        itemSet.addAll(itemList);

        Map<Double, Integer> bigToSmallItemCounts = new TreeMap<>(Comparator.reverseOrder());
        
        for(Double item : itemSet) 
            bigToSmallItemCounts.put(item, 0); // Initialize with each item count at zero     
        
        double total = 0; // To hold sum of itemList (input list item values)
        
        // Fill the map by iterating over the list, and also sum the itemList
        for(Double item : itemList) 
        { 
            // Count by assigning item sizes to occurances 
            bigToSmallItemCounts.replace(item, bigToSmallItemCounts.get(item) + 1);    
            
            // Also calculate the total of input items
            total += item;
        }    
//        System.out.println("bigToSmallItemCounts " + bigToSmallItemCounts); // (intermediate check)
//        System.out.println("total " + total); // (intermediate check)
        
        double half = total / 2; // Calculate half the sum of the itemList
//        System.out.println("half " + half); // (intermediate check)

        // (Have a feeling there might be better Map methods to use below? and/or could 
        // apply stream approaches, but will keep this approach for the moment...)
        
        double div1 = 0; // Sum of items assigned to first of two new lists  
        // representing as-equitable-as-possible division of the original list

        List<Double> itemsUsed = new ArrayList<>(); 
        
        // Now fill div1 as near as possible to half without exceeding 
        for (Double item : bigToSmallItemCounts.keySet()) 
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
//        System.out.println("div1 " + div1); // (intermediate check)        

        List<Double> itemsNotUsed = new ArrayList<>(itemList); // First make a copy
        // of itemList passed as arg...as it was using Arrays.asList() it cannot be modified
        // and its is best to allow that flexibility for testing for the moment at least

        for (int i = 0; i < itemsUsed.size(); ++i)
        {
            itemsNotUsed.remove(itemsUsed.get(i));
        } // NOW itemsNotUsed is properly nnamed
        // (Alternative would be to build the reciprocal list to itemsUsed in 
        // the nested loop above if the else...break statement is removed)
  
        
        
        // --replacing these output statements...
//        System.out.println("Smallest difference from half is: " + (half - div1));
//        System.out.println("Items used for div1: " + itemsUsed); 
//        System.out.println("...total div1: " + div1);
//        System.out.println("Items not used: " + itemsNotUsed); 
//        System.out.println("...total div2: " + (total - div1)); 
//        System.out.println("Difference between itemsUsed (div1) and itemssNotUsed (div2): " + (total - (2 * div1)));
//        System.out.println("");
        
        // ...with equivalent statements in prep for integration into GUI from 
        // previous program (where Strings will be displayed in a TextArea),
        // adding (back) extra line-breaks as needed
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
