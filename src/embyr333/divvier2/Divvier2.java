/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Second commit, at date_time  220619_2225
 */

package embyr333.divvier2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Divvier2
{
    public static void main(String[] args)
    {
        /**
         * --User will be entering individual numbers in a GUI field (separated by 
         * e.g. spaces or commas. For testing processing logic here will supply 
         * these as a List, so change input format for 'bricks' from two ints 
         * representing the number of size-1 and size-5 bricks to a List containing
         * the specified number ofcvalue-5 and value-1 elements 
         */
//        System.out.println(makeBricks(3, 1, 8)); // true
//        System.out.println(makeBricks(3, 1, 9)); // false
//        System.out.println(makeBricks(3, 2, 10)); // true
//        System.out.println(makeBricks(4, 0, 5)); // false  
//        System.out.println(makeBricks(5, 0, 5)); // true
//        System.out.println(makeBricks(7, 0, 7)); // true
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 5), 8)); // true
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 5), 9)); // false
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 5, 5), 10)); // true
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 1, 0), 5)); // false
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 1, 1, 0), 5)); // true
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 0), 7)); // true

    }    
    
    // --Change first two int method parameters to a List to match above inputs
//    static boolean makeBricks(int small, int big, int goal)
    static boolean makeBricks(List<Integer> bricks, int goal)
    {
        bricks.sort(Comparator.naturalOrder()); // --Put bricks in size order
       
        int bigBrickCapacity = goal / 5;
        
        // --Calculate value of "small" amd "big" variables below 
        int small = 0;
        int big = 0;
        for (Integer brick : bricks)
        {
            if (brick.equals(1))
                ++small;
        
            else if (brick.equals(5))
                ++big;            
        }    
        System.out.println("small = " + small + "  big = " + big); // (intermediate check)

        int bigBricksUsable = big <= bigBrickCapacity ? big : bigBrickCapacity;
        int smallBricksToUse = goal - 5 * bigBricksUsable;
        
        if (smallBricksToUse <= small) return true;

        return false;
    }
}
