package pageobjects;

import org.openqa.selenium.By;

import java.security.InvalidParameterException;

import static utils.SeleniumCommands.getCommands;


public class ProductDisplayPage {

  private static final By PRODUCT_DISPLAY_PAGE = By.cssSelector("[data-locator-id='pdp-page']");
  private static final By SIZE_SMALL_BUTTON = By.cssSelector("[data-locator-id='pdp-size-s-select']");
  private static final By SIZE_MEDIUM_BUTTON = By.cssSelector("[data-locator-id='pdp-size-m-select']");
  private static final By ADD_TO_BAG_BUTTON = By.cssSelector("[data-locator-id='pdp-addToBag-submit']");


  public ProductDisplayPage() {
    getCommands().waitForAndGetVisibleElementLocated(PRODUCT_DISPLAY_PAGE);
  }

  public ProductDisplayPage selectSmallSize() {
    getCommands().waitForAndClickOnElementLocated(SIZE_SMALL_BUTTON);
    return this;
  }

  public ProductDisplayPage selectAddToBag() {
    getCommands().waitForAndClickOnElementLocated(ADD_TO_BAG_BUTTON);
    return this;
  }

  public ProductDisplayPage addProductSize(String size) {
    switch (size) {
      case "s":
        getCommands().waitForAndClickOnElementLocated(SIZE_SMALL_BUTTON);
        break;
      case "m":
        getCommands().waitForAndClickOnElementLocated(SIZE_MEDIUM_BUTTON);
        break;
      default:
        throw new InvalidParameterException("Size needs to be s or m");
    }
    selectAddToBag();

    return this;
  }
}