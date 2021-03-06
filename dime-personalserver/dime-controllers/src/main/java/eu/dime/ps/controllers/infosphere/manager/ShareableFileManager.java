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

package eu.dime.ps.controllers.infosphere.manager;

import ie.deri.smile.vocabulary.NAO;
import ie.deri.smile.vocabulary.NFO;
import ie.deri.smile.vocabulary.NIE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ontoware.rdf2go.model.node.URI;

import eu.dime.ps.controllers.exception.ForbiddenException;
import eu.dime.ps.controllers.exception.InfosphereException;
import eu.dime.ps.semantic.exception.NotFoundException;
import eu.dime.ps.semantic.model.nfo.FileDataObject;

/**
 * Implementation of ShareableManager for files, compliant with the
 * privacy preferences of the user.
 * 
 * @author Ismael Rivera
 */
public class ShareableFileManager extends ShareableManagerBase<FileDataObject> implements ShareableManager<FileDataObject> {

	public static final List<URI> SHAREABLE_FILE_PROPERTIES;
	static {
		SHAREABLE_FILE_PROPERTIES = new ArrayList<URI>(8);
		SHAREABLE_FILE_PROPERTIES.add(NAO.created);
		SHAREABLE_FILE_PROPERTIES.add(NAO.lastModified);
		SHAREABLE_FILE_PROPERTIES.add(NAO.prefLabel);
		SHAREABLE_FILE_PROPERTIES.add(NAO.privacyLevel);
		SHAREABLE_FILE_PROPERTIES.add(NFO.fileName);
		SHAREABLE_FILE_PROPERTIES.add(NFO.fileSize);
		SHAREABLE_FILE_PROPERTIES.add(NFO.fileLastModified);
		SHAREABLE_FILE_PROPERTIES.add(NIE.mimeType);
	};

	private FileManager fileManager;

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	@Override
	public boolean exist(String resourceId) throws InfosphereException {
		return fileManager.exist(resourceId);
	}

	@Override
	public FileDataObject get(String fileId, String requesterId) throws NotFoundException, ForbiddenException, InfosphereException {
		FileDataObject file = fileManager.get(fileId, SHAREABLE_FILE_PROPERTIES);
		checkAuthorized(file, requesterId);

		// sets the file as sharedWith the requester user
		setSharedWith(file, requesterId);
		
		return file;
	}
	
	@Override
	public Collection<FileDataObject> getAll(String accountId, String requesterId) throws InfosphereException {
		Collection<FileDataObject> all = fileManager.getAll(SHAREABLE_FILE_PROPERTIES);
		Collection<FileDataObject> authorized = filterAuthorized(all, requesterId);
		
		// sets the files as sharedWith the requester user
		setSharedWith(authorized, requesterId);
		
		return authorized;
	}

}
