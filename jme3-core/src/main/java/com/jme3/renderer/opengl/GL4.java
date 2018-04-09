/*
 * Copyright (c) 2009-2014 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.renderer.opengl;

/**
 * GL functions only available on vanilla desktop OpenGL 4.0.
 * 
 * @author Kirill Vainer
 */
public interface GL4 extends GL3 {

    public static final int GL_TESS_CONTROL_SHADER = 0x8E88;
    public static final int GL_TESS_EVALUATION_SHADER = 0x8E87;
    public static final int GL_PATCHES = 0xE;

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPatchParameteri">Reference Page</a></p>
     * <p>
     * Specifies the integer value of the specified parameter for patch primitives.
     *
     * @param count the new value for the parameter given by {@code pname}
     */
    public void glPatchParameter(int count);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glFramebufferTexture">Reference Page</a></p>
     * <p>
     * Attach a level of a texture object as a logical buffer of a framebuffer object.
     *
     * @param target Specifies the target to which the framebuffer is bound for all commands except glNamedFramebufferTexture.
     * @param attachment Specifies the attachment point of the framebuffer.
     * @param texture Specifies the name of an existing texture object to attach.
     * @param level Specifies the mipmap level of the texture object to attach.
     */
    public void glFramebufferTexture(int target, int attachment, int texture, int level);
}
