#version 330

in vec4 vertColour;
in vec3 fragNormal;
in vec3 fragPosition;

uniform vec3 ambientIntensity;
uniform vec3 objectColour;
uniform vec3 lightPosition;
uniform vec3 lightColour;
uniform vec3 viewPosition;
uniform float shininess;

out vec4 fragColour;

void main() {
	vec3 norm = normalize(fragNormal);
	vec3 lightDir = normalize(lightPosition - fragPosition);

	vec3 ambient = ambientIntensity * objectColour;
	
	float diff = max(dot(norm, lightDir), 0.0);
	vec3 diffuse = diff * lightColour * objectColour;
	
	vec3 viewDir = normalize(viewPosition - fragPosition);
	vec3 reflectDir = reflect(-lightDir, norm);
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), shininess);
	vec3 specular = spec * lightColour;
	
	vec3 finalColour = (ambient + diffuse + specular) * vertColour.rgb;
    fragColour = vec4(finalColour, 1.0f);
}
