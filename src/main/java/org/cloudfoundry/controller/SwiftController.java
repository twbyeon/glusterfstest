package org.cloudfoundry.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cloudfoundry.config.SwiftConnector;
import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.exception.CommandException;
import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SwiftController {
	
	@Autowired
	SwiftConnector swiftConnector;
	/**
	 * Glsterfs-Swift test
	 */
	@RequestMapping(value="/swifttest", method=RequestMethod.POST)
	public String saveFile1(@RequestParam MultipartFile attchFile,Model model) {
		
		String result = "Success";
		//SwiftConnector swiftConnector = new SwiftConnector();
    	Account account = swiftConnector.getAccount();
    	
    	Container container = account.getContainer("images");
    	
    	if(!container.exists()){
    		container.create();
    		container.makePublic();
    	}
    	
        Collection<Container> containers = account.list();
        for (Container currentContainer : containers) {
            System.out.println("container list : " + currentContainer.getName());
            
        }
        
        //upload
        String fileName = System.currentTimeMillis()+"-"+attchFile.getOriginalFilename();
        StoredObject object = container.getObject(fileName);

        String imagePath=account.getPublicURL() + object.getPath();
        try {
        	object.uploadObject(attchFile.getInputStream());
		} catch (CommandException e) {
			result = e.toString()+"(413:Storage quota limit,....)";
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        model.addAttribute("image", imagePath);
        model.addAttribute("info", result);
		return "swift";
	}
	
	/*@RequestMapping(value="/del", method=RequestMethod.GET)
	public String delete(Model model) {
		
		
		//SwiftConnector swiftConnector = new SwiftConnector();
    	Account account = swiftConnector.getAccount();
    	
    	Container container = account.getContainer("images");
    	
    	if(!container.exists()){
    		container.create();
    		container.makePublic();
    	}
    	
        Collection<Container> containers = account.list();
        for (Container currentContainer : containers) {
            System.out.println("container list : " + currentContainer.getName());
            
        }
        
        //upload
        //StoredObject object = container.getObject("test" + File.separator + "attchFile"+System.currentTimeMillis()+".jpg");
        try {
        	for(String fileName : delList()){
        	
        		StoredObject object = container.getObject(fileName);
        	
        		object.delete();
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        //model.addAttribute("image", imagePath);
        model.addAttribute("info", "Success");
		return "swift";
	}
	
	public List<String> delList(){
		List<String> list = new ArrayList<String>();
		
		list.add("attchFile1437024457786.jpg");
		list.add("attchFile1437024465464.jpg");
		
		return list;

	}*/
}

