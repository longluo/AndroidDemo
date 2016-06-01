package com.longluo.demo.task;

import java.util.HashMap;

public class TaskParams {

    private HashMap<String, Object> mParams = null;

    public TaskParams() {
        mParams = new HashMap<String, Object>();
    }

    /**
     * 获取值.
     *
     * @param key 参数名
     * @return
     */
    public Object get(String key) {
        return mParams.get(key);
    }

    /**
     * 添加参数项.
     *
     * @param key   参数名
     * @param value 参数值
     */
    public void put(String key, Object value) {
        mParams.put(key, value);
    }

    /**
     * 返回String类型参数值.
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        if (mParams.get(key) == null) {
            return "";
        }
        return mParams.get(key).toString();
    }

    /**
     * 返回Boolean类型参数值.
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        if (mParams.get(key) == null) {
            return false;
        }
        return "true".equalsIgnoreCase(mParams.get(key).toString());
    }

    /**
     * 返回Integer类型参数值.
     *
     * @param key
     * @return 如果出现NumberFormatException, 则返回 -999.
     */
    public int getInt(String key) {
        String value = null;
        try {
            value = mParams.get(key).toString();
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -999;
        }
    }

    /**
     * 返回Integer类型参数值.
     *
     * @param key
     * @param callback 默认返回值
     * @return
     */
    public int getInt(String key, int callback) {
        String value = null;
        try {
            value = mParams.get(key).toString();
            return Integer.parseInt(value);
        } catch (Exception e) {
            return callback;
        }
    }

    /**
     * 返回Double类型参数值.
     *
     * @param key
     * @return 如果出现NumberFormatException, 则返回 -999.
     */
    public double getDouble(String key) {
        String value = null;
        try {
            value = mParams.get(key).toString();
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return -999;
        }
    }

    /**
     * 返回HashMap列表对象
     *
     * @return
     */
    public HashMap<String, Object> getHashMap() {
        return mParams;
    }

}

