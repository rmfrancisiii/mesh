MeshCommandParser{
	classvar active = true, chatTag = "//!!", panicTag = "//??", pingTag = "//**";
	classvar pingFunc;

	*initClass {
		pingFunc = { |str| if (str.beginsWith(pingTag)) { Mesh.peek.ping } };
	}

	*on { thisProcess.interpreter.codeDump = pingFunc }

	*off { thisProcess.interpreter.codeDump.removeFunc(pingFunc) }

	*status { if (active) {^"on"}{^"off"} }
}