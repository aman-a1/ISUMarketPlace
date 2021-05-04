//package com.example.myapplication;
//import android.os.Build;
//import com.example.myapplication.Register.RegisterLogic;
//import com.example.myapplication.Register.RegisterPage;
//import com.example.myapplication.Register.ServerReq;
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.annotation.Config;
//import java.sql.Date;
//
//import static org.hamcrest.Matchers.any;
//import static org.mockito.ArgumentMatchers.anyObject;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@RunWith(RobolectricTestRunner.class)
//@Config(sdk = Build.VERSION_CODES.O_MR1)
////@Config(manifest=Config.NONE)
//public class RegisterTest {
//
//    String url = "http://10.24.227.48:8080/CreateUser";
//
//
//    @Before
//    public void setUpMockito() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Mock
//    RegisterPage registerPage;
//
//    @Mock
//    ServerReq req;
//
//    @Mock
//    RegisterLogic logic;
//
//
///*
//Checks if a req to server is made in case of correct arguments passed
// */
//    @Test
//    public void mockTestSuccess()
//    {
//        registerPage =  Robolectric.buildActivity(RegisterPage.class)
//                .create()
//                .resume()
//                .get();
//
//        registerPage.logic.setServer(req);
//
//
//            registerPage.FName = "test2";
//            registerPage.LName = "Test2";
//            registerPage.Email = "test2@iastate.edu";
//            registerPage.Pass ="Test1234";
//            registerPage.CPass ="Test1234";
//
//            registerPage.logic.registerUser(url,registerPage.FName,registerPage.LName,registerPage.Email,registerPage.Pass,registerPage.CPass);
//
//             verify(req, times(1)).sendToServer(anyString(), (JSONObject)anyObject(),anyString());
//
//
//
//    }
//
//    /*
//    Checks that no req to server is made in case of wrong arguments passed
//     */
//    @Test
//    public void mockTestFailure1()
//    {
//
//        registerPage =  Robolectric.buildActivity(RegisterPage.class)
//                .create()
//                .resume()
//                .get();
//        registerPage.setLogic(logic);
//        registerPage.logic.setServer(req);
//
//        JSONObject user_object = new JSONObject();
//        try {
//            long millis = System.currentTimeMillis();
//            Date date = new Date(millis);
//            user_object.put("firstName", "test2");
//            user_object.put("lastName", "Test2");
//            user_object.put("emailAddress", "test2@iastate.edu");
//            user_object.put("userName", "test2");
//            user_object.put("userPassword", "Test1234");
//            user_object.put("dateCreated",date);
//            registerPage.FName = "test2";
//            registerPage.LName = "Test2";
//            registerPage.Email = "test2@iastate.edu";
//            registerPage.Pass ="Test12345";
//            registerPage.CPass ="Test1235";
//
//            registerPage.logic.registerUser(url,registerPage.FName,registerPage.LName,registerPage.Email,registerPage.Pass,registerPage.CPass);
//            verify(req, times(0)).sendToServer("http://10.24.227.48:8080/CreateUser", user_object,"POST");
//
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//    /*
//    Checks that no req to server is made in case of wrong arguments passed
//    and thus onSuccess method is not evoked
//     */
//    @Test
//    public void serverReq()
//    {
//        registerPage =  Robolectric.buildActivity(RegisterPage.class)
//                .create()
//                .resume()
//                .get();
//        registerPage.setLogic(logic);
//        registerPage.logic.setServer(req);
//
//        registerPage.FName = "test2";
//        registerPage.LName = "Test2";
//        registerPage.Email = "test2@iastate.edu";
//        registerPage.Pass ="Test12345";
//        registerPage.CPass ="Test1235";
//        registerPage.logic.registerUser(url,registerPage.FName,registerPage.LName,registerPage.Email,registerPage.Pass,registerPage.CPass);
//
//        verify(req, times(0)).sendToServer(anyString(), (JSONObject)anyObject(),anyString());
//        verify(registerPage.logic,times(0)).onSuccess(anyString());
//
//
//    }
// /*
//    Checks that onError method is not evoked with correct argument being passed
//     */
//
//    @Test
//    public void serverReq2()
//    {
//        registerPage =  Robolectric.buildActivity(RegisterPage.class)
//                .create()
//                .resume()
//                .get();
//        registerPage.setLogic(logic);
//        registerPage.logic.setServer(req);
//
//        registerPage.FName = "Aman";
//        registerPage.LName = "Agarwal";
//        registerPage.Email = "amana1@iastate.edu";
//        registerPage.Pass ="Test12345";
//        registerPage.CPass ="Test12345";
//        registerPage.logic.registerUser(url,registerPage.FName,registerPage.LName,registerPage.Email,registerPage.Pass,registerPage.CPass);
//
//        verify(registerPage.logic,times(0)).onError(anyString());
//
//
//    }
//
//    /*
//    Checks for multiple scenarios where wrong credentials could be provided, none of which should
//    evoke the server or onSuccess method!
//     */
//    @Test
//    public void mockTestFailure2()
//    {
//
//        registerPage =  Robolectric.buildActivity(RegisterPage.class)
//                .create()
//                .resume()
//                .get();
//
//        registerPage.setLogic(logic);
//        registerPage.logic.setServer(req);
//
//        JSONObject user_object = new JSONObject();
//        try {
//            long millis = System.currentTimeMillis();
//            Date date = new Date(millis);
//            user_object.put("firstName", "test2");
//            user_object.put("lastName", "Test2");
//            user_object.put("emailAddress", "test2@iastate.edu");
//            user_object.put("userName", "test2");
//            user_object.put("userPassword", "Test1234");
//            user_object.put("dateCreated",date);
//            registerPage.FName = "test2";
//            registerPage.LName = "Test2";
//            registerPage.Email = "test2@gmail.com";
//            registerPage.Pass ="Test12345";
//            registerPage.CPass ="Test12345";
//
//            registerPage.logic.registerUser(url,registerPage.FName,registerPage.LName,registerPage.Email,registerPage.Pass,registerPage.CPass);
//
//            registerPage.FName = "";
//            registerPage.LName = "Test2";
//            registerPage.Email = "test2@giastate.edu";
//            registerPage.Pass ="Test12345";
//            registerPage.CPass ="Test12345";
//
//            registerPage.logic.registerUser(url,registerPage.FName,registerPage.LName,registerPage.Email,registerPage.Pass,registerPage.CPass);
//
//            registerPage.FName = "test2";
//            registerPage.LName = "Test2";
//            registerPage.Email = "";
//            registerPage.Pass ="Test12345";
//            registerPage.CPass ="Test12345";
//
//            registerPage.logic.registerUser(url,registerPage.FName,registerPage.LName,registerPage.Email,registerPage.Pass,registerPage.CPass);
//
//            registerPage.FName = "test2";
//            registerPage.LName = "Test2";
//            registerPage.Email = "@iastate.edu";
//            registerPage.Pass ="Test12345";
//            registerPage.CPass ="Test12345";
//
//            registerPage.logic.registerUser(url,registerPage.FName,registerPage.LName,registerPage.Email,registerPage.Pass,registerPage.CPass);
//
//
//
//
//            verify(registerPage.logic, times(0)).onSuccess(anyString());
//            verify(req, times(0)).sendToServer("http://10.24.227.48:8080/CreateUser", user_object,"POST");
//
//
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//
//}
