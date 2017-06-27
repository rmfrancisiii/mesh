(
// a SynthDef
SynthDef(\test, { | out, freq = 440, amp = 0.1, nharms = 10, pan = 0, gate = 1 |
    var audio = Blip.ar(freq, nharms, amp);
    var env = Linen.kr(gate, doneAction: 2);
    OffsetOut.ar(out, Pan2.ar(audio, pan, env) );
}).add;
)

(
Pbind(
    \instrument, \test,
	\server, Server.default,
    \nharms, Pseq([4, 10, 40], inf),
    \dur, Pseq([1, 1, 2, 1]/10, inf),
    #[freq, sustain], Ptuple([
		 // assignment to multiple keys
         Pseq( (1..16) * 50, 4),
         Pseq([1/10, 0.5, 1, 2], inf)
     ])
).play;
)
