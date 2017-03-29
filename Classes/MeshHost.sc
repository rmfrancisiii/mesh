/* ----------------------
- Class: MeshHost
----------------------
-	Class Methods : Args : Return Type
-		new : - : MeshHostList
-
----------------------
- 	Instance Variables : Type : Description
-
-	Instance Methods : Args : Return Type
-
---------------------- */

MeshHost {

	var <name, <>addr, <>online;

	*new {|name, addr, online = true|
		^super.newCopyArgs(name.asSymbol, addr, online);
	}

	*newFrom { |item|
		if(item.isKindOf(this)) { ^item };
		item = item ?? { this.getHostName };
		^this.new(item, MeshHostAddr.localAddr)
	}

	*getHostName {
		var cmd;
		"getting hostname".postln;
		cmd = Platform.case(
			\osx,       { ^ ("hostname".unixCmdGetStdOut.split($.)[0]).stripWhiteSpace.asSymbol},
			\linux,     { ^ ("hostname".unixCmdGetStdOut).stripWhiteSpace.asSymbol }
		);
	}

	isOnline {
		}

	printOn { |stream| stream << this.class.name << "(" << [name, addr, online] << ")" }

}

