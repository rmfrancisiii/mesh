Vertex {
	classvar vertexTypeList;
	var <>obj;


	*initClass {
		// obv this should come from a list of vertex types somewhere
		vertexTypeList = IdentityDictionary.with(*[\server -> VertexServer, \type1 -> VertexTypeOne, \type2-> VertexTypeOne, \type3-> VertexTypeThree]);
	}

	*new {|name, type ... passArgs|
		var vertexList;
		if (Mesh.peek.isNil){"Sorry, no active Mesh".error};
		vertexList = Mesh.peek.vertexList
		^ vertexList[name] ?? {^ super.new.init(name, type, vertexList, passArgs)}
	}

	init {|myName, myType, myVertexList, passArgs|
		if (myType.isNil){^ "Vertex Does Not Exist. To create a Vertex, you must supply a type.".error};
		obj = vertexTypeList[myType.asSymbol].new(*passArgs);
		myVertexList.add(myName.asSymbol -> this);
	}

}

VertexAbstract {

	joinMesh {
	}

	free {
	}

}

VertexServer : VertexAbstract {
	var <>host, <>server;

	*new { |myHost, myServer|
		^ super.new.init(myHost, myServer)}

	init { |myHost, myServer|
		host = myHost;
		server = myServer;
	}

	status {
		DebugMon(host);
		DebugMon(server);
	}

}

VertexAudioOut : VertexAbstract {
	var myServer;

	plarp {
	}

}

VertexTypeOne : VertexAbstract  {
	var <>symbol = \testSymbol;


}

VertexTypeTwo : VertexAbstract  {
	var <>symbol = \testSymbol;


}

VertexTypeThree : VertexAbstract  {
	var <>int = 54321;

}