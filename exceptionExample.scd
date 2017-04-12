(
~inPlaceSub = { |array, find, replace|
    array.do({ |item, i|
        if(item == find) { array[i] = replace };
    });
};

~trySub = { |array, find, replace|
    try {
        ~inPlaceSub.(array, find, replace)
    } { |error|
        switch(error.species.name)
            { 'PrimitiveFailedError' } {
                if(error.what.find("immutable").notNil) {
                    "caught ImmutableError".postln;
                    ~inPlaceSub.(array.copy, find, replace)
                } { "unknown primitive exception".postln; error.throw; }
            }
                // default case: unhandled exception, should die so re-throw error
            { "unknown exception".postln; error.throw; }
    };
};
)

// pass in a mutable array, OK
~trySub.((0..9), 9, 19);

// pass in a literal array, Immutable exception is caught and handled
~trySub.(#[0, 1, 2, 3, 4, 5], 5, 6);

// pass in a nonsense value, other exception is re-thrown
~trySub.(10, 5, 6);