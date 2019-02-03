package com.nva.rbuilder.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConfigsList{

    public static List<String> get(String dirPath){
        Path path = Paths.get(dirPath);

        try {
            if(!Files.exists(path)){Files.createDirectory(path);}
        }catch (IOException ex){
            System.exit(0);
        }

        List<String> configsFilesList = new ArrayList<String>();
        String filter = ".xml";
        File file = new File(dirPath);
        File[] f = file.listFiles();

        for(int i = 0; i< f.length; i++){
            if(f[i].getPath().endsWith(filter)) configsFilesList.add(f[i].getName());
        }

        return configsFilesList;
    }

}
