// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;

import inft3032.assign.Shader;
import inft3032.math.Vector3;

import java.io.File;
import java.nio.*;
import java.util.Arrays;

/**
 * This class represents a Triangle polygon.
 * @author Michael Marner <michael.marner@unisa.edu.au>
 *
 */
public class Triangle extends Shape {
	
	Vertex v1;
	Vertex v2;
	Vertex v3;
	
	private int vao;
    private int vbo;
    private float[] vertices;
	

	/**
	 * Constructs a new Triangle object using the vertices specified.
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 */
	public Triangle(Vertex v1, Vertex v2, Vertex v3, Material m) {
		super(m);
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}


	/**
	 * OpenGL initialisation for the Triangle.
	 */
	public void init(GL3 gl) {

		// Initalise array of ID's
		int[] temp = new int[]{1};
		
		// Generate the VAO
		gl.glGenVertexArrays(1, IntBuffer.wrap(temp));
		vao = temp[0];
		
		// Generate the VBO
		gl.glGenBuffers(1, IntBuffer.wrap(temp));
		vbo = temp[0];
		
		// Define the vertices
		vertices = new float[] {
				v1.pos.getX(), v1.pos.getY(), v1.pos.getZ(),
				v2.pos.getX(), v2.pos.getY(), v2.pos.getZ(),
				v3.pos.getX(), v3.pos.getY(), v3.pos.getZ()
		};
		
		// Activate the VAO and VBO
		gl.glBindVertexArray(vao);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);
		
		// Send the data to OpenGL
		gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.length * 4, FloatBuffer.wrap(vertices), GL.GL_STATIC_DRAW);
		
		// Tell OpenGL data is used for a vertex attribute
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 3*4, 0);
		gl.glEnableVertexAttribArray(0);
	}
	

	/**
	 * Renders the triangle to the current GL context.
	 */
	public void draw(GL3 gl) {
		
		// Bind the VAO and draw the triangle
		gl.glBindVertexArray(vao);
		gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3);
	}

}
