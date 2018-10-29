package com.sp.ipv;

import com.sp.ipv.util.Images;
import com.sp.ipv.util.TestConf;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Stepdefinitions {

    private TestConf TEST_CONF = TestConf.getTestConf();


    @Before
    public void setUp() {
        System.out.println(">>>>>" + TEST_CONF.getIpvUser());
        try {
            Desktop.getDesktop().open(new File(TestConf.getTestConf().getApplicationPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TEST_CONF.getScreen().type(Images.USERNAME, TEST_CONF.getIpvUser());
        TEST_CONF.getScreen().type(Images.PASSWORD, TEST_CONF.getIpvPassword());
        try {
            TEST_CONF.getScreen().click(Images.LOGINBUTTON);
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
        }

    }

    @Given("^IPVanish is accessible$")
    public void ipvanish_is_accessible() {
        /*
        try {
            Desktop.getDesktop().open(new File(TestConf.getTestConf().getApplicationPath()));
            screen = new Screen();
            screen.type(",", Key.META);
            screen.click(Images.PROTOCOL_SELECTOR);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
        }
        */
    }

    @Given("^I am logged$")
    public void i_am_logged() throws Throwable {

    }

    @When("^I select \"([^\"]*)\"$")
    public void i_select(String protocol) throws Throwable {
        TEST_CONF.getScreen().type(",", Key.META);
        TEST_CONF.getScreen().click(Images.PROTOCOL_SELECTOR);
        TEST_CONF.getScreen().click(Images.IKEV2);
        TEST_CONF.getScreen().click(Images.CLOSEPREFERENCES);
        TEST_CONF.getScreen().click(Images.QUICKCONNECT);
        Thread.sleep(3000);
        TEST_CONF.getScreen().click(Images.CONNECT);
        TEST_CONF.getScreen().click(Images.ALLOW);
        TEST_CONF.getScreen().type(Images.KEYPASSWORD, "vico2018");
        TEST_CONF.getScreen().click(Images.OKBUTTON);
        TEST_CONF.getScreen().type(Images.NEAGENT, "vico2018");
        TEST_CONF.getScreen().click(Images.ALLOW);
    }

    @When("^attempt connection$")
    public void attempt_connection() throws Throwable {
        Thread.sleep(4000);
    }

    @Then("^I should successfully connect$")
    public void i_should_successfully_connect() throws Throwable {

    }

    @After
    public void tearDown() {
        try {
            TEST_CONF.getScreen().click(Images.ACCOUNT);
            TEST_CONF.getScreen().click(Images.LOGOUTBUTTON);
            TEST_CONF.getScreen().click(Images.CONFIRM);
            TEST_CONF.getScreen().click(Images.IPVMENU);
            TEST_CONF.getScreen().click(Images.QUIT);
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
        }
    }
}
