Mesh(\mesh1, local:true).push;
Vertex(\localServer, \server, \nadsac, \mesh1);
Vertex(\localServer).boot;


Vertex(\formantSynth, \synth, \nadsac, \mesh1, \formant);
Mesh(\mesh1).patchbay.addPatch(\formantSynth, \localServer);
Vertex(\formantPattern, \pattern, \nadsac, \mesh1);
Mesh(\mesh1).patchbay.addPatch(\formantPattern, \formantSynth);
Vertex(\formantPattern).play;

Vertex(\formantPattern).set(\dur, Pshuf([1/4, 1/4, 1/8, 1/8, 1/2, 1/2], inf));

Vertex(\formantPattern).set(\fund, (Pseq([12, 14, 12, 16, 17, 14], inf)+Prand([0, 12, 24], inf)+Pn(Pseries(0, 1, 7))).midicps);

Vertex(\formantPattern).set(\dur, Prand([1, Pstutter(Pwhite(1, 6, 1), Pseq([0.375, 0.125])), Pstutter(Pwhite(1, 20, 1), Pn(0.1))], inf)*Pwrand([0.1, 1, 10], [0.99, 0.009, 0.001], inf));

Vertex(\formantPattern).set(\form, Pn(Pgeom(4900, 1.05914, 12))*Pstutter(Prand([6, 12, 18, 24], inf), Pwrand([0.25, 0.5, 1, 2], [0.8, 0.09, 0.09, 0.02], inf))*Pbrown(0.8, 1.2, 0.05));


