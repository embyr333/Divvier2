/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Done here:
Return is now the smallest defecit (ideally 0) from "half sum of bricks"
, i.e how close we can get to using exactly half of the "total brick length" 
(how close to being able to make two equal-lenght lines of bricks).
Also switched input collection type from Integer to Double, as otherwise, if total
is an odd number, half will be 1 less than actual half due to integer division
dropping the remainder, and want to allow fractional input values eventually anyway

Fourth commit, at date_time  220621_2313
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
        // --removed the second (int) arg, as 'goal' will now be calculated
        //  as half the sum of the element values; also changed type to Double
        System.out.println(makeBricks(Arrays.asList(1.0, 1.0, 1.0, 5.0))); // 1.0
        System.out.println(makeBricks(Arrays.asList(1.0, 1.0, 1.0, 5.0, 5.0))); // 0.5
        System.out.println(makeBricks(Arrays.asList(1.0, 1.0, 1.0, 1.0, 0.0))); // 0.0
        System.out.println(makeBricks(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 0.0))); // 0.5
        System.out.println(makeBricks(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0))); // 0.5
        System.out.println(makeBricks(Arrays.asList(1.0, 1.0, 1.0, 5.0, 2.0, 2.0, 2.0))); // 0.0
        System.out.println(makeBricks(Arrays.asList(7.0, 5.0, 2.0, 2.0, 2.0))); // 0.0

    }    
    
    // --removed the second (int) parameter, and change list type to Double, as mentioned above,
    // and made matching changes as needed in body (int/Integer->double/Double);
    // also changed some variable names for (wh I hope is increased) clarity
    static double makeBricks(List<Double> bricks) 
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

        return halfBricksLength - brickLine;
    }
}
