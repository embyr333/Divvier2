/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Done here: 
- Reverted condition limit in outer loop of nested loop to previous expression

Next: 
- Try out the 'map setup simplification' idea
- Further testing...
- If get to a point where the program seems like it might handle any input, 
 then use the class to replace the Divvier_to_11_IG and Divvier_unlimited_IG classes used there

12th commit, at date_time  220628_1341
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

        // --first test generating a fail now passes...
        process(Arrays.asList(9.0, 6.0, 7.0, 11.0)); // 1.0

    }    

    static void process(List<Double> itemList) 
    { 
        double total = 0; // To hold sum of itemList (input list item numerical values)

        // Define the set of unique item numerical values in the input list
        Set<Double> itemSet = new HashSet(); 
        itemSet.addAll(itemList);

        
        // --next time, to improve conciseness, and maybe efficiency,
        //see if can combine the map 'initialisation' and filling
        // ie. maybe can omit the itemSet, use putIfAbsent() with put() instead of
        // replace() below and bigToSmallItemCounts.keySet().size() in place of
        // itemSet.size() later, as flagged --------------------------------------------------------------------------------------
        
        Map<Double, Integer> bigToSmallItemCounts = new TreeMap<>(Comparator.reverseOrder());
        
        for(Double item : itemSet) 
            bigToSmallItemCounts.put(item, 0); // Initialize with each item (key) count value at zero      
                
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
        
        

        // --rethinking how many times I want the outer loop to go:
        // although the changing  bigToSmallItemCounts.keySet().size()  limit
        // does 'work' with test inputs so far, probably best for the moment to 
        // set the value before the loop as...
//        int outerLimit = bigToSmallItemCounts.keySet().size();
        // and use that as a < outerLimit
        // or, if I do retain itemSet (decide against map setup simplification idea above)
        // just leave as...        
        for (int a = 0; a < itemSet.size(); ++a) 
        {    
            // to store 'current-best' split data
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

            // store 'best' split so far
            if (tempDiv1 > div1)
            {
                div1 = tempDiv1;
                itemsUsed = new ArrayList<>(tempItemsUsed);  
            }    
            
            System.out.println("bigToSmallItemCounts " + bigToSmallItemCounts); // (temp internediate check)
            
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
