MeshDebugMon {
	classvar debug = true;

	*new {
		|msg| if (debug){("DEBUG:  " ++ msg).inform}
	}

	*on {debug = true}
	*off {debug = false}
	*status { if (debug) {^"on"}{^"off"} }
}