package com.highgreat.sven.hotfix.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileUtils {

    /**
     * 复制文件
     * @param sourceFile
     * @param targetFile
     * @throws Exception
     */
    public static void copyFile(File sourceFile, File targetFile) throws Exception {
        //  新建文件输入流并对它进行缓冲
        FileInputStream is = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(is);
        //新建文件输出流并对它进行缓冲
        FileOutputStream os = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(os);
        //缓冲数组
        byte[] b = new byte[1024*5];
        int len;
        while((len = inBuff.read(b)) != -1){
            outBuff.write(b,0,len);
        }
        outBuff.flush();
        //关闭流
        inBuff.close();
        outBuff.close();
        is.close();
        os.close();
    }

}
