package com.ucsmy.jindu.manage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by ucs_zhengfucheng on 2017/6/20.
 */
@ConfigurationProperties(prefix = "enterpriseAccount")
@PropertySource("classpath:config/common.yml")
@Component
public class EnterpriseAccountConfig {

    private String clientId;
    private String clientSecret;
    private String listDetailUrl;

    private String companyCount;

    private String updateCorporDepositNoStatus;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(String companyCount) {
        this.companyCount = companyCount;
    }

    public String getUpdateCorporDepositNoStatus() {
        return updateCorporDepositNoStatus;
    }

    public void setUpdateCorporDepositNoStatus(String updateCorporDepositNoStatus) {
        this.updateCorporDepositNoStatus = updateCorporDepositNoStatus;
    }

    public String getListDetailUrl() {
        return listDetailUrl;
    }

    public void setListDetailUrl(String listDetailUrl) {
        this.listDetailUrl = listDetailUrl;
    }
}
