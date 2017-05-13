VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *requestor { |vertexHost...passArgs|
    var path = this.makeOSCdefPath("Request", "Vertex");
    vertexHost.sendMsg(path, *passArgs)
  }

  *makeOSCdef {|transaction, object, method|
    OSCdef(this.makeOSCdefName(transaction, object), {
      |msg, time, host, recvPort|
        msg = this.oscMsgToVertexMessage(host, msg);
        this.tryPerform(method, msg);
    }, this.makeOSCdefPath(transaction, object));
  }

  *oscMsgToVertexMessage {|requestingHost, msg|
    ^ VertexMessage.new(requestingHost, msg)
  }

  *makeAbstractOSCDefs {
    this.makeOSCdef("Request", "Vertex", \tryMakeVertex);
    this.makeOSCdef("Confirm", "Vertex", \confirmVertex);
    this.makeOSCdef("Error", "Vertex", \errorVertex);
    this.makeOSCdef("Request", "Proxy", \tryMakeProxy);
    this.makeOSCdef("Confirm", "Proxy", \confirmProxy);
    this.makeOSCdef("Error", "Proxy", \errorProxy);

  }

  *makeOSCdefPath {|transaction, object|
    ^ "/" ++ this.name ++ "/" ++ transaction ++ "/" ++ object
  }

  *makeOSCdefName {|transaction, object|
    ^ (this.asSymbol ++ transaction ++ object).asSymbol
  }

  *tryMakeVertex { |msg|
    if (this.vertexExists(msg))
       { this.sendVertexError(msg, "Vertex name already in use") }

       { "received vertex request".postln;
         try { this.makeVertex(msg) }
             { |error| this.sendVertexError(msg, error)};

         this.sendVertexConfirmation(msg);
       };
  }

  *vertexExists {|msg|
    ^ (msg.mesh).includesVertex(msg.vertexName)
  }

  *makeVertex{ |msg|
    var vertex = super.new.initVertex(msg.vertexName, msg.mesh, msg.args);
    Error("This is a basic error.").throw;
  //  msg.mesh.vertexes.put(msg.vertexName, vertex);
  //  this.sendVertexConfirmation(msg);
  }

  *sendVertexConfirmation { |msg|
		var path = (this.makeOSCdefPath("Confirm", "Vertex"));
    this.relayMsg(path, msg);
	}

  *sendVertexError { |msg, error|
  	var path = (this.makeOSCdefPath("Error", "Vertex"));
    var errorString = error.errorString;
    this.relayMsg(path, msg, errorString);
  }

  *sendProxyRequest{ |msg|
  	var path = (this.makeOSCdefPath("Request", "Proxy"));
    this.relayMsg(path, msg);
  }

  *relayMsg {|path, msg, error|
    msg.requestingHost.sendMsg(path, msg, error);
  }

  *tryMakeProxy { |msg|
    /*var proxyHost = Mesh.thisHost;
    var oscAddr = msg[0];
    var vertexName = msg[1];
    var mesh = Mesh(msg[2]);
    var args = msg[3..];

    if (vertexHost.ip != Mesh.thisHost.ip){

      "received proxy request".postln;

      if (mesh.includesVertex(vertexName).not)
        {
          if (this.makeProxy(vertexName, mesh, vertexHost, args))
            { "Proxy added, sending Proxy Confirmation".postln;
              this.sendProxyConfirmation(vertexName, mesh.name, proxyHost, vertexHost);
            }{ var error;
              // TODO:  construct Proxy Creation Failed Error Message
              this.sendProxyError(vertexName, mesh.name, vertexHost, error);
              // add caught error?
            }
        }

        {var error;
          // TODO:  construct Proxy Creation Error Message
          this.sendProxyError(vertexName, mesh.name, vertexHost, error);
          // add caught error?
        }
    }*/
  }

  *makeProxy{ |msg|
    /*var proxy = super.new.initProxy(vertexName, mesh, vertexHost, args);
    mesh.vertexes.put(vertexName, proxy);
    ^ true*/
  }

  *sendProxyConfirmation { |msg|
    var path = (this.makeOSCdefPath("Confirm", "Proxy"));
    this.relayMsg(path, msg);
	  }

  *sendProxyError { |msg, error|
  	var path = (this.makeOSCdefPath("Error", "Proxy"));
    this.relayMsg(path, msg, error);
	  }

	*confirmProxy {|msg|
  	"Proxy Confirmed".postln;
	}

	*confirmVertex {|msg|
		"Vertex Confirmed".postln;
	}

	*errorProxy {|msg|
  	"Proxy Error".postln;
	}

	*errorVertex {|msg|
    "ERROR".postln;
    msg.postln;

    /*var vertexName = msg[1];
    var meshName = msg[2];
    var errorString = msg[3];
		("Vertex " ++ msg.vertexName ++ "reports error in " ++ msg.mesh.name).postln; errorString.postln;*/
	}


}
