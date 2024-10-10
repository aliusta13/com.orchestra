package utilities;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import static org.junit.Assert.assertTrue;

public class ReusableMethods {

    private static final int TIMEOUT = 10;

    private static Actions actions = new Actions(Driver.getDriver());

    // ========================= Screenshot Methods =========================//

    /**
     * Takes a screenshot and returns the file path
     *
     * @param name the name of the screenshot file
     * @return the file path of the screenshot
     */
    public static String takeScreenshot(String name) throws IOException {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + "_" + date + ".png";
        File finalDestination = new File(target);
        FileUtils.copyFile(source, finalDestination);
        return target;
    }

    // ========================= Window Handling Methods =========================//

    /**
     * Switches to a window with a given title
     *
     * @param targetTitle the title of the window to switch to
     */
    public static void switchToWindowWithTitle(String targetTitle) {
        String currentWindow = Driver.getDriver().getWindowHandle();
        for (String windowHandle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(windowHandle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(currentWindow); // switch back if title not
        // found
    }

    // ========================= Hover and Interactions =========================//

    /**
     * Hovers over a web element
     *
     * @param element the WebElement to hover over
     */
    public static void hoverOver(WebElement element) {

        actions.moveToElement(element).perform();
    }

    /**
     * Sends TAB key press
     */
    public static void pressTab() {

        actions.sendKeys(Keys.TAB).perform();
    }

    // ========================= Drag and Drop Methods =========================//

    /**
     * Performs a drag and drop operation from source element to target element
     *
     * @param source the WebElement to drag
     * @param target the WebElement to drop to
     */
    public static void dragAndDrop(WebElement source, WebElement target) {

        actions.dragAndDrop(source, target).perform();
    }

    /**
     * Drag and drop an element by offset values
     *
     * @param element the WebElement to drag
     * @param xOffset the horizontal offset
     * @param yOffset the vertical offset
     */
    public static void dragAndDropByOffset(WebElement element, int xOffset, int yOffset) {

        actions.dragAndDropBy(element, xOffset, yOffset).perform();
    }

    // ========================= File Upload Methods =========================//

    /**
     * Uploads a file by sending the file path to an input element
     *
     * @param filePath      the full file path to upload
     * @param uploadElement the input WebElement (usually input[type='file'])
     */
    public static void uploadFile(String filePath, WebElement uploadElement) {

        uploadElement.sendKeys(filePath); // Directly sends the file path to the input
        // element
    }

    // ========================= Text Retrieval Methods =========================//

    /**
     * Gets text from a list of web elements
     *
     * @param elements the list of WebElements
     * @return the list of text values of elements
     */
    public static List<String> getElementTexts(List<WebElement> elements) {
        List<String> texts = new ArrayList<>();
        for (WebElement el : elements) {
            if (!el.getText().isEmpty()) {
                texts.add(el.getText());
            }
        }
        return texts;
    }

    /**
     * Gets text from elements located by a specific locator
     *
     * @param locator the By locator to find the elements
     * @return the list of text values of elements
     */
    public static List<String> getElementTexts(By locator) {
        List<WebElement> elements = Driver.getDriver().findElements(locator);
        return getElementTexts(elements);
    }

    // ========================= Waiting Methods =========================//

    /**
     * Waits for a web element to be visible
     *
     * @param element the WebElement to wait for
     * @param timeout in seconds
     * @return the visible WebElement
     */
    public static WebElement waitForElementVisibility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for an element located by a locator to be visible
     *
     * @param locator the By locator of the element
     * @param timeout in seconds
     * @return the visible WebElement
     */
    public static WebElement waitForElementVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for a web element to be clickable
     *
     * @param element the WebElement to wait for
     * @param timeout in seconds
     * @return the clickable WebElement
     */
    public static WebElement waitForElementToBeClickable(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Fluent wait for a web element
     *
     * @param element the WebElement to wait for
     * @param timeout in seconds
     * @return the WebElement found
     */
    public static WebElement fluentWait(WebElement element, int timeout) {
        FluentWait<WebDriver> wait = new FluentWait<>(Driver.getDriver()).withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        return wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return element;
            }
        });
    }

    /**
     * Waits for the page to load completely
     *
     * @param timeout in seconds
     */
    public static void waitForPageToLoad(long timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
                .equals("complete"));
    }

    // ========================= Scrolling Methods =========================//

    /**
     * Scrolls the page until the given element is in view
     *
     * @param element the WebElement to scroll to
     */
    public static void scrollToElement(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scrolls vertically by a specified amount
     *
     * @param scrollY the amount to scroll in the Y-axis
     */
    public static void scrollByAmount(int scrollY) {

        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollBy(0," + scrollY + ")");
    }

    // ========================= Utility Methods =========================//

    /**
     * Converts a date string from "dd-MM-yyyy" to "yyyy-MM-dd" format
     *
     * @param date the original date string
     * @return the formatted date string
     */
    public static String formatDateString(String date) {
        String day = date.substring(0, 2);
        String month = date.substring(3, 5);
        String year = date.substring(6);
        return year + "-" + month + "-" + day;
    }

    /**
     * Simple hard wait method using Thread.sleep
     *
     * @param seconds the number of seconds to wait
     */
    public static void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clicks on an element with a retry mechanism for a specified timeout
     *
     * @param element the WebElement to click on
     * @param timeout the maximum time to keep retrying
     */
    public static void clickWithTimeout(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                hardWait(1); // wait 1 second and retry
            }
        }
    }

    public static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue(element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);

        }
    }

    // ========================= Click Methods =========================//

    public static void clickRandomlyOnScreen(WebDriver driver) {
        // Tarayıcı penceresinin genişliği ve yüksekliğini alın
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();

        // Rastgele x ve y koordinatları oluşturun
        Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);

        // Rastgele bir koordinata tıklama işlemi gerçekleştirin
        Actions actions = new Actions(driver);
        actions.moveByOffset(x, y).doubleClick().perform();
    }

    public static void clickWithText(String text) {

        Driver.getDriver().findElement(By.xpath("//*[text()='" + text + "']")).click();
    }

    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitAndClick(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                hardWait(2);
            }
        }
    }

    // ========================= Sort Methods =========================//

    public static void sortingLowToHighVerification(List<WebElement> priceElements) {
        List<Integer> prices = new ArrayList<>();

        for (WebElement priceElement : priceElements) {

            String priceText = priceElement.getText().replaceAll("\\D", "");

            try {
                int price = Integer.parseInt(priceText);
                price = price / 100;
                prices.add(price);
            } catch (NumberFormatException e) {
                System.out.println("Could not parse price: " + priceText);
            }

        }
        prices.sort(Integer::compareTo);

        List<Integer> originalPrices = new ArrayList<>(prices);
        Assert.assertEquals(prices, originalPrices);
    }

    public static void sortingHighToLowVerification(List<WebElement> priceElements) {

        List<Integer> prices = new ArrayList<>();

        for (WebElement priceElement : priceElements) {

            String priceText = priceElement.getText().replaceAll("\\D", "");
            try {
                int price = Integer.parseInt(priceText);
                price = price / 100;
                prices.add(price);

            } catch (NumberFormatException e) {
                System.out.println("Could not parse price: " + priceText);
            }

        }
        prices.sort(Comparator.reverseOrder());

        List<Integer> originalPrices = new ArrayList<>(prices);
        Assert.assertEquals(prices, originalPrices);
    }

    // ========================= Select Methods =========================//

    /**
     * @param element
     * @param check
     */
    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    /**
     * Selects a random value from a dropdown list and returns the selected Web Element
     *
     * @param select
     * @return
     */
    public static WebElement selectRandomTextFromDropdown(Select select) {
        Random random = new Random();
        List<WebElement> weblist = select.getOptions();
        int optionIndex = 1 + random.nextInt(weblist.size() - 1);
        select.selectByIndex(optionIndex);
        return select.getFirstSelectedOption();
    }

    public static void selectAnItemFromDropdown(WebElement item, String selectItem) {
        hardWait(5);
        Select select = new Select(item);
        for (int i = 0; i < select.getOptions().size(); i++) {
            if (select.getOptions().get(i).getText().equalsIgnoreCase(selectItem)) {
                select.getOptions().get(i).click();
                break;
            }
        }
    }

    public static void selectByVisibleText(WebElement elements, String text) {
        Select select = new Select(elements);
        select.selectByVisibleText(text);
    }

    public static void selectByIndex(WebElement elements, int index) {
        Select select = new Select(elements);
        select.selectByIndex(index);
    }

    public static void selectByValue(WebElement elements, String value) {
        Select select = new Select(elements);
        List<WebElement> elementCount = select.getOptions();
        select.selectByValue(value);
    }

    public static WebElement findMenuItemByTextMerchant(WebDriver driver, String menuText) {
        //!!!!!!!FYİ:KONTROL ETTİM ADMİN'DE DE ÇALIŞIYOR.

        // <li> elementlerini bul class="navbar-nav" içinde
        List<WebElement> menuItems = driver.findElements(By.xpath("//*[@class='navbar-nav']//li"));

        // Metni içeren WebElement'i bulmak için bir değişken oluştur
        WebElement foundElement = null;

        // Her bir menü öğesini kontrol et
        for (WebElement item : menuItems) {
            // Metni al ve başındaki/sonundaki boşlukları temizle
            String normalizedText = item.getText().trim();

            // Eğer normalize edilmiş text beklenen text ile eşleşiyorsa
            if (normalizedText.equals(menuText)) {
                foundElement = item; // Metni içeren WebElement'i bul
                break; // Döngüden çık
            }
        }

        // WebElement'i döndür (null olabilir, bu nedenle null kontrolü yapılmalı)
        return foundElement;
    }

    public static WebElement findElementByText(String text) {
        try {
            // XPath ile sayfadaki her türlü element içinde verilen metni arar ancak asagi yukari kaydirma yapmaz
            WebElement element = Driver.getDriver().findElement(By.xpath("//*[contains(text(), '" + text + "')]"));
            return element;
        } catch (Exception e) {
            System.out.println("Element bulunamadı: " + text);
            return null;
        }
    }

    public static WebElement firstElementByTextandNumber(String text, int sayi) {
        try {
            // XPath ile element birden fazla ise sayi girilerek aranir
            WebElement element = Driver.getDriver().findElement(By.xpath("(//*[contains(text(), '" + text + "')])[" + sayi + "]"));
            return element;

        } catch (Exception e) {
            System.out.println("Element bulunamadı: " + text + sayi);
        }
        return null;

    }

    public static boolean paymentNotificationCatcher() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        String script = "return document.evaluate(\"//*[contains(text(), 'success')]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;";
        WebElement notification = (WebElement) js.executeScript(script);
        boolean flag = false;

        if (notification != null) {

            flag = true;

        }

        return flag;
    }


    // Metne göre elementi bulup sayfayı o elemente kaydıran fonksiyon
    public static void scrollToText(WebDriver driver, String text) {
        // Metni içeren elementin XPath ile bulunması
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), '" + text + "')]"));

        // Bulunan elemente scroll yapma işlemi
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        // Bulunan elementin varligini kontrol eder
        assertTrue(driver.findElement(By.xpath("//*[text()='" + text + "']")).isDisplayed());
    }

    // -----------------Gül
    public static boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void sendKeysWithActions(WebElement element, String text) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).click().sendKeys(text).build().perform();
    }
    // Dropdown'dan seçim yap ve text alana metin gir
    public static void selectFromDropdownAndEnterText(WebElement dropdown, String optionText, WebElement textbox, String text) {
        Actions actions = new Actions(Driver.getDriver());

        // 1. Dropdown'u aç
        actions.moveToElement(dropdown).click().perform();

        // 2. Dinamik olarak verilen metin ile dropdown seçeneğini bul
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[normalize-space(text())='" + optionText + "']")
        ));

        // 3. Bulunan dropdown seçeneğine tıkla
        actions.moveToElement(option).click().perform();

        // 4. Textbox'a tıklayıp, verilen metni gir
        actions.moveToElement(textbox).click().sendKeys(text).perform();
    }

	public static void switchToWindowWithTitleContains(String targetWord) {
		String currentWindow = Driver.getDriver().getWindowHandle();
		for (String windowHandle : Driver.getDriver().getWindowHandles()) {
			Driver.getDriver().switchTo().window(windowHandle);
			ReusableMethods.hardWait(1);
			if (Driver.getDriver().getTitle().contains(targetWord)) {
				return;
			}
		}
		Driver.getDriver().switchTo().window(currentWindow); // switch back if title not
		// contains the word
	}


}



