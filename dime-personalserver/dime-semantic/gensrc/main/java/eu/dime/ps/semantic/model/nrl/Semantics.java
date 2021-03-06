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

package eu.dime.ps.semantic.model.nrl;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.exception.ModelRuntimeException;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.BlankNode;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.ontoware.rdfreactor.runtime.Base;
import org.ontoware.rdfreactor.runtime.ReactorResult;


/**
 * This class manages access to these properties:
 * <ul>
 *   <li> SemanticsDefinedBy </li>
 * </ul>
 *
 * class- This class was generated by <a href="http://RDFReactor.semweb4j.org">RDFReactor</a> */
public class Semantics extends eu.dime.ps.semantic.model.RDFReactorThing {

    /** http://www.semanticdesktop.org/ontologies/2007/08/15/nrl#Semantics */
    @SuppressWarnings("hiding")
	public static final URI RDFS_CLASS = new URIImpl("http://www.semanticdesktop.org/ontologies/2007/08/15/nrl#Semantics", false);

    /** http://www.semanticdesktop.org/ontologies/2007/08/15/nrl#semanticsDefinedBy */
    @SuppressWarnings("hiding")
	public static final URI SEMANTICSDEFINEDBY = new URIImpl("http://www.semanticdesktop.org/ontologies/2007/08/15/nrl#semanticsDefinedBy",false);

    /** 
     * All property-URIs with this class as domain.
     * All properties of all super-classes are also available. 
     */
    @SuppressWarnings("hiding")
    public static final URI[] MANAGED_URIS = {
      new URIImpl("http://www.semanticdesktop.org/ontologies/2007/08/15/nrl#semanticsDefinedBy",false) 
    };


	// protected constructors needed for inheritance
	
	/**
	 * Returns a Java wrapper over an RDF object, identified by URI.
	 * Creating two wrappers for the same instanceURI is legal.
	 * @param model RDF2GO Model implementation, see http://rdf2go.semweb4j.org
	 * @param classURI URI of RDFS class
	 * @param instanceIdentifier Resource that identifies this instance
	 * @param write if true, the statement (this, rdf:type, TYPE) is written to the model
	 *
	 * [Generated from RDFReactor template rule #c1] 
	 */
	protected Semantics (Model model, URI classURI, org.ontoware.rdf2go.model.node.Resource instanceIdentifier, boolean write) {
		super(model, classURI, instanceIdentifier, write);
	}

	// public constructors

	/**
	 * Returns a Java wrapper over an RDF object, identified by URI.
	 * Creating two wrappers for the same instanceURI is legal.
	 * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
	 * @param instanceIdentifier an RDF2Go Resource identifying this instance
	 * @param write if true, the statement (this, rdf:type, TYPE) is written to the model
	 *
	 * [Generated from RDFReactor template rule #c2] 
	 */
	public Semantics (Model model, org.ontoware.rdf2go.model.node.Resource instanceIdentifier, boolean write) {
		super(model, RDFS_CLASS, instanceIdentifier, write);
	}


	/**
	 * Returns a Java wrapper over an RDF object, identified by a URI, given as a String.
	 * Creating two wrappers for the same URI is legal.
	 * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
	 * @param uriString a URI given as a String
	 * @param write if true, the statement (this, rdf:type, TYPE) is written to the model
	 * @throws ModelRuntimeException if URI syntax is wrong
	 *
	 * [Generated from RDFReactor template rule #c7] 
	 */
	public Semantics (Model model, String uriString, boolean write) throws ModelRuntimeException {
		super(model, RDFS_CLASS, new URIImpl(uriString,false), write);
	}

	/**
	 * Returns a Java wrapper over an RDF object, identified by a blank node.
	 * Creating two wrappers for the same blank node is legal.
	 * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
	 * @param bnode BlankNode of this instance
	 * @param write if true, the statement (this, rdf:type, TYPE) is written to the model
	 *
	 * [Generated from RDFReactor template rule #c8] 
	 */
	public Semantics (Model model, BlankNode bnode, boolean write) {
		super(model, RDFS_CLASS, bnode, write);
	}

	/**
	 * Returns a Java wrapper over an RDF object, identified by 
	 * a randomly generated URI.
	 * Creating two wrappers results in different URIs.
	 * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
	 * @param write if true, the statement (this, rdf:type, TYPE) is written to the model
	 *
	 * [Generated from RDFReactor template rule #c9] 
	 */
	public Semantics (Model model, boolean write) {
		super(model, RDFS_CLASS, model.newRandomUniqueURI(), write);
	}

    ///////////////////////////////////////////////////////////////////
    // typing

	/**
	 * Return an existing instance of this class in the model. No statements are written.
	 * @param model an RDF2Go model
	 * @param instanceResource an RDF2Go resource
	 * @return an instance of Semantics  or null if none existst
	 *
	 * [Generated from RDFReactor template rule #class0] 
	 */
	public static Semantics  getInstance(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getInstance(model, instanceResource, Semantics.class);
	}

	/**
	 * Create a new instance of this class in the model. 
	 * That is, create the statement (instanceResource, RDF.type, http://www.semanticdesktop.org/ontologies/2007/08/15/nrl#Semantics).
	 * @param model an RDF2Go model
	 * @param instanceResource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #class1] 
	 */
	public static void createInstance(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.createInstance(model, RDFS_CLASS, instanceResource);
	}

	/**
	 * @param model an RDF2Go model
	 * @param instanceResource an RDF2Go resource
	 * @return true if instanceResource is an instance of this class in the model
	 *
	 * [Generated from RDFReactor template rule #class2] 
	 */
	public static boolean hasInstance(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.hasInstance(model, RDFS_CLASS, instanceResource);
	}

	/**
	 * @param model an RDF2Go model
	 * @return all instances of this class in Model 'model' as RDF resources
	 *
	 * [Generated from RDFReactor template rule #class3] 
	 */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllInstances(Model model) {
		return Base.getAllInstances(model, RDFS_CLASS, org.ontoware.rdf2go.model.node.Resource.class);
	}

	/**
	 * @param model an RDF2Go model
	 * @return all instances of this class in Model 'model' as a ReactorResult,
	 * which can conveniently be converted to iterator, list or array.
	 *
	 * [Generated from RDFReactor template rule #class3-as] 
	 */
	public static ReactorResult<? extends Semantics> getAllInstances_as(Model model) {
		return Base.getAllInstances_as(model, RDFS_CLASS, Semantics.class );
	}

    /**
	 * Remove rdf:type Semantics from this instance. Other triples are not affected.
	 * To delete more, use deleteAllProperties
	 * @param model an RDF2Go model
	 * @param instanceResource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #class4] 
	 */
	public static void deleteInstance(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.deleteInstance(model, RDFS_CLASS, instanceResource);
	}

	/**
	 * Delete all (this, *, *), i.e. including rdf:type
	 * @param model an RDF2Go model
	 * @param resource
	 */
	public static void deleteAllProperties(Model model,	org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.deleteAllProperties(model, instanceResource);
	}

    ///////////////////////////////////////////////////////////////////
    // property access methods

	/**
	 * @param model an RDF2Go model
	 * @param objectValue
	 * @return all A's as RDF resources, that have a relation 'Semantics' to this Semantics instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse1static] 
	 */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllSemantics_Inverse(Model model, Object objectValue) {
		return Base.getAll_Inverse(model, eu.dime.ps.semantic.model.nrl.Data.SEMANTICS, objectValue);
	}

	/**
	 * @return all A's as RDF resources, that have a relation 'Semantics' to this Semantics instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse1dynamic] 
	 */
	public ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllSemantics_Inverse() {
		return Base.getAll_Inverse(this.model, eu.dime.ps.semantic.model.nrl.Data.SEMANTICS, this.getResource() );
	}

	/**
	 * @param model an RDF2Go model
	 * @param objectValue
	 * @return all A's as a ReactorResult, that have a relation 'Semantics' to this Semantics instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse-as1static] 
	 */
	public static ReactorResult<org.ontoware.rdf2go.model.node.Resource> getAllSemantics_Inverse_as(Model model, Object objectValue) {
		return Base.getAll_Inverse_as(model, eu.dime.ps.semantic.model.nrl.Data.SEMANTICS, objectValue, org.ontoware.rdf2go.model.node.Resource.class);
	}


	/**
	 * @param model an RDF2Go model
	 * @param objectValue
	 * @return all A's as RDF resources, that have a relation 'Realizes' to this Semantics instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse1static] 
	 */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllRealizes_Inverse(Model model, Object objectValue) {
		return Base.getAll_Inverse(model, eu.dime.ps.semantic.model.nrl.ViewSpecification.REALIZES, objectValue);
	}

	/**
	 * @return all A's as RDF resources, that have a relation 'Realizes' to this Semantics instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse1dynamic] 
	 */
	public ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllRealizes_Inverse() {
		return Base.getAll_Inverse(this.model, eu.dime.ps.semantic.model.nrl.ViewSpecification.REALIZES, this.getResource() );
	}

	/**
	 * @param model an RDF2Go model
	 * @param objectValue
	 * @return all A's as a ReactorResult, that have a relation 'Realizes' to this Semantics instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse-as1static] 
	 */
	public static ReactorResult<org.ontoware.rdf2go.model.node.Resource> getAllRealizes_Inverse_as(Model model, Object objectValue) {
		return Base.getAll_Inverse_as(model, eu.dime.ps.semantic.model.nrl.ViewSpecification.REALIZES, objectValue, org.ontoware.rdf2go.model.node.Resource.class);
	}



    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@47ebae27 has at least one value set 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return true if this property has at least one value
	 *
	 * [Generated from RDFReactor template rule #get0has-static] 
     */
	public static boolean hasSemanticsDefinedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.has(model, instanceResource, SEMANTICSDEFINEDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@47ebae27 has at least one value set 
     * @return true if this property has at least one value
	 *
	 * [Generated from RDFReactor template rule #get0has-dynamic] 
     */
	public boolean hasSemanticsDefinedBy() {
		return Base.has(this.model, this.getResource(), SEMANTICSDEFINEDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@47ebae27 has the given value (maybe among other values).  
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given value
	 *
	 * [Generated from RDFReactor template rule #get0has-value-static] 
     */
	public static boolean hasSemanticsDefinedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value ) {
		return Base.hasValue(model, instanceResource, SEMANTICSDEFINEDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@47ebae27 has the given value (maybe among other values).  
	 * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given value
	 *
	 * [Generated from RDFReactor template rule #get0has-value-dynamic] 
     */
	public boolean hasSemanticsDefinedBy( org.ontoware.rdf2go.model.node.Node value ) {
		return Base.hasValue(this.model, this.getResource(), SEMANTICSDEFINEDBY);
	}

     /**
     * Get all values of property SemanticsDefinedBy as an Iterator over RDF2Go nodes 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get7static] 
     */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllSemanticsDefinedBy_asNode(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_asNode(model, instanceResource, SEMANTICSDEFINEDBY);
	}
	
    /**
     * Get all values of property SemanticsDefinedBy as a ReactorResult of RDF2Go nodes 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a List of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get7static-reactor-result] 
     */
	public static ReactorResult<org.ontoware.rdf2go.model.node.Node> getAllSemanticsDefinedBy_asNode_(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_as(model, instanceResource, SEMANTICSDEFINEDBY, org.ontoware.rdf2go.model.node.Node.class);
	}

    /**
     * Get all values of property SemanticsDefinedBy as an Iterator over RDF2Go nodes 
     * @return a ClosableIterator of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get8dynamic] 
     */
	public ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllSemanticsDefinedBy_asNode() {
		return Base.getAll_asNode(this.model, this.getResource(), SEMANTICSDEFINEDBY);
	}

    /**
     * Get all values of property SemanticsDefinedBy as a ReactorResult of RDF2Go nodes 
     * @return a List of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get8dynamic-reactor-result] 
     */
	public ReactorResult<org.ontoware.rdf2go.model.node.Node> getAllSemanticsDefinedBy_asNode_() {
		return Base.getAll_as(this.model, this.getResource(), SEMANTICSDEFINEDBY, org.ontoware.rdf2go.model.node.Node.class);
	}
     /**
     * Get all values of property SemanticsDefinedBy     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of $type
	 *
	 * [Generated from RDFReactor template rule #get11static] 
     */
	public static ClosableIterator<org.ontoware.rdfreactor.schema.rdfs.Resource> getAllSemanticsDefinedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll(model, instanceResource, SEMANTICSDEFINEDBY, org.ontoware.rdfreactor.schema.rdfs.Resource.class);
	}
	
    /**
     * Get all values of property SemanticsDefinedBy as a ReactorResult of org.ontoware.rdfreactor.schema.rdfs.Resource 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ReactorResult of $type which can conveniently be converted to iterator, list or array
	 *
	 * [Generated from RDFReactor template rule #get11static-reactorresult] 
     */
	public static ReactorResult<org.ontoware.rdfreactor.schema.rdfs.Resource> getAllSemanticsDefinedBy_as(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_as(model, instanceResource, SEMANTICSDEFINEDBY, org.ontoware.rdfreactor.schema.rdfs.Resource.class);
	}

    /**
     * Get all values of property SemanticsDefinedBy     * @return a ClosableIterator of $type
	 *
	 * [Generated from RDFReactor template rule #get12dynamic] 
     */
	public ClosableIterator<org.ontoware.rdfreactor.schema.rdfs.Resource> getAllSemanticsDefinedBy() {
		return Base.getAll(this.model, this.getResource(), SEMANTICSDEFINEDBY, org.ontoware.rdfreactor.schema.rdfs.Resource.class);
	}

    /**
     * Get all values of property SemanticsDefinedBy as a ReactorResult of org.ontoware.rdfreactor.schema.rdfs.Resource 
     * @return a ReactorResult of $type which can conveniently be converted to iterator, list or array
	 *
	 * [Generated from RDFReactor template rule #get12dynamic-reactorresult] 
     */
	public ReactorResult<org.ontoware.rdfreactor.schema.rdfs.Resource> getAllSemanticsDefinedBy_as() {
		return Base.getAll_as(this.model, this.getResource(), SEMANTICSDEFINEDBY, org.ontoware.rdfreactor.schema.rdfs.Resource.class);
	}
 
    /**
     * Adds a value to property SemanticsDefinedBy as an RDF2Go node 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #add1static] 
     */
	public static void addSemanticsDefinedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.add(model, instanceResource, SEMANTICSDEFINEDBY, value);
	}
	
    /**
     * Adds a value to property SemanticsDefinedBy as an RDF2Go node 
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #add1dynamic] 
     */
	public void addSemanticsDefinedBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.add(this.model, this.getResource(), SEMANTICSDEFINEDBY, value);
	}
    /**
     * Adds a value to property SemanticsDefinedBy from an instance of org.ontoware.rdfreactor.schema.rdfs.Resource 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #add3static] 
     */
	public static void addSemanticsDefinedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdfreactor.schema.rdfs.Resource value) {
		Base.add(model, instanceResource, SEMANTICSDEFINEDBY, value);
	}
	
    /**
     * Adds a value to property SemanticsDefinedBy from an instance of org.ontoware.rdfreactor.schema.rdfs.Resource 
	 *
	 * [Generated from RDFReactor template rule #add4dynamic] 
     */
	public void addSemanticsDefinedBy(org.ontoware.rdfreactor.schema.rdfs.Resource value) {
		Base.add(this.model, this.getResource(), SEMANTICSDEFINEDBY, value);
	}
  

    /**
     * Sets a value of property SemanticsDefinedBy from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be set
	 *
	 * [Generated from RDFReactor template rule #set1static] 
     */
	public static void setSemanticsDefinedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.set(model, instanceResource, SEMANTICSDEFINEDBY, value);
	}
	
    /**
     * Sets a value of property SemanticsDefinedBy from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set1dynamic] 
     */
	public void setSemanticsDefinedBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.set(this.model, this.getResource(), SEMANTICSDEFINEDBY, value);
	}
    /**
     * Sets a value of property SemanticsDefinedBy from an instance of org.ontoware.rdfreactor.schema.rdfs.Resource 
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set3static] 
     */
	public static void setSemanticsDefinedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdfreactor.schema.rdfs.Resource value) {
		Base.set(model, instanceResource, SEMANTICSDEFINEDBY, value);
	}
	
    /**
     * Sets a value of property SemanticsDefinedBy from an instance of org.ontoware.rdfreactor.schema.rdfs.Resource 
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set4dynamic] 
     */
	public void setSemanticsDefinedBy(org.ontoware.rdfreactor.schema.rdfs.Resource value) {
		Base.set(this.model, this.getResource(), SEMANTICSDEFINEDBY, value);
	}
  


    /**
     * Removes a value of property SemanticsDefinedBy as an RDF2Go node 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove1static] 
     */
	public static void removeSemanticsDefinedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.remove(model, instanceResource, SEMANTICSDEFINEDBY, value);
	}
	
    /**
     * Removes a value of property SemanticsDefinedBy as an RDF2Go node
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove1dynamic] 
     */
	public void removeSemanticsDefinedBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.remove(this.model, this.getResource(), SEMANTICSDEFINEDBY, value);
	}
    /**
     * Removes a value of property SemanticsDefinedBy given as an instance of org.ontoware.rdfreactor.schema.rdfs.Resource 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove3static] 
     */
	public static void removeSemanticsDefinedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdfreactor.schema.rdfs.Resource value) {
		Base.remove(model, instanceResource, SEMANTICSDEFINEDBY, value);
	}
	
    /**
     * Removes a value of property SemanticsDefinedBy given as an instance of org.ontoware.rdfreactor.schema.rdfs.Resource 
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove4dynamic] 
     */
	public void removeSemanticsDefinedBy(org.ontoware.rdfreactor.schema.rdfs.Resource value) {
		Base.remove(this.model, this.getResource(), SEMANTICSDEFINEDBY, value);
	}
  
    /**
     * Removes all values of property SemanticsDefinedBy     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #removeall1static] 
     */
	public static void removeAllSemanticsDefinedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.removeAll(model, instanceResource, SEMANTICSDEFINEDBY);
	}
	
    /**
     * Removes all values of property SemanticsDefinedBy	 *
	 * [Generated from RDFReactor template rule #removeall1dynamic] 
     */
	public void removeAllSemanticsDefinedBy() {
		Base.removeAll(this.model, this.getResource(), SEMANTICSDEFINEDBY);
	}
 }