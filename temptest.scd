~patchbay = TestPatchbay();
~patchbay.postln;
~patchbay.dump
//init;
~patchbay.methods.do({|i| i.name.postln});
~patchbay.addPort(\port1);
~patchbay.addPort(\port2);
~patchbay.addPatch(\port1, \port2);
~patchbay.portList.postln;


~patchbay.instVarDict.postln;
