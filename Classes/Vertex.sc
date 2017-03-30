Vertex {
	classvar vertexTypeList;
	var <>name, <>type, <>obj;


	*initClass {
		// obv this should come from a list of vertex types somewhere
		vertexTypeList = IdentityDictionary.with(*[\type1 -> VertexTypeOne, \type2-> VertexTypeOne, \type3-> VertexTypeThree]);
	}

	*new {|name, type|

		//this probably breaks some kind of rules? but makes nice syntax
		var vertexList = Mesh.peek.vertexList;

		^ vertexList[name] ?? {^ super.new.init(name, type, vertexList) }
	}

	init {|myName, myType, myVertexList|
		"initializing vertex".postln;

		name = myName.asSymbol;
		type = myType.asSymbol;
		obj = vertexTypeList[type].new;

		myVertexList.add(name -> this);

	}

	free {
		Mesh.peek.vertexList.removeAt[name]
	}


}

VertexTypeOne {
	var <>textString = "test number one";

	plarp {
		"blahblah".postln;
	}

}

VertexTypeTwo {
	var <>symbol = \testSymbol;


}

VertexTypeThree {
	var <>int = 54321;

}