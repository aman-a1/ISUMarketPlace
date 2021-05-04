//package com.example.myapplication;
//
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
//public class DeleteAccountTest {
//
//    @Mock
//    private IView view;
//
//    @Mock
//    private IServerRequest request;
//
//    @Test
//    public void twoRight(){
//
//        String one = "AJisu123";
//
//        String two = "AJisu123";
//
//        String three = "AJisu123";
//
//        DeleteAccountLogic logic = new DeleteAccountLogic(view, request);
//
//        logic.checkInput(one,two, three);
//
//        verify(view, times(1)).toastText("You have successfully entered the data!");
//    }
//
//    @Test
//    public void firstWrong(){
//
//        String one = "AJ";
//
//        String two = "AJisu123";
//
//        String three = "AJisu123";
//
//        DeleteAccountLogic logic = new DeleteAccountLogic(view, request);
//
//        logic.checkInput(one,two, three);
//
//        verify(view, times(1)).toastText("First entered password does not equal second!");
//    }
//
//    @Test
//    public void secondWrong(){
//
//        String one = "AJisu123";
//
//        String two = "AJ";
//
//        String three = "AJisu123";
//
//        DeleteAccountLogic logic = new DeleteAccountLogic(view, request);
//
//        logic.checkInput(one,two, three);
//
//        verify(view, times(1)).toastText("Second entered password does not equal first!");
//    }
//
//    @Test
//    public void twoWrong(){
//
//        String one = "AJ";
//
//        String two = "AJ";
//
//        String three = "AJisu123";
//
//        DeleteAccountLogic logic = new DeleteAccountLogic(view, request);
//
//        logic.checkInput(one,two, three);
//
//        verify(view, times(1)).toastText("You have not entered the correct password!");
//    }
//
//
//}
