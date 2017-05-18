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

public class CommentsTester extends BaseTest{
    String post_id,id,name,email,body,random_postid,random_id;
    String request,response;

    @Test
    public void testRequest_0() throws IOException {

        response = AWSService.get(EndPoints.aws_Comments);
    }
    @Test
    public void testRequest_1() throws IOException {
        request = FileUtils.readFileToString(new File("./testdata/JSON/Comments/request_1.json"), Charset.defaultCharset());
        JSONObject json=generateJSONObject(request);
        post_id=Integer.toString(json.getInt("postId"));
        id=Integer.toString(json.getInt("id"));
        name=json.getString("name");
        email=json.getString("email");
        body=json.getString("body");
        random_postid=getRandomNumberString();
        random_id=getRandomNumberString();
        request=request.replace(post_id,random_postid);
        request=request.replace(id,random_id);
        System.out.println(request);
        response = AWSService.post(request, EndPoints.aws_Comments);
        validateResponseAndLog(request, response);
    }

    @Test
    public void testRequest_2() throws IOException {
        String rsp_id;
        response = AWSService.get(EndPoints.aws_Comments);
        JSONObject json_rsp=new JSONObject();
        JSONArray jsonarray = new JSONArray(response);
        if(jsonarray.length()>0)
        {
        json_rsp=jsonarray.getJSONObject(0);
        }
        rsp_id=Integer.toString(json_rsp.getInt("id"));
        request = FileUtils.readFileToString(new File("./testdata/JSON/Comments/request_2.json"), Charset.defaultCharset());
        JSONObject json=generateJSONObject(request);
        post_id=Integer.toString(json.getInt("postId"));
        id=Integer.toString(json.getInt("id"));
        name=json.getString("name");
        email=json.getString("email");
        body=json.getString("body");
        random_postid=getRandomNumberString()+1;
        random_id=random_postid;
        request=request.replace(post_id,random_postid);
        request=request.replace(id,random_id);
        request=request.replace(name,name+random_id);
        request=request.replace(email,email+random_id);
        request=request.replace(body,body+"\n"+random_id);
        response = AWSService.put(request, EndPoints.aws_Comments+"/"+json_rsp.getInt("id"));
        request=request.replace(random_id,id);
        assertFalse(response.equals(request));
        }

    @Test
    public void testRequest_3() throws IOException {
        String rsp_id;
        response = AWSService.get(EndPoints.aws_Comments);
        JSONObject json_rsp=new JSONObject();
        JSONArray jsonarray = new JSONArray(response);
        if(jsonarray.length()>1)
        {
            json_rsp=jsonarray.getJSONObject(1);
        }
        rsp_id=Integer.toString(json_rsp.getInt("id"));
        request = FileUtils.readFileToString(new File("./testdata/JSON/Comments/request_3.json"), Charset.defaultCharset());
        JSONObject json=generateJSONObject(request);
        post_id=Integer.toString(json.getInt("postId"));
        id=Integer.toString(json_rsp.getInt("id"));
        name=json.getString("name");
        email=json.getString("email");
        body=json.getString("body");
        random_postid=getRandomNumberString()+1;
        random_id=random_postid;
        request=request.replace(post_id,random_postid);
        request=request.replace(id,random_id);
        response = AWSService.delete(request, EndPoints.aws_Comments+"/"+json_rsp.getInt("id"));
        assert(response.equals("{}"));
    }
    public void validateResponseAndLog(String request, String response) {
        //TODO : Customize the logic for response validation.
        request = request.replaceAll("\\r","");
        if (request.equals(response)) {
            logStep(LogStatus.PASS, "Comments posted succesfully");
            logStep(LogStatus.PASS, "Actual Response : \n" + response);
        } else {
            logStep(LogStatus.FAIL, "Actual Response : \n" + response);
            Assert.fail("Test case failed");
        }

    }
}