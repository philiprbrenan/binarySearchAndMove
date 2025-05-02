//------------------------------------------------------------------------------
// Move logarithmically
// Philip R Brenan at appaapps dot com, Appa Apps Ltd Inc., 2025
//------------------------------------------------------------------------------
package com.AppaApps.Silicon;                                                   // Btree in a block on the surface of a silicon chip.

import java.util.*;

class Move extends Test                                                         // Move
 {int[]array;                                                                   // Array to check which must have unique values and at least one element
  int[]Array;                                                                   // Array to check which must have unique values and at least one element
  int index;                                                                    // Index of element found if found
  boolean found;                                                                // Whether the element was found
  final int N;                                                                  // Width of array

  Move(int L)                                                                   // Make a move
   {N = L;
    array = new int[N];
    Array = new int[N];
    for (int i = 0; i < N; i++) array[i] = i;
   }

  void moveUp(final int at)                                                     // Move up one out of this index
   {for (int i = 0; i < array.length; ++i) Array[i] = array[i];                 // Copy the source array - a single step

    for (int p = at, q = nextPowerOfTwo(array.length); q > 0; q >>= 1)
     {ok(q, nextPowerOfTwo(q));                                                 // Always a power of two which makes it possible to switch on the log thereof
      if (p + q < array.length)
       {System.arraycopy(Array, p, array, p+1, q);
        p += q;
       }
     }
   }

  void moveDown(final int at)                                                   // Move down one into this index
   {for (int i = 0; i < array.length; ++i) Array[i] = array[i];                 // Copy the source array - a single step

    for (int p = at, q = nextPowerOfTwo(array.length); q > 0; q >>= 1)
     {ok(q, nextPowerOfTwo(q));                                                 // Always a power of two which makes it possible to switch on the log thereof
      if (p + q < array.length)
       {System.arraycopy(Array, p+1, array, p, q);
        p += q;
       }
     }
   }

  String print()                                                                // Print the array
   {final StringBuilder s = new StringBuilder();
    for (int i = 0; i < array.length; ++i) s.append(""+array[i] + " ");
    if (s.length() > 0) s.setLength(s.length()-1);
    return ""+s;
   }

  void test_moveUp(int length, String result)
   {moveUp(length);
    ok(print(), result);
   }

  void test_moveDown(int length, String result)
   {moveDown(length);
    ok(print(), result);
   }

  static void test_moveUp()
   {new Move(0).test_moveUp(0, "");
    new Move(0).test_moveUp(1, "");

    new Move(1).test_moveUp(0, "0");
    new Move(1).test_moveUp(1, "0");

    new Move(2).test_moveUp(0, "0 0");
    new Move(2).test_moveUp(1, "0 1");
    new Move(2).test_moveUp(2, "0 1");

    new Move(3).test_moveUp(0, "0 0 1");
    new Move(3).test_moveUp(1, "0 1 1");
    new Move(3).test_moveUp(2, "0 1 2");
    new Move(3).test_moveUp(3, "0 1 2");

    new Move(7).test_moveUp(0, "0 0 1 2 3 4 5");
    new Move(7).test_moveUp(1, "0 1 1 2 3 4 5");
    new Move(7).test_moveUp(2, "0 1 2 2 3 4 5");
    new Move(7).test_moveUp(3, "0 1 2 3 3 4 5");
    new Move(7).test_moveUp(4, "0 1 2 3 4 4 5");
    new Move(7).test_moveUp(5, "0 1 2 3 4 5 5");
    new Move(7).test_moveUp(6, "0 1 2 3 4 5 6");
    new Move(7).test_moveUp(7, "0 1 2 3 4 5 6");

    new Move(8).test_moveUp(0, "0 0 1 2 3 4 5 6");
    new Move(8).test_moveUp(1, "0 1 1 2 3 4 5 6");
    new Move(8).test_moveUp(2, "0 1 2 2 3 4 5 6");
    new Move(8).test_moveUp(3, "0 1 2 3 3 4 5 6");
    new Move(8).test_moveUp(4, "0 1 2 3 4 4 5 6");
    new Move(8).test_moveUp(5, "0 1 2 3 4 5 5 6");
    new Move(8).test_moveUp(6, "0 1 2 3 4 5 6 6");
    new Move(8).test_moveUp(7, "0 1 2 3 4 5 6 7");
    new Move(8).test_moveUp(8, "0 1 2 3 4 5 6 7");
   }

  static void test_moveDown()
   {new Move(0).test_moveDown(0, "");
    new Move(0).test_moveDown(1, "");

    new Move(1).test_moveDown(0, "0");
    new Move(1).test_moveDown(1, "0");

    new Move(2).test_moveDown(0, "1 1");
    new Move(2).test_moveDown(1, "0 1");
    new Move(2).test_moveDown(2, "0 1");

    new Move(3).test_moveDown(0, "1 2 2");
    new Move(3).test_moveDown(1, "0 2 2");
    new Move(3).test_moveDown(2, "0 1 2");
    new Move(3).test_moveDown(3, "0 1 2");

    new Move(7).test_moveDown(0, "1 2 3 4 5 6 6");
    new Move(7).test_moveDown(1, "0 2 3 4 5 6 6");
    new Move(7).test_moveDown(2, "0 1 3 4 5 6 6");
    new Move(7).test_moveDown(3, "0 1 2 4 5 6 6");
    new Move(7).test_moveDown(4, "0 1 2 3 5 6 6");
    new Move(7).test_moveDown(5, "0 1 2 3 4 6 6");
    new Move(7).test_moveDown(6, "0 1 2 3 4 5 6");
    new Move(7).test_moveDown(7, "0 1 2 3 4 5 6");

    new Move(8).test_moveDown(0, "1 2 3 4 5 6 7 7");
    new Move(8).test_moveDown(1, "0 2 3 4 5 6 7 7");
    new Move(8).test_moveDown(2, "0 1 3 4 5 6 7 7");
    new Move(8).test_moveDown(3, "0 1 2 4 5 6 7 7");
    new Move(8).test_moveDown(4, "0 1 2 3 5 6 7 7");
    new Move(8).test_moveDown(5, "0 1 2 3 4 6 7 7");
    new Move(8).test_moveDown(6, "0 1 2 3 4 5 7 7");
    new Move(8).test_moveDown(7, "0 1 2 3 4 5 6 7");
    new Move(8).test_moveDown(8, "0 1 2 3 4 5 6 7");
   }

  protected static void oldTests()                                              // Tests thought to be in good shape
   {test_moveUp();
    test_moveDown();
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
