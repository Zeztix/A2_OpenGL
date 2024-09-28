// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//

package inft3032.assign;

import inft3032.drawables.Image;
import inft3032.drawables.Shape;
import inft3032.drawables.Texture;
import inft3032.math.Matrix4;
import inft3032.math.MatrixFactory;
import inft3032.math.Vector3;
import inft3032.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;


public class AssignGLListener implements GLEventListener {

	Scene scene;
	private Shader shader;
	private GLAutoDrawable glAutoDrawable;

	public AssignGLListener(Scene s) {
		this.scene = s;
	}
	
	// Called once at the start. Initialisation code goes here
	public void init(GLAutoDrawable drawable) {
		
		this.glAutoDrawable = drawable;
		GL3 gl = drawable.getGL().getGL3();
		
		gl.glClearColor(0, 0, 0, 0); // Set colour to black
		// gl.glEnable(GL.GL_CULL_FACE); // Enable backface culling - no longer useful
		gl.glDisable(GL.GL_CULL_FACE); // Disable backface culling
		gl.glEnable(GL.GL_DEPTH_TEST); // Enable depth testing
		gl.glEnable(GL.GL_TEXTURE_2D); // Enable texturing
	    
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
		for (Texture t : scene.textures) {
			t.init(gl);
		}
	}
	
	// Called every frame. You should have your update and render code here
	public void display(GLAutoDrawable drawable) {
		
		this.glAutoDrawable = drawable; // Ensure it updates
		GL3 gl = drawable.getGL().getGL3();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // Clear colour and depth buffers
        
		shader.enable(gl);
		
		// Projection matrices setup
		Matrix4 projectionMatrix = MatrixFactory.perspective(scene.camera.getHeightAngle(), scene.camera.getAspectRatio(), 0.1f, 100.0f);
		Matrix4 viewMatrix = MatrixFactory.lookAt(scene.camera.getPosition(), scene.camera.getDirection(), scene.camera.getUp());	
		
		// Set uniform locations
		shader.setUniform("viewPosition", scene.camera.getPosition(), gl);
		shader.setUniform("projection", projectionMatrix, gl);
	    shader.setUniform("view", viewMatrix, gl);
	    
	    // Ambient lighting uniforms
	    shader.setUniform("ambientIntensity", scene.ambient, gl);
	    shader.setUniform("objectColour", new Vector3(1.0f, 1.0f, 1.0f), gl);
	    
	    // Specular lighting uniforms
	    shader.setUniform("shininess", 5.0f, gl);
	    
	    // Texture uniforms
	    shader.setUniform("tex", 0, gl);
	    
	    shader.setUniform("numLights", scene.lights.length, gl);
	    
	    // Loop through all lights in the scene
	    for (int i = 0; i != scene.lights.length; i++) {
	    	// Set light uniforms
	    	shader.setUniform("lightPositions["+i+"]", scene.lights[i].location, gl);
	    	shader.setUniform("lightColours["+i+"]", scene.lights[i].colour, gl);
	    }
	    
		for (Shape s : scene.shapes) {
			Matrix4 modelMatrix = s.transform;
			shader.setUniform("model", modelMatrix, gl);
			
			s.draw(gl);
		}

		// Disable the shader
		shader.disable(gl);

		// Take a screenshot of the rendered scene
		takeScreenshot(glAutoDrawable, "screenshot.bmp");
	    System.out.println("Screenshot taken");
	}

	// Called once at the end. You should clean up any resources here
	public void dispose(GLAutoDrawable drawable) {
	}

	// Called when the window is resized. You should update your projection matrix here.
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		
		this.glAutoDrawable = drawable;
		GL3 gl = drawable.getGL().getGL3();

		float aspect = (float) width / height;
		Matrix4 projectionMatrix = MatrixFactory.perspective(scene.camera.getHeightAngle(), aspect, 0.1f, 100.0f);
		
		// Enable shader and up the projection matrix
		shader.enable(gl);
		shader.setUniform("projection", projectionMatrix, gl);
		shader.disable(gl);
	}
	
	public void takeScreenshot(GLAutoDrawable drawable, String fileName) {
		
		GL3 gl = drawable.getGL().getGL3();
		
		// Ensure all rendering commands are finished
	    gl.glFinish(); 
		
		// Get the width and height of the current viewport
		int width = drawable.getWidth();
	    int height = drawable.getHeight();
	    
	    // Create a buffer to store the pixel data
	    // TODO: Index out of bounds error when reshaping the window, doesn't prevent resizing and taking screenshots though.
	    ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 3);
	    
	    // Read pixels from the buffer
	    buffer.clear();
	    gl.glReadBuffer(GL.GL_BACK);
	    gl.glReadPixels(0, 0, width, height, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, buffer);
	    
	    // Get the size of the image
	    int[] pixels = new int[width * height];
	    
	    // Loop through scene pixels
	    for (int row = 0; row < height; row++) {
	        for (int col = 0; col < width; col++) {
	        	
	            int index = (row * width + col) * 3;
	            int r = buffer.get(index) & 0xFF;
	            int g = buffer.get(index + 1) & 0xFF;
	            int b = buffer.get(index + 2) & 0xFF;
	            
	            // Store the pixel data in the array and flip it verically
	            pixels[(height - row - 1) * width + col] = (255 << 24) | (r << 16) | (g << 8) | b;
	        }
	    }
	    
	    // Use the Image class to save the image as a BMP file
	    Image screenshot = new Image(height, width, pixels);
	    try {
	    	screenshot.write(fileName);
	    }
	    catch (IOException e) {
	    	System.err.println("Failed to save screenshot: " + e.getMessage());
	    }
	}
}
