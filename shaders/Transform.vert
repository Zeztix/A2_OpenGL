#version 330

layout(location=0) in vec3 vPos;
layout(location=1) in vec3 normal;
layout(location=2) in vec2 texCoord;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

//out vec4 vertColour;
//out vec3 fragPosition;
//out vec3 fragNormal;
out vec2 vertTexCoord;

void main() {
	gl_Position = projection * view * model * vec4(vPos, 1.0);
	
	//fragPosition = vec3(model * vec4(vPos, 1.0));
    //fragNormal = normal;
    vertTexCoord = texCoord;
	
	//vertColour = vec4(colour, 1.0);
}