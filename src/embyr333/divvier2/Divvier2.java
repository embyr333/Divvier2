/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Did here: Reported the contents of the 'used' and 'unused' subcollections, their 
respective 'total brick lengths', and the difference between

Next: Convert descriptions and vaariable names etc. to that of the Divvier program,
then modify to replace the Divvier_to_11_IG and Divvier_unlimited_IG classes
used by the Divvier GUI

Sixth commit, at date_time  220626_0134
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
        // --Comments after these calls show the expected 'difference from half'
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
        
        
        List<Double> bricksUsed = new ArrayList<>(); // --added

        
        for (Double brick : bigToSmallBrickCounts.keySet())
        {            
            for (int i = 0; i < bigToSmallBrickCounts.get(brick); ++i)
            {
                if (brickLine <= halfBricksLength - brick)
                {    
                    brickLine += brick;
                    
                    
                    bricksUsed.add(brick); // --added
                    
                    
                }    
                else // (Not essential, but more efficient to include)
                    break;
            }    
        }          
        System.out.println("brickLine " + brickLine); // (intermediate check)

        
        // --added...
        List<Double> bricksNotUsed = new ArrayList<>(bricks); // first make a copy
        // of bricks passed as arg...as it was using Arrays.asList() it cannot be modified
        // and its is best to allow that flexibility for testing for the moment at least
        System.out.println("copy of bricks: " + bricksNotUsed); // (intermediate check)
        for (int i = 0; i < bricksUsed.size(); ++i)
        {
            bricksNotUsed.remove(bricksUsed.get(i));
        } // NOW bricksNotUsed is properly nnamed
        // (Alternative would be to build the reciprocal list to bricksUsed in the
        // nested loop above if the else...break statement is removed)
        
        
        System.out.println("Smallest difference from half is: " + (halfBricksLength - brickLine)); // --added

        // --added
        System.out.println("Bricks used: " + bricksUsed); // collection (List) of bricks used
        System.out.println("...total length: " + brickLine);
        System.out.println("Bricks not used: " + bricksNotUsed); // collection (List) of bricks unused
        System.out.println("...total length: " + (totalBricksLength - brickLine)); 
        System.out.println("Difference between bricksUsed and bricksNotUsed: " + (totalBricksLength - (2 * brickLine)));
        System.out.println("");
        
    }
}
