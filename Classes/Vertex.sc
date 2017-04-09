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

	*new {| vertexName, vertexType, vertexHost = (Mesh.me), vertexMesh = (Mesh.peek) ... passArgs|
		if (vertexMesh.isNil) {"nil Mesh".error; ^ Error};
		^ vertexMesh.vertexList[vertexName] ?? {
			// vertex does not exist? send a request to the VertexType Requestor
			vertexTypeList[vertexType.asSymbol].requestor( vertexName, vertexHost, vertexMesh.name, *passArgs)
		}
	}
}






