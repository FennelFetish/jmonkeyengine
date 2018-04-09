layout(points) in;
layout(triangle_strip, max_vertices=4) out;

out vec2 texCoord;
flat out int layer;

void main()
{
    gl_Layer = gl_PrimitiveIDIn;
    layer = gl_PrimitiveIDIn;

    gl_Position = vec4(-1.0, -1.0, 0.0, 1.0);
    texCoord = vec2(0.0, 0.0);
    EmitVertex();

    gl_Position = vec4(1.0, -1.0, 0.0, 1.0);
    texCoord = vec2(1.0, 0.0);
    EmitVertex();

    gl_Position = vec4(-1.0, 1.0, 0.0, 1.0);
    texCoord = vec2(0.0, 1.0);
    EmitVertex();

    gl_Position = vec4(1.0, 1.0, 0.0, 1.0);
    texCoord = vec2(1.0, 1.0);
    EmitVertex();
}