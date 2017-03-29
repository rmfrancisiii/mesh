MeshView {

	var <>model, <>window, <>listView;

	*new { |model|
		^super.newCopyArgs(model).init(model);
	}

	init { |mdl|
		model = mdl;
		model.addDependant(this);
		listView = ListView();
		this.setListView(mdl);
		this.makeGui;
	}

	setListView {|obj|
		listView.items_(obj.collectAs({
			|obj| obj.name ++ " | " ++ if(obj.online, "online", "offline")}, Array));
	}


	makeGui {
		window = Window(\window).front;
		window.layout = VLayout.new.add(listView);
		window.alwaysOnTop = true;
		window.onClose_({ model.removeDependant(this)});
	}

	update { |obj, what, val|
		this.setListView(obj);


		"refreshed MeshView".postln;

	}

}