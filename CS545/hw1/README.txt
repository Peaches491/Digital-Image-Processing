Daniel Miller
CS 545- Digital Image Processing
Homework 2






Problem 2 - Laplacian Filter

This PlugInFilter processes 8 Bit Greyscale images ONLY. 

Performs a Laplacian kernel convolution using the kernel specified in 
the assignment. The 'C' value is input into a text box, and the image 
updated accordingly. In order to exit the filter, simply enter the same 
number twice. I.E, the filter will continue to prompt for new 'C' values,
until the same number is entered twice in a row. 

Using the included 'man-8g.jpg' file, I was able to greatly improve
the image using a 'C' value of -4.0. With this value, the subject's eyes 
become clearer, and the frames of his glasses become sharper. 

At values above -4, additional salt & pepper noise begins to appear on
his shoulder.  However, at values below -4, including positive numbers, 
the original blurriness in the image is not rectified.



Problem 3 - Restoration of Degraded_A.jpg

This PlugInFilter processes RGB Images. 

Degraded image A suffers from RGB-style "Salt and
Pepper" noise. In order to combat this noise, this 
plugin implements a 3x3 median filter. This non-linear
filter iterates through the given image, and sets each 
pixel equal to the median intensity of it's neighbors.

As shown in the included image, 'Restored_A.jpg', this
filter removes all of the random points obscuring the
original image. However, this filter does lose some 
of the finer details.  


Problem 3 - Restoration of Degraded_D.jpg

This PlugInFilter processes RGB Images. 

Degraded image A suffers from what appears to be
a simple radial or Gaussian blur. As shown in 
problem 2 of this assignment, the easiest way to
combat this blurring is with a Laplacian Filter.

By reusing my code from problem 2, I was able to 
try several values of C. I found that a value of 
-14 worked well to recover some finer details of 
the source image, and avoided adding more noise
and artifacts. At values lower than this, grid-like
artifacts begin to appear, possibly due to the 
JPG compression. Values of C higher than -14 are
still clearer than the original, but there is still
room for improvement.