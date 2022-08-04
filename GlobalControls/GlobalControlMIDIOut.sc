
GlobalControlMIDIOut {
	var model, ctlmsg;
	var <>low = 0, <>high = 127;

	*new { |model, midisender, midichannel, ccnum|
		^super.newCopyArgs(model).init(midisender, midichannel, ccnum);
	}

	init { |midisender, midichannel, ccnum|
		if(midisender.isNil) { Error("GlobalControlMIDIOut: MIDISender is empty").throw };
		if(midichannel.isNil) { midichannel = 0 };
		if(ccnum.isNil) { ccnum = 1 };
		ctlmsg = MIDIControlMessage(ccnum, channel: midichannel, device: midisender, latency: 0);
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
		ctlmsg.play(nil, this.midiValue);
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
	connectMIDIOut { |midisender, midichannel, ccnum|
		^GlobalControlMIDIOut(this, midisender, midichannel, ccnum);
	}
}
