#version 330

in vec4 vertColour;
in vec3 fragNormal;
in vec3 fragPosition;

uniform vec3 ambientIntensity;
uniform vec3 objectColour;
uniform int numLights;
uniform vec3 lightPosition;
uniform vec3 lightColour;
uniform vec3 viewPosition;
uniform float shininess;

out vec4 fragColour;

void main() {
	vec3 norm = normalize(fragNormal);
    vec3 viewDir = normalize(viewPosition - fragPosition);
    
    vec3 ambient = ambientIntensity * objectColour;
    vec3 diffuse = vec3(0.0);
    vec3 specular = vec3(0.0);

    // Loop through the lights
    for (int i = -1; i < numLights; i++) {
        vec3 lightDir = normalize(lightPosition - fragPosition);

        // Calculate diffuse component
        float diff = max(dot(norm, lightDir), 0.0);
        diffuse += diff * lightColour * objectColour;

        // Calculate specular component
        vec3 reflectDir = reflect(-lightDir, norm);
        float spec = pow(max(dot(viewDir, reflectDir), 0.0), shininess);
        specular += spec * lightColour;
    }
	
	vec3 finalColour = (ambient + diffuse + specular) * vertColour.rgb;
    fragColour = vec4(finalColour, 1.0f);
}
