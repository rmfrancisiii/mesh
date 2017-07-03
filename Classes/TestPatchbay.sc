TestPatch {
	var <>rand;
	*new  { ^ super.new.init }
	init { rand = 5.rand }
}

TestPatchbay {
	var <>portList;

	*new { ^ super.new.init }
	init { portList = NamedList() }
	addPort { |portName| portList.addLast(portName, NamedList()) }
	addPatch { |portOut, portIn|
		portList.at(portOut).addLast(portIn, \hello)}
}

