package com.nva.rbuilder.utils;


import com.nva.rbuilder.model.ReportConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ReadConfig {

    public static ReportConfig read(String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(ReportConfig.class);
            Unmarshaller um = context.createUnmarshaller();
            // Чтение XML из файла и демаршализация.
            return (ReportConfig) um.unmarshal(new File(path));
        } catch (JAXBException ex) {
            return null;
        }
    }

}
