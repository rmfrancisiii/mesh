VertexList : IdentityDictionary {
	at { arg key;
		var me = array.at(this.scanFor(key) + 1);
		^ me !? {^ me.obj};
	}
}