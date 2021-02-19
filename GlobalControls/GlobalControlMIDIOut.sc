
GlobalControlMIDIOut {
	var model, midiout, midichannel, ccnum;
	var <>low = 0, <>high = 127;

	*new { |model, midiout, midichannel, ccnum|
		^super.newCopyArgs(model, midiout, midichannel, ccnum).init;
	}

	init {
		if(midiout.isNil) { Error("GlobalControlMIDIOut: MIDIOut is empty").throw };
		if(midichannel.isNil) { midichannel = 0 };
		if(ccnum.isNil) { ccnum = 1 };
		model.addDependant(this);
		this.sendMIDI;
	}

	free {
		model.removeDependant(this);
	}

	setMIDIRange { |low, high|
		this.low_(low).high_(high);
		model.spec_(model.spec);  // force 'changed'
	}

	midiValue {
		^model.unmappedValue.linlin(0, 1, low, high).round.asInteger
	}

	sendMIDI {
		midiout.control(midichannel, ccnum, this.midiValue);
	}

	update { |obj, what|
		switch(what[\what])
		{ \modelWasFreed } { this.free }
		{ \value } {
			this.sendMIDI;
		}
		{ \spec } {
			this.sendMIDI;
		}
	}
}

+ GlobalControlBase {
	connectMIDIOut { |midiout, midichannel, ccnum|
		^GlobalControlMIDIOut(this, midiout, midichannel, ccnum);
	}
}
