TestVertex : UnitTest {

	setUp {
	}

	tearDown {
	}

	testVertexIsInitialized {
		this.assert( Vertex.vertexTypeDict.isKindOf(IdentityDictionary),
			"Vertex Type Dictionary created");
	}

}