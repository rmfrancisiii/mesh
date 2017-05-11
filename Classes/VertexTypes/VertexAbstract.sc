VertexAbstract {
  var <>name, <>mesh, <>isProxy;

  *requestor { |vertexHost...passArgs|
    var path = (this.requestPath("Vertex"));
    vertexHost.sendMsg(path, *passArgs);
  }

  *makeAbstractOSCDefs {
    var typeName = this.name;

    OSCdef(this.requestName("Vertex"), {|msg, time, requestingHost, recvPort|
      "received vertex request".postln;
      this.tryMakeVertex(requestingHost, msg);
    }, this.requestPath("Vertex"));

    OSCdef(this.requestName("Proxy"), {|msg, time, vertexHost, recvPort|
      "received proxy request".postln;
      this.tryMakeProxy(vertexHost, msg);
      }, this.requestPath("Proxy"));

    OSCdef(this.confirmName("Vertex"), {|msg, time, vertexHost, recvPort|
      this.confirmVertex(vertexHost, msg);
      }, this.confirmPath("Vertex"));

    OSCdef(this.confirmName("Proxy"), {|msg, time, requestingHost, recvPort|
      this.confirmProxy(requestingHost, msg);
      }, this.confirmPath("Proxy"));
  }

  *requestPath {|method|
    ^ "/" ++ this.name ++ "/request/" ++ method
  }

  *confirmPath {|method|
    ^ "/" ++ this.asSymbol ++ "/confirm/" ++ method
  }

  *requestName {|method|
    ^ (this.asSymbol ++ "Request" ++ method).asSymbol
  }

  *confirmName {|method|
    ^ (this.asSymbol ++ "Confirm" ++ method).asSymbol
  }

  *tryMakeVertex { |requestingHost, msg|
		var oscAddr = msg[0];
		var vertexName = msg[1];
		var mesh = Mesh(msg[2]);
		var vertexHost = Mesh.thisHost;
    var args = msg[3..];

		if (mesh.includesVertex(vertexName).not)
			{
				if (this.makeVertex(vertexName, mesh, args))
					{ "Vertex added, sending Vertex Confirmation".postln;
						this.sendVertexConfirmation(vertexName, mesh.name, requestingHost);
						this.sendProxyRequest(vertexName, mesh.name)
					}{
						"failed to add Vertex".postln;
					}
			}

			{	"error, vertex exists".postln}

	}

  *makeVertex{ |vertexName, mesh...args|
    var vertex = super.new.initVertex(vertexName, mesh, args);
    mesh.vertexes.put(vertexName, vertex);
    ^ true
  }

  *sendVertexConfirmation { |vertexName, meshName, requestingHost|
			var path = (this.confirmPath("Vertex"));
			requestingHost.sendMsg(path, vertexName, meshName);
		}

  *sendProxyRequest{ |vertexName, meshName|
  			var path = (this.requestPath("Proxy"));
  			Mesh.broadcastAddr.sendMsg(path, vertexName, meshName);
  	}

  *tryMakeProxy { |vertexHost, msg|
    var proxyHost = Mesh.thisHost;
    var oscAddr = msg[0];
    var vertexName = msg[1];
    var mesh = Mesh(msg[2]);
    var args = msg[3..];

    if (mesh.includesVertex(vertexName).not)
      {
        if (this.makeProxy(vertexName, mesh, vertexHost, args))
          { "Proxy added, sending Proxy Confirmation".postln;
            this.sendProxyConfirmation(vertexName, mesh.name, proxyHost, vertexHost);
          }{
            "failed to add Proxy".postln;
          }
      }

      {	"error, proxy exists".postln}

  }

    *makeProxy{ |vertexName, mesh, vertexHost, args|
      var proxy = super.new.initProxy(vertexName, mesh, vertexHost, args);
      mesh.vertexes.put(vertexName, proxy);
      ^ true
    }


    *sendProxyConfirmation { |vertexName, meshName, proxyHost, vertexHost|
        var path = (this.confirmPath("Proxy"));
        vertexHost.sendMsg(path, vertexName, meshName);
      }

	*confirmProxy {|proxyHost, msg|
      msg.postln;

      /*(proxyHost ++ " reports a proxy for " ++ msg[1].asSymbol ++ " was added to Mesh(" ++ msg[2] ++ ")").postln;*/
     // VertexServer/confirm/Proxy, server1, mesh1


  	"Proxy Confirmed".postln;
    //      /VertexServer/confirm/Proxy, server1, mesh1

	}

	*confirmVertex {|vertexHost, msg|
    vertexHost.postln;
    msg.postln;
		"Vertex Confirmed".postln;
	}

}
