package com.jida.db.xml;

import android.util.Log;

import com.jida.db.bean.System;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * 封装了XML转换成object，object转换成XML的代码
 *
 * Created by chenyuye on 17/12/5.
 *
 */
public class XMLUtil {

    private static final String TAG = "XMLUtil";


        public static void toEntity(String inputXML){
            XStream xs = new XStream();
//        这句和@XStreamAlias("person")等效
//        xs.alias("person",Person.class);
//        xs.alias("address",Address.class);
            xs.setMode(XStream.NO_REFERENCES);
//      这句和@XStreamImplicit()等效
//        xs.addImplicitCollection(Person.class,"addresses");
//        这句和@XStreamAsAttribute()
//        xs.useAttributeFor(Person.class, "name");
            //注册使用了注解的VO
//            xs.processAnnotations(new Class[]{System.class});
            System person = (System) xs.fromXML(inputXML);
            Log.d(TAG, "toEntity: "+ person.toString());
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

