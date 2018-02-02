package com.wys.ssm.base.util;

/**
 * Class Name : BackendDomainUtils<br>
 * 
 * Description : 获取后台domain 域名配置<br>
 * 
 * @author yangwb
 * @version $Revision$
 * @see
 *
 */
public class BackendDomainUtils {
    /**
     * 后台domain name
     */
    private String domainName;
    /**
     * 后台domain url
     */
    private String domainUrl;
    
    /**
     * 加密KEY
     */
    private String cryptKey;
    
    

    public String getCryptKey() {
		return cryptKey;
	}

	public void setCryptKey(String cryptKey) {
		this.cryptKey = cryptKey;
	}

	public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

}
