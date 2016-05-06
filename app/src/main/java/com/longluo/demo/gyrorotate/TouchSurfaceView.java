package com.longluo.demo.gyrorotate;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by luolong on 2016/4/22.
 */
public class TouchSurfaceView extends GLSurfaceView {

    private final float TOUCH_SCALE_FACTOR = 0.5625F;

    private float mPreviousX;
    private float mPreviousY;
    private float mPreviousZ;

    private CubeRender mRenderer = new CubeRender();

    public TouchSurfaceView(Context context) {
        super(context);

        setRenderer(mRenderer);
        setRenderMode(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            mRenderer.mAngleZ = 0.0F;
            mRenderer.mAngleY = 0.0F;
            mRenderer.mAngleX = 0.0F;
        }

        return super.onKeyDown(keyCode, event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        mRenderer.mAngleZ = 0.0F;
        mRenderer.mAngleY = 0.0F;
        mRenderer.mAngleX = 0.0F;

        return true;
    }

    public void updateGyro(float x, float y, float z) {
        float lastX = mPreviousX;
        float lastY = mPreviousY;
        float lastZ = mPreviousZ;

        mRenderer.mAngleX += (x - lastX) * TOUCH_SCALE_FACTOR;
        mRenderer.mAngleY += (y - lastY) * TOUCH_SCALE_FACTOR;
        mRenderer.mAngleZ += (z - lastZ) * TOUCH_SCALE_FACTOR;

        requestRender();

        mPreviousX = x;
        mPreviousY = y;
        mPreviousZ = z;
    }

/*    private class CubeRenderer implements GLSurfaceView.Renderer {
        public float mAngleX;
        public float mAngleY;
        public float mAngleZ;

        private Cube mCube = new Cube();

        public CubeRenderer() {

        }

        @Override
        public void onDrawFrame(GL10 gl) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0.0F, 0.0F, -6.0F);

            gl.glRotatef(mAngleY, 1.0F, 0.0F, 0.0F);
            gl.glRotatef(mAngleX, 0.0F, 1.0F, 0.0F);
            gl.glRotatef(mAngleZ, 0.0F, 0.0F, 1.0F);

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

            mCube.draw(gl);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);

            float f = width / height;
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glFrustumf(-f, f, -1.0F, 1.0F, 1.0F, 10.0F);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glDisable(GL10.GL_DITHER);
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

            gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
            gl.glEnable(GL10.GL_CULL_FACE);

            gl.glShadeModel(GL10.GL_SMOOTH);
            gl.glEnable(GL10.GL_DEPTH_TEST);
        }
    }*/
}
