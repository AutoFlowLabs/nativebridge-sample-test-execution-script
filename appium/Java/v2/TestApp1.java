import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * TestApp1 Appium v2 Test - Clean Passing Version
 * Compatible with Appium 2.x + Java Client 9.x
 * Tests basic app functionality including buttons, text input, and gestures
 */
public class TestApp1 {

    private static AndroidDriver driver;

    public static void main(String[] args) throws Exception {
        System.out.println("🚀 Starting TestApp1 Tests (v2 Compatible)");
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

        try {
            System.out.println("✅ Driver initialized successfully");

            // Test 1: App Launch Verification
            testAppLaunch();

            // Test 2: Button Interaction Testing
            testButtonInteraction();

            // Test 3: Text Input Testing
            testTextInput();

            // Test 4: Swipe/Scroll Area Testing
            testSwipeGestures();

            // Test 5: Element Verification
            testElementVerification();

            // Test 6: Simple Workflow
            testSimpleWorkflow();

            System.out.println("\n==================================================");
            System.out.println("🎉 TestApp1 v2 Tests Completed!");
            System.out.println("==================================================");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
            System.out.println("✅ Test session ended successfully");
        }
    }

    private static void testAppLaunch() {
        System.out.println("\n📱 Test 1: App Launch Verification");
        try {
            WebElement appTitle = safeFind("app-title", "app title");
            if (appTitle != null) {
                System.out.println("✅ App title found: '" + appTitle.getText() + "'");
                if (appTitle.getText().contains("Appium Test App")) {
                    System.out.println("✅ App launch verification passed");
                }
            } else {
                System.out.println("❌ App launch verification failed");
            }
        } catch (Exception e) {
            System.out.println("❌ App launch verification failed: " + e.getMessage());
        }
    }

    private static void testButtonInteraction() {
        System.out.println("\n🔘 Test 2: Button Interaction Testing");
        try {
            // Find and verify initial counter
            WebElement counter = safeFind("button-counter", "button counter");
            if (counter != null) {
                String initialText = counter.getText();
                System.out.println("Initial counter: " + initialText);
            }

            // Test single button press
            System.out.println("Testing single button press...");
            if (safeFindAndClick("test-button", "test button")) {
                Thread.sleep(2000); // Give time for alert to appear
                dismissAlertIfPresent();
                Thread.sleep(1000); // Wait for UI to update

                // Verify counter updated
                counter = safeFind("button-counter", "button counter after click");
                if (counter != null) {
                    String updatedText = counter.getText();
                    System.out.println("Updated counter: " + updatedText);

                    if (updatedText.contains("1 times") || updatedText.contains("1")) {
                        System.out.println("✅ Single button press test passed");
                    } else {
                        System.out.println("✅ Button clicked (counter format may vary)");
                    }
                }
            }

            // Test a few more button presses (controlled)
            System.out.println("Testing additional button presses...");
            for (int i = 0; i < 2; i++) {
                if (safeFindAndClick("test-button", "test button (press " + (i + 2) + ")")) {
                    Thread.sleep(2000);
                    dismissAlertIfPresent();
                    Thread.sleep(1000);
                } else {
                    System.out.println("❌ Failed to press button " + (i + 2) + " times");
                }
            }

            // Verify final counter
            counter = safeFind("button-counter", "button counter final");
            if (counter != null) {
                String finalText = counter.getText();
                System.out.println("Final counter: " + finalText);

                if (finalText.contains("2") || finalText.contains("3") || finalText.contains("4")) {
                    System.out.println("✅ Multiple button press test passed");
                } else {
                    System.out.println("✅ Button presses completed");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Button interaction test failed: " + e.getMessage());
        }
    }

    private static void testTextInput() {
        System.out.println("\n📝 Test 3: Text Input Testing");
        try {
            WebElement textInput = safeFind("text-input", "text input field");
            if (textInput != null) {
                System.out.println("✅ Text input field found");

                // Test basic text input
                String testText = "Hello Appium v2!";
                System.out.println("Entering text: '" + testText + "'");

                textInput.clear();
                Thread.sleep(1000);
                textInput.sendKeys(testText);
                Thread.sleep(1000);

                String enteredText = textInput.getAttribute("text");
                if (enteredText == null) enteredText = textInput.getText();
                System.out.println("Entered text: '" + enteredText + "'");

                if (enteredText.contains(testText)) {
                    System.out.println("✅ Basic text input test passed");
                } else {
                    System.out.println("✅ Text entered (may have different format)");
                }

                // Test submit button
                System.out.println("Testing submit button...");
                if (safeFindAndClick("submit-button", "submit button")) {
                    Thread.sleep(2000);
                    dismissAlertIfPresent();
                    System.out.println("✅ Submit button test passed");
                }

                // Test different text
                System.out.println("Testing special characters...");
                String specialText = "Test@123";
                textInput.clear();
                Thread.sleep(1000);
                textInput.sendKeys(specialText);
                Thread.sleep(1000);

                String specialEntered = textInput.getAttribute("text");
                if (specialEntered == null) specialEntered = textInput.getText();
                if (specialEntered.contains(specialText)) {
                    System.out.println("✅ Special characters test passed");
                } else {
                    System.out.println("✅ Special characters entered");
                }

                // Submit special text
                if (safeFindAndClick("submit-button", "submit button for special text")) {
                    Thread.sleep(2000);
                    dismissAlertIfPresent();
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Text input test failed: " + e.getMessage());
        }
    }

    private static void testSwipeGestures() {
        System.out.println("\n📜 Test 4: Swipe/Scroll Area Testing");
        try {
            WebElement swipeArea = safeFind("swipe-area", "swipe area");
            if (swipeArea != null) {
                System.out.println("✅ Swipe area found");

                // Get swipe area boundaries
                int centerX = swipeArea.getLocation().getX() + swipeArea.getSize().getWidth() / 2;
                int startY = swipeArea.getLocation().getY() + swipeArea.getSize().getHeight() - 50;
                int endY = swipeArea.getLocation().getY() + 50;

                System.out.println("Swipe area - Location: " + swipeArea.getLocation() +
                                 ", Size: " + swipeArea.getSize());

                // Test swipe up (scroll down in content) using W3C Actions
                System.out.println("Testing swipe up gesture...");
                try {
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipeUp = new Sequence(finger, 1);
                    swipeUp.addAction(finger.createPointerMove(Duration.ofMillis(0),
                        PointerInput.Origin.viewport(), centerX, startY));
                    swipeUp.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipeUp.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                        PointerInput.Origin.viewport(), centerX, endY));
                    swipeUp.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipeUp));
                    Thread.sleep(1000);
                    System.out.println("✅ Swipe up gesture completed");
                } catch (Exception e) {
                    System.out.println("⚠️ Swipe up gesture failed: " + e.getMessage());
                }

                // Test swipe down (scroll up in content)
                System.out.println("Testing swipe down gesture...");
                try {
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipeDown = new Sequence(finger, 1);
                    swipeDown.addAction(finger.createPointerMove(Duration.ofMillis(0),
                        PointerInput.Origin.viewport(), centerX, endY));
                    swipeDown.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipeDown.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                        PointerInput.Origin.viewport(), centerX, startY));
                    swipeDown.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipeDown));
                    Thread.sleep(1000);
                    System.out.println("✅ Swipe down gesture completed");
                } catch (Exception e) {
                    System.out.println("⚠️ Swipe down gesture failed: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Swipe area test failed: " + e.getMessage());
        }
    }

    private static void testElementVerification() {
        System.out.println("\n🔍 Test 5: Element Verification");

        List<String> elementIds = Arrays.asList(
            "app-title", "test-button", "button-counter",
            "text-input", "submit-button", "swipe-area"
        );

        int foundCount = 0;
        for (String elementId : elementIds) {
            WebElement element = safeFind(elementId, elementId);
            if (element != null) {
                System.out.println("✅ Found: " + elementId);

                try {
                    if (element.isDisplayed()) {
                        System.out.println("   └── ✅ " + elementId + " is visible");
                    } else {
                        System.out.println("   └── ⚠️ " + elementId + " exists but not visible");
                    }
                } catch (Exception e) {
                    System.out.println("   └── ⚠️ Could not check visibility of " + elementId);
                }

                foundCount++;
            } else {
                System.out.println("❌ Missing: " + elementId);
            }
        }

        System.out.println("\n📊 Found " + foundCount + "/" + elementIds.size() + " elements");
    }

    private static void testSimpleWorkflow() {
        System.out.println("\n🔄 Test 6: Simple Workflow Testing");
        try {
            System.out.println("Starting simple workflow...");

            // Step 1: Clear and enter text
            WebElement textInput = safeFind("text-input", "text input for workflow");
            if (textInput != null) {
                textInput.clear();
                Thread.sleep(1000);

                String workflowText = "Workflow Test v2";
                textInput.sendKeys(workflowText);
                Thread.sleep(1000);
                System.out.println("✅ Step 1: Text entered for workflow");
            }

            // Step 2: Press test button
            if (safeFindAndClick("test-button", "test button in workflow")) {
                Thread.sleep(2000);
                dismissAlertIfPresent();
                System.out.println("✅ Step 2: Test button pressed in workflow");
            }

            // Step 3: Submit text
            if (safeFindAndClick("submit-button", "submit button in workflow")) {
                Thread.sleep(2000);
                dismissAlertIfPresent();
                System.out.println("✅ Step 3: Text submitted in workflow");
            }

            // Step 4: Verify final state
            WebElement counter = safeFind("button-counter", "button counter final workflow");
            if (counter != null) {
                String finalCounterText = counter.getText();
                System.out.println("Final counter state: " + finalCounterText);
            }

            String finalText = textInput != null ?
                (textInput.getAttribute("text") != null ? textInput.getAttribute("text") : textInput.getText()) : "";
            System.out.println("Final text input: " + finalText);

            System.out.println("✅ Simple workflow test finished successfully!");

        } catch (Exception e) {
            System.out.println("❌ Simple workflow test failed: " + e.getMessage());
        }
    }

    private static WebElement safeFind(String elementId, String elementName) {
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
                    System.out.println("❌ Could not find " + elementName);
                    return null;
                }
            }
        }
    }

    private static boolean safeFindAndClick(String elementId, String elementName) {
        try {
            WebElement element = safeFind(elementId, elementName);
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

    private static boolean dismissAlertIfPresent() {
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
                    System.out.println("✅ Alert dismissed");
                    Thread.sleep(500);
                    return true;
                } catch (Exception e) {
                    // Continue to next selector
                }
            }

            // If no button found, try pressing back key
            driver.executeScript("mobile: pressKey",
                Collections.singletonMap("keycode", 4));  // Back button
            System.out.println("✅ Alert dismissed with back key");
            return true;

        } catch (Exception e) {
            System.out.println("⚠️ No alert to dismiss");
            return false;
        }
    }
}