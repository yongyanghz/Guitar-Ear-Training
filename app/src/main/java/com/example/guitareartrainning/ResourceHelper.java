package com.example.guitareartrainning;

import android.content.Context;
import android.content.res.TypedArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


// From https://basememara.com/storing-multidimensional-resource-arrays-in-android/
public class ResourceHelper {

    public static List<TypedArray> getMultiTypedArray(Context context, String key) {
        List<TypedArray> array = new ArrayList<>();

        try {
            Class<R.array> res = R.array.class;
            Field field;
            int counter = 0;

            do {
                field = res.getField(key + "_" + counter);
                array.add(context.getResources().obtainTypedArray(field.getInt(null)));
                counter++;
            } while (field != null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return array;
        }
    }

    public static String[] getStringArray(Context context, String key) {
        String[] array = {};
        try {
            Class<R.array> res = R.array.class;
            Field field;
            field = res.getField(key);
            array = context.getResources().getStringArray(field.getInt(field));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return array;
        }
    }

    public static int getStringArrayId(Context context, String key) {
        int res = 0;
        try {
            Class<R.array> resArray = R.array.class;
            Field field;
            field = resArray.getField(key);
            res = field.getInt(field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }

    public static List<List<Integer> > get2DimIntegerArray(Context context, String key) {
        List<List<Integer>> arrays = new ArrayList<>();
        try {
            Class<R.array> res = R.array.class;
            Field field;
            int counter = 0;

            do {
                field = res.getField(key + "_" + counter);
                List<Integer> array = new ArrayList<>();
                int[] nums = context.getResources().getIntArray(field.getInt(field));
                for (int i = 0; i < nums.length; ++i) {
                    array.add(nums[i]);
                }
                arrays.add(array);
                counter++;
            } while (field != null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return arrays;
        }
    }
}