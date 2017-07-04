MeshNamedList : NamedList {
  // depends upon Modality Quark

storeOn { |stream|
		stream << this.class.name << "("
		<<<* this.storeArgs << ")";
	}

storeArgs { ^[names, array].flop }

pretty {
  names.do({|item, i|
    item.postln;
    array[i].keysDo({|key| ("  " ++ key ).postln });
    "".postln;
    });
}

printOn {|stream|
  stream << "Patches: \n";
  names.do({|item, i|
    stream << item << Char.nl;
    array[i].keysDo({|key| stream << ("  " ++ key ++ Char.nl)});
    stream << Char.nl;
    });
}
}
