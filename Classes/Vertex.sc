Vertex {

	// This is the interface for creating and controlling vertexes,
	// any method calls get passed to the vertex

	classvar vertexTypeList;
	var <>obj;

	*initVertexTypeList { // called by Mesh.initClass
		vertexTypeList = VertexAbstract.subclasses.collectAs({|item|
			var key = item.name.asString.drop(6);
			key[0] = key.first.toLower;
			key.asSymbol -> item;
		}, IdentityDictionary);

		// For all vertex types, try initializing their OSCdefs:
		vertexTypeList.keysValuesDo({|key, value| value.tryPerform(\makeOSCDefs)});

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






