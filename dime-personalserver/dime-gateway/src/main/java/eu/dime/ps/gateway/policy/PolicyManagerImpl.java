package eu.dime.ps.gateway.policy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import eu.dime.ps.gateway.service.ServiceAdapter;
import eu.dime.ps.storage.entities.ServiceProvider;
import eu.dime.ps.storage.manager.EntityFactory;

/**
 * @author Sophie.Wrobel
 * 
 */
public class PolicyManagerImpl implements PolicyManager {

	private static final Logger logger = LoggerFactory.getLogger(PolicyManagerImpl.class);

	/**
	 * Distinguishes global policies (default) from adapter policies (overrides global policies) 
	 */
	private static final String GLOBAL_PREFIX = "GLOBAL";
	
	/**
	 * Contains global-level policy setting values
	 */
	private Map<String, String> globalPolicy;
	
	/**
	 * Contains adapter-level policy setting values
	 */
	private Map<String, String> adapterPolicy;
	
	/**
	 * Accessor to the services.properties file 
	 */
	private Properties properties;
	
	/**
	 * List of registered policy plugins
	 */
	private List<ServicePolicy> policyPlugins;
	
	private static final PolicyManagerImpl INSTANCE = new PolicyManagerImpl();
	
	@Autowired
	private PolicyStore policyStore;

	/**
	 * Returns singleton instance.
	 *  
	 * @return the singleton instance
	 */
	public static PolicyManagerImpl getInstance() {
		return INSTANCE;
	}
	
	public void setPolicyStore(PolicyStore policyStore) {
		this.policyStore = policyStore;
	}

	public PolicyManagerImpl() {

		this.globalPolicy = new HashMap<String, String>();
		this.adapterPolicy = new HashMap<String, String>();
		this.policyPlugins = new ArrayList<ServicePolicy>();
		
		try {
			if (this.properties == null || this.properties.size() == 0) {
				this.properties = PropertiesLoaderUtils.loadAllProperties("services.properties");
				Enumeration<?> iterator = this.properties.propertyNames();
				while (iterator.hasMoreElements()) {
					String propertyName = (String) iterator.nextElement();
					if (propertyName.matches("^"+GLOBAL_PREFIX+"_.*")) {
						this.globalPolicy.put(propertyName.substring(
								GLOBAL_PREFIX.length()+1), 
								this.properties.getProperty(propertyName));	
					} else {
						String adapterId = propertyName.replaceFirst("_(.*)$", "");
						this.adapterPolicy.put(clean(adapterId) + "_" + 
								propertyName.substring(adapterId.length()+1), 
								this.properties.getProperty(propertyName));
					}
				}
			}
		} catch (IOException e) {
			logger.warn("Could not load properties: "+e.getMessage(), e);
		}
		
		// bootstrap ServiceProvider entity
		if (this.globalPolicy.containsKey("ENABLED")) {
			
			String[] enabled = this.globalPolicy.get("ENABLED").split(",");
			
			logger.debug("Bootstraping service providers: "+this.globalPolicy.get("ENABLED"));

			for (String providerName : enabled) {
				providerName = providerName.trim();
				ServiceProvider dbProvider = ServiceProvider.findByName(providerName);
				
				// create provider if it does not exist yet
				if (dbProvider == null) {
					dbProvider = EntityFactory.getInstance().buildServiceProvider();
					dbProvider.setEnabled(Boolean.TRUE);
					dbProvider.setServiceName(providerName);
					String key, secret;
					
					key = this.getPolicyString("CONSUMER_KEY", providerName);
					secret = this.getPolicyString("CONSUMER_SECRET", providerName);
					
					if (secret == null || secret.equals("")){
						secret = "COULD NOT LOAD FROM PREFERENCES";
					}					
					if (key == null || key.equals("")){
						key = "COULD NOT LOAD FROM PREFERENCES";
					}
					dbProvider.setConsumerKey(key);
					dbProvider.setConsumerSecret(secret);
					
					dbProvider.persist();
				} else {
					//try to update keys
					String key, secret;
					key = this.getPolicyString("CONSUMER_KEY", providerName);
					secret = this.getPolicyString("CONSUMER_SECRET", providerName);
					
					if (secret == null || secret.equals("")){
						secret = "COULD NOT LOAD FROM PREFERENCES";
					}					
					if (key == null || key.equals("")){
						key = "COULD NOT LOAD FROM PREFERENCES";
					}
					dbProvider.setConsumerKey(key);
					dbProvider.setConsumerSecret(secret);
					
					dbProvider.merge();
					dbProvider.flush();
				}
			}
			
			// FIXME for some reason, the following code throws an exception
			
//			// enable/disable providers if they are found in GLOBAL_ENABLED or not
//			for (ServiceProvider provider : ServiceProvider.findAll()) {
//				if (ArrayUtils.contains(enabled, provider.getServiceName())) {
//					provider.setEnabled(Boolean.TRUE);
//					provider.merge();
//				} else {
//					provider.setEnabled(Boolean.FALSE);
//					provider.merge();
//				}
//			}
			
//			Caused by: java.lang.ExceptionInInitializerError
//			at eu.dime.ps.gateway.impl.ServiceGatewayImpl.<init>(ServiceGatewayImpl.java:39)
//			at eu.dime.ps.controllers.service.ServiceAdapterManagerImpl.<init>(ServiceAdapterManagerImpl.java:59)
//			at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
//			at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:39)
//			at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:27)
//			at java.lang.reflect.Constructor.newInstance(Constructor.java:513)
//			at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:126)
//			... 45 more
//		Caused by: org.springframework.dao.InvalidDataAccessApiUsageException: no transaction is in progress; nested exception is javax.persistence.TransactionRequiredException: no transaction is in progress
//			at org.springframework.orm.jpa.EntityManagerFactoryUtils.convertJpaAccessExceptionIfPossible(EntityManagerFactoryUtils.java:306)
//			at org.springframework.orm.jpa.aspectj.JpaExceptionTranslatorAspect.ajc$afterThrowing$org_springframework_orm_jpa_aspectj_JpaExceptionTranslatorAspect$1$18a1ac9(JpaExceptionTranslatorAspect.aj:15)
//			at eu.dime.ps.storage.entities.ServiceProvider.merge(ServiceProvider.java:123)
//			at eu.dime.ps.gateway.policy.PolicyManagerImpl.<init>(PolicyManagerImpl.java:120)
//			at eu.dime.ps.gateway.policy.PolicyManagerImpl.<clinit>(PolicyManagerImpl.java:55)
//			... 52 more
//		Caused by: javax.persistence.TransactionRequiredException: no transaction is in progress
//			at org.hibernate.ejb.AbstractEntityManagerImpl.flush(AbstractEntityManagerImpl.java:793)
//			at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
//			at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
//			at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
//			at java.lang.reflect.Method.invoke(Method.java:597)
//			at org.springframework.orm.jpa.ExtendedEntityManagerCreator$ExtendedEntityManagerInvocationHandler.invoke(ExtendedEntityManagerCreator.java:365)
//			at $Proxy48.flush(Unknown Source)
//			at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
//			at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
//			at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
//			at java.lang.reflect.Method.invoke(Method.java:597)
//			at org.springframework.orm.jpa.SharedEntityManagerCreator$SharedEntityManagerInvocationHandler.invoke(SharedEntityManagerCreator.java:240)
//			at $Proxy47.flush(Unknown Source)
//			... 55 more

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.dime.ps.communications.services.PolicyManager#getPolicyInteger(java.lang.
	 * String, java.lang.String)
	 */
	public Integer getPolicyInteger(String policyName, String adapterId) {
		Integer value = null;
		policyName = clean(policyName);
		adapterId = clean(adapterId);
		if (globalPolicy.get(policyName) != null) {
			value = Integer.parseInt(globalPolicy.get(policyName));
		}
		if (adapterId != null && adapterPolicy.get(adapterId + "_" + policyName) != null) {
			value = Integer.parseInt(adapterPolicy.get(adapterId + "_" + policyName));
		}
		if (adapterId != null && policyStore != null && policyStore.getValue(adapterId + "_" + policyName) != null && policyStore.getValue(adapterId + "_" + policyName).length()>0) {
			value = Integer.parseInt(policyStore.getValue(adapterId + "_" + policyName));
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.dime.ps.communications.services.PolicyManager#getPolicyInteger(java.lang.
	 * String, java.lang.String)
	 */
	public String getPolicyString(String policyName, String adapterId) {
		policyName = clean(policyName);
		adapterId = clean(adapterId);
		String value = globalPolicy.get(policyName);
		if (adapterId != null && adapterPolicy.get(adapterId + "_" + policyName) != null) {
			value = adapterPolicy.get(adapterId + "_" + policyName);
		}
		try {
			if (adapterId != null && policyStore != null && policyStore.getValue(adapterId + "_" + policyName) != null && policyStore.getValue(adapterId + "_" + policyName).length() > 0) {
				value = policyStore.getValue(adapterId + "_" + policyName);
			}
		} catch (NullPointerException e) {
			// Ignore - this happens if the key does not exist
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.dime.ps.communications.services.PolicyManager#setGlobalPolicy(java
	 * .lang.String, java.lang.Integer)
	 */
	public void setGlobalPolicy(String policyName, String value) {
		policyName = clean(policyName);
		
		globalPolicy.put(policyName, value);

		// Write changes to disk
		if (this.properties != null) {
			this.properties.setProperty(
					GLOBAL_PREFIX + "_" + policyName, value.toString());
			if (value.toString().length() > 0)
				policyStore.storeOrUpdate(GLOBAL_PREFIX + "_" + policyName, value.toString());
			try {
				FileOutputStream fos = new FileOutputStream("services.properties");
				this.properties.store(fos, null);
				fos.close();
			} catch (IOException e) {
				logger.warn("Could not save oAuthToken to services.properties.", e);
			}
		} else {
			logger.warn("Could not save policy: services.properties was not loaded.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.dime.ps.communications.services.PolicyManager#setAdapterPolicy(java
	 * .lang.String, java.lang.String, java.lang.Integer)
	 */
	public void setAdapterPolicy(String policyName, String adapterId,
			String value) {
		policyName = clean(policyName);
		adapterId = clean(adapterId);
		adapterPolicy.put(adapterId + "_" + policyName, value);
		policyStore.storeOrUpdate(adapterId + "_" + policyName, value.toString());
		
		// Write changes to disk
		if (this.properties != null) {
			this.properties.setProperty(
					adapterId + "_" + policyName, value.toString());
			try {
				FileOutputStream fos = new FileOutputStream("services.properties");
				this.properties.store(fos, null);
				fos.close();
			} catch (IOException e) {
				logger.warn("Could not save oAuthToken to services.properties.", e);
			}
		} else {
			logger.warn("Could not save policy: services.properties was not loaded.");
		}
	}
	
	/* (non-Javadoc)
	 * @see eu.dime.ps.communications.services.PolicyManager#before_get(eu.dime.ps.communications.services.ServiceAdapter, java.lang.String, java.lang.Class)
	 */
	public <T> void before_get(ServiceAdapter adapter, String attribute, Class<T> returnType) {
		Iterator<ServicePolicy> iter = this.policyPlugins.iterator();
		while (iter.hasNext()) {
			ServicePolicy plugin = iter.next();
			if (plugin.appliesTo(attribute)) {
				plugin.after_set(adapter, attribute, returnType);
			}
		}
	}

	/* (non-Javadoc)
	 * @see eu.dime.ps.communications.services.PolicyManager#after_get(eu.dime.ps.communications.services.ServiceAdapter, java.lang.String, java.lang.Class, java.util.Collection)
	 */
	
	public <T> Collection<T> after_get(ServiceAdapter adapter, String attribute, Class<T> returnType, Collection<T> data) {
		Iterator<ServicePolicy> iter = this.policyPlugins.iterator();
		Collection<T> ret = data;
		while (iter.hasNext()) {
			ServicePolicy plugin = iter.next();
			if (plugin.appliesTo(attribute)) {
				ret = plugin.after_get(adapter, attribute, returnType, ret);
			}
		}
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see eu.dime.ps.communications.services.PolicyManager#before_set(eu.dime.ps.communications.services.ServiceAdapter, java.lang.String, java.lang.Object)
	 */
	public Object before_set(ServiceAdapter adapter, String attribute, Object value) {
		Iterator<ServicePolicy> iter = this.policyPlugins.iterator();
		Object ret = value;
		while (iter.hasNext()) {
			ServicePolicy plugin = iter.next();
			if (plugin.appliesTo(attribute)) {
				ret = plugin.before_set(adapter, attribute, ret);
			}
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see eu.dime.ps.communications.services.PolicyManager#after_set(eu.dime.ps.communications.services.ServiceAdapter, java.lang.String, java.lang.Object)
	 */
	public void after_set(ServiceAdapter serviceAdapterWrapper,
			String attribute, Object value) {
		Iterator<ServicePolicy> iter = this.policyPlugins.iterator();
		while (iter.hasNext()) {
			ServicePolicy plugin = iter.next();
			if (plugin.appliesTo(attribute)) {
				plugin.after_set(serviceAdapterWrapper, attribute, value);
			}
		}
	}

	/* (non-Javadoc)
	 * @see eu.dime.ps.communications.services.policy.PolicyManager#registerPolicyPlugin(eu.dime.ps.communications.services.policy.ServicePolicy)
	 */
	@Override
	public void registerPolicyPlugin(ServicePolicy plugin) {
		this.policyPlugins.add(plugin);
	}
	
	private String clean(String dirty) {
		if (dirty == null)
			return null;
		else
			return dirty.replaceAll(":", "-");
	}

}
