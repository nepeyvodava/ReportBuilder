package com.nva.rbuilder.utils;

import com.nva.rbuilder.MainApp;
import com.nva.rbuilder.model.ReportConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class WriteConfig {

    public static boolean write(ReportConfig config, String name) {
        try {
            JAXBContext context = JAXBContext.newInstance(ReportConfig.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Маршаллируем и сохраняем XML в файл.
            m.marshal(config,new File(MainApp.CONFIGS_DIR + name));
            return true;
        }catch (JAXBException e){
            return false;
        }
    }

}
