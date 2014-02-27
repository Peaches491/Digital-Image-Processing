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