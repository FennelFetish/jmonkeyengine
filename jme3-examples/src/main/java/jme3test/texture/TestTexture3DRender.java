/*
 * Copyright (c) 2009-2018 jMonkeyEngine
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

package jme3test.texture;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Torus;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture3D;
import com.jme3.texture.image.ColorSpace;
import com.jme3.util.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class TestTexture3DRender extends SimpleApplication {
    private static final int TEX3D_W = 32;
    private static final int TEX3D_H = 16;
    private static final int TEX3D_D = 256;

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(15);
        viewPort.setBackgroundColor(ColorRGBA.DarkGray);

        Texture3D tex3d = create3DTexture();
        tex3d.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        tex3d.setMagFilter(Texture.MagFilter.Nearest);
        createOffscreenViewport(tex3d);

        Material mat = new Material(assetManager, "jme3test/texture/tex3D.j3md");
        mat.setTexture("Texture", tex3d);

        Mesh mesh = new Torus(64, 64, 0.7f, 1.0f);
        generate3DTexCoords(mesh);
        
        Geometry geom = new Geometry("Geom", mesh);
        geom.setMaterial(mat);
        geom.move(0, 0, 6);
        rootNode.attachChild(geom);
    }

    private Texture3D create3DTexture() {
        ArrayList<ByteBuffer> data = new ArrayList<>(1);
        data.add( BufferUtils.createByteBuffer(TEX3D_W * TEX3D_H * TEX3D_D * 3) );
        Image img = new Image(Image.Format.RGB8, TEX3D_W, TEX3D_H, TEX3D_D, data, ColorSpace.Linear);
        return new Texture3D(img);
    }

    private void generate3DTexCoords(Mesh mesh) {
        mesh.updateBound();
        BoundingBox bounds = (BoundingBox) mesh.getBound();
        Vector3f bbMin = bounds.getMin(null);
        Vector3f bbMax = bounds.getMax(null);
        Vector3f bbSize = bbMax.subtract(bbMin);

        VertexBuffer vbPos = mesh.getBuffer(VertexBuffer.Type.Position);
        FloatBuffer bufPos = (FloatBuffer) vbPos.getData();
        float[] uvw = BufferUtils.getFloatArray(bufPos);

        for(int i=0; i<uvw.length; i+=3) {
            uvw[i]   = (uvw[i]   - bbMin.x) / bbSize.x;
            uvw[i+1] = (uvw[i+1] - bbMin.y) / bbSize.y;
            uvw[i+2] = (uvw[i+2] - bbMin.z) / bbSize.z;
        }

        mesh.clearBuffer(VertexBuffer.Type.TexCoord);
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 3, BufferUtils.createFloatBuffer(uvw));
    }

    private Mesh createLayerMesh(int numLayers) {
        IntBuffer indexBuffer = BufferUtils.createIntBuffer(numLayers);
        for(int i=0; i<numLayers; ++i) {
            indexBuffer.put(i);
        }

        Mesh mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Index, 1, indexBuffer);
        mesh.setMode(Mesh.Mode.Points);
        return mesh;
    }

    private void createOffscreenViewport(Texture3D renderTarget) {
        Camera vpCam = new Camera(TEX3D_W, TEX3D_H);
        vpCam.setParallelProjection(true);
        vpCam.setFrustum(-0.1f, 0.1f, -1f, 1f, 1f, -1f);

        FrameBuffer fb = new FrameBuffer(TEX3D_W, TEX3D_H, 1);
        fb.addColorTexture(renderTarget);

        ViewPort vp = renderManager.createPreView("OffscreenViewport", vpCam);
        vp.setBackgroundColor(ColorRGBA.LightGray);
        vp.setClearFlags(true, true, true);
        vp.setOutputFrameBuffer(fb);

        Material mat = new Material(assetManager, "jme3test/texture/tex3DLayers.j3md");
        mat.setInt("NumLayers", TEX3D_D);
        Geometry geom = new Geometry("LayerGeom", createLayerMesh(TEX3D_D));
        geom.setMaterial(mat);

        Node vpRoot = new Node("OffscreenRoot");
        vpRoot.attachChild(geom);
        vpRoot.updateGeometricState();
        vpRoot.updateLogicalState(0);
        vp.attachScene(vpRoot);
    }

    public static void main(String[] args) {
        TestTexture3DRender app = new TestTexture3DRender();
        app.start();
    }
}
