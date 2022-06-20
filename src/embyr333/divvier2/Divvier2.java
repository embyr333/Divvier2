/*
Objective and proposed approach: See comment atop first commit (220619_1442)

Done here: generalised "make bricks" problem to unlimited number of brick sizes
(rather than just the two, 5 and 1, in the original problem)

Next stage: Return instead the smallest defecit (ideally 0) from 
"half sum of bricks", i.e how close we can get to using exactly half of the
"brick weight" (how close to beingable to make two equal-lenght lines of bricks)

Third commit, at date_time  220620_2026
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
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 5), 8)); // true
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 5), 9)); // false (nearest is 8)
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 5, 5), 10)); // true
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 1, 0), 5)); // false (nearest is 4)
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 1, 1, 0), 5)); // true
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 0), 7)); // true
        
        // Inputs with more than two sizes of brick...  
        System.out.println(makeBricks(Arrays.asList(1, 1, 1, 5, 2, 2, 2), 8)); // true
        System.out.println(makeBricks(Arrays.asList(7, 5, 2, 2, 2), 8)); // false (nearest is 7)

    }    
    
    static boolean makeBricks(List<Integer> bricks, int goal) // ...actually, List parameteer will
    { // need to be of type Double eventaully, but more convenient to work with Integer at first
////        bricks.sort(Comparator.naturalOrder()); // --Put bricks in size order
//       // Now think I want non-ascending order instead...
//        bricks.sort(Comparator.reverseOrder()); // --Put bricks in reverse size order
//        // Note that I have a feeling I may end up using a TreeMap instead,
//        // but will explore this approach first...
//        System.out.println(bricks); // (intermediate check)
//        
//        int brickLine = 0;
//                
//        // Now fill to large brick capacity this way...
//        for (int i = 0; i < bricks.size(); ++i) // or maybe a while loop...
//        {
//            int currentBrick = bricks.get(i);
//            
//            if (brickLine <= goal - currentBrick)
//                brickLine += currentBrick;
//        }          
//        System.out.println(brickLine);
        // ...incomplete...maybe would be messy to determine when next brick size is encountered...
        // decided to go with TreeMap instead after all... 
        // which should be a better approach to ultimate goal of...
        // Instead of having variables for number big and small bricks, make general, 
        // without fixed variables, for an unknown number of ‘brick lengths’, 
        // i.e. as well a size-1 and size-5, might have e.g. size-2, size-7...
        // (Looked back at my NuritasChallenge_2 program to help remember how to make a 
        // Map (though in this case sorted) with brick sizes as keys, frequencies as values...
       
        Set<Integer> brickSizes = new HashSet();
//        for (Integer brick : bricks)
//            brickSizes.add(brick);
        // ...or, alternative way...
        brickSizes.addAll(bricks);
//        System.out.println(brickSizes); // (intermediate check)

        Map<Integer, Integer> bigToSmallBrickCounts = new TreeMap<>(Comparator.reverseOrder());
        
        for(Integer brick : brickSizes)
            bigToSmallBrickCounts.put(brick, 0); // Initialize with each brick zize count at zero       
        for(Integer brick : bricks)
            bigToSmallBrickCounts.replace(brick, bigToSmallBrickCounts.get(brick) + 1); // Now count by assigning bricks to occurrances             
        System.out.println(bigToSmallBrickCounts); // (intermediate check)
        
        // (Have a feeling ther might be better Map methods to use? and/or could 
        // apply stream approaches, but will go with this for the moment)
                
        int brickLine = 0;
        // Now fill brickLine as far as possible withou exceeding goal
        for (Integer brick : bigToSmallBrickCounts.keySet())
        {            
            for (int i = 0; i < bigToSmallBrickCounts.get(brick); ++i)
            {
                if (brickLine <= goal - brick)
                    brickLine += brick;
                else // not essential, but more efficient to include
                    break;
            }    
        }          
        System.out.println(brickLine); // (intermediate check)
        if (brickLine == goal)
                return true;
        // (There is probably a more concise and/or efficiet way to do this, 
        // but this works for the moment)
        
        // I think this approach now allows the generalisation of the 'make bricks'
        // problem to an unlimited set of different brick sizes (prev just 
        // bid and small, i.e. sizes 1 and 5)
        
        
        // (old code)
//        int bigBrickCapacity = goal / 5;
//        
//        // --Calculate value of "small" amd "big" variables below 
//        int small = 0;
//        int big = 0;
//        for (Integer brick : bricks)
//        {
//            if (brick.equals(1))
//                ++small;
//        
//            else if (brick.equals(5))
//                ++big;            
//        }    
//        System.out.println("small = " + small + "  big = " + big); // (intermediate check)
//
//        int bigBricksUsable = big <= bigBrickCapacity ? big : bigBrickCapacity;
//        int smallBricksToUse = goal - 5 * bigBricksUsable;
//        
//        if (smallBricksToUse <= small) return true;


        return false;
    }
}
