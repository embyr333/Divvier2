/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Did here: Changed many of the variable names from the idea of a list of bricks
 of specified lengths to elements in a list of numbers

Next: 
- Possibly further name changes to improve clarity
- Convert the content of the console prints to match those for GUI display in
the original Divvier program
- Then the class to replace the Divvier_to_11_IG and Divvier_unlimited_IG classes used there

Seventh commit, at date_time  220626_1708
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
        // between the 'part1' and part2' splits
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 5.0)); // 2.0
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 5.0, 5.0)); // 1.0
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 1.0, 0.0)); // 0.0
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 1.0
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 1.0
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 5.0, 2.0, 2.0, 2.0)); // 0.0
        makeBricks(Arrays.asList(7.0, 5.0, 2.0, 2.0, 2.0)); // 0.0

    }    

    static void makeBricks(List<Double> numbersList) // --changed parameter name 'bricks' to 'numbersList'
    { 
        Set<Double> numberSet = new HashSet(); // --changed variable name 'brickSizes' to 'numberSet'
        numberSet.addAll(numbersList);

        // --changed variable name 'bigToSmallBrickCounts' to 'bigToSmallNumberCounts'
        Map<Double, Integer> bigToSmallNumberCounts = new TreeMap<>(Comparator.reverseOrder());

        for(Double number : numberSet) // --changed variable name 'number' to 'number'
            bigToSmallNumberCounts.put(number, 0); // Initialize with each number count at zero     

        double totalNumbersLength = 0; // --changed variable name 'totalBricksLength' to 'totalNumbersLength'
        // Sum the numbersList, i.e. total 'length' of input collection
        // to the map-filling loop itarating over the list...
        for(Double number : numbersList) // --changed variable name ''number to 'number'
        { 
            // Count by assigning number sizes to occurances 
            bigToSmallNumberCounts.replace(number, bigToSmallNumberCounts.get(number) + 1);    
            
            // Also calculate the total input number lenght if arranged in a line
            totalNumbersLength += number;
        }    
        System.out.println(bigToSmallNumberCounts); // (intermediate check)
        System.out.println("totalNumbersLength " + totalNumbersLength); // (intermediate check)

        // Calculate half of number collection 'length' 
        double halfNumbersLength = totalNumbersLength / 2; // --changed variable name 'halfBricksLength' to 'halfNumbersLength'
        System.out.println("halfNumbersLength " + halfNumbersLength); // (intermediate check)

        // (Have a feeling there might be better Map methods to use? and/or could 
        // apply stream approaches, but will keep this approach for the moment...)
        
        double numbersLine = 0; // --changed variable name 'brickline' to 'numbersLine'
        // Now fill numbersLine as near as possible to half without exceeding it

        List<Double> numbersUsed = new ArrayList<>(); // --changed variable name 'bricksUsed' to 'numbersUsed'
        
        for (Double number : bigToSmallNumberCounts.keySet()) // --changed variable name 'brick' to 'number'
        {            
            for (int i = 0; i < bigToSmallNumberCounts.get(number); ++i)
            {
                if (numbersLine <= halfNumbersLength - number)
                {    
                    numbersLine += number;
                    numbersUsed.add(number);                     
                }    
                else // (Not essential, but more efficient to include)
                    break;
            }    
        }          
        System.out.println("numbersLine " + numbersLine); // (intermediate check)        
        
        // --changed variable name 'bricksNotUsed' to 'numbersNotUsed'...
        List<Double> numbersNotUsed = new ArrayList<>(numbersList); // First make a copy
        // of numbersList passed as arg...as it was using Arrays.asList() it cannot be modified
        // and its is best to allow that flexibility for testing for the moment at least
        for (int i = 0; i < numbersUsed.size(); ++i)
        {
            numbersNotUsed.remove(numbersUsed.get(i));
        } // NOW numbersNotUsed is properly nnamed
        // (Alternative would be to build the reciprocal list to numbersUsed in 
        // the nested loop above if the else...break statement is removed)
  
        System.out.println("Smallest difference from half is: " + (halfNumbersLength - numbersLine));
        System.out.println("Numbers used: " + numbersUsed); // List of numbersList used
        System.out.println("...total length: " + numbersLine);
        System.out.println("Numbers not used: " + numbersNotUsed); // List) of numbers unused
        System.out.println("...total length: " + (totalNumbersLength - numbersLine)); 
        System.out.println("Difference between numbersUsed and numbersNotUsed: " + (totalNumbersLength - (2 * numbersLine)));
        System.out.println("");
        
    }
}
