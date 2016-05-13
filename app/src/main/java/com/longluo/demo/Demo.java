package com.longluo.demo;

/**
 * Created by luolong on 2016/5/9.
 */
public class Demo {

/*
    SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListenerImpl(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    public interface SensorEventListener {
        public void onSensorChanged(SensorEvent event);
        public void onAccuracyChanged(Sensor sensor, int accuracy);
    }
*/

/*

    public SystemSensorManager(Context context, Looper mainLooper) {
        mMainLooper = mainLooper;
        mContext = context;

        synchronized (sListeners) {
            if (!sSensorModuleInitialized) {
                sSensorModuleInitialized = true;
                nativeClassInit();

                // initialize the sensor list
                sensors_module_init();

                final ArrayList<Sensor> fullList = sFullSensorsList;
                int i = 0;
                do {
                    Sensor sensor = new Sensor();
                    i = sensors_module_get_next_sensor(sensor, i);
                    if (i >= 0) {
                        fullList.add(sensor);
                        sHandleToSensor.append(sensor.getHandle(), sensor);
                    }
                } while (i > 0);

                sPool = new SensorEventPool(sFullSensorsList.size() * 2);
                sSensorThread = new SensorThread();
            }
        }
    }

    static int sensors__data_poll(struct sensors_data_context_t*dev, sensors_data_t*values) {
        int n;
        int mag;
        float temp;
        char buf[ 10];
        while (1) {
            if (count % 3 == 2) // 读取ＡＤＣ值
            {
                if (read(dev -> event_fd[0], & mag,sizeof(mag))<0)
                {
                    LOGE("read adc error");
                }else{
                dev -> sensors[ID_MAGNETIC_FIELD].magnetic.v[0] = (float) mag;
                LOGE("read adc %f\n", (float) mag);
                *values = dev -> sensors[ID_MAGNETIC_FIELD];
                values -> sensor = ID_MAGNETIC_FIELD;
                count++;
            }
                usleep(500000);
                return ID_MAGNETIC_FIELD;
            } else if (count % 3 == 1) //读取温度传感器值
            {
                memset(buf, 0, sizeof(buf));
                if ((n = read(dev -> event_fd[1], buf, 10)) < 0) {
                    LOGE("read temp error");
                } else {
                    buf[n - 1] = '\0';
                    temp = (float) (atoi(buf) / 1000);
                    dev -> sensors[ID_TEMPERATURE].temperature = temp;
                    LOGE("read temp %f\n", temp);
                    *values = dev -> sensors[ID_TEMPERATURE];
                    values -> sensor = ID_TEMPERATURE;
                    count++;
                }
                close(dev -> event_fd[1]);
                dev -> event_fd[1] = open("/sys/bus/i2c/devices/0-0048/temp1_input", O_RDONLY);
                usleep(500000);
                return ID_TEMPERATURE;
            } else if (count % 3 == 0) //读取方向传感器模拟值
            {
                LOGI("read orientation\n");
                    */
/* fill up data of orientation *//*

                dev -> sensors[ID_ORIENTATION].orientation.azimuth = x + 5;
                dev -> sensors[ID_ORIENTATION].orientation.pitch = y + 5;
                dev -> sensors[ID_ORIENTATION].orientation.roll = z + 5;
                *values = dev -> sensors[ID_ORIENTATION];
                values -> sensor = ID_ORIENTATION;
                count++;
                x += 0.0001;
                y += 0.0001;
                z += 0.0001;
                usleep(500000);
                return ID_ORIENTATION;
            }
        }
    }
*/


}
