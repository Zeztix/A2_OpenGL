// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;


public class Box extends Shape {
	
	private float width;
	private float height;
	private float depth;
	
	private int vao;
    private int vbo;
    private float[] vertices;
    private int[] indices;
	
	public Box(float width, float height, float depth, Material m) {
		super(m);
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	public void init(GL3 gl) {
		
		// Initalise array of ID's
		int[] temp = new int[]{1};
		
		// Generate the VAO
		gl.glGenVertexArrays(1, IntBuffer.wrap(temp));
		vao = temp[0];
		
		// Generate the VBO
		gl.glGenBuffers(1, IntBuffer.wrap(temp));
		vbo = temp[0];
		
		// Extract the colours from the material
		float r = material.diffuse.getX();
		float g = material.diffuse.getY();
		float b = material.diffuse.getZ();
		
		// Define the vertices (2 triangles to form the square on each face)
		vertices = new float[] {
			    // Front face
			    -width / 2, -height / 2, depth / 2, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, b,
			    width / 2, -height / 2, depth / 2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, b,
			    width / 2, height / 2, depth / 2, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, b,
			    -width / 2, height / 2, depth / 2, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, b,

			    // Back face
			    -width / 2, -height / 2, -depth / 2, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, b,
			    width / 2, -height / 2, -depth / 2, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, b,
			    width / 2, height / 2, -depth / 2, 0.0f, 0.0f, -1.0f, 1.0f, 1.0f, b,
			    -width / 2, height / 2, -depth / 2, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, b,

			    // Left face
			    -width / 2, -height / 2, -depth / 2, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, b,
			    -width / 2, -height / 2, depth / 2, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, b,
			    -width / 2, height / 2, depth / 2, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, b,
			    -width / 2, height / 2, -depth / 2, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, b,

			    // Right face
			    width / 2, -height / 2, -depth / 2, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, b,
			    width / 2, -height / 2, depth / 2, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, b,
			    width / 2, height / 2, depth / 2, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, b,
			    width / 2, height / 2, -depth / 2, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, b,

			    // Top face
			    -width / 2, height / 2, depth / 2, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, b,
			    width / 2, height / 2, depth / 2, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, b, 
			    width / 2, height / 2, -depth / 2, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, b,
			    -width / 2, height / 2, -depth / 2, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, b,

			    // Bottom face
			    -width / 2, -height / 2, depth / 2, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, b,
			    width / 2, -height / 2, depth / 2, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, b,
			    width / 2, -height / 2, -depth / 2, 0.0f, -1.0f, 0.0f, 1.0f, 1.0f, b,
			    -width / 2, -height / 2, -depth / 2, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, b
			};
		
		// Bind and send the index data
	    int[] indexTemp = new int[]{1};
	    gl.glGenBuffers(1, IntBuffer.wrap(indexTemp));
	    int indexBuffer = indexTemp[0];
	    
		// Define the indices
		indices = new int[] {
			    // Front face
			    0, 1, 2, 2, 3, 0,
			    // Back face
			    4, 5, 6, 6, 7, 4,
			    // Left face
			    8, 9, 10, 10, 11, 8,
			    // Right face
			    12, 13, 14, 14, 15, 12,
			    // Top face
			    16, 17, 18, 18, 19, 16,
			    // Bottom face
			    20, 21, 22, 22, 23, 20
		};
		
		// Activate the VAO and VBO
		gl.glBindVertexArray(vao);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);
		
		// Send the data to OpenGL
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.length * 4, FloatBuffer.wrap(vertices), GL.GL_STATIC_DRAW);
		
		// Tell OpenGL data is used for a vertex attribute
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 8*4, 0); // Position
		gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 8*4, 3*4); // Normal
		gl.glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, 8*4, 6*4); // Colour
		//gl.glVertexAttribPointer(2, 2, GL.GL_FLOAT, false, 8*4, 6*4); // Tex Coord
		
		gl.glEnableVertexAttribArray(0);
		gl.glEnableVertexAttribArray(1);
		gl.glEnableVertexAttribArray(2);
		
		// Tell OpenGL data is used for a index attribute
		gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
	    gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, indices.length * 4, IntBuffer.wrap(indices), GL.GL_STATIC_DRAW);
	}
	
	public void draw(GL3 gl) {
		
		// Activate and bind the texture
		//gl.glActiveTexture(GL.GL_TEXTURE0);
        //gl.glBindTexture(GL.GL_TEXTURE_2D, material.diffuseMap.textureID);
        
		// Bind the VAO and draw the triangle
		gl.glBindVertexArray(vao);
		gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
	}
}
