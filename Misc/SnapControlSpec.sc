SnapControlSpec : ControlSpec {
	var <>snap = 0.05;
	map { arg value;
		// maps a value from [0..1] to spec range
		var out = warp.map(value.clip(0.0, 1.0)),
		rounded = out.round(step);
		if((out absdif: rounded) <= snap) { ^rounded } { ^out }
	}
	unmap { arg value;
		// maps a value from spec range to [0..1]
		var rounded = value.round(step);
		if((value absdif: rounded) <= snap) { value = rounded };
		^warp.unmap(value.clip(clipLo, clipHi));
	}
	constrain { arg value;
		var rounded;
		value = value.asFloat.clip(clipLo, clipHi);
		rounded = value.round(step);
		if((value absdif: rounded) <= snap) { ^rounded } { ^value }
	}
}