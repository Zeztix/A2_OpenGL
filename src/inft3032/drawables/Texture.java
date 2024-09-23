// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;

/**
 * A class to represent a Texture.
 * <p/>
 * A texture contains an index, the filename of the .bmp texture file, and the bitmap
 * loaded into a local Image object.
 * Created by Tony Sobey on 27/03/2004 at 03:09:41
 *
 * @author a.sobey
 * @version 1.0
 */
public class Texture {
    public int index;
    public String fileName;
    public Image image;
    
    public int textureID;
    
    public Texture(int index, String filename, Image image) {
    	this.index = index;
    	this.fileName = filename;
    	this.image = image;
    }
    
    public void init(GL3 gl) {
    	
    	// Generate texture ID
    	int[] temp = new int[]{1};
    	gl.glGenTextures(1, IntBuffer.wrap(temp));
    	textureID = temp[0];
    	gl.glBindTexture(GL.GL_TEXTURE_2D, textureID); // Bind the ID
    	
    	// Allocate storage for the texture
    	gl.glTexStorage2D(GL.GL_TEXTURE_2D, 1, GL.GL_RGB8, image.width(), image.height());
    	
    	// Create a buffer to hold the pixel data
        IntBuffer pixelBuffer = IntBuffer.allocate(image.width() * image.height());
        
        // Loop through each pixel of the image
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                pixelBuffer.put(image.get(col, row)); // Get the image data
            }
        }
        
        // Flip the buffer for reading by OpenGL
        pixelBuffer.flip();
        
        // Send the data to OpenGL
        gl.glTexSubImage2D(GL.GL_TEXTURE_2D, 0, 0, 0, image.width(), image.height(), GL.GL_BGRA, GL.GL_UNSIGNED_BYTE, pixelBuffer);
    }
}
