VertexList : IdentityDictionary {

	//this is what makes Vertex(\Name) return the vertex.obj contained

	at { arg key;
		// because i'm overloading Identity Dictionary here i'm using their code
		var me = array.at(this.scanFor(key) + 1);

		// But I'm returning something different:
		// if the key's not there, I still want to return nil, because nil.obj will throw an exception
		// and i'm using that nil in Vertex.new to decide whether it creates a new vertex or returns an existing Vertex
		// so, if it's a valid entry in the dictionary, I want to return the internal object i.e.
		// vertex.obj instead of just vertex.

		^ me !? {^ me.obj};
	}
}