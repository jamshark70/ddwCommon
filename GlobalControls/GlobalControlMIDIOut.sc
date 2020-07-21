
GlobalControlMIDIOut {
	var model, midiout, midichannel, ccnum;

	*new { |model, midiout, midichannel, ccnum|
		^super.newCopyArgs(model, midiout, midichannel, ccnum).init;
	}

	init {
		if(midiout.isNil) { Error("GlobalControlMIDIOut: MIDIOut is empty").throw };
		if(midichannel.isNil) { midichannel = 0 };
		if(ccnum.isNil) { ccnum = 1 };
		model.addDependant(this);
	}

	free {
		model.removeDependant(this);
	}

	update { |obj, what|
		switch(what[\what])
		{ \modelWasFreed } { this.free }
		{ \value } {
			midiout.control(midichannel, ccnum, (model.unmappedValue * 127).round.asInteger);
		}
		{ \spec } {
			midiout.control(midichannel, ccnum, (model.unmappedValue * 127).round.asInteger);
		}
	}
}

+ GlobalControlBase {
	connectMIDIOut { |midiout, midichannel, ccnum|
		^GlobalControlMIDIOut(this, midiout, midichannel, ccnum);
	}
}
