/*
Objective:

On doing CodingBat's MakeBricks problem, I realised the following might work for a definitive (non-random, guarenteed minimal difference for unlimited collection size) of the Divvier program (though original would still have some notional utility in that it could generate alternative divisions, some of which might also be exact minimal diff)...

Where total = sum of values and halfTotal = half that, want abs(h - minDiff) minimal (as before)

First sort the collection by value of elements: z, y, x... in descending order (z >= y, y >= x etc)
There may be duplicate/replicate values, e.g. z = y, so then make a Map for easier processing
i.e. value-frequency pairs e.g. z:2, x:1 ...

If z < halfTotal, add as many z's as possible for sum < h
(stop when you run out of z's or exceed halfTotal-1)
Decrement the frequency in the Map for each addition, i.e z:2 becomes z:1 and then z:0
(Also make a reciprocal Map)
(If z >= halfTotal already, solution is just z vs the rest of the collection)

Then add as many of the next value as possible in the same manner
(and if adding a single one would exceed halfTotal, add none)

Then repeat for each sucessive value (until halfTotal-1 exceeded)

Convert the two (reciprocal) maps to collections of the original type for return/reporting

First commit, at date_time  220619_1442
 */

package embyr333.divvier2;

class Divvier2
//class MakeBricks
{
    public static void main(String[] args)
    {
        System.out.println(makeBricks(3, 1, 8)); // t
        System.out.println(makeBricks(3, 1, 9)); // f
        System.out.println(makeBricks(3, 2, 10)); // t    
        
        // Some additional tests I added 
        System.out.println(makeBricks(4, 0, 5)); // expect: false  
        System.out.println(makeBricks(5, 0, 5)); // expect: true
        System.out.println(makeBricks(7, 0, 7)); // expect: true
    }
    
    static boolean makeBricks(int small, int big, int goal)
    {
        int bigBrickCapacity = goal / 5;
        int bigBricksUsable = big <= bigBrickCapacity ? big : bigBrickCapacity;
        int smallBricksToUse = goal - 5 * bigBricksUsable;
        
        if (smallBricksToUse <= small) return true;

        return false;
    }
}
/* CodingBat problem https://codingbat.com/prob/p183562, MakeBricks (Java)

We want to make a row of bricks that is goal inches long. 
We have a number of small bricks (1 inch each) and big bricks (5 inches each). 
Return true if it is possible to make the goal by choosing from the given bricks. 
This is a little harder than it looks and can be done without any loops. 
See also: Introduction to MakeBricks (--I haven't looked at (yet))
makeBricks(3, 1, 8) → true
makeBricks(3, 1, 9) → false
makeBricks(3, 2, 10) → true

Yes, passes all tests here, and extra on the site
 */