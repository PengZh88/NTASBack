package me.zhengjie.utils;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.NumberUtil;
import com.google.common.math.DoubleMath;

public class AdMathUtils {

    /**
     * 计算标准差
     *
     * @param numArray
     * @return
     */
    public static double calculateSD(double numArray[]) {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for (double num : numArray) {
            sum += num;
        }

        double mean = sum / length;

        for (double num : numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }

    public static double mean(double[] array) {
        double sum = 0d;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return NumberUtil.round(sum / array.length, 2).doubleValue();
    }

    public static double[] zNormalize(double[] array) {
        double sdValue = calculateSD(array);
        double meanValue = mean(array);
        double[] newArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = NumberUtil.round((array[i] - meanValue) / sdValue, 2).doubleValue();
        }
        return newArray;
    }
}
