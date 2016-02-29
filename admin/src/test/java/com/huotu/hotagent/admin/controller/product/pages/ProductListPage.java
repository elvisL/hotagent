package com.huotu.hotagent.admin.controller.product.pages;

import com.huotu.hotagent.admin.pages.AbstractPage;
import com.huotu.hotagent.service.entity.product.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by admin on 2016/2/25.
 */
public class ProductListPage extends AbstractPage {
    @FindBy(css = "table[class~=table-striped]")
    private WebElement productsTable;
    @FindBy(id = "totalRecordSpan")
    private WebElement totalRecord;
    @FindBy(id = "editProduct")
    private WebElement editProduct;
    @FindBy(css = "input[id=edit_name]")
    private WebElement editName;
    @FindBy(css = "input[id=edit_basePrice]")
    private WebElement editBasePrice;
    @FindBy(css = "textarea[id=edit_productDesc]")
    private WebElement editProductDesc;
    @FindBy(css = "button[class~=btn-primary]")
    private WebElement saveEdit;


    private List<Product> products;
    private Product mockProduct;

    public ProductListPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void validate() {
        assertThat(totalRecord.getText()).isEqualTo(String.valueOf(products.size()));
        validateResult();
        validateShowProduct();
        validateEditProduct();
    }

    //修改第一条产品记录，校验Table中的第一条是否修改成功
    private void validateEditProduct() {
        editName.sendKeys(mockProduct.getName());
        editBasePrice.sendKeys(String.valueOf(mockProduct.getBasePrice()));
        editProductDesc.sendKeys(mockProduct.getProductDesc());
        saveEdit.click();
        //获取第一行数据
        List<WebElement> firstRow = productsTable.findElements(By.xpath("//tr[1]/td"));
        //name
        assertThat(firstRow.get(1).getText()).isEqualTo(products.get(0).getName());
        //productType
        assertThat(firstRow.get(2).getText()).isEqualTo(products.get(0).getProductType().getName());
        //basePrice
        assertThat(firstRow.get(3).getText()).isEqualTo(String.valueOf(products.get(0).getBasePrice()));
        //productDesc
        assertThat(firstRow.get(4).getText()).isEqualTo(products.get(0).getProductDesc());
    }

    //点击第一行数据中 修改 按钮，显示修改界面
    private void validateShowProduct() {
        List<WebElement> tableTrs = productsTable.findElements(By.tagName("tr"));
        WebElement edit = tableTrs.get(1).findElements(By.xpath("//td/a")).get(0);
        edit.click();
        //等待直到修改窗口显示
        WebDriverWait driverWait = new WebDriverWait(webDriver,10);
        editProduct = driverWait.until(ExpectedConditions.visibilityOf(editProduct));
        System.out.println(editName.getAttribute("value"));
        System.out.println(editBasePrice.getAttribute("value"));
        System.out.println(editProductDesc.getText());
        assertThat(editName.getAttribute("value")).isEqualTo(products.get(0).getName());
        assertThat(Double.valueOf(editBasePrice.getAttribute("value"))).isEqualTo(products.get(0).getBasePrice());
        assertThat(editProductDesc.getText()).isEqualTo(products.get(0).getProductDesc());
    }

    //校验Table中的每条记录，与数据库取出的数据比对
    private void validateResult() {
        List<WebElement> tableTrs = productsTable.findElements(By.tagName("tr"));
        int itemSize = tableTrs.size() - 1;
        assertThat(itemSize).isEqualTo(products.size());
        for (int i = 0; i < itemSize; i++) {
            //获取内容区每一行的每一列
            List<WebElement> tds = tableTrs.get(i + 1).findElements(By.tagName("td"));
            //name
            assertThat(tds.get(1).getText()).isEqualTo(products.get(i).getName());
            //productType
            assertThat(tds.get(2).getText()).isEqualTo(products.get(i).getProductType().getName());
            //basePrice
            assertThat(tds.get(3).getText()).isEqualTo(String.valueOf(products.get(i).getBasePrice()));
            //productDesc
            assertThat(tds.get(4).getText()).isEqualTo(products.get(i).getProductDesc());
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Product getMockProduct() {
        return mockProduct;
    }

    public void setMockProduct(Product mockProduct) {
        this.mockProduct = mockProduct;
    }
}
