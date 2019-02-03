package com.nva.rbuilder.utils;

import com.nva.rbuilder.MainApp;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

public class FileWriter {

    public static void write(String fileName, String configData) {
        //если файл с таким именем уже есть, прибавляем к названию "_"
        List<String> configsList = ConfigsList.get(MainApp.CONFIGS_DIR);

        if(configsList.contains(fileName)){fileName = "_" + fileName;}
        //создам файл и записываем в него данные конфига
        File config = new File(MainApp.CONFIGS_DIR + fileName);

        try {
            FileUtils.write(config, configData, false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
