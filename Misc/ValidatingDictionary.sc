ValidatingDictionary {
	var <>dict;
	var <>validator, <>putFallback;

	*new { |dict, validator, putFallback|
		^super.newCopyArgs(dict, validator, putFallback)
	}

	// core methods for speed

	at { |key| ^dict.at(key) }

	put { |key, value|
		if(validator.(value, key, dict) ?? { true }) {
			dict.put(key, value);
		} {
			putFallback.(key, value, dict);
		}
	}

	putGet { |key, value|
		var prev = this.at(key);
		this.put(key, value);
		^prev
	}

	keys { ^dict.keys }
	values { ^dict.values }
	do { |func| ^dict.do(func) }
	keysValuesDo { |func| dict.keysValuesDo(func) }

	copy { ^this.class.new(dict.copy, validator, putFallback) }

	collect { |func|
		^this.class.new(dict.collect(func), validator, putFallback)
	}
	select { |func|
		^this.class.new(dict.select(func), validator, putFallback)
	}
	reject { |func|
		^this.class.new(dict.reject(func), validator, putFallback)
	}

	// some are missing here, probably (invert, merge etc)

	// catch the rest
	doesNotUnderstand { |selector ... args|
		case
		{ dict.respondsTo(selector) } {
			^dict.perform(selector, *args)
		}
		{ dict.tryPerform(\know) == true } {
			^dict.doesNotUnderstand(selector, *args)
		}
	}

	// and these exist in Object so I have to dup them
	printOn { |stream|
		stream << this.class.name << "("
		<< dict << ", " << validator << ", " << putFallback
		<< ")"
	}
	storeOn { |stream|
		stream << this.class.name << ".new("
		<<< dict << ", " <<< validator << ", " <<< putFallback
		<< ")"
	}

	hash { ^dict.hash }

	composeEvents { |event| ^this.copy.putAll(event) }

	== { |that|
		this.class === that.class and: { this.dict == that.dict }
	}

	isRest { ^dict.isRest }
	next { |inval|
		^inval.asValidatingDictionary.composeEvents(this.dict)
		.validator_(inval.tryPerform(\validator) ?? { validator })
		.putFallback_(inval.tryPerform(\putFallback) ?? { putFallback })
	}

	// not at all sure about this
	embedInStream { |event| ^dict.embedInStream(event) }

	stop { ^dict.stop }
	release { |releaseTime| ^dict.release(releaseTime) }
	free { ^dict.free }

	asControlInput { ^dict.asControlInput }
	asUGenInput { ^dict.asControlInput }  // see Event definition

	trueAt { |key| ^dict.trueAt(key) }
	eventAt { |key| ^dict.trueAt(key) }
}

+ Dictionary {
	asValidatingDictionary { ^ValidatingDictionary(this) }
}
