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

package eu.dime.ps.semantic.model;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

import eu.dime.ps.semantic.model.nid3.*;

/**
 * A factory for the Java classes generated automatically for the NID3 vocabulary.
 * 
 * @author Ismael Rivera
 * 
 */

public class NID3Factory extends ResourceFactory {

	public ID3Audio createID3Audio() {
		return new ID3Audio(createModel(), generateUniqueURI(), true);
	}

	public ID3Audio createID3Audio(URI resourceUri) {
		return new ID3Audio(createModel(), resourceUri, true);
	}

	public ID3Audio createID3Audio(String resourceUriString) {
		return new ID3Audio(createModel(), new URIImpl(resourceUriString), true);
	}

	public InvolvedPerson createInvolvedPerson() {
		return new InvolvedPerson(createModel(), generateUniqueURI(), true);
	}

	public InvolvedPerson createInvolvedPerson(URI resourceUri) {
		return new InvolvedPerson(createModel(), resourceUri, true);
	}

	public InvolvedPerson createInvolvedPerson(String resourceUriString) {
		return new InvolvedPerson(createModel(), new URIImpl(resourceUriString), true);
	}

	public SynchronizedText createSynchronizedText() {
		return new SynchronizedText(createModel(), generateUniqueURI(), true);
	}

	public SynchronizedText createSynchronizedText(URI resourceUri) {
		return new SynchronizedText(createModel(), resourceUri, true);
	}

	public SynchronizedText createSynchronizedText(String resourceUriString) {
		return new SynchronizedText(createModel(), new URIImpl(resourceUriString), true);
	}

	public SynchronizedTextElement createSynchronizedTextElement() {
		return new SynchronizedTextElement(createModel(), generateUniqueURI(), true);
	}

	public SynchronizedTextElement createSynchronizedTextElement(URI resourceUri) {
		return new SynchronizedTextElement(createModel(), resourceUri, true);
	}

	public SynchronizedTextElement createSynchronizedTextElement(String resourceUriString) {
		return new SynchronizedTextElement(createModel(), new URIImpl(resourceUriString), true);
	}

	public UserDefinedFrame createUserDefinedFrame() {
		return new UserDefinedFrame(createModel(), generateUniqueURI(), true);
	}

	public UserDefinedFrame createUserDefinedFrame(URI resourceUri) {
		return new UserDefinedFrame(createModel(), resourceUri, true);
	}

	public UserDefinedFrame createUserDefinedFrame(String resourceUriString) {
		return new UserDefinedFrame(createModel(), new URIImpl(resourceUriString), true);
	}

	public UserDefinedURLFrame createUserDefinedURLFrame() {
		return new UserDefinedURLFrame(createModel(), generateUniqueURI(), true);
	}

	public UserDefinedURLFrame createUserDefinedURLFrame(URI resourceUri) {
		return new UserDefinedURLFrame(createModel(), resourceUri, true);
	}

	public UserDefinedURLFrame createUserDefinedURLFrame(String resourceUriString) {
		return new UserDefinedURLFrame(createModel(), new URIImpl(resourceUriString), true);
	}

}