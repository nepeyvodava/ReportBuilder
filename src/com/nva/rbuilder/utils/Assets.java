package com.nva.rbuilder.utils;

import com.nva.rbuilder.model.builder.core.ReportThreadData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Assets {

    private static FileHandler logFile;
    private static Logger logger = Logger.getLogger("MyLog");
    public static final String SPECIALSYMBOL = ":~:~";
    public static final String LOGIN = "v.nepeyvoda";
    public static final String PASSWORD = "***";

    //Tools
    public static String getCurrentPath(){
        return (System.getProperty("user.dir") + "/");
    }

    public static String getNextFriday(){
        SimpleDateFormat date = new SimpleDateFormat("dd.MM");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        return date.format(calendar.getTime());
    }

    public static void initializeLog() {
        try {
            logFile = new FileHandler(getCurrentPath() + "DATA/zbuild.log", 100000, 1, true);
            logger.addHandler(logFile);
            logger.setUseParentHandlers(false);
            SimpleFormatter formatter = new SimpleFormatter();
            logFile.setFormatter(formatter);
        }catch (SecurityException sex){sex.printStackTrace();
        }catch (IOException ex){ex.printStackTrace();}
    }

    public static void println(Object str, ReportThreadData reportData) {
        System.out.println(str.toString());
        logPrint(str);
        reportData.setProgressLabel(str.toString());
    }

    public static void print(Object str, ReportThreadData reportData) {
        System.out.print(str.toString());
        logPrint(str);
        reportData.setProgressLabel(str.toString());}

    public static void printrep(Object str, ReportThreadData reportData) {
        System.out.print("\r"+str.toString());
        logPrint(str);
        reportData.setProgressLabel(str.toString());
    }

    public static void logPrint(Object str) {
        logger.info(str.toString());
    }

    public static String cut(String str, int numberWord){
        return str.split(SPECIALSYMBOL, 2)[numberWord];
    }

    public static String cutBugLabel(String str, int numberWord){
        return str.split("::", 2)[numberWord];
    }

    public static boolean checkFileExists(String path){
        File file = new File(path);
        if(file.exists() && !file.isDirectory()){ return true;
        }else {return false;}
    }

    public static final String CONFIG_LOTTERY_DEFAULT =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root>\n" +
            "\n" +
            "<settings>\n" +
            "\t<issue>LBGMEILINT-195</issue>\n" +
            "\t<conferenceMode>false</conferenceMode>\n" +
            "\t<dateEnd></dateEnd>\n" +
            "\t<login>i.ivanov</login>\n" +
            "\t<pass></pass>\n" +
            "\t<jiraURL>https://jira-srv.octavian.ru</jiraURL>\n" +
            "\t<autoAuthorisationMode>true</autoAuthorisationMode>\n" +
            "</settings>\n" +
            "\n" +
            "<report gameName=\"Lottery Game\">\n" +
            "\t<section name=\"Base Game\" label=\"BG\">\n" +
            "\t\t<unit name=\"Logic\" label=\"L\">0</unit>\n" +
            "\t\t<unit name=\"Graphics\" label=\"G\">0</unit>\n" +
            "\t\t<unit name=\"Audio\" label=\"A\">0</unit>\n" +
            "\t\t<unit name=\"Text Resources\" label=\"TR\">0</unit>\n" +
            "\t\t<unit name=\"Info\" label=\"I\">0</unit>\n" +
            "\t\t<unit name=\"Prize Structure\" label=\"PS\">0</unit>\n" +
            "\t\t<unit name=\"Specification\" label=\"S\">0</unit>\n" +
            "\t</section>   \n" +
            "</report>\n" +
            "\n" +
            "</root>"
            ;

    public static final String CONFIG_GAME_DEFAULT =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<root>\n" +
            "\n" +
            "<settings>\n" +
            "\t<issue>LBGMEILINT-108</issue>\n" +
            "\t<conferenceMode>false</conferenceMode>\n" +
            "\t<dateEnd></dateEnd>\n" +
            "\t<login>i.ivanov</login>\n" +
            "\t<pass></pass>\n" +
            "\t<jiraURL>https://jira-srv.octavian.ru</jiraURL>\n" +
            "\t<autoAuthorisationMode>true</autoAuthorisationMode>\n" +
            "</settings>\n" +
            "\n" +
            "<report gameName=\"Slot Game\">\n" +
            "\t<section name=\"BaseGame\" label=\"BG\">\n" +
            "\t\t<unit name=\"Logic\" label=\"L\">0</unit>\n" +
            "\t\t<unit name=\"Lines/Bet\" label=\"LB\">0</unit>\n" +
            "\t\t<unit name=\"Demo Combinations\" label=\"DC\">0</unit>\n" +
            "\t\t<unit name=\"Buttons\" label=\"B\">0</unit>\n" +
            "\t\t<unit name=\"Indicators/Displays\" label=\"ID\">0</unit>\n" +
            "\t\t<unit name=\"Game's Settings (audit menu)\" label=\"GS\">0</unit>\n" +
            "\t\t<unit name=\"Animation/Sound/Graphics\" label=\"ASG\">0</unit>\n" +
            "\t\t<unit name=\"Restore\" label=\"R\">0</unit>\n" +
            "\t\t<unit name=\"Game history\" label=\"GH\">0</unit>\n" +
            "\t\t<unit name=\"AutoPlay\" label=\"AP\">0</unit>\n" +
            "\t\t<unit name=\"BigWin\" label=\"BW\">0</unit>\n" +
            "\t\t<unit name=\"Illumination Control\" label=\"IC\">0</unit>\n" +
            "\t\t<unit name=\"PlayAway\" label=\"PA\">0</unit>\n" +
            "\t\t<unit name=\"Statistics (audit menu)\" label=\"S\">0</unit>\n" +
            "\t\t<unit name=\"System Error\" label=\"SE\">0</unit>\n" +
            "\t\t<unit name=\"Transactions\" label=\"T\">0</unit>\n" +
            "\t</section>\n" +
            "\t\n" +
            "   \t<section name=\"Screens, windows\" label=\"SW\">\n" +
            "\t\t<unit name=\"Table wins\" label=\"TW\">0</unit>\n" +
            "\t\t<unit name=\"Help screens\" label=\"HS\">0</unit>\n" +
            "\t\t<unit name=\"Demo Menu\" label=\"DM\">0</unit>\n" +
            "\t\t<unit name=\"3-rd screen\" label=\"SS\">0</unit>\n" +
            "\t\t<unit name=\"Explorer\" label=\"E\">0</unit>\n" +
            "\t</section>\n" +
            "\n" +
            "\t<section name=\"Free Games\" label=\"FG\">\n" +
            "\t\t<unit name=\"Logic\" label=\"L\">0</unit>\n" +
            "\t\t<unit name=\"Indicators/Displays\" label=\"ID\">0</unit>\n" +
            "\t\t<unit name=\"Animation/Sound/Graphics\" label=\"ASG\">0</unit>\n" +
            "\t\t<unit name=\"Restore\" label=\"R\">0</unit>\n" +
            "\t\t<unit name=\"Game history\" label=\"GH\">0</unit>\n" +
            "\t\t<unit name=\"AutoPlay\" label=\"AP\">0</unit>\n" +
            "\t\t<unit name=\"Risk mode\" label=\"RM\">0</unit>\n" +
            "\t\t<unit name=\"System Error\" label=\"SE\">0</unit>\n" +
            "\t\t<unit name=\"Buttons\" label=\"B\">0</unit>\n" +
            "\t\t<unit name=\"PlayAway\" label=\"PA\">0</unit>\n" +
            "\t\t<unit name=\"Transactions\" label=\"T\">0</unit>\n" +
            "\t</section>\n" +
            "\n" +
            "\t<section name=\"Feature, Wild\" label=\"FW\">\n" +
            "\t\t<unit name=\"Logic\" label=\"L\">0</unit>\n" +
            "\t\t<unit name=\"Indicators/Displays\" label=\"ID\">0</unit>\n" +
            "\t\t<unit name=\"Animation/Sound/Graphics\" label=\"ASG\">0</unit>\n" +
            "\t\t<unit name=\"Restore\" label=\"R\">0</unit>\n" +
            "\t\t<unit name=\"PlayAway\" label=\"PA\">0</unit>\n" +
            "\t\t<unit name=\"AutoPlay\" label=\"AP\">0</unit>\n" +
            "\t\t<unit name=\"Risk Mode\" label=\"RM\">0</unit>\n" +
            "\t\t<unit name=\"System Error\" label=\"SE\">0</unit>\n" +
            "\t\t<unit name=\"Transactions\" label=\"T\">0</unit>\n" +
            "\t</section>\n" +
            "\n" +
            "\t<section name=\"Risk Mode\" label=\"RM\">\n" +
            "\t\t<unit name=\"Logic\" label=\"L\">0</unit>\n" +
            "\t\t<unit name=\"Indicators/Displays\" label=\"ID\">0</unit>\n" +
            "\t\t<unit name=\"Animation/Sound/Graphics\" label=\"ASG\">0</unit>\n" +
            "\t\t<unit name=\"Restore\" label=\"R\">0</unit>\n" +
            "\t\t<unit name=\"Game History\" label=\"GH\">0</unit>\n" +
            "\t\t<unit name=\"System Error\" label=\"SE\">0</unit>\n" +
            "\t\t<unit name=\"Buttons\" label=\"B\">0</unit>\n" +
            "\t\t<unit name=\"Risk Mode Settings\" label=\"RMS\">0</unit>\n" +
            "\t\t<unit name=\"PlayAway\" label=\"PA\">0</unit>\n" +
            "\t\t<unit name=\"Transactions\" label=\"T\">0</unit>\n" +
            "\t</section>\n" +
            "\n" +
            "\t<section name=\"Timers\" label=\"T\">\n" +
            "\t\t<unit name=\"Reels Spinning Type/Time\" label=\"RST\">0</unit>\n" +
            "\t\t<unit name=\"Collect\" label=\"C\">0</unit>\n" +
            "\t\t<unit name=\"Animation of Win Combination\" label=\"AWC\">0</unit>\n" +
            "\t\t<unit name=\"Options menu\" label=\"OM\">0</unit>\n" +
            "\t</section>\n" +
            "\n" +
            "\t<section name=\"Limits\" label=\"L\">\n" +
            "\t\t<unit name=\"Max Win (credit limit)\" label=\"MW\">0</unit>\n" +
            "\t\t<unit name=\"Max Credit\" label=\"MC\">0</unit>\n" +
            "\t</section>\n" +
            "\n" +
            "\t<section name=\"Documentation\" label=\"D\">\n" +
            "\t\t<unit name=\"Correctness\" label=\"C\">0</unit>\n" +
            "\t\t<unit name=\"Messages/Helps\" label=\"MH\">0</unit>\n" +
            "\t</section>\n" +
            "</report>\n" +
            "\n" +
            "</root>"
            ;

    public static final String CONFIG_HTML_DEFAULT =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<root>\n" +
            "    <settings>\n" +
            "        <issue>MOBCRLT-2</issue>\n" +
            "        <conferenceMode>false</conferenceMode>\n" +
            "        <dateEnd></dateEnd>\n" +
            "        <login>i.ivanov</login>\n" +
            "        <pass></pass>\n" +
            "        <jiraURL>https://jira-srv.octavian.ru</jiraURL>\n" +
            "        <autoAuthorisationMode>true</autoAuthorisationMode>\n" +
            "    </settings>\n" +
            "    <report gameName=\"Html5 Game\">\n" +
            "        <section name=\"BaseGame\" label=\"BG\">\n" +
            "            <unit name=\"Logic\" label=\"L\">0</unit>\n" +
            "            <unit name=\"Lines/Bet\" label=\"LB\">0</unit>\n" +
            "            <unit name=\"Demo Combinations\" label=\"DC\">0</unit>\n" +
            "            <unit name=\"Buttons\" label=\"B\">0</unit>\n" +
            "            <unit name=\"Indicators/Displays\" label=\"ID\">0</unit>\n" +
            "            <unit name=\"Game's Settings\" label=\"GS\">0</unit>\n" +
            "            <unit name=\"Animation/Sound/Graphics\" label=\"ASG\">0</unit>\n" +
            "            <unit name=\"AutoPlay\" label=\"AP\">0</unit>\n" +
            "            <unit name=\"BigWin\" label=\"BW\">0</unit>\n" +
            "            <unit name=\"Feature, Wild\" label=\"FW\">0</unit>\n" +
            "        </section>\n" +
            "        <section name=\"Screens, windows\" label=\"SW\">\n" +
            "            <unit name=\"Table wins\" label=\"TW\">0</unit>\n" +
            "            <unit name=\"Help screens\" label=\"HS\">0</unit>\n" +
            "        </section>\n" +
            "        <section name=\"Risk Mode\" label=\"RM\">\n" +
            "            <unit name=\"Logic\" label=\"L\">0</unit>\n" +
            "            <unit name=\"Indicators/Displays\" label=\"ID\">0</unit>\n" +
            "            <unit name=\"Animation/Sound/Graphics\" label=\"ASG\">0</unit>\n" +
            "            <unit name=\"Buttons\" label=\"B\">0</unit>\n" +
            "            <unit name=\"Risk Mode Settings\" label=\"RMS\">0</unit>\n" +
            "        </section>\n" +
            "        <section name=\"Timers\" label=\"T\">\n" +
            "            <unit name=\"Reels Spinning Type/Time\" label=\"RST\">0</unit>\n" +
            "            <unit name=\"Collect\" label=\"C\">0</unit>\n" +
            "            <unit name=\"Animation of Win Combination\" label=\"AWC\">0</unit>\n" +
            "        </section>\n" +
            "        <section name=\"Documentation\" label=\"D\">\n" +
            "            <unit name=\"Correctness\" label=\"C\">0</unit>\n" +
            "            <unit name=\"Messages/Helps\" label=\"MH\">0</unit>\n" +
            "        </section>\n" +
            "        <section name=\"Platforms\" label=\"P\">\n" +
            "            <unit name=\"Desktop Browser\" label=\"DB\">0</unit>\n" +
            "            <unit name=\"Mobile\" label=\"M\">0</unit>\n" +
            "        </section>\n" +
            "    </report>\n" +
            "</root>\n"
            ;

}
