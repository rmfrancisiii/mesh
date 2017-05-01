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

	*new {| vertexName, type, hostName, meshName ... passArgs|
		if (meshName.isNil) { meshName = Mesh.current.name };
		if (meshName.isNil) {"nil Mesh".error; ^ Error};

		^ Mesh.at(meshName).vertexes[vertexName] ?? {
			vertexTypeDict[type.asSymbol].vertexRequestor( name, Mesh.at(meshName)[hostName], Mesh(meshName), *passArgs);
		}
	}
}
