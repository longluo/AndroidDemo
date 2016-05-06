package com.longluo.demo.gyrorotate;

import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by luolong on 2016/5/5.
 */
public class CubeRender implements Renderer {
    private float ratio;

    private Cube cube;


    public float mAngleX;
    public float mAngleY;
    public float mAngleZ;

    public CubeRender() {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//        gl.glMatrixMode(GL10.GL_MODELVIEW);
//        gl.glLoadIdentity();
//        gl.glTranslatef(0, 0, -5);
//
//        cube.drawSelf(gl);


        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0F, 0.0F, -6.0F);

        gl.glRotatef(mAngleY, 1.0F, 0.0F, 0.0F);
        gl.glRotatef(mAngleX, 0.0F, 1.0F, 0.0F);
        gl.glRotatef(mAngleZ, 0.0F, 0.0F, 1.0F);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        cube.drawSelf(gl);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        gl.glViewport(0, 0, width, height);
//        gl.glMatrixMode(GL10.GL_PROJECTION);
//        gl.glLoadIdentity();
//        gl.glShadeModel(GL10.GL_SMOOTH);
//
//        ratio = (float) width / height;
//
//        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
//
//        cube = new Cube(0, 0, 0, 2, ratio);

        ratio = (float) width / height;

        gl.glViewport(0, 0, width, height);

        float f = width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-f, f, -1.0F, 1.0F, 1.0F, 10.0F);

        cube = new Cube(0, 0, 0, 2, ratio);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        gl.glClearColor(0, 0, 0, 0);
//        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
        gl.glEnable(GL10.GL_CULL_FACE);

        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

}
