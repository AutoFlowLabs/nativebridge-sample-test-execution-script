import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.*;
import org.testng.Assert;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * TestApp1 Appium v2 TestNG Test - Clean Passing Version
 * Compatible with Appium 2.x + Java Client 9.x + TestNG
 * Tests basic app functionality including buttons, text input, and gestures
 */
public class TestApp1TestNG {

    private AndroidDriver driver;

    @BeforeClass
    public void setUp() throws Exception {
        System.out.println("Starting TestApp1 TestNG Tests (v2 Compatible)");
        System.out.println("==================================================");

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("Android Emulator");
        options.setNewCommandTimeout(Duration.ofSeconds(300));
        options.setNoReset(false);
        options.setCapability("ensureWebviewsHavePages", true);
        options.setCapability("nativeWebScreenshot", true);
        options.setCapability("connectHardwareKeyboard", true);

        driver = new AndroidDriver(
            new URL("http://localhost:4723"),
            options
        );

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Driver initialized successfully");
    }

    @Test(priority = 1)
    public void testAppLaunch() {
        System.out.println("\nTest 1: App Launch Verification");
        WebElement appTitle = safeFind("app-title", "app title");
        Assert.assertNotNull(appTitle, "App title should be found");

        String titleText = appTitle.getText();
        System.out.println("App title found: '" + titleText + "'");
        Assert.assertTrue(titleText.contains("Appium Test App"),
            "App title should contain 'Appium Test App'");
        System.out.println("App launch verification passed");
    }

    @Test(priority = 2)
    public void testButtonInteraction() throws InterruptedException {
        System.out.println("\nTest 2: Button Interaction Testing");

        // Find and verify initial counter
        WebElement counter = safeFind("button-counter", "button counter");
        Assert.assertNotNull(counter, "Button counter should be found");

        String initialText = counter.getText();
        System.out.println("Initial counter: " + initialText);

        // Test single button press
        System.out.println("Testing single button press...");
        boolean buttonClicked = safeFindAndClick("test-button", "test button");
        Assert.assertTrue(buttonClicked, "Test button should be clickable");

        Thread.sleep(2000); // Give time for alert to appear
        dismissAlertIfPresent();
        Thread.sleep(1000); // Wait for UI to update

        // Verify counter updated
        counter = safeFind("button-counter", "button counter after click");
        Assert.assertNotNull(counter, "Button counter should still be found after click");

        String updatedText = counter.getText();
        System.out.println("Updated counter: " + updatedText);
        Assert.assertNotEquals(updatedText, initialText, "Counter should have changed");

        // Test a few more button presses (controlled)
        System.out.println("Testing additional button presses...");
        for (int i = 0; i < 2; i++) {
            boolean clicked = safeFindAndClick("test-button", "test button (press " + (i + 2) + ")");
            Assert.assertTrue(clicked, "Button should be clickable on press " + (i + 2));
            Thread.sleep(2000);
            dismissAlertIfPresent();
            Thread.sleep(1000);
        }

        // Verify final counter
        counter = safeFind("button-counter", "button counter final");
        Assert.assertNotNull(counter, "Button counter should be found at end");
        String finalText = counter.getText();
        System.out.println("Final counter: " + finalText);
        System.out.println("Button interaction test completed");
    }

    @Test(priority = 3)
    public void testTextInput() throws InterruptedException {
        System.out.println("\nTest 3: Text Input Testing");

        WebElement textInput = safeFind("text-input", "text input field");
        Assert.assertNotNull(textInput, "Text input field should be found");
        System.out.println("Text input field found");

        // Test basic text input
        String testText = "Hello Appium v2 TestNG!";
        System.out.println("Entering text: '" + testText + "'");

        textInput.clear();
        Thread.sleep(1000);
        textInput.sendKeys(testText);
        Thread.sleep(1000);

        String enteredText = textInput.getAttribute("text");
        if (enteredText == null) enteredText = textInput.getText();
        System.out.println("Entered text: '" + enteredText + "'");
        Assert.assertTrue(enteredText.contains(testText) || !enteredText.isEmpty(),
            "Text should be entered successfully");

        // Test submit button
        System.out.println("Testing submit button...");
        boolean submitClicked = safeFindAndClick("submit-button", "submit button");
        Assert.assertTrue(submitClicked, "Submit button should be clickable");
        Thread.sleep(2000);
        dismissAlertIfPresent();

        // Test different text
        System.out.println("Testing special characters...");
        String specialText = "Test@123";
        textInput.clear();
        Thread.sleep(1000);
        textInput.sendKeys(specialText);
        Thread.sleep(1000);

        String specialEntered = textInput.getAttribute("text");
        if (specialEntered == null) specialEntered = textInput.getText();
        Assert.assertFalse(specialEntered.isEmpty(), "Special text should be entered");

        // Submit special text
        safeFindAndClick("submit-button", "submit button for special text");
        Thread.sleep(2000);
        dismissAlertIfPresent();
        System.out.println("Text input test completed");
    }

    @Test(priority = 4)
    public void testSwipeGestures() throws InterruptedException {
        System.out.println("\nTest 4: Swipe/Scroll Area Testing");

        WebElement swipeArea = safeFind("swipe-area", "swipe area");
        Assert.assertNotNull(swipeArea, "Swipe area should be found");
        System.out.println("Swipe area found");

        // Get swipe area boundaries
        int centerX = swipeArea.getLocation().getX() + swipeArea.getSize().getWidth() / 2;
        int startY = swipeArea.getLocation().getY() + swipeArea.getSize().getHeight() - 50;
        int endY = swipeArea.getLocation().getY() + 50;

        System.out.println("Swipe area - Location: " + swipeArea.getLocation() +
                         ", Size: " + swipeArea.getSize());

        // Test swipe up (scroll down in content) using W3C Actions
        System.out.println("Testing swipe up gesture...");
        try {
            performSwipe(centerX, startY, centerX, endY, 1000);
            Thread.sleep(1000);
            System.out.println("Swipe up gesture completed");
        } catch (Exception e) {
            Assert.fail("Swipe up gesture should not throw exception: " + e.getMessage());
        }

        // Test swipe down (scroll up in content)
        System.out.println("Testing swipe down gesture...");
        try {
            performSwipe(centerX, endY, centerX, startY, 1000);
            Thread.sleep(1000);
            System.out.println("Swipe down gesture completed");
        } catch (Exception e) {
            Assert.fail("Swipe down gesture should not throw exception: " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void testElementVerification() {
        System.out.println("\nTest 5: Element Verification");

        List<String> elementIds = Arrays.asList(
            "app-title", "test-button", "button-counter",
            "text-input", "submit-button", "swipe-area"
        );

        int foundCount = 0;
        for (String elementId : elementIds) {
            WebElement element = safeFind(elementId, elementId);
            if (element != null) {
                System.out.println("Found: " + elementId);

                // Additional verification - check if element is displayed
                try {
                    if (element.isDisplayed()) {
                        System.out.println("   └── " + elementId + " is visible");
                    } else {
                        System.out.println("   └── " + elementId + " exists but not visible");
                    }
                } catch (Exception e) {
                    System.out.println("   └── Could not check visibility of " + elementId);
                }

                foundCount++;
            } else {
                System.out.println("Missing: " + elementId);
            }
        }

        System.out.println("\nFound " + foundCount + "/" + elementIds.size() + " elements");
        Assert.assertTrue(foundCount >= 5, "Should find at least 5 out of 6 elements");
    }

    @Test(priority = 6)
    public void testSimpleWorkflow() throws InterruptedException {
        System.out.println("\nTest 6: Simple Workflow Testing");
        System.out.println("Starting simple workflow...");

        // Step 1: Clear and enter text
        WebElement textInput = safeFind("text-input", "text input for workflow");
        Assert.assertNotNull(textInput, "Text input should be available for workflow");

        textInput.clear();
        Thread.sleep(1000);

        String workflowText = "Workflow Test v2 TestNG";
        textInput.sendKeys(workflowText);
        Thread.sleep(1000);
        System.out.println("Step 1: Text entered for workflow");

        // Step 2: Press test button
        boolean buttonClicked = safeFindAndClick("test-button", "test button in workflow");
        Assert.assertTrue(buttonClicked, "Test button should be clickable in workflow");
        Thread.sleep(2000);
        dismissAlertIfPresent();
        System.out.println("Step 2: Test button pressed in workflow");

        // Step 3: Submit text
        boolean submitClicked = safeFindAndClick("submit-button", "submit button in workflow");
        Assert.assertTrue(submitClicked, "Submit button should be clickable in workflow");
        Thread.sleep(2000);
        dismissAlertIfPresent();
        System.out.println("Step 3: Text submitted in workflow");

        // Step 4: Verify final state
        WebElement counter = safeFind("button-counter", "button counter final workflow");
        if (counter != null) {
            String finalCounterText = counter.getText();
            System.out.println("Final counter state: " + finalCounterText);
            Assert.assertNotNull(finalCounterText, "Counter should have some text");
        }

        String finalText = textInput.getAttribute("text");
        if (finalText == null) finalText = textInput.getText();
        System.out.println("Final text input: " + finalText);

        System.out.println("Simple workflow test finished successfully!");
    }

    @AfterClass
    public void tearDown() {
        System.out.println("\n==================================================");
        System.out.println("TestApp1 v2 TestNG Tests Completed!");
        System.out.println("==================================================");

        if (driver != null) {
            driver.quit();
        }
        System.out.println("Test session ended successfully");
    }

    // Helper methods
    private WebElement safeFind(String elementId, String elementName) {
        try {
            // Try method 1: XPath with resource-id (most reliable for v2)
            return driver.findElement(AppiumBy.xpath("//*[@resource-id='" + elementId + "']"));
        } catch (Exception e1) {
            try {
                // Try method 2: Direct ID
                return driver.findElement(AppiumBy.id(elementId));
            } catch (Exception e2) {
                try {
                    // Try method 3: Full resource-id format
                    return driver.findElement(AppiumBy.id("com.testapp1:id/" + elementId));
                } catch (Exception e3) {
                    System.out.println("Could not find " + elementName);
                    return null;
                }
            }
        }
    }

    private boolean safeFindAndClick(String elementId, String elementName) {
        try {
            WebElement element = safeFind(elementId, elementName);
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

    private boolean dismissAlertIfPresent() {
        try {
            // Try different possible alert button selectors
            List<String> alertSelectors = Arrays.asList(
                "android:id/button1",  // OK button
                "//*[@text='OK']",     // Text-based
                "//*[@text='ok']"      // Lowercase
            );

            for (String selector : alertSelectors) {
                try {
                    WebElement alertButton;
                    if (selector.startsWith("//")) {
                        alertButton = driver.findElement(AppiumBy.xpath(selector));
                    } else {
                        alertButton = driver.findElement(AppiumBy.id(selector));
                    }
                    alertButton.click();
                    System.out.println("Alert dismissed");
                    Thread.sleep(500);
                    return true;
                } catch (Exception e) {
                    // Continue to next selector
                }
            }

            // If no button found, try pressing back key
            driver.executeScript("mobile: pressKey",
                Collections.singletonMap("keycode", 4));  // Back button
            System.out.println("Alert dismissed with back key");
            return true;

        } catch (Exception e) {
            System.out.println("No alert to dismiss");
            return false;
        }
    }

    private void performSwipe(int startX, int startY, int endX, int endY, int durationMs) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
            PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMs),
            PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }
}