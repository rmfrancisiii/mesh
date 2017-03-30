Vertex {
	classvar vertexTypeList;
	var <>name, <>type, <>obj;


	*initClass {
		// obv this should come from a list of vertex types somewhere
		vertexTypeList = IdentityDictionary.with(*[\type1 -> VertexTypeOne, \type2-> VertexTypeOne, \type3-> VertexTypeThree]);
	}

	*new {|name, type|
		var vertexList;
		if (Mesh.peek.isNil){"Sorry, no active Mesh".error};
		vertexList = Mesh.peek.vertexList
		^ vertexList[name] ?? {^ super.new.init(name, type, vertexList)}
	}

	init {|myName, myType, myVertexList|
		if (myType.isNil){^ "Vertex Does Not Exist. To create a Vertex, you must supply a type.".error};

		"initializing vertex".postln;
		name = myName.asSymbol;
		type = myType.asSymbol;
		obj = vertexTypeList[type].new;
		myVertexList.add(name -> this);

	}

}

VertexAbstract {

	joinMesh {
		"joining".postln;
	}

	free {
		"Free This Now!".postln;
	}

}

VertexTypeOne : VertexAbstract {
	var <>textString = "test number one";

	plarp {
		"blahblah".postln;
	}

}

VertexTypeTwo : VertexAbstract  {
	var <>symbol = \testSymbol;


}

VertexTypeThree : VertexAbstract  {
	var <>int = 54321;

}