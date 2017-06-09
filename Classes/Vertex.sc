Vertex {
	classvar <vertexTypeDict;

	*initVertexTypes { // called by Mesh.initClass
		vertexTypeDict = VertexTypeDict.new;
	}

	*new {| vertexName, vertexTypeName, vertexHostName, meshName...args|

		var mesh = this.getMesh(mesh); // add TRY
		var vertex = this.getVertex(vertex, mesh);
		MeshDebugMon(thisFunctionDef);

		if (vertex == List.new)
			{	var vertexHost = this.getHost(vertexHostName, mesh);
				var vertexType = vertexTypeDict[vertexTypeName];
				vertexType.newVertexRequest( vertexName, vertexTypeName, vertexHostName, meshName, args);
				^ "New Vertex Request sent";
			};

		^ vertex
	}

	*getHost{|vertexHostName, mesh|
		MeshDebugMon(thisFunctionDef);

		if (vertexHostName.isNil)
				{	^ Mesh.thisHost};
		^ mesh[vertexHostName];
	}

	*getMesh{|meshName|
		MeshDebugMon(thisFunctionDef);

		if (meshName.isNil)
				{	^ this.currentMesh };
		^ Mesh.at(meshName)
	}

	*currentMesh {
		MeshDebugMon(thisFunctionDef);

		if (Mesh.hasCurrent)
				{	^ Mesh.current};
		"nil Mesh".error; ^ Error;
	}

	*getVertex {|vertexName, mesh|
		var vertex = mesh.vertexes.at(vertexName);
		MeshDebugMon(thisFunctionDef);

		if (vertex.isNil)
				{^ List.new};
		^ vertex
	}
}
