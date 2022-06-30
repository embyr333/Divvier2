/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Done here: 
- Further testing
- Also, slight tweak to display both split subcollections in ascending 
(strictly, non-descending) order

Next: 
- Try to think of at least one input list for which program would NOT generate
an as-equitable-as-possible split (though I would prefer if there is none!)
Not confident that I would be able to conceptually rule in or out whether the 
algorithm is a general solution. 
If I do find a 'fail', might try to generate more item combinations by having
the outer loop start in turn at ALL occurances of ‘multi-occurrance items, 
instead of going to a new item (key in map). Could probably do this by removing 
just one occurance from map before starting again from beginning of map. 
Otherwise, could revert to just processing a list, after sorting it, without 
converting data to map at all...just have use of each successive element contingent  
on ‘if (tempDiv1 <= half – item)’  condition being true.
- Then, if get to a point where the program seems like it might handle any input, then
 use the class to replace the Divvier_to_11_IG and Divvier_unlimited_IG classes used there

14th commit, at date_time  220630_2206   ...no changes, just re-commiting to change erroneous commit message
 */

package embyr333.divvier2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class Divvier2
{
    public static void main(String[] args)
    {
        // Comments after these test calls show the expected difference 
        // between the two splits; msome may be commented out after verifying 
        // expected result, at least until code changed again
        
//        process(Arrays.asList(1.0, 1.0, 1.0, 5.0)); // 2.0
//        process(Arrays.asList(1.0, 1.0, 1.0, 5.0, 5.0)); // 1.0
//        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 0.0)); // 0.0
//        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 1.0
//        process(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 1.0
//        process(Arrays.asList(1.0, 1.0, 1.0, 5.0, 2.0, 2.0, 2.0)); // 0.0
//        process(Arrays.asList(7.0, 5.0, 2.0, 2.0, 2.0)); // 0.0
//        process(Arrays.asList(9.0, 6.0, 7.0, 11.0)); // 1.0
        
        // --further tests passed...
        
//        process(Arrays.asList(9.0, 6.0, 7.0, 10.0)); // 0.0
//        process(Arrays.asList(9.0, 6.0, 7.0, 9.0)); // 1.0
//        process(Arrays.asList(9.0, 6.0, 7.0, 8.0)); // 0.0
//        process(Arrays.asList(9.0, 7.0, 6.0, 8.0)); // 0.0
//        process(Arrays.asList(9.0, 6.0, 8.0, 7.0)); // 0.0
        
        // (NB: will just write input elements in numerical order from now, 
        // as I know the sorting is (of course) reliable)
        
        // Tests with 4 and 5 elements can be definitively verified easily via  
        // original Divvier (which displays all possible lowest splits for 4 & 5)
//        process(Arrays.asList(1.0, 2.0, 3.0, 4.0)); // 0.0
//        process(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0)); // 1.0
//        process(Arrays.asList(1.0, 2.0, 3.0, 4.0, 6.0)); // 0.0
//        process(Arrays.asList(1.0, 2.0, 9.0, 22.0, 57.0)); // 23.0
//        process(Arrays.asList(1.0, 2.0, 9.0, 22.0, 57.0, // (duplicatiing that)
//                              1.0, 2.0, 9.0, 22.0, 57.0)); // 0.0
//        process(Arrays.asList(2.0, 7.0, 15.0, 22.0)); // 2.0
//        process(Arrays.asList(2.0, 7.0, 10.0, 15.0, 22.0)); // 2.0
//        process(Arrays.asList(0.0, 7.0, 10.0, 15.0, 22.0)); // 4.0
//        process(Arrays.asList(2.0, 7.0, 10.0, 22.0, 111.0)); // 70.0
        
        // For tests with 6-11 elements original Divvier gives 'almost-certain'
        // results, so if outputs here agree, will consider them verified...
        // Example of an 8-item aand an 11-item input...
//        process(Arrays.asList(2.0, 5.0, 9.0, 11.0, 15.0, 45.0, 49.0, 60.0)); // 2.0
//        process(Arrays.asList(3.0, 6.0, 12.0, 19.0, 21.0, 37.0, 
//                37.0, 41.0, 44.0, 48.0, 63.0)); // 1.0 
        // (Original Divvier gives also a number of other splits with same 
        // minimaal diff of 1.0 (which is why it would still have complementary
        // utility if user wanted to know about them)
        
        // Example of 25-item input
//        process(Arrays.asList(78.9, 14.3, 85.7, 30.7, 72.0, 97.5, 65.9, 19.5, 
//                49.3, 70.0, 6.3, 37.6, 29.6, 57.1, 12.3, 86.7, 60.0, 13.1, 8.9, 
//                58.5, 64.2, 52.1, 48.6, 81.0, 54.2)); // 0.0
        // Verified: Original Divvier gives 0.0 most of the time on "10x turbo" 
        // setting, and can't get any smaller than zero
        
        // Example of 100-item input
        process(Arrays.asList(67.9, 94.0, 97.7, 27.8, 14.4, 38.6, 53.5, 85.0, 
                58.9, 96.6, 34.8, 88.2, 36.3, 49.1, 25.8, 48.1, 20.6, 81.1, 92.3, 
                54.6, 37.0, 66.2, 18.4, 38.9, 7.4, 96.8, 42.2, 59.5, 48.4, 52.3, 
                10.7, 74.7, 66.0, 24.7, 81.3, 24.6, 15.8, 67.6, 10.5, 40.7, 90.6, 
                89.3, 30.3, 86.1, 97.5, 34.1, 83.3, 7.3, 91.8, 84.2, 5.7, 73.3, 
                33.1, 45.6, 44.3, 97.7, 76.7, 40.0, 36.6, 82.4, 60.1, 3.2, 58.2, 
                82.0, 51.9, 39.4, 84.9, 54.7, 38.4, 56.9, 53.6, 81.4, 91.4, 77.7, 
                69.0, 54.9, 7.0, 90.6, 40.4, 33.9, 38.8, 97.9, 13.5, 99.1, 69.3, 
                74.5, 78.0, 48.6, 23.5, 59.5, 73.0, 89.5, 9.3, 68.1, 51.8, 41.3, 
                24.2, 22.5, 8.1, 42.0)); // 0.0 ...obtained after many runs of 
                // original Divvier (even 100x turbo struggled!); interestingly,  
                // two splits I got were different from each other, and also from
                // the (also diff=0.0) split from Divvier2 here...see file 
                // "100-item input test.txt" for the splits
        
        // add the utility file to the git, push-------------------------------------------------------------------------
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

        
        // --minor change: realised that call to keySet() not needed
//        int outerLimit = bigToSmallItemCounts.keySet().size(); 
        int outerLimit = bigToSmallItemCounts.size(); 
//        System.out.println("outerLimit = " + outerLimit); // (temp check)
        
        
        for (int a = 0; a < outerLimit; ++a) 
        {    
            // To store 'current-best' split data
            double tempDiv1 = 0.0;
            List<Double> tempItemsUsed = new ArrayList<>();            
            
//            System.out.println("bigToSmallItemCounts " + bigToSmallItemCounts); // (temp internediate check)

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
        
        
        // --just for convenience of viewing, decided to arrange itemsNotUsed
        // (previously unordered, unless input data happens to be in order)
        // in ascending (strictly, non-descending) order also..
        itemsNotUsed.sort(Comparator.naturalOrder());
        
        // --then decided to also dispaly itemsUsed in ascending order
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
