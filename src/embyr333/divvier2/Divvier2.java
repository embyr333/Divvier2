/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Did here:
Changed the method to void return type and reported the 'difference from half'
as a print in the console, while removing the 'print wraps' on the test input
calls in the main method

Next: report the contents of the 'used' and 'unused' subcollections, and their 
respective 'total brick lengths'

Fifth commit, at date_time  220622_43
 */

package embyr333.divvier2;

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
        // --Comments after these calls show the expected 'difference from half'
        // (which is now reported in a print statement in makeBricks() instead
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 5.0)); // 1.0
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 5.0, 5.0)); // 0.5
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 1.0, 0.0)); // 0.0
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 0.5
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0)); // 0.5
        makeBricks(Arrays.asList(1.0, 1.0, 1.0, 5.0, 2.0, 2.0, 2.0)); // 0.0
        makeBricks(Arrays.asList(7.0, 5.0, 2.0, 2.0, 2.0)); // 0.0

    }    

    static void makeBricks(List<Double> bricks) 
    { 
        Set<Double> brickSizes = new HashSet();
        brickSizes.addAll(bricks);

        Map<Double, Integer> bigToSmallBrickCounts = new TreeMap<>(Comparator.reverseOrder());

        for(Double brick : brickSizes)
            bigToSmallBrickCounts.put(brick, 0); // Initialize with each brick size count at zero     
        
        double totalBricksLength = 0; // --added
        // --added statement to sum the bricks, i.e. total 'length' of input collection
        // to the map-filling loop itarating over the list...
        for(Double brick : bricks)
        { 
            // Count by assigning brick sizes to occurances 
            bigToSmallBrickCounts.replace(brick, bigToSmallBrickCounts.get(brick) + 1);    
            
            // --Also caculate the total input brick lenght if arranged in a line
            totalBricksLength += brick;
        }    
        System.out.println(bigToSmallBrickCounts); // (intermediate check)
        System.out.println("totalBricksLength " + totalBricksLength); // (intermediate check)

        // --Calculate half of brick collection 'length' (new 'goal')
        double halfBricksLength = totalBricksLength / 2;
        System.out.println("halfBricksLength " + halfBricksLength); // (intermediate check)

        // (Have a feeling there might be better Map methods to use? and/or could 
        // apply stream approaches, but will keep this approach for the moment...)
        double brickLine = 0;
        // Now fill brickLine as near as possible to half without exceeding it
        for (Double brick : bigToSmallBrickCounts.keySet())
        {            
            for (int i = 0; i < bigToSmallBrickCounts.get(brick); ++i)
            {
                if (brickLine <= halfBricksLength - brick)
                    brickLine += brick;
                else // (Not essential, but more efficient to include)
                    break;
            }    
        }          
        System.out.println("brickLine " + brickLine); // (intermediate check)

        System.out.println("Smallest fifference from half is: " + (halfBricksLength - brickLine)); // --added

    }
}
