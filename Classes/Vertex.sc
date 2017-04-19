Vertex {
	classvar <vertexTypeDict;

	// This is the interface for creating and controlling vertexes and vertexTypes,
	// any method calls get passed to the vertex type OR vertex Proxy object in the vertexDict

	*initVertexTypeDict { // called by Mesh.initClass
		vertexTypeDict = VertexAbstract.subclasses.collectAs({|item|
			var key = item.name.asString.drop(6);
			key[0] = key.first.toLower;
			key.asSymbol -> item;
		}, IdentityDictionary);

		// For all vertex types, try initializing their OSCdefs:
		vertexTypeDict.keysValuesDo({|key, value| value.tryPerform(\makeOSCDefs)});
	}

	*new {| vertexname, type, hostName, meshName ... passArgs|

		//need to look at this bit closer, if arg has an unknown Mesh, it just creates it.

		if (meshName.isNil) {meshName = Mesh.peek.name};
		if (meshName.isNil) {"nil Mesh".error; ^ Error};


		// TODO: Handle nil or wrong VertexType
		^ Mesh(name).vertexDict[name] ?? {
			vertexTypeDict[type.asSymbol].vertexRequestor( name, Mesh(name)[hostName], Mesh(name), *passArgs);
			^ "New Vertex Requested".inform
		}
	}
}
