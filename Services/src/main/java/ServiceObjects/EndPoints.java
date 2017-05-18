package ServiceObjects;

import framework.Utils;

import java.util.Properties;

public class EndPoints {

    public static Properties properties;

    static {
        properties = new Properties();
        properties = Utils.loadConfig("./testdata/configs/application/global.properties");
    }
    public static String aws_Comments = "http://ec2-54-174-213-136.compute-1.amazonaws.com:3000/comments";
    public static String aws_users= "http://ec2-54-174-213-136.compute-1.amazonaws.com:3000/users";
    public static String aws_posts="http://ec2-54-174-213-136.compute-1.amazonaws.com:3000/posts";
}
