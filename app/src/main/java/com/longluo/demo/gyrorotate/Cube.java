package com.longluo.demo.gyrorotate;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by luolong on 2016/4/22.
 */

/**
 * A vertex shaded cube.
 */
public class Cube {
    private int one = 50000;
    private int cone = 65535;
    private int icont;
    private IntBuffer pointBuffer;
    private IntBuffer colorBuffer;
    private ByteBuffer indexBuffer;
    private int rotateX;
    private int rotateY;
    private int rotateZ;

    public Cube(int centerX, int centerY, int centerZ, int sideLen, float ratio) {
        int leftX = centerX - sideLen / 2;
        int rightX = centerX + sideLen / 2;
        int bottomY = centerY - (int) (sideLen / 2 * ratio);//*ratio 对高度 进行修复
        int topY = centerY + (int) (sideLen / 2 * ratio);//*ratio 对高度 进行修复
        int outZ = centerZ + sideLen / 2;
        int inZ = centerZ - sideLen / 2;

        int[] points = new int[]{

                leftX * one, bottomY * one, outZ * one,
                rightX * one, bottomY * one, outZ * one,
                rightX * one, topY * one, outZ * one,
                leftX * one, topY * one, outZ * one,

                leftX * one, bottomY * one, inZ * one,
                leftX * one, topY * one, inZ * one,

                rightX * one, bottomY * one, inZ * one,
                rightX * one, topY * one, inZ * one,
        };

        byte[] index = new byte[]{
                0, 1, 2, 2, 3, 0,
                0, 4, 3, 3, 5, 4,
                1, 6, 2, 2, 7, 6,
                4, 5, 6, 5, 7, 6,
                3, 2, 5, 5, 7, 2,
                0, 1, 4, 4, 6, 1
        };
        icont = index.length;

        int[] color = new int[]{
                cone, 0, 0, 0,
                0, cone, 0, 0,
                0, 0, cone, 0,
                0, 0, 0, cone,
                cone, 0, 0, cone,
                cone, 0, cone, 0,
                cone, cone, 0, 0,
                cone, cone, 0, cone,
        };

        ByteBuffer pBuf = ByteBuffer.allocateDirect(points.length * 4);
        pBuf.order(ByteOrder.nativeOrder());
        pointBuffer = pBuf.asIntBuffer();
        pointBuffer.put(points);
        pointBuffer.flip();
        pointBuffer.position(0);


        ByteBuffer iBuf = ByteBuffer.allocateDirect(index.length);
        iBuf.order(ByteOrder.nativeOrder());
        indexBuffer = iBuf;
        indexBuffer.put(index);
        indexBuffer.flip();
        indexBuffer.position(0);

        ByteBuffer cBuf = ByteBuffer.allocateDirect(color.length * 4);
        cBuf.order(ByteOrder.nativeOrder());
        colorBuffer = cBuf.asIntBuffer();
        colorBuffer.put(color);
        colorBuffer.flip();
        colorBuffer.position(0);
    }

    public void drawSelf(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, pointBuffer);

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);

        gl.glRotatef(rotateX, 1, 0, 0);
        gl.glRotatef(rotateY, 0, 1, 0);

        gl.glDrawElements(GL10.GL_TRIANGLES, icont, GL10.GL_UNSIGNED_BYTE, indexBuffer);
    }

    /**
     * @param rotateX the rotateX to set
     */
    public void setRotateX(int rotateX) {
        this.rotateX += rotateX;
    }

    /**
     * @param rotateY the rotateY to set
     */
    public void setRotateY(int rotateY) {
        this.rotateY += rotateY;
    }

    /**
     * @param rotateZ the rotateZ to set
     */
    public void setRotateZ(int rotateZ) {
        this.rotateZ += rotateZ;
    }

}