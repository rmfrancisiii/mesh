Vertex {
	classvar <vertexTypeDict;

	*initVertexTypes { // called by Mesh.initClass
		vertexTypeDict = this.collectVertexTypes;
		this.initTypeOSCdefs;
	}

	*collectVertexTypes{
		var allTypes = VertexAbstract.subclasses;
		var dict = allTypes.collectAs(
				{ |vertexType|
					var key = this.trimClassName(vertexType.name);
		 			key -> vertexType },
				IdentityDictionary);
		^ dict;
	}

	*trimClassName{ |key|
		key = key.asString.drop(6);
		key[0] = key.first.toLower;
		^ key.asSymbol;
	}

	*initTypeOSCdefs{
		vertexTypeDict.keysValuesDo({|key, value| value.tryPerform(\makeOSCDefs)
		})
	}

	*new {| vertexName, vertexType, vertexHost, meshName ...passArgs|
		var mesh = this.getMesh(meshName); // add TRY
		var vertex = this.getVertex(vertexName, mesh);
		vertexHost = mesh[vertexHost];
		vertexType = vertexTypeDict[vertexType];

		if (vertex == List.new)
			{	vertexType.requestor( vertexHost, vertexName, mesh.name, *passArgs) }
		^ vertex
	}

	*getMesh{|meshName|
		if (meshName.isNil)
				{	^ this.currentMesh };
		^ Mesh.at(meshName)
	}

	*currentMesh {
		if (Mesh.hasCurrent)
				{^ Mesh.current};
		"nil Mesh".error; ^ Error;
	}

	*getVertex {|vertexName, mesh|
		var vertex = mesh.vertexes.at(vertexName);
		if (vertex.isNil)
				{^ List.new};
		^ vertex
	}
}
