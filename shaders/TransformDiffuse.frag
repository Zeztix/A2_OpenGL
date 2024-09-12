#version 330

in vec4 vertColour;
in vec2 vertTextCoord;

uniform vec3 ambientIntensity;
uniform vec3 objectColour;

out vec4 fragColour;

void main() {
	vec3 ambient = ambientIntensity * objectColour;
	vec3 finalColour = ambient * vertColour.rgb;
    fragColour = vec4(finalColour, 1.0f);
}
