package org.cloudfoundry.config;

import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.model.Account;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;


public class SwiftConnector {

	private static String provider;
	private static String tenantname;
	private static String username;
	private static String password;
	private static String authurl;
	
	public SwiftConnector() {
		// TODO Auto-generated constructor stub
		setUserProvidedVcapServices();		
	}
	
	private void setUserProvidedVcapServices() {
		/* test */
        String vcap_services = System.getenv("VCAP_SERVICES");
        System.out.println("VCAP_SERVICES = " + vcap_services);
        if(vcap_services == null)return;
        JSONObject jsonObj = JSONObject.fromObject(vcap_services);
        System.out.println("vcap_service = " + jsonObj);

        JSONArray userPro = jsonObj.getJSONArray("glusterfs");

        jsonObj = JSONObject.fromObject(userPro.get(0));
        jsonObj = jsonObj.getJSONObject("credentials");
        SwiftConnector.authurl = jsonObj.getString("auth_url");
        SwiftConnector.password = jsonObj.getString("password");
        SwiftConnector.provider = jsonObj.getString("provider");
        SwiftConnector.tenantname = jsonObj.getString("tenantname");
        SwiftConnector.username = jsonObj.getString("username");
	}

	public static String getProvider() {
		return provider;
	}

	public static String getTenantname() {
		return tenantname;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static String getAuthurl() {
		return authurl;
	}

	public Account getAccount(){
		AccountConfig config = new AccountConfig();
		config.setUsername(username);
		config.setTenantName(tenantname);
		config.setPassword(password);
        config.setAuthUrl(authurl + "/tokens");
        config.setAuthenticationMethod(AuthenticationMethod.KEYSTONE);
    	Account account = new AccountFactory(config).createAccount();
    	return account;
	}
	
}
