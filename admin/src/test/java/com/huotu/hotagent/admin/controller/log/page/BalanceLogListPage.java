package com.huotu.hotagent.admin.controller.log.page;

import com.huotu.hotagent.admin.pages.AbstractPage;
import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.service.entity.log.BalanceLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by admin on 2016/2/25.
 */
public class BalanceLogListPage extends AbstractPage {
    @FindBy(css = "table[class~=table-striped]")
    private WebElement balanceLogTable;
    @FindBy(css = ".pagination li")
    private List<WebElement> pageBtn;
    @FindBy(css = "input[name=agentName]")
    private WebElement agentName;
    @FindBy(css = "input[name=customerName]")
    private WebElement customerName;
    @FindBy(css = "input[name=beginTime]")
    private WebElement beginTime;
    @FindBy(css = "input[name=endTime]")
    private WebElement endTime;
    @FindBy(className = "submitSearch")
    private WebElement submitSearch;
    @FindBy(className = "resetSearch")
    private WebElement resetSearch;

    private List<BalanceLog> balanceLogs;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public BalanceLogListPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void validate() {
        //1.校验显示的数据(Table中的数据和分页相关数据)
        validateResult();
        //2.校验分页功能


    }

    private void validateResult() {
        List<WebElement> tableTrs = balanceLogTable.findElements(By.xpath("//tr"));
        int itemSize = tableTrs.size() - 1;
        assertThat(itemSize).isEqualTo(Math.min(SysConstant.DEFAULT_PAGE_SIZE, balanceLogs.size()));
        for (int i = 0; i < itemSize; i++) {
            //获取内容区每一行的每一列
            List<WebElement> tds = tableTrs.get(i + 1).findElements(By.tagName("td"));
            //agentName
            assertThat(tds.get(1).getText()).isEqualTo((balanceLogs.get(i).getAgent() != null ? balanceLogs.get(i).getAgent().getName() : ""));
            //customerName
            assertThat(tds.get(2).getText()).isEqualTo(balanceLogs.get(i).getCustomer() != null ? balanceLogs.get(i).getCustomer().getName() : "");
            //money
            assertThat(Double.valueOf(tds.get(3).getText())).isEqualTo(balanceLogs.get(i).getMoney());
            //createTime
            assertThat(tds.get(4).getText()).isEqualTo(sf.format(balanceLogs.get(i).getCreateTime()));
            //memo
            assertThat(tds.get(5).getText()).isEqualTo(balanceLogs.get(i).getMemo());
        }

    }

    public List<BalanceLog> getBalanceLogs() {
        return balanceLogs;
    }

    public void setBalanceLogs(List<BalanceLog> balanceLogs) {
        this.balanceLogs = balanceLogs;
    }
}
