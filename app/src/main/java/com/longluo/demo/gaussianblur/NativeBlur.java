package com.longluo.demo.gaussianblur;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class NativeBlur {
    public native static void functionToBlur(Bitmap bitmapOut, int radius,
                                             int threadCount, int threadIndex, int round);

    static {
        System.loadLibrary("ndkblur");
    }

    public Bitmap blur(Bitmap original, float radius) {
        Bitmap bitmapOut = original.copy(Bitmap.Config.ARGB_8888, true);

        int cores = Runtime.getRuntime().availableProcessors();

        ArrayList<NativeBlurTask> horizontal = new ArrayList<NativeBlurTask>(
                cores);
        ArrayList<NativeBlurTask> vertical = new ArrayList<NativeBlurTask>(
                cores);
        for (int i = 0; i < cores; i++) {
            horizontal.add(new NativeBlurTask(bitmapOut, (int) radius, cores,
                    i, 1));
            vertical.add(new NativeBlurTask(bitmapOut, (int) radius, cores, i,
                    2));
        }

        try {
            Executors.newFixedThreadPool(cores).invokeAll(horizontal);
        } catch (InterruptedException e) {
            return bitmapOut;
        }

        try {
            Executors.newFixedThreadPool(cores).invokeAll(vertical);
        } catch (InterruptedException e) {
            return bitmapOut;
        }
        return bitmapOut;
    }

    private static class NativeBlurTask implements Callable<Void> {
        private final Bitmap _bitmapOut;
        private final int _radius;
        private final int _totalCores;
        private final int _coreIndex;
        private final int _round;

        public NativeBlurTask(Bitmap bitmapOut, int radius, int totalCores,
                              int coreIndex, int round) {
            _bitmapOut = bitmapOut;
            _radius = radius;
            _totalCores = totalCores;
            _coreIndex = coreIndex;
            _round = round;
        }

        @Override
        public Void call() throws Exception {
            functionToBlur(_bitmapOut, _radius, _totalCores, _coreIndex, _round);
            return null;
        }
    }
}
