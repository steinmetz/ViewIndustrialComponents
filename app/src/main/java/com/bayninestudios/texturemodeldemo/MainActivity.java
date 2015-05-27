package com.bayninestudios.texturemodeldemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import org.hogel.android.linechartview.LineChartView;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/*
3D
Características/Manual
Dados de manutenção
 */


public class MainActivity extends Activity {
    private GLSurfaceView mGLView;
    public static float angle, x, y, z;
    /**
     * Called when the activity is first created.
     */

    boolean touching  =false;
    static float prevDist = 0;

    LinearLayout linearContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mGLView = new ClearGLSurfaceView(this);
        //setContentView(mGLView);

        setContentView(R.layout.layout);

        linearContent = (LinearLayout) findViewById(R.id.linearContent);

        mGLView = (GLSurfaceView) findViewById(R.id.surfaceView);



        mGLView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    //Screen starting to be touched, set control variable and store position.
                    case MotionEvent.ACTION_DOWN:
                        touching = true;
                        x = event.getX();
                        y = event.getY();
                        break;

                    //Screen stopped being touched, set control variable.
                    case MotionEvent.ACTION_UP:
                        touching = false;
                        break;

                    //Moving the finger across the screen
                    case MotionEvent.ACTION_MOVE:

                        //If only one finger, rotate, redraw, and store position.
                        if (event.getPointerCount() == 1){
                            if (touching){

                            }
                        }

                        //If two (or more) fingers, zoom (redraws automatically) and store position.
                        else if (event.getPointerCount() > 1){
                            float dist = (float) Math.sqrt(Math.abs((event.getX(1) - event.getX(0)) * (event.getX(1) - event.getX(0) + (event.getY(1) - event.getY(0)) * (event.getY(1) - event.getY(0)))));
                            //zoom(prevDist - dist);
                            prevDist = dist;
                        }
                        break;
                }



                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    public void dadosManutView(View view) {
        linearContent.removeAllViews();

        List<LineChartView.Point> points = new ArrayList<LineChartView.Point>();
        points.add(new LineChartView.Point(-17, -100));
        points.add(new LineChartView.Point(4, 200));
        points.add(new LineChartView.Point(5, 400));
        points.add(new LineChartView.Point(6, 1100));
        points.add(new LineChartView.Point(7, 700));

        LineChartView lineChartView = new LineChartView(this, points);

        linearContent.addView(lineChartView);

        //View v = getLayoutInflater().inflate(R.layout.dados_manut, null);
        //LinearLayout dadosManut = (LinearLayout) v.findViewById(R.id.linearDadosManut);
        //  linearContent.addView(dadosManut);
    }


    public void caracteristicaView(View view) {
        linearContent.removeAllViews();
        View v = getLayoutInflater().inflate(R.layout.caracteristicas, null);
        LinearLayout linarCaracteristicas = (LinearLayout) v.findViewById(R.id.linearCaracteristicas);
        linearContent.addView(linarCaracteristicas);
    }

    public void tresDView(View view) {
        linearContent.removeAllViews();
        linearContent.addView(mGLView);
    }
}

class ClearGLSurfaceView extends GLSurfaceView {
    ClearRenderer mRenderer;

    public ClearGLSurfaceView(Context context) {
        super(context);
        mRenderer = new ClearRenderer(context, this);
        setRenderer(mRenderer);
    }

    public ClearGLSurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mRenderer = new ClearRenderer(context, this);
        setRenderer(mRenderer);
    }

}

class ClearRenderer implements GLSurfaceView.Renderer {
    private ClearGLSurfaceView view;
    private Context context;
    private DrawModel model;
    private float angleY = 0f;


    private int[] mTexture = new int[1];

    public ClearRenderer(Context context, ClearGLSurfaceView view) {
        this.view = view;
        this.context = context;
        model = new DrawModel(context, R.raw.rock);
    }

    private void loadTexture(GL10 gl, Context mContext, int mTex) {
        gl.glGenTextures(1, mTexture, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture[0]);
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), mTex);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 25.0f, (view.getWidth() * 1f) / view.getHeight(), 1, 100);
        GLU.gluLookAt(gl, 0f, 0f, 12f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        loadTexture(gl, context, R.drawable.rock);

        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        gl.glViewport(0, 0, w, h);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClearColor(0f, 0f, .7f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glPushMatrix();
        //gl.glRotatef(angleY, 0f, 1f, 0f);
        //gl.glRotatef(angleY, 0f, 1f, 0f); x
        //gl.glRotatef(angleY, angleY, 1f, 0f); yy
        gl.glRotatef(MainActivity.x, MainActivity.x, 1f, MainActivity.prevDist);
        model.draw(gl);
        gl.glPopMatrix();
        angleY += 0.4f;
    }
}
