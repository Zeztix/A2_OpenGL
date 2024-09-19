#version 330

in vec3 fragPosition;
in vec4 vertColour;
in vec3 fragNormal;

uniform vec3 ambientIntensity;
uniform vec3 objectColour;
uniform vec3 lightPosition;
uniform vec3 lightColour;

out vec4 fragColour;

void main() {
	vec3 norm = normalize(fragNormal);
	vec3 lightDir = normalize(lightPosition - fragPosition);
	float intensity = max(dot(norm, lightDir), 0.0);
	
	vec3 ambient = ambientIntensity * objectColour;
	
	vec3 multiplier;
	
	if (intensity > 0.95) {
        multiplier = vec3(1.0, 1.0, 1.0);
    }
    else if (intensity > 0.75) {
        multiplier = vec3(0.8, 0.8, 0.8);
    }
    else if (intensity > 0.5) {
        multiplier = vec3(0.6, 0.6, 0.6);
    }
    else if (intensity > 0.25) {
        multiplier = vec3(0.4, 0.4, 0.4);
    }
    else {
        multiplier = vec3(0.2, 0.2, 0.2);
    }
    
    vec3 finalColour = vertColour.rgb * multiplier * lightColour + ambient;
    fragColour = vec4(finalColour, 1.0f);
}
