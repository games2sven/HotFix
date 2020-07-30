package com.highgreat.sven.hotfix.utils;

import android.content.Context;

import java.io.File;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class FixDexUtils {
    //存放需要修复的dex集合
    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        //修复前先清空
        loadedDex.clear();
    }

    public static void loadFixedDex(Context context) {
        if (context == null)
            return;
        //dex文件目录
        File fileDir = context.getDir("odex", Context.MODE_PRIVATE);
        File[] files = fileDir.listFiles();
        for (File file : files) {
            if(file.getName().endsWith(".dex") && !"classes.dex".equals(file.getName())){
                //找到要修复的dex文件
                loadedDex.add(file);
            }
        }
        //创建类加载器
        createDexClassLoader(context,fileDir);
    }

    /**
     * 创建类加载器
     * @param context
     * @param fileDir
     */
    private static void createDexClassLoader(Context context, File fileDir) {
        String optimizedDirectory = fileDir.getAbsolutePath() + File.separator + "opt_dex";
        File fOpt = new File(optimizedDirectory);
        if(!fOpt.exists()){
            fOpt.mkdirs();
        }
        DexClassLoader classLoader;
        for (File dex : loadedDex) {
            classLoader = new DexClassLoader(dex.getAbsolutePath(),optimizedDirectory,null,context.getClassLoader());
            //热修复
            hotFix(classLoader,context);
        }
    }

    private static void hotFix(DexClassLoader myClassLoader, Context context) {
        //系统的类加载器
        PathClassLoader loader = (PathClassLoader) context.getClassLoader();

        try {
            //获取自己要插入的dexElements数组对象
            Object myDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(myClassLoader));
            //获取系统的DexElements数组对象
            Object sysDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(loader));
            //合并 将要插入的数组放在合并后的数组的前面
            Object dexElements = ArrayUtils.combineArray(myDexElements, sysDexElements);
            //获取系统的pathList属性
            Object sysPathList = ReflectUtils.getPathList(loader);
            //重新赋值给系统的pathList
            ReflectUtils.setField(sysPathList,sysPathList.getClass(),dexElements);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
