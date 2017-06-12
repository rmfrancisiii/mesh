Vertex {
	classvar <vertexTypeDict;

	*initVertexTypes { // called by Mesh.initClass
		vertexTypeDict = VertexTypeDict.new;
	}

	*new {| vertexName, vertexTypeName, vertexHostName, meshName...args|
		var mesh = this.getMesh(mesh); // add TRY
		var vertex = this.getVertex(vertexName, mesh);

		MeshDebugMon(thisFunctionDef, vertexName, mesh);

		if (vertex == List.new)
			{	var vertexHost = this.getHost(vertexHostName, mesh);
				var vertexType = vertexTypeDict[vertexTypeName];
				vertexType.sendNewVertex( vertexName, vertexTypeName, vertexHostName, meshName, args);
			};

		^ vertex
	}

	*getHost{|vertexHostName, mesh|
		if (vertexHostName.isNil)
				{	^ Mesh.thisHost};
		^ mesh[vertexHostName];
	}

	*getMesh{|meshName|
		MeshDebugMon(thisFunctionDef, meshName);

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
		MeshDebugMon(thisFunctionDef,vertexName, mesh);
		if (vertex.isNil)
				{^ List.new};
		^ vertex
	}
}
