Daniel Miller
CS 545- Digital Image Processing
Homework 3

#####################################################

Problem 1 - Automatic Harris Corner Detection

This PlugInFilter applies the book's HarrisCornerDetector methods
in order to detect the corners in this image. In an effort to 
find the optimal threshold value, this filter will iterate through 
several several threshold values, and return the statistics for 
value which returns the best corner qualities. 

In order to determine which threshold produces the best corners, 
this filter sums the Q value for each corner, and returns the threshold
value with the highest Q Sum. 

This file depends on the following java files:
  HarrisCornerDetector.java
  Corner.java



#####################################################

CS 545 - Digital Image Processing
Homework 3
Daniel Miller

Problem 2d - Laplacian Edge Detection

This PlugInFilter applies the image transformations defined in
the previous stages of problem 2. The sigma and threshold values 
may be changed to suit the user's needs. 

The resulting edges are shown in a new window for comparison
against the source images

This file depends on the following java files:
  Utils.java

