MeshView {

	var <>model, <>window, <>listView;

	*new { |model|
		^super.newCopyArgs(model).init(model);
	}

	init { |mdl|
		model = mdl;
		model.addDependant(this);
		listView = ListView().items_(model.getPairs);

		listView = ListView().items_(model.collectAs({
			|obj| obj.name ++ " | " ++ if(obj.online, "online", "offline")}, Array)

	);


		this.makeGui;
	}

	makeGui {
		window = Window(\window).front;
		window.layout = VLayout.new.add(listView);
		window.onClose_({ model.removeDependant(this)});
	}

	update { |obj, what, val|
		listView.items_(obj.collectAs({
			|obj| obj.name ++ " | " ++ if(obj.online, "online", "offline")}, Array));

		"refreshed MeshView".postln;

	}

}