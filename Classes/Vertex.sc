Vertex {

	// This is the interface for creating and controlling vertexes,
	// any method calls get passed to the vertex

	classvar vertexTypeList;
	var <>obj;

	*initClass {
		vertexTypeList = this.getVertexTypeList;
	}

	*getVertexTypeList {
		// TODO: this should be abstracted
		^ IdentityDictionary.with(*[
			\server -> VertexServer,
			\audioOut -> VertexAudioOut,
			\audioIn -> VertexAudioIn
		]);
	}

	*new {|name, type ... passArgs|
		var vertexList;
		if (Mesh.peek.isNil){"Sorry, no active Mesh".error};
		vertexList = Mesh.peek.vertexList;
		^ vertexList[name] ?? {^ super.new.init(name, type, vertexList, passArgs)}
	}

	init {|myName, myType, myVertexList, passArgs|
		if (myType.isNil){^ "Vertex Does Not Exist. To create a Vertex, you must supply a type.".error};
		obj = vertexTypeList[myType.asSymbol].new(*passArgs);
		myVertexList.add(myName.asSymbol -> this);
	}

}






