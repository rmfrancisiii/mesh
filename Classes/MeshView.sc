MeshView {

	var <>model, <>window, <>listView;

	*new { |mesh|
		^super.newCopyArgs(mesh).init(mesh);
	}

	init { |mesh|
		var name = mesh.name.asSymbol;
		var hostList = mesh.hostManager.hostList;
		hostList.addDependant(this);
		listView = ListView();
		this.setListView(hostList);
		this.makeGui(name);
	}

	setListView {|obj|
		listView.items_(obj.collectAs({
			|obj| obj.name ++ " | " ++ if(obj.online, "online", "offline") ++ " | " ++ obj.ip}, Array));
	}


	makeGui {|name|
		window = Window(name).visible_(false);
		window.bounds = Rect(1271, 5, 169, 873);
		window.layout = VLayout.new.add(listView);
		window.alwaysOnTop = true;
		window.onClose_({ model.removeDependant(this)});
	}

	bounds {^ window.bounds}

	bounds_{|rect| window.bounds_(rect)}

	deactivate {
		window.visible_(false);
	}

	activate {
		window.visible_(true);
	}

	free {
		window.close;
	}


	update { |obj, what, val| {this.setListView(obj)}.defer }

}