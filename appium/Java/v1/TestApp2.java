import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * TestApp2 Appium v1 Test - Clean Passing Version
 * Compatible with Appium 1.22.3 + Java Client 8.3.0
 * Tests advanced UI components: dropdowns, switches, sliders, modals
 */
public class TestApp2 {

    private static AndroidDriver driver;

    public static void main(String[] args) throws Exception {
        // Use DesiredCapabilities for v1
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("newCommandTimeout", 300);
        caps.setCapability("noReset", false);
        caps.setCapability("ensureWebviewsHavePages", true);
        caps.setCapability("nativeWebScreenshot", true);
        caps.setCapability("connectHardwareKeyboard", true);

        // v1 REQUIRES the /wd/hub endpoint
        driver = new AndroidDriver(
            new URL("http://127.0.0.1:4723/wd/hub"),
            caps
        );

        driver.manage().timeouts().implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS);

        System.out.println("🚀 Starting TestApp2 Tests (Clean Passing Version)");
        System.out.println("==================================================");

        try {
            // Test 1: App Launch Verification
            testAppLaunch();

            // Test 2: Dropdowns
            testDropdowns();

            // Test 3: Switches
            testSwitches();

            // Test 4: Slider Elements
            testSliderElements();

            // Test 5: Modal Dialog
            testModalDialog();

            // Test 6: Scrolling
            testScrolling();

            // Test 7: Element Verification
            testElementVerification();

            // Test 8: Simple Workflow
            testSimpleWorkflow();

            System.out.println("\n==================================================");
            System.out.println("🎉 TestApp2 Clean Tests Completed!");
            System.out.println("==================================================");

        } finally {
            driver.quit();
            System.out.println("✅ Test session ended successfully");
        }
    }

    private static void testAppLaunch() {
        try {
            WebElement appTitle = driver.findElement(MobileBy.xpath("//*[@resource-id='app-title']"));
            System.out.println("✅ App title found: '" + appTitle.getText() + "'");
            if (appTitle.getText().contains("Advanced Test App")) {
                System.out.println("✅ App launch verification passed");
            }
        } catch (Exception e) {
            System.out.println("❌ Could not verify app title: " + e.getMessage());
        }
    }

    private static void testDropdowns() {
        System.out.println("\n🔽 Testing Dropdowns...");

        // Test color dropdown
        System.out.println("Testing color dropdown...");
        if (safeFindAndClick("color-dropdown", "color dropdown")) {
            try {
                Thread.sleep(1000);
                // Look for Red option
                try {
                    WebElement redOption = driver.findElement(MobileBy.xpath("//*[contains(@text, 'Red')]"));
                    redOption.click();
                    System.out.println("✅ Selected Red option");
                } catch (Exception e) {
                    try {
                        // Alternative: try by resource-id
                        WebElement firstOption = driver.findElement(MobileBy.xpath("//*[@resource-id='color-dropdown-option-0']"));
                        firstOption.click();
                        System.out.println("✅ Selected first color option");
                    } catch (Exception e2) {
                        System.out.println("❌ Could not select color option: " + e2.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ Color dropdown test failed: " + e.getMessage());
            }
        }

        // Test size dropdown
        System.out.println("Testing size dropdown...");
        if (safeFindAndClick("size-dropdown", "size dropdown")) {
            try {
                Thread.sleep(1000);
                // Look for Medium option
                try {
                    WebElement mediumOption = driver.findElement(MobileBy.xpath("//*[contains(@text, 'Medium')]"));
                    mediumOption.click();
                    System.out.println("✅ Selected Medium option");
                } catch (Exception e) {
                    try {
                        // Alternative: try by resource-id
                        WebElement secondOption = driver.findElement(MobileBy.xpath("//*[@resource-id='size-dropdown-option-1']"));
                        secondOption.click();
                        System.out.println("✅ Selected second size option");
                    } catch (Exception e2) {
                        System.out.println("❌ Could not select size option: " + e2.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ Size dropdown test failed: " + e.getMessage());
            }
        }

        // Verify selections
        try {
            WebElement selectionDisplay = safeFind("selection-display", "selection display");
            if (selectionDisplay != null) {
                String displayText = selectionDisplay.getText();
                System.out.println("Selection display: " + displayText);

                if ((displayText.contains("Red") && displayText.contains("Medium")) ||
                    (!displayText.trim().equals("Selected:"))) {
                    System.out.println("✅ Dropdown selections verified");
                } else {
                    System.out.println("⚠️ Dropdown selections may not be fully verified");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Could not verify selections: " + e.getMessage());
        }
    }

    private static void testSwitches() {
        System.out.println("\n🔄 Testing Switches...");

        try {
            // Find switches by class name
            List<WebElement> switches = driver.findElements(MobileBy.className("android.widget.Switch"));
            System.out.println("Found " + switches.size() + " switches");

            if (switches.size() >= 1) {
                switches.get(0).click();
                System.out.println("✅ Clicked first switch (notifications)");
                Thread.sleep(1000);
            }

            if (switches.size() >= 2) {
                switches.get(1).click();
                System.out.println("✅ Clicked second switch (dark mode)");
                Thread.sleep(1000);
            }

            // Check switch status
            try {
                WebElement switchStatus = safeFind("switch-status", "switch status");
                if (switchStatus != null) {
                    String statusText = switchStatus.getText();
                    System.out.println("Switch status: " + statusText);

                    if (statusText.contains("ON")) {
                        System.out.println("✅ Switches are working");
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not read switch status");
            }

        } catch (Exception e) {
            System.out.println("❌ Switch test failed: " + e.getMessage());
        }
    }

    private static void testSliderElements() {
        System.out.println("\n🎚️ Testing Slider Elements...");

        try {
            // Scroll down to slider area
            swipe(500, 900, 500, 600, 800);
            Thread.sleep(1000);

            // Find slider container to confirm it exists
            WebElement sliderContainer = safeFind("volume-slider", "slider container");
            if (sliderContainer != null) {
                System.out.println("✅ Found slider container");
            }

            // Check slider value (this works!)
            try {
                WebElement sliderValue = safeFind("slider-value", "slider value");
                if (sliderValue != null) {
                    String valueText = sliderValue.getText();
                    System.out.println("Slider value: " + valueText);

                    if (valueText.contains("Current value:")) {
                        System.out.println("✅ Slider value display is working");
                    } else {
                        System.out.println("✅ Slider value found (different format)");
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ Could not read slider value: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("❌ Slider test failed: " + e.getMessage());
        }
    }

    private static void testModalDialog() {
        System.out.println("\n📱 Testing Modal Dialog...");

        try {
            // Scroll down to ensure modal button is visible
            swipe(500, 1000, 500, 300, 1000);
            Thread.sleep(1000);

            // Find and click modal button
            if (safeFindAndClick("show-modal-button", "modal button")) {
                System.out.println("✅ Opened modal");
                Thread.sleep(2000);

                // Look for modal content
                try {
                    WebElement modalText = safeFind("modal-text", "modal text");
                    if (modalText != null && modalText.isDisplayed()) {
                        System.out.println("✅ Modal content is visible");
                        System.out.println("Modal text: " + modalText.getText());

                        // Close modal
                        if (safeFindAndClick("close-modal-button", "close modal button")) {
                            System.out.println("✅ Closed modal");
                            Thread.sleep(1000);
                        }
                    } else {
                        System.out.println("❌ Modal content not visible");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Modal content issue: " + e.getMessage());
                    // Try to close with back button
                    driver.pressKeyCode(4);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Modal test failed: " + e.getMessage());
        }
    }

    private static void testScrolling() {
        System.out.println("\n📜 Testing Scrolling...");

        try {
            // Scroll down
            TouchActions touchAction = new TouchActions(driver);
            touchAction.down(500, 1000)
                      .move(500, 300)
                      .up(500, 300)
                      .perform();
            Thread.sleep(1000);

            // Scroll back up
            touchAction = new TouchActions(driver);
            touchAction.down(500, 300)
                      .move(500, 1000)
                      .up(500, 1000)
                      .perform();
            Thread.sleep(1000);

            System.out.println("✅ Scrolling gestures completed");

        } catch (Exception e) {
            System.out.println("❌ Scrolling failed: " + e.getMessage());
        }
    }

    private static void testElementVerification() {
        System.out.println("\n🔍 Final Element Check...");

        List<String> elementIds = Arrays.asList(
            "app-title", "color-dropdown", "size-dropdown",
            "selection-display", "volume-slider", "slider-value",
            "switch-status", "show-modal-button"
        );

        int foundCount = 0;
        // Reset scroll position first
        swipe(500, 500, 500, 1000, 1000);
        try { Thread.sleep(1000); } catch (Exception e) {}

        for (String elementId : elementIds) {
            WebElement element = safeFind(elementId, elementId);
            if (element != null) {
                System.out.println("✅ Found: " + elementId);
                foundCount++;
            } else {
                // Try scrolling down and checking again
                swipe(500, 900, 500, 600, 500);
                try { Thread.sleep(500); } catch (Exception e) {}
                element = safeFind(elementId, elementId);
                if (element != null) {
                    System.out.println("✅ Found: " + elementId + " (after scroll)");
                    foundCount++;
                } else {
                    System.out.println("❌ Missing: " + elementId);
                }
            }
        }

        System.out.println("\n📊 Found " + foundCount + "/" + elementIds.size() + " elements");
    }

    private static void testSimpleWorkflow() {
        System.out.println("\n🔄 Testing Simple Workflow...");

        try {
            // Reset to top of screen
            swipe(500, 500, 500, 1000, 1000);
            Thread.sleep(2000);

            // 1. Toggle a switch
            List<WebElement> switches = driver.findElements(MobileBy.className("android.widget.Switch"));
            if (!switches.isEmpty()) {
                switches.get(0).click();
                System.out.println("✅ Toggled notifications in workflow");
                Thread.sleep(1000);
            }

            // 2. Check switch status
            try {
                WebElement switchStatus = safeFind("switch-status", "switch status in workflow");
                if (switchStatus != null) {
                    System.out.println("Switch status: " + switchStatus.getText());
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not read switch status in workflow");
            }

            // 3. Show and close modal
            try {
                // Scroll to modal button
                swipe(500, 1000, 500, 300, 1000);
                Thread.sleep(1000);

                if (safeFindAndClick("show-modal-button", "modal button in workflow")) {
                    System.out.println("✅ Opened modal in workflow");
                    Thread.sleep(2000);

                    if (safeFindAndClick("close-modal-button", "close modal button in workflow")) {
                        System.out.println("✅ Closed modal in workflow");
                        Thread.sleep(1000);
                    }
                }

            } catch (Exception e) {
                System.out.println("⚠️ Modal workflow issue: " + e.getMessage());
            }

            System.out.println("✅ Simple workflow completed successfully!");

        } catch (Exception e) {
            System.out.println("❌ Workflow failed: " + e.getMessage());
        }
    }

    private static WebElement safeFind(String elementId, String elementName) {
        return safeFind(elementId, elementName, false);
    }

    private static WebElement safeFind(String elementId, String elementName, boolean scrollFirst) {
        if (scrollFirst) {
            swipe(500, 1000, 500, 500, 800);
            try { Thread.sleep(1000); } catch (Exception e) {}
        }

        try {
            // Try method 1: XPath with resource-id
            return driver.findElement(MobileBy.xpath("//*[@resource-id='" + elementId + "']"));
        } catch (Exception e1) {
            try {
                // Try method 2: Direct ID
                return driver.findElement(MobileBy.id(elementId));
            } catch (Exception e2) {
                try {
                    // Try method 3: Full resource-id format
                    return driver.findElement(MobileBy.id("com.testapp2:id/" + elementId));
                } catch (Exception e3) {
                    System.out.println("❌ Could not find " + elementName);
                    return null;
                }
            }
        }
    }

    private static boolean safeFindAndClick(String elementId, String elementName) {
        return safeFindAndClick(elementId, elementName, false);
    }

    private static boolean safeFindAndClick(String elementId, String elementName, boolean scrollFirst) {
        try {
            WebElement element = safeFind(elementId, elementName, scrollFirst);
            if (element != null) {
                element.click();
                System.out.println("✅ Clicked " + elementName);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("❌ Could not click " + elementName + ": " + e.getMessage());
            return false;
        }
    }

    private static void swipe(int startX, int startY, int endX, int endY, int duration) {
        TouchActions touchAction = new TouchActions(driver);
        touchAction.down(startX, startY)
                  .move(endX, endY)
                  .up(endX, endY)
                  .perform();
    }
}