Daniel Miller
CS 545- Digital Image Processing
Homework 2





#####################################################
Problem 2 - Laplacian Filter

This PlugInFilter processes 8 Bit Greyscale images ONLY. 

Performs a Laplacian kernel convolution using the kernel
specified in the assignment. The 'C' value is input 
into a text box, and the image updated accordingly. In 
order to exit the filter, simply enter the same number 
twice. I.E, the filter will continue to prompt for new 
'C' values, until the same number is entered twice in a 
row. 

Using the included 'man-8g.jpg' file, I was able to 
greatly improve the image using a 'C' value of -4.0. 
With this value, the subject's eyes become clearer, 
and the frames of his glasses become sharper. 

At values above -4, additional salt & pepper noise 
begins to appear on his shoulder.  However, at values 
below -4, including positive numbers, the original 
blurriness in the image is not rectified.


#####################################################
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




#####################################################
Problem 4a - Gaussian Blur


This PlugInFilter processes RGB Images. 

This PlugInFilter implements the makeGaussKernel1D()
function shown on page 103 of the text. However,  
an additional loop is added which ensures the
resulting kernel is normalized. The filter then 
calls the ImageProcessor.convolve() method twice,
the first time specifying a vertical kernel, and 
the second time, horizontal. 

The amount of blurring can be changed by editing the 
'sigma' field.



Problem 4b - Spatial Filtering


This PlugInFilter processes RGB and Greyscale images. 

This PlugInFilter performs a Spacial Filtering with 
a given Sigma and W values. These values are input 
into a text box, and the image updated accordingly. 
In order to exit the filter, simply enter the same 
number two times in a row. 

The resulting image is formed based on the following 
equation:

       I' = (1+w)*I - w*I*G
       
  where: I is the intensity of the image
  		G is the intensity of the blurred image
     and w is the amount of blurring/sharpening
     
     
1) w = -1 produces the most blurring. This is because
   the above equation will simplify to I' = w*I*G
   when w = -1
   
2) w = 1 produces the most Sharpening. This is because
   the above equation will become 2*I + w*I*G. 
   At this point, the filter begins to perform
   Unsharp Masking, enhancing the contrast in the
   image, and making edges more pronounced.
   
3) Despite my best efforts, the original image could 
   not be resurrected. There is simply too much blur
   with sigma = 10 to be easily reversed. 
   
4) The pattern.tif image's edges become more pronounced.
   However, the image already had very sharp edges
   to begin with. after the filter is applied, a sort
   of "glow' appears around the edges, as a result
   of the Unsharp Masking taking place.  
   
   
   
    