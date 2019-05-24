package ca.freedommobile.api.framework.config;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @project api-framework
 * Created By Rsingh on 2019-05-15.
 */

public class Config {
    private Yaml yaml;
    RouteConfig routeConfig;
    static Config config;
    private Config(){
        try {
            yaml = new Yaml();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("api.yaml");
            routeConfig = yaml.loadAs(inputStream, RouteConfig.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public static String getProperty(String key){
        String val = null;
        if(config==null){
            config = new Config();
        }
        if(key.indexOf(".")>0){
            val = config.routeConfig.getApi().getOrDefault(key.split("\\.")[1],"EMPTY");
        }else {

            switch (key){
                case "host": {
                        val =config.routeConfig.getHost();
                    }break;
                case "basePath":{
                    val = config.routeConfig.getBasePath();
                }

            }

        }
        return val;
    }
}
