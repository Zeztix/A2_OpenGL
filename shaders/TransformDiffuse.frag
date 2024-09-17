#version 330

in vec4 vertColour;
in vec3 fragNormal;

uniform vec3 ambientIntensity;
uniform vec3 objectColour;
uniform vec3 lightPosition;
uniform vec3 lightColour;

out vec4 fragColour;

void main() {
	vec3 ambient = ambientIntensity * objectColour;
	vec3 finalColour = ambient * vertColour.rgb;
    fragColour = vec4(finalColour, 1.0f);
}
