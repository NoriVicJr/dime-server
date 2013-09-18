/*
* Copyright 2013 by the digital.me project (http://www.dime-project.eu).
*
* Licensed under the EUPL, Version 1.1 only (the "Licence");
* You may not use this work except in compliance with the Licence.
* You may obtain a copy of the Licence at:
*
* http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
*
* Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the Licence for the specific language governing permissions and limitations under the Licence.
*/

package eu.dime.ps.controllers;

import ie.deri.smile.vocabulary.DDO;
import ie.deri.smile.vocabulary.NAO;
import ie.deri.smile.vocabulary.NIE;
import ie.deri.smile.vocabulary.NSO;
import ie.deri.smile.vocabulary.PIMO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.ontoware.rdfreactor.schema.rdfs.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.dime.ps.controllers.exception.InfosphereException;
import eu.dime.ps.controllers.infosphere.manager.AccountManager;
import eu.dime.ps.controllers.infosphere.manager.DataboxManager;
import eu.dime.ps.controllers.infosphere.manager.DeviceManager;
import eu.dime.ps.controllers.infosphere.manager.FileManager;
import eu.dime.ps.controllers.infosphere.manager.LivePostManager;
import eu.dime.ps.controllers.infosphere.manager.PersonGroupManager;
import eu.dime.ps.controllers.infosphere.manager.PersonManager;
import eu.dime.ps.controllers.infosphere.manager.ProfileCardManager;
import eu.dime.ps.controllers.infosphere.manager.ProfileManager;
import eu.dime.ps.controllers.infosphere.manager.SituationManager;
import eu.dime.ps.gateway.service.internal.DimeServiceAdapter;
import eu.dime.ps.semantic.BroadcastManager;
import eu.dime.ps.semantic.BroadcastReceiver;
import eu.dime.ps.semantic.Event;
import eu.dime.ps.semantic.model.ModelFactory;
import eu.dime.ps.semantic.model.dao.Account;
import eu.dime.ps.semantic.model.dcon.Situation;
import eu.dime.ps.semantic.model.ddo.Device;
import eu.dime.ps.semantic.model.dlpo.LivePost;
import eu.dime.ps.semantic.model.nco.EmailAddress;
import eu.dime.ps.semantic.model.nco.PersonContact;
import eu.dime.ps.semantic.model.nco.PersonName;
import eu.dime.ps.semantic.model.nfo.DataContainer;
import eu.dime.ps.semantic.model.nfo.FileDataObject;
import eu.dime.ps.semantic.model.pimo.Person;
import eu.dime.ps.semantic.model.pimo.PersonGroup;
import eu.dime.ps.semantic.model.ppo.AccessSpace;
import eu.dime.ps.semantic.model.ppo.PrivacyPreference;
import eu.dime.ps.semantic.privacy.PrivacyPreferenceType;

/**
 * This creates and configures, by default, tenants for a pre-defined list of
 * personas, loading pre-defined data for each of them.
 * 
 * @author Ismael Rivera
 */
public class DefaultDataSetup implements BroadcastReceiver {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultDataSetup.class);

	public static final String DEFAULT_PUBLIC_PROFILE_CARD_NAME = "MyPublicCard";
	
	private static final String TEST_USER_1_ID = "todo";
	private static final String TEST_USER_2_ID = "todo";
	private static final String TEST_USER_3_ID = "todo";
	
	private static final Map<String, String[]> CONTACTS = new HashMap<String, String[]>();
	static {
	  CONTACTS.put("todo", new String[] { "Christian Knecht", "christian.knecht@iao.fraunhofer.de", "Chris" });
	  CONTACTS.put("todo", new String[] { "Cristina Fra", "cristina.fra@telecomitalia.it", "cristina" });
	  CONTACTS.put("todo", new String[] { "Fabian Hermann", "fabian.hermann@iao.fraunhofer.de", "FHermann" });
	  CONTACTS.put("todo", new String[] { "Ismael Rivera", "ismael.rivera@email.com", "ismriv" });
	  CONTACTS.put("todo", new String[] { "Marc Planaguma", "marc.planaguma@email.com", "marc" });
	  CONTACTS.put("todo", new String[] { "Marcel Heupel", "heupel@wiwi.uni-siegen.de", "mhpl" });
	  CONTACTS.put("todo", new String[] { "Massimo Valla", "massi.tilab@gmail.com", "massitilab" });
	  CONTACTS.put("todo", new String[] { "Rafael Gimenez", "rgimenez@bdigital.org", "rgimenez" });
	  CONTACTS.put("todo", new String[] { "Simon Scerri", "simon.scerri@deri.org", "irrecs" });
	  CONTACTS.put("todo", new String[] { "Simon Thiel", "simon.thiel@iao.fraunhofer.de", "Simon" });
	  CONTACTS.put("todo", new String[] { "Sophie Wrobel", "sophie.wrobel@cas.de", "webmage" });
	  CONTACTS.put("todo", new String[] { "Richard Wacker", "richard.wacker@yellowmap.de", "riwa" });
	  CONTACTS.put("todo", new String[] { "Borja Gorriz", "bgorriz@bdigital.org", "bgorriz" });
	  CONTACTS.put("todo", new String[] { "Javier Vendrell", "jvendrell@ametic.es", "jvendrell" });
	  CONTACTS.put("todo", new String[] { "David Alonso", "dalonso@ametic.es", "dalonso" });
	  //new contacts - testusers
	  CONTACTS.put(TEST_USER_1_ID, new String[] { "Test User1", "test.user1@email.com", "testuser1" });
	  CONTACTS.put(TEST_USER_2_ID, new String[] { "Test User2", "test.user2@email.com", "testuser2" });
	  CONTACTS.put(TEST_USER_3_ID, new String[] { "Test User3", "test.user3@email.com", "testuser3" });
	}
	
	private static final URI FILE_FLYER = new URIImpl("urn:uuid:19b74400-1166-4eed-9eb0-f6b8a958c43d");
	private static final URI FILE_LOGO = new URIImpl("urn:uuid:6025e593-387b-422e-8af3-cb96923a08ba");
	private static final URI FILE_WELCOME = new URIImpl("urn:uuid:89935e86-2d49-4400-8cf4-3d49eae41422");
	private static final URI FILE_PHOTO = new URIImpl("urn:uuid:b0bdeed3-4a78-4886-9a33-7063f7e6f225");
	private static final URI DATABOX_DIME = new URIImpl("urn:uuid:a145f3db-bafd-4bf7-95d8-3d18619d1463");
	private static final URI DATABOX_FRIENDS = new URIImpl("urn:uuid:b6485de8-1aca-46e4-b71c-6d80402b51e3");
	private static final URI LIVEPOST_WELCOME = new URIImpl("urn:uuid:613627f7-605c-48be-ac84-4ab8974b87ee");
	
	private final ModelFactory modelFactory = new ModelFactory();

	private AccountManager accountManager;
	private PersonManager personManager;
	private ProfileManager profileManager;
	private PersonGroupManager personGroupManager;
	private ProfileCardManager profileCardManager;
	private DataboxManager databoxManager;
	private DeviceManager deviceManager;
	private SituationManager situationManager;
	private LivePostManager livePostManager;
	private FileManager fileManager;
	private UserManager userManager;

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@Autowired
	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	@Autowired
	public void setProfileManager(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}

	@Autowired
	public void setPersonGroupManager(PersonGroupManager personGroupManager) {
		this.personGroupManager = personGroupManager;
	}

	@Autowired
	public void setProfileCardManager(ProfileCardManager profileCardManager) {
		this.profileCardManager = profileCardManager;
	}

	@Autowired
	public void setDataboxManager(DataboxManager databoxManager) {
		this.databoxManager = databoxManager;
	}

	@Autowired
	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	@Autowired
	public void setSituationManager(SituationManager situationManager) {
		this.situationManager = situationManager;
	}

	@Autowired
	public void setLivePostManager(LivePostManager livePostManager) {
		this.livePostManager = livePostManager;
	}

	@Autowired
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public DefaultDataSetup() {
		BroadcastManager.getInstance().registerReceiver(this);
	}
	
	@Override
	public void onReceive(Event event) {
		
		// only interested in 'user registered' events
		if (!UserManager.ACTION_USER_REGISTERED.equals(event.getAction())) {
			return;
		}

		Long tenant = Long.parseLong(event.getTenant());
		TenantContextHolder.setTenant(tenant);
		logger.info("Creating default (dummy) data for new PIM/user [tenant = " + tenant + "]");
		
		// fetch owner of the PIM
		Person me;
		try {
			me = personManager.getMe();
		} catch (InfosphereException e) {
			logger.error("Can't find PIM's owner: default data won't be generated for " + event.getIdentifier(), e);
			return;
		}
		
		// creating pre-defined devices (ddo:Laptop and ddo:Mobile)
		Device laptop = modelFactory.getDDOFactory().createDevice("http://www.semanticdesktop.org/ontologies/2011/10/05/ddo#Laptop");
		laptop.setPrefLabel("My laptop");
		laptop.setCreator(me);
		laptop.getModel().addStatement(me, DDO.owns, laptop);
		Device mobile = modelFactory.getDDOFactory().createDevice("http://www.semanticdesktop.org/ontologies/2011/10/05/ddo#Mobile");
		mobile.setPrefLabel("My mobile phone");
		mobile.setCreator(me);
		mobile.getModel().addStatement(me, DDO.owns, mobile);
		try {
			deviceManager.add(laptop);
			deviceManager.add(mobile);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when creating pre-defined devices: " + e.getMessage(), e);
		}

		// creating pre-defined contacts & contacts of the di.me consortium
		Person testuser1 = null, testuser2 = null, testuser3 = null;
		List<Person> dimePeople = new ArrayList<Person>();
		List<Account> contactsAccounts = new ArrayList<Account>();
		for (String said : CONTACTS.keySet()) {
			
			// skip contacts which do not have a real said
			if (said == null || said.equals("") || said.equals("todo")) {
				continue;
			}
			
			String[] data = CONTACTS.get(said);
			logger.info("Adding '" + data[0] + "' as a contact [said=" + said + "]");
			
			PersonContact profile = modelFactory.getNCOFactory().createPersonContact();
			profile.setPrefLabel(data[0]);
			
			PersonName name = modelFactory.getNCOFactory().createPersonName();
			name.setFullname(data[0]);
			name.setNickname(data[2]);
			profile.setPersonName(name);
			profile.getModel().addAll(name.getModel().iterator());
			
			EmailAddress email = modelFactory.getNCOFactory().createEmailAddress();
			email.setEmailAddress(data[1]);
			profile.setEmailAddress(email);
			profile.getModel().addAll(email.getModel().iterator());
			
			try {
				final String accountUri = userManager.add(said).getAccountUri();
				userManager.addProfile(new URIImpl(accountUri), profile);
				
				Account account = accountManager.get(accountUri);
				contactsAccounts.add(account);
				
				if (account.hasCreator()) {
					Person person = personManager.get(account.getCreator().toString());
					
					if (said.equals(TEST_USER_1_ID)) {
						person.setTrustLevel(0.5); 
						personManager.update(person);
						testuser1 = person;
					} else if (said.equals(TEST_USER_2_ID)) {
						person.setTrustLevel(0.0);
						personManager.update(person);
						testuser2 = person;
					} else if (said.equals(TEST_USER_3_ID)) {
						person.setTrustLevel(1.0);
						personManager.update(person);
						testuser3 = person;
					} else {
						// if not a test user, then it's a di.me contact
						dimePeople.add(person);
					}
				} else {
					logger.error("Contact account " + accountUri + " does not have a pimo:Person as creator.");
				}
			} catch (InfosphereException e) {
				logger.error("Contact '" + data[0] + "' couldn't be added as a contact: " + e.getMessage(), e);
			}
		}
		
		// creating pre-defined groups
		PersonGroup businessGroup = createPersonGroup("Business Contacts", me, testuser1, testuser2);
		PersonGroup colleaguesGroup = createPersonGroup("Colleagues", me, testuser1);
		PersonGroup familyGroup = createPersonGroup("Family", me);
		PersonGroup friendsGroup = createPersonGroup("Friends", me, testuser1, testuser3);
		PersonGroup acquaintancesGroup = createPersonGroup("Acquaintances", me);

		// creating pre-defined profile cards
		PrivacyPreference publicCard = null, anonymousCard = null, businessCard = null, privateCard = null;
		Account publicAccount = null;
		Resource[] attributes = null;
		try {
			publicCard = profileCardManager.getByLabel(DEFAULT_PUBLIC_PROFILE_CARD_NAME);
			AccessSpace accessSpace = publicCard.getAllAccessSpace().next();
			publicAccount = accessSpace.getSharedThrough();

			attributes = publicCard.getAllAppliesToResource_as().asArray();
			
			shareProfileCard(publicCard, businessGroup, colleaguesGroup, familyGroup, friendsGroup, acquaintancesGroup, testuser1);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when creating the pre-defined profile cards.", e);
		}
		
		PersonName anonymousName = modelFactory.getNCOFactory().createPersonName();
		anonymousName.setNickname("dimeUser");
		anonymousCard = createProfileCard("MyAnonymousCard");
		
		businessCard = createProfileCard("MyBusinessCard", attributes);
		shareProfileCard(businessCard, businessGroup, colleaguesGroup);
		
		privateCard = createProfileCard("MyPrivateCard", attributes);
		
		// set the public card as shared with all contacts' accounts
		try {
			for (Account account : contactsAccounts) {
				publicCard.addSharedWith(account);
			}
			profileCardManager.update(publicCard);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when flagging public card as shared with all contacts.", e);
		}

		// creating welcome livepost
		Account testuser3Account = null;
		if (testuser3 != null) {
			try {
				testuser3Account = accountManager.getAllByCreator(testuser3).iterator().next();
				
				String text = "Welcome to the di.me Test Trial 2013! Please try out the prototype and give us feedback! Visit the trial page: http://trial.dime-project.eu or our project page: http://www.dime-project.eu";
				createLivePost(LIVEPOST_WELCOME, text, testuser3, testuser3Account.asURI(), publicAccount.asURI());
			} catch (Exception e) {
				logger.error("An error ocurred when creating the pre-defined liveposts.", e);
			}
		}
		
		// creating pre-defined files
		FileDataObject flyer = createFile(FILE_FLYER, "digital.me_project_flyer.pdf", DefaultDataSetup.class.getClassLoader().getResourceAsStream("default/digital.me_project_flyer.pdf"), 0.5, me);
		FileDataObject logo = createFile(FILE_LOGO, "digital.me_logo.jpg", DefaultDataSetup.class.getClassLoader().getResourceAsStream("default/digital.me_logo.jpg"), 0, me);
		FileDataObject trial = createFile(FILE_WELCOME, "welcome_to_di.me_test.txt", DefaultDataSetup.class.getClassLoader().getResourceAsStream("default/welcome_to_di.me_test.txt"), 0, me);
		FileDataObject photo = createFile(FILE_PHOTO, "hiking.jpg", DefaultDataSetup.class.getClassLoader().getResourceAsStream("default/hiking.jpg"), 1, me);

		DataContainer databoxDime = createDatabox(DATABOX_DIME, "di.me info", flyer, logo, trial);
		DataContainer databoxFriends = createDatabox(DATABOX_FRIENDS, "FriendsBox", photo);
		shareDatabox(databoxDime, publicAccount, testuser1);

		// creating pre-defined situations
		createSituation("Working@Office", me);
		createSituation("Working@Home", me);
		createSituation("@Conference", me);
		createSituation("Relaxing@Home", me);
		createSituation("Social Event", me);
		createSituation("Travelling", me);
		
		// add all dime people in a group
		createPersonGroup("di.me Project", me, dimePeople.toArray(new Person[dimePeople.size()]));
		
		// set photo and group "friends" related (nao:isRelated) to one another
		photo.getModel().addStatement(photo, NAO.isRelated, friendsGroup);
		friendsGroup.getModel().addStatement(friendsGroup, NAO.isRelated, photo);
		try {
			personGroupManager.update(friendsGroup);
			fileManager.update(photo);
		} catch (InfosphereException e) {
			logger.error("Could not set nao:isRelatedTo between photo and group 'friends': " + e.getMessage(), e);
		}
		
		TenantContextHolder.clear();
	}
	
	private PersonGroup createPersonGroup(String label, Person creator, Person... members) {
		PersonGroup group = modelFactory.getPIMOFactory().createPersonGroup();
		group.setPrefLabel(label);
		for (Person member : members) {
			if (member != null) {
				group.getModel().addStatement(group, PIMO.hasMember, member);
			}
		}
		
		try {
			personGroupManager.add(group);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when creating pre-defined situation '" + label + "'", e);
		}

		return group;
	}

	private LivePost createLivePost(URI uri, String text, Person creator, URI sharedBy, URI sharedWith) {
		if (creator == null || sharedBy == null || sharedWith == null) {
			return null;
		}
		
		LivePost livePost = modelFactory.getDLPOFactory().createLivePost(uri);
		livePost.setTextualContent(text);
		livePost.setCreator(creator);
		livePost.getModel().addStatement(livePost, NIE.dataSource, sharedBy);
		livePost.setSharedBy(sharedBy);
		livePost.setSharedWith(sharedWith);

		try {
			livePostManager.add(livePost);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when creating pre-defined livepost '" + text + "'", e);
		}

		return livePost;
	}

	private Situation createSituation(String label, Person creator) {
		Situation situation = modelFactory.getDCONFactory().createSituation();
		situation.setPrefLabel(label);
		situation.setCreator(creator);
		
		try {
			situationManager.add(situation);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when creating pre-defined situation '" + label + "'", e);
		}

		return situation;
	}
	
	private PrivacyPreference createProfileCard(String label, Resource... attributes) {
		// create the di.me account
		Account account = modelFactory.getDAOFactory().createAccount();
		account.setAccountType(DimeServiceAdapter.NAME);
		account.setPrefLabel(label);
		
		// create the profile card for the di.me account
		PrivacyPreference profileCard = modelFactory.getPPOFactory().createPrivacyPreference();
		profileCard.setLabel(PrivacyPreferenceType.PROFILECARD.name());
		profileCard.setPrefLabel(label);
		
		// set di.me account as sharedThrough in the profile card's access space
		AccessSpace accessSpace = modelFactory.getNSOFactory().createAccessSpace();
		accessSpace.setSharedThrough(account);
		profileCard.getModel().addAll(accessSpace.getModel().iterator());
		profileCard.setAccessSpace(accessSpace);
	
		// add profile attributes to profile card
		for (Resource attribute : attributes) {
			profileCard.addAppliesToResource(attribute);
		}
	
		try {
			accountManager.add(account);
			profileCardManager.add(profileCard);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when creating pre-defined profile card '" + 
					label + "': "+ e.getMessage(), e);
		}
		
		return profileCard;
	}

	private DataContainer createDatabox(URI uri, String label, FileDataObject... files) {
		// create the databox
		DataContainer databox = modelFactory.getNFOFactory().createDataContainer(uri);
		databox.setPrefLabel(label);
		
		// add files to databox
		for (FileDataObject file : files) {
			databox.addPart(file);
		}
		
		try {
			databoxManager.add(databox);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when creating pre-defined databox '" + 
					label + "': "+ e.getMessage(), e);
		}

		return databox;
	}
	
	private void shareProfileCard(PrivacyPreference profileCard, Resource... agents) {
		// find first access space
		AccessSpace accessSpace = profileCard.getAllAccessSpace().next();
		
		// include agents in access space
		for (Resource agent : agents) {
			if (agent != null) {
				accessSpace.getModel().addStatement(accessSpace, NSO.includes, agent);
			}
		}
		
		try {
			profileCardManager.update(profileCard);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when adding agents to pre-defined profile card '" +
					profileCard.getPrefLabel() + "': " + e.getMessage(), e);
		}
	}
	
	private void shareDatabox(DataContainer databox, Account sharedThrough, Resource... agents) {
		PrivacyPreference ppDatabox = (PrivacyPreference) databox.castTo(PrivacyPreference.class);
		
		// find access space sharedThrough given account
		AccessSpace accessSpace = null;
		ClosableIterator<AccessSpace> asIt = ppDatabox.getAllAccessSpace();
		while (asIt.hasNext()) {
			accessSpace = asIt.next();
			if (!sharedThrough.equals(accessSpace.getSharedThrough())) {
				accessSpace = null;
			}
		}
		asIt.close();
		
		// create a new access space if it didn't exist yet for given sharedThrough
		if (accessSpace == null) {
			accessSpace = modelFactory.getNSOFactory().createAccessSpace();
			accessSpace.setSharedThrough(sharedThrough);
			ppDatabox.addAccessSpace(accessSpace);
		}
		
		// include agents in access space
		for (Resource agent : agents) {
			if (agent != null) {
				accessSpace.getModel().addStatement(accessSpace, NSO.includes, agent);
			}
		}

		// add access space metadata to databox
		databox.getModel().addAll(accessSpace.getModel().iterator());

		try {
			databoxManager.update(databox);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when adding agents to pre-defined databox '" +
					databox.getPrefLabel() + "': " + e.getMessage(), e);
		}
	}

	private FileDataObject createFile(URI uri, String fileName, InputStream inputStream, double privacyLevel, Person creator) {
		FileDataObject file = modelFactory.getNFOFactory().createFileDataObject(uri);
		file.setFileName(fileName);
		file.setPrivacyLevel(privacyLevel);
		file.setCreator(creator);
		
		try {
			fileManager.add(file, inputStream);
		} catch (IOException e) {
			logger.error("An error ocurred when creating pre-defined file '" + fileName + "': " + e.getMessage(), e);
		} catch (InfosphereException e) {
			logger.error("An error ocurred when creating pre-defined file '" + fileName + "': " + e.getMessage(), e);
                } catch (Exception e) {
			logger.error("An error ocurred when creating pre-defined file '" + fileName + "': " + e.getMessage(), e);
		}
		
		return file;
	}

}
