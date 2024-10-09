package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static stepdefs.hooks.Hooks.getDriver;
import static utils.SeleniumCommands.getCommands;
import static utils.StringUtils.extractVariantIDFromString;

public class BagPage {

    private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
    private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
    public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";
    public static final By QUANTITY_DROPDOWN_SMALL_SIZE = By.xpath("//select[contains(@name, 'S-qty-dropdown')]");
    public static final By DELETE_PRODUCT_BUTTON = By.xpath("//button[contains(@aria-label, 'remove from bag')]");
    public static final By PRODUCT_DELETED_TEXT = By.xpath("//p[@id='minicart-removal-snackbox-notification']");
    private static final By QUANTITY_DROPDOWN_MEDIUM_SIZE = By.xpath("//select[contains(@name, 'M-qty-dropdown')]");
    private static final By QUANTITY_SMALL_SIZE_VALUE = By.xpath("//select[contains(@name, 'S-qty-dropdown')]/following-sibling::span");
    private static final By MINI_BAG_LOADING_SPINNER = By.cssSelector("[data-locator-id='miniBag-loadingOverlay-read']");

    public BagPage() {
        getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);
    }

    public List<Long> getVariantIdsInBag() {
        return getBagItems().stream()
                .map(this::getVariantId)
                .collect(Collectors.toList());
    }

    private List<WebElement> getBagItems() {
        return getCommands().waitForAndGetAllVisibleElementsLocated(BAG_ITEMS);
    }

    private long getVariantId(WebElement bagItem) {
        return extractVariantIDFromString(getCommands().getAttributeFromElement(bagItem, GS_LOCATOR_ATTRIBUTE));
    }

    public BagPage setProductQuantity(Integer quantityIndex) {
        Select dropdown = new Select(getDriver().findElement(QUANTITY_DROPDOWN_SMALL_SIZE));
        dropdown.selectByIndex(quantityIndex - 1);
        this.waitForProductSpinnerToDisappear();
        return this;
    }

    public BagPage removeProduct() {
        getCommands().waitForAndClickOnElementLocated(DELETE_PRODUCT_BUTTON);
        return this;
    }

    public BagPage verifyProductIsRemoved(){
        getCommands().waitForElementToAppearThenDisappear(PRODUCT_DELETED_TEXT);
        getCommands().waitForElementToNotBeVisible(QUANTITY_DROPDOWN_MEDIUM_SIZE);
        return this;
    }

    public BagPage verifyProductQuantity(String productQuantityToCheck){
        WebElement smallSizeProductQuantity = getCommands().waitForAndGetVisibleElementLocated(QUANTITY_SMALL_SIZE_VALUE);
        assertThat(smallSizeProductQuantity.getText()).as("Quantity of product").contains(productQuantityToCheck);

        return this;
    }

    private BagPage waitForProductSpinnerToDisappear(){
        getCommands().waitForElementToAppearThenDisappear(MINI_BAG_LOADING_SPINNER);
        return this;
    }

}
