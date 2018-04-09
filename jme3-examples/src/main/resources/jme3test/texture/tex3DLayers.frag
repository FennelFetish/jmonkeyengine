uniform float g_Time;
uniform int m_NumLayers;

in vec2 texCoord;
flat in int layer;

void main()
{
    gl_FragColor = vec4(texCoord.xy, 0.0, 1.0);
    gl_FragColor.b = float(layer) / float(m_NumLayers);
    gl_FragColor.b += g_Time;
    gl_FragColor.b = mod(gl_FragColor.b, 1.0);
}
