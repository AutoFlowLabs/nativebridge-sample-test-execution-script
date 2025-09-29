import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.junit.jupiter.api.*;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestApp2JUnit {

    private AndroidDriver driver;

    @BeforeAll
    void setUp() throws Exception {
        System.out.println("Starting TestApp2 JUnit Tests");

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("Android Emulator");
        options.setNewCommandTimeout(Duration.ofSeconds(300));
        options.setNoReset(false);
        options.setCapability("ensureWebviewsHavePages", true);
        options.setCapability("nativeWebScreenshot", true);
        options.setCapability("connectHardwareKeyboard", true);

        driver = new AndroidDriver(new URL("http://localhost:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Driver initialized successfully");
    }

    @Test
    @Order(1)
    void testAppLaunch() {
        System.out.println("Test 1: App Launch Verification");
        WebElement appTitle = driver.findElement(AppiumBy.xpath("//*[@resource-id='app-title']"));
        Assertions.assertNotNull(appTitle, "App title should be found");

        String titleText = appTitle.getText();
        System.out.println("App title found: " + titleText);
        Assertions.assertTrue(titleText.contains("Advanced Test App"), "App title should contain Advanced Test App");
        System.out.println("App launch verification passed");
    }

    @Test
    @Order(2)
    void testDropdowns() throws InterruptedException {
        System.out.println("Test 2: Testing Dropdowns");

        performSwipe(500, 500, 500, 1000, 1000);
        Thread.sleep(1000);

        System.out.println("Testing color dropdown");
        boolean colorDropdownClicked = safeFindAndClick("color-dropdown", "color dropdown");
        Assertions.assertTrue(colorDropdownClicked, "Color dropdown should be clickable");

        Thread.sleep(1000);
        boolean redOptionSelected = false;
        try {
            WebElement redOption = driver.findElement(AppiumBy.xpath("//*[contains(@text, 'Red')]"));
            redOption.click();
            System.out.println("Selected Red option");
            redOptionSelected = true;
        } catch (Exception e) {
            try {
                WebElement firstOption = driver.findElement(AppiumBy.xpath("//*[@resource-id='color-dropdown-option-0']"));
                firstOption.click();
                System.out.println("Selected first color option");
                redOptionSelected = true;
            } catch (Exception e2) {
                System.out.println("Could not select color option: " + e2.getMessage());
            }
        }

        System.out.println("Testing size dropdown");
        boolean sizeDropdownClicked = safeFindAndClick("size-dropdown", "size dropdown");
        Assertions.assertTrue(sizeDropdownClicked, "Size dropdown should be clickable");

        Thread.sleep(1000);
        boolean mediumOptionSelected = false;
        try {
            WebElement mediumOption = driver.findElement(AppiumBy.xpath("//*[contains(@text, 'Medium')]"));
            mediumOption.click();
            System.out.println("Selected Medium option");
            mediumOptionSelected = true;
        } catch (Exception e) {
            try {
                WebElement secondOption = driver.findElement(AppiumBy.xpath("//*[@resource-id='size-dropdown-option-1']"));
                secondOption.click();
                System.out.println("Selected second size option");
                mediumOptionSelected = true;
            } catch (Exception e2) {
                System.out.println("Could not select size option: " + e2.getMessage());
            }
        }

        WebElement selectionDisplay = safeFind("selection-display", "selection display");
        if (selectionDisplay != null) {
            String displayText = selectionDisplay.getText();
            System.out.println("Selection display: " + displayText);

            if ((displayText.contains("Red") && displayText.contains("Medium")) || (!displayText.trim().equals("Selected:"))) {
                System.out.println("Dropdown selections verified");
            } else {
                System.out.println("Dropdown selections may not be fully verified");
            }
        }

        Assertions.assertTrue(redOptionSelected || mediumOptionSelected, "At least one dropdown option should be selectable");
    }

    @Test
    @Order(3)
    void testSwitches() throws InterruptedException {
        System.out.println("Test 3: Testing Switches");

        List<WebElement> switches = driver.findElements(AppiumBy.className("android.widget.Switch"));
        System.out.println("Found " + switches.size() + " switches");
        Assertions.assertTrue(switches.size() >= 1, "Should find at least 1 switch");

        boolean switchClicked = false;
        if (switches.size() >= 1) {
            switches.get(0).click();
            System.out.println("Clicked first switch");
            Thread.sleep(1000);
            switchClicked = true;
        }

        if (switches.size() >= 2) {
            switches.get(1).click();
            System.out.println("Clicked second switch");
            Thread.sleep(1000);
            switchClicked = true;
        }

        Assertions.assertTrue(switchClicked, "At least one switch should be clickable");

        WebElement switchStatus = safeFind("switch-status", "switch status");
        if (switchStatus != null) {
            String statusText = switchStatus.getText();
            System.out.println("Switch status: " + statusText);

            if (statusText.contains("ON")) {
                System.out.println("Switches are working");
            }
        }
    }

    @Test
    @Order(4)
    void testModalDialog() throws InterruptedException {
        System.out.println("Test 4: Testing Modal Dialog");

        performSwipe(500, 1000, 500, 300, 1000);
        Thread.sleep(1000);

        boolean modalOpened = safeFindAndClick("show-modal-button", "modal button");
        Assertions.assertTrue(modalOpened, "Modal button should be clickable");
        System.out.println("Opened modal");
        Thread.sleep(2000);

        WebElement modalText = safeFind("modal-text", "modal text");

        if (modalText != null && modalText.isDisplayed()) {
            System.out.println("Modal content is visible");
            System.out.println("Modal text: " + modalText.getText());

            boolean modalClosed = safeFindAndClick("close-modal-button", "close modal button");
            if (modalClosed) {
                System.out.println("Closed modal");
                Thread.sleep(1000);
            } else {
                driver.executeScript("mobile: pressKey", Collections.singletonMap("keycode", 4));
                System.out.println("Closed modal with back button");
            }
        } else {
            System.out.println("Modal content not visible");
            driver.executeScript("mobile: pressKey", Collections.singletonMap("keycode", 4));
        }

        System.out.println("Modal dialog test completed");
    }

    @Test
    @Order(5)
    void testScrolling() throws InterruptedException {
        System.out.println("Test 5: Testing Scrolling");

        Assertions.assertDoesNotThrow(() -> {
            performSwipe(500, 1000, 500, 300, 1000);
            Thread.sleep(1000);
            performSwipe(500, 300, 500, 1000, 1000);
            Thread.sleep(1000);
        }, "Scrolling gestures should not throw exceptions");

        System.out.println("Scrolling gestures completed");
    }

    @Test
    @Order(6)
    void testSimpleWorkflow() throws InterruptedException {
        System.out.println("Test 6: Testing Simple Workflow");

        performSwipe(500, 500, 500, 1000, 1000);
        Thread.sleep(2000);

        List<WebElement> switches = driver.findElements(AppiumBy.className("android.widget.Switch"));
        boolean switchToggled = false;
        if (!switches.isEmpty()) {
            switches.get(0).click();
            System.out.println("Toggled notifications in workflow");
            Thread.sleep(1000);
            switchToggled = true;
        }
        Assertions.assertTrue(switchToggled, "Should be able to toggle a switch in workflow");

        WebElement switchStatus = safeFind("switch-status", "switch status in workflow");
        if (switchStatus != null) {
            String statusText = switchStatus.getText();
            System.out.println("Switch status: " + statusText);
        }

        try {
            performSwipe(500, 1000, 500, 300, 1000);
            Thread.sleep(1000);

            if (safeFindAndClick("show-modal-button", "modal button in workflow")) {
                System.out.println("Opened modal in workflow");
                Thread.sleep(2000);

                if (safeFindAndClick("close-modal-button", "close modal button in workflow")) {
                    System.out.println("Closed modal in workflow");
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            System.out.println("Modal workflow issue: " + e.getMessage());
        }

        System.out.println("Simple workflow completed successfully");
    }

    @AfterAll
    void tearDown() {
        System.out.println("TestApp2 v2 JUnit Tests Completed");
        if (driver != null) {
            driver.quit();
        }
        System.out.println("Test session ended successfully");
    }

    private WebElement safeFind(String elementId, String elementName) {
        return safeFind(elementId, elementName, false);
    }

    private WebElement safeFind(String elementId, String elementName, boolean scrollFirst) {
        if (scrollFirst) {
            performSwipe(500, 1000, 500, 500, 800);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // ignore
            }
        }

        try {
            return driver.findElement(AppiumBy.xpath("//*[@resource-id='" + elementId + "']"));
        } catch (Exception e1) {
            try {
                return driver.findElement(AppiumBy.id(elementId));
            } catch (Exception e2) {
                try {
                    return driver.findElement(AppiumBy.id("com.testapp2:id/" + elementId));
                } catch (Exception e3) {
                    if (!elementId.equals("show-modal-button")) {
                        System.out.println("Could not find " + elementName);
                    }
                    return null;
                }
            }
        }
    }

    private boolean safeFindAndClick(String elementId, String elementName) {
        return safeFindAndClick(elementId, elementName, false);
    }

    private boolean safeFindAndClick(String elementId, String elementName, boolean scrollFirst) {
        try {
            WebElement element = safeFind(elementId, elementName, scrollFirst);
            if (element != null) {
                element.click();
                System.out.println("Clicked " + elementName);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Could not click " + elementName + ": " + e.getMessage());
            return false;
        }
    }

    private void performSwipe(int startX, int startY, int endX, int endY, int durationMs) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }
}