package com.uottawa.colbytodd.mealer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.google.firebase.database.core.Context;

import org.junit.Test;
import org.mockito.Mock;

public class ExampleUnitTest {

        Meal myObjectUnderTest = new Meal("pasta","main","italian","20");
        private static final String MEAL_STRING = "pasta";
        private static final String TYPE_STRING = "main";
        private static final String CUISINE_STRING = "italian";
        private static final String PRICE_STRING = "20";


        Order myObjectUnderTest2 = new Order("pasta","democook@hotmail.com");
        private static final String EMAIL_STRING = "democook@hotmail.com";


        @Mock
        Context mMockContext;

        @Test
        public void getMealNameTest() {
                String result = myObjectUnderTest.getMealName();
                assertThat(result, is(MEAL_STRING));
        }

        @Test
        public void getMealTypeTest() {
                String result = myObjectUnderTest.getMealType();
                assertThat(result,is(TYPE_STRING));
        }

        @Test
        public void getMealCuisineTest() {
                String result = myObjectUnderTest.getCuisineType();
                assertThat(result, is(CUISINE_STRING));
        }

        @Test
        public void getMealPriceTest() {
                String result = myObjectUnderTest.getPrice();
                assertThat(result, is(PRICE_STRING));
        }

        @Test
        public void getOrderMealTest() {
                String result = myObjectUnderTest2.getMeal();
                assertThat(result, is(MEAL_STRING));
        }
        @Test
        public void getOrderEmailTest() {
                String result = myObjectUnderTest2.getClientEmail();
                assertThat(result, is(EMAIL_STRING));
        }
}
