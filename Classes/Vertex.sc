Vertex {
	classvar <vertexTypeDict;

	*initVertexTypes { // called by Mesh.initClass
		vertexTypeDict = VertexTypeDict.new;
	}

	*new {| vertexName, vertexTypeName, vertexHostName, meshName ...passArgs|
		var mesh = this.getMesh(meshName); // add TRY
		var vertex = this.getVertex(vertexName, mesh);
		var vertexHost = mesh[vertexHostName];
		var vertexType = vertexTypeDict[vertexTypeName];

		if (vertex == List.new)
			{	vertexType.requestor( vertexName, vertexType.name, vertexHost, mesh.name, *passArgs )};

		^ vertex
	}

	*getMesh{|meshName|
		if (meshName.isNil)
				{	^ this.currentMesh };
		^ Mesh.at(meshName)
	}

	*currentMesh {
		if (Mesh.hasCurrent)
				{	^ Mesh.current};
		"nil Mesh".error; ^ Error;
	}

	*getVertex {|vertexName, mesh|
		var vertex = mesh.vertexes.at(vertexName);
		if (vertex.isNil)
				{^ List.new};
		^ vertex
	}
}
