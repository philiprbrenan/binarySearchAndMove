//------------------------------------------------------------------------------
// BinarySearch
// Philip R Brenan at appaapps dot com, Appa Apps Ltd Inc., 2025
//------------------------------------------------------------------------------
package com.AppaApps.Silicon;                                                   // Btree in a block on the surface of a silicon chip.

import java.util.*;

class BinarySearch extends Test                                                 // Binary tree search for index of equal and first key greater than or equal to the search
             // 0  1  2  3  4   5   6   7
 {int[]array = {1, 3, 5, 7, 9, 11, 13, 15};                                     // Array to check which must have unique values and at least one element
  int[]Array = {0, 0, 0, 0, 0,  0,  0,  0};                                     // Array to check which must have unique values and at least one element
  int index;                                                                    // Index of element found if found
  boolean found;                                                                // Whether the  element was foundf
  final int N = 7;                                                              // Width of move

  void find(int find)                                                           // Find this value using an initial block of this power of two
   {found = false;
    int p = 0;
    int q = powerTwo(N);

    for (int i = 0; i <= N; ++i)
     {final int m = (p + q) >> 1;
      if (m < array.length) found = found || find == array[m];
      if (m >= array.length || find < array[m]) q = m; else p = m;
     }
    index = found ? p : q;                                                      // The index of the containing slice
   }

  void test_find()
   {for(int i = array[0]-1; i < array[array.length-1]+1; ++i)
     {find(i);
      ok(found, i % 2 != 0);
      ok(index, i >> 1);
     }
   }

  protected static void oldTests()                                              // Tests thought to be in good shape
   {new BinarySearch().test_find();
   }

  protected static void newTests()                                              // Tests being worked on
   {oldTests();
   }

  public static void main(String[] args)                                        // Test if called as a program
   {try                                                                         // Get a traceback in a format clickable in Geany if something goes wrong to speed up debugging.
     {if (github_actions) oldTests(); else newTests();                          // Tests to run
      if (github_actions)                                                       // Coverage analysis
       {coverageAnalysis(12, "yyy.java");                                       // Used for printing
       }
      testSummary();                                                            // Summarize test results
      System.exit(testsFailed);
     }
    catch(Exception e)                                                          // Get a traceback in a format clickable in Geany
     {System.err.println(e);
      System.err.println(fullTraceBack(e));
      System.exit(1);
     }
   }
 }
