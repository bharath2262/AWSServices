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

public class UsersTester extends BaseTest {
    String id,name,user,random_id;
    String st_address,st_suite,st_city,st_zip;
    String latitude,longitude;
    String c_phone,c_website,c_name,c_catchphrase,c_bs;
    String request,response;

    @Test
    public void testRequest_0() throws IOException {

        response = AWSService.get(EndPoints.aws_Comments);
    }
    @Test
    public void testRequest_1() throws IOException {
        request = FileUtils.readFileToString(new File("./testdata/JSON/Users/request_1.json"), Charset.defaultCharset());
        JSONObject json=generateJSONObject(request);
        JSONObject address=json.getJSONObject("address");
        JSONObject geo=address.getJSONObject("geo");;
        JSONObject company=json.getJSONObject("company");
        id=Integer.toString(json.getInt("id"));
        name=json.getString("name");
        user=json.getString("username");


        st_address=address.getString("street");
        st_suite=address.getString("suite");
        st_city=address.getString("city");
        st_zip=address.getString("zipcode");

        latitude=geo.getString("lat");
        longitude=geo.getString("lng");

        c_phone=json.getString("phone");
        c_website=json.getString("website");
        c_catchphrase=company.getString("catchPhrase");
        c_bs=company.getString("bs");
        random_id=getRandomNumberString();
        request=request.replace(id,random_id);
        System.out.println("post_req"+request);
        response = AWSService.post(request, EndPoints.aws_users);
        System.out.println("post_rsp"+response);
        validateResponseAndLog(request, response);
    }

    @Test
    public void testRequest_2() throws IOException {
        String rsp_id;
        response = AWSService.get(EndPoints.aws_users);
        JSONObject json_rsp=new JSONObject();
        JSONArray jsonarray = new JSONArray(response);
        if(jsonarray.length()>0)
        {
        json_rsp=jsonarray.getJSONObject(0);
        }
        rsp_id=Integer.toString(json_rsp.getInt("id"));
        request = FileUtils.readFileToString(new File("./testdata/JSON/Users/request_1.json"), Charset.defaultCharset());
        JSONObject json=generateJSONObject(request);
        JSONObject address=json.getJSONObject("address");
        JSONObject geo=address.getJSONObject("geo");;
        JSONObject company=json.getJSONObject("company");
        id=Integer.toString(json.getInt("id"));
        name=json.getString("name");
        user=json.getString("username");


        st_address=address.getString("street");
        st_suite=address.getString("suite");
        st_city=address.getString("city");
        st_zip=address.getString("zipcode");

        latitude=geo.getString("lat");
        longitude=geo.getString("lng");

        c_phone=json.getString("phone");
        c_website=json.getString("website");
        c_catchphrase=company.getString("catchPhrase");
        c_bs=company.getString("bs");
        random_id=getRandomNumberString();
        request=request.replace(id,random_id);
        response = AWSService.put(request, EndPoints.aws_users+"/"+rsp_id);
        assertFalse(response.equals(request));
        assert(response.contains(rsp_id));
    }
    @Test
    public void testRequest_3() throws IOException {
        String rsp_id;
        response = AWSService.get(EndPoints.aws_users);
        JSONObject json_rsp=new JSONObject();
        JSONArray jsonarray = new JSONArray(response);
        if(jsonarray.length()>0)
        {
            json_rsp=jsonarray.getJSONObject(0);
        }
        rsp_id=Integer.toString(json_rsp.getInt("id"));
        request = FileUtils.readFileToString(new File("./testdata/JSON/Users/request_1.json"), Charset.defaultCharset());
        JSONObject json=generateJSONObject(request);
        JSONObject address=json.getJSONObject("address");
        JSONObject geo=address.getJSONObject("geo");;
        JSONObject company=json.getJSONObject("company");
        id=Integer.toString(json.getInt("id"));
        name=json.getString("name");
        user=json.getString("username");


        st_address=address.getString("street");
        st_suite=address.getString("suite");
        st_city=address.getString("city");
        st_zip=address.getString("zipcode");

        latitude=geo.getString("lat");
        longitude=geo.getString("lng");

        c_phone=json.getString("phone");
        c_website=json.getString("website");
        c_catchphrase=company.getString("catchPhrase");
        c_bs=company.getString("bs");
        random_id=getRandomNumberString()+1;
        request=request.replace(id,random_id);
        System.out.println(request);
        response = AWSService.delete(request, EndPoints.aws_users+"/"+rsp_id);
        System.out.println(response);
        assert(response.equals("{}"));
    }
    public void validateResponseAndLog(String request, String response) {
        //TODO : Customize the logic for response validation.
        request = request.replaceAll("\\r","").trim().replaceAll("\t","");
        response=response.replaceAll("\\r","").trim().replaceAll("\t","");
        if (request.equals(response)) {
            logStep(LogStatus.PASS, "Comments posted succesfully");
            logStep(LogStatus.PASS, "Actual Response : \n" + response);
        } else {
            //logStep(LogStatus.FAIL, "Actual Response : \n" + response);
            Assert.fail("Test case failed");
        }

    }
}