//package com.example.myapplication;
//
//import android.content.Context;
//import static org.mockito.Mockito.*;
//
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.MockitoRule;
//import org.mockito.quality.Strictness;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.times;
//
//@RunWith(MockitoJUnitRunner.class)
//public class SecurityTest {
//
//    @Mock
//    private IView view;
//
//    @Mock
//    private IServerRequest request;
//
//    @Test
//    public void notValidNewPass(){
//        String one = "CorrectPassword";
//        String two = "aj";
//        String three = "CorrectPassword";
//
//        SecurityLogic logic = new SecurityLogic(view, request);
//        logic.checkInputChange(one, two, three);
//        verify(view, times(1)).toastText("Your new password has not met all conditions: contains at least 8 characters and at most 20 characters, contains at least one digit, contains at least one upper case alphabet, and contains at least one lower case alphabet.");
//
//    }
//
//    @Test
//    public void notValidCurrentPass(){
//        String one = "correct";
//        String two = "AJisu123";
//        String three = "CorrectPassword";
//
//        SecurityLogic logic = new SecurityLogic(view, request);
//        logic.checkInputChange(one, two, three);
//        verify(view, times(1)).toastText("You have incorrectly entered your current password!");
//    }
//
//    @Test
//    public void notValidBoth(){
//        String one = "correct";
//        String two = "aj";
//        String three = "CorrectPassword";
//
//        SecurityLogic logic = new SecurityLogic(view, request);
//        logic.checkInputChange(one, two, three);
//        verify(view, times(1)).toastText("You have incorrectly typed your current passsword and your new one does not" +
//                " follow the following requirements: contains at least 8 characters and at most 20 characters, It contains at least one digit, It contains at least one upper case alphabet, It contains at least one lower case alphabet.");
//    }
//
//    @Test
//    public void bothCorrect(){
//        String one = "CorrectPassword";
//        String two = "AJisu123";
//        String three = "CorrectPassword";
//
//        SecurityLogic logic = new SecurityLogic(view, request);
//        logic.checkInputChange(one, two, three);
//        verify(view, times(1)).toastText("You have successfully entered the data!");
//    }
//
//}
