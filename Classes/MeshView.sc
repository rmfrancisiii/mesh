MeshView {

	var <>model, <>window, <>listView;

	*new { |mesh|
		^super.newCopyArgs(mesh).init(mesh);
	}

	init { |mesh|
		var name = mesh.meshName.asSymbol;
		var hostList = mesh.hostManager.hostList;
		hostList.addDependant(this);
		listView = ListView();
		this.setListView(hostList);
		this.makeGui(name);
	}

	setListView {|obj|
		listView.items_(obj.collectAs({
			|obj| obj.name ++ " | " ++ if(obj.online, "online", "offline")}, Array));
	}


	makeGui {|name|
		window = Window(name).front;
		window.layout = VLayout.new.add(listView);
		window.alwaysOnTop = true;
		window.onClose_({ model.removeDependant(this)});
	}

	update { |obj, what, val| {this.setListView(obj)}.defer }

}