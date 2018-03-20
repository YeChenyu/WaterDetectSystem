package com.jida.db.xml;

import android.util.Log;

import com.jida.db.bean.System;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * 封装了XML转换成object，object转换成XML的代码
 *
 * Created by chenyuye on 17/12/5.
 *
 */
public class XMLUtil {

    private static final String TAG = "XMLUtil";


        public static void toEntity(String path){
            XStream xs = new XStream();
            try {
                Reader reader = new FileReader(new File(path));
                xs.setMode(XStream.NO_REFERENCES);
                System person = (System) xs.fromXML(reader);
                Log.d(TAG, "toEntity: "+ person.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static void toXML(System system, String path){
            Writer writer = null;
            try {
                writer = new FileWriter(new File(path));
                XStream xStream = new XStream();
                xStream.setMode(XStream.NO_REFERENCES);
                //注册使用了注解的VO
//                xStream.processAnnotations(new Class[]{System.class});
                xStream.toXML(system, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}

