package systemData.security;

import java.util.Date;
import java.util.Hashtable;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.jboss.logging.Logger;

import systemData.models.User.WLSConfig;

import java.util.ArrayList;
import java.util.List;

public class WLSJmxInterface {

	public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	  public static final String MBEAN_SERVER = "weblogic.management.mbeanservers.domainruntime";
	  public static final String JNDI_ROOT = "/jndi/";
	  //public static final String DEFAULT_PROTOCOL = "t3s";
	  public static final String PROTOCOL_PROVIDER_PACKAGES = "weblogic.management.remote";
	  //This how we get our DomainRuntimeService, this is where DomainConfigurationMBeans exists
	  public static final String DOMAIN_MBEAN_NAME = "com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean";
	  private static MBeanServerConnection connection;
	  private static ObjectName defaultAuthenticator;
	  private static ObjectName[] authenticationProviders;
	  private static String authenticatorName="DefaultAuthenticator";
	  
	  private static final Logger log = Logger
				.getLogger(WLSJmxInterface.class);
	  
	  //@PostConstruct
	  public void postConstruct(WLSConfig wlsConfig) {
	          try {
	        	connection = null;  
	        	defaultAuthenticator = null;
	            String host = wlsConfig.getSERVER_IP();
	            String port = wlsConfig.getSERVER_PORT();
	            String username = wlsConfig.getSERVER_USERNAME();
	            String password = wlsConfig.getSERVER_PASSWORD();
	            String DEFAULT_PROTOCOL = wlsConfig.getPROTOCOL();
	              //Decryption dec=null;
	              /*MTSPermission mtsPer = MTSPermission.getInstance();
	              if(mtsPer.getServerIp()==null)
	                  mtsPer = new MTSPermission();
	              host = mtsPer.getServerIp();
	              port = mtsPer.getServerPort();
	               dec=new Decryption();
	              username = mtsPer.getServerUsername();
	              password =  "dec.decrypt(mtsPer.getServerPassword());*/
	/*            SecurityAppModule am =
	      (SecurityAppModule)Configuration.createRootApplicationModule("MTS.security.SecurityAppModule", "SecurityAppModuleLocal");
	              ViewObject webLogicVo = am.findViewObject("WebLogicConfView1");
	            Row[] r = webLogicVo.getAllRowsInRange();
	                if(r.length>0)
	                {
	                  host = r[0].getAttribute("ServerIp").toString();
	                  port = r[0].getAttribute("ServerPort").toString();
	                  username = r[0].getAttribute("ServerUsername").toString();
	                  password = r[0].getAttribute("ServerPassword").toString();
	                }
	            Configuration.releaseRootApplicationModule(am, true);0
	*/
	              Hashtable h = new Hashtable();
	              JMXServiceURL serviceURL;
	              
	              //System.out.println("WLS Protocol: " + DEFAULT_PROTOCOL);
	              
	              serviceURL =
	                      new JMXServiceURL(DEFAULT_PROTOCOL, host, Integer.valueOf(port).intValue(),
	                                        "/jndi/weblogic.management.mbeanservers.domainruntime");

	              h.put("java.naming.security.principal", username);
	              h.put("java.naming.security.credentials", password);
	              h.put("jmx.remote.protocol.provider.pkgs",
	                    "weblogic.management.remote");

	              //Creating a JMXConnector to connect to JMX
	              JMXConnector connector =
	                  JMXConnectorFactory.connect(serviceURL, h);

	              connection = connector.getMBeanServerConnection();

	              /****
	                We Get Objects by creating ObjectName with it's Qualified name.
	                The constructor take a String of the full Qualified name of the MBean
	                We then use connection to get Attribute out of this ObjectName but specifying a String of
	                this Attribute
	                *****/
	              

	              ObjectName configurationMBeans=
	                  new ObjectName(DOMAIN_MBEAN_NAME);
	              ObjectName domain =
	                  (ObjectName)connection.getAttribute(configurationMBeans, "DomainConfiguration");

	              ObjectName security =
	                  (ObjectName)connection.getAttribute(domain, "SecurityConfiguration");

	              ObjectName realm =
	                  (ObjectName)connection.getAttribute(security, "DefaultRealm");

	              authenticationProviders =
	                      (ObjectName[])connection.getAttribute(realm,
	                                                            "AuthenticationProviders");

	              for (int i = 0; i < authenticationProviders.length; i++) {
	                  String name =
	                      (String)connection.getAttribute(authenticationProviders[i],
	                                                      "Name");

	                  if (name.equals(authenticatorName))
	                      defaultAuthenticator = authenticationProviders[i];
	              }
	          } catch (Exception e) {
	              e.printStackTrace();
	            //  throw new RuntimeException(e);
	          }
	      }
	      
	    public boolean changeUserPassword(String username, String oldPassword,String newPassword) {
	    	//System.out.println("ana hena ahooooo gwa el method");
	        try {
	            if (!username.equalsIgnoreCase("weblogic")) {
	                connection.invoke(defaultAuthenticator, "changeUserPassword",
	                                  new Object[] { username, oldPassword, newPassword },
	                                  new String[] { "java.lang.String","java.lang.String","java.lang.String" });
	           
	               // System.out.println("ana hena ahooooo gwa el if");
	            }
	            
	           // System.out.println("ana hena ahooooo ha return true");
	            return true;
	        } catch (Exception e) {
	            //addMessage(e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
//	            if(e.getMessage() != null && e.getMessage().contains("Validation of old password failed"))
//	                addMessage(StringManager.getString("MTS.security.ModelBundle", "OLD_PASSWORD_WRONG", ""), FacesMessage.SEVERITY_ERROR);
//	            else
//	            addMessage(StringManager.getString("MTS.security.ModelBundle", "OPERATION_FAILED", ""), FacesMessage.SEVERITY_ERROR);
	        	log.error("Invalid username or password" + (new Date()).toString());
	        	e.printStackTrace();
	        }
	        return false;
	    }
	    

	    public static boolean addUser(String username, String psw, String desc) {
	            try {
	                /** As of connection.getAttribute you can use connection.invoke to invoke an action
	                    It Takes ObjectName, String OperationName, Object[] Parameters, and String[] Parameters
	                    Definition
	                **/
	                connection.invoke(defaultAuthenticator, "createUser",
	                                  new Object[] { username, psw, desc },
	                                  new String[] { "java.lang.String",
	                                                 "java.lang.String",
	                                                 "java.lang.String" });

	                return true;
	            } catch (Exception e) {
	         //       addMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
	                return false;
	                //throw new RuntimeException(e);
	            }
	        }

	        public static boolean removeUser(String username) {
	            try {
	                if (!username.equalsIgnoreCase("weblogic")) {
	                    connection.invoke(defaultAuthenticator, "removeUser",
	                                      new Object[] { username },
	                                      new String[] { "java.lang.String" });
	                }

	                return true;
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return false;
	        }
	        
	        /** As of connection.getAttribute you can use connection.invoke to invoke an action
            It Takes ObjectName, String OperationName, Object[] Parameters, and String[] Parameters
            Definition, It returns an Object we cast it to Boolean, you can know all about function from
            MBeans Reference
        **/
public static boolean isUserExists(String currentUser) throws RuntimeException {
    try {
        boolean userExists =
            ((Boolean)connection.invoke(defaultAuthenticator, "userExists",
                                        new Object[] { currentUser },
                                        new String[] { "java.lang.String" })).booleanValue();

        return userExists;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
// throw new RuntimeException(ex);
    }
}

public static boolean isGroupExists(String currentGroup) throws RuntimeException {
    try {
        boolean gourpExists =
            ((Boolean)connection.invoke(defaultAuthenticator,
                                        "groupExists",
                                        new Object[] { currentGroup },
                                        new String[] { "java.lang.String" })).booleanValue();

        return gourpExists;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
      //  throw new RuntimeException(ex);
    }
}

public static boolean isUserLocked(String username) {
  try {
    ObjectName service =
            new ObjectName("com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean");
    
    ObjectName[] serverRT =
        (ObjectName[])connection.getAttribute(service,
                                              "ServerRuntimes");
    ObjectName ssr =
        (ObjectName)connection.getAttribute(serverRT[0], "ServerSecurityRuntime");
    ObjectName rrm =
        (ObjectName)connection.getAttribute(ssr, "DefaultRealmRuntime");
    ObjectName ulr =
        (ObjectName)connection.getAttribute(rrm, "UserLockoutManagerRuntime");
      
      Boolean IsLocked =
              (Boolean)connection.invoke(ulr, "isLockedOut", new Object[] { username },
                                 new String[] { "java.lang.String" });
      return IsLocked;
  } catch (Exception ex) {
    ex.printStackTrace();
    return false;
  } 
}

public static boolean unlockUser(String username) {
try {
ObjectName service =
        new ObjectName("com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean");

ObjectName[] serverRT =
    (ObjectName[])connection.getAttribute(service,
                                          "ServerRuntimes");
ObjectName ssr =
    (ObjectName)connection.getAttribute(serverRT[0], "ServerSecurityRuntime");
ObjectName rrm =
    (ObjectName)connection.getAttribute(ssr, "DefaultRealmRuntime");
ObjectName ulr =
    (ObjectName)connection.getAttribute(rrm, "UserLockoutManagerRuntime");
  
  Boolean IsLocked =
          (Boolean)connection.invoke(ulr, "clearLockout", new Object[] { username },
                             new String[] { "java.lang.String" });
  return true;
} catch (Exception ex) {
//  addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
return false;
} 
}

/** This one is tricky, You first obtain a String cursor of the Iterator of Users, then you check if
    It have current, while true we invoke another function called "getCurrentName" which returns the name
    of the user, then I call advance function for the cursor to move forward, and invoke haveCurrent again
    and assign it to the same boolean I entered the while with (In order to get out of it!)
**/
public static List getListOfUsers() throws RuntimeException {
    try {
        List allUsers = new ArrayList();

        String cursor =
            (String)connection.invoke(defaultAuthenticator, "listUsers",
                                      new Object[] { "*",
                                                     Integer.valueOf(9999) },
                                      new String[] { "java.lang.String",
                                                     "java.lang.Integer" });

        boolean haveCurrent =
            ((Boolean)connection.invoke(defaultAuthenticator,
                                        "haveCurrent",
                                        new Object[] { cursor },
                                        new String[] { "java.lang.String" })).booleanValue();

        while (haveCurrent) {
            String currentName =
                (String)connection.invoke(defaultAuthenticator,
                                          "getCurrentName",
                                          new Object[] { cursor },
                                          new String[] { "java.lang.String" });
            
          String userDescription =
              (String)connection.invoke(defaultAuthenticator,
                                        "getUserDescription",
                                        new Object[] { currentName },
                                        new String[] { "java.lang.String" });

          if(userDescription != null && userDescription.equals("Application User"))
            allUsers.add(currentName);
            connection.invoke(defaultAuthenticator, "advance",
                              new Object[] { cursor },
                              new String[] { "java.lang.String" });

            haveCurrent =
                    ((Boolean)connection.invoke(defaultAuthenticator, "haveCurrent",
                                                new Object[] { cursor },
                                                new String[] { "java.lang.String" })).booleanValue();
        }

        return allUsers;
    } catch (Exception ex) {
//        addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
      List allUsers = new ArrayList();
      allUsers.add(ex.getMessage());
      return allUsers;
      //  throw new RuntimeException(ex);
    }
}

public static List getUserGroups(String username) throws RuntimeException {
    try {
        List allUserGroups = new ArrayList();

        String cursor =
            (String)connection.invoke(defaultAuthenticator, "listMemberGroups",
                                      new Object[] { username },
                                      new String[] { "java.lang.String"});

        boolean haveCurrent =
            ((Boolean)connection.invoke(defaultAuthenticator,
                                        "haveCurrent",
                                        new Object[] { cursor },
                                        new String[] { "java.lang.String" })).booleanValue();

        while (haveCurrent) {
            String currentName =
                (String)connection.invoke(defaultAuthenticator,
                                          "getCurrentName",
                                          new Object[] { cursor },
                                          new String[] { "java.lang.String" });

            allUserGroups.add(currentName);

            connection.invoke(defaultAuthenticator, "advance",
                              new Object[] { cursor },
                              new String[] { "java.lang.String" });

            haveCurrent =
                    ((Boolean)connection.invoke(defaultAuthenticator, "haveCurrent",
                                                new Object[] { cursor },
                                                new String[] { "java.lang.String" })).booleanValue();
        }

        return allUserGroups;
    } catch (Exception ex) {
//        addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
      List allUserGroups = new ArrayList();
      allUserGroups.add(ex.getMessage());
      return allUserGroups;
     //   throw new RuntimeException(ex);
    }
}

public static List getGroupMembers(String groupName) throws RuntimeException {
    try {
        List allGroupMembers = new ArrayList();

        String cursor =
            (String)connection.invoke(defaultAuthenticator, "listGroupMembers",
                                      new Object[] { groupName, "*", new java.lang.Integer(0) },
                                      new String [] { "java.lang.String", "java.lang.String", "java.lang.Integer" });

        boolean haveCurrent =
            ((Boolean)connection.invoke(defaultAuthenticator,
                                        "haveCurrent",
                                        new Object[] { cursor },
                                        new String[] { "java.lang.String" })).booleanValue();

        while (haveCurrent) {
            String currentName =
                (String)connection.invoke(defaultAuthenticator,
                                          "getCurrentName",
                                          new Object[] { cursor },
                                          new String[] { "java.lang.String" });

            allGroupMembers.add(currentName);

            connection.invoke(defaultAuthenticator, "advance",
                              new Object[] { cursor },
                              new String[] { "java.lang.String" });

            haveCurrent =
                    ((Boolean)connection.invoke(defaultAuthenticator, "haveCurrent",
                                                new Object[] { cursor },
                                                new String[] { "java.lang.String" })).booleanValue();
        }

        return allGroupMembers;
    } catch (Exception ex) {
//        addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
      List allGroupMembers = new ArrayList();
      allGroupMembers.add(ex.getMessage());
      return allGroupMembers;
       // throw new RuntimeException(ex);
    }
}

public static List getListOfGroups() throws RuntimeException {
    try {
        List allUsers = new ArrayList();

        String cursor =
            (String)connection.invoke(defaultAuthenticator, "listGroups",
                                      new Object[] { "*",
                                                     Integer.valueOf(9999) },
                                      new String[] { "java.lang.String",
                                                     "java.lang.Integer" });

        boolean haveCurrent =
            ((Boolean)connection.invoke(defaultAuthenticator,
                                        "haveCurrent",
                                        new Object[] { cursor },
                                        new String[] { "java.lang.String" })).booleanValue();

        while (haveCurrent) {
            String currentName =
                (String)connection.invoke(defaultAuthenticator,
                                          "getCurrentName",
                                          new Object[] { cursor },
                                          new String[] { "java.lang.String" });

            allUsers.add(currentName);

            connection.invoke(defaultAuthenticator, "advance",
                              new Object[] { cursor },
                              new String[] { "java.lang.String" });

            haveCurrent =
                    ((Boolean)connection.invoke(defaultAuthenticator, "haveCurrent",
                                                new Object[] { cursor },
                                                new String[] { "java.lang.String" })).booleanValue();
        }

        return allUsers;
    } catch (Exception ex) {
//        addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
      List allUsers = new ArrayList();
      allUsers.add(ex.getMessage());
      return allUsers;
     //   throw new RuntimeException(ex);
    }
}

public static boolean isServerConnected() {
  if(defaultAuthenticator==null)
      return false;
  return true;
}

	
}
