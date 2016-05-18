package com.longluo.demo.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Sphere {

    public void draw(GL10 gl) {
        float theta, pai;
        float co, si;
        float r1, r2;
        float h1, h2;
        float step = 2.0f;
        float[][] v = new float[32][3];
        ByteBuffer vbb;
        FloatBuffer vBuf;

        vbb = ByteBuffer.allocateDirect(v.length * v[0].length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vBuf = vbb.asFloatBuffer();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        for (pai = -90.0f; pai < 90.0f; pai += step) {
            int n = 0;

            r1 = (float) Math.cos(pai * Math.PI / 180.0);
            r2 = (float) Math.cos((pai + step) * Math.PI / 180.0);
            h1 = (float) Math.sin(pai * Math.PI / 180.0);
            h2 = (float) Math.sin((pai + step) * Math.PI / 180.0);

            for (theta = 0.0f; theta <= 360.0f; theta += step) {
                co = (float) Math.cos(theta * Math.PI / 180.0);
                si = -(float) Math.sin(theta * Math.PI / 180.0);

                v[n][0] = (r2 * co);
                v[n][1] = (h2);
                v[n][2] = (r2 * si);
                v[n + 1][0] = (r1 * co);
                v[n + 1][1] = (h1);
                v[n + 1][2] = (r1 * si);

                vBuf.put(v[n]);
                vBuf.put(v[n + 1]);

                n += 2;

                if (n > 31) {
                    vBuf.position(0);

                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
                    gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
                    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);

                    n = 0;
                    theta -= step;
                }

            }
            vBuf.position(0);

            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
            gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }

}
