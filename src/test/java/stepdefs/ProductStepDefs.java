package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.BagPage;
import pageobjects.ProductDisplayPage;
import stepdefs.hooks.Hooks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefs {

  private final WebDriver driver;
  private Long productId;

  public ProductStepDefs(){
    this.driver = Hooks.getDriver();
  }

  @Given("the user is on a product page")
  public void theUserIsOnAProductPage() {
    driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
    productId = 39654522814667L;
    new ProductDisplayPage();
  }

  @When("adding the product to the Bag")
  public void addingTheProductToTheBag() {
    ProductDisplayPage productDisplayPage = new ProductDisplayPage();
    productDisplayPage.selectSmallSize();
    productDisplayPage.selectAddToBag();
  }

  @Then("the product should appear in the Bag")
  public void theProductShouldAppearInTheBag() {
    BagPage bagPage = new BagPage();
    List<Long> variantIds = bagPage.getVariantIdsInBag();
    assertThat(variantIds).as("Expected product is in Bag").contains(productId);
  }

  @Given("there are products in the bag")
  public void thereAreProductsInTheBag() {
    this.addingTheProductToTheBag();
    //waiting for the page to load
    new BagPage();
    driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
    ProductDisplayPage productDisplayPage = new ProductDisplayPage();
    productDisplayPage.addProductSize("m");
  }

  @When("I remove a product")
  public void removeProduct() {
    BagPage bagPage = new BagPage();
    bagPage.removeProduct();
  }

  @Then("the product is removed from the bag")
  public void verifyRemovalOfProduct() {
    BagPage bagPage = new BagPage();
    bagPage.verifyProductIsRemoved();
  }

  @When("I add quantity")
  public void addQuantity() {
    BagPage bagPage = new BagPage();
    bagPage.setProductQuantity(2);
  }

  @Then("product quantity is increased")
  public void verifyProductQuantityIncreased() {
    BagPage bagPage = new BagPage();
    bagPage.verifyProductQuantity("2");
  }

  @When("I remove quantity")
  public void removeQuantity() {
    this.addQuantity();
    BagPage bagPage = new BagPage();
    bagPage.setProductQuantity(1);
  }

  @Then("product quantity is removed from the bag")
  public void verifyProductQuantityDecreased() {
    BagPage bagPage = new BagPage();
    bagPage.verifyProductQuantity("1");
  }

}
