Vertex {
	classvar vertexTypeList;

	// This is the interface for creating and controlling vertexes and vertexTypes,
	// any method calls get passed to the vertex type OR vertex Proxy object in the vertexList

	*initVertexTypeList { // called by Mesh.initClass
		vertexTypeList = VertexAbstract.subclasses.collectAs({|item|
			var key = item.name.asString.drop(6);
			key[0] = key.first.toLower;
			key.asSymbol -> item;
		}, IdentityDictionary);

		// For all vertex types, try initializing their OSCdefs:
		vertexTypeList.keysValuesDo({|key, value| value.tryPerform(\makeOSCDefs)});
	}

	*new {| name, type, hostName, meshName ... passArgs|

		//need to look at this bit closer, if arg has an unknown Mesh, it just creates it.

		if (meshName.isNil) {meshName = Mesh.peek.meshName};
		if (meshName.isNil) {"nil Mesh".error; ^ Error};

		^ Mesh(meshName).vertexList[name] ?? {
			vertexTypeList[type.asSymbol].requestor( name, Mesh(meshName)[hostName], Mesh(meshName), *passArgs)
		}
	}
}






