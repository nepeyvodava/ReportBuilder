package com.nva.rbuilder.utils;

import com.nva.rbuilder.MainApp;
import com.nva.rbuilder.model.ReportConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class WriteConfig {

    public static boolean write(ReportConfig config) {
        String configName = config.configName().get();

        /**
         * удаляем конфиг со старым именем
         */
        if(configName != null) {
            new File(MainApp.CONFIGS_DIR + configName).delete();
        }

        /**
         * задаем имя конфигу как у игры
         */
        String newConfigName = config.getReport().getGameName() + ".xml";

        /**
         * проверяем, есть ли конфиг с таким именем
         */
        newConfigName = checkName(newConfigName);

        /**
         * обновляем данные
         */
        config.getReport().setGameName(newConfigName.substring(0, newConfigName.length() - 4));
        config.setName(newConfigName);

        /**
         * Маршаллируем и сохраняем XML в файл.
         */
        try {
            JAXBContext context = JAXBContext.newInstance(ReportConfig.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(config,new File(MainApp.CONFIGS_DIR + config.configName().get()));
            return true;
        }catch (JAXBException e){
            return false;
        }
    }

    public static String checkName(String fileName) {
        String result = fileName;
        List<String> configsList = ConfigsList.get(MainApp.CONFIGS_DIR);
        //если конфиг с таким именем уже есть, прибавляем к названию "_"
        if(configsList.contains(result)) {
            result = "_" + result;
            result = checkName(result);
        }
        return result;
    }

}
