// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//

package inft3032.assign;

import inft3032.drawables.Box;
import inft3032.drawables.Material;
import inft3032.drawables.Shape;
import inft3032.drawables.Triangle;
import inft3032.drawables.Vertex;
import inft3032.lighting.PointLight;
import inft3032.math.Matrix4;
import inft3032.math.MatrixFactory;
import inft3032.math.Ray;
import inft3032.math.Vector3;
import inft3032.scene.Scene;

import java.io.File;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;


public class AssignGLListener implements GLEventListener {

	Scene scene;
	private Shader shader;

	public AssignGLListener(Scene s) {
		this.scene = s;
	}
	
	// Called once at the start. Initialisation code goes here
	public void init(GLAutoDrawable drawable) {
		
		GL3 gl = drawable.getGL().getGL3();
		
		gl.glClearColor(0, 0, 0, 0); // Set colour to black
		gl.glEnable(GL.GL_CULL_FACE); // Enable backface culling
	    
	    // Initialise and compile shaders
	    try {
	        shader = new Shader(new File("shaders/Transform.vert"), new File("shaders/TransformDiffuse.frag"));
	        shader.compile(gl);
	        System.out.println("Shaders have compiled successfully.");
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		for (Shape s : scene.shapes) {
			s.init(gl);
		}
	}
	
	// Called every frame. You should have your update and render code here
	public void display(GLAutoDrawable drawable) {
		
		GL3 gl = drawable.getGL().getGL3();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // Clear colour and depth buffers
        
		shader.enable(gl);
		
		// Projection matrices setup
		Matrix4 projectionMatrix = MatrixFactory.perspective(scene.camera.getHeightAngle(), scene.camera.getAspectRatio(), 0.1f, 100.0f);
		Matrix4 viewMatrix = MatrixFactory.lookAt(scene.camera.getPosition(), scene.camera.getDirection(), scene.camera.getUp());
		
		// Set uniform locations
		shader.setUniform("projection", projectionMatrix, gl);
	    shader.setUniform("view", viewMatrix, gl);
	    shader.setUniform("ambientIntensity", new Vector3(0.1f, 0.1f, 0.1f), gl);
	    shader.setUniform("objectColour", new Vector3(1.0f, 1.0f, 1.0f), gl);
	    
		for (Shape s : scene.shapes) {
			Matrix4 modelMatrix = s.transform;
			shader.setUniform("model", modelMatrix, gl);
			
			s.draw(gl);
		}
		
		// Disable the shader
		shader.disable(gl);
	}

	// Called once at the end. You should clean up any resources here
	public void dispose(GLAutoDrawable drawable) {
	}

	// Called when the window is resized. You should update your projection matrix here.
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		
		GL3 gl = drawable.getGL().getGL3();
		
		// Get the new aspect ratio and projection matrix
		float aspect = (float) width / height;
		Matrix4 projectionMatrix = MatrixFactory.perspective(scene.camera.getHeightAngle(), aspect, 0.1f, 10.0f);
		
		// Enable shader and up the projection matrix
		shader.enable(gl);
		shader.setUniform("projection", projectionMatrix, gl);
	}
}
