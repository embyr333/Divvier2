/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Note that this file/class was modified from Divvier2 iteration 220701_1407

Done here: 
- Trying to allow for more item combinations by having the outer loop start in turn 
at ALL occurances of ‘multi-occurrance items',instead of going to a new item (key in map). 
Did this by removing just one occurance from map before starting again from beginning of map. 
(Alternatively, could revert to just processing a list, after sorting it, without 
converting data to map at all...just have use of each successive element contingent  
on ‘if (tempDiv1 <= half – item)’  condition being true.)
Unfortunately, it does not generate better splits for the two previously-identified
'problem inputs'.

Next: 
- Perhaps spend some time trying to find evidence that this version could get a 
better split for some inputs.
- Even if no joy, perhaps make a version without use of a map, and compare run 
speeds vs this and the previous version.
- Explore any ideas for further tweaks that might improve ability to get near to
an optimal split for any input.
- For some inputs which have a poential ‘perfect split’, Divvier” gets this quickly, 
compared to the original Divvier using random sampling, where an equivalent split 
might be arrived at only after many runs of the program; there is therefore some 
complementarity, i.e. this approach may have some residual utility when used
in conjunction with the original Divvier
So even if I come up with no further improvements to this approach, it might still 
be worth using one of the versions of the class to make a GUI-based  program,
replace the Divvier_to_11_IG and Divvier_unlimited_IG classes in 
a copy of the original Divvier. In fact, for ease of testing, it would be bet to 
remove main() methods from these classes and run them from a client class, with
user input from a variant of that GUI...ill et that up first

Commit date_time  220707_2150
 */

package embyr333.divvier2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class Divvier2b
{
    public static void main(String[] args)
    {
        // Comments after these (further) test calls show the expected difference 
        // between the two splits; some may be commented out after verifying 
        // expected result, at least until code changed again
        
        // --a few more tests
        
        process(Arrays.asList(5.0, 5.0, 5.0, 4.0, 4.0)); // 3.0
        process(Arrays.asList(9.0, 9.0, 6.0, 5.0, 3.0, 3.0, 3.0)); // 2.0
        process(Arrays.asList(7.0, 7.0, 5.0, 3.0, 2.0)); // 0.0
        process(Arrays.asList(7.0, 7.0, 5.0, 3.0, 2.0, 1.0, 1.0)); // 0.0

        // Range 1-100, 20 random items
        process(Arrays.asList(72.8, 93.6, 13.9, 52.1, 67.5, 12.8, 21.1, 30.3, 72.0, 
                62.8, 63.2, 2.5, 73.4, 20.4, 25.0, 46.8, 49.8, 85.0, 87.1, 89.2)); // 0.1
        
        
        // --how will it fare now with the wo prev 'failed' inpurs?...
        
        // Range 1-100, 18 random items with last one repeated 3x
        process(Arrays.asList(60.4, 62.0, 44.3, 15.2, 30.2, 21.2, 91.5, 42.6, 56.6, 
                51.5, 14.5, 19.5, 14.6, 98.8, 3.4, 24.1, 64.0, 42.7, 42.7, 42.7)); // 0.1
        // Gaah - new Divvier2 code still gives 0.5
        
        // Range 1-1000, 20 random items 
        process(Arrays.asList(616.1, 23.7, 291.3, 683.4, 351.7, 713.9, 782.9, 137.8, 189.3, 
                631.5, 834.4, 130.9, 61.2, 281.7, 874.3, 161.0, 2.8, 865.8, 903.4, 771.5)); // 0.0
        // and 4.8 again :(

    }    

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

        
        
        
//        int outerLimit = bigToSmallItemCounts.size();         
        
//        for (int a = 0; a < outerLimit; ++a) 
        while (bigToSmallItemCounts.size() > 0)    
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
                        
            
            
//            System.out.println("bigToSmallItemCounts " + bigToSmallItemCounts); // (temp internediate check)             
            
            
            
            // Remove one occurrance of first (largest) item from the map before next iteration
            // of outer loop so that process can begin at subsequent occurrance, or new item
            
            Double currentKey = bigToSmallItemCounts.keySet().stream().findFirst().get();
            Integer currentValue = bigToSmallItemCounts.get(currentKey);
            
            if (currentValue > 1)
            {
                bigToSmallItemCounts.put(currentKey, currentValue - 1);
            }
            else 
            {
                bigToSmallItemCounts.remove(currentKey);
            }
            
            
//            System.out.println("bigToSmallItemCounts " + bigToSmallItemCounts); // (temp internediate check) 
            
            
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

        // (Can add (back) extra line-breaks displaying the strings below in 
        // TextArea on integration into the preexisting GUI setup)
        System.out.print(String.format("Smallest difference is: %.1f\n", (total - (2 * div1)))); 
        System.out.print(String.format("between sub-collection         %s\n", itemsUsed)); 
        System.out.print(String.format("(totalling  %.1f)\n", div1)); 
        System.out.print(String.format("and reciprocal sub-collection  %s\n", itemsNotUsed)); 
        System.out.print(String.format("(totalling  %.1f)\n", (total - div1))); 
        // ...and also add this note (disabled here for output economy while testing)
//        System.out.println("(However there may be other combinations that give the same split,\n"
//                + "which could be searched for with the originl 'Divvier' program,\n"
//                + "which uses random sampling for input collections of >5 items)"); 
        System.out.println("");
    }
}

