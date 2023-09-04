package systemData.services.UserService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetails;

import systemData.models.User.User;


public interface GlobalService {
	
	public static String getCurrentUserName() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
		  return ((UserDetails)principal).getUsername();
		} else {
		  return principal.toString();
		}
		
	};
	
//public static String getCurrentWorkerID() {
//
////		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////
////		return user.getWORKER_ID();
//	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	    return ((User)principal).getWORKER_ID();
//
//	};
	
public static String getCurrentEmpOrg() {
		
//	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	
//	return user.getEMP_ORG();
	
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ((User)principal).getEMP_ORG();
		
	};
	
	public static List<String> getCurrentUserPermissions() {
		
		List<String> perms = new ArrayList<String>();
		SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(a -> {
			perms.add(a.getAuthority());
		});
		
		return perms;
	}
	
	@SuppressWarnings("unchecked")
	public static String getCurrentUserPermissionsQueryString() {
		
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		String permissions = "";
		
		if(authorities != null) {
			for(int i = 0; i < authorities.size(); i++) {
				permissions += "'" + authorities.get(i).getAuthority() + "'";
				
				if(i != authorities.size() - 1)
					permissions += ",";
			}
		}
		
		return permissions;
	}
	
	
	public static String decrypt(String encrypted) {
		final String key = "MTS@SECRET#KEYMA";
		final String initVector = "AMYEK#TERCES@STM";
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes());
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

		try {
	        byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);
	
	        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	        
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
	
	        byte[] original = cipher.doFinal(encryptedBytes);
	        String originalString = new String(original);

        return new String(originalString);
    } catch (Exception ex) {
        ex.printStackTrace();
    }
        return null;
  }
}
