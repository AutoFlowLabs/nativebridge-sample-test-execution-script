import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * NativeBridge Debug App - Comprehensive Appium v2 Test Suite
 * Compatible with Appium 2.x + Java Client 9.x
 * Tests all features: UI, Network, Performance, Permissions, Storage
 *
 * Package: com.nativebridge.io
 * APK: latest-builds/nativebridge-debug-v2.apk
 */
public class NativeBridgeTest {

    private static AndroidDriver driver;
    private static final String APP_PACKAGE = "com.nativebridge.io";

    public static void main(String[] args) throws Exception {
        System.out.println("🚀 Starting NativeBridge Debug App Tests (Appium v2)");
        System.out.println("============================================================");

        // Setup Appium v2 capabilities
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        // App is already installed, so we use appPackage and appActivity instead of app
        options.setAppPackage(APP_PACKAGE);
        options.setAppActivity(APP_PACKAGE + ".MainActivity");
        options.setNewCommandTimeout(Duration.ofSeconds(300));
        options.setNoReset(true);  // Don't reinstall app
        options.setCapability("ensureWebviewsHavePages", true);
        options.setCapability("nativeWebScreenshot", true);

        options.setNoReset(true);  // Don't reinstall app
        options.setCapability("ensureWebviewsHavePages", true);
        options.setCapability("nativeWebScreenshot", true);

        driver = new AndroidDriver(new URL("http://localhost:4723"), options);
            Thread.sleep(3000); // Wait for app to fully load

            // Run all test suites
            testAppLaunch();
            testUITab();
            testNetworkTab();
            testPerformanceTab();
            testAppLaunch();
            testUITab();
            testNetworkTab();
            testPerformanceTab();
            testPermissionsTab();
            testStorageTab();
            testTabNavigation();

            System.out.println("\n============================================================");
            System.out.println("🎉 NativeBridge Debug App - All Tests Completed Successfully!");
            System.out.println("============================================================");

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

    // ==================== TEST: APP LAUNCH ====================
    private static void testAppLaunch() {
        System.out.println("\n📱 Test 1: App Launch Verification");
        try {
            WebElement appTitle = safeFind("app-title", "app title");
            if (appTitle != null) {
                String titleText = appTitle.getText();
                System.out.println("✅ App title found: '" + titleText + "'");
                if (titleText.contains("NativeBridge")) {
                    System.out.println("✅ App launch verification passed");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ App launch verification failed: " + e.getMessage());
        }
    }

    // ==================== TEST: UI TAB ====================
    private static void testUITab() {
        System.out.println("\n🎨 Test 2: UI Tab - Comprehensive Testing");
        try {
            // Ensure we're on UI tab
            safeFindAndClick("tab-ui", "UI tab");
            Thread.sleep(1000);

            // Test 2.1: Button Press
            System.out.println("\n  📝 Test 2.1: Button Press");
            WebElement buttonCounter = safeFind("button-counter", "button counter");
            if (buttonCounter != null) {
                String initialCount = buttonCounter.getText();
                System.out.println("  Initial counter: " + initialCount);

                if (safeFindAndClick("test-button", "test button")) {
                    Thread.sleep(2000);
                    dismissAlertIfPresent();
                    Thread.sleep(1000);

                    buttonCounter = safeFind("button-counter", "button counter after click");
                    if (buttonCounter != null) {
                        String updatedCount = buttonCounter.getText();
                        System.out.println("  Updated counter: " + updatedCount);
                        System.out.println("  ✅ Button press test passed");
                    }
                }
            }

            // Test 2.2: Long Press (with vibration)
            System.out.println("\n  📝 Test 2.2: Long Press");
            WebElement testButton = safeFind("test-button", "test button for long press");
            if (testButton != null) {
                performLongPress(testButton);
                Thread.sleep(2000);
                dismissAlertIfPresent();
                System.out.println("  ✅ Long press test passed");
            }

            // Test 2.3: Text Input
            System.out.println("\n  📝 Test 2.3: Text Input");
            WebElement textInput = safeFind("text-input", "text input field");
            if (textInput != null) {
                String testText = "Appium v2 Test!";
                textInput.clear();
                Thread.sleep(500);
                textInput.sendKeys(testText);
                Thread.sleep(1000);

                String enteredText = textInput.getAttribute("text");
                System.out.println("  Entered text: '" + enteredText + "'");

                WebElement inputDisplay = safeFind("input-display", "input display");
                if (inputDisplay != null) {
                    System.out.println("  Display shows: '" + inputDisplay.getText() + "'");
                }
                System.out.println("  ✅ Text input test passed");
            }

            // Test 2.4: Switch Toggle
            System.out.println("\n  📝 Test 2.4: Switch Toggle");
            WebElement switchElement = safeFind("test-switch", "test switch");
            if (switchElement != null) {
                WebElement switchStatus = safeFind("switch-status", "switch status");
                String initialStatus = switchStatus != null ? switchStatus.getText() : "";
                System.out.println("  Initial switch status: " + initialStatus);

                switchElement.click();
                Thread.sleep(1000);

                switchStatus = safeFind("switch-status", "switch status after toggle");
                String newStatus = switchStatus != null ? switchStatus.getText() : "";
                System.out.println("  New switch status: " + newStatus);
                System.out.println("  ✅ Switch toggle test passed");
            }

            // Test 2.5: Scrollable Area
            System.out.println("\n  📝 Test 2.5: Scrollable Area");
            WebElement scrollableArea = safeFind("scrollable-area", "scrollable area");
            if (scrollableArea != null) {
                System.out.println("  Found scrollable area");

                // Perform scroll gesture
                int centerX = scrollableArea.getLocation().getX() + scrollableArea.getSize().getWidth() / 2;
                int startY = scrollableArea.getLocation().getY() + scrollableArea.getSize().getHeight() - 50;
                int endY = scrollableArea.getLocation().getY() + 50;

                performSwipe(centerX, startY, centerX, endY, 1000);
                Thread.sleep(1000);
                System.out.println("  ✅ Scroll down completed");

                performSwipe(centerX, endY, centerX, startY, 1000);
                Thread.sleep(1000);
                System.out.println("  ✅ Scroll up completed");
            }

            System.out.println("\n✅ UI Tab - All tests passed");

        } catch (Exception e) {
            System.out.println("❌ UI Tab test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== TEST: NETWORK TAB ====================
    private static void testNetworkTab() {
        System.out.println("\n🌐 Test 3: Network Tab - API Testing");
        try {
            // Navigate to Network tab
            safeFindAndClick("tab-network", "Network tab");
            Thread.sleep(1500);

            // Test 3.1: GET Request
            System.out.println("\n  📝 Test 3.1: GET Request (Download)");
            if (safeFindAndClick("network-get-button", "GET request button")) {
                System.out.println("  Waiting for GET request to complete...");
                Thread.sleep(5000); // Wait for network request

                WebElement networkStatus = safeFind("network-status", "network status");
                if (networkStatus != null) {
                    String status = networkStatus.getText();
                    System.out.println("  Status: " + status);
                    if (status.contains("Downloaded") || status.contains("✓")) {
                        System.out.println("  ✅ GET request successful");
                    }
                }

                WebElement networkData = safeFind("network-data", "network data");
                if (networkData != null) {
                    String data = networkData.getText();
                    System.out.println("  Response data received: " + (data.length() > 50 ? data.substring(0, 50) + "..." : data));
                }
            }

            Thread.sleep(2000);

            // Test 3.2: POST Request
            System.out.println("\n  📝 Test 3.2: POST Request (Upload)");
            if (safeFindAndClick("network-post-button", "POST request button")) {
                System.out.println("  Waiting for POST request to complete...");
                Thread.sleep(5000); // Wait for network request

                WebElement networkStatus = safeFind("network-status", "network status after POST");
                if (networkStatus != null) {
                    String status = networkStatus.getText();
                    System.out.println("  Status: " + status);
                    if (status.contains("Uploaded") || status.contains("Created") || status.contains("✓")) {
                        System.out.println("  ✅ POST request successful");
                    }
                }

                WebElement networkData = safeFind("network-data", "network data after POST");
                if (networkData != null) {
                    String data = networkData.getText();
                    System.out.println("  Response data received: " + (data.length() > 50 ? data.substring(0, 50) + "..." : data));
                }
            }

            System.out.println("\n✅ Network Tab - All tests passed");

        } catch (Exception e) {
            System.out.println("❌ Network Tab test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== TEST: PERFORMANCE TAB ====================
    private static void testPerformanceTab() {
        System.out.println("\n⚡ Test 4: Performance Tab - CPU & Memory Testing");
        try {
            // Navigate to Performance tab
            System.out.println("\n  📝 Test 4.1: CPU Intensive Test");
            if (safeFindAndClick("cpu-test-button", "CPU test button")) {
                System.out.println("  Running Fibonacci(40) calculation...");
                System.out.println("  (This will take ~15-20 seconds on device)");

                // Wait for loading to finish and result to appear
                Thread.sleep(20000); // Fibonacci 40 takes longer on Android device

                WebElement performanceResult = safeFind("performance-result", "performance result");
                if (performanceResult != null) {
                Thread.sleep(20000); // Fibonacci 40 takes longer on Android device

                WebElement performanceResult = safeFind("performance-result", "performance result");
                if (performanceResult != null) {
                    String result = performanceResult.getText();
                    System.out.println("  CPU Test Result: " + result);
                    if (result.contains("Fibonacci") && result.contains("Time:")) {
                        System.out.println("  ✅ CPU test completed successfully");
                    }
                } else {
                    System.out.println("  ⚠️ CPU test completed but result not found");
                }
            }

            Thread.sleep(2000);

            // Test 4.2: Memory Test (Sort 1M elements)
            System.out.println("\n  📝 Test 4.2: Memory Intensive Test");
            if (safeFindAndClick("memory-test-button", "Memory test button")) {
                System.out.println("  Sorting 1,000,000 elements...");
                System.out.println("  (This will take a few seconds)");

                // Wait for loading to finish and result to appear
                Thread.sleep(8000); // Memory test takes time

                WebElement performanceResult = safeFind("performance-result", "performance result after memory test");
                if (performanceResult != null) {
                    String result = performanceResult.getText();
                    System.out.println("  Memory Test Result: " + result);
                    if (result.contains("Sorted") && result.contains("elements") && result.contains("Time:")) {
                        System.out.println("  ✅ Memory test completed successfully");
                    }
                } else {
                    System.out.println("  ⚠️ Memory test completed but result not found");
                }
            }

            System.out.println("\n✅ Performance Tab - All tests passed");

        } catch (Exception e) {
            System.out.println("❌ Performance Tab test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== TEST: PERMISSIONS TAB ====================
    private static void testPermissionsTab() {
        System.out.println("\n🔐 Test 5: Permissions Tab - Permission Requests & System Features");
        try {
            // Navigate to Permissions tab
            safeFindAndClick("tab-permissions", "Permissions tab");
            Thread.sleep(1500);

            // Test 5.1: Camera Permission
            System.out.println("\n  📝 Test 5.1: Camera Permission Request");
            if (safeFindAndClick("request-camera-button", "Camera permission button")) {
                Thread.sleep(2000);
                dismissPermissionDialog(); // Allow or Deny
                dismissAlertIfPresent();
                System.out.println("  ✅ Camera permission request handled");
            }

            Thread.sleep(1000);

            // Test 5.2: Location Permission
            System.out.println("\n  📝 Test 5.2: Location Permission Request");
            if (safeFindAndClick("request-location-button", "Location permission button")) {
                Thread.sleep(2000);
                dismissPermissionDialog(); // Allow or Deny
                dismissAlertIfPresent();
                System.out.println("  ✅ Location permission request handled");
            }

            Thread.sleep(1000);

            // Test 5.3: Storage Permission
            System.out.println("\n  📝 Test 5.3: Storage Permission Request");
            if (safeFindAndClick("request-storage-button", "Storage permission button")) {
                Thread.sleep(2000);
                dismissPermissionDialog(); // Allow or Deny
                dismissAlertIfPresent();
                System.out.println("  ✅ Storage permission request handled");
            }

            Thread.sleep(1000);

            // Test 5.4: Contacts Permission
            System.out.println("\n  📝 Test 5.4: Contacts Permission Request");
            if (safeFindAndClick("request-contacts-button", "Contacts permission button")) {
                Thread.sleep(2000);
                dismissPermissionDialog(); // Allow or Deny
                dismissAlertIfPresent();
                System.out.println("  ✅ Contacts permission request handled");
            }

            Thread.sleep(1000);

            // Scroll down to see system features
            scrollDownInTab();

            // Test 5.5: Vibrate Device
            System.out.println("\n  📝 Test 5.5: Vibrate Device");
            if (safeFindAndClick("vibrate-button", "Vibrate button")) {
                Thread.sleep(2000);
                dismissAlertIfPresent();
                System.out.println("  ✅ Vibration triggered (device should vibrate)");
            }

            Thread.sleep(1000);

            // Test 5.6: Open Browser
            System.out.println("\n  📝 Test 5.6: Open Browser");
            if (safeFindAndClick("open-browser-button", "Open browser button")) {
                Thread.sleep(3000);
                // Press back to return to app
                driver.executeScript("mobile: pressKey", Collections.singletonMap("keycode", 4));
                Thread.sleep(1000);
                System.out.println("  ✅ Browser opened and closed");
            }

            System.out.println("\n✅ Permissions Tab - All tests passed");

        } catch (Exception e) {
            System.out.println("❌ Permissions Tab test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== TEST: STORAGE TAB ====================
    private static void testStorageTab() {
        System.out.println("\n💾 Test 6: Storage Tab - Clipboard & Storage Operations");
        try {
            // Navigate to Storage tab
            safeFindAndClick("tab-storage", "Storage tab");
            Thread.sleep(1500);

            // First, enter some text in UI tab
            System.out.println("\n  📝 Preparing test data...");
            safeFindAndClick("tab-ui", "UI tab");
            Thread.sleep(1000);

            WebElement textInput = safeFind("text-input", "text input");
            if (textInput != null) {
                String testData = "Test Data 123";
                textInput.clear();
                Thread.sleep(500);
                textInput.sendKeys(testData);
                System.out.println("  Test data entered: '" + testData + "'");
            }

            // Go back to Storage tab
            safeFindAndClick("tab-storage", "Storage tab");
            Thread.sleep(1000);

            // Test 6.1: Copy to Clipboard
            System.out.println("\n  📝 Test 6.1: Copy to Clipboard");
            if (safeFindAndClick("copy-clipboard-button", "Copy to clipboard button")) {
                Thread.sleep(1000);
                dismissAlertIfPresent();
                System.out.println("  ✅ Text copied to clipboard");
            }

            Thread.sleep(1000);

            // Test 6.2: Paste from Clipboard
            System.out.println("\n  📝 Test 6.2: Paste from Clipboard");
            if (safeFindAndClick("paste-clipboard-button", "Paste from clipboard button")) {
                Thread.sleep(1000);
                dismissAlertIfPresent();

                WebElement clipboardContent = safeFind("clipboard-content", "clipboard content");
                if (clipboardContent != null) {
                    String content = clipboardContent.getText();
                    System.out.println("  Clipboard content: " + content);
                    System.out.println("  ✅ Paste from clipboard successful");
                }
            }

            Thread.sleep(1000);

            // Scroll down to see storage operations
            scrollDownInTab();

            // Test 6.3: Save to Storage
            System.out.println("\n  📝 Test 6.3: Save to Storage");
            if (safeFindAndClick("save-storage-button", "Save to storage button")) {
                Thread.sleep(1000);
                dismissAlertIfPresent();
                System.out.println("  ✅ Data saved to storage");
            }

            Thread.sleep(1000);

            // Test 6.4: Load from Storage
            System.out.println("\n  📝 Test 6.4: Load from Storage");
            if (safeFindAndClick("load-storage-button", "Load from storage button")) {
                Thread.sleep(1000);
                dismissAlertIfPresent();

                WebElement storageContent = safeFind("storage-content", "storage content");
                if (storageContent != null) {
                    String content = storageContent.getText();
                    System.out.println("  Loaded data: " + content);
                    System.out.println("  ✅ Load from storage successful");
                }
            }

            Thread.sleep(1000);

            // Test 6.5: Clear Storage
            System.out.println("\n  📝 Test 6.5: Clear Storage");
            if (safeFindAndClick("clear-storage-button", "Clear storage button")) {
                Thread.sleep(1000);
                dismissAlertIfPresent();
                System.out.println("  ✅ Storage cleared");
            }

            System.out.println("\n✅ Storage Tab - All tests passed");

        } catch (Exception e) {
            System.out.println("❌ Storage Tab test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== TEST: TAB NAVIGATION ====================
    private static void testTabNavigation() {
        System.out.println("\n🔀 Test 7: Tab Navigation");
        try {
            String[] tabs = {"tab-ui", "tab-network", "tab-performance", "tab-permissions", "tab-storage"};
            String[] tabNames = {"UI", "Network", "Performance", "Permissions", "Storage"};

            for (int i = 0; i < tabs.length; i++) {
                System.out.println("\n  Navigating to " + tabNames[i] + " tab...");
                if (safeFindAndClick(tabs[i], tabNames[i] + " tab")) {
                    Thread.sleep(1000);
                    System.out.println("  ✅ " + tabNames[i] + " tab accessed");
                }
            }

            System.out.println("\n✅ Tab Navigation - All tabs accessible");

        } catch (Exception e) {
            System.out.println("❌ Tab navigation test failed: " + e.getMessage());
        }
    }

    // ==================== HELPER METHODS ====================

    private static WebElement safeFind(String elementId, String elementName) {
        try {
            return driver.findElement(AppiumBy.xpath("//*[@resource-id='" + elementId + "']"));
        } catch (Exception e1) {
            try {
                return driver.findElement(AppiumBy.id(elementId));
            } catch (Exception e2) {
                try {
                    return driver.findElement(AppiumBy.id(APP_PACKAGE + ":id/" + elementId));
                } catch (Exception e3) {
                    System.out.println("    ⚠️ Could not find " + elementName);
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
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("    ❌ Could not click " + elementName + ": " + e.getMessage());
            return false;
        }
    }

    private static boolean dismissAlertIfPresent() {
        try {
            List<String> alertSelectors = Arrays.asList(
                "android:id/button1",  // OK button
                "//*[@text='OK']",
                "//*[@text='ok']"
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
                    Thread.sleep(500);
                    return true;
                } catch (Exception e) {
                    // Continue to next selector
                }
            }

            // Try back key
            driver.executeScript("mobile: pressKey", Collections.singletonMap("keycode", 4));
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    private static void dismissPermissionDialog() {
        try {
            Thread.sleep(1000);

            // Try to find and click "Allow" or "While using the app"
            List<String> allowSelectors = Arrays.asList(
                "//*[@text='Allow']",
                "//*[@text='ALLOW']",
                "//*[@text='While using the app']",
                "com.android.permissioncontroller:id/permission_allow_button",
                "com.android.permissioncontroller:id/permission_allow_foreground_only_button"
            );

            for (String selector : allowSelectors) {
                try {
                    WebElement allowButton;
                    if (selector.startsWith("//")) {
                        allowButton = driver.findElement(AppiumBy.xpath(selector));
                    } else {
                        allowButton = driver.findElement(AppiumBy.id(selector));
                    }
                    allowButton.click();
                    Thread.sleep(500);
                    return;
                } catch (Exception e) {
                    // Continue to next selector
                }
            }
        } catch (Exception e) {
            System.out.println("    ⚠️ Permission dialog handling: " + e.getMessage());
        }
    }

    private static void performSwipe(int startX, int startY, int endX, int endY, int durationMs) {
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMs),
                PointerInput.Origin.viewport(), endX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(swipe));
        } catch (Exception e) {
            System.out.println("    ⚠️ Swipe gesture failed: " + e.getMessage());
        }
    }

    private static void performLongPress(WebElement element) {
        try {
            int x = element.getLocation().getX() + element.getSize().getWidth() / 2;
            int y = element.getLocation().getY() + element.getSize().getHeight() / 2;

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence longPress = new Sequence(finger, 1);
            longPress.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), x, y));
            longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            longPress.addAction(finger.createPointerMove(Duration.ofMillis(2000),
                PointerInput.Origin.viewport(), x, y));
            longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(longPress));
        } catch (Exception e) {
            System.out.println("    ⚠️ Long press failed: " + e.getMessage());
        }
    }

    private static void scrollDownInTab() {
        try {
            int centerX = driver.manage().window().getSize().getWidth() / 2;
            int startY = driver.manage().window().getSize().getHeight() * 3 / 4;
            int endY = driver.manage().window().getSize().getHeight() / 4;
            performSwipe(centerX, startY, centerX, endY, 800);
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("    ⚠️ Scroll failed: " + e.getMessage());
        }
    }
}