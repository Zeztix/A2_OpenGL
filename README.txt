INFT3032 OpenGL Assignment
===========================

Extensions Implemented:
-----------------------
1. **Multiple Lights**:
   - Updated the fragment shaders (diffuse, toon) to loop through the array of lights and accumulate their contributions to lighting.
   - Demonstrates proper rendering of multiple point lights affecting the scene objects.

2. **Screenshot**:
   - Added functionality to capture a screenshot of the current rendered scene.
   - The scene is saved as a BMP file named `screenshot.bmp`.
   - Modified the display function to take a screenshot after rendering is complete, with adjustments to ensure the image is captured correctly.

3. **Texture**:
   - Implemented the Texture class's init function.
   - Texture images are properly used and render accordingly.
   - Texture coordinates are specified for Triangle in the beginning of repo, but only Box is in later commits.
   - Made modifications to the `Image` class to ensure correct loading and rendering of texture data onto shapes.

Notes:
-----------------------
- Specular lighting done in diffuse shading, not entirely implemented, especially in the point light class.
- Spotlight already implemented? I did not add or remove anything.
- All lighting data, I am not entirely sure if I did this for each light class, but I am looping through the lights and passing the uniforms.