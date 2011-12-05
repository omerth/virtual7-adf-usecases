package com.virtual7.xdm.uc008.view.test;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class UC008InitialData extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://127.0.0.1:7101/");
		selenium.start();
	}

	@Test
	public void testUC008InitialData() throws Exception {
		selenium.open("/UC008-UC008ViewController-context-root/faces/testpages/TaskEditOverview.jspx?_afrLoop=14446958252038&_afrWindowMode=0&_adf.ctrl-state=n12sjac33_13");
		assertEquals("", selenium.getTitle());
		selenium.click("id=pt1:r1:0:soc2::content");
		selenium.select("id=pt1:r1:0:soc2::content", "label=Steven Robertson");
		selenium.click("css=option[title=\"Steven Robertson\"]");
		selenium.click("id=pt1:r1:0:cb1");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
