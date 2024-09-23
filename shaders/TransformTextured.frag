#version 330

in vec2 vertTexCoord;

uniform vec3 ambientIntensity;
uniform vec3 objectColour;
uniform sampler2D tex;

out vec4 fragColour;

void main() {  
	vec3 ambient = ambientIntensity * objectColour.rgb;
    fragColour = texture(tex, vertTexCoord);
}
