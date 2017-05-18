package tests;

import ServiceObjects.AWSService;
import ServiceObjects.EndPoints;
import com.relevantcodes.extentreports.LogStatus;
import framework.BaseTest;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static framework.JSONUtils.generateJSONObject;
import static framework.Utils.getRandomNumberString;
import static org.testng.Assert.assertFalse;

public class PostsTester extends BaseTest {
    String user_id,id,title,body,random_userid,random_id;
    String request,response;

    @Test
    public void testRequest_0() throws IOException {

        response = AWSService.get(EndPoints.aws_Comments);
    }
    @Test
    public void testRequest_1() throws IOException {
        request = FileUtils.readFileToString(new File("./testdata/JSON/Posts/request_1.json"), Charset.defaultCharset());
        JSONObject json=generateJSONObject(request);
        user_id=Integer.toString(json.getInt("userId"));
        id=Integer.toString(json.getInt("id"));
        title=json.getString("title");
        body=json.getString("body");
        random_userid=getRandomNumberString();
        random_id=getRandomNumberString();
        request=request.replace(user_id,random_userid);
        request=request.replace(id,random_id);
        System.out.println(request);
        response = AWSService.post(request, EndPoints.aws_posts);
        validateResponseAndLog(request, response);
    }

    @Test
    public void testRequest_2() throws IOException {
        String rsp_id;
        response = AWSService.get(EndPoints.aws_posts);
        JSONObject json_rsp=new JSONObject();
        JSONArray jsonarray = new JSONArray(response);
        if(jsonarray.length()>0)
        {
        json_rsp=jsonarray.getJSONObject(0);
        }
        rsp_id=Integer.toString(json_rsp.getInt("id"));
        request = FileUtils.readFileToString(new File("./testdata/JSON/Posts/request_2.json"), Charset.defaultCharset());
        JSONObject json=generateJSONObject(request);
        user_id=Integer.toString(json.getInt("userId"));
        id=Integer.toString(json.getInt("id"));
        title=json.getString("title");
        body=json.getString("body");
        random_userid=getRandomNumberString()+1;
        random_id=random_userid;
        request=request.replace(user_id,random_userid);
        request=request.replace(id,random_id);
        request=request.replace(title,title+random_id);
        request=request.replace(body,body+"\n"+random_id);
        response = AWSService.put(request, EndPoints.aws_posts+"/"+rsp_id);
        System.out.println(rsp_id);
        request=request.replace(random_id,id);
        assertFalse(response.equals(request));
        assert(response.contains(rsp_id));
        }

    @Test
    public void testRequest_3() throws IOException {
        String rsp_id;
        response = AWSService.get(EndPoints.aws_posts);
        JSONObject json_rsp=new JSONObject();
        JSONArray jsonarray = new JSONArray(response);
        if(jsonarray.length()>1)
        {
            json_rsp=jsonarray.getJSONObject(0);
        }
        rsp_id=Integer.toString(json_rsp.getInt("id"));
        request = FileUtils.readFileToString(new File("./testdata/JSON/Posts/request_3.json"), Charset.defaultCharset());
        JSONObject json=generateJSONObject(request);
        user_id=Integer.toString(json.getInt("userId"));
        id=Integer.toString(json_rsp.getInt("id"));
        title=json.getString("title");
        body=json.getString("body");
        random_userid=getRandomNumberString()+1;
        random_id=random_userid;
        request=request.replace(user_id,random_userid);
        request=request.replace(id,random_id);
        response = AWSService.delete(request, EndPoints.aws_posts+"/"+json_rsp.getInt("id"));
        assert(response.equals("{}"));
    }
    public void validateResponseAndLog(String request, String response) {
        //TODO : Customize the logic for response validation.
        request = request.replaceAll("\\r","").trim();
        response=response.replaceAll("\\r","").trim();
        if (request.equals(response)) {
            logStep(LogStatus.PASS, "Comments posted succesfully");
            logStep(LogStatus.PASS, "Actual Response : \n" + response);
        } else {
            logStep(LogStatus.FAIL, "Actual Response : \n" + response);
            Assert.fail("Test case failed");
        }

    }

}